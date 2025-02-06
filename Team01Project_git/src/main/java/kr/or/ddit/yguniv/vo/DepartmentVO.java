package kr.or.ddit.yguniv.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *	학과
 */
@Data
@EqualsAndHashCode
public class DepartmentVO {
	@NotBlank
	@Size(max = 10)
	private String deptNo;  // 학과번호
	@NotBlank
	@Size(max = 10)
	private String colleNo;  // 학부번호
	@NotBlank
	@Size(max = 300)
	private String deptNm;  // 학과명
	@NotBlank
	@Size(min = 1, max = 1)
	private String useYn;  // 사용여부
	
	private CollegeVO collegeVO;
}
