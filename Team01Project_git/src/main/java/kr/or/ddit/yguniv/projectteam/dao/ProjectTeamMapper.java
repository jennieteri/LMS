package kr.or.ddit.yguniv.projectteam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.ProjectTeamVO;

@Mapper
public interface ProjectTeamMapper {
	/**팀생성
	 * @return
	 */
	public int insertProjectTeam(ProjectTeamVO projectTeam);
	/**팀단건조회
	 * @return
	 */
	public ProjectTeamVO selectProjectTeam(String teamCd);
	/**하나의 강의안에 과제에 속한 팀전체조회
	 * @return
	 */
	public List<ProjectTeamVO> selectProjectTeamlistWithTask(String taskNo);
	/**팀수정
	 * @return
	 */
	public int updateProjectTeam(ProjectTeamVO projectTeam);
	/**팀삭제
	 * @return
	 */
	public int deleteProjectTeam(String teamCd);
	
	/**프로젝트상태변경(제출처리)
	 * @param teamCd
	 * @return
	 */
	public int updateStatus(ProjectTeamVO projectTeam);
	
	/**상호평가완료여부변경(완료처리)
	 * @param teamCd
	 * @return
	 */
	public int updateEvyn(String teamCd);
	
	
	/**과제진행도변경처리(진행률)
	 * @param teamCd
	 * @return
	 */
	public int updateProge(ProjectTeamVO team);
	
	/**현재 개설된 프로젝트팀 중 프로젝트 멤버가 없는 팀의 갯수
	 * @param taskNo
	 * @return
	 */
	public int countProjectTeamNoMember(String taskNo);
	
	
	
	/**단순팀명수정
	 * @param projectTeam
	 * @return
	 */
	public int updateTeamNm(ProjectTeamVO projectTeam);
	
	/**팀 초기화
	 * @param taskNo
	 * @return
	 */
	public int resetTeamMember(String taskNo);
}
