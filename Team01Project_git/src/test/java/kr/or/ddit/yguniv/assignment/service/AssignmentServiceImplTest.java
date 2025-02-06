package kr.or.ddit.yguniv.assignment.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.vo.AssignmentVO;
import kr.or.ddit.yguniv.vo.LectureVO;

@RootContextWebConfig
@Transactional
class AssignmentServiceImplTest {
	
	@Autowired
	AssignmentService service;
	
	AssignmentVO assignmentWithFiles;
	AssignmentVO assignmentWithNoFiles;
	LocalDate date = LocalDate.now();
	
	LectureVO lecture;
	
	@BeforeEach
	void BeforeEach() {
		assignmentWithFiles = service.readAssignment("1");
		assignmentWithNoFiles = service.readAssignment("2");
		assignmentWithNoFiles.setAtchFile(null);
	}
	
//	@Disabled
//	@Test//성공
//	void testread() {
//		service.readAssignmentList();
//	}
//	
	@Disabled
	@Test//성공
	void testReadPaging() {
		PaginationInfo<AssignmentVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(1);
		SimpleCondition simpleCondition = new SimpleCondition();
		simpleCondition.setSearchWord("데이터");
		paging.setSimpleCondition(simpleCondition);
		assertDoesNotThrow(()->{
			service.readAssignmentListPaging(paging,assignmentWithFiles.getAssigNo());
		});
		
	}
	@Disabled
	@Test//성공
	void testModify() {
		assignmentWithFiles.setAssigNm("수정과제");
		assertDoesNotThrow(()->{
			service.modifyAssignment(assignmentWithFiles);
		});
	}
	@Disabled
	@Test//성공
	void testRemove() {
		assertDoesNotThrow(()->{
			service.removeAssignment("1");
		});
	}
	
	@Test//성공
	void testCreate() {
		AssignmentVO assignment = service.readAssignment("1");
		assignment.setLectNo("L004");
		assignment.setAssigNm("신규제목");
		assignment.setAssigNotes("과제내용이에요");
		assignment.setAssigScore(35);
		assignment.setPeerYn("Y");
		
		assertDoesNotThrow(()->{
			service.createAssignment(assignment);
		});
		
		
	}

}
