package kr.or.ddit.yguniv.lecture.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AttendanceVO;

@Mapper
public interface AttendanceTimerMapper {
	/**
	 * R-학생의 이전 수업 시청시간 조회
	 */
	public AttendanceVO selectViewTime(AttendanceVO attendanceVO);
	/**
	 * C-학생의 수업 시청시간 기록이 없을시 추가 있을시 수정
	 */
	public int mergeIntoViewTime(AttendanceVO attendanceVO);
}
