package kr.or.ddit.yguniv.projectchatroom.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import kr.or.ddit.yguniv.commons.exception.PKDuplicatedException;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectchatroom.service.ProjectChatRoomService;
import kr.or.ddit.yguniv.projectteam.service.ProjectTeamService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectChatRoom")
public class ProjectChatRoomController {
	@Autowired
	private ProjectTeamService projectTeamService;
	
	@Autowired
	private ProjectMemberService projectMemberService;
	
	@Autowired
	private ProjectChatRoomService service;
	
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
	
	//팀채팅방 목록조회
	@GetMapping("{taskNo}")
	public String selectList(
			@PathVariable String taskNo
			,Model model
			,Principal user
			) {
		if(StringUtils.isEmpty(taskNo)) {
			throw new PKNotFoundException("필수값 누락: 조별과제번호");
		}
		ProjectMemberVO projectMember = new ProjectMemberVO();
		
        projectMember.setStuId(user.getName());
        
        projectMember = projectMemberService.readProjectMember(projectMember);
        
        model.addAttribute("projectMember",projectMember);
        
        model.addAttribute("taskNo", taskNo);
		model.addAttribute("projectChatRoomList", service.readProjectChatRoomList(taskNo));
		
		return "lecture/materials/projectChatRoom/projectChatRoomList";
	}
	
	//팀채팅방입장
	@GetMapping("{taskNo}/{teamCd}")
	public String select(
			@PathVariable String taskNo
			,@PathVariable String teamCd
			,Model model
			) {
		ProjectTeamVO projectTeam = projectTeamService.readProjectTeam(teamCd);
		
		
		model.addAttribute("taskNo", taskNo);
		model.addAttribute("teamCd", teamCd);
		model.addAttribute("projectTeam", projectTeam);
		
		return "lecture/materials/projectChatRoom/projectChatRoom";
	}
	
	//팀채팅방 생성
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(
			@RequestBody ProjectChatRoomVO projectChatRoom
			) {
		//채팅방은 팀당 1개만 만들 수 있게!
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			service.createProjectChatRoom(projectChatRoom);
			body.put("message","정상적으로 채팅방이 개설되었습니다!");
		}catch(BoardException e ) {
			//동일한 채팅방 명
			status = HttpStatus.BAD_REQUEST;
			body.put("message",e.getMessage());
		}catch(PKDuplicatedException e) {
			//기존 채팅방 존재
			status = HttpStatus.BAD_REQUEST;
			body.put("message",e.getMessage());
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//팀채팅방 수정폼이동
	@GetMapping("{projectChatRoomNo}/edit")
	public String updateForm(@PathVariable()int projectChatRoomNo) {
		
		
		return "lecture/materials/projectChatRoom/projectChatRoomEditForm";
	}
	
	//팀채팅방 수정
	@PutMapping("{projectChatRoomNo}")
	public String update(@PathVariable()int projectChatRoomNo) {
		
		
		return "lecture/materials/projectChatRoom/?";
	}
	
	//팀채팅방 삭제
	@DeleteMapping("{projectChatRoomNo}")
	public String delete(@PathVariable()int projectChatRoomNo) {
		
		
		return "lecture/materials/projectChatRoom/projectChatRoomList";
	}
		
}
