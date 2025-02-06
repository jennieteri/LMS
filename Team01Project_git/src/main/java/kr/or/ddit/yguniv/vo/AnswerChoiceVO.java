package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AnswerChoiceVO  implements Serializable {
	//시험 문항에 대한 VO
	
	@Size(max = 10)
	@NotBlank
	private String anchNo;
	@NotBlank
	@Size(max = 10)
	private String queNo;
	@NotBlank
	@Size(max = 10)
	private String testNo;
	@Size(max = 300)
	@NotBlank(message = "문항 설명은 공백일 수 없습니다.")
	private String anchDescr;
	


}
