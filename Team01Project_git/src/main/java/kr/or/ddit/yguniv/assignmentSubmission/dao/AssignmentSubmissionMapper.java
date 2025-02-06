package kr.or.ddit.yguniv.assignmentSubmission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.SerchMappingVO;

@Mapper
public interface AssignmentSubmissionMapper {
	/**제출과제생성
	 * @param assignmentSubmission
	 * @return
	 */
	public int insertAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	
	/**피어리뷰대상자 조회
	 * @param assignmentSubmission
	 * @return
	 */
	public List<AttendeeVO> attendListNotPeer(AssignmentSubmissionVO assignmentSubmission);
	
	/**제출과제상세조회
	 * @param 
	 * @return
	 */
	public AssignmentSubmissionVO selectAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	/**제출과제목록전체조회(교수,학생)
	 * @param 
	 * @return
	 */
	public List<AssignmentSubmissionVO> selectAssignmentSubmissionList(AssignmentSubmissionVO assignmentSubmission);
	/**제출과제수정
	 * @param assignmentSubmission
	 * @return
	 */
	public int updateAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	/**제출과제삭제
	 * @param 
	 * @return
	 */
	public int deleteAssignmentSubmission(AssignmentSubmissionVO assignmentSubmission);
	
	
//	/**제출마감일 확인 ==> 서비스로직으로 처리예정
//	 * @param search 동적 검색조건
//	 * @return
//	 */
//	public AssignmentVO checkED(SerchMappingVO search);
	
	/** 중복제출확인
	 * @param search
	 * @return
	 */
	public int duplicateSubmit(AssignmentSubmissionVO assignmentSubmission);
	
	/** 채점하기
	 * @param assignmentSubmission 
	 * @return
	 */
	public int gradeScore(AssignmentSubmissionVO assignmentSubmission);
	
	
	/**피어리뷰 제출여부 업데이트처리
	 * @param search
	 * @return
	 */
	public int changePeerStatus(AssignmentSubmissionVO assignmentSubmission);
	
	/**피어리뷰작성시 해당 수강생제출정보에 업데이트
	 * @param assignmentSubmission
	 * @return
	 */
	public int updatePeerScr(AssignmentSubmissionVO assignmentSubmission);
	
	/**피어리뷰제출자 제출여부 업데이트
	 * @param assignmentSubmission
	 * @return
	 */
	public int updatePeerStatus(AssignmentSubmissionVO assignmentSubmission);
}
