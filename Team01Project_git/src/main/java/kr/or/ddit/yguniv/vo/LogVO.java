package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="logNo")
public class LogVO implements Serializable{
	
	private String id;
	private String logDate;
	private String logTime;
	private String logContNm;
	private String logMethod;
	private Long logCount;
	
	
	//PERSON
}
