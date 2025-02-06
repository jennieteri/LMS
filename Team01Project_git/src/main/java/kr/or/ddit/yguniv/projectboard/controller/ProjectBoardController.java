package kr.or.ddit.yguniv.projectboard.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectboard.service.ProjectBoardService;
import kr.or.ddit.yguniv.projectteam.service.ProjectTeamService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectBoard")
public class ProjectBoardController {
	@Autowired
	private ProjectTeamService projectTeamService;
	@Autowired
	private ProjectMemberService projectMemberService;
	
	@Autowired
	private ProjectBoardService service;	
	
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
	//게시글 목록조회
	@GetMapping("{teamCd}")
	public String getProjectBoardList(
			@RequestParam(required = false, defaultValue = "1") int page
			,@ModelAttribute("condition") SimpleCondition simpleCondition
			,@PathVariable String teamCd
			,Principal user
			,Model model
			) {
		PaginationInfo<ProjectBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		
		List<ProjectBoardVO> projectBoardList = service.readProjectBoardList(paging, teamCd);
		ProjectMemberVO projectMember = new ProjectMemberVO();
		projectMember.setStuId(user.getName());
		
		projectMember = projectMemberService.readProjectMember(projectMember);
		
		model.addAttribute("projectMember",projectMember);
		model.addAttribute("teamCd", teamCd);
		model.addAttribute("projectBoardList", projectBoardList);
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "lecture/materials/projectPersonal/projectBoardList";
	}
	//상세조회
	@GetMapping("{teamCd}/{pbNo}")
	public String getDetail(
			@PathVariable String teamCd
			,@PathVariable int pbNo
			,Model model
			) {
		
		ProjectBoardVO projectBoard = service.readProjectBoard(pbNo);
		ProjectTeamVO projectTeam = projectTeamService.readProjectTeam(teamCd);
		
		
		model.addAttribute("taskNo", projectTeam.getTaskNo());
		model.addAttribute("teamCd", teamCd);
		model.addAttribute("projectBoard", projectBoard);
		
		return "lecture/materials/projectPersonal/projectBoardDetail";
	}
	
	
	@GetMapping("new/{teamCd}")
	public String getBoardForm(
			@PathVariable String teamCd
			,Model model
			) {
		if(!model.containsAttribute("projectBoard")) {
			ProjectBoardVO projectBoard = new ProjectBoardVO();
			model.addAttribute("projectBoard", projectBoard);
		}
		model.addAttribute("teamCd", teamCd);
		
		
		return "lecture/materials/projectPersonal/projectBoardForm";
	}
	
	
	@GetMapping("edit/{teamCd}/{pbNo}")
	public String getBoardEditForm(
			@PathVariable String teamCd
			,@PathVariable int pbNo
			,Model model
			) {
		
		ProjectBoardVO projectBoard = service.readProjectBoard(pbNo);
		
		
		model.addAttribute("projectBoard", projectBoard);
		model.addAttribute("teamCd", teamCd);
		
		return "lecture/materials/projectPersonal/projectBoardEditForm";
	}
	
	//게시글생성처리
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> createBoard(
			@Validated ProjectBoardVO projectBoard
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			service.createProjectBoard(projectBoard);
			
			body.put("success", "success");
			body.put("message", "게시글이 작성되었습니다.");
			body.put("targetPbNo",projectBoard.getPbNo());
			
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
					.map(FieldError :: getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//게시글 수정처리
	@PostMapping("edit")
	@ResponseBody
	public ResponseEntity<Object> modifyBoard(
			@Validated ProjectBoardVO projectBoard
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			service.modifyProjectBoard(projectBoard);
			
			body.put("success", "success");
			body.put("message", "게시글이 수정되었습니다.");
			body.put("targetPbNo",projectBoard.getPbNo());
			
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("delete")
	@ResponseBody
	public ResponseEntity<Object> deleteBoard(
			@Validated ProjectBoardVO projectBoard
			,BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(!error.hasErrors()) {
			service.removeProjectBoard(projectBoard.getPbNo());
			
			body.put("success", "success");
			body.put("message", "게시글이 삭제되었습니다.");
			body.put("targetTeamCd",projectBoard.getTeamCd());
			
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
