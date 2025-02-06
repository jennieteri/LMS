package kr.or.ddit.yguniv.board.answerBoard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.SystemInquiryBoardVO;

@Service
public interface AnswerBoardService {
	
	
	public List<SystemInquiryBoardVO>list();
	// 시스템 게시판 게시글 등록
		public void insert(SystemInquiryBoardVO board);
		
		//게시판 게시글 상세 조회 
		public SystemInquiryBoardVO readBoard(String sibNo);
		
		// 페이징 처리ㅇ 없는 목록 조회 
		public List<SystemInquiryBoardVO> readBoardList();
		
		//게시판 게시글 목록 조회
		public List<SystemInquiryBoardVO> readBoardList(PaginationInfo<SystemInquiryBoardVO> paginationInfo);

		//게시판 게시글 수정 
		
		public void update(SystemInquiryBoardVO board);
		
		// 공지사항 게시판 게시글 삭제 
		public void delete(String sibNo);

		//댓글 업로드 및 상태 변경 메서드 
		 public void updateReply(SystemInquiryBoardVO board);
		 
		 // 댓글수정  
		 
		 public void updateReplyNew(SystemInquiryBoardVO board);
		 // 댓글삭제 
		 public void deleteReply(String sibNo);
		 // 댓글 달기 
		 public void updateReplyAdmin(SystemInquiryBoardVO board, String answerWriter);
}
