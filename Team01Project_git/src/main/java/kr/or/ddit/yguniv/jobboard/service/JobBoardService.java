package kr.or.ddit.yguniv.jobboard.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.JobBoardVO;

public interface JobBoardService {
	/**
	 * 참석한 학생
	 * @return
	 */
	public int isUserRegisteredForJobBoard(String stuId,String jobNo);
	/**
	 * 취업정보게시판 통계
	 * @return
	 */
	public List<Map<String, Object>>jobBoardStatics();
	/**
	 * 게시글 전체 출력
	 * @param paginationInfo
	 * @return
	 */
	public List<JobBoardVO>selectJobBoardListPaging(PaginationInfo<JobBoardVO>paginationInfo);
	/**
	 * 게시글 상세 보기
	 * @param jobNo
	 * @return
	 */
	public JobBoardVO selectJobBoard(String jobNo);
	/**
	 * 게시글 생성
	 * @param board
	 */
	public void insertJobBoard(JobBoardVO board);
	/**
	 * 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	
	/**
	 * 조회수 카운트 
	 * @param jobNo
	 * @return
	 */
	public int jobCount(String jobNo);
	
	/**
	 * 게시글 수정
	 * @param board
	 */
	public void updateJobBoard(JobBoardVO board);
	/**
	 * 게시글 삭제
	 * @param board
	 * @return 
	 */
	public ServiceResult deletejobBoard(JobBoardVO board);
}
