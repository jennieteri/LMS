package kr.or.ddit.yguniv.lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.dao.LectureMapper;
import kr.or.ddit.yguniv.vo.LectureVO;

@Service
public class LectureServiceImpl {
	@Autowired
	private LectureMapper mapper;
	
	/**
	 * R-강의 개설 신청 목록 조회
	 */
	public List<LectureVO> selectLectureRequestList(LectureVO lectureVO){
		return mapper.selectLectureRequestList(lectureVO);
	}
	/**
	 * R-강의 개설 신청 상세 조회
	 */
	public LectureVO selectLectureRequest(LectureVO lectureVO){
		return mapper.selectLectureRequest(lectureVO);
	}
	/** 
	 * C-강의 개설 신청 추가
	 */
	@Transactional
	public void insertLectureRequest(LectureVO lectureVO){
		int result = 1;
		result *= mapper.insertLectureRequest(lectureVO);
		if("N".equals(lectureVO.getLectOnlineYn())) {
			result *= mapper.insertSchedule(lectureVO);
		}
		result *= mapper.insertLectEvalStan(lectureVO);
		result *= mapper.insertWeekLecturePlan(lectureVO);
		if(!(result>0)) {
			throw new RuntimeException();
		}
	}
	/** 
	 * U-강의 개설 신청 수정
	 */
	@Transactional
	public void updateLectureRequest(LectureVO lectureVO){
		int result = 1;
		result *= mapper.updateLectureRequest(lectureVO);
		mapper.deleteSchedule(lectureVO);
		mapper.deleteLectEvalStan(lectureVO);
		mapper.deleteWeekLecturePlan(lectureVO);
		if("N".equals(lectureVO.getLectOnlineYn())) {
			result *= mapper.insertSchedule(lectureVO);
		}
		result *= mapper.insertLectEvalStan(lectureVO);
		result *= mapper.insertWeekLecturePlan(lectureVO);
		if(!(result>0)) {
			throw new RuntimeException();
		}
	}
	/** 
	 * D-강의 개설 신청 삭제
	 */
	public void deleteLectureRequest(LectureVO lectureVO){
		mapper.deleteSchedule(lectureVO);
		mapper.deleteLectEvalStan(lectureVO);
		mapper.deleteWeekLecturePlan(lectureVO);
		if(!(mapper.deleteLectureRequest(lectureVO)>0)) {
			throw new PKNotFoundException("강의번호에 해당하는 강의가 없습니다", true);
		}
	}
	/** 
	 * U-강의 상태 수정
	 */
	public void lectureStatusUpdate(LectureVO lectureVO){
		mapper.lectureStatusUpdate(lectureVO);
	}
	/** 
	 * C-강의 등록시 강의 자료에 주차별 계획대로 주차 등록
	 */
	public void insertLectureWeekPlan(LectureVO lectureVO){
		mapper.insertLectureWeekPlan(lectureVO);
	}
}
