package kr.or.ddit.yguniv.attendance;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.attendance.dao.AttendanceMapper;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class AttendanceTest {
	
	@Autowired
	AttendanceMapper mapper;
	
	AttendanceVO attendan;
	
	List<AttendanceVO> attendanList;
	

	@Test
	void test() {
	    List<AttendanceVO> result = mapper.selectAttendanceByWeekAndOrder("W01", "1");
	    assertNotNull(result);
	    log.info("체크 : {}", result.toString());
	}
	
	@Test
	void test2() {
		List<AttendanceVO> result = mapper.selectAttendanceList();
		assertNotNull(result);
		log.info("체크 : {}", result.toString());
	}
	
	@Test
	void test3() {
	    // 데이터 초기화
	    attendan = new AttendanceVO();
	    attendan.setStuId("2024100001");  // 예시로 학생 ID 설정
	    attendan.setLectNo("L005");       // 예시로 강의 번호 설정
	    attendan.setWeekCd("W01");       // 예시로 주차 코드 설정
	    attendan.setLectOrder(1);        // 예시로 강의 순서를 long 타입으로 설정
	    attendan.setAtndCd("ATN1");      // 출석 코드 설정
	    
	    assertEquals(1, mapper.updateAttendance(attendan));
	    
	}



}
