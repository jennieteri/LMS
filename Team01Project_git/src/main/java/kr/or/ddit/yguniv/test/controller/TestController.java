package kr.or.ddit.yguniv.test.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.enumpkg.NotificationCode;
import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.test.service.TestService;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProfessorVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;
import kr.or.ddit.yguniv.vo.TestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

	public static final String MODELNAME = "newTest";

	private final TestService service;

	// 교수 개인의 강의 리스트 조회
	// 추후에 로그인 한 교수의 교번을 가져와야함!
	public static final String PROFESSOR = "2024300001";

	@ModelAttribute(MODELNAME)
	public TestVO newModel() {
		return new TestVO();
	}

	// 교직원이 등록된 시험을 조회
	// 교수는 자신이 등록한 리스트를 조회
	// 교직원의 아이디와 교수의 아이디 - 인증 객체에서 아이디를 가져와서 202420 인지 30인지 비교하기
	@GetMapping()
	public String selectList(Model model
	        , @RequestParam(required = false, defaultValue = "1") int page
	        , @ModelAttribute("condition") SimpleCondition simpleCondition
	        , Principal prin) {

	    PaginationInfo<TestVO> paging = new PaginationInfo<TestVO>();
	    paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);

	    List<TestVO> list = service.selectTestList(prin.getName(), paging);

	    model.addAttribute("testList", list);
	    PaginationRenderer renderer = new BootStrapPaginationRenderer();

	    String pagingHTML = renderer.renderPagination(paging, "fnPaging");
	    model.addAttribute("pagingHTML", pagingHTML);

	    return "test/testList";
	}

	
//	@GetMapping()
//	public String selectList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
//			@ModelAttribute("condition") TestVO detailCondition, Principal prin) {
//		
//		PaginationInfo<TestVO> paging = new PaginationInfo<TestVO>();
//		paging.setDetailCondition(detailCondition);
////		paging.setSimpleCondition();
//		paging.setCurrentPage(page);
//		
//		PaginationRenderer renderer = new BootStrapPaginationRenderer();
//		String pagingHTML = renderer.renderPagination(paging, "testPaging");
//		
//		List<TestVO> list = service.selectTestList(prin.getName(), paging);
////		log.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§{}",list);
//		
//		model.addAttribute("pagingHTML", pagingHTML);
//		model.addAttribute("testList", list);
//		
//		return "test/testList";
//	}

	// 단건조회
	@GetMapping("{testNo}")
	public String selectTest(@PathVariable String testNo, Model model, Principal prin) {

//		List<TestVO> list = service.selectTestOneList(prin.getName());

//		model.addAttribute("detailTestList", list);
		model.addAttribute("detailTest", service.selectTestOne(testNo));

//		return "test/testDetail";
		return "test/testEdit";
	}

	// 단건조회 학생용
	@GetMapping("{testNo}/{stuId}")
	public String selectAttendTest() {

		return "test/testDetail";
	}

	// form으로 전송
	// 교수가 시험을 만드는 폼으로
	@GetMapping("{lectNo}/new/{testSe}")
	public String sendTestForm(@PathVariable String lectNo,@PathVariable String testSe, Model model) {
		model.addAttribute("lectNo", lectNo);
		model.addAttribute("testSe", testSe);
		
		

		return "test/testForm";
	}

	@PostMapping("new/test/{lectNo}")
	@ResponseBody
	public ResponseEntity<Object> createTest(@RequestBody @Validated TestVO testVo, BindingResult error,
			@PathVariable String lectNo) {
		log.info("TestVO: {}", testVo);

		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();

		if (!error.hasErrors()) {
			testVo.setLectNo(lectNo);

			Integer num = service.insertTest(testVo);

			if (num > 0) {
				body.put("on", "ok");
				body.put("testNo", testVo.getTestNo());
			} else {
				body.put("on", "no");
				body.put("message", "시험 등록에 실패했습니다.");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			// 유효성 검증 실패 처리
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}

		return ResponseEntity.status(status).body(body);
	}
	
	//시험 수정
	@PutMapping("new/test/{lectNo}")
	@ResponseBody
	public ResponseEntity<Object> updateTest(@RequestBody @Validated TestVO testVo, BindingResult error,
			@PathVariable String lectNo) {
		log.info("TestVO: {}", testVo);
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if (!error.hasErrors()) {
			testVo.setLectNo(lectNo);
			
			Integer num = service.updateTest(testVo);
			
			if (num > 0) {
				body.put("on", "ok");
				body.put("testNo", testVo.getTestNo());
			} else {
				body.put("on", "no");
				body.put("message", "시험 수정에 실패했습니다.");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			// 유효성 검증 실패 처리
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}

	@PutMapping("question/{testNo}")
	public ResponseEntity<Object> updateQuestion(@RequestBody @Validated List<QuestionVO> queVoList,
			@PathVariable String testNo,
			BindingResult error) {
		
		log.info("{}", queVoList);
//		Map<String, Object> map = new HashMap<String, Object>();
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			Integer num = service.deleteQuestionAndAnswer(testNo,queVoList);
			
		} else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
	
	
	@PostMapping("question")
	public ResponseEntity<Object> createQuestion(@RequestBody @Validated List<QuestionVO> queVoList,
			BindingResult error) {

		log.info("{}", queVoList);
//		Map<String, Object> map = new HashMap<String, Object>();

		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		if (!error.hasErrors()) {
			Integer num = service.insertQuestionAndQuestionAnswer(queVoList);

		} else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}

	// edit으로 전송 교수가 시험 수정폼으로 이동
	@GetMapping("{testNo}/edit")
	public String sendTestEdit() {

		return "test/testEdit";
	}

	@Autowired
	private NotificationService notiService;
	
	// 등록과 반려
	@GetMapping("profe/{testNo}/{check}")
	public ResponseEntity<Object> checkTest(@PathVariable String testNo, @PathVariable String check
			,Principal prin) {
		Map<String, Object> resp = new HashMap<>();

		log.info("＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠＠시험번호 : {} on? no? : {}", testNo, check);
		String message = ""; 
		String testCd = "";
		String notiCd =  NotificationCode.OK+""; //NotificationCode.INFO+"" NotificationCode.WARN+"" 
		
		if ("ok".equals(check)) {
			testCd = "OPEN";
			message = "시험이 승인처리 되었습니다.";
		} else if("no".equals(check)){
			testCd = "PEND";
			message = "시험이 반려처리 되었습니다.";
			notiCd =  NotificationCode.WARN+"";
		} else {
			testCd = "COMP";
			message = "시험이 대기처리 되었습니다.";
			notiCd =  NotificationCode.INFO+"";
			}
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		
		resp.put("ok", "ok");
		Integer result = service.checkTest(testNo, testCd);
		if(result>0) {
			String sendId = prin.getName();
			List<String> stuList = new ArrayList<>();
			String profeId = service.getProfeId(testNo);
			stuList.add(profeId);
			String url = "test/"+testNo; 
			String url2 = " "; 
			String head = "시험안내"; 
			
			//필요한것 전송자 id , 수신자 List (1명이라도), 내용 본문, 알림코드번호, url(없으면 공백 필수)
			notiService.createAndSendNotification(sendId, stuList, message, notiCd, url,head );
		}
		return ResponseEntity.status(status).body(body);

	}
	
	@DeleteMapping("{testNo}")
	public ResponseEntity<Object> deleteTest(@PathVariable String testNo) {
	    HttpStatus status = HttpStatus.OK;
	    Map<String, Object> body = new HashMap<>();
	        Integer num = service.deleteTest(testNo);
	        body.put("success", true);
	        body.put("testNo", testNo);
	   
	    
	    return ResponseEntity.status(status).body(body);
	}


}
