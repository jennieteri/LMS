package kr.or.ddit.yguniv.commons.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.ClassRoomVO;
import kr.or.ddit.yguniv.vo.CommonCodeVO;
import kr.or.ddit.yguniv.vo.DepartmentVO;
import kr.or.ddit.yguniv.vo.EducationTimeTableCodeVO;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.SemesterVO;
import kr.or.ddit.yguniv.vo.SubjectVO;

@Mapper
public interface CommonCodeMapper {
	/**
	 * R-부모 코드에 해당하는 코드 리스트
	 */
	public List<CommonCodeVO> getCodeList(@Param("parCocoCd") String parCocoCd);
	/**
	 * R-강의실 목록
	 */
	public List<ClassRoomVO> getClassRoomList();
	/**
	 * R-학기 목록
	 */
	public List<SemesterVO> getSemesterList(@Param("id") String id);
	/**
	 * R-과목 목록
	 */
	public List<SubjectVO> getSubjectList();
	/**
	 * R-시간표 목록
	 */
	public List<EducationTimeTableCodeVO> getEducationTimeTableCodeList();
	/**
	 * R-학과 목록
	 */
	public List<DepartmentVO> getDepartmentList();
	
	/**
	 * 교직원 부서 
	 */
	public List<EmployeeVO> getEmployeeDeptList();
}
