package kr.or.ddit.yguniv.attendcoeva.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.attendcoeva.dao.AttendCoevaMapper;
import kr.or.ddit.yguniv.commons.exception.TestScheduleException;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.SemesterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendCoevaServiceImpl implements AttendCoevaService {

	private final AttendCoevaMapper dao;
	
	//myPage 용도
	public List<AttendeeVO> selectMapageList(String stuId){
		return dao.selectMapageList(stuId);
	} 
	
	@Override
	public List<SemesterVO> selectAttenSemesterList(String stuId) {
		// TODO Auto-generated method stub
		
		return dao.selectAttenSemesterList(stuId);
	}
	

	@Override
	public List<SemesterVO> selectProfeSemesterList(String profeId) {
		// TODO Auto-generated method stub
		return dao.selectProfeSemesterList(profeId);
	}
	
	@Override
	public List<AttendeeVO> selectCoevaList(String stuId, String semNo) {
		// TODO Auto-generated method stub
		return dao.selectCoevaList(stuId, semNo);
	}

	@Override
	public AttendeeVO selectCoevaDetail(String stuId, String lectNo, String semstrNo) {
		// TODO Auto-generated method stub
		return dao.selectCoevaDetail(stuId, lectNo, semstrNo);
	}
	
	@Override
	public AttendeeVO selectCoevaPaper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertCoeva(AttendeeVO aVo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Transactional
	@Override
	public int updateCoeva(AttendeeVO aVo) {
		// TODO Auto-generated method stub
		int test = updateAttendeeScore(aVo);
		
		if(test==0) {
			throw new TestScheduleException("등록 실패");
		}
		int result = dao.updateCoeva(aVo);
		
		return result;
	}

	//이 updateCoeva가 등록이 완료되면 해당 학생의 해당 과목의 성적이 등록되어야함.
	
	@Override
	public int updateAttendeeScore(AttendeeVO paVo) {
	    // 학생의 성적 정보 가져오기
	    AttendeeVO aVo = dao.selectCoevaOne(paVo.getStuId(), paVo.getLectNo());

	    // 강의 평가 기준 가져오기
	    List<LectureEvaluationStandardVO> lesVoList = dao.selectLectureEvaluationStandardOne(paVo.getLectNo());

	    // 성적 계산
	    double finalScore = calculateFinalScore(aVo, lesVoList);

	    // 소숫점 한자릿수로 자르기
	    finalScore = Math.round(finalScore * 10) / 10.0;

	    // 계산된 성적 업데이트
	    aVo.setAttenScore(finalScore);
	    int res = dao.updateAttenScore(aVo);
	  
	    return res;
	}

	
	
	
	
	public double calculateFinalScore(AttendeeVO aVo, List<LectureEvaluationStandardVO> lesVoList) {
	    // 비율을 저장할 Map 생성
	    Map<String, Double> rateMap = new HashMap<>();
	    for (LectureEvaluationStandardVO lesVo : lesVoList) {
	        if (lesVo.getEvlStdrCd() != null && lesVo.getRate() != null) {
	            rateMap.put(lesVo.getEvlStdrCd(), lesVo.getRate() / 100.0); // 0~1로 변환
	        }
	    }

	    // 학생 점수와 평가 항목 매칭
	    double attendanceRate = rateMap.getOrDefault("ATT", 0.0);
	    double assignmentRate = rateMap.getOrDefault("ASS", 0.0);
	    double midtermRate = rateMap.getOrDefault("MID", 0.0);
	    double finalRate = rateMap.getOrDefault("FIN", 0.0);
	    double etcRate = rateMap.getOrDefault("ETC", 0.0);

	    // 학생의 점수
	    double attendanceScore = aVo.getAttenAtndScore() != null ? aVo.getAttenAtndScore() : 0.0;
	    double assignmentScore = aVo.getAssigScore() != null ? aVo.getAssigScore() : 0.0;
	    double midtermScore = aVo.getPrTestScore() != null ? aVo.getPrTestScore() : 0.0;
	    double finalScore = aVo.getFtTestScore() != null ? aVo.getFtTestScore() : 0.0;
	    double etcScore = aVo.getEtcScore() != null ? aVo.getEtcScore() : 0.0;

	    // 가중합 계산
	    double weightedScore = (attendanceScore * attendanceRate) +
	                           (assignmentScore * assignmentRate) +
	                           (midtermScore * midtermRate) +
	                           (finalScore * finalRate) +
	                           (etcScore * etcRate);

	    // 0~4.5 범위로 변환
	    double finalGrade = (weightedScore / 100.0) * 4.5;

	    // 최종 성적 반환
	    return finalGrade;
	}


	
	
	
	@Override
	public int deleteCoeva() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<AttendeeVO> selectProfeCoevaList(String profeId, String semstrNo) {
		// TODO Auto-generated method stub
		return dao.selectProfeCoevaList(profeId, semstrNo);
	}


	@Override
	public List<AttendeeVO> selectProfeCoevaDetail(String profeId, String lectNo, String semstrNo) {
		// TODO Auto-generated method stub
		return dao.selectProfeCoevaDetail(profeId, lectNo, semstrNo);
	}








}






















