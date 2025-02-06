package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 동아리
 */

@Data
@EqualsAndHashCode(of = {"clubNo"})
public class ClubVO implements Serializable { 

	@Size(max = 10)
	private String clubNo; // 동아리번호
	
	@Size(min = 4, max = 4)
	private String cocoCd; // 동아리 상태 코드
	
	@NotBlank
    @Size(max = 20)
	private String clubNm; // 동아리명
	
	@NotBlank
    @Size(max = 20)
	private String clubMas; // 동아리장명
	
	@NotBlank
    @Size(min = 2, max = 20)
	private String clubPurpo; // 동아리 목적
	
	@NotBlank
    @Size(max = 20)
	private String clubClass; // 동아리실
	
	private Long clubMnCnt; // 동아리 인원수
	
    @Size(min = 8, max = 8)
	private String clubDt; // 개설일
	
    @Size(min = 8, max = 8)
	private String clubEt; // 폐지일
	
    @NotBlank
	// blob
	private String clubPhoto; // 동아리 프로필 사진
    

    private CommonCodeVO commonCodeVO;
}
