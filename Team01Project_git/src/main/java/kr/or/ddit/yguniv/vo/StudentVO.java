package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@Data
@EqualsAndHashCode(of = "id")
public class StudentVO extends PersonVO implements Serializable{
	
	public String getStuId() {
		return getId();
	}
	
	public void setStuId(String id) {
		setId(id);
	}
	
	@NotBlank
	@Size(min = 4, max = 4)
	private String gradeCd; // 학년
	@NotBlank
	@Size(min = 4, max = 4)
	private String streCateCd; // 학적상태코드
	@NotBlank
	private String deptCd; // 학과번호 
	@NotBlank
	@Size(min = 10, max = 10)
	private String profeId; // 담당교수
	
	private String dormantDate; // 휴면게정일자 
	
	@ToString.Exclude
	private ProfessorVO professor;
	
	@ToString.Exclude
	private CommonCodeVO gradeCocoVO;
	
	@ToString.Exclude
	private CommonCodeVO streCateCocoVO;
	
	@ToString.Exclude
	private DepartmentVO departmentVO;
	
	@ToString.Exclude
	private List<AcademicProbationVO> academicProbationList;
	
	@ToString.Exclude
	private List<TranscriptVO> transcriptList;
	
}
