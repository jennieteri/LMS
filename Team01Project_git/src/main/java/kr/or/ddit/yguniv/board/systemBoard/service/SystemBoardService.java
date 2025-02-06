package kr.or.ddit.yguniv.board.systemBoard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;


@Service

public interface SystemBoardService {
	
//	공지사항 게시판 게시글 등록 
	public void insertSystemBoard(SystemNoticeBoardVO systemNoticeBoard); 
	
	//	공지사항 게시판 게시글 상세 조회
	public SystemNoticeBoardVO selectSystemBoard(String snbNo);
	
	
	public List<SystemNoticeBoardVO> selectList();
	
//	공지사항 게시판 게시글 목록 조회
	public List<SystemNoticeBoardVO> selectList(PaginationInfo<SystemNoticeBoardVO>paginationInfo);

	
//페이징 처리를 위한 검색 결과 레코드 수 조회 
	public int selectTotalRecord(@Param("paging") PaginationInfo<SystemNoticeBoardVO> paginationInfo); 
	
	
//	공지사항 게시판 게시글 수정
	public void updateSystemBoard(SystemNoticeBoardVO systemNoticeBoard);
	
//	공지사항 게시판 게시글 삭제
	public void deleteSystemBoard(String snbNo);
	
// 공지사항 게시글 검색 결과 목록 조회 
	public List<SystemNoticeBoardVO> selectSystemNoticeBoardList(PaginationInfo<SystemNoticeBoardVO>paginationInfo);
	/**
	 * 파일 다운로드
	 * 
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);

	/**
	 * 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSns
	 */
	public void removeFile(int atchFileId, int fileSn);

	
	
	/**
	 * @return
	 * 메인 화면에 보여질 리스트
	 */
	public List<SystemNoticeBoardVO> mainSystemBoardList();


	

	
}
