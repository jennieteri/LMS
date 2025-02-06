package kr.or.ddit.yguniv.chatbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/chatbot")
public class ChatbotController {
	
	//챗봇
	//입력하면 그에 맞는 uri출력
		@GetMapping("{what}")
		public String createForm(@PathVariable String what) {
			return "uri";
		}

}
