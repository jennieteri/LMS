package kr.or.ddit.yguniv.projectchatmessage.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.projectchatmessage.dao.ProjectChatMessageMapper;
import kr.or.ddit.yguniv.utils.FindBeanChildContext;
import kr.or.ddit.yguniv.vo.ProjectChatMessageVO;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectChatMessageServiceImpl implements ProjectChatMessageService {
	private final ProjectChatMessageMapper mapper;
	
	//private SimpMessagingTemplate messagingTemplate;
	/*
	 * 	SimpMessagingTemplate 주요 메서드
		convertAndSend(String destination, Object payload):
		모든 구독자에게 메시지를 전송.
		convertAndSendToUser(String user, String destination, Object payload):
		특정 사용자에게 메시지를 전송.
	 * 
	 * 
	 */
	@Value("#{badWord.badword}")
	private String badWord;
	
	@Override
	public int sendMessage(ProjectChatMessageVO message) {
		/*
		if(messagingTemplate==null) {
			messagingTemplate = FindBeanChildContext.findBean(SimpMessagingTemplate.class);
		}
		// 메시지 브로드캐스트
		if (result > 0) {
			messagingTemplate.convertAndSend("/topic/chat/" + message.getTeamCd(), message);
		}
		*/
		 // 금지어 필터링
        String filteredContent = filterBadWords(message.getProjectchatmessageContent());
        message.setProjectchatmessageContent(filteredContent);
        
        // 보낸사람 person으로 꺼내기위해
        // 보낸사람 학번
        String stuId = message.getProjectchatmessageSender();
        message.setPerson(mapper.selectPerson(stuId));
        
        // 메시지 저장
        int result = mapper.insertProjectChatMessage(message);


        return result;
	}

	@Override
	public List<ProjectChatMessageVO> getHistory(ProjectChatRoomVO room) {
		
		return mapper.selectProjectChatMessageList(room);
	}

	@Override
	public int removeMessage(String messageId) {
		
		return mapper.deleteProjectChatMessage(messageId);
	}
	
	private String filterBadWords(String content) {
        // 금지어 목록 로드
        List<String> badWords = Arrays.asList(badWord.split(","));

        // 금지어를 `***`로 대체
        for (String badWord : badWords) {
            content = content.replaceAll("(?i)" + badWord.trim(), "***");
        }

        return content;
    }
	
	
}
