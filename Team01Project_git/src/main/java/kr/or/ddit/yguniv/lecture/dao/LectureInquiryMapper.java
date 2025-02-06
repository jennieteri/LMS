package kr.or.ddit.yguniv.lecture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.LectureInquiryBoardVO;

@Mapper
public interface LectureInquiryMapper {
	/**
	 * R-강의문의게시판 총 게시글 갯수 조회
	 */
	public int selectTotalRecord(@Param("lectNo") String lectNo, @Param("paging") PaginationInfo<LectureInquiryBoardVO> paging);
	/**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	 * R-강의문의게시판 게시글 목록 조회
	 */
	public List<LectureInquiryBoardVO> selectLectureInquiryBoardList(@Param("lectNo") String lectNo, @Param("paging") PaginationInfo<LectureInquiryBoardVO> paging);
	/**
	 * R-강의문의게시판 게시글 상세조회
	 */
	public LectureInquiryBoardVO selectLectureInquiryBoard(@Param("libNo") Long libNo);
	/**
	 * U-게시글 조회수 증가
	 */
	public int incrementHit(@Param("libNo") Long libNo);
	/**
	 * C-강의문의게시판 게시글 추가
	 */
	public int insertLectureInquiryBoard(LectureInquiryBoardVO lectureInquiryBoardVO);
	/**
	 * U-강의문의게시판 게시글 수정
	 */
	public int updateLectureInquiryBoard(LectureInquiryBoardVO lectureInquiryBoardVO);
	/**
	 * D-강의문의게시판 게시글 삭제
	 */
	public int deleteLectureInquiryBoard(@Param("libNo") Long libNo);
	/**
	 * U-강의문의게시판 게시글 답변 추가
	 */
	public int insertLectureInquiryBoardAnswer(LectureInquiryBoardVO lectureInquiryBoardVO);
	/**
	 * U-강의문의게시판 게시글 답변 수정
	 */
	public int updateLectureInquiryBoardAnswer(LectureInquiryBoardVO lectureInquiryBoardVO);
	/**
	 * U-강의문의게시판 게시글 답변 삭제
	 */
	public int deleteLectureInquiryBoardAnswer(@Param("libNo") Long libNo);
}
