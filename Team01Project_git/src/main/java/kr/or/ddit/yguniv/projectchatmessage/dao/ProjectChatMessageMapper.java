package kr.or.ddit.yguniv.projectchatmessage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProjectChatMessageVO;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;

@Mapper
public interface ProjectChatMessageMapper {
	
	/** 메세지 보내기 DB저장
	 * @param message
	 * @return
	 */
	public int insertProjectChatMessage(ProjectChatMessageVO message);
	
	/** 메세지 받기 DB대화내역가져오기(입장)
	 * @return
	 */
	public List<ProjectChatMessageVO> selectProjectChatMessageList(ProjectChatRoomVO room);
	
	/** 메시지 전송취소(삭제)작성자만 가능해야함.
	 * @param messageId
	 * @return
	 */
	public int deleteProjectChatMessage(String messageId);
	
	
	/**Person정보 가져오기(필요값만)
	 * @param stuId
	 * @return
	 */
	public PersonVO selectPerson(String stuId);
}
