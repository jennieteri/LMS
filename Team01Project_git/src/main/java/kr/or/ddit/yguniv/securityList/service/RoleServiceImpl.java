package kr.or.ddit.yguniv.securityList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.securityList.dao.RoleMapper;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	
	private final RoleMapper mapper; 
	
	
	@Override
	public List<StudentVO> selectStudentList(String deptCd, String gradeCd) {
		
		return mapper.selectStudentList(deptCd,gradeCd );
	}

	@Override
	public List<ProfessorVO> selectProfessorList(String deptNo) {
		
		return mapper.selectProfessorList(deptNo);
	}

	@Override
	public List<EmployeeVO> selectEmployeeList(String empDept) {
	
		return mapper.selectEmployeeList(empDept);
	}

}
