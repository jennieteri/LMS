package kr.or.ddit.yguniv.board.systemBoard.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
//@Transactional // 롤백 
class SystemBoardMapperTest {
	
	@Autowired
	SystemBoardMapper mapper;

	SystemNoticeBoardVO board;

	
	
	@Test
	void testInsertSystemBoard() {
		board = new SystemNoticeBoardVO();
		board.setSnbNo("1");
		board.setAdminId("관리자");
		board.setSnbTtl("수정");
		board.setSnbCn("수정");
		assertEquals(1, mapper.insertSystemBoard(board));
	}

	@Test
	void testSelectSystemBoard() {
		assertNotNull( mapper.selectSystemBoard("19") );
		//	BuyerVO buyer = mapper.selectBuyer("P10101");
		//log.info("조회된 결과 : {}", buyer);

		//assertNotNull(mapper.selectSystemBoard(board.getSnbNo()));
	}

	@Test
	void testSnbCount() {
		assertEquals(1, mapper.snbCount(board.getSnbNo()));
	}

	@Test
	void testSelectList() {

		PaginationInfo paging = new PaginationInfo();
		paging.setCurrentPage(1);
		SimpleCondition simpleCondition = new SimpleCondition();
		simpleCondition.setSearchWord("수정");
		simpleCondition.setSearchType("수정");
		paging.setSimpleCondition(simpleCondition);
		assertDoesNotThrow(()->mapper.selectList(paging));
	
	}


	@Test
	void testUpdateSystemBoard() {
		
		SystemNoticeBoardVO board = mapper.selectSystemBoard("19");
		assertNotNull(board, "게시글 존재하지 않습니다.");
		
		//board.setSnbNo(board.getSnbNo());
		board.setSnbTtl("신유정이 쓴 글");
		board.setSnbCn("신유정 언니 일므 수정");
		
		 mapper.updateSystemBoard(board);
	}

	@Test
	void testDeleteSystemBoard() {
		//assertEquals(1, mapper.deleteSystemBoard(board.getSnbNo()));
		SystemNoticeBoardVO board = mapper.selectSystemBoard("19");
		
		String snbNo= "19"; //삭제할 게시글 번호 
		
		int result = mapper.deleteSystemBoard(snbNo);
		
		assertEquals(1, result, "삭제가 실패했습니다..");	
		
	}

}
