package kr.or.ddit.yguniv.anonymousboardreply.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AnonymousBoardReplyVO;

@Mapper
public interface AnonymousBoardReplyMapper {
	
	/**
	 * 익명 게시물 대댓글 추가
	 * @param anonymousboardreply
	 */
	public void insertAnonymousBoardReply(AnonymousBoardReplyVO anonymousBoardReply);
	
	/**
	 * 익명 게시물 대댓글 상세 조회
	 * @param abrepCd
	 * @return
	 */
	public AnonymousBoardReplyVO selectAnonymousBoardReply(String abrepCd);
	
	/**
	 * 익명 게시물 대댓글 전체 리스트 조회
	 * @return
	 */
	public List<AnonymousBoardReplyVO>selectAnonymousBoardReplyList();
	
	/**
	 * 익명 게시물 대댓글 수정
	 * @param Anonymousboardreply
	 */
	public void updateAnonymousBoardReply(AnonymousBoardReplyVO anonymousBoardReply);
	
	/**
	 * 익명 게시물 대댓글 삭제
	 * @param abrepCd
	 */
	public void deleteAnonymousBoardReply(String abrepCd);
	
}
