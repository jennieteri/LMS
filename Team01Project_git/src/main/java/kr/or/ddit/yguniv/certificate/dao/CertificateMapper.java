package kr.or.ddit.yguniv.certificate.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.CertificateVO;
import kr.or.ddit.yguniv.vo.JobTestVO;

@Mapper
public interface CertificateMapper {
	/**
	 * 자격증 추가
	 * @param certificate
	 */
	public void insertcertificate(CertificateVO certificate);
	/**
	 * 자격증 상세 조회
	 * @param stuId
	 * @return
	 */
	public CertificateVO selectcertificate(String stuId);
	/**
	 * 자격증 전체 조회
	 * @return
	 */
	public List<JobTestVO>selectcertificateList();
	/**
	 * 자격증 수정
	 * @param introduce
	 */
	public void updatecertificate(CertificateVO certificate);
	/**
	 * 자격증 삭제
	 * @param stuId
	 */
	public void deletecertificate(String stuId);
}
