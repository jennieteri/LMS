package kr.or.ddit.yguniv.award.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;

@Service
public interface AwardService {
	
	// 장학금 목록 조회 
	public List<AwardVO> awardList();
	
	// 장학금 상세 조회 
	public AwardVO selectAward(String awardCd);
	
	// 장학금 유형 등록
	public void insertAward(AwardVO award);
	
	
	// 장학금 유형 수정 
	public void updateAward(AwardVO award);
	
	// 장학금 삭제
	public void deleteAward(String awardCd);
	
	
	
	// 페이징 처리가 없는 게시글 목록 
	public List<AwardVO> selectAdminAwardList();
	
	//관리자가 관리하는 게시글 검샋 결과 목록 조회 
	public List<AwardVO> selectAdminAwardList(PaginationInfo<AwardVO>paginationInfo);
	
	
	//페이징 처리를 위한 검색 결과 레코드 수 조회 
	public int selectTotalRecord(@Param("paging") PaginationInfo<AwardVO> paginationInfo); 
		
	// 장학금 검색 결과 목록 조회 
	public List<AwardVO> selectAdminAwardSearch(PaginationInfo<AwardVO>paginationInfo);
	
	
	

}
