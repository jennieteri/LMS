package kr.or.ddit.yguniv.attendeetest.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.attendeetest.dao.AttendeeTestMapper;
import kr.or.ddit.yguniv.commons.exception.TestScheduleException;
import kr.or.ddit.yguniv.test.dao.TestMapper;
import kr.or.ddit.yguniv.vo.ExaminationRecordVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.QuestionAnswerVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.TestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendeeTestServiceImpl implements AttendeeTestService {

	private final AttendeeTestMapper dao;
	private final TestMapper testDao;

	@Override
	public List<TestVO> getTestPage(String lectNo) {
		// TODO Auto-generated method stub
		return dao.getTestPage(lectNo);
	}

	//평가기준 가져오기
	@Override
	public List<LectureEvaluationStandardVO> getLectureEvaluationStandardList(String lectNo){
		
		return dao.getLectureEvaluationStandardList(lectNo);
	}
	
	public TestVO getOneTestData(String lectNo,String testSe) {
		
		return dao.getOneTestData(lectNo,testSe);
	}
	
	
	
	
	@Override
	public ExaminationRecordVO getExaminationRecord(String testNo, String stuId, String lectNo) {

		return dao.getExaminationRecord(testNo, stuId, lectNo);
	}

	// 여기에 트랜잭션 어노테이션 추가하기
	@Transactional
	@Override
	public Integer questionScoreCheck(String testNo, List<QuestionAnswerVO> QuestionAnswerVOList) {

		TestVO testOneVO = testDao.selectTestOne(testNo);
		Integer score = questionCheck(testOneVO, QuestionAnswerVOList);
		String stuId = QuestionAnswerVOList.get(0).getStuId();
		// 로그인 한 계정의 아이디를 가져와야함

		String asd = testOneVO.getTestNo();
		String asd1 = QuestionAnswerVOList.get(0).getLectNo();// <<<<<<<<<<<<<<<<<<<

		ExaminationRecordVO examVo = getExaminationRecord(testOneVO.getTestNo(), stuId,
				QuestionAnswerVOList.get(0).getLectNo());
		// 여기서 응시기록도 하나 가져와야하네

		examVo.setTestScore(String.valueOf(score));

		int num = dao.updateExaminationRecord(examVo);

		return num;
	}

	// service에서 제출답안을 가지고 questionVO의 queAnswer과 QuestionAnswerVO의 queAnswer을
	// 비교해야함<---데이터명변경?
	// queAns와 attenQueAns를 비교해서 동일하면 QuestionAnswerVO.setQues_Yn('Y')로 업데이트.
	// 로직 바깥쪽에 선언한 int score = 0; 을 일치할 때 queVO의 queScore을 더함 (++)
	// 채점 로직이 끝난 후에 QuestionAnswerVO.setQuesScore(score);을 실행
	// 모든 로직이 끝난 후 examinationRecord의 testScore(총점) 업데이트 했다가 교수가 채점을 하면 그때 또 업데이
	// 저장된 시험과 학생이 입력한 test정보를 가져온다. QuestionAnswer을 가져와야하나??
	public Integer questionCheck(TestVO testOneVO, List<QuestionAnswerVO> attenQueAnsVOList) {
		List<QuestionVO> testQueList = testOneVO.getQuestionVO();

		int score = 0;
		int createQuestionAnswerResult = 0;
		for (int i = 0; i < testOneVO.getQuestionVO().size(); i++) {
			QuestionVO qVo = testOneVO.getQuestionVO().get(i);
			QuestionAnswerVO qaVo = attenQueAnsVOList.get(i);
			// String testAnswer = testOne.getQueAnswer();

			if (qVo.getQueType().equals("객관식")) {
				if (qVo.getQueAnswer().equals(qaVo.getQueAnswer())) {
					score += Integer.parseInt(qVo.getQueScore());
					qaVo.setQuesScore(qVo.getQueScore());
					qaVo.setQuesYn("Y");
				} else {
					qaVo.setQuesYn("N");
					qaVo.setQuesScore("0");
				}
			} else if (qVo.getQueType().equals("주관식")) {
				if (qVo.getQueAnswer().equals(qaVo.getQueAnswer())) {
					score += Integer.parseInt(qVo.getQueScore());
					qaVo.setQuesScore(qVo.getQueScore());
					qaVo.setQuesYn("Y");
				} else {
					qaVo.setQuesYn("N");
					qaVo.setQuesScore("0");
				}
			} else {
				qaVo.setQuesYn("N");
				qaVo.setQuesScore("0");
				score += 0; // 서술형은 그냥 패스
			}

			createQuestionAnswerResult += dao.createQuestionAnswer(qaVo);

		}

		return score;
	}
	// 학생이 홈페이지에서 [응시] 버튼을 누르면 응시기록 테이블을 생성. 만약 응시를 눌렀을 때 응시기록이 존재하면 내보내기=이중시험제거
	// 응시기록의 데이터 = 강의번호, 학번 , 시험등록번호, 응시일 => lectNo,stuId,testNo,examinDate =>insert
	// ----------------------------------------------------------------------------------
	// 학생이 시험을 친 뒤 [제출] 버튼을 누르면 제출하면 제출 답안 데이터를 controller에서 받음
	// 제출답안의 데이터 = 강의번호, 학번, 시험등록번호, 문제번호, 제출답안.

	public TestVO getTestOne(String testNo) {

		TestVO testVo = testDao.selectTestOne(testNo);

		return testVo;
	}

	@Transactional
	@Override
	public Integer createRecord(String testNo, String stuId) {

		TestVO testVo = dao.getTestOne(testNo);

		String reqTime = systemTimeNow();

		// 응시기록을 만들려면 서버시간이 시험 시간의 사이에 있어야함.
//		Integer recordCheck = 0;
		Integer create = 0;
		String result = "";

		String sysDate = systemDateNow();
		
		boolean dateCheck = dateChecker(testVo, sysDate);
		if(!dateCheck) {
			throw new TestScheduleException("시험 일자가 아닙니다.");
		}
		
		boolean timeCheck = timeChecker(testVo, reqTime);
		if(!timeCheck) {
			throw new TestScheduleException("시험 시간이 아닙니다.");
		}
		//boolean인지 integer인지 체크하기
		boolean recordCheck = checkRecord(testNo, stuId);
		if(!recordCheck) {
			throw new TestScheduleException("응시 기록이 존재 합니다.");
		}
		
		String lectNo = testVo.getLectNo();
		create = dao.createRecord(testNo, stuId, lectNo);
		
		//날짜체크 추가하기
		
		return create;
	}

	
	@Override
	public boolean checkRecord(String testNo, String stuId) {
	    String lectNo = dao.checkRecord(testNo, stuId);
	    // lectNo가 null일 경우 true 반환
	    return lectNo == null || lectNo.isEmpty();
	}
	

	public String systemTimeNow() {
		LocalDateTime dateTime = LocalDateTime.now();
		// 포맷 정의 (HHmm 형식)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		// 시간 부분만 포맷하여 문자열로 변환
		String time = dateTime.format(formatter);

		return time;
	}
	
	public String systemDateNow() {
		LocalDateTime dateTime = LocalDateTime.now();
		// 포맷 정의 (HHmm 형식)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		// 시간 부분만 포맷하여 문자열로 변환
		String date = dateTime.format(formatter);
		
		return date;
	}

	public boolean dateChecker(TestVO testVo, String date) {
		boolean tf = false;
		String startDate = testVo.getTestSchdl();
		String sysDate = systemDateNow();
		
		if (startDate.equals(sysDate)) {
			tf = true;
		}
		
		return tf;
	}
	public boolean timeChecker(TestVO testVo, String time) {
		boolean tf = false;
		String startTime = testVo.getTestDt();
		String endTime = testVo.getTestEt();

		int sTime = Integer.parseInt(startTime);
		int eTime = Integer.parseInt(endTime);
		int pTime = Integer.parseInt(time);

		if (sTime <= pTime && pTime < eTime) {
			tf = true;
		}

		return tf;
	}

//	@Transactional
//	@Override
//	public Integer createRecord(String testNo, String stuId) {
//		
//		TestVO testVo = dao.getTestOne(testNo);
//		
//		String reqTime = systemTimeNow();
//		
//		// 응시기록을 만들려면 서버시간이 시험 시간의 사이에 있어야함.
//		Integer recordCheck = 0;
//		Integer create = 0;
//		String result = "";
//		
//		
//		boolean timeCheck = timeChecker(testVo, reqTime);
//		if (timeCheck) { //시간이 시험시간 범위 내에 존재하고
//			recordCheck = checkRecord(testNo, stuId); //응시기록이 없는걸 확인하면
//			if (recordCheck == null || recordCheck == 0) {
//				String lectNo = testVo.getLectNo();
//				create = dao.createRecord(testNo, stuId, lectNo);
//				result = "";
//			} else {
//				// 세
//				result = "";
//			}
//		} else {
//			// 시험 시간이 아님
//			result = "";
//		}
//		
//		return create;
//	}

	public List<TestVO> getProfessorTestPage(String testNo) {

		return dao.getProfessorTestPage(testNo);
	}

	@Transactional
	@Override
	public String professorUpdateAttendeeScore(QuestionAnswerVO qVo) {
		ExaminationRecordVO exVo = getExaminationRecord(qVo.getTestNo(), qVo.getStuId(), qVo.getLectNo());
		// 제출답안 하나 받고 4개의 정보와 일치하는 답안 [서술형] 답안을 업데이트. 0점-> 입력점수 / N -> Y
		String result = "ok";

		if (scoreChecker(qVo)) {

			int score = Integer.parseInt(exVo.getTestScore());
			int eScore = Integer.parseInt(qVo.getQuesScore());
			String plusScore = String.valueOf(score + eScore);
			exVo.setTestScore(plusScore);
			dao.updateExaminationRecord(exVo);
			dao.professorUpdateAttendeeScore(qVo);
		} else {
			result = "no";
		}

		return result;

	}

	public boolean scoreChecker(QuestionAnswerVO qVo) {
		boolean tf = true;
		// 하나의 문제에 대한 정보를 QuestionAnswerVO 에서 문제번호와 시험번호로 QuestionVO를 가져온다.
		// 해당하는 question의 점수 배점과 사용자가 입력한 값이 범위 이외면 오류.
		int queScore = Integer.parseInt(dao.getQuestionScore(qVo.getTestNo(), qVo.getQueNo()));
		int ansScore = Integer.parseInt(qVo.getQuesScore());

		if (queScore < ansScore) {
			tf = false; // 만약 배점보다 입력 점수가 크면
		}
		return tf;
	}

	@Override
	public List<TestVO> checkLongStringQuestion(String lectNo) {
		// TODO Auto-generated method stub
		return dao.checkLongStringQuestion(lectNo);
	}

}
