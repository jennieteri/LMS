package kr.or.ddit.yguniv.attendeetest.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class AttendeeTestServiceImplTest {

	@Test
	void test() {
        LocalDateTime dateTime = LocalDateTime.now();
        // 포맷 정의 (HHmm 형식)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        // 시간 부분만 포맷하여 문자열로 변환
        String time = dateTime.format(formatter);
        log.info("{}",time);
	}

}
