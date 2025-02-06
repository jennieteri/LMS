package kr.or.ddit.yguniv.attendance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.AttendanceVO;

@Mapper
public interface AttendanceMapper {
	
	/**
	 * 출결 기록 등록
	 * @param attendance
	 */
	public int insertAttendance(AttendanceVO attendanceVO);
	
	/**
	 * 출결 기록 상세 조회
	 * @param id
	 * @return
	 */
	public List<AttendanceVO> selectAttendance(AttendanceVO attendanceVO);
	
	/**
	 * 출결 기록 전체 리스트 조회
	 * @return
	 */
	public List<AttendanceVO> selectAttendanceList();
	
	/**
	 * 출결 전체 정보 카운트 (학생용)
	 * @return
	 */
	public List<AttendanceVO> selectListCount(@Param("lectNo") String lectNo, @Param("stuId") String stuId);
	
	/**
	 * 출결 전체 정보 카운트 
	 * @return
	 */
	public List<AttendanceVO> selectListnonstuCount(@Param("lectNo") String lectNo);
	
	/**
	 * 강의정보 리스트
	 * @return
	 */
	public List<AttendanceVO> selectLectureList();
	
	/**
	 * 출결 기록 수정
	 * @param attendance
	 */
	public int updateAttendance(AttendanceVO attendanceVO);
	
	/**
	 * 출결 기록 삭제
	 * @param id
	 */
	public int deleteAttendance(AttendanceVO attendanceVO);

	public List<AttendanceVO> selectAttendanceByWeekAndOrder(@Param("weekCd") String weekCd, @Param("lectOrder") String lectOrder);
	
}
