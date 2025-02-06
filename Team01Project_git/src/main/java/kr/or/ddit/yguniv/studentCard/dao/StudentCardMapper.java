package kr.or.ddit.yguniv.studentCard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.StudentCardVO;

@Mapper
public interface StudentCardMapper {
	
		//학생증 발급 신청
		public Integer createStudentCard(StudentCardVO studentCard);
		
		//학생증 신청 상태 수정
		public void updateStatus(@Param("nextStatus") String nextStatus, @Param("stuId") String stuId);



		
		//학생증 신청 목록 삭제 
		public int deleteStudentCard(String stuId);
		
		// 학생증 신청 목록 조회 
		public List<StudentCardVO> studentCardList(PaginationInfo<StudentCardVO> paginationInfo);
	
		// 학생증 신청 상세 조회 
		public StudentCardVO selectStudentCard(String stuId);
		
		// 페이징 처리 없는 리스트 조회 
		public List<StudentCardVO> studentCardListNonPaging();
	

		//게시글 목록 수 조회 
		public int selectTotalRecord(PaginationInfo<StudentCardVO> paging);
		
		
		// 중복
		public int checkCard(String stuId);
	
	

}
