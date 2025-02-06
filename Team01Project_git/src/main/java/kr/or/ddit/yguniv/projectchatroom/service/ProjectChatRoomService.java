package kr.or.ddit.yguniv.projectchatroom.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;

public interface ProjectChatRoomService {
	
	public int createProjectChatRoom(ProjectChatRoomVO projectChatRoom);

	public ProjectChatRoomVO readProjectChatRoom(String teamCd);

	public List<ProjectChatRoomVO> readProjectChatRoomList(String taskNo);

	public int updateProjectChatRoom(ProjectChatRoomVO projectChatRoom);

	public int deleteProjectChatRoom(String teamCd);
}
