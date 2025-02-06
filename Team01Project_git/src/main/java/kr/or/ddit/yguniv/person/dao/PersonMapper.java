package kr.or.ddit.yguniv.person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;

@Mapper
public interface PersonMapper {
	/**
	 * 담당 교수
	 * @param deptNo
	 * @return
	 */
	public String selectRandomProfeId(@Param("deptNo") String deptNo);
    /**
     * 사용자 추가
     * @param person 사용자 정보
     */
    public Integer insertPerson(PersonVO person);

    /**
     * 아이디 중복 체크
     * @param id
     * @return
     */
    public Integer checkedId(@Param("id") String id);
    /**
     * 사용자 상세조회
     * @param id 사용자 ID
     * @return 사용자 정보
     */
    public PersonVO selectPerson(@Param("id") String id);
	/**
	 * 페이징 처리를 위한 검색 결과 레코드 수 조회
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<PersonVO> paginationInfo);
    /**
     * 사용자 전체 리스트 조회 , 목록 페이징 
     * @return 사용자 리스트
     */
    public List<PersonVO> selectPersonList(PaginationInfo<PersonVO> paginationInfo);
    
    /**
     * 사용자 수정
     * @param person 사용자 정보
     * @return 수정 결과
     */
    public int updatePerson(PersonVO person);
    /**
     * 사용자 비밀번호 초기화(수정)
     * @param person
     * @return
     */
    public int updatePw(PersonVO person);
    /**
     * 사용자 삭제
     * @param id 사용자 ID
     * @return 삭제 결과
     */
    public int deletePerson(@Param("id") String id);
    /**
     * 교수 정보 삽입
     * @param professor 교수 정보
     */
    public Integer insertPersonAndProfessor(PersonVO person);
    /**
     * 학생 정보 삽입
     * @param student
     * @return
     */
    public Integer insertStudent(StudentVO student);
    /**
     * 교수 정보 삽입
     * @param professor 교수 정보
     */
    public Integer insertProfessor(ProfessorVO professor);
    
    /**
     * 교직원 정보 삽입
     * @param employee
     * @return
     */
    public Integer insertEmployee(EmployeeVO employee);
    
    /**
     * 교수 상세 조회
     * @param professor 사용자 ID
     * @return 사용자 정보
     */
    public PersonVO selectProfessorDetail(@Param("id") ProfessorVO professor);
    /**
     * 교직원 상세 조회
     * @param id
     * @return
     */
    public PersonVO selectEmployeeDetail (@Param("id") String id);
    /**
     * 사용자 별 통계
     * @return
     */
    public List<Map<String, Object>> selectUserTypeStatistics();
    
    public int insertPersonRole(@Param("id") String id,@Param("roleId") String roleId);
    
    public int updatePersonRole (@Param("id") String id,@Param("roleId") String roleId);

    public int deletePersonRole(@Param("id") String id);
	
	public List<Map<String, Object>> getGender();

}
