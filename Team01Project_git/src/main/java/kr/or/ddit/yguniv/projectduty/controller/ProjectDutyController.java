package kr.or.ddit.yguniv.projectduty.controller;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectduty.service.ProjectDutyService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectDutyVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;

@Controller
@RequestMapping("/lecture/{lectNo}/projectDuty")
public class ProjectDutyController {
	
	@Autowired
	private ProjectDutyService service;
	
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
	
	//프로젝트일감일감 목록조회
	@GetMapping("{teamCd}")
	public String selectList(
			@RequestParam(required = false, defaultValue = "1") int page
			,@ModelAttribute("condition") SimpleCondition simpleCondition
			,@PathVariable String teamCd
			,Model model
			,Principal user
			) {
		//일감 리스트 반환
		ProjectMemberVO projectMember = new ProjectMemberVO();
		projectMember.setStuId(user.getName());
		PaginationInfo<ProjectDutyVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		
		projectMember = projectMemberService.readProjectMember(projectMember);
		List<ProjectDutyVO> projectDutyList = service.readProjectDutyList(paging, teamCd);
		
		model.addAttribute("projectMember", projectMember);
		model.addAttribute("projectDutyList", projectDutyList);
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "lecture/materials/projectPersonal/projectDutyList";
	}
	
	//프로젝트일감 상세조회
	@GetMapping("get/{dutyNo}")
	public String select(
			@PathVariable String dutyNo
			,Model model
			,Principal user
			) {
		if(dutyNo==null||dutyNo.isEmpty()) {
			throw new PKNotFoundException("필수값누락: 일감번호");
		}
		ProjectDutyVO projectDuty = service.readProjectDuty(dutyNo);
		ProjectMemberVO projectMember = new ProjectMemberVO();
		projectMember.setStuId(user.getName());
		projectMember = projectMemberService.readProjectMember(projectMember);
		
		model.addAttribute("projectMember", projectMember);
		model.addAttribute("projectDuty", projectDuty);
		
		return "lecture/materials/projectPersonal/projectDutyDetail";
	}
	//프로젝트일감 생성폼 이동
	@GetMapping("new/{teamCd}")
	public String createForm(
			@PathVariable String teamCd
			,Model model
			,Principal user
			) {
		if(!model.containsAttribute("projectDuty")) {
			ProjectDutyVO projectDuty = new ProjectDutyVO();
			model.addAttribute("projectDuty", projectDuty);
		}
		ProjectMemberVO projectMember = new ProjectMemberVO();
		projectMember.setStuId(user.getName());
		
		projectMember = projectMemberService.readProjectMember(projectMember);
		List<ProjectMemberVO> projectMemberList = projectMemberService.readProjectMemberList(teamCd);
		
		model.addAttribute("projectMemberList", projectMemberList);
		model.addAttribute("projectMember", projectMember);
		
		
		
		return "lecture/materials/projectPersonal/projectDutyForm";
	}
	
	
	//프로젝트일감 생성
	@PostMapping
	public String create(
			@PathVariable String lectNo
			,@Validated ProjectDutyVO projectDuty
			,BindingResult errors
			,RedirectAttributes redirectAttributes
			,Principal user
			) {
		
		try {
			String lvn = null;
			if (!errors.hasErrors()) {
				lvn = String.format("redirect:/lecture/%s/projectDuty/get/%s", lectNo, service.createProjectDuty(projectDuty) );
			} else {
				redirectAttributes.addFlashAttribute("projectDuty", projectDuty);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "projectDuty", errors);
				lvn = String.format("redirect:/lecture/%s/projectDuty/new/%s", lectNo,projectDuty.getDutyTeam());
			}
			return lvn;
		}catch (Throwable e) {
			throw new BoardException(e);
		}
		
	}
	
	//프로젝트일감 수정폼이동
	@GetMapping("edit/{dutyNo}")
	public String updateForm(
			@PathVariable String dutyNo
			,Model model
			) {
		ProjectDutyVO projectDuty = service.readProjectDuty(dutyNo);
		
		String teamCd = projectDuty.getDutyTeam();
		
		List<ProjectMemberVO> projectMemberList = projectMemberService.readProjectMemberList(teamCd);
		
		model.addAttribute("projectMemberList", projectMemberList);
		model.addAttribute("projectDuty", projectDuty);
		
		
		return "lecture/materials/projectPersonal/projectDutyEdit";
	}
	
	//프로젝트일감 수정
	@PostMapping("edit/{dutyNo}")
	public String update(
			@PathVariable String lectNo
			,@PathVariable String dutyNo
			,@Validated ProjectDutyVO projectDuty
			,BindingResult errors
			,RedirectAttributes redirectAttributes
			) {
		
		try {
			String lvn = null;
			if (!errors.hasErrors()) {
				service.modifyProjectDuty(projectDuty);
				lvn = String.format("redirect:/lecture/%s/projectDuty/get/%s", lectNo, projectDuty.getDutyNo() );
			} else {
				redirectAttributes.addFlashAttribute("projectDuty", projectDuty);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "projectDuty", errors);
				lvn = String.format("redirect:/lecture/%s/projectDuty/edit/%s", lectNo,projectDuty.getDutyNo() );
			}
			return lvn;
		}catch (Throwable e) {
			throw new BoardException(e);
		}
		
	}
	
	//프로젝트일감 삭제
	@PostMapping("delete/{dutyNo}")
	public String delete(
			@PathVariable String lectNo
			,@PathVariable String dutyNo
			,@RequestParam String dutyTeam
			) {
		
		if(dutyTeam==null||dutyTeam.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 팀번호");
		}
		
		if(dutyNo==null||dutyNo.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 일감번호");
		}
		service.removeProjectDuty(dutyNo);
		
		String lvn = String.format("redirect:/lecture/%s/projectDuty/%s", lectNo, dutyTeam);
		
		return lvn;
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
		
		List<ProjectDutyVO> projectDutyList = service.selectProjectDutylistNonPaging(teamCd);
		body.put("projectDutyList", projectDutyList);
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	
}















