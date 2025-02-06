package kr.or.ddit.yguniv.attendeetest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.ExaminationRecordVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.QuestionAnswerVO;
import kr.or.ddit.yguniv.vo.TestVO;

@Mapper
public interface AttendeeTestMapper {
	
	// 시험번호에 맞는 시험 문제와 문항 가져오기
	//제출을 하면 응시기록 추가하기
	//점수도 여기서 채점해서 내보내줘야하나
	
	/** 
	 * @return
	 */
	public List<TestVO> getTestPage(@Param("lectNo") String lectNo);
	public TestVO getOneTestData(@Param("lectNo") String lectNo,@Param("testSe") String testSe);

	public List<TestVO> getDummyTestPage(@Param("lectNo")String lectNo);
	public List<LectureEvaluationStandardVO> getLectureEvaluationStandardList(@Param("lectNo")String lectNo);

	
	public List<TestVO> checkLongStringQuestion(@Param("lectNo") String lectNo);
	/** 
	 * @return
	 */
	public TestVO getTestOne(@Param("testNo") String testNo);
	/** 학생의 응시기록을 생성
	 * @return 응시 기록 생성 완료 1 / null
	 */
	public Integer createRecord(@Param("testNo") String testNo,@Param("stuId") String stuId,@Param("lectNo") String lectNo);
	
	public String checkRecord(@Param("testNo") String testNo,@Param("stuId") String stuId);
	/** 학생이 등록한 답과 교수의 답을 체크하고 점수하는 기능
	 * @return 완료 1 / 실패 null
	 */
	public Integer attendeeScoreUpdate();
	/** 학생이 등록한 답과 db에 등록된 값을 비교하는 기능
	 * @return 완료 1 / 실패 null
	 */
	public Integer questionScoreCheck(@Param("testNo") String testNo);
	
	public ExaminationRecordVO getExaminationRecord(@Param("testNo") String testNo,@Param("stuId") String stuId,@Param("lectNo") String lectNo);

	public Integer updateExaminationRecord(ExaminationRecordVO examVo);
	
	public Integer createQuestionAnswer(QuestionAnswerVO qaVo);

	public List<TestVO> getProfessorTestPage(@Param("testNo") String testNo);
	
	public Integer professorUpdateAttendeeScore(QuestionAnswerVO qVo);

	public String getQuestionScore(@Param("testNo") String testNo, @Param("queNo")String queNo);
	
	
}
