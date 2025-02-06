package kr.or.ddit.yguniv.eventregistrant.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.eventregistrant.service.EventRegistrantService;
import kr.or.ddit.yguniv.vo.EventRegistrantVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/eventregistrant")
public class EventRegistrantController {
	public static final String MODELNAME = "event";
	
	@Autowired
	private EventRegistrantService service;
	
	@ModelAttribute
	public EventRegistrantVO EventRegistrant() {
		return new EventRegistrantVO();
	}
	
	
	@PostMapping
	public ResponseEntity<String> insertEventRegistrant(@RequestBody EventRegistrantVO eventRegistrant) {
	    try {
	        service.insertEventRegistrant(eventRegistrant);
	        return ResponseEntity.ok("신청 완료");
	    } catch (RuntimeException e) {
	        if ("이미 신청되었습니다.".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 중 오류가 발생했습니다.");
	    }
	}
	
	
	
	
	@DeleteMapping
	public ResponseEntity<String> deleteEventRegistrant(@RequestBody EventRegistrantVO eventRegistrant) {
	    try {
	        service.deleteRegistredForJObBoard(eventRegistrant);
	        return ResponseEntity.ok("취소 완료");
	    } catch (RuntimeException e) {
	        if ("이미 취소 되었습니다.".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 중 오류가 발생했습니다.");
	    }
	}

}

