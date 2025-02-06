package kr.or.ddit.yguniv.projectTaskSubmission.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectTaskSubmission.service.ProjectTaskSubmissionService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTaskSubmissionVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectTaskSubmission")
public class ProjectTaskSubmissionController {
	
	@Autowired
	private ProjectMemberService projectMemberService;
	
	@Autowired
	private ProjectTaskSubmissionService service;
	
	@Autowired
	private LectureMaterialsServiceImpl lectureservice;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model){
		LectureVO lectureVO = lectureservice.selectLecture(lectNo);
		if(lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	
	//제출프로젝트과제 조회
	@GetMapping
	public String getList(
			@PathVariable String lectNo
			, Model model) {
		
		model.addAttribute("projectTaskSubmissionList", service.readProjectTaskSubmissionList(lectNo));
		
		return "lecture/materials/projectTaskSubmission/projectTaskSubmission";
	}
	//프로젝트과제 제출폼 이동
	@GetMapping("{teamCd}")
	public String getSubmissionForm(
			@PathVariable String teamCd
			,Model model
			,Principal user
			) {
		ProjectMemberVO projectMember = new ProjectMemberVO();
		projectMember.setStuId(user.getName());
		
		
		projectMember = projectMemberService.readProjectMember(projectMember);
		//lectno랑 projectmember보내야함
		List<ProjectMemberVO> projectMemberList = projectMemberService.readProjectMemberList(teamCd);
		
		//제출한과제가 있다면 제출현황을 담아 보낸다
		boolean submitted = false;
		ProjectTaskSubmissionVO projectTaskSubmission = service.checkSubmit(teamCd);
		if(projectTaskSubmission!=null) {
			submitted = true ;
			model.addAttribute("projectTaskSubmission", projectTaskSubmission);
		}
		
		//본인 팀 제출현황 보내기
		model.addAttribute("submitted",submitted);
		//본인정보 보내기
		model.addAttribute("projectMember", projectMember);
		//멤버 목록 보내기
		model.addAttribute("projectMemberList", projectMemberList);
		
		return "lecture/materials/projectPersonal/projectSubmission";
	}
	
	//프로젝트과제 제출처리
	@PostMapping("task")
	@ResponseBody
	public ResponseEntity<Object> submitTask(
			@Validated ProjectTaskSubmissionVO projectTaskSubmission
			,BindingResult error
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		//프로젝트과제번호, 팀번호, 첨부파일
		
		if(!error.hasErrors()) {
			service.createProjectTaskSubmission(projectTaskSubmission);
			body.put("message", "프로젝트 과제가 성공적으로 제출되었습니다.");
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//프로젝트과제 상호평가 제출처리
	@PostMapping("peer")
	@ResponseBody
	public ResponseEntity<Object> submitPeer(
			@Validated ProjectTaskSubmissionVO projectTaskSubmission
			,BindingResult error
			,Principal user
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		//프로젝트과제제출번호, 첨부파일
		
		if(!error.hasErrors()) {
			int res= service.peerSubmit(projectTaskSubmission,user.getName());
			body.put("message", "상호평가가 성공적으로 제출되었습니다.");
			
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("grade")
	@ResponseBody
	public ResponseEntity<Object> insertGrade(
			@Validated @RequestBody ProjectTaskSubmissionVO projectTaskSubmission
			,BindingResult error
			,Principal user
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			int res= service.updateGradeProjectTaskSubmission(projectTaskSubmission);
			body.put("message", "점수가 성공적으로 입력되었습니다.");
			
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	
}
