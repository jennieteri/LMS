package kr.or.ddit.yguniv.introduce.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.CertificateVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;

public interface IntroduceService {
	/**
	 * 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	/**
	 * 자기소개서 추가
	 * @param introduce
	 */
	public void insertIntroduce(IntroduceVO introduce);
	/**
	 * 자격증 추가
	 * @param certificate
	 * @return 
	 */
	public void insertCertificate(CertificateVO certificate);
	
	/**
	 * 자기소개서 + 자격증
	 * @param certificate
	 */
	public void insertIntroduceCertificate(IntroduceVO introduce);
	/**
	 * 자기소개서 상세 보기
	 * @param stuId
	 * @return
	 */
	public IntroduceVO selectintroduce(@Param("intCd") int intCd);
	/**
	 * 내 자기소개서 상세 보기 
	 * @param intCd
	 * @return
	 */
	public IntroduceVO selectMyIntroduce(@Param("intCd") int intCd);
	/**
	 * 수정된 자기소개서 상세 보기
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
	 * 학생이 자기꺼 전체 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<IntroduceVO> selectIntroduceByUserId(String stuId, PaginationInfo<IntroduceVO>paging);
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
	 * @return 
	 */
	public IntroduceVO updateintroduce(IntroduceVO introduce);


}
