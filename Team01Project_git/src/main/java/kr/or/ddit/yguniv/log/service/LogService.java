package kr.or.ddit.yguniv.log.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.LogVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;

public interface LogService {
	public int mergeIntoLog(LogVO logVo);
	public int getNextLogNo();
	public List<LogVO> getPersonLog(PaginationInfo<LogVO> paging,String id);

	
	public int getTotalLogCount();
	public List<LogVO> getLogDayList();
	
	public List<LogVO> getTraficLogList(String logDate);
	public List<LogVO> getTraficMethodLogList(String logDate);
	
}
