package kr.or.ddit.yguniv.projectteam.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectboard.service.ProjectBoardService;
import kr.or.ddit.yguniv.projectteam.service.ProjectPersonalService;
import kr.or.ddit.yguniv.projectteam.service.ProjectTeamService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectPersonal")
public class ProjectPersonalController {
	
	@Autowired
	private ProjectBoardService projectBoardService;
	
	@Autowired
	private ProjectTeamService projectTeamService;
	
	@Autowired
	private ProjectPersonalService service;
	
	@Autowired
	private ProjectMemberService projectMemberService;
	
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
	
	@GetMapping
	public String getMain(
			@PathVariable String lectNo
			,Principal user
			,Model model
			) {
		String id = user.getName();
		if(id==null||id.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		ProjectMemberVO input = new ProjectMemberVO();
		input.setStuId(id);
		input.setLectNo(lectNo);
		
		ProjectMemberVO projectMember = service.selectProjectTeamWithId(input);
		if(projectMember==null) {
			model.addAttribute("message","현재 진행중인 프로젝트가 없습니다!");
			return "lecture/materials/projectPersonal/projectNot";
		}
		
		List<ProjectMemberVO> projectMemberList = projectMemberService.readProjectMemberList(projectMember.getTeamCd());
		ProjectBoardVO projectBoard = projectBoardService.readNoti(projectMember.getTeamCd());
		
		model.addAttribute("projectBoard",projectBoard);
		model.addAttribute("projectMember", projectMember);
		model.addAttribute("projectMemberList", projectMemberList);
		
		
		
		return "lecture/materials/projectPersonal/projectPersonal";
	}
	
	@PostMapping("updateProg")
	@ResponseBody
	public ResponseEntity<Object> updateProgress(
			@RequestBody ProjectTeamVO projectTeam
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			projectTeamService.updateProge(projectTeam);
			body.put("message","진척도 업데이트성공!");
		}catch(RuntimeException e){
			status= HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", "서버오류");
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("updateTeamNm")
	@ResponseBody
	public ResponseEntity<Object> updateTeamNm(
			@RequestBody ProjectTeamVO projectTeam
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			int res = projectTeamService.updateTeamNm(projectTeam);
			if(res>0) {
				body.put("message","팀명 수정성공!");
			}
			else {
				body.put("message","팀명 변경실패!");
			}
		}catch(RuntimeException e){
			status= HttpStatus.INTERNAL_SERVER_ERROR;
			body.put("message", "서버오류");
		}
		
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	
}
