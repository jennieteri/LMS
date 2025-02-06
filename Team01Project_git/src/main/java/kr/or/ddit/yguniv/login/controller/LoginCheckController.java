
package kr.or.ddit.yguniv.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("loginForm")
public class LoginCheckController{
	@GetMapping
	public String loginForm(
			@RequestParam(value = "error", required = false)String error,
			@RequestParam(value = "exception", required = false)String exception,
			Model model) {
		
		log.info("컨트롤러");
		log.info("로그인 폼 요청: error={}, exception={}", error, exception);

		
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		

		
		return "/login/loginForm";                                                                       
	}
	
	/*
	 * @PostMapping("/login") public String login(PersonVO person) {
	 * log.info("person {}", person);
	 * 
	 * return "/index";
	 * 
	 * }
	 */
	
	
	
	

}
