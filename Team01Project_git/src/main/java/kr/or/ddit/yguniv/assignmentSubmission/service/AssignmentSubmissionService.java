package kr.or.ddit.yguniv.assignmentSubmission.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.SerchMappingVO;

public interface AssignmentSubmissionService {                     
	/**제출과제생성 Assignment 생성시 거기에서 호출
	 * @param assignmentSubmission
	 */
	public void createAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	/**단건조회
	 * @param search
	 * @return
	 */
	public AssignmentSubmissionVO readAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	/**다건조회
	 * @param search
	 * @return
	 */
	public List<AssignmentSubmissionVO>  readAssignmentSubmissionlist(AssignmentSubmissionVO assignmentSubmission);
	/**수정
	 * @param assignmentSubmission
	 */
	public void modifyAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	/**삭제
	 * @param search
	 */
	public void removeAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
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
	
	/**점수입력
	 * @param assignmentSubmission
	 * @return
	 */
	public int createGrade(AssignmentSubmissionVO assignmentSubmission);
	
	
	/**피어리뷰작성시 해당 수강생제출정보에 업데이트
	 * @param assignmentSubmission
	 * @return
	 */
	public int updatePeerScr(AssignmentSubmissionVO assignmentSubmission);
}
