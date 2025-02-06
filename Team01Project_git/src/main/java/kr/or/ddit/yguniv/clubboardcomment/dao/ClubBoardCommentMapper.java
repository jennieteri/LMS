package kr.or.ddit.yguniv.clubboardcomment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ClubBoardCommentVO;

@Mapper
public interface ClubBoardCommentMapper {
	
	/**
	 * 동아리 활동 게시판 댓글 추가
	 * @param clubBoardComment
	 */
	public void insertClubBoardComment(ClubBoardCommentVO clubBoardComment);
	
	/**
	 * 동아리 활동 게시판 댓글 상세 조회
	 * @param clbomCd
	 * @return
	 */
	public ClubBoardCommentVO selectClubBoardComment(String clbomCd);
	
	/**
	 * 동아리 활동 게시판 댓글 전체 리스트 조회
	 * @return
	 */
	public List<ClubBoardCommentVO>selectClubBoardCommentList();
	
	/**
	 * 동아리 활동 게시판 댓글 수정
	 * @param clubBoardComment
	 */
	public void updateClubBoardComment(ClubBoardCommentVO clubBoardComment);
	
	/**
	 * 동아리 활동 게시판 댓글 삭제
	 * @param clbomCd
	 */
	public void deleteClubBoardComment(String clbomCd);
}
