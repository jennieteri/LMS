package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="empfiCd")
public class EmploymentFieldVO implements Serializable{
	@NotNull
    @Size(max = 10)
	private String empfiCd; //취업분야코드
	
    @Size(max = 20)
	private String empfiNm; //취업분야명
}
