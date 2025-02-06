package kr.or.ddit.yguniv.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 학사경고
 */
@Data
@EqualsAndHashCode(of = "proNo")
@ToString(exclude = "proRes")
public class AcademicProbationVO {
	@Size(min = 8, max = 8)
	private String proNo;  // 학사경고부여번호
	@NotBlank(message = "학사경고 부여 학생번호가 누락되었습니다")
	@Size(min = 10, max = 10)
	private String stuId;  // 학생번호
	@NotBlank(message = "학사경고사유를 입력해주세요")
	@Size(max = 3000)
	private String proRes;  // 학사경고사유
	@Size(min = 8, max = 8)
	private String proDate;  // 학사경고부여일자
	
	private StudentVO studentVO;
}
