package kr.or.ddit.yguniv.jobtest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.jobtest.dao.JobTestMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import kr.or.ddit.yguniv.vo.JobTestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class JobTestServiceImpl implements JobTestService {
	private final JobTestMapper mapper;

	//직업 선호도 평가 문제 추가
	@Override
	public ServiceResult insertjobTest(JobTestVO jobtest) {
		int result = mapper.insertjobTest(jobtest);

        if (result > 0) {
            return ServiceResult.OK;
        } else {
            return ServiceResult.FAIL; 
        }

	}

	@Override
	public JobTestVO selectjobTest(String jobtestNo) {
		// TODO Auto-generated method stub
		return null;
	}
	public JobTestResultVO selectjobTestResult(String stuId) {
		return mapper.selectjobTestResult(stuId);
	}

	@Override
	public List<JobTestVO> selectjobTestList() {
		List<JobTestVO>jobTestList = mapper.selectjobTestList();
		return jobTestList;
	}
	
	//직업선호도검사 문제 수정
	@Override
	public ServiceResult updateJobTest(JobTestVO jobtest) {
		int result = mapper.updateJobTest(jobtest);

        if (result > 0) {
            return ServiceResult.OK;
        } else {
            return ServiceResult.FAIL; // 수정 실패 시 FAIL 반환
        }
    }
		
	

	//직업선호도 검사 문제 삭제
	@Override
	public ServiceResult deletejobTest(String jobtestNo) {
		int result =  mapper.deletejobTest(jobtestNo);
		   if (result > 0) {
	            return ServiceResult.OK;
	        } else {
	            return ServiceResult.FAIL; // 수정 실패 시 FAIL 반환
	        }

	}

	@Override
	public void insertJobTestResult(JobTestResultVO jobtestresult) {
		mapper.insertJobTestResult(jobtestresult);
		
	}

	@Override
	public List<JobTestVO> getJobTestByNo(String jobtestNo) {
		List<JobTestVO> test=mapper.getJobTestByNo(jobtestNo);
		return test;
	}

	@Override
	public boolean checkTestToday(String stuId) {
		int count = mapper.checkTestToday(stuId);
		return count>0;
	}
	
	@Override
	@Transactional
	public void saveOrUpdateJobTestResult(JobTestResultVO jobTestResult) {
	    // 기존 데이터 삭제
	    mapper.updateJobTestResult(jobTestResult.getStuId());
	    
	    // 새로운 데이터 삽입
	    mapper.insertJobTestResult(jobTestResult);
	}



	@Override
	public int selectTotalRecord(PaginationInfo<JobTestVO> paging) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<JobTestVO> selectjobTestListEmp(PaginationInfo<JobTestVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<JobTestVO> testList = mapper.selectjobTestListEmp(paging);
		return testList;
	}
	
	@Override
	public JobTestVO selectJobTestListByNo(String jobtestNo) {
		return mapper.selectJobTestListByNo(jobtestNo);
	}

	@Override
	public void updateJobTestResult(String stuId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JobTestResultVO> selectjobTestResultListByEmp(PaginationInfo<JobTestResultVO> paging) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
