package kr.or.ddit.yguniv.student.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AcademicProbationVO;
import kr.or.ddit.yguniv.vo.StudentRecordsVO;

@Mapper
public interface StudentRecordsMapper {
	/**
	 * R-학적 변동 신청 목록 페이징
	 */
	public int selectTotalRecord(PaginationInfo<StudentRecordsVO> paging);
	/**
	 * R-학적 변동 신청 목록 조회
	 */
	public List<StudentRecordsVO> selectStudentRecordsList(PaginationInfo<StudentRecordsVO> paging);
	/**
	 * R-학적 변동 신청 상세 조회
	 */
	public StudentRecordsVO selectStudentRecords(StudentRecordsVO studentRecordsVO);
	/**
	 * R-학적 변동 신청 전 이전 요청 존재 여부 확인
	 */
	public String selectPrevRequest(@Param("stuId") String stuId);
	/**
	 * R-학적 변동 신청 전 해당학기 처리된 학적정보가 있는지 확인
	 */
	public int selectPrevRequestSemester(StudentRecordsVO studentRecordsVO);
	/**
	 * C-학적 변동 신청
	 */
	public int insertStudentRecords(StudentRecordsVO studentRecordsVO);
	/**
	 * U-학적 변동 서류 취소
	 */
	public int cancelStudentRecords(StudentRecordsVO studentRecordsVO);
	/**
	 * U-학적 변동 서류 승인
	 */
	public int consentStudentRecords(StudentRecordsVO studentRecordsVO);
	/**
	 * U-학생 학적 변경
	 */
	public void updateStudentRecords(StudentRecordsVO studentRecordsVO);
	/**
	 * U-학적 변동 서류 반려
	 */
	public int returnStudentRecords(StudentRecordsVO studentRecordsVO);
}
