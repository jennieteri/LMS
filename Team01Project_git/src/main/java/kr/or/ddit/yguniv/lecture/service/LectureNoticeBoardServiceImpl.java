package kr.or.ddit.yguniv.lecture.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.lecture.dao.LectureNoticeBoardMapper;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.LectureNoticeBoardVO;

@Service
public class LectureNoticeBoardServiceImpl{
	
	@Autowired
	private LectureNoticeBoardMapper mapper;
	
	
	
	// 파일 다운로드 하려면 있어야하는 서비스
	@Autowired
	private AtchFileService atchFileService;
	
	@Value("#{dirInfo.saveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	

	public boolean insertLectureNoticeBoard(LectureNoticeBoardVO lectNoticeVO) {
		Integer atchFileId = Optional.ofNullable(lectNoticeVO.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		lectNoticeVO.setAtchFileId(atchFileId);
		
		return mapper.insertLectureNoticeBoard(lectNoticeVO) > 0;
		
	}

	public LectureNoticeBoardVO selectLectureNoticeBoard(String cnbNo) {
		return mapper.selectLectureNoticeBoard(cnbNo);
	}
	
	public boolean updateCnbInq(String cnbNo) {
		return mapper.updateCnbInq(cnbNo) > 0;
	}
	
	public int mainBoardCount(String lectNo) {
		return mapper.mainBoardCount(lectNo);
	}
	

	public List<LectureNoticeBoardVO> selectLectureNoticeBoardList(
			LectureNoticeBoardVO lectNoticeVO
			, PaginationInfo<LectureNoticeBoardVO> paging
	) {
		paging.setTotalRecord(mapper.selectTotalRecord(lectNoticeVO, paging));
		return mapper.selectLectureNoticeBoardList(lectNoticeVO, paging);
	}
	public List<LectureNoticeBoardVO> selectLectureNoticeBoardMainList(
			LectureNoticeBoardVO lectNoticeVO) {
		return mapper.selectLectureNoticeBoardMainList(lectNoticeVO);
	}

	public boolean updateLectureNoticeBoard(final LectureNoticeBoardVO lectNoticeVO) {
		final LectureNoticeBoardVO saved = selectLectureNoticeBoard(lectNoticeVO.getCnbNo());
		
		Integer newAtchFileId = Optional.ofNullable(lectNoticeVO.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		lectNoticeVO.setAtchFileId(newAtchFileId);
		return mapper.updateLectureNoticeBoard(lectNoticeVO) > 0;
	}
	

	public boolean deleteLectureNoticeBoard(String cnbNo) {
		return mapper.deleteLectureNoticeBoard(cnbNo) > 0;
		
	}
	
	
	/**
	 * 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
	}
	
	/**
	 * 파일 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn) {
		atchFileService.removeAtchFileDetail(atchFileId, fileSn, saveFolder);
	}
	
	
	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함.
	 * 
	 * @param atchFileId
	 */
	private Integer mergeSavedDetailsAndNewDetails(AtchFileVO savedAtchFile, AtchFileVO newAtchFile) {
		// 새로 병합할 파일 정보 설정
		AtchFileVO mergeAtchFile = new AtchFileVO();
		
		// 기존 파일 그룹과 신규 파일 그룹 병합
		List<AtchFileDetailVO> fileDetails = Stream.concat(
			Optional.ofNullable(savedAtchFile)
					.filter(saf->!CollectionUtils.isEmpty(saf.getFileDetails()))
					.map(saf->saf.getFileDetails().stream())
					.orElse(Stream.empty())
			, Optional.ofNullable(newAtchFile)
					.filter(naf->!CollectionUtils.isEmpty(naf.getFileDetails()))
					.map(naf->naf.getFileDetails().stream())
					.orElse(Stream.empty())
		).collect(Collectors.toList());
		
		 // 병합된 파일 리스트 설정		
		mergeAtchFile.setFileDetails(fileDetails);
		
		 // 병합된 파일이 존재할 경우 저장
		if( ! mergeAtchFile.getFileDetails().isEmpty() ) {
			atchFileService.createAtchFile(mergeAtchFile, saveFolder);
		}
		
		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			atchFileService.disableAtchFile(savedAtchFile.getAtchFileId());
		}

		return mergeAtchFile.getAtchFileId();
	}
	
}
