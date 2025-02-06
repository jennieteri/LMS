package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

//성적 이의 신청
@Data
public class ScoreFormalObjectionVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String lectNo;
	@Size(max = 10)
	@NotBlank
	private String stuId;
	//@NotBlank(message = "이의 사유를 입력해 주세요")
	private String objcCn;
	@NotBlank(message = "답변을 입력해 주세요")
	private String answerCn;
	
	private LectureVO lectVO;
	
	@Valid
	private AttendeeVO attenVO;
	
	private PersonVO personVO;
	
	private SemesterVO semstrVO;
	
	private StudentVO studentVO;
	
	private String nm;
	
	private String semstrNo;
}
