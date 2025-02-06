package kr.or.ddit.yguniv.notification.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.NotificationVO;

@Mapper
public interface NotificationMapper {

	//알람을 어케할지

	//한명이 로그인 했으면 그 사람에게 뜬 알람들이 가져와야함. 알람의 갯수는 모르니까 리스트로
	public List<NotificationVO> selectNotificationList(@Param("recpId") String recpId);
	
	//알림 단건 선택
	public NotificationVO selectNotification(@Param("recpId") String recpId,@Param("notiNo") int notiNo);
	
	public int insertNotification(NotificationVO notiVO);
	public Integer updateNotification(int no);
	public Integer updateAllNotfication(String recpId);
	
	public List<NotificationVO> selectModuleNotificationList(@Param("recpId") String recpId);
	
}
