package kr.or.ddit.yguniv.eventregistrant.service;

import kr.or.ddit.yguniv.vo.EventRegistrantVO;

public interface EventRegistrantService {
	/**
	 * 참석 취소
	 * @param stuId
	 * @param jobNo
	 * @return
	 */
	public int deleteRegistredForJObBoard(EventRegistrantVO eventRegistrant);
	/**
	 * 신청학생 insert
	 * @param eventRegistrant
	 * @return
	 */
	public void insertEventRegistrant(EventRegistrantVO eventRegistrant);
	/**
	 * 신청학생 중복 체크 
	 * @param eventRegistrant
	 */
	public void checkDuplicate(EventRegistrantVO eventRegistrant);
}
