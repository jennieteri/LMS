package kr.or.ddit.yguniv.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.EmployeeVO;

@Mapper
public interface EmployeeMapper {
	
	
	public List<EmployeeVO> selectEmployeeList();


}
