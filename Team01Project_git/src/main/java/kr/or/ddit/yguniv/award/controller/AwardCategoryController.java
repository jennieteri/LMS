package kr.or.ddit.yguniv.award.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.award.service.AwardService;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.AwardVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/award")
public class AwardCategoryController {
	
	
	public static final String MODELNAME = "award";
	
	@Autowired
	private AwardService service;
	
	@ModelAttribute(MODELNAME)
	public AwardVO award() {
		return new AwardVO();
	}

	//장학금 목록 조회
	@GetMapping
	public String SelectList(Model model) {
		
		model.addAttribute("award", service.awardList());
		
		return "award/awardList";
		
	}
	
	//  관리자만 볼 수 있는 sidebar에 새롭게 만들거임요 
	@GetMapping("selectAdminAwardList")
	public String selectAdminAwardList(
			
			
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition, Model model
			
			) {
		PaginationInfo<AwardVO> paging = new PaginationInfo<AwardVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("award",service.selectAdminAwardList(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));

		return "award/selectAdminAwardList";
	}
	
	//관리자가 볼 수 있는 상세조회 
	@GetMapping("{awardCd}/detail")
	public String selectAdmin(
			@PathVariable String awardCd
			, Model model
			) {
		AwardVO award = service.selectAward(awardCd);
		model.addAttribute("award",award);
		
		return "award/selectAdminDetail";
	}
	
	
	// 새글 쓰기 
	@GetMapping("insertAward/new")
	public String insertAward(Model model, @ModelAttribute(MODELNAME) AwardVO award) {
		
		model.addAttribute(MODELNAME,award);
		
		return  "award/awardForm";
		
	}
	// 장학금 유형 추가 
	@PostMapping("insertAward")
	public String Award(@ModelAttribute(MODELNAME) AwardVO award,BindingResult errors,
			RedirectAttributes redirectAttributes
			) {
		
		  if (errors.hasErrors()) {
			  redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
		        return "redirect:/award/insertAward/new";
		    }
		  // 서비스 계층 호출
		    service.insertAward(award);
		  // 성공적으로 저장한 후 리다이렉트
		    return "redirect:/award/selectAdminAwardList";
	}
	
//장학금 유형 수정 폼
	@GetMapping("{awardCd}/edit")
	public String updateAward(@PathVariable String awardCd,Model model,
			@ModelAttribute(MODELNAME) AwardVO award) {
		
		log.info("유정 보드 체크 {}", awardCd);
		model.addAttribute(MODELNAME, service.selectAward(awardCd));
		
		return "award/awardEdit";
	}
	
	//수정
	@PostMapping("{awardCd}/edit")
	public String update(@ModelAttribute(MODELNAME) AwardVO award,
			 BindingResult errors, RedirectAttributes redirectAttributes) {

		 String lvn= null;
			if (!errors.hasErrors()) {
				try {
					service.updateAward(award);
					lvn = "redirect:/award/selectAdminAwardList";
				} catch (BoardException e) {
					redirectAttributes.addFlashAttribute(MODELNAME, award);
					redirectAttributes.addFlashAttribute("message", e.getMessage());
					lvn = "redirect:/award/selectAdminAwardList";
				}
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, award);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
				return "redirect:/award/{awardCd}/edit";
			}
			
			return lvn;
		
	}
	// 장학금 유형 삭제
	@PostMapping("delete/{awardCd}")
	public String deleteAward(
			@PathVariable String awardCd, 
			RedirectAttributes redirectAttributes
			) {
		 try {
	         
			 service.deleteAward(awardCd);
	         redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
	     } catch (Exception e) {
	         redirectAttributes.addFlashAttribute("error", "게시글 삭제 중 오류가 발생했습니다.");
	     }
	     // 게시글 목록으로 리다이렉트
	     return "redirect:/award/selectAdminAwardList";
		

	

	}
	
	
}
