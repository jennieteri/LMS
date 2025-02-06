package kr.or.ddit.yguniv.jobboard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;

@Mapper
public interface JobBoardMapper {
	/**
	 * 참석한 학생
	 * @return
	 */
	public int isUserRegisteredForJobBoard(@Param("stuId") String stuId, @Param("jobNo") String jobNo);
	/**
	 * 취업정보게시판 통계
	 * @return
	 */
	public List<Map<String, Object>>jobBoardStatistics();
	/**
	 * 취업정보 추가
	 * @param jobboard
	 */
	public void insertJobBoard(JobBoardVO jobboard);
	/**
	 * 취업정보 게시글 상세보기
	 * @param jobNo
	 * @return
	 */
	public JobBoardVO selectJobBoard(String jobNo);
	/**
	 * 취업정보 게시글 전체 리스트 조회
	 * @return
	 */
	public List<JobBoardVO>selectJobBoardListPaging(PaginationInfo<JobBoardVO>paging);
	/**
	 * 취업정보게시글 수정
	 * @param jobboard
	 */
	public void updateJobBoard(JobBoardVO jobboard);
	/**
	 * 취업정보게시글 삭제
	 * @param jobboard
	 * @return 
	 */
	public int deletejobBoard(String jobNo);
	
	/** 게시글 목록수조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<JobBoardVO> paging);
	
	/**
	 * 조회수 
	 * @param snbNo
	 * @return
	 */
	public int jobCount(String jobNo);
	

	
}
