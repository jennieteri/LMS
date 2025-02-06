package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "cocoCd")
public class CommonCodeVO implements Serializable{
	@NotBlank
	@Size(max = 4)
	private String cocoCd;  // 공통코드
	@Size(max = 4)
	private String parCocoCd;  // 부모공통코드
	@NotBlank
	@Size(max = 100)
	private String cocoStts;  // 상태
	
	
}
