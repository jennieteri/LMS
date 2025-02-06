package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 강의실 기자재 테이블
 */
@Data
@EqualsAndHashCode(of = {"eqmngCd"})
public class EquipmentManagementVO implements Serializable{ 
	
	@NotBlank
	@Size(max = 10)
	private String eqmngCd; // 기자재관리번호
	
	@NotBlank
	@Size(max = 10)
	private String croomCd; // 강의실번호
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String empId; // 사번
	
	@NotBlank
	@Size(max = 200)
	private String eqmngCp; // 기자재담당자명
	
	@NotBlank
	@Size(max = 200)
	private String eqmngNm; // 기자재명
	
	@NotBlank
	@Size(max = 500)
	private String eqmngDesc; // 기자재설명
	
	@NotBlank
	@Size(max = 8)
	private String eqmngStock; // 재고량
	
	@NotBlank
	@Size(min = 8, max = 8)
	private String eqmngSchdl; // 도입년월
	
	@NotBlank
	@Size(max = 2)
	private String eqmngSvlf; // 내용연수
	
	@NotBlank
	@Size(min = 8, max = 8)
	private String eqmngSerl; // 교체시기
	
	private ClassRoomVO classRoomVO;
	private EmployeeVO employeeVO;
}
