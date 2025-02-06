package kr.or.ddit.yguniv.lecture.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.checkerframework.framework.qual.RequiresQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureInquiryServiceImpl;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.validate.AnswerGroup;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.LectureInquiryBoardVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lecture/{lectNo}/inquiry")
public class LectureInquiryController {
	@Autowired
	LectureInquiryServiceImpl service;
	
	@Autowired
	private LectureMaterialsServiceImpl lectureMaterialsService;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model){
		LectureVO lectureVO = lectureMaterialsService.selectLecture(lectNo);
		if(lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	
	@GetMapping
	public String selectLectureInquiryBoardList(
		@PathVariable String lectNo
		, @RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
	) {
		PaginationInfo<LectureInquiryBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("libList", service.selectLectureInquiryBoardList(lectNo, paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		return "lecture/materials/lecture/lectureInquiryBoardList";
	}
	
	@GetMapping("{libNo}")
	public String selectLectureInquiryBoard(@PathVariable Long libNo, Model model) {
		model.addAttribute("lib", service.selectLectureInquiryBoard(libNo));
		return "lecture/materials/lecture/lectureInquiryBoardDetail";
	}
	
	@GetMapping("new")
	public String lectureInquiryBoardForm() {
		return "lecture/materials/lecture/lectureInquiryBoardForm";
	}
	
	@PostMapping("new")
	public ResponseEntity<Object> insertLectureInquiryBoard(
		@PathVariable String lectNo
		, @Validated(InsertGroup.class) @RequestBody LectureInquiryBoardVO lib
		, BindingResult error	
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			lib.setLectNo(lectNo);
			service.insertLectureInquiryBoard(lib);
			body.put("libNo", lib.getLibNo());
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
	
	@PutMapping("{libNo}/edit")
	public ResponseEntity<Object> updateLectureInquiryBoard(
		@PathVariable Long libNo
		, @RequestBody @Validated(UpdateGroup.class) LectureInquiryBoardVO lectureInquiryBoardVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			lectureInquiryBoardVO.setLibNo(libNo);
			service.updateLectureInquiryBoard(lectureInquiryBoardVO);
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
	
	@DeleteMapping("{libNo}")
	public void deleteLectureInquiryBoard(@PathVariable Long libNo) {
		service.deleteLectureInquiryBoard(libNo);
	}
	
	// 답변 등록, 수정 하나로 해결
	@PostMapping("{libNo}/answer")
	public ResponseEntity<Object> insertLectureInquiryBoardAnswer(
		@PathVariable Long libNo
		, @RequestBody @Validated(AnswerGroup.class) LectureInquiryBoardVO lectureInquiryBoardVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(!error.hasErrors()) {
			lectureInquiryBoardVO.setLibNo(libNo);
			service.insertLectureInquiryBoardAnswer(lectureInquiryBoardVO);
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
	
	@DeleteMapping("{libNo}/answer")
	public void deleteLectureInquiryBoardAnswer(@PathVariable Long libNo) {
		service.deleteLectureInquiryBoardAnswer(libNo);
	}
}
