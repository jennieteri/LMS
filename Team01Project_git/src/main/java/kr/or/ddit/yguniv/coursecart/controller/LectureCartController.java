package kr.or.ddit.yguniv.coursecart.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.coursecart.service.LectureCartService;
import kr.or.ddit.yguniv.lecture.service.LectureServiceImpl;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.LectureListDTO;
import kr.or.ddit.yguniv.vo.LectureVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lectureCart")
@RequiredArgsConstructor
public class LectureCartController {
	private final LectureCartService service;
	private final LectureServiceImpl lecService;

	// 테이블 ->VO
	//본 수강신청 페이지로 이동
	@GetMapping("final")
	public String selectList(Principal prin, Model model) {
		
		log.info("{}", prin.getName());
		model.addAttribute("stuId", prin.getName());

		return "lecturecart/postLectureCartList";
	}
	
	@GetMapping("pre")
	public String selectPreList(Principal prin, Model model) {
		
		log.info("{}", prin.getName());
		model.addAttribute("stuId", prin.getName());
		
		return "lecturecart/lectureCartList";
	}

	@GetMapping()
	public ResponseEntity<LectureListDTO> getLectureList(Principal prin) {
		
		List<LectureVO> lectureList = service.getLectureList();
		List<LectureVO> studentLectureList = service.getStudentLectureList(prin.getName());
		List<LectureVO> attendeeStudentLectureList = service.attendeeStudentLectureList(prin.getName());
		
		LectureListDTO listDto = new LectureListDTO();
		listDto.setLectureList(lectureList);
		listDto.setStudentLectureList(studentLectureList);
		listDto.setAttendeeStudentLectureList(attendeeStudentLectureList);
		
		log.info("{}",listDto);
		
		return ResponseEntity.ok(listDto);
	}


	// 강의 계획서 출력
	// 강의 계획서를 팝업창 처럼 출력해야함.
	@GetMapping("lecturePaper/{lectNo}")
	public String selectLecturePaperScript(LectureVO lectureVO,Model model) {
		model.addAttribute("lectVo", lecService.selectLectureRequest(lectureVO));
		return "/lecturecart/lecturePaperDetail";
	}

	
	@GetMapping("{lectNo}")
	@ResponseBody
	public Map<String,Object> selectLecturePaper(@PathVariable String lectNo) {
		Map<String, Object> map = new HashMap<>();
		LectureVO lectVo= service.getLecturePaper(lectNo);
		map.put("lectVo", lectVo);
		
		return map;
	}

	// 신청한 강의를 저장하기
	// [신청] 버튼을 누르면 해당 강의가 장바구니에 들어가고 페이지 유지
	@PostMapping
	public ResponseEntity<Object> insertLectureCart(@RequestBody LectureCartVO leVo, BindingResult error) {
		Map<String, Object> body = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		if(!error.hasErrors()) {
			service.insertCart(leVo);
			LectureVO lectureVO = new LectureVO();
			lectureVO.setLectNo(leVo.getLectNo());
			body.put("lectureVO", lecService.selectLectureRequest(lectureVO));
		}else {
			status = HttpStatus.BAD_REQUEST;
	    	String errorMessage = error.getFieldErrors().stream()
	    			.map(FieldError :: getDefaultMessage)
	    			.collect(Collectors.joining("\r\n"));
	    	body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	// 신청한 강의를 저장하기
	// [신청] 버튼을 누르면 해당 강의가 장바구니에 들어가고 페이지 유지
	@PutMapping
	public ResponseEntity<Object> updateLectureCart(@RequestBody List<LectureCartVO> leVo, BindingResult error) {
		Map<String, Object> body = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		if(!error.hasErrors()) {
			body.put("result", service.updateCart(leVo));
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
					.map(FieldError :: getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}

	@PutMapping("one")
	public ResponseEntity<Object> updateLectureOneCart(@RequestBody LectureCartVO leVo, BindingResult error) {
		Map<String, String> body = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		int result = 0;
		if(!error.hasErrors()) {
			result = service.directInsertOneCart(leVo);
			body.put("result", String.valueOf(result));
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
					.map(FieldError :: getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	// 신청한 강의를 취소하기
	// [삭제] 버튼을 누르면 해당 강의가 장바구니에서 빠지고 페이지 유지
	@DeleteMapping()
	public ResponseEntity<Object> deleteLectureCart(LectureCartVO leVo, BindingResult error) {
		Map<String, String> body = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		if(!error.hasErrors()) {
			service.deleteCart(leVo);
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
