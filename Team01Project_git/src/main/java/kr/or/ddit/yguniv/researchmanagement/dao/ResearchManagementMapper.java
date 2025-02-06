package kr.or.ddit.yguniv.researchmanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ResearchManagementVO;

@Mapper
public interface ResearchManagementMapper {
	
	/**
	 * 연구 논문 추가
	 * @param researchManagement
	 */
	public void insertResearchManagement(ResearchManagementVO researchManagement);
	
	/**
	 * 연구 논문 상세 조회
	 * @param resmaCd
	 */
	public void selectResearchManagement(String resmaCd);
	
	/**
	 * 연구 논문 전체 리스트 조회
	 * @return
	 */
	public List<ResearchManagementVO>selectResearchManagementList();
	
	/**
	 * 연구 논문 수정
	 * @param researchManagement
	 */
	public void updateResearchManagement(ResearchManagementVO researchManagement);
	
	/**
	 * 연구 논문 삭제
	 * @param resmaCd
	 */
	public void deleteResearchManagement(String resmaCd);
}
