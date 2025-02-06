package kr.or.ddit.yguniv.lecture.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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

import kr.or.ddit.yguniv.commons.dao.CommonCodeMapper;
import kr.or.ddit.yguniv.lecture.dao.LectureMapper;
import kr.or.ddit.yguniv.lecture.service.LectureServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.validate.DeleteGroup;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.ReturnGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.LectureInquiryBoardVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lecture")
public class LectureController {
	@Autowired
	private LectureMapper mapper;
	@Autowired
	private LectureServiceImpl service;
	@Autowired
	private CommonCodeMapper cocoMapper;
	
	@ResponseBody
	@GetMapping("my")
	public List<LectureVO> myLectureList(Authentication auth){
		if(auth == null) return null;
		return mapper.myLectureList(auth.getName());
	}
	
	@ResponseBody
	@GetMapping("{semstrNo}")
	public List<LectureVO> selectLectureList(Authentication authentication, @PathVariable String semstrNo) {
		Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
		LectureVO lectureVO = new LectureVO();
		lectureVO.setSemstrNo(semstrNo);
		if(role.contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"))) {
			lectureVO.setProfeId(authentication.getName());
		}
		return service.selectLectureRequestList(lectureVO);
	}
	
	@GetMapping
	public String selectLectureRequestList(
		@RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
	) {
		PaginationInfo<LectureInquiryBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		return "lecture/lectureRequestList";
	}
	
	@GetMapping("request/{lectNo}")
	public String selectLectureRequest(LectureVO lectureVO, Model model) {
		model.addAttribute("lectVo", service.selectLectureRequest(lectureVO));
		return "lecture/lectureRequestDetail";
	}
	
	@GetMapping("request/new")
	public String lectureRequestForm(Model model) {
		// 과목 리스트
		model.addAttribute("subjectList", cocoMapper.getSubjectList());
		// 학기 리스트
		model.addAttribute("semesterList", cocoMapper.getSemesterList(null));
		// 평가 기준 리스트
		model.addAttribute("leesList", cocoMapper.getCodeList("LEES"));
		// 주차 리스트
		model.addAttribute("weekList", cocoMapper.getCodeList("WEEK"));
		// 강의실 리스트
		model.addAttribute("croomList", cocoMapper.getClassRoomList());
		// 요일 리스트
		model.addAttribute("dotwList", cocoMapper.getCodeList("DOTW"));
		// 교시 리스트
		model.addAttribute("etimeList", cocoMapper.getEducationTimeTableCodeList());
		
		return "lecture/lectureRequestForm";
	}
	
	@ResponseBody
	@PostMapping("request/new")
	public ResponseEntity<Object> insertLectureRequest(
		@Validated(InsertGroup.class) @RequestBody LectureVO lectureVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			try {
				service.insertLectureRequest(lectureVO);
			}catch(RuntimeException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message", "서버 오류, 잠시 후 다시 시도해 주세요");
			}
			body.put("lectNo", lectureVO.getLectNo());
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
	
	@GetMapping("request/{lectNo}/edit")
	public String lectureRequestEdit(LectureVO lectureVO, Model model) {
		model.addAttribute("lectVo", service.selectLectureRequest(lectureVO));
		// 과목 리스트
		model.addAttribute("subjectList", cocoMapper.getSubjectList());
		// 학기 리스트
		model.addAttribute("semesterList", cocoMapper.getSemesterList(null));
		// 평가 기준 리스트
		model.addAttribute("leesList", cocoMapper.getCodeList("LEES"));
		// 주차 리스트
		model.addAttribute("weekList", cocoMapper.getCodeList("WEEK"));
		// 강의실 리스트
		model.addAttribute("croomList", cocoMapper.getClassRoomList());
		// 요일 리스트
		model.addAttribute("dotwList", cocoMapper.getCodeList("DOTW"));
		// 교시 리스트
		model.addAttribute("etimeList", cocoMapper.getEducationTimeTableCodeList());
		
		return "lecture/lectureRequestForm";
	}
	
	@PutMapping("request/{lectNo}/edit")
	public ResponseEntity<Object> updateLectureRequest(
		@PathVariable String lectNo
		, @Validated(UpdateGroup.class) @RequestBody LectureVO lectureVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			lectureVO.setLectNo(lectNo);
			try {
				service.updateLectureRequest(lectureVO);
			}catch(RuntimeException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message", "서버 오류, 잠시 후 다시 시도해 주세요");
			}
			body.put("lectNo", lectureVO.getLectNo());
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
	
	@DeleteMapping("request/{lectNo}")
	public void deleteLectureRequest(LectureVO lectureVO) {
		service.deleteLectureRequest(lectureVO);
	}
	
	@PutMapping("request/{lectNo}/consent")
	public void consentLectureRequest(LectureVO lectureVO) {
		lectureVO.setLectStatusCd("L02");
		service.lectureStatusUpdate(lectureVO);
		service.insertLectureWeekPlan(lectureVO);
	}
	
	@PutMapping("request/{lectNo}/return")
	public ResponseEntity<Object> returnLectureRequest(
		@RequestBody @Validated(ReturnGroup.class) LectureVO lectureVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			try {
				lectureVO.setLectStatusCd("L07");
				service.lectureStatusUpdate(lectureVO);
			}catch(RuntimeException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message", "서버 오류, 잠시 후 다시 시도해 주세요");
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
	
	@PutMapping("{lectNo}/abolition")
	public ResponseEntity<Object> updateLectureAbolition(
		@RequestBody @Validated(DeleteGroup.class) LectureVO lectureVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			try {
				lectureVO.setLectStatusCd("L04");
				service.lectureStatusUpdate(lectureVO);
			}catch(RuntimeException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body.put("message", "서버 오류, 잠시 후 다시 시도해 주세요");
			}
		}else {
			status = HttpStatus.BAD_REQUEST;
			List<FieldError> errorList = error.getFieldErrors();
	    	String errorMessage = error.getFieldErrors().stream()
	    							.findFirst()
	    							.map(FieldError :: getDefaultMessage)
					    			.get();
	    	body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
	
	@DeleteMapping("{lectNo}/abolition")
	public void deleteLectureAbolition(LectureVO lectureVO) {
		// 추후 강의 개강 기간인지 확인해서 L02 / L03 수정이 필요함
		lectureVO.setLectStatusCd("L02");
		lectureVO.setLectAbl("");
		service.lectureStatusUpdate(lectureVO);
	}
	
	@PutMapping("{lectNo}/abolition/consent")
	public void consentLectureAbolition(LectureVO lectureVO) {
		lectureVO.setLectStatusCd("L05");
		service.lectureStatusUpdate(lectureVO);
	}
	
	@PutMapping("{lectNo}/abolition/return")
	public void returnLectureAbolition(LectureVO lectureVO) {
		// 추후 강의 개강 기간인지 확인해서 L02 / L03 수정이 필요함
		lectureVO.setLectStatusCd("L02");
		lectureVO.setLectAbl("");
		service.lectureStatusUpdate(lectureVO);
	}
	
	@GetMapping(value = "request/{lectNo}", produces = MediaType.APPLICATION_PDF_VALUE)
	public void selectLecturePDF() {
		
	}
}
