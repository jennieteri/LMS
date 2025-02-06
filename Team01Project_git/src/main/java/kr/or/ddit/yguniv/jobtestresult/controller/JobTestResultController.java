package kr.or.ddit.yguniv.jobtestresult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.yguniv.jobtestresult.service.JobTestResultService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.JobTestResultVO;


@Controller
@RequestMapping("/jobtestresult")
public class JobTestResultController {
	public static final String MODELNAME ="jobtestresult";
		
		@Autowired
		private JobTestResultService service;
		
		@ModelAttribute(MODELNAME)
		public JobTestResultVO jobtestresult() {
			return new JobTestResultVO();
		}

	//form으로 보냄
	@GetMapping("new")
	public String createForm() {
		return "jobTestResult/jobTestResultForm";
	}
	
	
	//전체조회
	@GetMapping()
	public String selectlist(
			@RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			,Model model
			) {
		PaginationInfo<JobTestResultVO>paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("list", service.selectjobTestResultListByEmp(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		return "jobTestResult/jobTestResultList";
	}
	

	// 학생 바로 응시 결과 조회하는 (window창으로)
	@GetMapping("result/{stuId}")
	public String result(Model model, @PathVariable String stuId, Authentication auth) {
		String lvn ="";
		JobTestResultVO result = service.selectjobTestResult(stuId);
		System.out.println("응시 결과: " + result);
		model.addAttribute("result", result);
		int [] scores= {
			result.getJtrRea(),
			result.getJtrInq(),
			result.getJtrArt(),
			result.getJtrSoc(),
			result.getJtrCon()
		};
		int topScore = scores[0];
		String topType="진취형";
		if(result.getJtrArt()>topScore) {
			topScore = result.getJtrArt();
			topType="예술형";
		}
		if(result.getJtrInq()>topScore) {
			topScore = result.getJtrInq();
			topType ="탐구형";
		}
		if(result.getJtrSoc()>topScore) {
			topScore=result.getJtrSoc();
			topType="사회형";
		}
		if(result.getJtrEnt()>topScore) {
			topScore=result.getJtrEnt();
			topType="진취형";
		}
		if(result.getJtrCon()>topScore) {
			topScore=result.getJtrCon();
			topType="관습형";
		}
		System.out.println(topType);
		model.addAttribute("topType", topType);
		model.addAttribute("auth",auth.getPrincipal());
		lvn= "/jobTestResult/jobTestResultForm";
		return lvn;
	}
	
	
	
	
	
	// 학생 응시 결과 조회 사이드 바에서
	@GetMapping("resultside/{stuId}")
	public String resultside(Model model, @PathVariable String stuId, Authentication auth) {
		String lvn ="";
		JobTestResultVO result = service.selectjobTestResult(stuId);
		System.out.println("응시 결과: " + result);
		model.addAttribute("result", result);
		int [] scores= {
			result.getJtrRea(),
			result.getJtrInq(),
			result.getJtrArt(),
			result.getJtrSoc(),
			result.getJtrCon()
		};
		int topScore = scores[0];
		String topType="진취형";
		if(result.getJtrArt()>topScore) {
			topScore = result.getJtrArt();
			topType="예술형";
		}
		if(result.getJtrInq()>topScore) {
			topScore = result.getJtrInq();
			topType ="탐구형";
		}
		if(result.getJtrSoc()>topScore) {
			topScore=result.getJtrSoc();
			topType="사회형";
		}
		if(result.getJtrEnt()>topScore) {
			topScore=result.getJtrEnt();
			topType="진취형";
		}
		if(result.getJtrCon()>topScore) {
			topScore=result.getJtrCon();
			topType="관습형";
		}
		System.out.println(topType);
		model.addAttribute("topType", topType);
		model.addAttribute("auth",auth.getPrincipal());
		return "jobTestResult/jobTestResultDetail";
	}
	

}
