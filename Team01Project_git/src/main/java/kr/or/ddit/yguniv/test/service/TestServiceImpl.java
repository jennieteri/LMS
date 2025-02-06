package kr.or.ddit.yguniv.test.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.test.dao.TestMapper;
import kr.or.ddit.yguniv.vo.AnswerChoiceVO;
import kr.or.ddit.yguniv.vo.QuestionAnswerVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

	@Inject
	private TestMapper dao;

	@Override
	public List<TestVO> selectTestList(@Param("id") String id, @Param("paging") PaginationInfo<TestVO> paging) {
		
		return dao.selectTestList(id,paging);
	}
	public List<TestVO> selectTestOneList(@Param("id") String id){
		
		return dao.selectTestOneList(id);
	}

	@Override
	public TestVO selectTestOne(String testNo) {
		
		return dao.selectTestOne(testNo);
	}

	//시험을 업데이트 하면 문제를 지웠다가 다시 등록해야함
	@Override
	public Integer updateTest(TestVO testVo) {
		testVo = dateAndTimeFormat(testVo);

		return dao.updateTest(testVo);
	}

	@Transactional
	@Override
	public Integer deleteQuestionAndAnswer(String testNo,List<QuestionVO> queVoList) {
		
		boolean ans = deleteAnswer(testNo);

		boolean que = deleteQuestion(testNo);
		
		Integer res = 0;
		if(ans&&que) {
			res = insertQuestionAndQuestionAnswer(queVoList);
		}
		
		return res;
	}
	
	@Override
	public boolean deleteQuestion(String testNo) {
		boolean tf = true;
		Integer getQue = dao.getQuestion(testNo);
		int delQue = dao.deleteQuestion(testNo);
		
		if(getQue!=delQue) {
			tf=false;
		}
		
		return tf;
	}
	public boolean deleteAnswer(String testNo) {
		boolean tf = true;
		Integer getAns = dao.getAnswer(testNo);
		int delAns = dao.deleteAnswer(testNo);
		
		if(getAns!=delAns) {
			tf=false;
		}
		
		return tf;
	}
	
	@Override
	public Integer deleteTest(String testNo) {
		boolean ans = deleteAnswer(testNo);

		boolean que = deleteQuestion(testNo);
		
		Integer res = 0;
		if(ans&&que) {
			res = dao.deleteTest(testNo);
		}
		
		return res;
	}
	@Override
	public String getProfeId(String testNo) {
		
		return dao.getProfeId(testNo);
	}

	/* 등록 반려용 */
	@Override
	public Integer checkTest(String testNo, String testCd) {

		return dao.checkTest(testNo, testCd);
	}

	@Override
	public Integer insertTest(TestVO testVo) {

		testVo = dateAndTimeFormat(testVo);
//		Integer testNo = dao.insertTest(testVo);
		return dao.insertTest(testVo);
	}

	// 2024-10-10 => 20241010 / 09:00 => 0900
	public TestVO dateAndTimeFormat(TestVO testVo) {
		testVo.setTestSchdl(testVo.getTestSchdl().replace("-", ""));
		testVo.setTestDt(testVo.getTestDt().replace(":", ""));
		testVo.setTestEt(testVo.getTestEt().replace(":", ""));

		return testVo;
	}
	
	// 여러개의 문제를 받고 문제 하나 등록 - 문항 4개 등록 - 이걸 끝날때까지 돌려야함

	@Transactional
	@Override
	public Integer insertQuestionAndQuestionAnswer(List<QuestionVO> queVoList) {
		int totalInsertedCount = 0;

		for (QuestionVO qVo : queVoList) {
			// 1. 문제 등록
			dao.insertQuestion(qVo);
			log.info("문제 등록 완료: {}", qVo);

			// 2. 문항 처리
			if ("객관식".equals(qVo.getQueType()) || "주관식".equals(qVo.getQueType()) || "서술형".equals(qVo.getQueType())) {
				List<AnswerChoiceVO> queAnsVoList = qVo.getAnswerVO();

				if (queAnsVoList != null && !queAnsVoList.isEmpty()) {
					for (AnswerChoiceVO ansVo : queAnsVoList) {
						ansVo.setQueNo(qVo.getQueNo()); // 문제 번호를 문항에 설정
						dao.insertAnswerChoice(ansVo);
						log.info("문항 등록 완료: {}", ansVo);
						totalInsertedCount++;
					}
				} else {
					log.warn("문항 리스트가 비어있습니다. 문제 번호: {}", qVo.getQueNo());
				}
			}
		}

		log.info("↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔등록ㅁ 문항 수 : {}", totalInsertedCount);
		return totalInsertedCount; // 총 등록된 문항 수 반환
	}

	@Override
	public Integer insertQuestion(QuestionVO queVo) {
		dao.insertQuestion(queVo);
		return 1;
	}

	@Override
	public Integer insertAnswerChoice(AnswerChoiceVO ansVo) {

		dao.insertAnswerChoice(ansVo);
		return 1;
	}
	

}
