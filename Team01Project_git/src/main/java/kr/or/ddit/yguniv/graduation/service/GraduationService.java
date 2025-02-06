package kr.or.ddit.yguniv.graduation.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;
import kr.or.ddit.yguniv.vo.GraduationVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;

public interface GraduationService {
	/**
	 * 졸업인증제 삭제 
	 * @param gdtCd
	 * @return
	 */
	public int deletegraduation(int gdtCd);
	/**
	 * 학생 졸업인증제 수정 대기 상태 인 애들만 
	 * @param graduation
	 * @return
	 */
	public int updategraduation(GraduationVO graduation);
	/**
	 * 졸업인증제 반려
	 * @param graduation
	 * @return
	 */
	public int updategraduationToReject(GraduationVO graduation);
	/**
	 * 졸업인증제 승인
	 * @param graduation
	 */
	public int updategraduationToAccess(GraduationVO graduation);

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
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging);
	/**
	 * 교직원 학생 졸업인증제 전체 조회
	 * @return
	 */
	public List<GraduationVO>selectgraduationListByEmp(PaginationInfo<GraduationVO>paging);
	/**
	 * 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	/**
	 * 졸업인증자료 추가
	 * 
	 * @param graduation
	 */
	public int insertgraduation(GraduationVO graduation);
	/**
	 * 봉사 점수 환산
	 * @param stuId
	 * @return
	 */
	public Integer selectTotalVolunteerScore(String stuId);
	/**
	 * 학생 자기 자신 졸업인증제 전체리스트
	 * @return
	 */
	public List<GraduationVO> selectgraduationList(@Param("stuId") String stuId);
	int deletegraduation(GraduationVO graduation);

}
