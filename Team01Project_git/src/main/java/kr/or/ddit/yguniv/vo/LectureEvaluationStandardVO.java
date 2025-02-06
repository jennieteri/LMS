package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 강의 평가기준
 */
@Data
@EqualsAndHashCode(exclude = "rate")
public class LectureEvaluationStandardVO implements Serializable {
	@Size(max = 10)
	private String lectNo;  // 강의번호
	@NotBlank
	@Size(max = 4)
	private String evlStdrCd;  // 평가기준코드
	@NotNull(message = "평가비율을 입력해야 합니다")
	@Min(value = 0, message = "평가비울은 0이상이어야 합니다")
	@Max(value = 100, message = "평가비울은 100이하이어야 합니다")
	private Integer rate;  // 평가 비율
	
	private LectureVO lectureVO;
	private CommonCodeVO commonCodeVO;
}
