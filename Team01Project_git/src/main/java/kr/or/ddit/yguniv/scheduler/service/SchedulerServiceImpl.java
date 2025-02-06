package kr.or.ddit.yguniv.scheduler.service;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.scheduler.dao.SchedulerMapper;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

	private final SchedulerMapper dao;
	
	@Override
	public PersonVO selectPersonData(String id) {
		// TODO Auto-generated method stub
		return dao.selectPersonData(id);
	}
	
}
