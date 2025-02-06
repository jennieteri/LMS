package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of="awardCd")
public class AwardVO implements Serializable{
	//장학금
	
	private int rnum;
	
	@Size(max = 10)
	private String awardCd; //장학금 코드 
	
	private String awardEdnstNm; // 지급 기관 명
	
	@NotBlank
	private String awardNm; // 장학금 이름
	
	@NotBlank(message = "장학금 유형이 선택되지 않았습니다.")
	private String awardType; // 장학금 유형
	
	@Size(min = 0, max = 8)
	@NotNull
	private Integer awardGiveAmt; // 금액 
	
	@NotBlank
	@ToStringExclude
	private String awardDetail; // 장학금 설명 
	
	@ToStringExclude
	@NotBlank
	private String awardBenefit; // 장학혜택 
	
	@NotBlank
	@ToStringExclude
	private String awardDocument; // 제출 서류 
	
	@ToString.Exclude
	private List<AwardAskVO> awardAsk;
	
	
	

	
}
