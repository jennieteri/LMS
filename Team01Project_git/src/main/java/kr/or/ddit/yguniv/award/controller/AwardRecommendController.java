package kr.or.ddit.yguniv.award.controller;

import java.security.Principal;
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

import kr.or.ddit.yguniv.award.dao.AwardMapper;
import kr.or.ddit.yguniv.award.service.AwardRecommendService;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.person.dao.PersonMapper;
import kr.or.ddit.yguniv.student.dao.StudentMapper;
import kr.or.ddit.yguniv.vo.AwardAskVO;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("recommendAward")
public class AwardRecommendController {
	
	
	public static final String MODELNAME ="ask";
	
	@Autowired
	private AwardRecommendService service;
	
	// 상태 코드 
		@Autowired
		CommonCodeServiceImpl cocoService;
		
		@ModelAttribute(MODELNAME)
		public AwardAskVO ask() {
			return new AwardAskVO();
		}
	
	
	// 교수가 추천
	// 장학금 추천서 목록 조회 동일해서 안 만듬 
//	@GetMapping
//	public String selectrecommendAward(
//			
//			
//			){
//		return "recommendAward/recommendAwardList";
//	}
	
	 // 교직원이 보는 교수가 추천한 추천서ㅓ 상세조회
	@GetMapping("{shapDocNo}")
	public String selectAwardRec(
			@PathVariable String shapDocNo
			,Model model
			) {
		
		AwardAskVO ask  = service.selectAwardRec(shapDocNo);
		
		log.info("게시글 번호 {}", ask);
		
		model.addAttribute(MODELNAME , ask);
		
		return "recommendAward/recommendAwardDetail"; 
	}
	
	// 추천서 작성 
	@GetMapping("createRecAward/new")
	public String insertAwardRec (
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
	     return "recommendAward/recommendAwardForm";
		
	}
	
	
	@PostMapping("createRecAward")
	public String createRecAward(@ModelAttribute(MODELNAME) AwardAskVO ask, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal) throws IllegalAccessException {
	    // 로그인한 교수 확인
	    String profeId = principal.getName();
	    if (profeId == null || profeId.isEmpty()) {
	        throw new IllegalAccessException("로그인한 교수가 없습니다.");
	    }

	    // 학번 확인
	    if (ask.getStuId() == null || ask.getStuId().isEmpty()) {
	        errors.rejectValue("stuId", "error.stuId", "학번이 누락되었습니다.");
	        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
	        return "redirect:/recommendAward/createRecAward/new";
	    }

	    // 서비스 호출 전 유효성 점검
	    ask.setProfeId(profeId);
	    service.createRecAward(ask);

	    redirectAttributes.addFlashAttribute(MODELNAME, ask);
	    return "redirect:/recommendAward/" + ask.getShapDocNo() + "/professor";
	}

	
	
	// 교수가 보는 상세조회 리스트 <LIST>
	@GetMapping("selectPro/{profeId}")
	public String selectProfessorAward(
			Model model
			,@PathVariable String profeId
			,Principal principal
			)throws IllegalAccessException {
		
		// 로그인된 사용자 ID 확인
	      String loggedInStuId = principal.getName();
	      if (loggedInStuId == null || loggedInStuId.isEmpty()) {
	          return "redirect:/askAward"; // 학생 존재하지 않을 경우 목록 화면으로 이동
	      }

	      // 로그인된 사용자와 요청된 stuId가 일치하지 않는 경우
	      if (!loggedInStuId.equals(profeId)) {
	          return "redirect:/askAward"; // 목록 화면으로 이동
	      }

	      // 학생 신청 내역 조회
	      List<AwardAskVO> professor = service.selectAwardProRecList(profeId);
	      if (professor == null || professor.isEmpty()) {
	          model.addAttribute("message", "신청 내역이 없습니다.");
	      } else {
	          model.addAttribute("ask", professor);
	      }
		
		
		return "recommendAward/awardProList";
	}

	
	 
	  //교수가 상세조회  
	 @GetMapping("{shapDocNo}/professor")
	 public String selectStudent(
			 @PathVariable String shapDocNo,
			 Model model
			 ) {
		 
		 model.addAttribute("ask",service.selectAwardRec(shapDocNo));
		
		 return "recommendAward/recommendAwardDetail"; 
		 
	 }
	
	
	
	// 삭제
	@PostMapping("delete/{shapDocNo}")
	public String deletePro(
			@PathVariable String shapDocNo,
		     Principal principal, // Principal 객체 추가
		     RedirectAttributes redirectAttributes
			
			) {
		  try {
		         // 로그인한 학생의 ID 가져오기
		         String proId = principal.getName();
		         if (proId == null || proId.isEmpty()) {
		             throw new IllegalAccessException("교수 존재하지 않습니다.");
		         }

		         // 서비스 호출: 삭제 수행
		         service.deleteAwardRec(shapDocNo);

		         // 삭제 완료 메시지 추가
		         redirectAttributes.addFlashAttribute("message", "장학금 신청이 삭제되었습니다.");

		         // 학생의 신청 내역 리스트로 리다이렉트
		         return "redirect:/recommendAward/selectPro/" + proId;
		        
		     } catch (BoardException | IllegalAccessException e) {
		         // 오류 발생 시 에러 메시지 추가
		         redirectAttributes.addFlashAttribute("error", "삭제 중 문제가 발생했습니다.");
		         return "redirect:/recommendAward"; // 에러 발생 시 기본 경로로 리다이렉트
		     }
		
		
	}
	// 수정
	 @GetMapping ("{shapDocNo}/professor/edit")
	 public String updateProfessorAwardAsk
	 		(
			 @PathVariable String shapDocNo,
			 Model model, 
			 @ModelAttribute(MODELNAME) AwardAskVO ask 
			 ) {
		 
		 model.addAttribute(MODELNAME,service.selectAwardRec(shapDocNo));
		 
		 
		 return "recommendAward/recommendAwardEdit";
		 
	 }
	 
	 @PostMapping("{shapDocNo}/professor/edit")
	 public String update(
			 
			 @ModelAttribute(MODELNAME) AwardAskVO ask
			 ,BindingResult errors
			 ,RedirectAttributes redirectAttributes
			 ) {
		 String lvn= null;
			if (!errors.hasErrors()) {
				try {
					service.updateAwardRec(ask);
					
					lvn = "redirect:/recommendAward/"+ask.getShapDocNo()+"/professor";
				} catch (BoardException e) {
					redirectAttributes.addFlashAttribute(MODELNAME, ask);
					redirectAttributes.addFlashAttribute("message", e.getMessage());
					lvn = "redirect:/recommendAward/"+ask.getShapDocNo()+"professor";
				}
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, ask);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
				return "redirect:/recommendAward/"+ask.getShapDocNo()+"/professor/edit";
			}
			ask.setAtchFile(null);
			return lvn;
		 
	 }
	 
	 
	  @Inject
	  private AwardMapper awardMapper;
	  
	  @ModelAttribute("awardList")
	  public List<AwardVO> awardList(){
		  return awardMapper.selectList();
	  }
	  
	  
	  @Inject
	  private StudentMapper studentMapper;
	  
	  @ModelAttribute("stuList")
	  public List<StudentVO> stuList(){
		  return studentMapper.selectStudentList();
	  }
	 
	 
}
