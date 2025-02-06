package kr.or.ddit.yguniv.graduationpaper.service;

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
import kr.or.ddit.yguniv.graduationpaper.dao.GraduationPaperMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraduationPaperServiceImpl implements GraduationPaperService {
	
	@Autowired
	private final GraduationPaperMapper mapper;
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
	public List<GraduationPaperVO> getGraduationPapers(String stuId) {
		List<GraduationPaperVO>graduationPaperList = mapper.getGraduationPapers(stuId);
		return graduationPaperList;
	}

	@Override
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertGraduationPaper(final GraduationPaperVO graduationpaper) {
		Integer atchFileId = Optional.ofNullable(graduationpaper.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		graduationpaper.setAtchFileId(atchFileId);
		return mapper.insertGraduationPaper(graduationpaper); 
	}

	@Override
	public GraduationPaperVO getPaperById(int gpaCd) {
		return mapper.getPaperById(gpaCd);
	}

	@Override
	public List<GraduationPaperVO> getProfessorStudentsPapers(String profId) {
		return mapper.getProfessorStudentsPapers(profId);
	}

	@Override
	public int updategraduationPaperToAccess(int gpaCd) {
		return mapper.updategraduationPaperToAccess(gpaCd);
	}
	
	

}
