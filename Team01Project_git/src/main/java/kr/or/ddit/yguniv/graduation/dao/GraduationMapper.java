package kr.or.ddit.yguniv.graduation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.GraduationVO;

@Mapper
public interface GraduationMapper {
	/**
	 * 졸업인증제 삭제 
	 * @param getCd
	 * @return
	 */
	public int deletegraduation(int gdtCd);
	/**
	 * 
	 * @param gdtCd
	 * @return
	 */
	public String selectCocoCdByCd(int gdtCd);
	/**
	 * 졸업인증제 수정
	 * @param graduation
	 * @return
	 */
	public int updategraduation(GraduationVO graduation);
	/**
	 * 상세보기
	 * @param gdtCd
	 * @return
	 */
	public GraduationVO selectgraduationByCd(int gdtCd);
	/**
	 * 게시글 목록수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<GraduationVO> paging);
	/**
	 * 교직원 학생 졸업인증제 전체 조회
	 * @return
	 */
	public List<GraduationVO>selectgraduationListByEmp(PaginationInfo<GraduationVO>paging);
	/**
	 * 봉사 점수 환산
	 * @param stuId
	 * @return
	 */
	public int selectTotalVolunteerScore(String stuId);
	/**
	 * 졸업인증자료 추가
	 * 
	 * @param graduation
	 */
	public int insertgraduation(GraduationVO graduation);

	/**
	 * 졸업인증제 상세보기
	 * 
	 * @param gdtCd
	 * @return
	 */
	public GraduationVO selectgraduation(String gdtCd);

	/**
	 * 학생 자기 자신 졸업인증제 전체리스트
	 * 
	 * @return
	 */
	public List<GraduationVO> selectgraduationList(@Param("stuId") String stuId);
	/**
	 * 졸업인증제 승인
	 * @param graduation
	 */
	public int updategraduationToAccess(GraduationVO graduation);
	/**
	 * 졸업인증제 반려
	 * @param graduation
	 * @return
	 */
	public int updategraduationToReject(GraduationVO graduation);

	/**
	 * 졸업인증제 삭제
	 * 
	 * @param gdtCd
	 */
	public void deletegraduation(String gdtCd);
}
