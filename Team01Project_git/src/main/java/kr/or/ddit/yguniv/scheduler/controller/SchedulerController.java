package kr.or.ddit.yguniv.scheduler.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.or.ddit.yguniv.scheduler.service.SchedulerService;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerController  {
	
//	private final SchedulerService service;

//	@Scheduled(fixedRateString = "5000")//5초
//	@Scheduled(cron = "0 0/1 * 1/1 * ?") //1분
	public void SchedulerTest() {
//		String id = "2024100001";
//		
//		PersonVO pVo = service.selectPersonData(id);
		
//		log.info("스케쥴러로 가져온 pVo 데이터 {}",pVo);
		
		
	}
	
}

//@Scheduled(fixedRateString = "5000")//5초
//public void SchedulerTest() {
//	String testStr = "스케쥴러 테스트용 문자";
//	log.info("{}",testStr);
//}
