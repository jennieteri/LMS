package kr.or.ddit.yguniv.assignment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.assignment.service.AssignmentService;
import kr.or.ddit.yguniv.assignmentSubmission.service.AssignmentSubmissionService;
import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.enumpkg.NotificationCode;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.utils.AttendeeIdListMaker;
import kr.or.ddit.yguniv.validate.DeleteGroup;
import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;


/**
 * @author AYS
 * lvn에 ?는 디테일로 갈 수 있게 해당넘버를 붙여줘야함
 */
@Controller
@RequestMapping("/lecture/{lectNo}/assignment")
public class AssignmentController {
	
	public static final String MODELNAME = "assignment";
	
	@Autowired
	private AttendeeMapper attendeeMapper;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private AssignmentSubmissionService assignmentSubmissionService;
	
	@Autowired
	private AssignmentService service;
	
	@Autowired
	private LectureMaterialsServiceImpl lectureservice;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model){
		LectureVO lectureVO = lectureservice.selectLecture(lectNo);
		if(lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
		model.addAttribute("lectNo", lectNo);
	}
	
	//과제전체조회(검색어 +페이징)
	@GetMapping
	public String selectList(
			@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			,@PathVariable String lectNo
			, Model model
			) {
		
		PaginationInfo<AssignmentVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("assignmentList", service.readAssignmentListPaging(paging,lectNo));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "lecture/materials/assignment/assignmentList";
	}
	//과제 생성폼이동
	@GetMapping("new")
	public String createForm(Model model) {
		String lectNo = (String)model.getAttribute("lectNo");
		if(!model.containsAttribute(MODELNAME)) {
			AssignmentVO assignment = new AssignmentVO();
			assignment.setLectNo(lectNo);
			assignment.setLectNm(service.checkLecture(lectNo).getLectNm());
			model.addAttribute(MODELNAME, assignment);
		}
		
		return "lecture/materials/assignment/assignmentForm";
	}

	//과제 생성
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(
			@Validated AssignmentVO assignment
			, BindingResult errors
			,Principal user
			,@PathVariable String lectNo
			) {
		AttendeeIdListMaker maker = new AttendeeIdListMaker();
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			if (!errors.hasErrors()) {
				service.createAssignment(assignment);;
				body.put("message","과제가 정상적으로 생성되었습니다!");
				//알림처리
				List<AttendeeVO> attendeeList = attendeeMapper.selectAttendeeListWithLecture(lectNo);
				
				List<String> attendeeIdList = maker.listMaker(attendeeList);
				
				String targetUrl = "lecture/"+lectNo+"/assignment"+assignment.getAssigNo();
				
				notiService.createAndSendNotification(user.getName()
						, attendeeIdList, "과제가 등록되었습니다!", NotificationCode.INFO.toString(), targetUrl, "과제등록알림!");
			} else {
				body.put("message","부적합한 입력값!");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
			
		}catch (Exception e) {
			body.put("message","게시글 생성 중 오류 발생");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//과제상세조회
	@GetMapping("{assigNo}")
	public String select(
			@PathVariable()String assigNo
			,@PathVariable() String lectNo
			,Principal user
			, Model model) {
		AssignmentVO assignment = service.readAssignment(assigNo);
		//제출여부확인처리필요
		boolean submissionYn = service.checkSubmission(assigNo, user.getName());
		//제출했다면 본인이 제출한 과제 보이게
		if(submissionYn) {
			
			AssignmentSubmissionVO assignmentSubmission = new AssignmentSubmissionVO();
			
			assignmentSubmission.setStuId(user.getName());
			assignmentSubmission.setLectNo(lectNo);
			assignmentSubmission.setAssigNo(assigNo);
			
			assignmentSubmission = assignmentSubmissionService.readAssignmentSubmission(assignmentSubmission);
			
			model.addAttribute("submission", assignmentSubmission);
		}
		
		model.addAttribute("submissionYn",submissionYn);
		model.addAttribute("lectNo", lectNo);
		model.addAttribute(MODELNAME,assignment);
		
		
		return "lecture/materials/assignment/assignmentDetail";
	}
	
	//과제수정폼이동
	@GetMapping("{assigNo}/edit")
	public String updateForm(Model model, @PathVariable()String assigNo) {
		model.addAttribute(MODELNAME, service.readAssignment(assigNo));
		return "lecture/materials/assignment/assignmentEditForm";
	}
	
	
	//과제수정--TODO
	@PostMapping("edit")
	@ResponseBody
	public ResponseEntity<Object> edit(
			@Validated AssignmentVO assignment
			,BindingResult errors
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			if (!errors.hasErrors()) {
				service.modifyAssignment(assignment);
				body.put("message","과제가 정상적으로 수정되었습니다!");
				
			} else {
				body.put("message","부적합한 입력값!");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
			
		}catch (Exception e) {
			body.put("message","과제 수정 중 오류 발생");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//과제삭제
	@PostMapping("delete")
	@ResponseBody
	public ResponseEntity<Object> delete(
			@RequestBody @Validated(DeleteGroup.class) AssignmentVO assignment
			,BindingResult errors
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			if (!errors.hasErrors()) {
				service.removeAssignment(assignment.getAssigNo());
				body.put("message","과제가 정상적으로 삭제되었습니다!");
				
			} else {
				body.put("message","부적합한 입력값!");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
			
		}catch (Exception e) {
			body.put("message","과제 삭제 중 오류 발생");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
}
