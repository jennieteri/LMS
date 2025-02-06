package kr.or.ddit.yguniv.dissent.controller;

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

import kr.or.ddit.yguniv.attendcoeva.service.AttendCoevaService;
import kr.or.ddit.yguniv.commons.enumpkg.NotificationCode;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.dissent.service.DissentService;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.security.conf.PersonVoWrapper;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/lecture/{lectNo}/dissent")
@RequiredArgsConstructor
public class DissentController {

	public static final String MODELNAME = "newDissent";
	private final DissentService service;

	// 학생 개인의 강의 리스트 조회
	// 추후에 로그인 한 학생의 학번을 가져와야함!
//	public static final String STUDENTID="2024100001";
//	public static final String PROFESSORID="2024300001";

	@ModelAttribute(MODELNAME)
	public ScoreFormalObjectionVO newModel() {
		return new ScoreFormalObjectionVO();
	}
	@Autowired
	private LectureMaterialsServiceImpl lectMateService;
	
	 @ModelAttribute
	   public void addLecture(@PathVariable String lectNo, Model model){
	
	      LectureVO lectureVO = lectMateService.selectLecture(lectNo);
	      if(lectureVO == null) {
	         throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
	      }
	      model.addAttribute("lecture", lectureVO);
	   }

	@GetMapping
	public String selectList(@PathVariable String lectNo,Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") ScoreFormalObjectionVO detailCondition, Principal prin) {

		PaginationInfo<ScoreFormalObjectionVO> paging = new PaginationInfo<>();
		paging.setDetailCondition(detailCondition);
		paging.setCurrentPage(page);

		List<ScoreFormalObjectionVO> dissList = service.readProfeDissentList(prin.getName(), paging,lectNo);

		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		log.info("-----------------------------------------------------------{}",lectNo);
		
		String pagingHTML = renderer.renderPagination(paging, "dissentPaging");
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("dissList", dissList);

		return "lecture/materials/dissent/courseDissentList";
	}

	@GetMapping("profe/new/dissent/list")
	@ResponseBody
	public List<ScoreFormalObjectionVO> newList(Model model,@PathVariable String lectNo,
			@RequestParam(required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") ScoreFormalObjectionVO detailCondition, Principal prin) {

		PaginationInfo<ScoreFormalObjectionVO> paging = new PaginationInfo<>();
		paging.setDetailCondition(detailCondition);
		paging.setCurrentPage(page);

		List<ScoreFormalObjectionVO> data = service.readProfeDissentList(prin.getName(), paging,lectNo);
		log.info("-----------------------------------------------------------{}",lectNo);
		model.addAttribute("lectNo", lectNo);


//			model.addAttribute("dissList", dissList) ;
		return data;
	}

	// 이의 내역 하나 조회
	//
	@GetMapping("{stuId}/{lectNo}")
	@ResponseBody
	public Map<String, Object> selectOne(@PathVariable String stuId, @PathVariable String lectNo, Model model) {

		Map<String, Object> resp = new HashMap<>();
		resp.put("dissOne", service.readDissentOne(stuId, lectNo));

		return resp;
	}

	@GetMapping("atten/{stuId}/{lectNo}")
	@ResponseBody
	public Map<String, Object> readAttenLectOne(@PathVariable String stuId, @PathVariable String lectNo, Model model) {


		Map<String, Object> resp = new HashMap<>();
		resp.put("attenOne", service.readAttenLectOne(stuId, lectNo));

		return resp;
	}

	// 이의 신청 페이지로 이동
	// 학생은 입력 폼
	// 학생은 등록할수 있는 페이지가
	private final AttendCoevaService attenCoevaService;

	@GetMapping("new/{stuId}/{lectNo}/{semstrNo}")
	public String createForm(Model model, Authentication auth, @PathVariable String stuId, @PathVariable String lectNo,
			@PathVariable String semstrNo) {

		PersonVoWrapper pVw = (PersonVoWrapper) auth.getPrincipal();
		PersonVO pVo = pVw.getRealUser();

		AttendeeVO semesterLectureDetail = attenCoevaService.selectCoevaDetail(stuId, lectNo, semstrNo);
		ScoreFormalObjectionVO sVo = service.selectAttendeeDissentOne(stuId, lectNo);
		
		model.addAttribute("stuId", pVo.getId());
		model.addAttribute("stuNm", pVo.getNm());
		model.addAttribute("sVo", sVo);
		model.addAttribute("semesterLectureDetail", semesterLectureDetail);

		return "/dissent/courseDissentForm";
	}
//		public String createForm(Model model, Authentication auth) {
//			
//			PersonVoWrapper pVw =(PersonVoWrapper) auth.getPrincipal();
//			PersonVO pVo = pVw.getRealUser();
//			
////			model.addAttribute("attenLectList", service.readAttenLectList(pVo.getId()));
//			model.addAttribute("stuId", pVo.getId());
//			model.addAttribute("stuNm", pVo.getNm());
//			
//			return "/dissent/courseDissentForm";
//		}

	@GetMapping("/newList")
	@ResponseBody
	public Map<String, Object> getNewLectList(Authentication auth) {
		PersonVoWrapper pVw = (PersonVoWrapper) auth.getPrincipal();
		PersonVO pVo = pVw.getRealUser();

		Map<String, Object> response = new HashMap<>();
		response.put("attenLectList", service.readAttenLectList(pVo.getId()));

		return response;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> create(@RequestBody @Validated ScoreFormalObjectionVO sVo, BindingResult error) {
	    Map<String, String> resp = new HashMap<>();
	    
	    try {
	        service.createDissent(sVo);
	        resp.put("status", "success");
	        resp.put("message", "이의신청이 정상적으로 등록되었습니다.");
	        return ResponseEntity.ok(resp);
	    } catch (Exception e) {
	        resp.put("status", "error");
	        resp.put("message", "이의신청 등록에 실패했습니다.");
	        return ResponseEntity.badRequest().body(resp);
	    }
	}

	
	// 이의 수정 페이지로 이동
	// 교수와 교직원이 상세조회를 보고 점수 변동을 할 수 있도록
	private final AttendCoevaService coevaService;
	private final NotificationService notiService;
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<Object> updateForm(@RequestBody @Validated ScoreFormalObjectionVO sVo,
			Principal prin,BindingResult error) {
		Map<String, Object> resp = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		//교수가 이의신청에 답변을 남기면 학생에게 알림이 가야함.
		
		
		
		if (!error.hasErrors()) {
			AttendeeVO aVo = sVo.getAttenVO();
			String sendId=prin.getName();
			List<String> stuList = new ArrayList<>();
			stuList.add(sVo.getStuId());
			String url ="attendCoeva"; // 학생이  이 url을 클릭했을 때 바로 이의신청 페이지가 나와야함.
			String notiCd =NotificationCode.INFO+"";
			String head="이의신청";
			
			String lVo=service.selectOneProfeName(sVo.getLectNo());
			
			String message = "이의신청한 "+lVo+"의 답변이 등록되었습니다.";
			
			// 어떤 이의신청의 답변인지 알아야함
			aVo.setLectNo(sVo.getLectNo());
			aVo.setStuId(sVo.getStuId());
			service.modifyDissentAndAttendee(sVo, aVo);
			coevaService.updateAttendeeScore(aVo);
			notiService.createAndSendNotification(sendId, stuList, message, notiCd, url,head );
			
		} else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			resp.put("message", errorMessage); // 메시지 추가
		}

		return ResponseEntity.status(status).body(resp);
	}

	@GetMapping("{lectNo}")
	@ResponseBody
	public Map<String, Object> getProfeName(@PathVariable String lectNo) {
		Map<String, Object> resp = new HashMap<>();

		List<LectureVO> lVo = service.readProfeName(lectNo);

		resp.put("profeList", lVo);

		return resp;
	}

	// 등록된 이의 삭제?
	// 부적절한 이의 신청 삭제 기능
	@DeleteMapping()
	public String delete() {

		return "dissent/courseDissentList";
	}

}
