package kr.or.ddit.yguniv.jobtest.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.jobtest.service.JobTestService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.security.conf.PersonVoWrapper;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import kr.or.ddit.yguniv.vo.JobTestVO;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/jobtest")
public class JobTestController {
	public static final String MODELNAME ="jobtest";
	
	@Autowired
	private JobTestService service;
	
	@ModelAttribute(MODELNAME)
	public JobTestVO jobtest() {
		return new JobTestVO();
	}

	// form으로 보냄
	@GetMapping("new")
	public String createForm() {
		return "jobTest/jobTestForm";
	}

	// 시험메인화면
	@GetMapping
	public String main(Model model, Principal principal) {
		model.addAttribute("principal",principal.getName());
		model.addAttribute("list", service.selectjobTestList());
		return "jobTest/jobTestMain";
	}
	

	@GetMapping("checkToday/{stuId}")
	public ResponseEntity<Map<String, String>> checkTestToday(@PathVariable String stuId) {
	    boolean isDuplicate = service.checkTestToday(stuId);
	    
	    Map<String, String> response = new HashMap<>();
	    if (isDuplicate) {
	        response.put("status", "denied"); // 중복 응시
	        response.put("message", "오늘 이미 검사를 완료하셨습니다.");
	    } else {
	        response.put("status", "allowed"); // 검사 가능
	        response.put("message", "검사를 진행할 수 있습니다.");
	   }
	    return ResponseEntity.ok(response);
	}

	
	// 학생 윈도우 창으로 직업선호도평가문제 전체 조회
	@GetMapping("list")
	public String selectlist(Model model, Principal principal) {
		String lvn="";
		model.addAttribute("principal",principal.getName());
		model.addAttribute("list",service.selectjobTestList());
		lvn= "/jobTest/jobTestList";
		return lvn;
	}
	
	
	// 교직원 직업선호도평가문제 전체 조회
	@GetMapping("listemp")
	public String selectListByEmp(Model model, Principal principal
			) {
		model.addAttribute("principal",principal.getName());
		model.addAttribute("list",service.selectjobTestList());
		
		return "jobTest/jobTestEmpList";
		
	}
	
	//학생 검사 응시
	@PostMapping("submit")
	@ResponseBody
	public Map<String, String> submitTest(@RequestBody JobTestResultVO jobTestResult) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        service.saveOrUpdateJobTestResult(jobTestResult);
	        response.put("status", "success");
	        response.put("message", "응시가 성공적으로 완료되었습니다.");
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("message", "응시 저장 중 오류가 발생했습니다.");
	    }
	    return response;
	}

	// 수정
	@PutMapping("edit/{jobTestNo}")
	@ResponseBody
	public ServiceResult update(
	        @PathVariable String jobTestNo, 
	        @RequestBody JobTestVO jobtest 
	) {
	    ServiceResult result = service.updateJobTest(jobtest);
	    if (result == ServiceResult.FAIL) {
	        throw new PKNotFoundException(); 
	    }
	    return result;
	}


	// 삭제
	@DeleteMapping("delete/{jobTestNo}")
	@ResponseBody
	public ServiceResult delete(
			@PathVariable() String jobTestNo
	) {
	    ServiceResult result = service.deletejobTest(jobTestNo);
	    if (result == ServiceResult.FAIL) {
	        throw new PKNotFoundException(); 
	    }
	    return result;
	}
	
	// 문제 추가
	@PostMapping("create")
	@ResponseBody
	public List<JobTestVO> createJobTest(@RequestBody JobTestVO jobTest) {
	    // 문제를 추가
	    service.insertjobTest(jobTest);

	    // 전체 문제 리스트를 반환
	    List<JobTestVO> jobTestList = service.selectjobTestList();

	    return jobTestList;
	}

}

