package kr.or.ddit.yguniv.eventregistrant.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.EventRegistrantVO;

@Mapper
public interface EventRegistrantMapper {
	/**
	 * 참석 취소
	 * @param stuId
	 * @param jobNo
	 * @return
	 */
	public int deleteRegistredForJObBoard(EventRegistrantVO eventRegistrant);

	/**
	 * 행사 신청 학생 등록
	 * @param eventRegistrant
	 * @return
	 */
	public int insertEventRegistrant(EventRegistrantVO eventRegistrant);
	/**
	 * 신청 중복 체크 
	 * @param eventRegistrant
	 * @return
	 */
	public int checkDuplicate(EventRegistrantVO eventRegistrant);
	}
