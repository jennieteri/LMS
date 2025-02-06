package kr.or.ddit.yguniv.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *	학부
 */
@Data
@EqualsAndHashCode(of = "colleNo")
public class CollegeVO {
	@NotBlank
	@Size(max = 10)
	private String colleNo;  // 학부번호
	@NotBlank
	@Size(max = 300)
	private String colleNm;  // 학부명
	@NotBlank
	@Size(min = 1, max = 1)
	private String useYn;  // 사용여부
}
