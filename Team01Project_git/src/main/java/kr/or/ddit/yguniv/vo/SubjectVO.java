package kr.or.ddit.yguniv.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *	과목
 */
@Data
@EqualsAndHashCode(of = "subNo")
@ToString(exclude = "subInfo")
public class SubjectVO {
	@NotBlank
	@Size(max = 10)
	private String subNo;  // 과목번호
	@NotBlank
	@Size(max = 10)
	private String deptNo;  // 학과번호
	@NotBlank
	@Size(max = 300)
	private String subNm;  // 과목명
	@NotBlank
	@Size(max = 3000)
	private String subInfo;  // 과목설명
	@NotBlank
	@Size(max = 4)
	private String subFicdCd;  // 과목분류코드
	private String subFicdCdNm;  // 과목분류이름

	@NotBlank
	@Size(max = 4)
	private String gradeCd;  // 수강학년코드
	private String gradeCdNm;  // 수강학년코드이름
	
	@NotBlank
	@Size(min = 1, max = 1)
	private String useYn;  // 사용여부
	
	private DepartmentVO departmentVO;
	private CommonCodeVO subFicdCdCommonCodeVO;
	private CommonCodeVO gradeCdCommonCodeVO;
}
