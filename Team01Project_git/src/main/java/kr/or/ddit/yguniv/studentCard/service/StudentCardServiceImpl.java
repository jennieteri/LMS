package kr.or.ddit.yguniv.studentCard.service;

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
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.studentCard.dao.StudentCardMapper;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.StudentCardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentCardServiceImpl implements StudentCardService {
	

	private final StudentCardMapper mapper;
	private final AtchFileService atchFileService;
	
	
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}

	//학색증 신청 
	@Override
	public void createStudentCard(StudentCardVO card) {
		int cnt = mapper.checkCard(card.getStuId());
		if(cnt >0) {
			throw new IllegalArgumentException("학생증 발급 중 입니다.");
		}
		mapper.createStudentCard(card); 

	}
	// 학생증 신청 목록 삭제 
	@Override
	public void deleteStudnetCard(String stuId) {
		StudentCardVO card =  mapper.selectStudentCard(stuId);
		if(card == null) {
			throw new BoardException(String.format("삭제 실패: stuId", card));
		
		}
		
		
		
		mapper.deleteStudentCard(stuId);

	}
	// 학생증 신청 목록 조회 페이징처리 있음 
	@Override
	public List<StudentCardVO> selectStudentCardList(PaginationInfo<StudentCardVO> paginationInfo) {
		paginationInfo.setTotalRecord(mapper.selectTotalRecord(paginationInfo));
		List<StudentCardVO>  card = mapper.studentCardList(paginationInfo);
		
		return card;
	}

	//학생증 신청 상세조회 
	@Override
	public StudentCardVO selectStudentCardDetail(String stuId) {
		
		StudentCardVO card = mapper.selectStudentCard(stuId);
		
		if(card == null) {
			throw new BoardException(String.format("%s 글없음", card));
		}
		
		return card;
				}

	// 학생증 신청 상태 변경 
	@Override
	public String updateStatus(String cocoCd, String stuId) {
	    String nextStatus;
	    switch (cocoCd) {
	        case "ST01":
	            nextStatus = "ST02";
	            break;
	        case "ST02":
	            nextStatus = "ST03";
	            break;
	        default:
	            throw new IllegalArgumentException("잘못된 상태 값입니다: " + cocoCd);
	    }

	    mapper.updateStatus(nextStatus, stuId); // Mapper 호출
	    
	   
	    
	    return nextStatus;
	}




	@Override
	public List<StudentCardVO> selectStudentCardListNonPaging() {
		
		return mapper.studentCardListNonPaging();
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
	public int selectTotalRecord(PaginationInfo<StudentCardVO> paginationInfo) {
		
		return 0;
	}

	//검색 결과 목록
	@Override
	public List<StudentCardVO> selectStudentList(PaginationInfo<StudentCardVO> paginationInfo) {
		if(paginationInfo != null) {
			int totalRecord = mapper.selectTotalRecord(paginationInfo);
			paginationInfo.setTotalRecord(totalRecord);
		}
		return mapper.studentCardList(paginationInfo);
	}



}
