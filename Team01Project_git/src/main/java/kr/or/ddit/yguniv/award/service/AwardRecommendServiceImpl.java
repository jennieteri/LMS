package kr.or.ddit.yguniv.award.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.award.dao.AwardRecommendMapper;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.AwardAskVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwardRecommendServiceImpl implements AwardRecommendService {
	
	private final AwardRecommendMapper mapper;
	private final AtchFileService atchFileService;
	
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}

	/*
	 * @Override public List<AwardAskVO> awardAskList(PaginationInfo<AwardAskVO>
	 * paginationInfo) { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public List<AwardAskVO> selectList() {
		
		return mapper.selectList();
	}
	
	@Override
	public AwardAskVO selectAwardRec(String shapDocNo) {
		AwardAskVO ask = mapper.selectAwardRec(shapDocNo);
		if(ask == null ) {
			throw new BoardException(String.format("%s 글 없음.", shapDocNo));
		}
			
			return ask;
		}
	

	@Override
	public void createRecAward(AwardAskVO ask) {
		Integer atchFileId = Optional.ofNullable(ask.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		ask.setAtchFileId(atchFileId);
	    mapper.insertAwardRecAward(ask);

	}

	@Override
	public void deleteAwardRec(String shapDocNo) {
		 AwardAskVO ask = mapper.selectAwardRec(shapDocNo);
		    if (ask == null) {
		        throw new BoardException(String.format("삭제 실패: shapDocNo=%s에 해당하는 데이터를 찾을 수 없습니다.", shapDocNo));
		    }
		    
		    Optional.ofNullable(ask.getAtchFileId())
			.ifPresent(fid -> atchFileService.disableAtchFile(fid));
		    
		    mapper.deleteAwardRecAward(shapDocNo);

	}

	@Override
	public void updateAwardRec(AwardAskVO ask) {
		AwardAskVO saved = mapper.selectAwardRec(ask.getShapDocNo());
				
				Integer newAtchFileId = Optional.ofNullable(ask.getAtchFile())
						.filter(af -> af.getFileDetails() != null)
						.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile(), af))
						.orElse(null);
		
				ask.setAtchFileId(newAtchFileId);
					mapper.updateAwardRecAward(ask);

	}

	@Override
	public List<AwardAskVO> selectAwardProRecList(String profeId) {
	
		return mapper.selectAwardProRec(profeId);
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
	
	
	
}
