package kr.or.ddit.yguniv.vo;

import java.util.List;

import lombok.Data;

@Data
public class LogDTO {
	private List<LogVO> todayLogList;
	private String totalCountLog;
	
	private List<LogVO> traficLogList;
	private List<LogVO> traficMethodLogList;
	
	
	
}
