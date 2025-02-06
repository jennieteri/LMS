package kr.or.ddit.yguniv.projectTaskSubmission.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.ProjectTaskSubmissionVO;

public interface ProjectTaskSubmissionService {
	
	/** 프로젝트과제제출생성(프로젝트과제제출처리)
	 * @param projectTaskSubmission
	 * @return
	 */
	public int createProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/** 프로젝트과제제출단건조회(상세조회)
	 * @param tasksubNo
	 * @return
	 */
	public ProjectTaskSubmissionVO readProjectTaskSubmission(String tasksubNo);
	
	/** 프로젝트과제제출목록조회(다건조회)
	 * @return
	 */
	public List<ProjectTaskSubmissionVO> readProjectTaskSubmissionList(String taskNo);
	
	/** 프로젝트과제제출수정
	 * @param projectTaskSubmission
	 * @return
	 */
	public int modifyProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/** 프로젝트과제제출삭제
	 * @param tasksubNo
	 * @return
	 */
	public int removeProjectTaskSubmission(String tasksubNo);
	
	/** 채점처리
	 * @param tasksubScore
	 * @return
	 */
	public int updateGradeProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission);
	
	/** 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	/** 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn);
	
	/**제출여부확인
	 * @param teamCd
	 * @return
	 */
	public ProjectTaskSubmissionVO checkSubmit(String teamCd);
	
	/**상호평가제출
	 * @param projectTaskSubmission
	 * @return
	 */
	public int peerSubmit(ProjectTaskSubmissionVO projectTaskSubmission, String stuId);
	
}
