package kr.or.ddit.yguniv.jobtestresult.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import kr.or.ddit.yguniv.vo.JobTestVO;

@Mapper
public interface JobTestResultMapper {
	/**
	 * 직업선호도검사결과조회
	 * @param stuId
	 * @return
	 */
	public JobTestResultVO selectjobTestResult(String stuId);
	/** 검색기능
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<JobTestResultVO> paging);
	/**
	 * 교직원이 보는 학생 전체 직업선호도검사결과지
	 * @param stuId
	 * @return
	 */
	public List<JobTestResultVO>selectjobTestResultListByEmp(PaginationInfo<JobTestResultVO>paging);
}