package kr.or.ddit.yguniv.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/findMyAccount")
public class FindMyAccountController {
	//학번 찾기 
	@GetMapping
	public String createUserId(){
		
		return "login/findIdForm";
	}
	
	//비밀번호 읽어오기 
	@GetMapping("/pw")
	public String createForm() {
		return "login/findPwForm";
	}
	
	//비밀번호 초기화 
	@PostMapping()
	public String createUserPw() {
		return "login/resetPwForm";
		
	}
	
	
	

}
