package kr.or.ddit.yguniv.assignmentSubmission.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.yguniv.assignment.service.AssignmentService;
import kr.or.ddit.yguniv.assignmentSubmission.service.AssignmentSubmissionService;
import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;
import kr.or.ddit.yguniv.vo.SerchMappingVO;

/**
 * @author AYS
 * 과제제출처리 리팩토링 필요
 * 제출처리 ==> insert 처리
 */
@Controller
@RequestMapping("/lecture/{lectNo}/assignmentSubmission")
public class AssignmentSubmissionController {
	
	@Autowired
	private AttendeeMapper attendeeMapper;
	
	@Autowired
	private AssignmentSubmissionService service;
	
	@Autowired
	private LectureMaterialsServiceImpl lectureservice;
	
	@Autowired
	private AssignmentService assignmentservice;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model){
		LectureVO lectureVO = lectureservice.selectLecture(lectNo);
		if(lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	@GetMapping
	public String getList(@PathVariable String lectNo, Model model) {
		
		List<AssignmentVO> assignmentList = assignmentservice.readAssignmentList(lectNo);
		
		//총수강생 담아보내기
		model.addAttribute("attendeeCount", attendeeMapper.countAttendee(lectNo));
		
		model.addAttribute("assignmentList", assignmentList);
		
		return "lecture/materials/assignmentSubmission/assignmentSubmission";
	}
	//과제상세조회
	@GetMapping("assign/{assigNo}")
	@ResponseBody
	public AssignmentVO detailAssignment(@PathVariable String assigNo) {
		AssignmentVO data = assignmentservice.readAssignment(assigNo);
		return data;
	}
	
	@GetMapping("getChart")
	@ResponseBody
	public ResponseEntity<Object> getSubmitChart(
			@PathVariable String lectNo
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(lectNo==null||lectNo.isEmpty()) {
			throw new PKNotFoundException("해당강의는 없습니다!");
		}
		
		List<AssignmentVO> assignmentList = assignmentservice.readAssignmentList(lectNo);
		
		body.put("assignmentList", assignmentList);
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	//과제 제출
	//리테스트완료
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(
			@Validated AssignmentSubmissionVO assignmentSubmission
			,BindingResult error
			) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			service.createAssignmentSubmission(assignmentSubmission);
			body.put("message", "과제가 성공적으로 제출되었습니다.");
			
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//제출과제상세조회 json
	//리테스트완료
	@GetMapping("detail/{assigNo}/{stuId}")
	@ResponseBody
	public AssignmentSubmissionVO select(
			AssignmentSubmissionVO submission
			) {
		
		AssignmentSubmissionVO data = service.readAssignmentSubmission(submission);
		
		return data;
	}
	
	//제출과제수정
	//리테스트완료
	@PostMapping("edit")
	@ResponseBody
	public ResponseEntity<Object> updateAssignmentSubmission(
			AssignmentSubmissionVO assignmentSubmission
			) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		assignmentSubmission = service.readAssignmentSubmission(assignmentSubmission);
		
		if(assignmentSubmission.getAssubPeerScr()>0) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message","피어리뷰가 작성된 과제는 수정 할 수 없습니다.");
			return ResponseEntity.status(status).body(body);
		}
		
		try {
			service.modifyAssignmentSubmission(assignmentSubmission);
			body.put("message", "제출과제수정완료!");
		}catch(RuntimeException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message","서버오류");
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//제출과제삭제(회수)
	//리테스트완료
	@PutMapping("{assigNo}")
	@ResponseBody
	public ResponseEntity<Object> update(
			@PathVariable()String lectNo
			,@PathVariable()String assigNo
			,@RequestBody AssignmentSubmissionVO assignmentSubmission
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		//피어리뷰가 작성된 과제는 회수 할 수 없게끔
		assignmentSubmission = service.readAssignmentSubmission(assignmentSubmission);
		
		if(assignmentSubmission.getAssubPeerScr()>0) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message","피어리뷰가 작성된 과제는 회수 할 수 없습니다.");
			return ResponseEntity.status(status).body(body);
		}
		
		
		try {
			service.removeAssignmentSubmission(assignmentSubmission);
			body.put("message", "제출과제회수완료!");
		}catch(RuntimeException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message","서버오류");
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//점수입력
	@PostMapping("grade")
	@ResponseBody
	public ResponseEntity<Object> gradeUpdate(
			@PathVariable String lectNo
			,@RequestBody AssignmentSubmissionVO assignmentSubmission
			){
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		int res = service.createGrade(assignmentSubmission);
		if(res<=0) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message","서버오류; 점수입력실패!");
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//피어리뷰작성모달
	//리테스트완료
	@GetMapping("peer/{assigNo}/{stuId}")
	@ResponseBody
	public AssignmentSubmissionVO getPeer(
			AssignmentSubmissionVO assignmentSubmission
			) {
		
		AssignmentSubmissionVO res = service.readAssignmentSubmission(assignmentSubmission);
		res.setStuId(res.getPeerId());
		//피어리뷰대상이 과제제출을 했는지 확인
		
		try {
			res = service.readAssignmentSubmission(res);
		}catch(BoardException e){
			res = new AssignmentSubmissionVO();
			res.setAssubYn("N");
		}
		
		return res;
	}
	//피어리뷰작성처리
	//리테스트완료
	@PostMapping("peer/{assigNo}/{stuId}")
	@ResponseBody
	public ResponseEntity<Object> peerScoreUpdate(
			@PathVariable String lectNo
		,	@RequestBody AssignmentSubmissionVO assignmentSubmission
			) {
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		assignmentSubmission.setLectNo(lectNo);
		
		int res = service.updatePeerScr(assignmentSubmission);
		if(res<=0) {
			//실패처리
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message","서버오류; 점수입력실패!");
		}else {
			body.put("message","피어리뷰 제출성공!");
		}
		
		return ResponseEntity.status(status).body(body);
	}

}
