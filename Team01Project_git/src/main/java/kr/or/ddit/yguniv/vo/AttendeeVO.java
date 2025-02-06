package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * 수강생
 */
@Data
@EqualsAndHashCode(of = {"lectNo", "stuId"})
@ToString(exclude = "attenCoeva")
public class AttendeeVO implements Serializable {
	@Size(max = 10)
	private String lectNo;  // 강의번호

	@Size(min = 10, max = 10, message = "10자리의 학생번호가 아닙니다.")
	private String stuId;  // 학생번호

	@NotNull(message = "출석점수를 입력해야 합니다.")
	@Min(value = 0, message = "0 이상의 점수를 입력해야 합니다.")
	@Max(value = 100, message = "출석점수는 100 이하의 점수를 입력해야 합니다.")
	private Double attenAtndScore;  // 출석점수

	@NotNull(message = "과제종합점수를 입력해야 합니다.")
	@Min(value = 0, message = "0 이상의 점수를 입력해야 합니다.")
	@Max(value = 100, message = "과제종합점수는 100 이하의 점수를 입력해야 합니다.")
	private Double assigScore;  // 과제종합점수

	@NotNull(message = "시험점수를 입력해야 합니다.")
	@Min(value = 0, message = "0 이상의 점수를 입력해야 합니다.")
	@Max(value = 100, message = "중간고사점수는 100 이하의 점수를 입력해야 합니다.")
	private Double prTestScore;  // 시험종합점수
	@NotNull(message = "시험점수를 입력해야 합니다.")
	@Min(value = 0, message = "0 이상의 점수를 입력해야 합니다.")
	@Max(value = 100, message = "기말고사점수는 100 이하의 점수를 입력해야 합니다.")
	private Double ftTestScore;  // 시험종합점수
	@NotNull(message = "시험점수를 입력해야 합니다.")
	@Min(value = 0, message = "0 이상의 점수를 입력해야 합니다.")
	@Max(value = 100, message = "기타점수는 100 이하의 점수를 입력해야 합니다.")
	private Double etcScore;  // 시험종합점수

	private Double attenScore;  // 성적

	
	@Size(max = 3000)
	private String attenCoeva;  // 강의평가

	private ScoreFormalObjectionVO scoreFormalObjectionVO;
	private LectureVO lectureVO;
	private StudentVO studentVO;
	private PersonVO personVO;
}
