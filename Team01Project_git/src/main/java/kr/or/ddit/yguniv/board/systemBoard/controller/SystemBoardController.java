

package kr.or.ddit.yguniv.board.systemBoard.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import kr.or.ddit.yguniv.board.systemBoard.service.SystemBoardService;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;

//시스템 공지사항 
@Controller
@RequestMapping("/systemBoard")
public class SystemBoardController {
	
	
	public static final String MODELNAME = "systemBoard";

	@Autowired
	private SystemBoardService service;
	
	@ModelAttribute(MODELNAME)
	public SystemNoticeBoardVO systemBoard() {
		return new SystemNoticeBoardVO();
	}
	

	@GetMapping() // 목록 조회
	public String selectList(

			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition, Model model

	) {
		PaginationInfo<SystemNoticeBoardVO> paging = new PaginationInfo<SystemNoticeBoardVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("boardList", service.selectList(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));

		return "systemBoard/systemBoardList";

	}

	// 상세조회
	@GetMapping("{snbNo}")
	public String selectSystemBoard(@PathVariable String snbNo, Model model) {
		SystemNoticeBoardVO systemBoard = service.selectSystemBoard(snbNo);
		model.addAttribute("systemBoard", systemBoard);
		return "systemBoard/systemBoardDetail";
	}
	
	// 작성 폼으로 이동 
	@GetMapping("createSystemBoard/new")
	public String createSystemBoard(
			HttpSession session ,
			Model model, 
			@ModelAttribute(MODELNAME) SystemNoticeBoardVO board
			) {
		//세선에 담기 
		String adminId = (String) session.getAttribute("adminId");
		 if (adminId== null|| adminId.isEmpty()) {
			 adminId= "관리자";
	        }
		 
		 board.setAdminId(adminId);
	
		 
		model.addAttribute(MODELNAME,board);
		
		
		return "systemBoard/systemBoardForm";
	}
	
	
	@PostMapping("createSystemBoard")
	public String create(
	        @ModelAttribute(MODELNAME) SystemNoticeBoardVO board,
	        BindingResult errors,
	        RedirectAttributes redirectAttributes
	) {
	    if (errors.hasErrors()) {
	        // 검증 실패 시 입력 폼으로 다시 이동
	        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
	        redirectAttributes.addFlashAttribute(MODELNAME, board);
	        return "redirect:/systemBoard/createSystemBoard/new";
	    }

	    // adminId가 없으면 "관리자"로 설정
	    if (board.getAdminId() == null || board.getAdminId().isEmpty()) {
	        board.setAdminId("관리자");
	    }

	    service.insertSystemBoard(board);

	    redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 등록되었습니다!");
	    return "redirect:/systemBoard/" + board.getSnbNo();
	}


	 @GetMapping("{snbNo}/edit")
		public String form(
				Model model,
				HttpSession session,
				@PathVariable String snbNo,
				@ModelAttribute(MODELNAME) SystemNoticeBoardVO board ) {
		 
		//세선에 담기 
			String adminId = (String) session.getAttribute("adminId");
			 if (adminId== null|| adminId.isEmpty()) {
				 adminId= "관리자";
		        }
			 
			 board.setAdminId(adminId);
			 
			
				model.addAttribute(MODELNAME, service.selectSystemBoard(snbNo));
			
				return "systemBoard/systemBoardEdit";
			
		
	 }
	 
	 @PostMapping("{snbNo}/edit")
	 public String update(
			 @ModelAttribute(MODELNAME) SystemNoticeBoardVO board,
			 BindingResult errors, RedirectAttributes redirectAttributes) {
		 
		 String lvn= null;
			if (!errors.hasErrors()) {
				try {
					service.updateSystemBoard(board);
					lvn = "redirect:/systemBoard/{snbNo}";
				} catch (BoardException e) {
					redirectAttributes.addFlashAttribute(MODELNAME, board);
					redirectAttributes.addFlashAttribute("message", e.getMessage());
					lvn = "redirect:/systemBoard/{snbNo}";
				}
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, board);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
				return "redirect:/systemBoard/{snbNo}/edit";
			}
			board.setAtchFile(null);
			return lvn;
		 
	 }
		
	 @PostMapping("delete/{snbNo}")
	 public String delete(
	         @PathVariable String snbNo, RedirectAttributes redirectAttributes) {

	     try {
	         // 게시글 삭제 서비스 호출
	         service.deleteSystemBoard(snbNo);
	         redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
	     } catch (Exception e) {
	         redirectAttributes.addFlashAttribute("error", "게시글 삭제 중 오류가 발생했습니다.");
	     }
	     // 게시글 목록으로 리다이렉트
	     return "redirect:/systemBoard";
	 }

	 
	 
	 // 되는지 안되는지 확인 
	 @GetMapping("/mainSystemBoard")
	 public String mainPage(
		Model model
	
			 ) {
		 
		 model.addAttribute(MODELNAME, service.mainSystemBoardList());
		 return "/moduleUI/system";
	 }
			
		
}
