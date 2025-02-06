package kr.or.ddit.yguniv.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AnswerChoiceVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.TestVO;

/**
 * 교수 및 교직원 용 TEST person student
 */
@Mapper
public interface TestMapper {
	
	//시험
	/** [교직원] 등록된 시험 리스트가 출력된다. [교수] 등록한 시험 리스트
	 * @return List<TestVO>
	 */
	public List<TestVO> selectTestList(@Param("id") String id,@Param("paging") PaginationInfo<TestVO> paging);
	public List<TestVO> selectTestOneList(@Param("paging") String id);
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
	public Integer updateTest(@Param("testVO")TestVO testVo);
	/**
	 * @return
	 */
	public Integer deleteTest(String  testNo);
	
	public Integer deleteQuestion(String testNo);
	
	public Integer deleteAnswer(String testNo);
	
	public Integer getQuestion(String testNo);
	
	public Integer getAnswer(String testNo);
	
	
	public String getProfeId(@Param("testNo")String testNo);
	
	public Integer insertQuestion(QuestionVO queVo);

	public Integer insertAnswerChoice(AnswerChoiceVO ansVo);
	
	public Integer insertQuestionAndQuestionAnswer(List<QuestionVO> queVoList);
	
	
	public Integer checkTest(@Param("TEST_NO")String testNo,@Param("TEST_CD") String testCd);
	
}
