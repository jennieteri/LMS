package kr.or.ddit.yguniv.award.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AwardAskVO;

@Service
public interface AwardRecommendService {
	
	/*
	 * // 장학금 목록 조회 같이 public List<AwardAskVO>
	 * awardAskList(PaginationInfo<AwardAskVO> paginationInfo);
	 */
	
	// 목록 조회 
	public List<AwardAskVO> selectList();
	
	// 교수가 보는 상세조회 
	public AwardAskVO selectAwardRec(String shapDocNo);
	
	
	
	// 교직원 추천서 삭제
	public void deleteAwardRec(String shapDocNo);
	
	// 교직원 추천서 수정
	public void updateAwardRec(AwardAskVO ask);
	
	// 교직원이 볼 수 있는 신청 내역 -- 보여지는 폼이 다름 
	public List<AwardAskVO> selectAwardProRecList(String profeId);

	// 교직원 추천서 신청 - 폼이 다름 
	public void createRecAward(AwardAskVO ask);

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
	
	

}
