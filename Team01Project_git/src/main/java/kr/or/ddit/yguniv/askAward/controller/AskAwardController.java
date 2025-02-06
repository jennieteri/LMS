package kr.or.ddit.yguniv.askAward.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.askAward.service.AskAwardService;
import kr.or.ddit.yguniv.award.dao.AwardMapper;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.AwardAskVO;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.CommonCodeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/askAward")
public class AskAwardController {


	public static final String MODELNAME ="ask";
	
	@Autowired
	private AskAwardService service;
	
	
	// 상태 코드 
	@Autowired
	CommonCodeServiceImpl cocoService;
	
	@ModelAttribute(MODELNAME)
	public AwardAskVO ask() {
		return new AwardAskVO();
	}
	
	// 관리자가 보는 장학금 신청서 목록 조회 
	@GetMapping
	public String askAwardList(
			
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition, Model model
			
			){
		   List<CommonCodeVO> statusCodes = cocoService.getCodeList("STATUS_CODES"); // 상태 코드 조회
		    model.addAttribute("statusCodes", statusCodes);
		    
		PaginationInfo<AwardAskVO> paging = new PaginationInfo<AwardAskVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("ask", service.awardAskList(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "awardAsk/awardAskList";
		
	}

	  
	  // 교직원이 보는 장학금 신청서 상세조회
	
	  
	 @GetMapping("{shapDocNo}") 
	 public String selectAskAwardDetail(
			 
			 @PathVariable String shapDocNo
			 ,Model model
			 )
	 	{ 
		AwardAskVO ask = service.select(shapDocNo);
		
		  // 디버깅용 로그 출력
	    log.info("상세조회 결과:{}",ask);
		model.addAttribute(MODELNAME, ask);
		
		 return "awardAsk/awardAskDetail"; 
		
	 	}
	  
	 
	 @GetMapping("createAwardAsk/new")
	 public String createAwardAskForm(
	         @RequestParam(name = "awardCd", required = false) String awardCd,
	         @RequestParam(name = "awardNm", required = false) String awardNm,
	         Model model
	 ) {
	     if (awardCd != null) {
	         model.addAttribute("selectedAwardCd", awardCd);
	     }
	     if (awardNm != null) {
	         model.addAttribute("selectedAwardNm", awardNm);
	     }
	     return "awardAsk/awardAskForm";
	 }


	  
	  @PostMapping ("createAskAward")
	  public String create(
			  @ModelAttribute(MODELNAME) AwardAskVO ask, 
			  BindingResult errors,
			  RedirectAttributes redirectAttributes,
			  Principal principal
			  
			  ) throws IllegalAccessException{ 
		  
		 String stuId = principal.getName();
		 if(stuId == null || stuId.isEmpty()) {
			 
			 throw new IllegalAccessException("로그인한 학생이 없습니다.");
		 }
		  log.info("유정보드체크 {}",ask);
		  
		  ask.setStuId(stuId);
		  
		  // board 객체 Null 체크
		    if (ask == null) {
		        throw new IllegalArgumentException("board 객체가 Null입니다.");
		    }

		  service.insertAwardAsk(ask);
		  
		  redirectAttributes.addFlashAttribute(MODELNAME, ask);
		  redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
		  

		  	return "redirect:/askAward/"+ask.getShapDocNo()+"/student";
	  }
	  
	  
	  //학생이 보는 상세조회 리스트
	  @GetMapping("selectStudent/{stuId}")
	  public String selectStudentAwardAsk(
	          Model model,
	          @PathVariable String stuId,
	          Principal principal
	  ) throws IllegalAccessException {
	      // 로그인된 사용자 ID 확인
	      String loggedInStuId = principal.getName();
	      if (loggedInStuId == null || loggedInStuId.isEmpty()) {
	          return "redirect:/askAward"; // 학생 존재하지 않을 경우 목록 화면으로 이동
	      }

	      // 로그인된 사용자와 요청된 stuId가 일치하지 않는 경우
	      if (!loggedInStuId.equals(stuId)) {
	          return "redirect:/askAward"; // 목록 화면으로 이동
	      }

	      // 학생 신청 내역 조회
	      List<AwardAskVO> studentList = service.studentAwardAskList(stuId);
	      if (studentList == null || studentList.isEmpty()) {
	          model.addAttribute("message", "신청 내역이 없습니다.");
	      } else {
	          model.addAttribute("ask", studentList);
	      }

	      return "awardAsk/awardAskStuList";
	  }

	 
	 
	 
	  
	  //학생이 상세조회  
	 @GetMapping("{shapDocNo}/student")
	 public String selectStudent(
			 @PathVariable String shapDocNo,
			 Model model
			 ) {
		 
		 model.addAttribute("ask",service.select(shapDocNo));
		
		 return "awardAsk/awardAskStuDetail"; 
		 
	 }
	 
	 
	 		//학생이 수정하는 것도 폼이 같아야겠네
		 // 장학금 정보 수정은 회원 정보 수정에서 하기 
	 // 학생 마이페이지에서 확인할 떄 
		 @GetMapping ("{shapDocNo}/student/edit")
		 public String updateStudentAwardAsk
		 		(
				 @PathVariable String shapDocNo,
				 Model model, 
				 @ModelAttribute(MODELNAME) AwardAskVO ask 
				 ) {
			 
			 model.addAttribute(MODELNAME,service.select(shapDocNo));
			 
			 
			 return "awardAsk/awardAskStuEdit";
			 
		 }
		 
		 @PostMapping("{shapDocNo}/student/edit")
		 public String update(
				 
				 @ModelAttribute(MODELNAME) AwardAskVO ask
				 ,BindingResult errors
				 ,RedirectAttributes redirectAttributes
				 ) {
			 String lvn= null;
				if (!errors.hasErrors()) {
					try {
						service.updateAwardAsk(ask);
						
						lvn = "redirect:/askAward/"+ask.getShapDocNo()+"/student";
					} catch (BoardException e) {
						redirectAttributes.addFlashAttribute(MODELNAME, ask);
						redirectAttributes.addFlashAttribute("message", e.getMessage());
						lvn = "redirect:/askAward/"+ask.getShapDocNo()+"student";
					}
				} else {
					redirectAttributes.addFlashAttribute(MODELNAME, ask);
					redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
					return "redirect:/askAward/"+ask.getShapDocNo()+"/student/edit";
				}
				ask.setAtchFile(null);
				return lvn;
			 
		 }
		 
		 
		 
		 
		 
		//학생이 삭제 
	  //학생이 [장학금신청] 버튼을 만들어야함. 그 버튼을 뉴누르면 이제 신청 
	 // 신청하기 전에 동의서에 체크를 해야 넘어올 수 있음 
	 // 신청하기 버튼을 눌러야 신청하는 것으로 들어갈 수 있음 - 신청폼 만들어져 있음
	 // 학생이 신청할 수 있는 신청폼 - 동의 

		 @PostMapping("delete/{shapDocNo}")
		 public String deleteAward(
		     @PathVariable String shapDocNo,
		     Principal principal, // Principal 객체 추가
		     RedirectAttributes redirectAttributes
		 ) {
		     log.info("삭제 요청 shapDocNo: " + shapDocNo); // 디버깅 로그 추가

		     try {
		         // 로그인한 학생의 ID 가져오기
		         String stuId = principal.getName();
		         if (stuId == null || stuId.isEmpty()) {
		             throw new IllegalAccessException("학생이 존재하지 않습니다.");
		         }

		         // 서비스 호출: 삭제 수행
		         service.deleteAwardAsk(shapDocNo);

		         // 삭제 완료 메시지 추가
		         redirectAttributes.addFlashAttribute("message", "장학금 신청이 삭제되었습니다.");

		         // 학생의 신청 내역 리스트로 리다이렉트
		         return "redirect:/askAward/selectStudent/" + stuId;
		     } catch (BoardException | IllegalAccessException e) {
		         // 오류 발생 시 에러 메시지 추가
		         redirectAttributes.addFlashAttribute("error", "삭제 중 문제가 발생했습니다.");
		         return "redirect:/askAward"; // 에러 발생 시 기본 경로로 리다이렉트
		     }
		 }

		 
// 상태변경
		 @PostMapping("/updateStatus")
		 public String updateStatus(
				 @RequestParam String cocoStts, 
				 @RequestParam String shapDocNo,
				 @RequestParam(required = false) String shapNoReason // 반려 사유
				 ,Model model
				 ) {
		     service.updateApplicationStatus(cocoStts, shapDocNo,shapNoReason);
		     
		  // 변경된 데이터 로드
		     AwardAskVO updatedApplication = service.select(shapDocNo);
		     model.addAttribute("ask", updatedApplication);
		     
		     return "redirect:/askAward"; // 상세 페이지로 이동
		 }



	 
	  @Inject
	  private AwardMapper awardMapper;
	  
	  @ModelAttribute("awardList")
	  public List<AwardVO> awardList(){
		  return awardMapper.selectList();
	  }
	  
	  
	  
}
