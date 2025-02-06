package kr.or.ddit.yguniv.assignment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.LectureVO;

@Mapper
public interface AssignmentMapper {
	
	/**해당 강의 존재여부 확인 및 강의명 확인
	 * @param lectNo
	 * @return
	 */
	public LectureVO checkLecture(String lectNo);
	
	/** 과제생성
	 * @param assignment
	 * @return
	 */
	public int insertAssignment(AssignmentVO assignment);
	
	/**전체조회 페이징
	 * @param paging
	 * @return
	 */
	public List<AssignmentVO> selectAssignmentListPaging(@Param("paging") PaginationInfo<AssignmentVO> paging, @Param("lectNo")String lectNo);
	
	/** 전체조회
	 * @return
	 */
	public List<AssignmentVO> selectAssignmentList(String lectNo);
	
	/** 단건조회
	 * @param assigNo
	 * @return
	 */
	public AssignmentVO selectAssignment(String assigNo);
	
	
	/**글 목록수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(@Param("paging")PaginationInfo<AssignmentVO> paging, @Param("lectNo")String lectNo);
	
	/** 과제수정
	 * @param assignment
	 * @return
	 */
	public int updateAssignment(AssignmentVO assignment);
	
	/** 과제삭제
	 * @param assigNo
	 * @return
	 */
	public int deleteAssignment(String assigNo);
	
	/**조회수 증가
	 * @param assigNo
	 * @return
	 */
	public int incrementCnt(String assigNo);
	
	
	/**제출여부체크
	 * @param assigNo
	 * @param stuId
	 * @return
	 */
	public int checkSubmission(@Param("assigNo") String assigNo,@Param("stuId") String stuId);
}
