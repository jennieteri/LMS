package kr.or.ddit.yguniv.commons.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.CommonCodeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class CommonCodeMapperTest {
	@Autowired
	CommonCodeMapper mapper;

	@Test
	void testGetCodeList() {
		List<CommonCodeVO> list = mapper.getCodeList("WEEK");
		list.forEach(vo -> log.info("체크:{}", vo));
	}

}
