package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public class EmployeeVO extends PersonVO implements Serializable{
	
	public String getEmpId() {
		return getId();
	}
	
	public void setEmpId(String id) {
		setId(id);
	}
	
	private String empDept; //부서
	private String empJbgd; //직급 
	
}
