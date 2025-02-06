package kr.or.ddit.yguniv.jobtestresult.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.jobtestresult.dao.JobTestResultMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class JobTestResultServiceImpl implements JobTestResultService {
	private final JobTestResultMapper mapper;


	@Override
	public List<JobTestResultVO> selectjobTestResultListByEmp(PaginationInfo<JobTestResultVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<JobTestResultVO>resultList = mapper.selectjobTestResultListByEmp(paging);
		return resultList;
	}


	@Override
	public JobTestResultVO selectjobTestResult(String stuId) {
		return mapper.selectjobTestResult(stuId);
	}


}
