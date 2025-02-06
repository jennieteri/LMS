package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class EducationTimeTableCodeVO implements Serializable{
	private String edcTimeCd;
	private String beginTime;
	private String endTime;
	
	private CommonCodeVO commonCodeVO;
}
