package kr.or.ddit.yguniv.projectchatroom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;

@Mapper
public interface ProjectChatRoomMapper {
	
	/**채팅방생성
	 * @param chatroom
	 * @return
	 */
	public int insertProjectChatRoom(ProjectChatRoomVO chatroom);
	/**자기팀 채팅방조회
	 * @param teamCd
	 * @return
	 */
	public ProjectChatRoomVO selectProjectChatRoom(String teamCd);
	/**채팅방 목록조회
	 * @return
	 * 
	 */
	public List<ProjectChatRoomVO> selectProjectChatRoomlist(String taskNo);
	/**채팅방 수정
	 * @param chatroom
	 * @return
	 */
	public int updateProjectChatRoom(ProjectChatRoomVO chatroom);
	/**채팅방 삭제
	 * @param teamCd
	 * @return
	 */
	public int deleteProjectChatRoom(String teamCd);
	
	
	/**채팅방 개설 전 해당 팀 채팅방 있는지 확인
	 * @param teamCd
	 * @return
	 */
	public int checkRoomCount(String teamCd);
	
	/**채팅방 개설 전 동일한 채팅방 명 있는지 확인
	 * @param projectchatroomTitle
	 * @return
	 */
	public int checkRoomNameCount(String projectchatroomTitle);
	
}
