package kr.or.ddit.yguniv.projectteam.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProjectMemberVO;

@Mapper
public interface ProjectPersonalMapper {
	/**접속자 프로젝트조회
	 * @param id
	 * @return
	 */
	public ProjectMemberVO selectProjectTeamWithId(ProjectMemberVO projectMember);
	
}
