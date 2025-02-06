package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ExaminationRecordVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String lectNo;
	@Size(max = 10)
	@NotBlank
	private String stuId;
	@Size(max = 10)
	@NotBlank
	private String testNo;
	@Size(max = 8)
	private String examinDate;
	@Size(max = 3)
	private String testScore;


	//has many 제출답안
	private List<QuestionAnswerVO> questionAnswerList;
	private List<CheatingVO> cheatList;
	
}
