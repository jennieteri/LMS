package kr.or.ddit.yguniv.lecture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.WeekLecturePlanVO;

@Mapper
public interface LectureMapper {
	/**
	 * R-학생일 경우 수강중인, 교사일 경우 강의중인 강의목록 조회
	 */
	public List<LectureVO> myLectureList(@Param("id") String id);
	/**
	 * R-강의 개설 신청 목록 조회
	 */
	public List<LectureVO> selectLectureRequestList(LectureVO lectureVO);
	/**
	 * R-강의 개설 신청 상세 조회
	 */
	public LectureVO selectLectureRequest(LectureVO lectureVO);
	/** 
	 * C-강의 개설 신청 추가(LECTURE 테이블)
	 */
	public int insertLectureRequest(LectureVO lectureVO);
	/** 
	 * C-강의 개설 신청 추가(SCHEDULE 테이블)
	 */
	public int insertSchedule(LectureVO lectureVO);
	/** 
	 * C-강의 개설 신청 추가(LECTURE_EVALUATION_STANDARD 테이블)
	 */
	public int insertLectEvalStan(LectureVO lectureVO);
	/** 
	 * C-강의 개설 신청 추가(WEEK_LECTURE_PLAN 테이블)
	 */
	public int insertWeekLecturePlan(LectureVO lectureVO);
	/** 
	 * U-강의 개설 신청 수정(LECTURE 테이블)
	 */
	public int updateLectureRequest(LectureVO lectureVO);
	/** 
	 * D-강의 개설 수정, 기존 자료 삭제(SCHEDULE 테이블)
	 */
	public int deleteSchedule(LectureVO lectureVO);
	/** 
	 * D-강의 개설 수정, 기존 자료 삭제(LECTURE_EVALUATION_STANDARD 테이블)
	 */
	public int deleteLectEvalStan(LectureVO lectureVO);
	/** 
	 * D-강의 개설 수정, 기존 자료 삭제(WEEK_LECTURE_PLAN 테이블)
	 */
	public int deleteWeekLecturePlan(LectureVO lectureVO);
	/** 
	 * D-강의 개설 신청 삭제
	 */
	public int deleteLectureRequest(LectureVO lectureVO);
	/** 
	 * U-강의 상태 변경
	 */
	public int lectureStatusUpdate(LectureVO lectureVO);
	/** 
	 * C-강의 등록시 강의 자료에 주차별 계획대로 주차 등록
	 */
	public void insertLectureWeekPlan(LectureVO lectureVO);
}
