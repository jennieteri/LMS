package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MyCalendarVO implements Serializable{
	
	private String prsId;
	
	private String myCalendarNo;
	@Size(max = 100)
	private String myCalendarTitle;
	@Size(max = 3000)
	private String myCalendarContent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate myCalendarSd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate myCalendarEd;
	
	@Size(max = 10)
	private String boardNo;
	
	private String myCalendarYn;
	
	@Size(max = 4)
	private String myCalendarCd;
	
}
