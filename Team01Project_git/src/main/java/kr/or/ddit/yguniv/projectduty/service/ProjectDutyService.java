package kr.or.ddit.yguniv.projectduty.service;

import java.util.List;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.ProjectDutyVO;

public interface ProjectDutyService {

	/** 일감생성
	 * @param projectDuty
	 * @return
	 */
	public String createProjectDuty(ProjectDutyVO projectDuty);

	/** 일감조회 상세조회
	 * @param dutyNo
	 * @return
	 */
	public ProjectDutyVO readProjectDuty(String dutyNo);

	/** 일감 목록조회
	 * @param teamCd
	 * @return
	 */
	public List<ProjectDutyVO> readProjectDutyList(PaginationInfo<ProjectDutyVO> paging, String teamCd);

	/** 일감 수정
	 * @param projectDuty
	 * @return
	 */
	public int modifyProjectDuty(ProjectDutyVO projectDuty);

	/** 일감 제거
	 * @param dutyNo
	 * @return
	 */
	public int removeProjectDuty(String dutyNo);

	/**논페이징일감리스트
	 * @param dutyTeam
	 * @return
	 */
	public List<ProjectDutyVO> selectProjectDutylistNonPaging(String dutyTeam);
	
}
