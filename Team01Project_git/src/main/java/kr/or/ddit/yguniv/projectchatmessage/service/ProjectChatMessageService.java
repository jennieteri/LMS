package kr.or.ddit.yguniv.projectchatmessage.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.ProjectChatMessageVO;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;

public interface ProjectChatMessageService {
	
	/** 메세지보내기(저장insert)
	 * @return
	 */
	public int sendMessage(ProjectChatMessageVO message);

	/**이전대화내역가져오기
	 * @return
	 */
	public List<ProjectChatMessageVO> getHistory(ProjectChatRoomVO room);

	/**메시지삭제
	 * @return
	 */
	public int removeMessage(String messageId);
}
