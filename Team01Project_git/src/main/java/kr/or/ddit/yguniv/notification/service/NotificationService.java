package kr.or.ddit.yguniv.notification.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.yguniv.notification.dao.NotificationMapper;
import kr.or.ddit.yguniv.vo.NotificationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationMapper dao;
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	// 첫 연결 수립. dd
	public SseEmitter createEmitter(String userId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitters.put(userId, emitter);

		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));

		// 연결 즉시 더미 이벤트 전송
		sendNotification(userId, "SSE 연결이 설정되었습니다.");

		return emitter;
	}

	// 첫 연결 수립 후 데이터 전송. - 기존 알림 //기존에 잇는 정보
	public void sendNotification(String userId, String message) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			try {
				Map<String, Object> data = new HashMap<>();
				List<NotificationVO> notiList = dao.selectNotificationList(userId);
				List<NotificationVO> moduleList = dao.selectModuleNotificationList(userId);
				log.info("{}", notiList);
				data.put("message", message);
				data.put("timestamp", System.currentTimeMillis());
				data.put("list", notiList);
				data.put("moduleList", moduleList);
				emitter.send(SseEmitter.event().data(data));
			} catch (IOException e) {
				emitters.remove(userId);
			}
		}
	}
	// 새로운 알림 하나만 보냄
	public void sendOneNotification(String userId, String message,int notiNo) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			try {
				Map<String, Object> data = new HashMap<>();
				NotificationVO notiOne = dao.selectNotification(userId,notiNo);
				List<NotificationVO> moduleList = dao.selectModuleNotificationList(userId);
				log.info("{}", notiOne);
				data.put("message", message);
				data.put("timestamp", System.currentTimeMillis());
				data.put("moduleList", moduleList);
				data.put("one", notiOne);
				emitter.send(SseEmitter.event().data(data));
			} catch (IOException e) {
				emitters.remove(userId);
			}
		}
	}

	// 새로운 알림 생성 및 전송
	public void createAndSendNotification(String sendId, List<String> recdIdList, 
										  String message, String notiCd, String url,String head) {
		// 1. 새 알림 생성
		for (String recdId : recdIdList) {

			NotificationVO newNotification = new NotificationVO();
			newNotification.setRecpId(recdId);
			newNotification.setSendId(sendId);
			newNotification.setNotiCn(message);
			newNotification.setNotiCd(notiCd);
			newNotification.setNotiUrl(url);
			newNotification.setNotiHead(head);

			int result = dao.insertNotification(newNotification);
			
			if(result>0) {
		        int newNotiNo = newNotification.getNotiNo();
		        if (newNotiNo > 0) {
		        	sendOneNotification(recdId, "새로운 알림이 도착했습니다. 알림 번호: " + newNotiNo,newNotiNo);
		            log.info("○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○ notiNo 값{}",newNotiNo);
		        }
			}
		}
	}
	
	public void updateReadNotfication(String id ,int lectNo) {
		Integer newNo = dao.updateNotification(lectNo);
		
		if (newNo == 1) {
			sendNotification(id, "새로운 알림이 도착했습니다.");
		}
	}
	public void updateAllNotfication(String recpId) {
	    Integer newNo = dao.updateAllNotfication(recpId);
	    
	    if (newNo > 0) {
	        // 모든 알림을 제거한 후 새로운 알림 전송
	        sendNotification(recpId, "기존 알림을 제거했습니다.");
	    }
	}


}













