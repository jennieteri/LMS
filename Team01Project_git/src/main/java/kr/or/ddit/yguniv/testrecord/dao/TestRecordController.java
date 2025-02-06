package kr.or.ddit.yguniv.testrecord.dao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface TestRecordController {
	
	public String selectTestRecordList();
	//시험 응시 상세 조회 - 교수가 학생의 응시 상세 기록을 조회
	public String selectTestRecordOne();
	public String insertTestRecord();
	public String updateTestRecord();
	public String deleteTestRecord();
	
}
