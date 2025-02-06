package kr.or.ddit.yguniv.assignmentSubmission.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.assignment.dao.AssignmentMapper;
import kr.or.ddit.yguniv.assignment.service.AssignmentService;
import kr.or.ddit.yguniv.assignmentSubmission.dao.AssignmentSubmissionMapper;
import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.SerchMappingVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {
	private final AssignmentSubmissionMapper mapper;
	private final AtchFileService atchFileService;
	private final AssignmentService assignmentService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	@Override
	public void createAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission) {
		//과제 제출처리 ==> assignmentSubmission테이블에 추가처리
		
		if(assignmentSubmission.getLectNo()!=null) {
			Integer atchFileId = Optional.ofNullable(assignmentSubmission.getAtchFile())
					.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
					.map(af -> {
						atchFileService.createAtchFile(af, saveFolder);
						return af.getAtchFileId();
					}).orElse(null);
			
			LectureVO lecture = assignmentService.checkLecture(assignmentSubmission.getLectNo());
			
			assignmentSubmission.setLectNm(lecture.getLectNm());
			assignmentSubmission.setAtchFileId(atchFileId);
			
			int res = mapper.insertAssignmentSubmission(assignmentSubmission);
			
			if(res>0 && readAssignmentSubmission(assignmentSubmission).getAssignment().getPeerYn().equalsIgnoreCase("Y")) {
				insertPeerTarget(assignmentSubmission);
			}
			
		}
		else {
			throw new PKNotFoundException("해당강의번호는 존재하지않습니다.");
		}
	}
	//피어리뷰대상 업데이트 로직
	private void insertPeerTarget(AssignmentSubmissionVO assignmentSubmission) {
		// 1. 해당 과제 수강생 중 제출과제 피어리뷰대상에 없는사람 리스트조회
		List<AttendeeVO> peerList = mapper.attendListNotPeer(assignmentSubmission);
		
		// 남아있는 피어리뷰대상자가 없는경우
		if(peerList.isEmpty()||peerList.size()<=0) {
			throw new PKNotFoundException("남아있는 피어리뷰가능 대상자가 없습니다. 관리자에게 문의해주세요!");
		}
		
		String myId = assignmentSubmission.getStuId();
		// 2. 해당 리스트 중 랜덤으로 한명의 학번 assignmentSubmission.setPeerId("?"); 처리 단, 자기자신 본인은 제외
		List<AttendeeVO> filteredPeerList = peerList.stream().filter(attendee->!attendee.getStuId().equals(assignmentSubmission.getStuId()))
							.collect(Collectors.toList());
		//본인제외하니 피어리뷰대상자가 없는경우
		if(filteredPeerList.isEmpty()||filteredPeerList.size()<=0) {
			throw new PKNotFoundException("본인을 제외하면, 남아있는 피어리뷰가능 대상자가 없습니다. 관리자에게 문의해주세요!");
		}
		Random random = new Random();
		AttendeeVO peerTarget = filteredPeerList.get(random.nextInt(filteredPeerList.size()));
		// 3. assignment modify처리.
		assignmentSubmission.setPeerId(peerTarget.getStuId());
		
		int res = mapper.updateAssignmentSubmission(assignmentSubmission);
		if(res<=0) {
			throw new BoardException("피어리뷰 대상 업데이트 실패!");
		}
		
	}

	@Override
	public AssignmentSubmissionVO readAssignmentSubmission(AssignmentSubmissionVO submission) {
		
		AssignmentSubmissionVO assignmentSubmission = mapper.selectAssignmentSubmission(submission);
		
		assignmentSubmission.setLectNm(assignmentService.checkLecture(submission.getLectNo()).getLectNm());
		
		return assignmentSubmission;
	}
	//페이징처리와 검색처리를 하지않은 이유는 data-table 사용예정  
	@Override
	public List<AssignmentSubmissionVO> readAssignmentSubmissionlist(AssignmentSubmissionVO submission) {
		
		return mapper.selectAssignmentSubmissionList(submission);
	}

	@Override//과제수정
	public void modifyAssignmentSubmission(final AssignmentSubmissionVO assignmentSubmission) {
		
		final AssignmentSubmissionVO saved = readAssignmentSubmission(assignmentSubmission);
		
		Integer newAtchFileId = Optional.ofNullable(assignmentSubmission.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		assignmentSubmission.setAtchFileId(newAtchFileId);
		
		mapper.updateAssignmentSubmission(assignmentSubmission);
	}
	@Override
	public void removeAssignmentSubmission(AssignmentSubmissionVO submission) {
		AssignmentSubmissionVO assignmentSubmission = mapper.selectAssignmentSubmission(submission);
		
		Optional.ofNullable(assignmentSubmission.getAtchFileId())
		.ifPresent(fid -> atchFileService.disableAtchFile(fid));
		
		mapper.deleteAssignmentSubmission(submission);
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
	
	public int createGrade(AssignmentSubmissionVO assignmentSubmission) {
		
		return mapper.gradeScore(assignmentSubmission);
	}

	//@Transactional 트랜잭션필수
	@Override
	public int updatePeerScr(AssignmentSubmissionVO assignmentSubmission) {
		//받아온 assignmentSubmission에는 점수가 들어있다.
		AssignmentSubmissionVO target = assignmentSubmission;
		
		assignmentSubmission = mapper.selectAssignmentSubmission(assignmentSubmission);
		
		String peerTarget = assignmentSubmission.getPeerId();
		
		assignmentSubmission.setPeerYn("Y");
		int result = mapper.updatePeerStatus(assignmentSubmission);
		
		if(result>=0) {
			//피어리뷰제출여부가 변경이 되면, 해당 타겟에게 점수입력.
			target.setStuId(peerTarget);
			int res = mapper.updatePeerScr(target);
			if(res>=0) {
				return res;
			}
			else {
				throw new BoardException("피어리뷰점수입력 실패!!");
			}
		}
		else {
			throw new BoardException("피어리뷰제출여부 변경실패!");
		}
		
	}
	
	
	
}
