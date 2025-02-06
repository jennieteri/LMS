package kr.or.ddit.yguniv.board.answerBoard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.board.answerBoard.dao.AnswerBoardMapper;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.SystemInquiryBoardVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerBoardServiceImpl implements AnswerBoardService {

	private final AnswerBoardMapper mapper;

	
	@Override
	public List<SystemInquiryBoardVO> list() {
		return mapper.selectBoardListNonPaging();
	}

	@Override
	public void insert(SystemInquiryBoardVO board) {
		
		mapper.insert(board);

	}

	@Override
	public SystemInquiryBoardVO readBoard(String sibNo) {
		SystemInquiryBoardVO board = mapper.select(sibNo);
		if (board == null)
			throw new BoardException(String.format("%s 번 글이 없음.", sibNo));
		
		return board;
	}

	@Override
	public List<SystemInquiryBoardVO> readBoardList() {
		return mapper.selectBoardListNonPaging();
	}

	@Override
	public List<SystemInquiryBoardVO> readBoardList(PaginationInfo<SystemInquiryBoardVO> paginationInfo) {
paginationInfo.setTotalRecord(mapper.selectTotalRecord(paginationInfo));
		List<SystemInquiryBoardVO> boardList = mapper.list(paginationInfo);
		return boardList;
	}

	

	@Override
	public void update(SystemInquiryBoardVO board) {
		mapper.update(board);
	}

	@Override
	public void delete(String sibNo) {
		  if (sibNo == null || sibNo.isEmpty()) {
		        throw new IllegalArgumentException("게시글 번호가 없습니다.");
		    }
		  
		    int result = mapper.delete(sibNo);
		    if (result == 0) {
		        throw new BoardException("삭제할 게시글이 존재하지 않습니다.");
		    }
	}

	@Override
	public void updateReply(SystemInquiryBoardVO board) {
		 // 답변 등록
        mapper.updateReply(board);
        // 상태 코드 변경 (Y -> N)
        mapper.updateStatusToN(board.getSibNo());	

	}

	@Override
	public void updateReplyNew(SystemInquiryBoardVO board) {
		
		
		// 답변 수정 
		mapper.updateReplyNew(board);
		
		
	}

	@Override
	public void deleteReply(String sibNo) {
		mapper.deleteReply(sibNo);
		
	}

	@Override
	public void updateReplyAdmin(SystemInquiryBoardVO board, String answerWriter) {
		 // 필요한 정보를 VO에서 설정하거나, 직접 매퍼로 전달
	    Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("sibNo", board.getSibNo());
	    paramMap.put("sibAns", board.getSibAns());
	    paramMap.put("answerWriter", answerWriter); // 작성자 정보 추가

	    // 데이터베이스에 필요한 정보를 전송
	    mapper.updateReplyAdmin(paramMap);
		
	}
	
	

}
