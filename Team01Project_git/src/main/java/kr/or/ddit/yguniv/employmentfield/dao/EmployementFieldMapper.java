package kr.or.ddit.yguniv.employmentfield.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.EmploymentFieldVO;
import kr.or.ddit.yguniv.vo.JobTestVO;

@Mapper
public interface EmployementFieldMapper {
	public void insertEmployementField(EmploymentFieldVO employementfield);
	public JobTestVO selectEmployementField(String empfiCd);
	public List<JobTestVO>selectEmployementFieldList();
	public void updateEmployementField(EmploymentFieldVO employementfield);
	public void deleteEmployementField(String empfiCd);
}
