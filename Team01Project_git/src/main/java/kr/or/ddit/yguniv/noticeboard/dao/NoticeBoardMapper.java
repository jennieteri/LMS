package kr.or.ddit.yguniv.noticeboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;

@Mapper
public interface NoticeBoardMapper {
	
	/**
	 * 새글등록
	 * @param board
	 * @return
	 */
	public int insertNoticeBoard(NoticeBoardVO board);
	
	/**상세조회
	 * @param ntcCd
	 * @return
	 */
	public NoticeBoardVO selectNoticeBoard(int ntcCd);
	
	/**게시글전체조회기본
	 * @return
	 */
	public List<NoticeBoardVO> selectNoticeBoardlist();
	
	/**글목록조회 페이징
	 * @param paging
	 * @return
	 */
	public List<NoticeBoardVO> selectNoticeBoardlistPaging(PaginationInfo<NoticeBoardVO> paging);
	
	/** 게시글 목록수조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<NoticeBoardVO> paging);
	
	/**게시글수정
	 * @param board
	 * @return
	 */
	public int updateNoticeBoard(NoticeBoardVO board);
	
	/**게시글삭제
	 * @param ntcCd
	 * @return
	 */
	public int deleteNoticeBoard(int ntcCd);
	
	/**조회수 증가
	 * @param ntcCd
	 * @return
	 */
	public int incrementCnt(int ntcCd);
	
	/*단체알림 발신용 학생 아이디*/
	public List<String> getStudentList();
	
}
