package kr.or.ddit.yguniv.projectteam.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.ProjectTeamFormVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;

public interface ProjectTeamService {
	/** 프로젝트 팀 생성
	 * @param projectTeam
	 * @return
	 */
	public int createProjectTeam(ProjectTeamFormVO projectTeam);
	/** 프로젝트팀 단건조회(상세조회)
	 * @param teamCd
	 * @return
	 */
	public ProjectTeamVO readProjectTeam(String teamCd);
	/** 프로젝트 팀 다건조회(목록조회)
	 * @param taskNo
	 * @return
	 */
	public List<ProjectTeamVO> readProjectTeamList(String taskNo);
	/** 프로젝트 팀 수정(일반수정 주제/제목 등)
	 * @param projectTeam
	 * @return
	 */
	public int modifyProjectTeam(ProjectTeamVO projectTeam);
	/** 프로젝트 팀 제거
	 * @param teamCd
	 * @return
	 */
	public int removeProjectTeam(String teamCd);
	/** 프로젝트 제출/회수 처리
	 * @param teamCd
	 * @return
	 */
	public int updateStatus(String teamCd);
	/** 프로젝트 상호평가 완료처리
	 * @param teamCd
	 * @return
	 */
	public int updateEvyn(String teamCd);
	/** 프로젝트 진척도 변경(진행률 반영처리)
	 * @param projectTeam
	 * @return
	 */
	public int updateProge(ProjectTeamVO projectTeam);
	
	/**현재 개설된 프로젝트팀 중 프로젝트 멤버가 없는 팀의 갯수
	 * @param taskNo
	 * @return
	 */
	public int countProjectTeamNoMember(String taskNo);
	
	/**팀명 변경
	 * @param projectTeam
	 * @return
	 */
	public int updateTeamNm(ProjectTeamVO projectTeam);
	
	/**팀원 자동배정
	 * @param projectTeamForm
	 * @return
	 */
	public List<ProjectTeamVO> autoAllot(ProjectTeamFormVO projectTeamForm);
	
	
	/**팀 초기화
	 * @param taskNo
	 * @return
	 */
	public int resetTeamMember(String taskNo);
	
}
