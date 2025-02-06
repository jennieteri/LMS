package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProfessorVO extends PersonVO implements Serializable{
	
	public String getProfeId() {
		return getId();
	}
	
	public void setProfeId(String id) {
		setId(id);
	}
	
	
	@NotBlank
	private String deptNo; // 학과 코드 
	private String profeType; // 구분코드 ex)정교수 / 기간게 
	
	
	private DepartmentVO departmentVO;
}
