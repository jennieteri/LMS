package kr.or.ddit.yguniv.projectTaskSubmission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProjectTaskSubmissionVO;

@Mapper
public interface ProjectTaskSubmissionMapper {
	
	/** 프로젝트과제제출생성(프로젝트과제제출처리)
	 * @param projectTaskSubmission
	 * @return
	 */
	public int insertProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/** 프로젝트과제제출단건조회(상세조회)
	 * @param tasksubNo
	 * @return
	 */
	public ProjectTaskSubmissionVO selectProjectTaskSubmission(String tasksubNo);
	
	/** 프로젝트과제제출목록조회(다건조회)
	 * @return
	 */
	public List<ProjectTaskSubmissionVO> selectProjectTaskSubmissionList(String lectNo);
	
	/** 프로젝트과제제출수정
	 * @param projectTaskSubmission
	 * @return
	 */
	public int updateProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/** 프로젝트과제제출삭제
	 * @param tasksubNo
	 * @return
	 */
	public int deleteProjectTaskSubmission(String tasksubNo);
	
	/** 채점처리
	 * @param tasksubScore
	 * @return
	 */
	public int updateGradeProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/**제출확인
	 * @param teamCd
	 * @return
	 */
	public ProjectTaskSubmissionVO checkSubmit(String teamCd);
	
	/**상호평가제출처리
	 * @param projectTaskSubmission
	 * @return
	 */
	public int submitPeer(ProjectTaskSubmissionVO projectTaskSubmission);
	
}
