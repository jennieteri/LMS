package kr.or.ddit.yguniv.club.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ClubVO;

@Mapper
public interface ClubMapper {
	
	/**
	 * 동아리 추가
	 * @param club
	 */
	public void insertClub(ClubVO club);
	
	/**
	 * 동아리 상세 조회
	 * @param clubNo
	 * @return
	 */
	public ClubVO selectClub(String clubNo);
	
	/**
	 * 동아리 전체 리스트 조회
	 * @return
	 */
	public List<ClubVO>selectClubList();
	
	/**
	 * 동아리 수정
	 * @param Club
	 */
	public void updateClub(ClubVO Club);
	
	/**
	 * 동아리 삭제
	 * @param clubNo
	 */
	public void deleteClub(String clubNo);
	
}
