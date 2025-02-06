package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChatbotLog  implements Serializable {
	@Size(min = 10,max = 10)
	@NotBlank
	private String chatbId;
	@Size(max = 8)
	private String chatlDt;
	@Size(max = 3000)
	private String chatlNotes;
}
