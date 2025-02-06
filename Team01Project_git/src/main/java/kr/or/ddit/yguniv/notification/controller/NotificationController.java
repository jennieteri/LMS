package kr.or.ddit.yguniv.notification.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.vo.NotificationVO;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	  @Autowired
	    private NotificationService notificationService;

	    @GetMapping("stream/{userId}")
	    public SseEmitter streamNotifications(@PathVariable String userId) {
	        return notificationService.createEmitter(userId);
	    }

	    @PostMapping("test/{userId}")
	    public ResponseEntity<?> sendTestNotification(@PathVariable String userId) {
	        notificationService.sendNotification(userId, "Service 이것은 테스트 알림입니다.");
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Controller Map : 테스트 알림이 전송되었습니다.");
	        return ResponseEntity.ok().body(response);
	    }
	    //읽음 표시 하기
	    @GetMapping("test/{notiNo}")
	    public ResponseEntity<?> updateReadNotfication(@PathVariable int notiNo,Principal prin) {
	    	notificationService.updateReadNotfication(prin.getName(),notiNo);
	    	Map<String, String> response = new HashMap<>();
	    	response.put("status", "success");
	    	response.put("message", "Controller Map : 테스트 알림이 전송되었습니다.");
	    	return ResponseEntity.ok().body(response);
	    }
	    
	    //전체 읽기 전용
	    @GetMapping("test/all")
	    public ResponseEntity<?> updateAllNotfication(Principal prin) {
	        notificationService.updateAllNotfication(prin.getName());
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "모든 알림이 처리되었습니다.");
	        return ResponseEntity.ok().body(response);
	    }
	    //전체 읽기 전용


	    
	    
	    
	    
}


