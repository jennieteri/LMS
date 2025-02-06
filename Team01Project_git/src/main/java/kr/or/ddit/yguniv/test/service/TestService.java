package kr.or.ddit.yguniv.test.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AnswerChoiceVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.TestVO;

public interface TestService {
	/**
	 * 시험 리스트를 조회한다
	 * @return
	 */
	public List<TestVO> selectTestList(String id,PaginationInfo<TestVO> paging);
	public List<TestVO> selectTestOneList(@Param("id") String id);
	/** [교직원] 해당 시험을 상세 조회한다
	 * @return
	 */
	public TestVO selectTestOne(String testNo);
	/** [교수] 시험을 새로 등록한다
	 * @return
	 */
	public Integer insertTest(TestVO testVo);
	/** [교직원] 등록된 시험을 {대기} 에서 {등록} 상태로 변경한다
	 * @return
	 */
	public Integer updateTest(TestVO testVo);
	/**
	 * @return
	 */
	public Integer deleteTest(String  testNo);
	
	public boolean deleteQuestion(String testNo);
	public boolean deleteAnswer(String testNo);
	public Integer deleteQuestionAndAnswer(String testNo,List<QuestionVO> queVoList);
	
	
	public String getProfeId(String testNo);
	public Integer insertQuestion(QuestionVO queVo);
	
	public Integer insertAnswerChoice(AnswerChoiceVO ansVo);
	public Integer checkTest(String testNo,String testCd);
	public Integer insertQuestionAndQuestionAnswer(List<QuestionVO> queVoList);
	
	
}
