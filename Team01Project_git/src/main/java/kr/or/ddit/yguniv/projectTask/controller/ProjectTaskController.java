package kr.or.ddit.yguniv.projectTask.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.projectTask.service.ProjectTaskService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectTaskVO;

/**
 * @author AYS
 * lvn에 ?는 디테일로 갈 수 있게 해당넘버를 붙여줘야함
 */
@Controller
@RequestMapping("/lecture/{lectNo}/projectTask")
public class ProjectTaskController {
	@Autowired
	private AttendeeMapper attendeeMapper;
	
	@Autowired
	private ProjectTaskService service;
	
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
	
	//프로젝트 목록조회
	@GetMapping
	public String selectList(
			@PathVariable String lectNo
			, Model model
			) {
		model.addAttribute("projectTaskList", service.readProjectTaskList(lectNo));
		model.addAttribute("attendeeCount", attendeeMapper.countAttendee(lectNo));
		
		return "lecture/materials/projectTask/projectTaskList";
	}
	
	//프로젝트 상세조회
	@GetMapping("{taskNo}")
	@ResponseBody
	public ProjectTaskVO select(@PathVariable()String taskNo) {
		
		ProjectTaskVO projectTask = service.readProjectTask(taskNo);
		
		return projectTask;
	}
	//프로젝트 생성폼 이동
	@GetMapping("new")
	public String createForm(@PathVariable String lectNo, Model model) {
		if(!model.containsAttribute("projectTask")) {
			ProjectTaskVO projectTask = new ProjectTaskVO();
			model.addAttribute("projectTask", projectTask);
		}
		
		return "lecture/materials/projectTask/projectTaskForm";
	}
	//프로젝트 생성
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(@PathVariable()String lectNo
			,@Validated ProjectTaskVO projectTask
			,BindingResult error) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		projectTask.setLectNo(lectNo);
		projectTask.setTaskStatus("Y");
		
		try {
			service.createProjectTask(projectTask);
			body.put("message", "프로젝트생성완료!");
		}catch(RuntimeException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", e.getMessage());
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//프로젝트 수정
	@PostMapping("edit/{taskNo}")
	@ResponseBody
	public ResponseEntity<Object> update(
			@PathVariable String lectNo
			,@PathVariable String taskNo
			,@Validated ProjectTaskVO projectTask
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			service.modifyProjectTask(projectTask);
			body.put("message", "프로젝트 수정완료!");
			
		}catch(RuntimeException e){
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", e.getMessage());
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	//프로젝트 삭제
	@DeleteMapping("{taskNo}")
	public ResponseEntity<Object> delete(
			@PathVariable String lectNo
			,@PathVariable String taskNo
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			service.removeProjectTask(taskNo);
			body.put("message", "프로젝트 삭제완료!");
			
		}catch(RuntimeException e){
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", e.getMessage());
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	
	
}
