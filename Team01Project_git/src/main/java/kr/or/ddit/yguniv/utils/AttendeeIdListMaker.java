package kr.or.ddit.yguniv.utils;

import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.yguniv.vo.AttendeeVO;

public class AttendeeIdListMaker {
	
	public List<String> listMaker(List<AttendeeVO> list){
		List<String>attendeeIdList = new ArrayList<>();
		
		for(AttendeeVO attendee : list) {
			attendeeIdList.add(attendee.getStuId());
		}
		
		return attendeeIdList;
	}
}
