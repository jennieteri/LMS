package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//장학금 신청
@Data
@ToString
@EqualsAndHashCode(of="awardCd")
public class ScholarshipApplicationVO implements Serializable{
	@Size(min = 6, max = 6)
	@NotBlank
	private String semstrNo; // 학기 번호 202406
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId; // 학생번호 
	
	@Size(min= 10 ,max = 10)
	@NotBlank
	private String awardCd; // 장학금 코드 
	
	@Size(min=8, max=8)
	@NotBlank
	private String shapRcptDate; //접수 일자 
	
	@Size(min=8,max = 8)
	@NotBlank
	private String shapChcDate; //선발일자 

	@Size(min = 10, max = 10)
	private String profeId; // 학생아이디 

	@ToStringExclude
	private long shapRecommend; // 추천사유 
	
	
	@NotBlank
	private String shapAccCd; // 장학금 진행코드 
	
	@ToStringExclude
	private String shapNoReason; //신청 반려 사유 
	
	
	//award
	@ToString.Exclude
	private AwardVO award;
	
	@ToString.Exclude
	private StudentVO student;
	
	
	
	
	
	

}
