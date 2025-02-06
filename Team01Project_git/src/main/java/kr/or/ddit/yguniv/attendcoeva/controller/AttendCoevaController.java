package kr.or.ddit.yguniv.attendcoeva.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.attendcoeva.service.AttendCoevaService;
import kr.or.ddit.yguniv.security.conf.PersonVoWrapper;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.SemesterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/attendCoeva")
@RequiredArgsConstructor
public class AttendCoevaController {

	private final AttendCoevaService service;
	
	//강의 평가 목록 조회
	//[학,교수]강의평가 페이지로 이동한다.
		@GetMapping()
//		public String getPage(Principal prin,Model  model) {
		public String getPage(Authentication authentication,Model  model) {
//			model.addAttribute("id", prin.getName());
			
			PersonVoWrapper pwVo = (PersonVoWrapper) authentication.getPrincipal();
			model.addAttribute("id", pwVo.getRealUser().getId());
			model.addAttribute("nm", pwVo.getRealUser().getNm());
			
			
			return "attendcoeva/attendCoevaList";
			
		}
		//강의 평가 목록 조회
		//[학,교수]강의평가 페이지로 이동한다.
		@GetMapping("profe")
		public String getPages(Principal prin,Model  model) {
			model.addAttribute("id", prin.getName());
			
			return "attendcoeva/attendCoevaDetail";
			
		}
		
		
		@GetMapping("{stuId}")
		@ResponseBody
		public Map<String,Object> selectSemesterList(Principal prin, Model model,
		        Authentication authentication, @PathVariable String stuId) {
		    
		    Map<String,Object> map = new HashMap<>();
		    PersonVoWrapper pVw = (PersonVoWrapper) authentication.getPrincipal();
		    PersonVO pVo = pVw.getRealUser();
		    log.info("현재 로그인 한 사용자 객체{}",pVo);
		    
		    List<SemesterVO> coevaList = null;
		    if(pVo.getPersonType().contains("STUDENT")) {
		        coevaList = service.selectAttenSemesterList(prin.getName());
		    } else {
		        coevaList = service.selectProfeSemesterList(prin.getName());
		    }
		    
		    
		    // Model 대신 Map에 데이터를 담아서 반환
		    map.put("lectList", coevaList);
		    return map;
		}

		
		@GetMapping("profe/{profeId}/{semNo}")
		@ResponseBody
		public Map<String,Object> selectProfe(@PathVariable String profeId ,@PathVariable String semNo ,Model  model) {
			
			Map<String,Object> map = new HashMap<>();
			
			List<AttendeeVO> selectProfeCoevaList = service.selectProfeCoevaList(profeId, semNo);
			
			map.put("selectProfeCoevaList", selectProfeCoevaList);
			return map;
//			return "attendcoeva/attendCoevaList";
			
		}
		
		@GetMapping("profe/{profeId}/{lectNo}/{semNo}")
		@ResponseBody
		public Map<String,Object> selectProfeCoevaDetail(@PathVariable String profeId 
				,@PathVariable String lectNo ,@PathVariable String semNo
				,Model  model) {
			
			Map<String,Object> map = new HashMap<>();
			
			List<AttendeeVO> selectProfeCoevaDetail = service.selectProfeCoevaDetail(profeId,lectNo, semNo);
			
			map.put("selectProfeCoevaDetail", selectProfeCoevaDetail);
			return map;
			
		}
		
		
		@GetMapping("{stuId}/{semNo}")
		@ResponseBody
		public Map<String,Object> selectList(@PathVariable String stuId ,@PathVariable String semNo ,Model  model) {
			
			Map<String,Object> map = new HashMap<>();
				   
			List<AttendeeVO> semesterLectureList = service.selectCoevaList(stuId, semNo);
				    
			map.put("semesterLectureList", semesterLectureList);
			return map;
//			return "attendcoeva/attendCoevaList";
			
		}
		
		
		
		@GetMapping("{stuId}/{lectNo}/{semNo}")
		@ResponseBody
		public Map<String,Object> selectCoevaDetail(@PathVariable String stuId 
				,@PathVariable String lectNo ,@PathVariable String semNo
				,Model  model) {
			
			Map<String,Object> map = new HashMap<>();
			
			AttendeeVO semesterLectureDetail = service.selectCoevaDetail(stuId,lectNo, semNo);
			
			map.put("semesterLectureDetail", semesterLectureDetail);
			return map;
			
		}
	
		
		@PutMapping
		public ResponseEntity<Object> updateCoeva(@RequestBody AttendeeVO aVo,
		                                               BindingResult error) {
		    log.info("Received evaluation: {}", aVo);

		    Map<String, Object> responseMap = new HashMap<>();
		    HttpStatus status = HttpStatus.OK;

		    if (!error.hasErrors()) {
		        try {
		            // 서비스 호출: 강의 평가 저장/업데이트 처리
		            service.updateCoeva(aVo);

		            // 성공 메시지
		            responseMap.put("message", "평가가 등록되었습니다.");
		        } catch (Exception e) {
		            // 예외 발생 시 처리
		            status = HttpStatus.INTERNAL_SERVER_ERROR;
		            responseMap.put("message", "등록이 실패하였습니다.");
		        }
		    } else {
		        // 오류가 있을 경우
		        status = HttpStatus.BAD_REQUEST;
		        String errorMessage = error.getFieldErrors().stream()
		                                    .map(FieldError::getDefaultMessage)
		                                    .collect(Collectors.joining("\r\n"));
		        responseMap.put("message", errorMessage);
		    }

		    return ResponseEntity.status(status).body(responseMap);
		}
		
		
//		@GetMapping()
//		public String selectOne() {
//			
//			return "attendCoeva/attendCoevaDetail";
//		}

		
		
		//교수 혹은 교직원이 강의에 등록된 강의평가 조회
		//무기명
//		@GetMapping("professor/{courNo}")
//		public String ProfeesorSelectOne() {
//			
//			return "attendCoeva/attendCoevaList";
//		}
		
		/*
		 * dissent test question 
		 * 
		 * */
		
		
}
