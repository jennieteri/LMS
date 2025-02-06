package kr.or.ddit.yguniv.projectteam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectTask.service.ProjectTaskService;
import kr.or.ddit.yguniv.projectteam.service.ProjectTeamService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectDutyVO;
import kr.or.ddit.yguniv.vo.ProjectTeamFormVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectTeam")
public class ProjectTeamController {
	@Autowired
	private AttendeeMapper attendeeMapper;
	
	@Autowired
	private ProjectTeamService service;
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
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
	
	//프로젝트팀 목록조회
	@GetMapping
	public String selectList(
			@PathVariable String lectNo
			,Model model
			) {
		model.addAttribute("attendeeCount", attendeeMapper.countAttendee(lectNo));
		
		model.addAttribute("projectTaskList", projectTaskService.readProjectTaskList(lectNo));
		
		return "lecture/materials/projectTeam/projectTeamList";
	}
	//프로젝트 생성폼
	@GetMapping("getForm")
	@ResponseBody
	public Map<String, Object> getAutoForm(
			@PathVariable String lectNo
			) {
		Map<String, Object> resp = new HashMap<>();
		resp.put("attendeeCount", attendeeMapper.countAttendee(lectNo));
		
		return resp;
	}
	@PostMapping("createTeam")
	@ResponseBody
	public ResponseEntity<Object> createTeamAuto(
			@RequestBody ProjectTeamFormVO teamForm
			){
		if(teamForm.getTaskNo()==null||teamForm.getTaskNo().isEmpty()) {
			throw new PKNotFoundException("과제를 선택하지않았습니다.");
		}
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			service.createProjectTeam(teamForm);
			body.put("message","팀생성 성공!");
		}catch(RuntimeException e){
			status= HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", "서버오류");
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	//프로젝트팀 목록조회
	@GetMapping("{taskNo}")
	@ResponseBody
	public List<ProjectTeamVO> selectListWithTaskNo(
			@PathVariable String taskNo
			,Model model
			) {
		if(taskNo==null||taskNo.isEmpty()) {
			throw new PKNotFoundException("과제를 선택하지않았습니다.");
		}
		
		return service.readProjectTeamList(taskNo);
	}
	//프로젝트팀 상세조회
	@GetMapping("detail/{teamCd}")
	@ResponseBody
	public ProjectTeamVO select(@PathVariable()String teamCd) {
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("팀번호 미입력!");
		}
		
		return service.readProjectTeam(teamCd);
	}
	//프로젝트팀 수정폼
	@GetMapping("edit/{teamCd}")
	@ResponseBody
	public Map<String, Object> getEditForm(
			@PathVariable String lectNo
			,@PathVariable String teamCd
			) {
		
		if(lectNo==null||lectNo.isEmpty()) {
			throw new PKNotFoundException("강의번호 미입력! 비정상접근");
		}
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("팀번호 미입력!");
		}
		Map<String, Object> resp = new HashMap<>();
		resp.put("projectTeam", service.readProjectTeam(teamCd));
		resp.put("projectTaskList",projectTaskService.readProjectTaskList(lectNo));
		
		
		return resp;
	}
	//프로젝트팀 수정
	@PutMapping("{teamCd}")
	@ResponseBody
	public ResponseEntity<Object> update(
			@PathVariable String teamCd
			,@RequestBody ProjectTeamVO projectTeam
			) {
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("팀번호 미입력!");
		}
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			service.modifyProjectTeam(projectTeam);
			body.put("message","팀수정 성공!");
		}catch(RuntimeException e){
			status= HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", "서버오류");
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//프로젝트팀 삭제
	@DeleteMapping("{teamCd}")
	@ResponseBody
	public ResponseEntity<Object> delete(@PathVariable()String teamCd) {
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("팀번호 미입력!");
		}
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		try {
			int res =service.removeProjectTeam(teamCd);
			if(res>0) {
				body.put("message","팀삭제 성공!");
			}else {
				status= HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message","팀삭제 실패!");
			}
		}catch(BoardException e){
			status= HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", "서버오류");
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	@GetMapping("getChart/{teamCd}")
	@ResponseBody
	public ResponseEntity<Object> getDutyChart(
			@PathVariable String teamCd
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("해당팀은 없습니다!");
		}
		
		ProjectTeamVO projectTeam = service.readProjectTeam(teamCd);
		body.put("projectTeam", projectTeam);
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	/*
	@PostMapping("previewAutoAssign")
	@ResponseBody
	public ResponseEntity<Object> getPreview(
			@RequestBody ProjectTeamFormVO projectTeamForm
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(projectTeamForm==null) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message", "팀배정 실패: 잘못된 요청값!");
		}
		
		try {
			service.autoAllot(projectTeamForm);
			body.put("message","팀배정성공!");
		}catch(RuntimeException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message","팀배정 실패: 서버로직오류!");
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	*/
	
	
	
}
