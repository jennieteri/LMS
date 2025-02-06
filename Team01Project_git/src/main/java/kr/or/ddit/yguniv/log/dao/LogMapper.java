package kr.or.ddit.yguniv.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.LogVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;

@Mapper
public interface LogMapper {
	/**
	 * log 추가
	 * @param log
	 */
	public int mergeIntoLog(LogVO logVo);
	public int getNextLogNo();
	/**
	 * 로그 상세보기
	 * @param logNO
	 * @return
	 */
	public LogVO selectlog(String logNO);
	public int getTotalLogCount();
	public List<LogVO> getLogDayList();
	
	public List<LogVO> getTraficLogList(@Param("logDate") String logDate);
	public List<LogVO> getTraficMethodLogList(@Param("logDate") String logDate);
	
	
	/**
	 * 로그 전체리스트
	 * @return
	 */
	public List<LogVO>getPersonLog(@Param("paging") PaginationInfo<LogVO> paging,@Param("id")String id);
	/**
	 * 로그 수정 -> 이건 없어도 될거같음.
	 * @param log
	 */
	public void updateintroduce(LogVO log);
	/**
	 * 로그 삭제
	 * @param logNO
	 */
	public void deleteintroduce(String logNO);
}
