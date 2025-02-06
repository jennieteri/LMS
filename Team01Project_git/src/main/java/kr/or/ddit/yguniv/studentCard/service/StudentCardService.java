package kr.or.ddit.yguniv.studentCard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AwardAskVO;
import kr.or.ddit.yguniv.vo.StudentCardVO;

@Service
public interface StudentCardService {
	
	// 학생증 발급 신청
	public void createStudentCard(StudentCardVO card); 

	// 학생증 신청 목록 삭제
	public void deleteStudnetCard(String stuId);
	
	// 학생증 신청 목록 조회 
	public List<StudentCardVO> selectStudentCardList(PaginationInfo<StudentCardVO> paginationInfo); 
	
	//페이징 처리 없는 목록 조회 
	public List<StudentCardVO> selectStudentCardListNonPaging();
	
	// 학생증 신청 상세 조회 
	public StudentCardVO selectStudentCardDetail(String stuId);
	
	// 학생증 신청 상태 수정
	public String updateStatus(String cocoCd, String stuId);
	
	/**
	 * 파일 다운로드
	 * 
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);

	/**
	 * 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSns
	 */
	public void removeFile(int atchFileId, int fileSn);
	
	

	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회 
	 * @param paginationInfo
	 * @return
	 */
	public int selectTotalRecord(@Param("paging") PaginationInfo<StudentCardVO> paginationInfo);
	
	/**
	 * 
	 * 검색 결과 목록
	 * @param paginationInfo
	 * @return
	 */
	public List<StudentCardVO> selectStudentList (PaginationInfo<StudentCardVO>paginationInfo);
	
	
	
}
