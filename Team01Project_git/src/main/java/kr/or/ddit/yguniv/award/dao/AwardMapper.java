package kr.or.ddit.yguniv.award.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;

@Mapper
public interface AwardMapper {
	
	// 장학금 리스트 조회 
	public List<AwardVO> selectList();
	
	// 장학금 유형 상세 조회
	public AwardVO selectAward(String awardCd);
	
	// 장학금 유형 등록 
	public Integer insertAward(AwardVO award);

	// 장학금 유형 수정
	public int updateAward(AwardVO award);
	
	// 장학금 유형 삭제 부모
	public int deleteAward(String awardCd);

	//관리자가 관리하는 장학금 유형 
	public List<AwardVO> selectAdminAwardList(PaginationInfo<AwardVO>paging);
	
	// 페이징 처리를 위한 검색 결과 레코드 수 조회 게시글 목록 수 조회 
	public int selectTotalRecord(PaginationInfo<AwardVO> paging);
	


}
