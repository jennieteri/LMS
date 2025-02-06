package kr.or.ddit.yguniv.securityList.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Mapper
public interface RoleMapper {

	List<StudentVO> selectStudentList(@Param("deptCd") String deptCd, @Param("gradeCd") String gradeCd);

	List<ProfessorVO> selectProfessorList(@Param("deptNo") String deptNo);

	List<EmployeeVO> selectEmployeeList(@Param("empDept") String empDept);

}
