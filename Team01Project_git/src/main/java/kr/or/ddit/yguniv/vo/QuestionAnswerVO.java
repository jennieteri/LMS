package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;

@Data
public class QuestionAnswerVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String lectNo;
	@Size(max = 10)
	@NotBlank
	private String stuId;
	@Size(max = 10)
	@NotBlank
	private String testNo;
	@Size(max = 10)
	@NotBlank
	private String queNo;
	
	@ToStringExclude
	@Size(max = 200)
	private String queAnswer;
	@Size(max = 1)
	private String quesYn;
	@Size(max = 3)
	private String quesScore;
}
