package kr.or.ddit.yguniv.board.answerBoard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.SystemInquiryBoardVO;

@Mapper
public interface AnswerBoardMapper {
	
	// 시스템 게시판 게시글 등록
	public Integer insert(SystemInquiryBoardVO board);
	
	//게시판 게시글 상세 조회 
	public SystemInquiryBoardVO select(String sibNo);
	
	//게시판 게시글 목록 조회
	public List<SystemInquiryBoardVO> list(PaginationInfo<SystemInquiryBoardVO> paging);
	
	// 글 목록 조회 
	public List<SystemInquiryBoardVO> selectBoardListNonPaging();
	
	// 페이징 처리를 위한 검색 결과 레크도 수 조회 게시글 목록 수 조회 
	public int selectTotalRecord(PaginationInfo<SystemInquiryBoardVO> paging); 
	
	//게시판 게시글 수정 
	
	public int update(SystemInquiryBoardVO board);
	
	// 공지사항 게시판 게시글 삭제 
	
	public int delete(String sibNo);

	
	
	//댓글 업로드
	public int updateReply(SystemInquiryBoardVO board);
	
	//상태변경
	public int updateStatusToN(String sibNo);
	
	
	//댓글 수정 
	public int updateReplyNew (SystemInquiryBoardVO board);
	
	//댓글 삭제 
	public int deleteReply(String sibNo);

	public void updateReplyAdmin(Map<String, Object> paramMap);

}
