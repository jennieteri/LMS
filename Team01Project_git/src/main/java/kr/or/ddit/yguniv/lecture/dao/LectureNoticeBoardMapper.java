package kr.or.ddit.yguniv.lecture.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.LectureNoticeBoardVO;

@Mapper
public interface LectureNoticeBoardMapper {
	
	/**
	 * 강의 공지 게시판 등록
	 * @param lectureNoticeBoard
	 */
	public int insertLectureNoticeBoard(LectureNoticeBoardVO lectNoticeVO);
	
	/**
	 * 강의 공지 게시판 상세 조회
	 * @param cnbNo
	 * @return
	 */
	public LectureNoticeBoardVO selectLectureNoticeBoard(String cnbNo);
	
	/**
	 * 강의 공지 게시판 전체 리스트 조회
	 * @return
	 */
	public List<LectureNoticeBoardVO> selectLectureNoticeBoardList(
			@Param("lectNoticeVO") LectureNoticeBoardVO lectNoticeVO
			, @Param("paging") PaginationInfo<LectureNoticeBoardVO> paging
	);
	
	public List<LectureNoticeBoardVO> selectLectureNoticeBoardMainList(
			@Param("lectNoticeVO") LectureNoticeBoardVO lectNoticeVO);

	public int mainBoardCount(String lectNo);
	
	/**
	 * 강의 공지 게시판 수정
	 * @param lectureNoticeBoard
	 */
	public int updateLectureNoticeBoard(LectureNoticeBoardVO lectNoticeVO);
	
	/**
	 * 강의 공지 게시판 삭제
	 * @param cnbNo
	 */
	public int deleteLectureNoticeBoard(String cnbNo);
	
	/** 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	
	/** 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn);
	
	/**
	 * 페이지 조회시, 조회수가 늘어나는 작업
	 * @param cnbNo
	 * @return
	 */
	public int updateCnbInq(String cnbNo);
	
	public int selectTotalRecord(
			@Param("lectNoticeVO") LectureNoticeBoardVO lectNoticeVO
			, @Param("paging") PaginationInfo<LectureNoticeBoardVO> paging
	);

	
	
	
	
	
	
	
	
	
	
}
