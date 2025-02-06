package kr.or.ddit.yguniv.lecture.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import kr.or.ddit.yguniv.vo.OrderLectureDataVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class LectureMaterialsMapperTest {
	@Autowired
	LectureMaterialsMapper mapper;
	
	LectureWeekVO lectureWeek;
	
	OrderLectureDataVO orderLectureData;
	
	@BeforeEach
	void beforeEach() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W16");
		lectureWeek.setLeweNm("더미강의11주차");
		assertEquals(1, mapper.insertLectureWeek(lectureWeek));
		
		orderLectureData = new OrderLectureDataVO();
		orderLectureData.setLectOrder(5L);
		orderLectureData.setWeekCd("W03");
		orderLectureData.setLectNo("L001");
		orderLectureData.setSectNm("더미강의4차수");
		orderLectureData.setSectDt("20241210");
		orderLectureData.setSectEt("20241217");
		orderLectureData.setSectIdnty("0100");
		assertEquals(1, mapper.insertOrderLectureData(orderLectureData));
	}

	@Test
	void testSelectOrderLectureDataList() {
		List<LectureWeekVO> result = mapper.selectOrderLectureDataList("L005", "2024100001");
		assertNotNull(result);
		log.info("체크 : {}", result.toString());
	}

	@Test
	void testSelectOrderLectureData() {
		orderLectureData = new OrderLectureDataVO();
		orderLectureData.setLectOrder(3L);
		orderLectureData.setLectNo("L001");
		OrderLectureDataVO result = mapper.selectOrderLectureData(orderLectureData);
		assertNotNull(result);
		log.info("체크 : {}", result.toString());
	}
	
	@Test
	void testSelectLectureWeek() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W01");
		lectureWeek.setLeweNm("더미강의 주차 등록 테스트");
		assertEquals(1, mapper.selectLectureWeek(lectureWeek));
	}
	
	@Test
	void testInsertLectureWeek() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W12");
		lectureWeek.setLeweNm("더미강의 주차 등록 테스트");
		assertEquals(1, mapper.insertLectureWeek(lectureWeek));
	}
	
	@Test
	void testUpdateLectureWeek() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W11");
		lectureWeek.setLeweNm("더미강의 주차 수정 테스트");
		assertEquals(1, mapper.updateLectureWeek(lectureWeek));
	}

	@Test
	void testDeleteLectureWeek() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W11");
		assertEquals(1, mapper.deleteLectureWeek(lectureWeek));
	}

	@Test
	void testUpdateOrderLectureData() {
		orderLectureData = new OrderLectureDataVO();
		orderLectureData.setOriginLectOrder(5L);
		orderLectureData.setLectOrder(6L);
		orderLectureData.setWeekCd("W02");
		orderLectureData.setLectNo("L001");
		orderLectureData.setSectNm("더미강의3차수 수정 테스트");
		orderLectureData.setSectDt("20241215");
		orderLectureData.setSectEt("20241218");
		orderLectureData.setSectIdnty("0200");
		assertEquals(1, mapper.updateOrderLectureData(orderLectureData));
	}

	@Test
	void testDeleteOrderLectureData() {
		orderLectureData = new OrderLectureDataVO();
		orderLectureData.setWeekCd("W02");
		orderLectureData.setLectNo("L001");
		assertEquals(2, mapper.deleteOrderLectureData(orderLectureData));
	}

}
