package kr.or.ddit.yguniv.lecture.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.vo.LectureInquiryBoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class LectureInquiryMapperTest {
	@Autowired
	LectureInquiryMapper mapper;

	@Test
	void testSelectLectureInquiryBoardList() {
		assertDoesNotThrow(()->{
			PaginationInfo<LectureInquiryBoardVO> paging = new PaginationInfo<LectureInquiryBoardVO>();
			paging.setCurrentPage(1);
			paging.setSimpleCondition(new SimpleCondition());
			paging.setTotalRecord(mapper.selectTotalRecord("L001", paging));
			List<LectureInquiryBoardVO> result = mapper.selectLectureInquiryBoardList("L001", paging);
		});
	}

	@Test
	void testSelectLectureInquiryBoard() {
		assertDoesNotThrow(()->{
			mapper.incrementHit(1L);
			mapper.selectLectureInquiryBoard(1L);
		});
	}

	@Test
	void testInsertLectureInquiryBoard() {
		LectureInquiryBoardVO vo = mapper.selectLectureInquiryBoard(1L);
		vo.setLectNo("L001");
		vo.setLibNo(555L);
		assertEquals(1, mapper.insertLectureInquiryBoard(vo));
	}

	@Test
	void testUpdateLectureInquiryBoard() {
		LectureInquiryBoardVO vo = mapper.selectLectureInquiryBoard(1L);
		vo.setLibSj("수정깐따삐야");
		assertEquals(1, mapper.updateLectureInquiryBoard(vo));
	}

	@Test
	void testDeleteLectureInquiryBoard() {
		assertEquals(1, mapper.deleteLectureInquiryBoard(1L));
	}

	@Test
	void testInsertLectureInquiryBoardAnswer() {
		LectureInquiryBoardVO vo = new LectureInquiryBoardVO();
		vo.setProfeId("2024300001");
		vo.setLibAnsCn("답변 깐따삐야");
		vo.setLibNo(1L);
		assertEquals(1, mapper.insertLectureInquiryBoardAnswer(vo));
	}

	@Test
	void testUpdateLectureInquiryBoardAnswer() {
		LectureInquiryBoardVO vo = new LectureInquiryBoardVO();
		vo.setLibAnsCn("답변 깐따삐야");
		vo.setLibNo(1L);
		assertEquals(1, mapper.updateLectureInquiryBoardAnswer(vo));
	}

	@Test
	void testDeleteLectureInquiryBoardAnswer() {
		assertEquals(1, mapper.deleteLectureInquiryBoardAnswer(1L));
	}

}
