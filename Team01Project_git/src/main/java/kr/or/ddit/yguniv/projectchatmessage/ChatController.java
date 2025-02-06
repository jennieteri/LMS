package kr.or.ddit.yguniv.projectchatmessage;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectchatmessage.service.ProjectChatMessageService;
import kr.or.ddit.yguniv.vo.ProjectChatMessageVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final ProjectChatMessageService service;
	private final ProjectMemberService projectMemberService;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	SimpMessagingTemplate temp;
	
	// 클라이언트에서 메시지 발행 경로: /app/chat/send
    @MessageMapping("/chat/send")
    //@SendTo("/topic/chat")
    public void sendMessage(@Payload ProjectChatMessageVO message) throws JsonMappingException, JsonProcessingException {
    	
        message.setProjectchatmessageDt(LocalDateTime.now()); // 메시지 전송 시간 설정
        
        // 메시지 저장
        service.sendMessage(message);
        
        //return mapper.readValue(message, ProjectChatMessageVO.class);
    	
    	temp.convertAndSend("/topic/chat",message);
    }
    
    // 사용자 입장 (STOMP 처리)
    @MessageMapping("/chat/join")
    @SendTo("/topic/chat")//구독경로는 생략 x
    public ProjectChatMessageVO joinChat(@Payload ProjectChatMessageVO message) {
    	
    	naming(message);
        message.setProjectchatmessageContent(message.getProjectchatmessageSender() + "님이 입장하셨습니다.");
        message.setProjectchatmessageDt(LocalDateTime.now());
        
        //입장은 별도의 저장처리 없이
        return message;
    }
	
	public void naming(ProjectChatMessageVO message) {
		String id = message.getProjectchatmessageSender();
		
		ProjectMemberVO projectMember = new ProjectMemberVO();
    	projectMember.setStuId(id);
    	projectMember = projectMemberService.readProjectMember(projectMember);
    	
    	message.setProjectchatmessageSender(projectMember.getNm());
	}
}
