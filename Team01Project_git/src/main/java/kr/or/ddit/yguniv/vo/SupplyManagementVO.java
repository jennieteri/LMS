package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 비품관리
 */

@Data
@EqualsAndHashCode(of = "sumngCd")
public class SupplyManagementVO implements Serializable {
	
	@NotBlank
	@Size(max = 10)
	private String sumngCd; // 비품번호
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String empId; // 사번
	
	@NotBlank
	@Size(max = 200)
	private String sumngCp; // 비품담당자명
	
	@NotBlank
	@Size(max = 200)
	private String sumngNm; // 비품명
	
	@NotBlank
	@Size(max = 500)
	private String sumngDesc; // 비품설명
	
	@NotBlank
	@Size(min = 8, max = 8)
	private String sumngSchdl; // 구매날짜
	
	@NotBlank
	@Size(max = 8)
	private String sumngSvlf; // 내용연수
	
	@NotBlank
	@Size(max = 8)
	private String sumngStock; // 재고량
	
	private EmployeeVO employeeVO;
	
}
