package kr.or.ddit.yguniv.commons.mycalendar.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.yguniv.vo.MyCalendarVO;

public interface MyCalendarService {
	
	/** 나의 일정 생성
	 * @param mycalendar
	 * @return
	 */
	public int createMyCalendar(MyCalendarVO mycalendar);

	/** 나의 일정 단건 조회
	 * @param myCalendarNo
	 * @return
	 */
	public MyCalendarVO readMyCalendar(int myCalendarNo);

	/** 나의 일정 목록조회
	 * @return
	 */
	public List<MyCalendarVO> readMyCalendarList(String prsId);

	/** 나의 일정 수정
	 * @param mycalendar
	 * @return
	 */
	public int modifyMyCalendar(MyCalendarVO mycalendar);

	/** 나의 일정 삭제
	 * @param myCalendarNo
	 * @return
	 */
	public int deleteMyCalendar(String myCalendarNo);
	
	
	/**게시글 연동처리
	 * @param mycalendar
	 * @param type
	 * @return
	 */
	public int linkedMycalendar(MyCalendarVO mycalendar,String type);
	
	/**게시글 연동제거
	 * @param mycalendar
	 * @param type
	 * @return
	 */
	public int deleteLinked(MyCalendarVO mycalendar,String type);
	
	
	/**해당 게시글 연동여부 확인
	 * @param boNo
	 * @return
	 */
	public boolean checkMyCal(Map<String, Object> param);
	
}