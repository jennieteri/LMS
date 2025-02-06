package kr.or.ddit.yguniv.askAward.service;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AwardAskVO;

@Service
public interface AskAwardService {
	
	
	/**
	 *  신청서 목록 조회 
	 * @param paginationInfo
	 * @return
	 */
	public List<AwardAskVO> awardAskList(PaginationInfo<AwardAskVO> paginationInfo);
	
	
	/**
	 * 페이지 처리 없는 신청서 목록 조회 
	 * @return
	 */
	public List<AwardAskVO> list();
	
	/**
	 * 학생이보는 장학금 신청서 상세조회
	 * @param stuId
	 * @return
	 */
	public AwardAskVO select(String shapDocNo);
	

	/**
	 * 신청서 등록
	 * @param awardAsk
	 */
	public void insertAwardAsk(AwardAskVO ask);
	

	/**
	 * 신청서 삭제
	 * @param stuId
	 * @return
	 */
	public void deleteAwardAsk(String shapDocNo);
	

	/**
	 * 신청서 수정
	 * @param awardAsk
	 * @return
	 */
	public void updateAwardAsk(AwardAskVO ask);
	

	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회 
	 * @param paginationInfo
	 * @return
	 */
	public int selectTotalRecord(@Param("paging") PaginationInfo<AwardAskVO> paginationInfo);

	/**
	 * 
	 * 검색 결과 목록
	 * @param paginationInfo
	 * @return
	 */
	public List<AwardAskVO> selectAwardAskList (PaginationInfo<AwardAskVO>paginationInfo);
	
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
	 * 학생이 확인하는 장학금 신청 목록 
	 * @return
	 */
	public List<AwardAskVO> studentAwardAskList(String stuId);


	/**
	 * 요청 변경 
	 * @param shapDocNo
	 * @param cocoStts
	 * @param shapChcDate
	 */
	public void updateApplicationStatus(String cocoStts, String shapDocNo,String shapNoReason);


	



	
	
	
	
	

	
	
}
