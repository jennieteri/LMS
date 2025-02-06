package kr.or.ddit.yguniv.commons.mycalendar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.MyCalendarVO;

@Mapper
public interface MyCalendarMapper {
	/**
	 * @param calendar
	 * @return
	 */
	public int insertMyCalendar(MyCalendarVO calendar);
	/**
	 * @param myCalendarNo
	 * @return
	 */
	public MyCalendarVO selectMyCalendar(int myCalendarNo);
	/**
	 * @return
	 */
	public List<MyCalendarVO> selectMyCalendarList(String prsId);
	/**
	 * @param calendar
	 * @return
	 */
	public int updateMyCalendar(MyCalendarVO calendar);
	/**
	 * @param myCalendarNo
	 * @return
	 */
	public int deleteMyCalendar(String myCalendarNo);
	
	/**사용자일정 게시글번호로 조회
	 * @param calendar
	 * @return
	 */
	public MyCalendarVO selectMyCalendarWithBoNo(MyCalendarVO calendar);
	
	/**기존 연동된 일정중복체크
	 * @param calendar
	 * @return
	 */
	public int checkDuplicate(MyCalendarVO calendar);
	
}
