package kr.or.ddit.yguniv.dissent.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class DissentMapperTest {

	@Inject
	DissentMapper dao;
	
	
	void init() {
		assertDoesNotThrow(()->{
			dao.selectDissentOne("2024100001","L003");
		});
	}
	
	
	void test2() {
	AttendeeVO aVo = new AttendeeVO();
	aVo.setAttenAtndScore((double)55);
	aVo.setAssigScore((double)90);
//	aVo.setTestScore((double)88);
	aVo.setLectNo("L003");
	aVo.setStuId("2024100001");
	
	ScoreFormalObjectionVO sVo = new ScoreFormalObjectionVO();
	sVo.setAnswerCn("zzzz등록zzzz");
	sVo.setLectNo("L003");
	sVo.setStuId("2024100001");
		assertDoesNotThrow(()->{
			dao.updateAttendee(aVo);
			dao.updateDissent(sVo);
		});
	}

	
	void test3() {
		assertDoesNotThrow(()->{
//			dao.selectAttenLectList();
		});
		
//		List<ScoreFormalObjectionVO> sVo = dao.selectAttenLectList();
		
//		log.info("{}",sVo);
		
	}
//	void test() {
//		assertDoesNotThrow(()->{
//			dao.selectProfeDissentList();
//		});
//		log.info("{}",dao.selectProfeDissentList());
//	}
	
}
