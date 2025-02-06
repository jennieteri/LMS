package kr.or.ddit.yguniv.absencecertificatereceipt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;

@Mapper
public interface AbsenceCertificateReceiptMapper {
	
	/**
	 * 공결인정서류 총 게시글 갯수 조회
	 * @param absenceCertificateReceiptVO
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(@Param("absenceVO") AbsenceCertificateReceiptVO absenceVO // lectNo 을 넣어야해
								, @Param("paging") PaginationInfo<AbsenceCertificateReceiptVO> paging); // sql 조건이 똑같아야해 = 페이지조건
	/**
	 * 공결인정서류 등록
	 * @param absenceCertificateReceipt
	 */
	public int insertAbsenceCertificateReceipt(AbsenceCertificateReceiptVO absenceCertificateReceipt);
	
	/**
	 * 공결인정서류 상세조회
	 * @param id
	 * @return
	 */
	public AbsenceCertificateReceiptVO selectAbsenceCertificateReceipt(String absenceCd);
	
	/**
	 * 공결인정서류 조회
	 * @param lectNo 
	 * @param absenceCertificateReceiptVO 
	 * @return
	 */
	public List<AbsenceCertificateReceiptVO> selectAbsenceCertificateReceiptList(
			@Param("absenceVO") AbsenceCertificateReceiptVO absenceVO // 맵퍼이기때문에 param을 전달해준다.
			, @Param("paging") PaginationInfo<AbsenceCertificateReceiptVO> paging
	);
	
	public List<AbsenceCertificateReceiptVO> AbsenceListDistinct();

	/**
	 * 공결인성서류 수정
	 * @param absenceCertificateReceiptVO
	 * @return
	 */
	public int updateAbsence(AbsenceCertificateReceiptVO absenceCertificateReceiptVO);
	
	/**
	 * 공결인정서류 상태수정
	 * @param absenceCertificateReceipt
	 */
	public int updateAbsenceCertificateReceipt(AbsenceCertificateReceiptVO absenceCertificateReceipt);
	
	/**
	 * 공결인정서류상태 와 출결정보 수정
	 * @param absenceCertificateReceiptVO
	 * @return
	 */
	public int updateAbsenceAndAttendance(AbsenceCertificateReceiptVO absenceCertificateReceiptVO);
	
	/**
	 * 공결인정서류 삭제
	 * @param absenceCertificateReceiptVO
	 */
	public int deleteAbsenceCertificateReceipt(String absenceCd);
	
	/** 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	
	/** 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn);
}
