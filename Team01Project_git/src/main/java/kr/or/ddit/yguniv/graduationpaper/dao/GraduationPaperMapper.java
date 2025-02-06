package kr.or.ddit.yguniv.graduationpaper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;
import kr.or.ddit.yguniv.vo.GraduationVO;

@Mapper
public interface GraduationPaperMapper {
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
	 * 게시글 목록수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<GraduationVO> paging);
	/**
	 * 학생 자기자신 졸업논문 모아보기 
	 * @return
	 */
	public List<GraduationPaperVO>getGraduationPapers(String stuId);
	/**
	 * 졸업논문 추가
	 * @param graduationpaper
	 */
	public int insertGraduationPaper(GraduationPaperVO graduationpaper);
	/**
	 * 졸업논문 상세보기
	 * @param gpaCd
	 * @return
	 */
	public GraduationPaperVO selectgraduationpaper(String gpaCd);

	/**
	 * 졸업논문 전체 리스트 
	 * @return
	 */
	public List<GraduationPaperVO> selectgraduationpaperList();

	/**
	 * 졸업논문 수정
	 * @param graduationpaper
	 */
	public void updategraduationpaper(GraduationPaperVO graduationpaper);

	/**
	 * 졸업논문 삭제
	 * @param gpaCd
	 */
	public void deletegraduationpaper(String gpaCd);
}
