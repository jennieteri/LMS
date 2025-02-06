package kr.or.ddit.yguniv.commons.login.qrlogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qrLogin")
public class QrLoginController {
	
	@GetMapping
	public String qrLoginForm() {
		//qr로그인 버튼을 누르면 qr로그인 할 수있는 화면으로 이동.
		//웹캠과 웹캠에대한 접근권한 확인
		
		return "qrLogin/qrLoginForm";
	}
	
	@PostMapping
	public String qrLogin() {
		//qr코드 데이터를 받아 로그인 처리
		//로그인 성공시 2차인증으로 이동
		
		return "";
	}
	
}
