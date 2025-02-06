package kr.or.ddit.yguniv.board.systemBoard.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;

@Mapper
public interface SystemBoardMapper {
	
//	공지사항 게시판 게시글 등록 
	public Integer insertSystemBoard(SystemNoticeBoardVO systemNoticeBoard); 
	
	//	공지사항 게시판 게시글 상세 조회
	public SystemNoticeBoardVO selectSystemBoard(String snbNo);
	
// 조회수 카운트 다운 
	public int snbCount(String snbNo);
	
//	공지사항 게시판 게시글 목록 조회
	public List<SystemNoticeBoardVO> selectList(PaginationInfo<SystemNoticeBoardVO>paging);

	/**
	 * 글 목록 조회
	 * 
	 * @param paginationInfo
	 * @return
	 */
	public List<SystemNoticeBoardVO> selectBoardListNonPaging();
	
	
// 페이징 처리를 위한 검색 결과 레코드 수 조회  게시글 목록 수 조회 
	public int selectTotalRecord(PaginationInfo<SystemNoticeBoardVO> paging); 
	
	
//	공지사항 게시판 게시글 수정
	public int updateSystemBoard(SystemNoticeBoardVO systemNoticeBoard);
	
//	공지사항 게시판 게시글 삭제
	public int deleteSystemBoard(String snbNo);

	
	
	/**
	 * 모듈에 넣을 List
	 * @return
	 */
	public List<SystemNoticeBoardVO> mainSystemBoardList();
	
	


}
