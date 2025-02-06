package kr.or.ddit.yguniv.person.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import lombok.extern.slf4j.Slf4j;

@RootContextWebConfig
@Transactional
@Slf4j
class PersonMapperTest {

	@Autowired
	PersonMapper mapper;
	
	PersonVO person;
	
	
		void beforeEach() {
		    person = new PersonVO();
		    person.setId("1234");
		    person.setNm("dummy");
		    person.setPswd("java");
		    person.setBrdt("19970201");
		    person.setSexdstnCd("f");
		    person.setLastConectDe("20241202");
		    person.setPswdFailrCo(0);
		  
		    // 교수 정보 설정
		    List<ProfessorVO> professorList = new ArrayList<>();
		    ProfessorVO professor = new ProfessorVO();
//		    professor.setProfeId("prof001");
		    professor.setDeptNo("01");
		    professor.setProfeType("Full-time");
		    professorList.add(professor);
		    
//		    person.setProfessor(professorList);

		    // 1. Person 정보 삽입
//		    mapper.insertPerson(person);
		    // 2. 교수 정보 삽입
//		    if (person.getProfessor() != null) {
		      //  for (ProfessorVO professorVO : person.getProfessor()) {
		          //  mapper.insertProfessor(professorVO);
//		        }
		    }

		    // Assertions or further tests can go here
		
		
		@Test
		void testSelectRandomProfeId() {
		    String deptNo = "D001"; // 테스트할 DEPT_NO 값
		    String profeId = mapper.selectRandomProfeId(deptNo);

		    System.out.println("Random PROFE_ID: " + profeId);

		    if (profeId != null) {
		        System.out.println("PROFE_ID가 정상적으로 반환되었습니다: " + profeId);
		    } else {
		        System.out.println("PROFE_ID가 NULL입니다. 조건에 맞는 값이 존재하지 않습니다.");
		    }
		}

	
	@Test
	void testSelectPerson() {
		log.info("{}", mapper.selectPerson("2024100001").toString());
	}
	@Disabled
	@Test
	void testIncrementHit() {
		//assertEquals(1, mapper.incrementHit(board.getBoNo()));
	}
	@Disabled
	@Test
	void testSelectBoardList() {
//		PaginationInfo paging = new PaginationInfo();
//		paging.setCurrentPage(1);
//		SimpleCondition simpleCondition = new SimpleCondition();
//		simpleCondition.setSearchWord("은대");
//		paging.setSimpleCondition(simpleCondition);
//		assertDoesNotThrow(() -> mapper.selectBoardList(paging));
	}
	@Disabled
	@Test
	void testSelectBoardCount() {
//		PaginationInfo paging = new PaginationInfo();
//		paging.setCurrentPage(1);
//		SimpleCondition simpleCondition = new SimpleCondition();
//		simpleCondition.setSearchWord("은대");
//		paging.setSimpleCondition(simpleCondition);
//		assertDoesNotThrow(() -> mapper.selectTotalRecord(paging));
	}

	@Test
	@Disabled
	void testUpdateBoard() {
		person.setNm("new test");
		assertEquals(1, mapper.updatePerson(person));
	}
	@Disabled
	@Test
	void testDeleteBoard() {
		assertEquals(1, mapper.deletePerson(person.getId()));
	}
	
	



}
