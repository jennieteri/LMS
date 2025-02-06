package kr.or.ddit.yguniv.person.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/authorization")
public class authorizationController {
	
	 //접근 제어 권한 조회
		@GetMapping 
		public String SelectAccess(){
			return "person/authorizationList";
		}
		/*
		 * // 접근 제어 권한 등록
		 * 
		 * @PostMapping 
		 * public String CreateAccess(){ 
		 * return "person/authorizationForm"; 
		 * }
		 * 
		 * 
		 * //수정
		 * 
		 * @PutMapping("{authorityId}") 
		 * public String UpdateAccess(@PathVariable()String authorityId) { 
		 * return "person/authorizationEdit"; 
		 * } 
		 * 
		 * //삭제
		 * 
		 * @PutMapping("{authorityId}") 
		 * public String DeleteAccess(@PathVariable()String authorityId) { 
		 * return "person/authorizationForm"; 
		 * }
		 */

	
	

}
