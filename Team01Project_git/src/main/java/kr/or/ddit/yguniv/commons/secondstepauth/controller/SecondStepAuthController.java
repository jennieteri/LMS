package kr.or.ddit.yguniv.commons.secondstepauth.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.login.dao.PersonDAO;
import kr.or.ddit.yguniv.vo.PersonVO;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.service.DefaultMessageService;
import net.nurigo.sdk.message.service.MessageService;

@Controller
@RequestMapping("/2fa")
public class SecondStepAuthController {
	
	@Autowired
	PersonDAO personDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@GetMapping
	public String getForm(
			@RequestParam(value = "error", required = false)String error,
			@RequestParam(value = "exception", required = false)String exception,
			HttpSession session
			,Model model) {
		// 세션에서 2차 인증 데이터를 가져옴
	    String secondAuthMethod = (String) session.getAttribute("secondAuthMethod");
	    String secondAuthData = (String) session.getAttribute("secondAuthData");
	
	    model.addAttribute("secondAuthMethod", secondAuthMethod);
	    model.addAttribute("secondAuthData", secondAuthData);
	    
	    model.addAttribute("error", error);
		model.addAttribute("exception", exception);
	    
		return "/secondstepauth/secondStepAuth";
	}
	
	//인증 처리
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> secondAuth(
			@RequestBody Map<String,Object> request
			,HttpSession session
			){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		String authCode = (String)request.get("authCode");
		
		String savedAuthCode = (String)session.getAttribute("authCode");
		
		if (savedAuthCode == null) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message", "인증시간초과");
	    }

	    if (savedAuthCode.equals(authCode)) {
	        // 인증 성공 시 세션에서 인증번호 제거
	        session.removeAttribute("authCode");
	        session.setAttribute("is2FAAuthenticated", true);
	        body.put("message", "인증성공!");
	    } else {
	        status = HttpStatus.UNAUTHORIZED;
	        body.put("message", "인증번호 불일치, 인증실패!");
	    }
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("request")
	@ResponseBody
	public ResponseEntity<Object> checkAuth(
			Principal user
			,HttpSession session
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		
		PersonVO person = personDao.selectPersonForAuth(id);
		String secondAuthMethod = person.getCrtfcMnCd();
		
		switch (secondAuthMethod) {
		case "CM01":
			emailMethod(person.getEml(),session);
			body.put("message","등록된 이메일로 인증번호가 전송되었습니다.");
			break;
		case "CM02":
			smsMethod(person.getMbtlnum(),session);
			body.put("message","등록된 연락처로 인증번호가 전송되었습니다.");
			break;
		default:
			//todo: 테스트기간동안 2차인증 패스
			session.setAttribute("is2FAAuthenticated", true);
			body.put("pass","index.do");
			//--------------------------------
			
			
			//body.put("message","유효하지않은 인증방법입니다.");
			
			break;
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//sms인증인경우
	public void smsMethod(String phoneNumber,HttpSession session) {
		final String API_KEY = "NCSFMBZFCLMHWXX1";
		final String API_SECRET = "LLUC1U6VITX6DZGX5TALMHMF4TC98MJN";
		final String API_URL = "https://api.coolsms.co.kr"; 
		
		// MessageService 객체 초기화
	    DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET, API_URL);
		
		String authCode = RandomNumber();
		Message message = new Message();
		message.setTo(phoneNumber);
		message.setFrom("010-4051-3955");
		message.setText("[연근대학교] 인증번호는 " + authCode + " 입니다.");
		message.setType(MessageType.SMS);
		session.setAttribute("authCode", authCode);
		 // 메시지 전송
	    try {
	        messageService.sendOne(new net.nurigo.sdk.message.request.SingleMessageSendingRequest(message));
	        System.out.println("SMS 전송 성공: " + phoneNumber);
	    } catch (Exception e) {
	        System.err.println("SMS 전송 실패: " + e.getMessage());
	    }
	}
	
	//email인증인경우
	public void emailMethod(String email,HttpSession session) {
		String authCode = RandomNumber();
		session.setAttribute("authCode", authCode);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("[연근대학교] 이메일 인증 코드"); // 이메일 제목
	    mailMessage.setText("인증번호는 " + authCode + " 입니다."); // 이메일 내용
	    mailMessage.setFrom("dksdustjq123@gmail.com"); // 발신자 이메일 (설정된 이메일 계정)
		
	    try {
	        // 이메일 전송
	        mailSender.send(mailMessage);
	        System.out.println("이메일 전송 성공: " + email);
	    } catch (Exception e) {
	        System.err.println("이메일 전송 실패: " + e.getMessage());
	    }
	    
	    
	}
	
	
	public String RandomNumber() {
		
		Random random = new Random();
		int ranNum = random.nextInt(90000)+10000;
		String authcode = String.valueOf(ranNum);
		
		return authcode;
		
		//return "0000";
	}
}
