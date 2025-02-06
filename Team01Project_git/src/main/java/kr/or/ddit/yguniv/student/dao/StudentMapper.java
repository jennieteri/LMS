package kr.or.ddit.yguniv.student.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.vo.AcademicProbationVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Mapper
public interface StudentMapper {
	/**
	 * 학생 전체 레코드
	 */
	public int selectTotalRecord();
	/**
	 * 학생 목록 페이징 레코드 조회
	 */
	public int selectPagingTotalRecord(DataTablesPaging<StudentVO> paging);
	/**
	 * 학생 목록 조회
	 */
	public List<StudentVO> studentList(DataTablesPaging<StudentVO> paging);
	/**
	 * 학생 상세 조회
	 */
	public StudentVO selectStudent(@Param("stuId") String stuId);
	/**
	 * 학생 학사경고 부여
	 */
	public int insertAcademicProbation(AcademicProbationVO academicProbationVO);
	/**
	 * 학생 제적 처리
	 */
	public void expulsion(@Param("stuId") String stuId);
	
	public int insertImg(@Param("proflPhoto") String proflPhoto);
	
	
	/**
	 * 학생 리스트 조회 
	 * @return
	 */
	public List<StudentVO> selectStudentList();
}
