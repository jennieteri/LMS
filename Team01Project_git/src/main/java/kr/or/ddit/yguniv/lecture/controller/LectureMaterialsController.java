package kr.or.ddit.yguniv.lecture.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.absencecertificatereceipt.service.AbsenceCertificateReceiptServiceImpl;
import kr.or.ddit.yguniv.atch.service.AtchFileServiceImpl;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.lecture.service.LectureServiceImpl;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.OfflineInsertGroup;
import kr.or.ddit.yguniv.validate.OnlineInsertGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import kr.or.ddit.yguniv.vo.OrderLectureDataVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lecture/{lectNo}/materials")
public class LectureMaterialsController {
	@Autowired
	private LectureMaterialsServiceImpl service;
	
	@Autowired
	private LectureServiceImpl lecService;
	
	@Autowired
	private AbsenceCertificateReceiptServiceImpl absenceService;
	
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model){
		LectureVO lectureVO = service.selectLecture(lectNo);
		if(lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	
	@GetMapping
	public String selectLectureMaterialsList(@PathVariable String lectNo, Authentication authentication, Model model) {
		model.addAttribute("lectureMaterialsList", service.selectOrderLectureDataList(lectNo, authentication.getName()));
		model.addAttribute("absenceList",absenceService.AbsenceListDistinct());
		model.addAttribute("stuId", authentication.getName());
		
		return "lecture/materials/lecture/lectureMaterialsList";
	}
	
	@GetMapping("{weekCd}/{lectOrder}")
	public OrderLectureDataVO selectOrderLectureData(
		@PathVariable String lectNo
		, @PathVariable String weekCd
		, @PathVariable Long lectOrder
	) {
		OrderLectureDataVO orderLectureDataVO = new OrderLectureDataVO();
		orderLectureDataVO.setLectNo(lectNo);
		orderLectureDataVO.setWeekCd(weekCd);
		orderLectureDataVO.setLectOrder(lectOrder);
		return service.selectOrderLectureData(orderLectureDataVO);
	}
	
	@GetMapping("{weekCd}/{lectOrder}/viewer")
	public String OrderLectureDataViewer(
		@PathVariable String lectNo
		, @PathVariable String weekCd
		, @PathVariable Long lectOrder
		, Model model
		, Authentication authentication
	) {
		OrderLectureDataVO orderLectureDataVO = new OrderLectureDataVO();
		orderLectureDataVO.setLectNo(lectNo);
		orderLectureDataVO.setWeekCd(weekCd);
		orderLectureDataVO.setLectOrder(lectOrder);
		model.addAttribute("orderLectureDataVO", service.selectOrderLectureData(orderLectureDataVO));
		return "lecture/materials/lecture/lectureMaterialsDetail";
	}
	
	@GetMapping("plan")
	public String selectLectureRequest(LectureVO lectureVO, Model model) {
		model.addAttribute("lectVo", lecService.selectLectureRequest(lectureVO));
		return "lecture/materials/lecture/lecturePlan";
	}
	
	@PostMapping("new")
	public ResponseEntity<Object> insertLectureWeek(
		@PathVariable String lectNo
		, @RequestBody @Validated(InsertGroup.class) LectureWeekVO lectureWeekVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if(!error.hasErrors()) {
			lectureWeekVO.setLectNo(lectNo);
			service.insertLectureWeek(lectureWeekVO);
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
	
	@PutMapping("{weekCd}/edit")
	public ResponseEntity<Object> updateLectureWeek(
		@PathVariable String lectNo, @PathVariable String weekCd
		, @RequestBody @Validated(UpdateGroup.class) LectureWeekVO lectureWeekVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			lectureWeekVO.setLectNo(lectNo);
			lectureWeekVO.setWeekCd(weekCd);
			service.updateLectureWeek(lectureWeekVO);
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
	
	@DeleteMapping("{weekCd}")
	public void deleteLectureWeek(@PathVariable String lectNo, @PathVariable String weekCd) {
		LectureWeekVO lectureWeekVO = new LectureWeekVO();
		lectureWeekVO.setLectNo(lectNo);
		lectureWeekVO.setWeekCd(weekCd);
		service.deleteLectureWeek(lectureWeekVO);
	}
	
	@PostMapping("orderdata/online")
	public ResponseEntity<Object> insertOnlineOrderLectureData(
		@PathVariable String lectNo
		, @Validated(OnlineInsertGroup.class) OrderLectureDataVO orderLectureDataVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			orderLectureDataVO.setLectNo(lectNo);
			service.insertOrderLectureData(orderLectureDataVO);
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
	
	@PostMapping("orderdata/offline")
	public ResponseEntity<Object> insertOfflineOrderLectureData(
			@PathVariable String lectNo
			, @Validated(OfflineInsertGroup.class) OrderLectureDataVO orderLectureDataVO
			, BindingResult error
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			orderLectureDataVO.setLectNo(lectNo);
			service.insertOrderLectureData(orderLectureDataVO);
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
	
	@PostMapping("{weekCd}/{lectOrder}/edit")
	public ResponseEntity<Object> updateOrderLectureData(
		@PathVariable String lectNo
		, @PathVariable Long lectOrder
		, @Validated(UpdateGroup.class) OrderLectureDataVO orderLectureDataVO
		, BindingResult error
	) {
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			orderLectureDataVO.setLectNo(lectNo);
			orderLectureDataVO.setOriginLectOrder(lectOrder);
			service.updateOrderLectureData(orderLectureDataVO);
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
	
	@DeleteMapping("{weekCd}/{lectOrder}")
	public void deleteOrderLectureData(
		@PathVariable String lectNo
		, @PathVariable String weekCd
		, @PathVariable Long lectOrder
	) {
		OrderLectureDataVO orderLectureDataVO = new OrderLectureDataVO();
		orderLectureDataVO.setLectNo(lectNo);
		orderLectureDataVO.setWeekCd(weekCd);
		orderLectureDataVO.setLectOrder(lectOrder);
		service.deleteOrderLectureData(orderLectureDataVO);
	}
	
	// 공결 인정서류 등록
	@PostMapping("new/absence")
	public ResponseEntity<Object> insertAbsence(
		@PathVariable String lectNo
		, @Validated(InsertGroup.class) AbsenceCertificateReceiptVO absenceVO
		, BindingResult error
	) {
		try {
			absenceVO.setLectNo(lectNo);
			absenceService.insertAbsenceCertificateReceipt(absenceVO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("공결 인증서 접수 완료");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("공결 인증서 접수 실패 : " + e.getMessage());
        }
	}
}
