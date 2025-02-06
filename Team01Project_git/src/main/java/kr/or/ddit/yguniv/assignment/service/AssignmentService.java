package kr.or.ddit.yguniv.assignment.service;

import java.util.List;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.LectureVO;

public interface AssignmentService {
	
	/**존재하는 강의인지 확인
	 * @param lectNo
	 * @return
	 */
	public LectureVO checkLecture(String lectNo);
	
	/**과제 생성
	 * @param assignment
	 */
	public void createAssignment(AssignmentVO assignment);
	/**과제 상세조회
	 * @param assig
	 * @return
	 */
	public AssignmentVO readAssignment(String assigNo);
	/**논페이징 목록조회
	 * @return
	 */
	public List<AssignmentVO> readAssignmentList(String lectNo);
	/**페이징 목록조회
	 * @return
	 */
	public List<AssignmentVO> readAssignmentListPaging(PaginationInfo<AssignmentVO> paging, String lectNo);
	/**과제 수정
	 * @param assignment
	 */
	public void modifyAssignment(AssignmentVO assignment);
	/**과제 삭제
	 * @param assig
	 */
	public void removeAssignment(String assigNo);
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
	 * @param assigNo
	 * @param stuId
	 * @return
	 */
	public boolean checkSubmission(String assigNo, String stuId);
}
