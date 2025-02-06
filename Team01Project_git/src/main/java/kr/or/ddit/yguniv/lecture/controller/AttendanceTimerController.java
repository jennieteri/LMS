package kr.or.ddit.yguniv.lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.yguniv.lecture.dao.AttendanceTimerMapper;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attendance/{lectNo}/{weekNo}/{lectOrder}/{stuId}")
public class AttendanceTimerController {
	@Autowired
	private AttendanceTimerMapper mapper; 
	
	@GetMapping
	public AttendanceVO getViewTime(AttendanceVO attendanceVO) {
		return mapper.selectViewTime(attendanceVO);
	}
	
	@PostMapping
	public void updateViewTime(@RequestBody AttendanceVO attendanceVO) {
		log.info("{}", attendanceVO.toString());
		mapper.mergeIntoViewTime(attendanceVO);
	}
}
