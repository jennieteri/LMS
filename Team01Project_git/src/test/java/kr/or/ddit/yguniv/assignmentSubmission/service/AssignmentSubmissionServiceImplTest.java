package kr.or.ddit.yguniv.assignmentSubmission.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.AssignmentSubmissionVO;
import kr.or.ddit.yguniv.vo.SerchMappingVO;

@RootContextWebConfig
@Transactional
class AssignmentSubmissionServiceImplTest {
	@Autowired
	AssignmentSubmissionService service;
	
	SerchMappingVO search= new SerchMappingVO();
	
//	@Test
//	void testCreateAssignmentSubmission() {
//		search.setAssignNo("13");
//		search.setLectNo("L003");
//		
//		AssignmentSubmissionVO submission = service.readAssignmentSubmission(search);
//		submission.setStuId("2024100002");
//		service.createAssignmentSubmission(submission);
//		service.readAssignmentSubmissionlist(search);
//		
//		search.setAssignNo("13");
//		search.setLectNo("L003");
//		search.setStuId("2024100002");
//		service.removeAssignmentSubmission(search);
//		
//		search.setAssignNo("13");
//		search.setLectNo("L003");
//		search.setStuId(null);
//		service.readAssignmentSubmissionlist(search);
//	}
////
//	@Test
//	void testReadAssignmentSubmission() {
//		search.setAssignNo("13");
//		search.setLectNo("L003");
//		service.readAssignmentSubmission(search);
//		
//	}
//
//	@Test
//	void testReadAssignmentSubmissionlist() {
//		search.setLectNo("L003");
//		service.readAssignmentSubmissionlist(search);
//	}
//
//	@Test
//	void testModifyAssignmentSubmission() {
//		search.setAssignNo("13");
//		search.setLectNo("L003");
//		
//		AssignmentSubmissionVO submission = service.readAssignmentSubmission(search);
//		submission.setAssubNotes("내용수정");
//		
//		assertDoesNotThrow(()->{
//			service.readAssignmentSubmission(search);
//		}); 
//				
//	}
//
//	@Test
//	void testRemoveAssignmentSubmission() {
//		
//	}
//
//	@Test
//	void testUpdateSubmissionStatus() {
//		fail("Not yet implemented");
//	}
//

}
