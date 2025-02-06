package kr.or.ddit.yguniv.anonymousboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.AnonymousBoardVO;

@Mapper
public interface AnonymousBoardMapper {
	
	/**
	 * 익명 게시물 등록
	 * @param anonymousBoard
	 */
	public void insertAnonymousBoard(AnonymousBoardVO anonymousBoard);
	
	/**
	 * 익명 게시물 상세 조회
	 * @param nick
	 * @return
	 */
	public AnonymousBoardVO selectAnonymousBoard(String ambCd);
	
	/**
	 * 익명 게시물 전체 리스트 조회
	 * @return
	 */
	public List<AnonymousBoardVO>selectAnonymousBoardList();
	
	/**
	 * 익명 게시물 수정
	 * @param anonymousBoard
	 */
	public void updateAnonymousBoard(AnonymousBoardVO anonymousBoard);
	
	/**
	 * 익명 게시물 삭제
	 * @param nick
	 */
	public void deleteAnonymousBoard(String ambCd);
	
}
