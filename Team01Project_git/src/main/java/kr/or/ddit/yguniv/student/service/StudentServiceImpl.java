package kr.or.ddit.yguniv.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.student.dao.StudentMapper;
import kr.or.ddit.yguniv.vo.AcademicProbationVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Service
public class StudentServiceImpl {
	@Autowired
	StudentMapper mapper;
	
	public List<StudentVO> studentList(DataTablesPaging<StudentVO> paging){
		paging.setRecordsTotal(mapper.selectTotalRecord());
		paging.setRecordsFiltered(mapper.selectPagingTotalRecord(paging));
		return mapper.studentList(paging);
	}

	public StudentVO selectStudet(String stuId) {
		return mapper.selectStudent(stuId);
	}
	
	public boolean insertAcademicProbation(AcademicProbationVO academicProbationVO) {
		return mapper.insertAcademicProbation(academicProbationVO)>0;
	}

	public void expulsion(String stuId) {
		mapper.expulsion(stuId);
	}
	
	
	//여기서 부터 내가 만든거임요
	
	public List<StudentVO> selectStudentList() {
		
		return mapper.selectStudentList();
	}
	
	
	
}
