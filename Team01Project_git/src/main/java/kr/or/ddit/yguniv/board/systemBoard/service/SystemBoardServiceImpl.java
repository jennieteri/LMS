package kr.or.ddit.yguniv.board.systemBoard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.board.systemBoard.dao.SystemBoardMapper;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemBoardServiceImpl implements SystemBoardService {

	private final SystemBoardMapper mapper;
	private final AtchFileService atchFileService;

	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}


	@Override
	public void insertSystemBoard(final SystemNoticeBoardVO systemNoticeBoard) {

		
		Integer atchFileId = Optional.ofNullable(systemNoticeBoard.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		systemNoticeBoard.setAtchFileId(atchFileId);
	    mapper.insertSystemBoard(systemNoticeBoard);
				
		
		
	}
	
	@Override
	public SystemNoticeBoardVO selectSystemBoard(String snbNo) {
		SystemNoticeBoardVO system = mapper.selectSystemBoard(snbNo);
		
		if (system == null){
			throw new BoardException(String.format("%s 번 글이 없음.", snbNo));
		
	}
		mapper.snbCount(snbNo);
	
		return system;
	}
	
	@Override
	public List<SystemNoticeBoardVO> selectList() {
		
		return mapper.selectBoardListNonPaging();
	}

//	리스트 출력 
	@Override
	public List<SystemNoticeBoardVO> selectList(PaginationInfo<SystemNoticeBoardVO> paginationInfo) {

		paginationInfo.setTotalRecord(mapper.selectTotalRecord(paginationInfo));
		
		List<SystemNoticeBoardVO> boardList= mapper.selectList(paginationInfo);
		return boardList;
		
	}


	@Override
	public int selectTotalRecord(PaginationInfo<SystemNoticeBoardVO> paginationInfo) {
		
		return 0;
	}
	
	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함.
	 * 
	 * @param atchFileId
	 */
	private Integer mergeSavedDetailsAndNewDetails(AtchFileVO savedAtchFile, AtchFileVO newAtchFile) {
		AtchFileVO mergeAtchFile = new AtchFileVO();
		List<AtchFileDetailVO> fileDetails = Stream.concat(
			Optional.ofNullable(savedAtchFile)
					.filter(saf->! CollectionUtils.isEmpty(saf.getFileDetails()))
					.map(saf->saf.getFileDetails().stream())
					.orElse(Stream.empty())
			, Optional.ofNullable(newAtchFile)
					.filter(naf->! CollectionUtils.isEmpty(naf.getFileDetails()))
					.map(naf->naf.getFileDetails().stream())
					.orElse(Stream.empty())
		).collect(Collectors.toList());		
				
		mergeAtchFile.setFileDetails(fileDetails);
		
		if( ! mergeAtchFile.getFileDetails().isEmpty() ) {
			atchFileService.createAtchFile(mergeAtchFile, saveFolder);
		}
		
		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			atchFileService.disableAtchFile(savedAtchFile.getAtchFileId());
		}

		return mergeAtchFile.getAtchFileId();
	}
	
	
	@Override
	public void updateSystemBoard(final SystemNoticeBoardVO systemNoticeBoard) {
		
		// 기존 게시글 조회
	 SystemNoticeBoardVO saved = mapper.selectSystemBoard(systemNoticeBoard.getSnbNo());

		Integer newAtchFileId = Optional.ofNullable(systemNoticeBoard.getAtchFile())
										.filter(af -> af.getFileDetails() != null)
										.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile(), af))
										.orElse(null);
		
		systemNoticeBoard.setAtchFileId(newAtchFileId);
		mapper.updateSystemBoard(systemNoticeBoard);
	}

	@Override
	public void deleteSystemBoard(String snbNo) {
		 SystemNoticeBoardVO board = mapper.selectSystemBoard(snbNo);
			
			Optional.ofNullable(board.getAtchFileId())
			.ifPresent(fid -> atchFileService.disableAtchFile(fid));

		mapper.deleteSystemBoard(snbNo);
	}
	


	@Override
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
						.filter(fd -> fd.getSavedFile().exists())
						.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
	}

	@Override
	public void removeFile(int atchFileId, int fileSn) {
		atchFileService.removeAtchFileDetail(atchFileId, fileSn, saveFolder);
	}
	

	@Override
	public List<SystemNoticeBoardVO> selectSystemNoticeBoardList(PaginationInfo paginationInfo) {

		if(paginationInfo != null) {
			
			int totalRecord = mapper.selectTotalRecord(paginationInfo);
			paginationInfo.setTotalRecord(totalRecord);
		}
		
		return mapper.selectList(paginationInfo); //실제 데이터 조회
	}


	@Override
	public List<SystemNoticeBoardVO> mainSystemBoardList() {
		
		return mapper.mainSystemBoardList();
	}


}
