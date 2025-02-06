package kr.or.ddit.yguniv.person.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;


public interface PersonService {
    /**
     * 사용자 별 통계
     * @return
     */
    public List<Map<String, Object>> selectUserTypeStatistics();
	/**
	 * 사용자 미리보기
	 * @param file
	 * @return
	 */
	public List<PersonVO>previewODSFile(MultipartFile file);
	/**
	 * 사용자 다중 추가
	 * @param file
	 * @return
	 */
	public int processODSFile(MultipartFile file);
	/**
	 * 사용자 추가 
	 * @param person
	 * @return PKDUPLICATED, OK, FAIL 
	 */
	public ServiceResult createPerson(PersonVO person);
	/**
	 * 사용자 상세 조회
	 * @param Id
	 * @return
	 */
	public PersonVO readPerson(String Id);
	/**
	 * 교수 상세 조회
	 * @param Id
	 * @return
	 */
	public ProfessorVO readProfessor(String profeId);
	/**
	 * 사용자 상세 조회
	 * @param Id
	 * @return
	 */
	public EmployeeVO readEmployee(String empId);
	/**
	 * 사용자 전체 조회 페이징, 검색
	 * @return
	 */
	public List<PersonVO> readPersonList(PaginationInfo<PersonVO> paginationInfo);
	/**
	 * 사용자 수정
	 * @param personId
	 * @param updatedPerson 
	 * @return
	 */
	public ServiceResult modifyPerson(PersonVO person);
	/**
	 * 사용자 비밀번호 초기화(수정)
	 * @param person
	 * @return
	 */
	public ServiceResult updatePw(PersonVO person);
	/**
	 * 사용자 삭제
	 * @param person
	 * @return
	 */
	public ServiceResult removePerson(PersonVO person);
	/**
	 * 학생 정보 삽입
	 * @param student
	 * @return
	 */
    public ServiceResult insertStudent(StudentVO student);
    /**
     * 교수 정보 삽입
     * @param professor 교수 정보
     */
    public ServiceResult insertProfessor(ProfessorVO professor);
    
    /**
     * 교직원 정보 삽입
     * @param employee
     * @return
     */
    public ServiceResult insertEmployee(EmployeeVO employee);
    
    /**
     * 아이디 중복 체크
     * @param id
     * @return
     */
    public Boolean checkId(@Param("id") String id);
    
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

    public int updatePersonRole (PersonVO person);
    
    public Map<String, Integer> getGender(); 
    
    }
   
    


