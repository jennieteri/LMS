package kr.or.ddit.yguniv.student.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.SingletonMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.SemesterDuplicatedException;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.student.service.StudentRecordsServiceImpl;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.ReturnGroup;
import kr.or.ddit.yguniv.vo.StudentRecordsVO;

@Controller
@RequestMapping("/student")
public class StudentRecordsController {
	@Autowired
	StudentRecordsServiceImpl srService;
	
	@Autowired
	CommonCodeServiceImpl cocoService;
	
	@GetMapping("studentRecords")
	public String selectStudentRecordsList(
		Authentication authentication
		, @RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") StudentRecordsVO detailCondition
		, Model model
	) {
		PaginationInfo<StudentRecordsVO> paging = new PaginationInfo<>();
		detailCondition.setId(authentication.getName());
		detailCondition.setRole(authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.filter(a->StringUtils.containsAny(a, "ROLE_STUDENT", "ROLE_PROFESSOR"))
				.findFirst().orElse(null));
		paging.setCurrentPage(page);
		paging.setDetailCondition(detailCondition);
		model.addAttribute("stuRecList", srService.selectStudentRecordsList(paging));
		model.addAttribute("departmentList", cocoService.getDepartmentList());
		model.addAttribute("semesterList", cocoService.getSemesterList(null));
		model.addAttribute("streCateCodeList", cocoService.getCodeList("STU_REC"));
		model.addAttribute("streStatusCodeList", cocoService.getCodeList("ST01"));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "student/studentRecordsList";
	}
	
	@ResponseBody
	@GetMapping("studentRecords/{streIssuNo}")
	public StudentRecordsVO selectStudentRecords(StudentRecordsVO studentRecordsVO) {
		return srService.selectStudentRecords(studentRecordsVO);
	}
	
	@ResponseBody
	@GetMapping("studentRecords/prevrequest/{stuId}")
	public Map<String, String> hasPrevRequest(@PathVariable String stuId){
		return new SingletonMap("streIssuNo", srService.selectPrevRequest(stuId));
	}
	
	@PostMapping("studentRecords/new")
	public ResponseEntity<Object> createStudentRecords(
		Authentication authentication
		, @Validated(InsertGroup.class) StudentRecordsVO studentRecordsVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			studentRecordsVO.setStuId(authentication.getName());
			try {
				srService.insertStudentRecords(studentRecordsVO);
			} catch(SemesterDuplicatedException e) {
				status = HttpStatus.BAD_REQUEST;
				body.put("message", "해당 학기에는 이미 처리된 신청이 있습니다");
			}
		}else {
			status = HttpStatus.BAD_REQUEST;
	    	String errorMessage = error.getFieldErrors().stream()
	    							.findFirst()
					    			.map(FieldError :: getDefaultMessage)
					    			.get();
	    	body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
	
	@ResponseBody
	@PutMapping("studentRecords/{streIssuNo}/cancel")
	public Map<String, Boolean> cancelStudentRecords(StudentRecordsVO studentRecordsVO) {
		return new SingletonMap("result", srService.cancelStudentRecords(studentRecordsVO));
	}
	
	@ResponseBody
	@PutMapping("studentRecords/{streIssuNo}/consent")
	public Map<String, Boolean> consentStudentRecords(StudentRecordsVO studentRecordsVO) {
		return new SingletonMap("result", srService.consentStudentRecords(studentRecordsVO));
	}
	
	
	@PutMapping("studentRecords/{streIssuNo}/return")
	public ResponseEntity<Object> returnStudentRecords(
		@PathVariable String streIssuNo
		, @Validated(ReturnGroup.class) @RequestBody StudentRecordsVO studentRecordsVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			studentRecordsVO.setStreIssuNo(streIssuNo);
			srService.returnStudentRecords(studentRecordsVO);
		}else {
			status = HttpStatus.BAD_REQUEST;
	    	String errorMessage = error.getFieldErrors().stream()
	    							.findFirst()
					    			.map(FieldError :: getDefaultMessage)
					    			.get();
	    	body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
}