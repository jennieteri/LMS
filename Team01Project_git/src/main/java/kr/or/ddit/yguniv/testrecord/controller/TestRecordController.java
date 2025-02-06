package kr.or.ddit.yguniv.testrecord.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/testRecord")
public class TestRecordController {
	//시험 응시 목록 조회 - 교수가 학생의 응시 기록을 조회
	@GetMapping()
	public String selectList(@PathVariable()int courCd) {
			
		return "testRecord/testRecordList";
	}
	//시험 응시 상세 조회 - 교수가 학생의 응시 상세 기록을 조회
	@GetMapping("{testNo}")
	public String createForm() {
		return "testRecord/testRecordDetail";
	}

	
	//응시 학생 점수 수정 채점
	@GetMapping("{attenNO}/edit")
	public String edit() {
		return "testRecord/testRecordEdit";
	}
	
	

}
