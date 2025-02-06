package kr.or.ddit.yguniv.supplymanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.SupplyManagementVO;

@Mapper
public interface SupplyManagementMapper {
	
	/**
	 * 비품 추가
	 * @param supplyManagement
	 */
	public void insertSupplyManagement(SupplyManagementVO supplyManagement);
	
	/**
	 * 비품 상세 조회
	 * @param sumngCd
	 * @return
	 */
	public SupplyManagementVO selectSupplyManagement(String sumngCd);
	
	/**
	 * 비품 전체 리스트 조회
	 * @return
	 */
	public List<SupplyManagementVO> selectSupplyManagementList();
	
	/**
	 * 비품 수정
	 * @param supplyManagement
	 */
	public void updateSupplyManagement(SupplyManagementVO supplyManagement);
	
	/**
	 * 비품 삭제
	 * @param sumngCd
	 */
	public void deleteSupplyManagement(String sumngCd);
}
