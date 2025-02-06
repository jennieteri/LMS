package kr.or.ddit.yguniv.vo;

import java.util.List;

import lombok.Data;

@Data
public class NotificationDTO {
	List<NotificationVO> headerNotificationList;
	List<NotificationVO> moduleNotificationList;
	
}
