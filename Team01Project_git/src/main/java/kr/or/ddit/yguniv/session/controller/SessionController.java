package kr.or.ddit.yguniv.session.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
	
	  @PostMapping("extendSession")
	    public ResponseEntity<String> extendSession(HttpSession session) {
	        try {
	            session.setMaxInactiveInterval(30 * 60); // 세션 타임아웃을 30분으로 연장
	            return ResponseEntity.ok("세션 연장 완료");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("세션 연장 실패");
	        }
	    }
}
