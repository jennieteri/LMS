package kr.or.ddit.yguniv.vo;



import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.ToString;

@Data
public class ChatbotVO  implements Serializable {
	@Size(min = 10,max = 10)
	@NotBlank
	private String chatbId;
	@Size(max = 20)
	private String chatbNm;
	
//	@ToString.Exclude
//	private List<ChatbotDataVO> chatbotList;
//	private List<ChatbotLogVO> logList;
}
