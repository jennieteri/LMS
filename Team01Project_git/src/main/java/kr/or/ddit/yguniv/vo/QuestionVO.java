package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class QuestionVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String queNo;
	@Size(max = 10)
	@NotBlank
	private String testNo;
	@Size(max = 500)
	@NotBlank(message = "문제 설명은 공백일 수 없습니다.")
	private String queDescr;
	@Size(max = 3)
	private String queType;
	@Size(max = 3)
	@NotBlank(message = "문제 배점은 공백 또는 100점을 초과할 수 없습니다.")
	private String queScore;
	@Size(max = 500)
	private String queAnswer; //정답. 복수정답 처리도 가능하게 할려면? 서술형 감안해서 크게
	
	//has many 문항
	private List<AnswerChoiceVO> answerVO;
	
	//has many 제출답안
	private List<QuestionAnswerVO> queAnsVO;
}
