package kr.or.ddit.yguniv.attendeetest.service;

import java.security.Principal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.ExaminationRecordVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.QuestionAnswerVO;
import kr.or.ddit.yguniv.vo.TestVO;

public interface AttendeeTestService {
	
	public List<TestVO> getTestPage(String lectNo);
	
	public List<TestVO> checkLongStringQuestion(String testNo);

	
	public List<TestVO> getProfessorTestPage(String testNo);
	/** 학생이 등록한 답과 db에 등록된 값을 비교하는 기능
	 * @return 완료 1 / 실패 null
	 * 
	 * 
	 */
	public Integer questionScoreCheck(String testNo,List<QuestionAnswerVO> attendeeTestVOList);
	
	public Integer createRecord(String testNo,String stuId);
	
	public boolean checkRecord(String testNo,String stuId);

	public ExaminationRecordVO getExaminationRecord(String testNo, String stuId,String lectNo);
	
	public String professorUpdateAttendeeScore(QuestionAnswerVO qVo);
	
	public List<LectureEvaluationStandardVO> getLectureEvaluationStandardList(String lectNo);
//	public TestVO ();
//	public TestVO ();
}
