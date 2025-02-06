package kr.or.ddit.yguniv.introduce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.CertificateVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;

@Mapper
public interface IntroduceMapper {
	/**
	 * 자기소개서 추가
	 * @param introduce
	 * @return 
	 */
	public void insertIntroduce(IntroduceVO introduce);
	/**
	 * 자격증 추가
	 * @param certificate
	 */
	public void insertCertificate(CertificateVO certificate);
	/**
	 * 내 자기소개서 상세 보기 
	 * @param intCd
	 * @return
	 */
	public IntroduceVO selectMyIntroduce(int intCd);
	/**
	 * 자기소개서 상세 보기
	 * @param stuId
	 * @return
	 */
	public IntroduceVO selectintroduce(int intCd);
	
	/**
	 * 수정된 자기소개서 조회
	 * @param stuId
	 * @return
	 */
	public IntroduceVO selectEditedIntroduce(String stuId);
	/**
	 * 자기소개서 전체 리스트 조회
	 * @return
	 */
	public List<IntroduceVO>selectintroduceListPaging(PaginationInfo<IntroduceVO>paging);
	
	
	/**
	 * 학생 자기 자신 자기소개서 리스트 조회
	 * @param userId
	 * @param paging
	 * @return
	 */
	public List<IntroduceVO> selectIntroduceByUserId(
		    @Param("stuId") String stuId,
		    @Param("paging") PaginationInfo<IntroduceVO> paging
		);

	/**
	 * 자기소개서 삭제
	 * @param stuId
	 */
	public void deleteintroduce(String stuId);
	/**
	 * 게시글 목록수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging);

	/**
	 * 교직원 자기소개서 첨삭
	 * @param stuId
	 */
	public int updateintroduce(IntroduceVO introduce); 


	

}
