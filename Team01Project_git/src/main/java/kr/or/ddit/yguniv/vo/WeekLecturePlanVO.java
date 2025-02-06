package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 강의 주차별 계획
 */
@Data
@EqualsAndHashCode(exclude = "lectPlan")
@ToString(exclude = "lectPlan")
public class WeekLecturePlanVO implements Serializable {
	@Size(max = 10)
	private String lectNo;  // 강의번호
	@NotBlank
	@Size(max = 4)
	private String weekCd;  // 주차코드
	@NotBlank(message = "주차계획을 입력해야 합니다")
	private String lectPlan;  // 주차계획
	
	private LectureVO lectureVO;
	private CommonCodeVO commonCodeVO;
}
