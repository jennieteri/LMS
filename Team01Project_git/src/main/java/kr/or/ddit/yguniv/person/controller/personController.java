package kr.or.ddit.yguniv.person.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.person.TempStorage;
import kr.or.ddit.yguniv.person.dao.PersonMapper;
import kr.or.ddit.yguniv.person.service.PersonService;
import kr.or.ddit.yguniv.validate.RoleGroup;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/person")
@Controller
@RequiredArgsConstructor
public class personController {
	
	private final PersonService service;
	
	@Autowired
	private final TempStorage tempStorage;
	
	public static final String MODELNAME = "person";
	
	@ModelAttribute(MODELNAME)
	public PersonVO member() {
		return new PersonVO();
	}
	
	@GetMapping("new")
	public String createForm() {
	    return "person/personForm"; // 정적 리소스 HTML 파일로 리다이렉트
	}
	
	@GetMapping("new/upload")
	public String uploadForm() {
	    return "person/personUpload"; // 정적 리소스 HTML 파일로 리다이렉트
	}
	
	@PostMapping("bulkUpload")
	public ResponseEntity<?> bulkUpload(@RequestParam("file") MultipartFile file) {
	    try {
	        // 미리보기 데이터 생성
	        List<PersonVO> previewList = service.previewODSFile(file);
	        tempStorage.save(previewList); // 임시 저장

	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("preview", previewList); // 미리보기 데이터 반환
	        response.put("count", previewList.size());
	        response.put("message", previewList.size() + "명의 사용자를 일괄 등록 하시겠습니까?");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("message", "파일 처리 중 오류 발생: " + e.getMessage());
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}
	
	@PostMapping("confirmUpload")
	public ResponseEntity<?> confirmUpload(@RequestParam("file") MultipartFile file) {
	    try {
	        // TempStorage에서 데이터를 가져옴
	        List<PersonVO> users = tempStorage.get();

	        if (users == null || users.isEmpty()) {
	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("success", false);
	            errorResponse.put("message", "TempStorage에 저장된 데이터가 없습니다.");
	            return ResponseEntity.badRequest().body(errorResponse);
	        }

	        // 기존 processODSFile 호출 (파일만 처리)
	        int savedCount = service.processODSFile(file);

	        // TempStorage 초기화
	        tempStorage.clear();

	        // 응답 데이터 구성
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("message", savedCount + "명의 사용자가 성공적으로 저장되었습니다.");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("message", "데이터 저장 중 오류 발생: " + e.getMessage());
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}




	
	
	@GetMapping("templateDownload")
	public void downloadTemplate(HttpServletResponse response) {
	    try {
	        // ODS 템플릿 생성
	        OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.newSpreadsheetDocument();
	        OdfTable table = ods.getTableByName("Sheet1");

	        // 헤더 추가
	        table.getCellByPosition(0, 0).setStringValue("ID");
	        table.getCellByPosition(1, 0).setStringValue("NM");
	        table.getCellByPosition(2, 0).setStringValue("BRDT");
	        table.getCellByPosition(3, 0).setStringValue("SEXDSTN_CD");
	        table.getCellByPosition(4, 0).setStringValue("GRADE_CD");
	        table.getCellByPosition(5, 0).setStringValue("STRE_CATE_CD");
	        table.getCellByPosition(6, 0).setStringValue("DEPT_CD");

	        // 샘플 데이터 추가
	        table.getCellByPosition(0, 1).setStringValue("2024100100");
	        table.getCellByPosition(1, 1).setStringValue("홍길동");
	        table.getCellByPosition(2, 1).setStringValue("1997-02-02");
	        table.getCellByPosition(3, 1).setStringValue("M");
	        table.getCellByPosition(4, 1).setStringValue("1001");
	        table.getCellByPosition(5, 1).setStringValue("SC01");
	        table.getCellByPosition(6, 1).setStringValue("D001");

	        // 파일 다운로드
	        response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
	        response.setHeader("Content-Disposition", "attachment; filename=\"ygunivtemplate.ods\"");
	        ods.save(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	



	
//	 @GetMapping("/updateform/{personId}")
//	    public String getUpdateForm(@RequestParam("id") String personId, Model model) {
//	        // 데이터베이스에서 personId에 해당하는 사용자 정보를 가져옴
//	        PersonVO person = service.readPerson(personId);
//
//	        // 모델에 데이터 추가
//	        model.addAttribute("person", person);
//
//	        // personUpdate.jsp 반환
//	        return "person/personUpdate";
//	    }
	
	@GetMapping("list")
	public String selectlist(
			@RequestParam(required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition
			, Model model
) {

	    // 페이징 및 검색 조건 설정
	    PaginationInfo<PersonVO> paging = new PaginationInfo<>();
	    paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);

	    // 목록 조회
	    model.addAttribute("list", service.readPersonList(paging));

	    // 페이지네이션 렌더링
	    PaginationRenderer renderer = new BootStrapPaginationRenderer();
	    model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));

	    return "person/personList";
	}

	
	//생성
	@PostMapping()
	public String create() {
		return "person/personDetail";
	}
	
	//상세보기
	@GetMapping("detail/{personid}")
	@ResponseBody
	public PersonVO detailperson(@PathVariable String personid, Model model) {
	    PersonVO person = service.readPerson(personid);

	    if (person == null) {
	        throw new PKNotFoundException("사용자를 찾을 수 없습니다.");
	    }

	    return person; 
	}

	
	@GetMapping("/checkId")
	@ResponseBody
	public boolean checkId(@RequestParam String id) {
	    return service.checkId(id); // `true`면 사용 가능, `false`면 중복
	}

	
	
	//학생 insert
	@PostMapping("create/student")
	public String insertPersonAndStudent(@RequestBody StudentVO student, RedirectAttributes redirectAttributes) {
	    log.info("================================컨트롤러에서 받은 StudentVO 데이터: {}", student);

	    try {
	        // Person 데이터 삽입
	        ServiceResult res = service.createPerson(student);

	        if (res == ServiceResult.PKDUPLICATED) {
	            redirectAttributes.addFlashAttribute("message", "아이디 중복, 바꾸셈.");
	            return "redirect:/person/new"; // 리다이렉트 경로
	        }

	        log.info("사용자 정보가 성공적으로 추가되었습니다.");
            ServiceResult result = service.insertStudent(student);
            
            if(result==ServiceResult.FAIL) {
            	redirectAttributes.addFlashAttribute("errorMessage", "학생 정보 추가 실패");
            	return "redirect:/person/new";
            }
	        redirectAttributes.addFlashAttribute("message", "사용자가 성공적으로 추가되었습니다.");
	        return "redirect:/person/list"; // 성공 시 리스트 페이지로 리다이렉트
	    } catch (Exception e) {
	        log.error("사용자 추가 중 오류 발생", e);
	        redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생했습니다. 다시 시도해주세요.");
	        return "redirect:/person/new"; // 실패 시 새 사용자 추가 페이지로 리다이렉트
	    }
	}
	
	//교수 insert
	@PostMapping("create/professor")
	public String insertPersonAndProfessor(@RequestBody ProfessorVO professor, RedirectAttributes redirectAttributes) {
	    log.info("================================컨트롤러에서 받은 ProfessorVO 데이터: {}", professor);

	    try {
	        // Person 데이터 삽입
	        ServiceResult res = service.createPerson(professor);

	        if (res == ServiceResult.PKDUPLICATED) {
	            redirectAttributes.addFlashAttribute("message", "아이디 중복, 바꾸셈.");
	            return "redirect:/person/new"; // 리다이렉트 경로
	        }

	        log.info("사용자 정보가 성공적으로 추가되었습니다.");

	        ServiceResult result = service.insertProfessor(professor);

	            if (result == ServiceResult.FAIL) {
	                redirectAttributes.addFlashAttribute("errorMessage", "교수 정보 추가 실패");
	                return "redirect:/person/new";
	            }
	
	        redirectAttributes.addFlashAttribute("message", "사용자가 성공적으로 추가되었습니다.");
	        return "redirect:/person/list"; // 성공 시 리스트 페이지로 리다이렉트
		    } catch (Exception e) {
		        log.error("사용자 추가 중 오류 발생", e);
		        redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생했습니다. 다시 시도해주세요.");
		        return "redirect:/person/new"; // 실패 시 새 사용자 추가 페이지로 리다이렉트
		    }
	}
	
	// 교직원 insert
	@PostMapping("create/employee")
	public String insertPersonAndEmployee(@RequestBody EmployeeVO employee, RedirectAttributes redirectAttributes) {
		log.info("================================컨트롤러에서 받은 EmployeeVO 데이터: {}", employee);

		try {
			// Person 데이터 삽입
			ServiceResult res = service.createPerson(employee);

			if (res == ServiceResult.PKDUPLICATED) {
				redirectAttributes.addFlashAttribute("message", "아이디 중복, 바꾸셈.");
				return "redirect:/person/new"; // 리다이렉트 경로
			}

			log.info("사용자 정보가 성공적으로 추가되었습니다.");

			ServiceResult result = service.insertEmployee(employee);

			if (result == ServiceResult.FAIL) {
				redirectAttributes.addFlashAttribute("errorMessage", "교직원 정보 추가 실패");
				return "redirect:/person/new";
			}

			redirectAttributes.addFlashAttribute("message", "사용자가 성공적으로 추가되었습니다.");
			return "redirect:/person/list"; // 성공 시 리스트 페이지로 리다이렉트
		} catch (Exception e) {
			log.error("사용자 추가 중 오류 발생", e);
			redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생했습니다. 다시 시도해주세요.");
			return "redirect:/person/new"; // 실패 시 새 사용자 추가 페이지로 리다이렉트
		}
	}
	
	
	

	//수정폼
	@GetMapping("editform/{personId}")
	@ResponseBody
	public PersonVO updateperson(@PathVariable String personId, Model model) {
		
		PersonVO person = service.readPerson(personId);
		
		if(person == null) {
			throw new PKNotFoundException();
		}
		
		return person;
	
	}
	//수정
	@PutMapping("edit/{personId}")
	@ResponseBody
	public ServiceResult saveUpdate(@PathVariable String personId, @RequestBody PersonVO updatedPerson) {
	    ServiceResult result = service.modifyPerson(updatedPerson); // 수정된 PersonVO 객체 전달
	    
	    if (result == ServiceResult.FAIL) {
	        throw new PKNotFoundException();
	    }

	    return result;
	}
	
	//비밀번호 초기화 (수정)
	@PutMapping("pwedit/{personId}")
	@ResponseBody
	public ServiceResult updatePw(@PathVariable String personId, @RequestBody PersonVO updatedPerson) {
	    ServiceResult result = service.updatePw(updatedPerson); // 수정된 PersonVO 객체 전달
	    
	    if (result == ServiceResult.FAIL) {
	        throw new PKNotFoundException();
	    }

	    return result;
	}
	
	
		
	//사용자 삭제(Y/N여부)
	@PutMapping("delete/{personId}")
	@ResponseBody
	public ServiceResult deletePerson(@PathVariable()String personId, @RequestBody PersonVO updatedPerson) {
		ServiceResult result = service.removePerson(updatedPerson);
		
		if(result == ServiceResult.FAIL) {
			throw new PKNotFoundException();
		}
		return result;
	}
	
	//교수 insert
	@PostMapping("newpro")
	public String professorcreateForm() {
		return "professor/professorInsert";
	}
	
	//사용자 별 통계
	@GetMapping("statistics")
	public ResponseEntity<List<Map<String, Object>>> getStatistic(){
		List<Map<String, Object>> Statistics = service.selectUserTypeStatistics();
		return ResponseEntity.ok(Statistics);
	}
	
	//사용자 별 통계 모듈
	@GetMapping("statisticsperson")
	public String getStatisticPerson(){
		return "/person/personStatistics";
	}
	
	
	@PutMapping("role")
	public ResponseEntity<Object> updateRoles(@RequestBody @Validated(RoleGroup.class) PersonVO pVo,
	                                          BindingResult error) {
	    HttpStatus status = HttpStatus.OK;
	    Map<String, String> body = new HashMap<>();

	    log.info("pVopVopVopVopVopVopVopVopVopVopVopVopVopVopVo{}",pVo);
	    
	    // 에러 검증
	    if (!error.hasErrors()) {
	        // 서비스 로직 호출 (권한 업데이트)
	        int success = service.updatePersonRole(pVo);
	        if (success>0) {
	            body.put("message", "권한이 성공적으로 업데이트되었습니다.");
	        } else {
	            status = HttpStatus.INTERNAL_SERVER_ERROR;
	            body.put("message", "권한 업데이트 중 오류가 발생했습니다.");
	        }
	    } else {
	        status = HttpStatus.BAD_REQUEST;
	        String errorMessage = error.getFieldErrors()
	                                   .stream()
	                                   .map(FieldError::getDefaultMessage)
	                                   .collect(Collectors.joining("\r\n"));
	        body.put("message", errorMessage);
	    }

	    return ResponseEntity.status(status).body(body);
	}
	
	
	@GetMapping("girlNboy")
	public String girlNboy(Model model) {
	    try {
	        Map<String, Integer> gender = service.getGender();
	        log.info("Controller에서 받은 gender 데이터: {}", gender);

	        model.addAttribute("boyCount", gender.getOrDefault("M", 0));
	        model.addAttribute("girlCount", gender.getOrDefault("F", 0));
	    } catch (Exception e) {
	        log.error("예외 발생: ", e);
	        model.addAttribute("boyCount", 0);
	        model.addAttribute("girlCount", 0);
	    }
	    
	    return "/moduleUI/girlNboy";
	}
	
}






