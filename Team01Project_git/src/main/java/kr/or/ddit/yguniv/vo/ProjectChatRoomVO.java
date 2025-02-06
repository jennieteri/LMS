package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProjectChatRoomVO implements Serializable {
	@NotBlank
	@Size(max = 10)
	private String teamCd;
	@Size(max = 100)
	private String projectchatroomTitle;
	
	private String taskNo;
	//HAS A
	private ProjectTeamVO team;
}
