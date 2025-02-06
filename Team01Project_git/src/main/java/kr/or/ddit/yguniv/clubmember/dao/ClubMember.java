package kr.or.ddit.yguniv.clubmember.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ClubMemberVO;

@Mapper
public interface ClubMember {
	
	/**
	 * 동아리원 추가
	 * @param clubMember
	 */
	public void insertClubMember(ClubMemberVO clubMember);
	
	/**
	 * 동아리원 상세 조회
	 * @param id
	 * @return
	 */
	public ClubMemberVO selectClubMember(String id);
	
	/**
	 * 동아리원 전체 리스트 조회
	 * @return
	 */
	public List<ClubMemberVO>selectClubMemberList();
	
	/**
	 * 동아리원 수정
	 * @param clubMember
	 */
	public void updateClubMember(ClubMemberVO clubMember);
	
	/**
	 * 동아리원 삭제
	 * @param id
	 */
	public void deleteClubMember(String id);
	
}
