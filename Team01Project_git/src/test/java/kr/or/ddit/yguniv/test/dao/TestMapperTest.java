package kr.or.ddit.yguniv.test.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class TestMapperTest {

	@Inject
	TestMapper dao;
	
	@Test
	void test() {
	TestVO dt =	new TestVO();
	
	dt.setLectNo("L004");
	dt.setTestSe("PR");
	dt.setTestOnlineYn("N");
	dt.setTestSchdl("20241123");
	dt.setTestDt("1500");
	dt.setTestEt("1630");
	dt.setCroomCd("CR002");
	
	log.info("{}",dt);
		assertDoesNotThrow(()->{
			dao.insertTest(dt);			
		});
//			assertNull(
//				dao.insertTest(dt)			
//			);
	}

}
