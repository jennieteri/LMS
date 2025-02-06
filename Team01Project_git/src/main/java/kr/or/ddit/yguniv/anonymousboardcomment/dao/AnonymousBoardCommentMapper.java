package kr.or.ddit.yguniv.anonymousboardcomment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AnonymousBoardCommentVO;

@Mapper
public interface AnonymousBoardCommentMapper {
	
	/**
	 * 익명 게시물 댓글 추가
	 * @param anonymousboardcomment
	 */
	public void insertAnonymousBoardComment(AnonymousBoardCommentVO anonymousBoardComment);
	
	/**
	 * 익명 게시물 댓글 상세 조회
	 * @param cbcomCd
	 * @return
	 */
	public AnonymousBoardCommentVO selectAnonymousBoardComment(String cbcomCd);
	
	/**
	 * 익명 게시물 댓글 전체 리스트 조회
	 * @return
	 */
	public List<AnonymousBoardCommentVO>selectAnonymousBoardCommentList();
	
	/**
	 * 익명 게시물 댓글 수정
	 * @param Anonymousboardcomment
	 */
	public void updateAnonymousBoardComment(AnonymousBoardCommentVO anonymousBoardComment);
	
	/**
	 * 익명 게시물 댓글 삭제
	 * @param cbcomCd
	 */
	public void deleteAnonymousBoardComment(String cbcomCd);
	
}
