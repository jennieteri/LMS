package kr.or.ddit.yguniv.askAward.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.askAward.dao.AskAwardMapper;
import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.AwardAskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class AskAwardServiceImpl implements AskAwardService {
	
	
	private final AskAwardMapper mapper;
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}


	
	
	
	@Override
	public List<AwardAskVO> awardAskList(PaginationInfo<AwardAskVO> paginationInfo) {
		paginationInfo.setTotalRecord(mapper.selectTotalRecord(paginationInfo));
		
		List<AwardAskVO> ask = mapper.selectAwardAskList(paginationInfo);
		
		return ask;
	}

	@Override
	public List<AwardAskVO> list() {
	
		return mapper.selectList();
	}

	@Override
	public AwardAskVO select(String shapDocNo) {
		
	AwardAskVO ask = mapper.selectAskAward(shapDocNo);
	if(ask == null ) {
		throw new BoardException(String.format("%s 글 없음.", shapDocNo));
	}
		
		return ask;
	}

	
	
	
	
	
	@Override
	public void insertAwardAsk(final AwardAskVO ask) {
		
		Integer atchFileId = Optional.ofNullable(ask.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		ask.setAtchFileId(atchFileId);
	    mapper.insertAskAward(ask);
				
		
	}

	
	
	@Override
	public void deleteAwardAsk(String shapDocNo) {
	    AwardAskVO ask = mapper.selectAskAward(shapDocNo);
	    if (ask == null) {
	        throw new BoardException(String.format("삭제 실패: shapDocNo=%s에 해당하는 데이터를 찾을 수 없습니다.", shapDocNo));
	    }
	    
	    Optional.ofNullable(ask.getAtchFileId())
		.ifPresent(fid -> atchFileService.disableAtchFile(fid));
	    
	    mapper.deleteAskAward(shapDocNo);
	}
	
	
	
	
	

	@Override
	public void updateAwardAsk(final AwardAskVO ask) {
		
		AwardAskVO saved = mapper.selectAskAward(ask.getShapDocNo());
		
		Integer newAtchFileId = Optional.ofNullable(ask.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile(), af))
				.orElse(null);

		ask.setAtchFileId(newAtchFileId);
			mapper.updateAskAward(ask);
		
	}

	@Override
	public int selectTotalRecord(PaginationInfo<AwardAskVO> paginationInfo) {
		
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
	public List<AwardAskVO> selectAwardAskList(PaginationInfo<AwardAskVO> paginationInfo) {
		if(paginationInfo != null) {
			int totalRecord = mapper.selectTotalRecord(paginationInfo);
			paginationInfo.setTotalRecord(totalRecord);
		}
		return mapper.selectAwardAskList(paginationInfo);
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
	public List<AwardAskVO> studentAwardAskList(String stuId) {
		 return mapper.selectStudentAwardAsk(stuId);
	}







	 @Override
	    public void updateApplicationStatus(String cocoStts, String shapDocNo, String shapNoReason) {
	        LocalDateTime shapChcDate = null;
	        if ("A03".equals(cocoStts)) {
	            shapChcDate = LocalDateTime.now();
	        }

	        log.info("Updating status: cocoStts={}, shapDocNo={}, shapChcDate={}", cocoStts, shapDocNo, shapChcDate);

	        int updatedRows = mapper.updateStatus(cocoStts, shapDocNo, shapChcDate, shapNoReason);

	        if (updatedRows == 0) {
	            throw new RuntimeException("상태 변경에 실패했습니다. shapDocNo=" + shapDocNo);
	        }
	    }





	


}
