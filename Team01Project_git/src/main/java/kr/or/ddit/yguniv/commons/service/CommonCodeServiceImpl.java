package kr.or.ddit.yguniv.commons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.dao.CommonCodeMapper;
import kr.or.ddit.yguniv.vo.ClassRoomVO;
import kr.or.ddit.yguniv.vo.CommonCodeVO;
import kr.or.ddit.yguniv.vo.DepartmentVO;
import kr.or.ddit.yguniv.vo.EducationTimeTableCodeVO;
import kr.or.ddit.yguniv.vo.SemesterVO;
import kr.or.ddit.yguniv.vo.SubjectVO;

@Service
public class CommonCodeServiceImpl {
	@Autowired
	CommonCodeMapper cocoMapper;
	
	/**
	 * R-부모 코드에 해당하는 코드 리스트
	 */
	public List<CommonCodeVO> getCodeList(String parCocoCd){
		return cocoMapper.getCodeList(parCocoCd);
	}
	/**
	 * R-강의실 목록
	 */
	public List<ClassRoomVO> getClassRoomList(){
		return cocoMapper.getClassRoomList();
	}
	/**
	 * R-학기 목록
	 */
	public List<SemesterVO> getSemesterList(String id){
		return cocoMapper.getSemesterList(id);
	}
	/**
	 * R-과목 목록
	 */
	public List<SubjectVO> getSubjectList(){
		return cocoMapper.getSubjectList();
	}
	/**
	 * R-시간표 목록
	 */
	public List<EducationTimeTableCodeVO> getEducationTimeTableCodeList(){
		return cocoMapper.getEducationTimeTableCodeList();
	}	
	/**
	 * R-학과 목록
	 */
	public List<DepartmentVO> getDepartmentList(){
		return cocoMapper.getDepartmentList();
	}
	
	
	/**
	 * 교직원 부서 목록 
	 */
	public Object getEmployeeDeptList() {
		
		return cocoMapper.getEmployeeDeptList();
	}	
}
