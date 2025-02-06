package kr.or.ddit.yguniv.equipmentmanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.EquipmentManagementVO;

@Mapper
public interface EquipmentManagementMapper {
	
	/**
	 * 강의실 기자재 추가
	 * @param equipmentManagement
	 */
	public void insertEquipmentManagement(EquipmentManagementVO equipmentManagement);
	
	/**
	 * 강의실 기자재 상세 조회
	 * @param eqmngCd
	 * @return
	 */
	public EquipmentManagementVO selectEquipmentManagement(String eqmngCd);
	
	/**
	 * 강의실 기자재 전체 리스트 조회
	 * @return
	 */
	public List<EquipmentManagementVO>selectEquipmentManagementList();
	
	/**
	 * 강의실 기자재 수정
	 * @param equipmentManagement
	 */
	public void updateEquipmentManagement(EquipmentManagementVO equipmentManagement);
	
	/**
	 * 강의실 기자재 삭제
	 * @param eqmngCd
	 */
	public void deleteEquipmentManagement(String eqmngCd);
	
}
