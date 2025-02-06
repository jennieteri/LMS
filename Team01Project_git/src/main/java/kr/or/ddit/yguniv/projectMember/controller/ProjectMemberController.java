package kr.or.ddit.yguniv.projectMember.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectTask.service.ProjectTaskService;
import kr.or.ddit.yguniv.projectteam.service.ProjectTeamService;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTeamFormVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectMember")
public class ProjectMemberController {
	@Autowired
	private AttendeeMapper attendeeMapper;
	@Autowired
	private ProjectTeamService teamService;
	@Autowired
	private ProjectMemberService memberService;
	
	@Autowired
	private ProjectTaskService taskService;
	
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
	
	//팀원관리화면으로 이동
	//필요데이터 프로젝트과제리스트, 수강생리스트, 수강생 총인원 수
	//팀리스트는 과제 선택 후 비동기로 반영
	@GetMapping
	public String getMember(
			@PathVariable String lectNo
			,Model model
			) {
		model.addAttribute("projectTaskList", taskService.readProjectTaskList(lectNo));
		model.addAttribute("attendeeList", attendeeMapper.selectAttendeeForProject(lectNo));
		model.addAttribute("attendeeCount", attendeeMapper.countAttendee(lectNo));
		
		return  "lecture/materials/projectMember/projectMember";
	}
	
	//과제 선택시 팀리스트 응답
	@GetMapping("getTeams/{taskNo}")
	@ResponseBody
	public List<ProjectTeamVO> getTeams(
			@PathVariable String lectNo
			,@PathVariable String taskNo
			) {
		if(taskNo==null||taskNo.isEmpty()) {
			throw new PKNotFoundException("오류: 과제 미입력!");
		}
		
		return teamService.readProjectTeamList(taskNo);
	}
	
	@GetMapping("getMemberAndAttendee/{teamCd}/{taskNo}")
	@ResponseBody
	public Map<String, Object> getMemberAndAttendee(
			@PathVariable String lectNo
			,@PathVariable String taskNo
			,@PathVariable String teamCd
			){
		Map<String, Object> resp = new HashMap<>();
		if(lectNo==null||teamCd==null||lectNo.isEmpty()||teamCd.isEmpty()) {
			throw new PKNotFoundException("비정상접근: 필수값 누락");
		}
		
		//팀에 속하지않은 수강생리스트
		List<AttendeeVO> noTeamAttendeeList = attendeeMapper.selectAttendeeForProjectNoTeam(lectNo,taskNo);
		//팀원 리스트
		List<ProjectMemberVO> projectMemberList = memberService.readProjectMemberList(teamCd);
		
		resp.put("noTeamAttendeeList", noTeamAttendeeList);
		resp.put("projectMemberList", projectMemberList);
		
		return resp;
	}
	
	//drag&drop 멤버 인서트처리
	@PostMapping("updateMember")
	public ResponseEntity<Object> addProjectMember(
			@PathVariable String lectNo
			,@Validated @RequestBody ProjectMemberVO projectMember
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			projectMember.setLectNo(lectNo);
			memberService.createProjectMember(projectMember);
		}
		else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//drag&drop 멤버 삭제처리
	@PostMapping("removeMember")
	public ResponseEntity<Object> removeProjectMember(
			@PathVariable String lectNo
			,@Validated @RequestBody ProjectMemberVO projectMember
			,BindingResult error
			){
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			projectMember.setLectNo(lectNo);
			memberService.removeProjectMember(projectMember);
		}
		else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	@GetMapping("getCount/{taskNo}")
	@ResponseBody
	public Map<String, Object> getCount(
			@PathVariable String lectNo
			,@PathVariable String taskNo
			){
		Map<String, Object> resp = new HashMap<>();
		if(taskNo==null||taskNo.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 프로젝트과제번호 미입력");
		}
		List<AttendeeVO> noTeamAttendeeList = attendeeMapper.selectAttendeeForProjectNoTeam(lectNo,taskNo);
		
		resp.put("noTeamAttendeeCount",noTeamAttendeeList.size());
		resp.put("teamCount", teamService.countProjectTeamNoMember(taskNo));
		
		return resp;
	}
	
	@PostMapping("justUpdate")
	public ResponseEntity<Object> setLeader(
			@PathVariable String lectNo
			,@Validated @RequestBody ProjectMemberVO projectMember
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			projectMember.setLectNo(lectNo);
			memberService.updateLeadYn(projectMember);
		}
		else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("getPreview")
	@ResponseBody
	public ResponseEntity<Object> getPreview(
			@Validated @RequestBody ProjectTeamFormVO projectTeamForm
			,BindingResult error
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			try {
				List<ProjectTeamVO> projectTeamList = teamService.autoAllot(projectTeamForm);
				body.put("projectTeamList",projectTeamList);
				body.put("message", "자동배정완료!");
			}catch(BoardException e) {
				 status = HttpStatus.BAD_REQUEST;
		         body.put("message", e.getMessage());
			}
		}
		else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("reset")
	@ResponseBody
	public ResponseEntity<Object> resetMember(
			@RequestBody ProjectMemberVO projectMember
			){
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		String taskNo = projectMember.getTaskNo();
		if(taskNo==null||taskNo.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message", "필수값 누락!");
		}
		else {
			try {
				teamService.resetTeamMember(taskNo);
				body.put("message", "팀초기화완료!");
			}catch(BoardException e) {
				 status = HttpStatus.BAD_REQUEST;
		         body.put("message", e.getMessage());
			}
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
}
