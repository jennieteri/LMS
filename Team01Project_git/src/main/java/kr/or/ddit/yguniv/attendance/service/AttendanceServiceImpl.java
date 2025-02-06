package kr.or.ddit.yguniv.attendance.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.attendance.dao.AttendanceMapper;
import kr.or.ddit.yguniv.vo.AttendanceVO;

@Service
public class AttendanceServiceImpl {
	
	@Autowired
	private AttendanceMapper mapper;
	
	/**
	 * R-한 학생의 출결조회
	 * @param stuId
	 * @return
	 */
	public List<AttendanceVO> selectAttendance(AttendanceVO attendanceVO) {
		return mapper.selectAttendance(attendanceVO);
	}
	
	/**
	 * R-주차별 강의 출결 조회
	 * @param lectNm
	 * @param weekCd
	 * @return
	 */
	public List<AttendanceVO> selectAttendanceList(){
		return mapper.selectAttendanceList();
	}
	
	public List<AttendanceVO> selectListCount(@Param("lectNo") String lectNo, @Param("stuId") String stuId){
		return mapper.selectListCount(lectNo, stuId);
	}
	
	public List<AttendanceVO> selectListnonstuCount(@Param("lectNo") String lectNo){
		return mapper.selectListnonstuCount(lectNo);
	}
	
	public List<AttendanceVO> selectLectureList(){
		return mapper.selectLectureList();
	}
	
	
	public boolean insertAttendance(AttendanceVO attendanceVO) {
		return mapper.insertAttendance(attendanceVO) > 0;
	}
	
	public boolean updateAttendance(AttendanceVO attendanceVO) {
		return mapper.updateAttendance(attendanceVO) > 0;
	}
	
	public boolean deleteAttendance(AttendanceVO attendanceVO) {
		return mapper.deleteAttendance(attendanceVO) > 0;
	}

	// 주차와 차수에 맞는 출결 데이터를 조회
    // 예를 들어, 데이터베이스에서 해당 주차와 차수의 출결 기록을 가져오는 로직
	/**
     * 주차 코드와 강의 차수에 맞는 출결 데이터를 조회
     * @param weekCd : 주차 코드
     * @param lectOrder : 강의 차수
     * @return : 출결 정보 리스트
     */
	public List<AttendanceVO> findAttendanceByWeekAndOrder(String weekCd, String lectOrder) {
		return mapper.selectAttendanceByWeekAndOrder(weekCd, lectOrder);
    }
	
}
