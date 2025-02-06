package kr.or.ddit.yguniv.projectMember.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.projectMember.dao.ProjectMemberMapper;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
	private final ProjectMemberMapper mapper;

	@Override
	public int createProjectMember(ProjectMemberVO projectMember) {
		String stuId = projectMember.getStuId();
		String teamCd = projectMember.getTeamCd();
		
		if(stuId==null||stuId.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 학번");
		}
		if(teamCd==null||teamCd.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 팀번호");
		}
		
		return mapper.insertProjectMember(projectMember);
	}

	@Override
	public ProjectMemberVO readProjectMember(ProjectMemberVO projectMember) {
		return mapper.selectProjectMember(projectMember);
	}

	@Override
	public List<ProjectMemberVO> readProjectMemberList(String teamCd) {
		return mapper.selectProjectMemberList(teamCd);
	}

	@Override
	public int modifyProjectMember(ProjectMemberVO projectMember) {
		return mapper.updateProjectMember(projectMember);
	}

	@Override
	public int removeProjectMember(ProjectMemberVO projectMember) {
		String stuId = projectMember.getStuId();
		
		if(stuId==null||stuId.isEmpty()) {
			throw new PKNotFoundException("필수값 누락: 학번");
		}
		
		return mapper.deleteProjectMember(projectMember);
	}

	@Override
	public int updateLeadYn(ProjectMemberVO projectMember) {
		return mapper.updateLeadYn(projectMember);
	}

	@Override
	public int updatePeerYn(String stuId) {
		
		return mapper.updatePeerYn(stuId);
	}

}
