package kr.or.ddit.yguniv.securityList.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Service
public interface RoleService {
	
	//학생 학과 학년 
	List<StudentVO> selectStudentList(String deptCd, String gradeCd);
	
	//교수 학과 
	List<ProfessorVO> selectProfessorList(String deptNo);
	
	//교직원 직급
	List<EmployeeVO> selectEmployeeList(String empDept); 
}
