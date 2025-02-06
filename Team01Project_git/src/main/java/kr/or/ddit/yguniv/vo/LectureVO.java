package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.yguniv.validate.DeleteGroup;
import kr.or.ddit.yguniv.validate.ReturnGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 강의
 */
@Data
@EqualsAndHashCode(of="lectNo")
@ToString(exclude = {"lectDescr", "lectReturn", "lectAbl"})
public class LectureVO implements Serializable{
	@Size(max = 10)
	private String lectNo;  // 강의번호
	@NotBlank
	@Size(min = 10, max = 10)
	private String profeId;  // 교수번호
	@NotBlank(message = "과목을 선택해야 합니다")
	@Size(max = 10)
	private String subNo;  // 과목번호
	@Size(max = 4)
	private String lectStatusCd;  // 강의상태코드
	@NotBlank(message = "강의 학기를 선택해야 합니다")
	private String semstrNo;  // 학기번호
	@NotNull(message = "학점을 입력해야 합니다")
	@Min(value = 1, message = "학점은 1점 이상이어야 합니다")
	@Max(value = 3, message = "학점은 3점 이하이어야 합니다")
	private Integer lectScore;  // 학점
	public void setLectScore(Integer lectScore) {
		if(lectScore != null) {
			this.lectScore = lectScore;
			this.lectSession = lectScore * 16;
		}
	}
	@NotBlank(message = "강의명을 입력해야 합니다")
	@Size(max = 200, message = "강의명이 너무 깁니다")
	private String lectNm;  // 강의명
	private Integer lectSession;  // 총차수
	@Min(0)
	private Integer lectAttenNope;  // 수강인원
	@NotNull(message = "모집인원을 입력해야 합니다")
	@Min(0)
	private Integer lectEnNope;  // 모집인원
	@NotBlank(message = "강의방식을 선택해야 합니다")
	@Size(max = 1)
	private String lectOnlineYn;  // 온라인여부
	@NotBlank(message = "강의설명을 입력해야 합니다")
	private String lectDescr;  // 강의설명
	@NotBlank(groups = ReturnGroup.class, message = "반려사유를 입력해야 합니다")
	private String lectReturn;  // 반려사유
	@NotBlank(groups = DeleteGroup.class, message = "폐강사유를 입력해야 합니다")
	private String lectAbl;  // 폐강사유
	
	
	private ProfessorVO professorVO;
	private SubjectVO subjectVO;
	private CommonCodeVO commonCodeVO;
	private SemesterVO semesterVO;
	private List<AttendeeVO> attendeeList;
	 
	 
	//강의평가 용 테이블
	@Valid
	private List<LectureEvaluationStandardVO> lesVo;
	@JsonIgnore
	@AssertTrue(message = "평가 비율의 합은 100이 되어야 합니다")
	public boolean isLesValid() {
		int[] sum = {0};
		lesVo.stream().filter(l->l.getRate() != null)
					.map(LectureEvaluationStandardVO::getRate)
					.forEach(r->sum[0] += r);
		return sum[0] == 100;
	}
	@Valid
	private List<ScheduleVO> scheduleVO;
	@JsonIgnore
	@AssertTrue(message = "오프라인 강의는 강의실과 강의시간을 선택해야합니다")
	public boolean isScheduleVOValid() {
		boolean result = true;
		if("N".equals(this.lectOnlineYn)) {
			result = !this.scheduleVO.isEmpty();
		}
		return result;
	}
	@Valid
	private List<WeekLecturePlanVO>  weekVO;
//	private EducationTimeTableCodeVO ettcVO ;
//	private ClassRoomVO classRoomVO;
	 
	private String joinSchedule;
}
