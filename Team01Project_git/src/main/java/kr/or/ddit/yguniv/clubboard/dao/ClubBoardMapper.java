package kr.or.ddit.yguniv.clubboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ClubBoardVO;

@Mapper
public interface ClubBoardMapper {
	
	/**
	 * 동아리 활동 게시판 추가
	 * @param clubboard
	 */
	public void insertClubBoard(ClubBoardVO clubBoard);
	
	/**
	 * 동아리 활동 게시판 상세 조회
	 * @param clboaCd
	 * @return
	 */
	public ClubBoardVO selectClubBoard(String clboaCd);
	
	/**
	 * 동아리 활동 게시판 전체 리스트 조회
	 * @return
	 */
	public List<ClubBoardVO>selectClubBoardList();
	
	/**
	 * 동아리 활동 게시판 수정
	 * @param clubboard
	 */
	public void updateClubBoard(ClubBoardVO clubBoard);
	
	/**
	 * 동아리 활동 게시판 삭제
	 * @param clboaCd
	 */
	public void deleteClubBoard(String clboaCd);
	
}
