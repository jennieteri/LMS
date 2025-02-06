package kr.or.ddit.yguniv.person.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.person.dao.PersonMapper;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonMapper dao;

	@Transactional
	public void insertPersonAndProfessor(PersonVO person) {
		try {
			// Person을 먼저 삽입
			dao.insertPerson(person);

		} catch (Exception e) {
			// 예외 발생 시 트랜잭션 롤백
			throw new RuntimeException("Person or Professor insert failed", e);
		}
	}

	@Override
	public ServiceResult createPerson(PersonVO person) {
		ServiceResult result = null;

		if (dao.selectPerson(person.getId()) != null) {
			result = ServiceResult.PKDUPLICATED;
		} else {

			int rowcnt = dao.insertPerson(person);
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}

		return result;
	}

	public String insertPersonRole(String id) {
		String roleId = "";
		String mid = id.substring(4, 5); // 5번째 문자를 추출 (인덱스 4)

		switch (mid) {
		case "1":
			roleId = "STUDENT";
			break;
		case "2":
			roleId = "EMPLOYEE";
			break;
		case "3":
			roleId = "PROFESSOR";
			break;
		default:
			roleId = "UNKNOWN";
			break;
		}

		// role과 id를 함께 전달하여 insertPersonRole 메서드 호출
		return roleId;
	}

	@Override
	public ServiceResult insertStudent(StudentVO student) {
		int rows = dao.insertStudent(student);

		String roleId = insertPersonRole(student.getId());
		int role = dao.insertPersonRole(student.getId(), roleId);

		return (rows + role) > 1 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult insertEmployee(EmployeeVO employee) {
		int rows = dao.insertEmployee(employee);
		String roleId = insertPersonRole(employee.getId());
		int role = dao.insertPersonRole(employee.getId(), roleId);

		return (rows + role) > 1 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult insertProfessor(ProfessorVO professorVO) {

		int rows = dao.insertProfessor(professorVO);
		String roleId = insertPersonRole(professorVO.getId());
		int role = dao.insertPersonRole(professorVO.getId(), roleId);

		return (rows + role) > 1 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public PersonVO readPerson(String id) {
		// PERSON 테이블에서 기본 정보 조회
		PersonVO person = Optional.ofNullable(dao.selectPerson(id))
				.orElseThrow(() -> new PKNotFoundException(String.format("%s 사용자 없음.", id)));
		return person;
	}

	@Override
	public List<PersonVO> readPersonList(PaginationInfo<PersonVO> paginationInfo) {
		paginationInfo.setTotalRecord(dao.selectTotalRecord(paginationInfo));
		List<PersonVO> personList = dao.selectPersonList(paginationInfo);

		return personList;
	}

	@Override
	public ServiceResult modifyPerson(PersonVO person) {
		ServiceResult result = null;
		if (dao.updatePerson(person) > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult updatePw(PersonVO person) {
		ServiceResult result = null;
		if (dao.updatePw(person) > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removePerson(PersonVO person) {
		ServiceResult result = null;
		if (dao.deletePerson(person.getId()) > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public PersonVO selectEmployeeDetail(String id) {
		return dao.selectEmployeeDetail(id);
	}

	@Override
	public PersonVO selectProfessorDetail(ProfessorVO professor) {

		return dao.selectProfessorDetail(professor);
	}

	@Override
	public Boolean checkId(String id) {
		Integer count = dao.checkedId(id);
		return count == 0; // 존재하지 않으면 true, 존재하면 false
	}

	@Override
	public ProfessorVO readProfessor(String profeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeVO readEmployee(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<PersonVO> previewODSFile(MultipartFile file) {
		List<PersonVO> previewList = new ArrayList<>();

		try (InputStream inputStream = file.getInputStream()) {
			OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.loadDocument(inputStream);
			OdfTable table = ods.getTableList().get(0);

			for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
				OdfTableRow row = table.getRowByIndex(rowIndex);

				String id = getCellValue(row, 0);
				String name = getCellValue(row, 1);
				String brdt = getCellValue(row, 2);
				String sexdstnCd = getCellValue(row, 3);

				if (id == null || id.isEmpty()) {
					continue; // ID가 없으면 건너뛰기
				}

				// VO 객체 생성
				PersonVO personVO = new PersonVO();
				personVO.setId(id);
				personVO.setNm(name);
				personVO.setBrdt(brdt);
				personVO.setSexdstnCd(sexdstnCd);

				previewList.add(personVO); // 미리보기 목록에 추가
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return previewList;
	}

	// 다중 사용자 파일 매핑
	@Transactional
	@Override
	public int processODSFile(MultipartFile file) {
		int addedCount = 0;

		try (InputStream inputStream = file.getInputStream()) {
			// ODS 파일 열기
			OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.loadDocument(inputStream);

			OdfTable table = ods.getTableList().get(0);

			// 모든 행 반복 (헤더 제외, 1번 행부터 시작)
			for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
				OdfTableRow row = table.getRowByIndex(rowIndex);

				// 각 셀의 데이터 읽기
				String id = getCellValue(row, 0);
				String name = getCellValue(row, 1);
				String brdt = getCellValue(row, 2);
				String sexdstnCd = getCellValue(row, 3);
				String gradeCd = getCellValue(row, 4);
				String streCateCd = getCellValue(row, 5);
				String deptCd = getCellValue(row, 6);

				if (id == null || id.isEmpty()) {
					continue; // ID가 없으면 건너뛰기 (빈 행)
				}

				// VO 객체 생성 및 자동 매핑
				PersonVO personVO = new PersonVO();
				personVO.setId(id);
				personVO.setNm(name);
				personVO.setBrdt(brdt);
				personVO.setSexdstnCd(sexdstnCd);

				dao.insertPerson(personVO);

				StudentVO studentVO = new StudentVO();
				studentVO.setStuId(id); // studentId=personId
				studentVO.setGradeCd(gradeCd);
				studentVO.setStreCateCd(streCateCd);
				studentVO.setDeptCd(deptCd);

				dao.insertStudent(studentVO);
				
				String roleId = insertPersonRole(id);
				dao.insertPersonRole(id, roleId);

				addedCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		// 추가된 행
		return addedCount;
	}

	// 셀 데이터 가져오기 (빈 셀 처리 포함)
	private String getCellValue(OdfTableRow row, int columnIndex) {
		try {
			OdfTableCell cell = row.getCellByIndex(columnIndex);
			return cell.getStringValue().trim();
		} catch (Exception e) {
			return ""; // 예외 발생 시 빈 문자열 반환
		}
	}

	@Override
	public List<Map<String, Object>> selectUserTypeStatistics() {
		return dao.selectUserTypeStatistics();
	}

	@Override
	public int updatePersonRole(PersonVO person) {

		int del = dao.deletePersonRole(person.getId());
		int res = 0;
		if (del > 0) {
			List<String> list = person.getPersonType();

			for (String type : list) {
				log.info("aaaaaaaaaaaaaaaaaaaa{}", type);

				res += dao.insertPersonRole(person.getId(), type);
			}
		}
		log.info("aaaaaaaaaaaaaaaaaaaa실행횟수{}", res);
		return res;
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Integer> getGender() {
	    List<Map<String, Object>> result = dao.getGender();
	    log.info("DAO에서 반환된 데이터: {}", result);

	    if (result == null || result.isEmpty()) {
	        log.warn("DAO 결과가 비어 있습니다.");
	        return new HashMap<>();
	    }

	    Map<String, Integer> genderMap = result.stream()
	            .filter(map -> map.get("SEXDSTN_CD") != null && map.get("COUNT") != null) // null 필터링
	            .collect(Collectors.toMap(
	                map -> (String) map.get("SEXDSTN_CD"), 
	                map -> ((Number) map.get("COUNT")).intValue()
	            ));

	    log.info("Service에서 반환한 genderMap: {}", genderMap);
	    return genderMap;
	}


}
