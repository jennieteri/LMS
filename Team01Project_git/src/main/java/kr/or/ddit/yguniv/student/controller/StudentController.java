package kr.or.ddit.yguniv.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.SemesterDuplicatedException;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.person.dao.PersonMapper;
import kr.or.ddit.yguniv.student.service.StudentServiceImpl;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.vo.AcademicProbationVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentServiceImpl service;
	
	@Autowired
	CommonCodeServiceImpl cocoService;
	
	@GetMapping
	public String stuList(Model model) {
		model.addAttribute("condition", new StudentVO());
		model.addAttribute("gradeList", cocoService.getCodeList("YEAR"));
		model.addAttribute("departmentList", cocoService.getDepartmentList());
		model.addAttribute("streCateList", cocoService.getCodeList("GR01"));
		
		return "student/studentList";
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> stuListDataTables(@RequestBody DataTablesPaging<StudentVO> paging) {
		Map<String, Object> result = new HashMap<>();
	    result.put("draw", paging.getDraw());
	    result.put("data", service.studentList(paging));
	    result.put("recordsTotal", paging.getRecordsTotal());
	    result.put("recordsFiltered", paging.getRecordsFiltered());
	    result.put("pageHTML", new BootStrapPaginationRenderer().renderPagination(paging.getPaginationInfo(), "fnPaging"));
		return result;
	}

	@GetMapping("{stuId}")
	@ResponseBody
	public StudentVO selectStudet(@PathVariable String stuId) {
		return service.selectStudet(stuId);
	}
	
	@PostMapping("academicProbation")
	public ResponseEntity<Object> insertAcademicProbation(
		@Validated(InsertGroup.class) AcademicProbationVO academicProbationVO
		, BindingResult error
	){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			if(service.insertAcademicProbation(academicProbationVO)) {
				body.put("academicProbationVO", academicProbationVO);
			}else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message", "서버오류로 인한 등록 실패");
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
	
	@PutMapping("expulsion/{stuId}")
	public void expulsion(@PathVariable String stuId){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		service.expulsion(stuId);
	}
	
	
	
}






