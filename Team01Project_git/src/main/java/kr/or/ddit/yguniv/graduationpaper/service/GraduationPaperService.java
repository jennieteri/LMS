package kr.or.ddit.yguniv.graduationpaper.service;

import java.util.List;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;

public interface GraduationPaperService {
	/**
	 * 교수 학생 졸업논문 승인
	 * @param gpaCd
	 * @return
	 */
	public int updategraduationPaperToAccess(int gpaCd);
	/**
	 * 교수 학생 졸업논문 보기
	 * @return
	 */
	public List<GraduationPaperVO>getProfessorStudentsPapers(String profId);
	/**
	 * 상세보기 논문 하나 가져오기
	 * @param gpaCd
	 * @return
	 */
	public GraduationPaperVO getPaperById(int gpaCd);
	/**
	 * 졸업논문 추가
	 * @param graduationpaper
	 */
	public int insertGraduationPaper(GraduationPaperVO graduationpaper);
	/**
	 * 게시글 목록수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging);
	/**
	 * 학생 자기자신 졸업논문 모아보기 
	 * @return
	 */
	public List<GraduationPaperVO>getGraduationPapers(String stuId);
	/**
	 * 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
}
