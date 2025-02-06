package kr.or.ddit.yguniv.projectchatroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.exception.PKDuplicatedException;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectchatroom.dao.ProjectChatRoomMapper;
import kr.or.ddit.yguniv.vo.ProjectChatRoomVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectChatRoomServiceImpl implements ProjectChatRoomService {
	private final ProjectChatRoomMapper mapper;

	@Override
	public int createProjectChatRoom(ProjectChatRoomVO projectChatRoom) {
		// 동일한 채팅방명 있는지?
		// 해당 팀의 채팅방이 이미 존재하는지??
		String teamCd = projectChatRoom.getTeamCd();
		String projectchatroomTitle = projectChatRoom.getProjectchatroomTitle();
		
		if(mapper.checkRoomCount(teamCd)>0) {
			throw new PKDuplicatedException("이미 해당팀의 채팅방이 존재합니다.");
		}
		
		if(mapper.checkRoomNameCount(projectchatroomTitle)>0) {
			throw new BoardException("동일 채팅방 이름이 이미 존재합니다.");
		}
		
		return mapper.insertProjectChatRoom(projectChatRoom);
	}

	@Override
	public ProjectChatRoomVO readProjectChatRoom(String teamCd) {
		// TODO Auto-generated method stub
		return mapper.selectProjectChatRoom(teamCd);
	}

	@Override
	public List<ProjectChatRoomVO> readProjectChatRoomList(String taskNo) {
		// TODO Auto-generated method stub
		return mapper.selectProjectChatRoomlist(taskNo);
	}

	@Override
	public int updateProjectChatRoom(ProjectChatRoomVO projectChatRoom) {
		// TODO Auto-generated method stub
		return mapper.updateProjectChatRoom(projectChatRoom);
	}

	@Override
	public int deleteProjectChatRoom(String teamCd) {
		// TODO Auto-generated method stub
		return mapper.deleteProjectChatRoom(teamCd);
	}
	
	
	

}
