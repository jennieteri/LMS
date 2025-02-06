package kr.or.ddit.yguniv.jobtest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import kr.or.ddit.yguniv.vo.JobTestVO;

@Mapper
public interface JobTestMapper {
	
	/** 검색기능
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<JobTestVO> paging);
	/**
	 * 직업선호도검사 추가
	 * @param jobtest
	 */
	public int insertjobTest(JobTestVO jobtest);
	/**
	 * 직업선호도검사 추가
	 * @param jobtest
	 */
	public void insertJobTestResult(JobTestResultVO jobtestresult);
	/**
	 * 하루에 한번 중복 검사
	 * @param stuId
	 * @return
	 */
	public int checkTestToday(String stuId);
	/**
	 * 직업선호도 검사 결과 업데이트 하루에 한번만 할 수 있으니까 다음날 응시할 떄 update+insert 처리하는 로직
	 * @param jobTestResult
	 */
	public void updateJobTestResult(String stuId);
	/**
	 * 직업선호도검사 상세조회
	 * @param jobtestNo
	 * @return
	 */
	public JobTestVO selectjobTest(String jobtestNo);
	/**
	 * 직업선호도검사 번호별 문제 상세조회
	 * @param jobtestNo
	 * @return
	 */
	public JobTestVO selectJobTestListByNo(String jobtestNo);
	/**
	 * 직업선호도검사결과조회
	 * @param stuId
	 * @return
	 */
	public JobTestResultVO selectjobTestResult(String stuId);
	/**
	 * 직업선호도검사 유형 가져오기
	 * @param jobtestNo
	 * @return
	 */
	public List<JobTestVO> getJobTestByNo(String jobtestNo);
	/**
	 * 교직원이 보는 학생 전체 직업선호도검사결과지
	 * @param stuId
	 * @return
	 */
	public List<JobTestResultVO>selectjobTestResultListByEmp(PaginationInfo<JobTestResultVO>paging);
	/**
	 * 직업선호도검사 전체 리스트 조회
	 * @return
	 */
	public List<JobTestVO>selectjobTestList();
	/**
	 * 직업선호도검사 교직원 페이징
	 * @return
	 */
	public List<JobTestVO>selectjobTestListEmp(PaginationInfo<JobTestVO>paging);
	/**
	 * 직업선호도검사 수정
	 * @param jobtest
	 */
	public int updateJobTest(JobTestVO jobtest);
	/**
	 * 직업선호도 검사 삭제
	 * @param jobtestNo
	 */
	public int deletejobTest(String jobtestNo);
}
