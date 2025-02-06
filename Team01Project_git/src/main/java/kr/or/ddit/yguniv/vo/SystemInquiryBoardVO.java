package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of ="sibNo" )
//시스템 문의 게시판
public class SystemInquiryBoardVO implements Serializable{
	private int rnum;
	
	@Size(min = 1, max= 10)
	private String sibNo; // 게시판 번호 
	
	@Size(min = 10, max = 10)
	private String userId; // 사용자 아이디 
	
	@NotBlank
	private String nm; // 사용자 일므 

	@NotBlank
	@ToString.Exclude
	@Size(max = 200)
	private String sibTtl; // 문의제목
	
	@NotBlank
	@ToString.Exclude
	private String sibCn; // 문의내용 
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime sibDt; // 문의등록일자
	
	@Size(min=1, max=1)
	private String sibSttsYn; // 문의처리 상태
	
	@ToString.Exclude 
	private String sibAns; // 문의내용답변 
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime sibTime; //문의 답변 일시

	
	
	


	

}
