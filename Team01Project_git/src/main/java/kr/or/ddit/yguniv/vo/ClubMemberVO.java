package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 동아리원
 */

@Data
@EqualsAndHashCode(of = {"clubNo", "stuId"})
public class ClubMemberVO implements Serializable { 
	
	@NotBlank
	@Size(max = 10)
	private String clubNo; // 동아리번호
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId; // 동아리원학번
	
	@NotBlank
	@Size(max = 4)
	private String clmemYn; // 동아리장여부
	
	@NotBlank
	@Size(max = 1)
	private String cocoCd; // 동아리원상태코드
	
	@NotBlank
	@Size(min = 8, max = 8)
	private String clmemDate; // 동아리가입일시
    
	private ClubVO clubVO;
    private StudentVO studentVO;
    private CommonCodeVO commonCodeVO;
}
