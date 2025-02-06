package kr.or.ddit.yguniv.projectduty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.ProjectDutyVO;

@Mapper
public interface ProjectDutyMapper {
	/**일감추가
	 * @param duty
	 * @return
	 */
	public int insertProjectDuty(ProjectDutyVO duty);
	/**일감상세조회
	 * @param dutyNo
	 * @return
	 */
	public ProjectDutyVO selectProjectDuty(String dutyNo);
	/**일감전체조회
	 * @return
	 */
	public List<ProjectDutyVO> selectProjectDutylist(@Param("paging") PaginationInfo<ProjectDutyVO> paging, @Param("dutyTeam") String teamCd);
	
	/** 게시글 목록수조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<ProjectDutyVO> paging );
	
	/**일감수정
	 * @param duty
	 * @return
	 */
	public int updateProjectDuty(ProjectDutyVO duty);
	/**일감삭제
	 * @param dutyNo
	 * @return
	 */
	public int deleteProjectDuty(String dutyNo);
	
	/**논페이징일감리스트
	 * @param dutyTeam
	 * @return
	 */
	public List<ProjectDutyVO> selectProjectDutylistNonPaging(String dutyTeam);
	
}
