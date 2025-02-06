package kr.or.ddit.yguniv.projectchatmessage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.projectchatmessage.service.ProjectChatMessageService;
import kr.or.ddit.yguniv.vo.ProjectChatMessageVO;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;

@Controller
@RequestMapping("/projectChatMessage")
public class ProjectChatMessageController {
	
	@Autowired
	ProjectChatMessageService service;
	
	//채팅방 입장 시 해당 채팅방 메시지내역 조회
	@GetMapping("{taskNo}/{teamCd}")
	public ResponseEntity<List<ProjectChatMessageVO>> getChat(
			@PathVariable String taskNo
			,@PathVariable String teamCd
			) {
		ProjectChatRoomVO room = new ProjectChatRoomVO();
		room.setTaskNo(taskNo);
		room.setTeamCd(teamCd);
		
		// 서비스 호출
        List<ProjectChatMessageVO> chatList;
        try {
            chatList = service.getHistory(room);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
		
		return ResponseEntity.ok(chatList);
	}
	
	@DeleteMapping("{projectchatmessageId}")
	@ResponseBody
	public ResponseEntity<Object> deleteChat(
			@PathVariable String projectchatmessageId
			){
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		try {
			service.removeMessage(projectchatmessageId);
			body.put("message", "메시지가 성공적으로 삭제되었습니다.");
		}catch(RuntimeException e) {
			status = HttpStatus.BAD_REQUEST;
			body.put("message", "메시지 삭제실패!");
		}
		
		
		return ResponseEntity.status(status).body(body);
	}
	
}
