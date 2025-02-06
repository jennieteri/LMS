package kr.or.ddit.yguniv.award.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AwardAskVO;

//추천
@Mapper
public interface AwardRecommendMapper {

	/*
	 * //그 교직원이 볼 수 있는 신청내역 (학생꺼랑 동일 )
	 * 
	 * public List<AwardAskVO> selectAwardAskList(PaginationInfo<AwardAskVO>paging);
	 */
	
	// 페이징 처리 없는 
	public List<AwardAskVO> selectList();
	
	//교직원이 보는 교수가 추천한 추천서 상세조회 
	public AwardAskVO selectAwardRec(String shapDocNo);
	
	//추천서 작성
	public Integer insertAwardRecAward(AwardAskVO ask);
	
	// 삭제
	public int deleteAwardRecAward(String shapDocNo);	
	
	// 수정 
	public int updateAwardRecAward(AwardAskVO ask);
	
	// 그 작성한 교수가 볼 수 있는 내역조회 LIST
	public List<AwardAskVO> selectAwardProRec(String profeId);
	
	

}
