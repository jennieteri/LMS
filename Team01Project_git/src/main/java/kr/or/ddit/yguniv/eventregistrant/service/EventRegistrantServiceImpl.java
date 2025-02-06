package kr.or.ddit.yguniv.eventregistrant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.eventregistrant.dao.EventRegistrantMapper;
import kr.or.ddit.yguniv.vo.EventRegistrantVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRegistrantServiceImpl implements EventRegistrantService {
	
	@Autowired
	private final EventRegistrantMapper mapper;
	
	//중복확인
	 @Override
	    public void insertEventRegistrant(EventRegistrantVO eventRegistrant) {
		  
	        int count = mapper.checkDuplicate(eventRegistrant);
	        if (count > 0) {
	            throw new RuntimeException("이미 신청되었습니다.");
	        }
	        mapper.insertEventRegistrant(eventRegistrant);
	 }
	@Override
	public void checkDuplicate(EventRegistrantVO eventRegistrant) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int deleteRegistredForJObBoard(EventRegistrantVO eventRegistrant) {
		return mapper.deleteRegistredForJObBoard(eventRegistrant);
	}

	

}
