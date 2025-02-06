package kr.or.ddit.yguniv.projectteam.service;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.projectteam.dao.ProjectPersonalMapper;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectPersonalService {
	private final ProjectPersonalMapper mapper;
	
	public ProjectMemberVO selectProjectTeamWithId(ProjectMemberVO projectMember) {
		
		return mapper.selectProjectTeamWithId(projectMember);
	};
	
}
