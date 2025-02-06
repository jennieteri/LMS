package kr.or.ddit.yguniv.attendeetest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.attendeetest.service.AttendeeTestService;
import kr.or.ddit.yguniv.commons.enumpkg.NotificationCode;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.test.service.TestService;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.NotificationVO;
import kr.or.ddit.yguniv.vo.QuestionAnswerVO;
import kr.or.ddit.yguniv.vo.QuestionVO;
import kr.or.ddit.yguniv.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lecture/{lectNo}/attendeeTest")
public class AttendeeTestController {
	
	public static final String MODELNAME = "newQuestion";
		//교수 개인의 강의 리스트 조회
	//추후에 로그인 한 교수의 교번을 가져와야함!
	@Autowired
	private LectureMaterialsServiceImpl lectMateService;
	

	@Autowired
	private AttendeeTestService service;
	@Autowired
	private TestService testService;
	   
	
	   @ModelAttribute
	   public void addLecture(@PathVariable String lectNo, Model model){
	
	      LectureVO lectureVO = lectMateService.selectLecture(lectNo);
	      if(lectureVO == null) {
	         throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
	      }
	      model.addAttribute("lecture", lectureVO);
	   }

	   @ModelAttribute(MODELNAME)
	   public QuestionVO testBox(@PathVariable String lectNo, Model model){
		   return new QuestionVO();
	   }
	   

	//강의페이지에서 시험조회 페이지로 이동
	   @GetMapping()
	   public String getTestPage(@PathVariable String lectNo, Model model
	                          , Principal prin
	                          , Authentication authentication) {
	       List<TestVO> attendeeTestList = service.getTestPage(lectNo);
	       List<TestVO> longQuestion = service.checkLongStringQuestion(lectNo);
	       List<LectureEvaluationStandardVO> LectureEvaluationStandardVOList = service.getLectureEvaluationStandardList(lectNo);
	       
	       
	       model.addAttribute("attendeeTestList", attendeeTestList);
	       model.addAttribute("LectureEvaluationStandardVOList", LectureEvaluationStandardVOList);
	       model.addAttribute("longQuestion", longQuestion);

	       model.addAttribute("lectNo", lectNo);
	       
	       return "lecture/materials/attendTest/attendeeTest";
	   }

	
	@GetMapping("professor/{testNo}")
	public String getProfessorTestPage2(@PathVariable String lectNo,@PathVariable String testNo, Model model
			,Principal prin
			,Authentication authentication
			) {
		model.addAttribute("lectNo",lectNo);
		model.addAttribute("testNo",testNo);
		return "lecture/materials/attendTest/professorTestList";
	}
	
	//교수가 시험조회 페이지에서 서술형 답안 리스트로 이동
	@GetMapping("professor/{testNo}/get")
	@ResponseBody
	public Map<String,Object> getProfessorTestPage(
			@PathVariable String lectNo, @PathVariable String testNo
			) {
	    Map<String, Object> map = new HashMap<>();
	    List<TestVO> professorTestList = service.getProfessorTestPage(testNo);
	    map.put("professorTestList", professorTestList);
	    return map;
	}
	
	@PostMapping("{queNo}/score")
	public ResponseEntity<Map<String, String>> updateAttendeeScore(@RequestBody QuestionAnswerVO qVo,
	                                                               @PathVariable String lectNo,
	                                                               @PathVariable String queNo,
	                                                               BindingResult error) {
	    Map<String, String> response = new HashMap<>();
	    
	    // 에러가 있는 경우
	    if (error.hasErrors()) {
	        String errorMessage = error.getFieldErrors().stream()
	                                    .map(FieldError::getDefaultMessage)
	                                    .collect(Collectors.joining("\r\n"));
	        response.put("status", "fail");
	        response.put("message", errorMessage);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	    // 에러가 없을 경우
	    try {
	        String res = service.professorUpdateAttendeeScore(qVo);
	        
	        if ("ok".equals(res)) {
	            response.put("status", "success");
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("status", "fail");
	            return ResponseEntity.ok(response);
	        }
	        
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("message", "서버 오류가 발생했습니다.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	
	//학생이 시험 보기 전 응시기록 생성하기
	@GetMapping("attendeeTest/{testNo}")
	public ResponseEntity<Object> getTest(
			
			@PathVariable String lectNo,@PathVariable String testNo, Model model ,Principal prin) {
		
		String stuId = prin.getName(); //2024100001
		
		HttpStatus status = HttpStatus.OK;
		Map<String, String> body = new HashMap<>();
		
		//새로고침 하면 기록 생성됨. 주석묶기.		
		Integer newRecord=service.createRecord(testNo, stuId);
		
		
		return ResponseEntity.status(status).body(body);
		
	}
	
	
	
	//시험페이지 띄우기 원
	@GetMapping("{testNo}")
	public String getTest22(
			
			@PathVariable String lectNo,@PathVariable String testNo, Model model ,Principal prin) {
		
		String stuId = prin.getName(); //2024100001
		String lvn = "";
		
		lvn = "/attendTest/attendeeTestForm"; //성공하면 새 시험 폼 출력
		TestVO studentTestVo = testService.selectTestOne(testNo);
		studentTestVo.setLectNo(lectNo);
		QuestionVO questionForm = new QuestionVO();
		
		model.addAttribute("studentTestVo", studentTestVo);
		model.addAttribute("newQuestion", questionForm); // 폼 바인딩용
		
		return lvn;
		
	}
	
	//문제 채점하기
//	action="/yguniv/attendeeTest/L004/attendeeTest/TEST004"
	@PostMapping("{testNo}")
	@ResponseBody
	public Map<String, Object> scoreCheck(
			Principal prin,
			@PathVariable String testNo ,@PathVariable String lectNo
			,@RequestBody List<QuestionAnswerVO> QuestionAnswerVO) {

	 
		String stuId = prin.getName(); //2024100001
		
		QuestionAnswerVO.forEach(que->{
					que.setLectNo(lectNo);
					que.setStuId(stuId);
					que.setTestNo(testNo);
				});
		

		Integer res = service.questionScoreCheck(testNo, QuestionAnswerVO);
		
		HashMap<String , Object> map = new HashMap<String, Object>();
		
		if(res!=null&&res!=0) {
			map.put("result", "ok");			
		}else {
			map.put("result", "no");						
		}
		
		
		return map;
	}
	
	@Autowired
	private NotificationService notiService;
	
	@GetMapping("test/api/{testNo}/sse")
	@ResponseBody
	public Map<String, Object> scoreCheckss(
			Principal prin,
			@PathVariable String testNo ,@PathVariable String lectNo) {
	
		String stuId = "2024100001";
		String stuId2 = "2024100002";

		
		String sendId = prin.getName();

		List<String> stuList = new ArrayList<>();
		stuList.add(stuId);
		stuList.add(stuId2);
		
		String message = "메시지 본문"; 
		String notiCd =  NotificationCode.OK+""; //NotificationCode.INFO+"" NotificationCode.WARN+"" 
		String url = "test/api/L005/sse"; 
		String url2 = " "; 
		String head = "교무처"; 
		
		//필요한것 전송자 id , 수신자 List (1명이라도), 내용 본문, 알림코드번호, url(없으면 공백 필수)
		notiService.createAndSendNotification(sendId, stuList, message, notiCd, url,head );
		HashMap<String , Object> map = new HashMap<String, Object>();
		
		if(true) {
			map.put("result", "ok");			
		}
		
		return map;
	}
	
	
}






















