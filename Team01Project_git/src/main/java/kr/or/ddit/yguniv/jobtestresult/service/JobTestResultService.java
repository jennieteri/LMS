package kr.or.ddit.yguniv.jobtestresult.service;

import java.util.List;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobTestResultVO;

public interface JobTestResultService {
	/**
	 * 직업선호도검사결과조회
	 * @param stuId
	 * @return
	 */
	public JobTestResultVO selectjobTestResult(String stuId);
	/**
	 * 교직원이 보는 학생 전체 직업선호도검사결과지
	 * @param stuId
	 * @return
	 */
	public List<JobTestResultVO>selectjobTestResultListByEmp(PaginationInfo<JobTestResultVO>paging);
}
