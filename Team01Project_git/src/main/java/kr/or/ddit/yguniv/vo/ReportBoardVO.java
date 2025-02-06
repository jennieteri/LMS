package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//신고게시판

@Data
@ToString
@EqualsAndHashCode(of= "prsId")
public class ReportBoardVO implements Serializable{
	
	//신고게시판
	
	//게시판 번호 
	@NotBlank
	@Size(min = 1,max = 10)
	private String dclrNo;
	//작성자 아이디
	@Size(min = 10, max =10)
	@NotBlank
	private String prsId;
	
	//게시판 제목
	@NotBlank
	private String dclrNm;
	// 사유내용
	
	@NotBlank
	@ToStringExclude
	private Long dclrNotes;
	
	//처리여부
	@NotBlank
	@Size(min = 1, max = 1)
	private String dclrYn;
	

}
