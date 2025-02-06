package kr.or.ddit.yguniv.lecture.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.lecture.dao.LectureInquiryMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.LectureInquiryBoardVO;

@Service
public class LectureInquiryServiceImpl {
	@Autowired
	private LectureInquiryMapper mapper;
	
	/**
	 * R-강의문의게시판 게시글 목록 조회
	 */
	public List<LectureInquiryBoardVO> selectLectureInquiryBoardList(
		String lectNo
		, PaginationInfo<LectureInquiryBoardVO> paging
	){
		paging.setTotalRecord(mapper.selectTotalRecord(lectNo, paging));
		return mapper.selectLectureInquiryBoardList(lectNo, paging);
	}
	/**
	 * R-강의문의게시판 게시글 상세조회
	 */
	public LectureInquiryBoardVO selectLectureInquiryBoard(Long libNo) {
		mapper.incrementHit(libNo);
		return mapper.selectLectureInquiryBoard(libNo);
	}
	/**
	 * C-강의문의게시판 게시글 추가
	 */
	public boolean insertLectureInquiryBoard(LectureInquiryBoardVO lectureInquiryBoardVO) {
		return mapper.insertLectureInquiryBoard(lectureInquiryBoardVO)>0;
	}
	/**
	 * U-강의문의게시판 게시글 수정
	 */
	public boolean updateLectureInquiryBoard(LectureInquiryBoardVO lectureInquiryBoardVO) {
		return mapper.updateLectureInquiryBoard(lectureInquiryBoardVO)>0;
	}
	/**
	 * D-강의문의게시판 게시글 삭제
	 */
	public boolean deleteLectureInquiryBoard(Long libNo) {
		return mapper.deleteLectureInquiryBoard(libNo)>0;
	}
	/**
	 * U-강의문의게시판 게시글 답변 추가
	 */
	public boolean insertLectureInquiryBoardAnswer(LectureInquiryBoardVO lectureInquiryBoardVO) {
		return mapper.insertLectureInquiryBoardAnswer(lectureInquiryBoardVO)>0;
	}
	/**
	 * U-강의문의게시판 게시글 답변 삭제
	 */
	public boolean deleteLectureInquiryBoardAnswer(Long libNo) {
		return mapper.deleteLectureInquiryBoardAnswer(libNo)>0;
	}
}
