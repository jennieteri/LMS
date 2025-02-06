package kr.or.ddit.yguniv.projectMember.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.ProjectMemberVO;

public interface ProjectMemberService {
	
	/**프로젝트멤버 생성
	 * @param projectMember
	 * @return
	 */
	public int createProjectMember(ProjectMemberVO projectMember);

	/**프로젝트멤버 단건조회(상세)
	 * @param projectMember
	 * @return
	 */
	public ProjectMemberVO readProjectMember(ProjectMemberVO projectMember);

	/**프로젝트멤버 목록조회
	 * @param teamCd
	 * @return
	 */
	public List<ProjectMemberVO> readProjectMemberList(String teamCd);

	/**프로젝트멤버 수정
	 * @param projectMember
	 * @return
	 */
	public int modifyProjectMember(ProjectMemberVO projectMember);

	/**프로젝트멤버 삭제
	 * @param projectMember
	 * @return
	 */
	public int removeProjectMember(ProjectMemberVO projectMember);
	
	
	/**대표자설정
	 * @param projectMember
	 * @return
	 */
	public int updateLeadYn(ProjectMemberVO projectMember);
	
	
	/**피어리뷰제출처리
	 * @param stuId
	 * @return
	 */
	public int updatePeerYn(String stuId);
}
