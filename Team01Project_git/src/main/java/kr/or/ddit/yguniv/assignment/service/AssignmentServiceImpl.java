package kr.or.ddit.yguniv.assignment.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.assignment.dao.AssignmentMapper;
import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
	private final AssignmentMapper mapper;
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	@Override
	public LectureVO checkLecture(String lectNo) {
		return mapper.checkLecture(lectNo);
	}
	
	@Override
	public void createAssignment(final AssignmentVO assignment) {
		LectureVO lecture = mapper.checkLecture(assignment.getLectNo());
		if(lecture!=null) {
			
			Integer atchFileId = Optional.ofNullable(assignment.getAtchFile())
					.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
					.map(af -> {
						atchFileService.createAtchFile(af, saveFolder);
						return af.getAtchFileId();
					}).orElse(null);
			
			assignment.setLecture(lecture);
			assignment.setLectNm(lecture.getLectNm());
			assignment.setAtchFileId(atchFileId);
			
			mapper.insertAssignment(assignment);
		}
		else {
			throw new PKNotFoundException("해당강의번호는 존재하지않습니다.");
		}
		
	}

	@Override
	public AssignmentVO readAssignment(String assigNo) {
		AssignmentVO assignment = mapper.selectAssignment(assigNo);
		if(assignment == null) {
			throw new BoardException(String.format("%s 번 과제가 없습니다.", assigNo));
		}
		
		return assignment;
	}

	@Override
	public List<AssignmentVO> readAssignmentList(String lectNo) {
		
		return mapper.selectAssignmentList(lectNo);
	}

	@Override
	public List<AssignmentVO> readAssignmentListPaging(PaginationInfo<AssignmentVO> paging,String lectNo) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging,lectNo));
		List<AssignmentVO> assignmentList = mapper.selectAssignmentListPaging(paging,lectNo);
		return assignmentList;
	}

	@Override
	public void modifyAssignment(final AssignmentVO assignment) {
		
		final AssignmentVO saved = readAssignment(assignment.getAssigNo());
		
		Integer newAtchFileId = Optional.ofNullable(assignment.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		assignment.setAtchFileId(newAtchFileId);
		
		mapper.updateAssignment(assignment);
	}

	@Override
	public void removeAssignment(String assigNo) {
		AssignmentVO assignment = mapper.selectAssignment(assigNo);
		
		Optional.ofNullable(assignment.getAtchFileId())
				.ifPresent(fid -> atchFileService.disableAtchFile(fid));
		mapper.deleteAssignment(assigNo);
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
	public boolean checkSubmission(String assigNo, String stuId) {

		return mapper.checkSubmission(assigNo, stuId)>0 ;
	}
	
	
}
