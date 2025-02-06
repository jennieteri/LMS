package kr.or.ddit.yguniv.projectMember.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProjectMemberVO;

@Mapper
public interface ProjectMemberMapper {
	/**프로젝트 멤버 생성
	 * @return
	 */
	public int insertProjectMember(ProjectMemberVO projectMember);
	
	/** 프로젝트멤버상세조회
	 * @return
	 */
	public ProjectMemberVO selectProjectMember(ProjectMemberVO projectMember);
	
	/** 프로젝트멤버목록조회
	 * @return
	 */
	public List<ProjectMemberVO> selectProjectMemberList(String teamCd);
	
	/** 프로젝트멤버수정
	 * @return
	 */
	public int updateProjectMember(ProjectMemberVO projectMember);
	
	/**프로젝트멤버삭제
	 * @return
	 */
	public int deleteProjectMember(ProjectMemberVO projectMember);
	
	/** 대표자여부수정
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
