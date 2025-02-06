package kr.or.ddit.yguniv.graduation.service;

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
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.graduation.dao.GraduationMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;
import kr.or.ddit.yguniv.vo.GraduationVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class GraduationServiceImpl implements GraduationService {

	@Autowired
	private final GraduationMapper mapper;
	@Autowired
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	@Override
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
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
	
	
	@Override
	public List<GraduationVO> selectgraduationList(String stuId) {
		List<GraduationVO>graduationList = mapper.selectgraduationList(stuId);
		return graduationList;
	}

	@Override
	public Integer selectTotalVolunteerScore(String stuId) {
		return mapper.selectTotalVolunteerScore(stuId);
	}
	

	@Override
	public int deletegraduation(int gdtCd) {
		String cocoCd = mapper.selectCocoCdByCd(gdtCd);
		if("G001".equals(cocoCd)) {
			throw new IllegalStateException("승인된 항목은 삭제할 수 없습니다.");
		}
		return mapper.deletegraduation(gdtCd);
	}


	@Override
	public int updategraduation(final GraduationVO graduation) {

	    // 승인 여부 확인
	    String cocoCd = mapper.selectCocoCdByCd(graduation.getGdtCd());
	    if ("G001".equals(cocoCd)) {
	        throw new IllegalStateException("승인된 항목은 수정할 수 없습니다.");
	    }

	    // 새로운 첨부 파일 ID 처리
	    Integer newAtchFileId = Optional.ofNullable(graduation.getAtchFile())
	            .filter(af -> af.getFileDetails() != null)
	            .map(af -> mergeSavedDetailsAndNewDetails(null, af))
	            .orElse(null);

	    graduation.setAtchFileId(newAtchFileId);

	    // 업데이트 실행
	    return mapper.updategraduation(graduation);
	}

	@Override
	public int updategraduationToAccess(GraduationVO graduation) {
		return mapper.updategraduationToAccess(graduation);
	}
	
	@Override
	public int updategraduationToReject(GraduationVO graduation) {
		return mapper.updategraduationToReject(graduation);
		
	}

	

	@Override
	public int insertgraduation(final GraduationVO graduation) {
		Integer atchFileId = Optional.ofNullable(graduation.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		graduation.setAtchFileId(atchFileId);
		return mapper.insertgraduation(graduation); 
	}

	@Override
	public List<GraduationVO> selectgraduationListByEmp(PaginationInfo<GraduationVO>paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<GraduationVO>graduationList = mapper.selectgraduationListByEmp(paging);
		return graduationList;
	}

	@Override
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GraduationVO selectgraduationByCd(int gdtCd) {
		GraduationVO graduation = mapper.selectgraduationByCd(gdtCd);
		if(graduation == null) {
			throw new PKNotFoundException("해당 졸업인증제를 찾을 수 없습니다.");
		}
		return graduation;
	}

	@Override
	public int deletegraduation(GraduationVO graduation) {
		// TODO Auto-generated method stub
		return 0;
	}








}
