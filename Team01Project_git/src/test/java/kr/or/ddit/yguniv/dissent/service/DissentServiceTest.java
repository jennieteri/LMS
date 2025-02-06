package kr.or.ddit.yguniv.dissent.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;

@RootContextWebConfig
class DissentServiceTest {

	@Inject
	DissentService service;
	@Test
	void test() {
		assertDoesNotThrow(()->service.readDissentList());
	}

}
