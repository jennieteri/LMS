package kr.or.ddit.yguniv.noticeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

	//전체 일정 가져오기
	@GetMapping("all")
	public String getAll() {
		return "noticeboard/noticeboardCalendar";
	}
	
	//1학기 일정 가져오기
	@GetMapping("firstSemester")
	public String getFirstSemester() {
		return "";
	}
	
	//2학기 일정 가져오기
	@GetMapping("secondSemester")
	public String getSecondSemester() {
		return "";
	}
}
