package kr.or.ddit.yguniv.log.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.log.dao.LogMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.LogVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{
	
	private final LogMapper dao;

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public int mergeIntoLog(LogVO logVo) {
		// TODO Auto-generated method stub
		return dao.mergeIntoLog(logVo);
	}

	@Override
	public int getNextLogNo() {
		// TODO Auto-generated method stub
		return dao.getNextLogNo();
	}

	@Override
	public List<LogVO> getPersonLog(PaginationInfo<LogVO> paging, String id) {
		// TODO Auto-generated method stub
		return dao.getPersonLog(paging,id);
	}
	
	@Override
	public int getTotalLogCount() {
		return dao.getTotalLogCount();
	}
	@Override
	public List<LogVO> getLogDayList(){
		
		return dao.getLogDayList();
	}
	
	public List<LogVO> getTraficLogList(String logDate){
		
		return dao.getTraficLogList(logDate);
	}

	@Override
	public List<LogVO> getTraficMethodLogList(String logDate) {
		// TODO Auto-generated method stub
		return dao.getTraficMethodLogList(logDate);
	}
	
	
	
}
