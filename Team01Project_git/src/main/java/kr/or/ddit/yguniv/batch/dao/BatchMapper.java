package kr.or.ddit.yguniv.batch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Mapper
public interface BatchMapper {

	//----------수강신청--------------------
	//수강신청 테이블 전체
	public List<LectureCartVO> selectCartList();
	
	//attendee 테이블에 추가
	public int insertAttendee(AttendeeVO aVo);

	//성공하면 카트 다 삭제
	public int deleteCart();
	//------------------------------------
	
	//----------학생 휴면계정--------------------
	//학생 계정 가져오기
	public List<StudentVO> selectStudentList();
	
	//휴면일자 업데이트
	public int updateStudentDormant(StudentVO sVo);
	
	//------------------------------------
	
	
	
}
