package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "projectchatmessageId")
public class ProjectChatMessageVO implements Serializable{
	
	@NotBlank
	@Size(max = 10)
	private String projectchatmessageId;
	@NotBlank
	@Size(max = 10)
	private String teamCd;
	
	private String taskNo;
	
	@NotBlank
	@Size(max = 300)
	private String projectchatmessageSender;
	@NotBlank
	@Size(max = 3000)
	private String projectchatmessageContent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 입력 시 포맷
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // JSON 반환 시 포맷
	private LocalDateTime projectchatmessageDt;
	
	private String projectchatmessageYn;
	
	private ProjectChatRoomVO room;
	
	private PersonVO person;
}
