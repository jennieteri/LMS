package kr.or.ddit.yguniv.askAward.dao;

import java.time.LocalDateTime;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AwardAskVO;

@Mapper
public interface AskAwardMapper {
	// 신청서 추가 (학생)
	public Integer insertAskAward(AwardAskVO ask); 
	
	// 신청서 상세 조회 
	public AwardAskVO selectAskAward(String shapDocNo); 
		
	// 글 목록 조회
	public List<AwardAskVO> selectAwardAskList(PaginationInfo<AwardAskVO>paging);
	
	
	// 신청서 목록 조회 페이징 처리 없는 
	public List<AwardAskVO> selectList(); 
	
	//게시글 목록 수 조회
	public int selectTotalRecord(PaginationInfo<AwardAskVO> paging);
	
	// 신청서 수정(학생)
	public int updateAskAward(AwardAskVO ask);
	
	// 신청서 삭제 (학생)
	public int deleteAskAward(String shapDocNo);
	
	// 신청서 신청서 접수 승인 상태 변경 (교직언)
	public int checkAskAward(String shapDocNo);
	
	// 신청서 신청서 접수 반려 상태 변경 (굦기원)
	public int passAskAward(String shapDocNo);
	
	//학생 신청 리스트 내역 조회 (학생이)
	public List<AwardAskVO> selectStudentAwardAsk(String stuId);

	
	public int updateStatus(
							@Param("cocoStts") String cocoStts, 
            				@Param("shapDocNo") String shapDocNo, 
            				@Param("shapChcDate") LocalDateTime shapChcDate,
            				@Param("shapNoReason")String shapNoReason
			);

	

	 
	

}
