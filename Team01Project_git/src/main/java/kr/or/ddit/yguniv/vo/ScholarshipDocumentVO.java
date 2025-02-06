package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//장학금제출서류
@Data
@ToString
@EqualsAndHashCode(of="scholDocNo")
public class ScholarshipDocumentVO implements Serializable{
	
	@Size(min = 10,max = 10)
	@NotBlank
	private String scholDocNo; // 제출 서류 번호 
	
	@NotBlank
	@Size(min= 10, max = 10)
	private String stuId; // 학생 번호
	
	@Size(min= 10, max=10)
	@NotBlank
	private String awardCd; //장학금 코드 
	
	@Size(min = 6,max=6)
	@NotBlank
	private String semstrNo; // 학기코드 (2024006)
	
	@NotNull
	private Integer atchFileId; //파일그룹번호 
	
	// 장학금 신청
	@ToStringExclude
	private ScholarshipApplicationVO scholarshipApplication;
	

}
