package kr.or.ddit.yguniv.board.answerBoard.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.board.answerBoard.service.AnswerBoardService;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.SystemInquiryBoardVO;
import lombok.extern.slf4j.Slf4j;

// 시스템 문의 게시판 
@Controller
@RequestMapping("/answer")
@Slf4j
public class AnswerBoardController {

	public static final String MODELNAME = "board";

	@Autowired
	private AnswerBoardService service;

	@ModelAttribute(MODELNAME)
	public SystemInquiryBoardVO board() {
		return new SystemInquiryBoardVO();
	}


	@GetMapping
	public String selectanswerList(

			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition, Model model) {

		PaginationInfo<SystemInquiryBoardVO> paging = new PaginationInfo<SystemInquiryBoardVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("board", service.readBoardList(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));

		return "answerBoard/answerBoardList";

	}

	@GetMapping("{sibNo}") // 상세보기
	public String selectanswerDetail(@PathVariable String sibNo, 
			Model model
			) {

		// HttpSession session 추가 
		SystemInquiryBoardVO board = service.readBoard(sibNo);
		board.setSibNo(sibNo);
		
	
		model.addAttribute("board", board);
		
		return "answerBoard/answerBoardDetail";

	}

	// 새글쓰기
	@GetMapping("createAnswerBoard/new")
	public String createAnswer(Model model, @ModelAttribute(MODELNAME) SystemInquiryBoardVO board) {

		model.addAttribute(MODELNAME, board);

		return "answerBoard/answerBoardForm";
	}

	@PostMapping("createAnswerBoard")
	public String create(@ModelAttribute("board") SystemInquiryBoardVO board,
	                     BindingResult errors,
	                     RedirectAttributes redirectAttributes,
	                     Principal principal) {
	   
	    // userId 설정
	    String userId = principal.getName();
	    if (userId == null || userId.isEmpty()) {
	        throw new IllegalArgumentException("로그인한 사용자의 ID가 없습니다.");
	    }
	    board.setUserId(userId);

	    // board 객체 Null 체크
	    if (board == null) {
	        throw new IllegalArgumentException("board 객체가 Null입니다.");
	    }

	    // 로그 확인
	    log.info("게시물 등록: {}", board);

	    // 데이터 저장
	    service.insert(board);

	    // 리다이렉트 설정
	    redirectAttributes.addFlashAttribute("board", board);
	    redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "board", errors);
	    return "redirect:/answer";
	}

	

	
	//수정
	@GetMapping("{sibNo}/edit")
	public String form(Model model, @PathVariable String sibNo, @ModelAttribute(MODELNAME) SystemInquiryBoardVO board) {
		
			
			model.addAttribute(MODELNAME, service.readBoard(sibNo) );
			

			return "answerBoard/answerBoardEdit";
	}
	

	@PostMapping("{sibNo}/edit") // 수정
	public String updateAnswer(@ModelAttribute(MODELNAME) SystemInquiryBoardVO board, BindingResult errors,
			RedirectAttributes redirectAttributes) {

		String lvn = null;
		if (!errors.hasErrors()) {
			try {
				service.update(board);
				lvn = "redirect:/answer/{sibNo}";
			} catch (BoardException e) {
				redirectAttributes.addFlashAttribute(MODELNAME, board);
				redirectAttributes.addFlashAttribute("message", e.getMessage());
				lvn = "redirect:/answer/{sibNo}";
			}
		} else {
			redirectAttributes.addFlashAttribute(MODELNAME, board);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
			return "redirect:/answer/{sibNo}/edit";
		}

		return lvn;

	}
	 @PostMapping("delete/{sibNo}")
		public String delete(
				@PathVariable String sibNo, 
				RedirectAttributes redirectAttributes
				) {
			
		  try {
		        // 게시글 삭제 서비스 호출
		        service.delete(sibNo);
		        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("error", "게시글 삭제 중 오류가 발생했습니다.");
		    }
		    // 게시글 목록으로 리다이렉트
		  return "redirect:/answer";
		 
				
			
		}
	
	
	// 답변 달기 
	 @ResponseBody
	 @PostMapping("/registerReply")
	    public String registerReply(@RequestBody SystemInquiryBoardVO board) {
		    log.info("여기는 왔니 ?,{}",board);

		    String result = "success";
	        try {
	            // 답변 내용 등록 및 상태 코드 변경 서비스 호출
	            service.updateReply(board);
	        } catch (Exception e) {
	            //e.printStackTrace();
	           // return "error";
	        	result ="fail";
	        }
	        return result;
	    }
	
	
	 
	 @PostMapping("deleteReply/{sibNo}") // 삭제
		public String deleteReply(
				@PathVariable String sibNo 
				,HttpSession session
				,RedirectAttributes redirectAttributes
				) {
		 String result = "success";
			try {
				service.deleteReply(sibNo);
				redirectAttributes.addFlashAttribute("message","답변이 삭제 되었습니다.");
				 
		    } catch (BoardException e) {
		    	result ="fail";
		    	redirectAttributes.addFlashAttribute("error", "답변 삭제 중 오류가 발생했습니다.");
		    }
		    // 게시글 목록으로 리다이렉트
			return "redirect:/answer/{sibNo}";
		}
	 
	 @ResponseBody
	 @PostMapping("updateReply")
	 public String updateReply(
			 @RequestBody SystemInquiryBoardVO board
			 ) {

		 String result = "success";
	        try {
	            // 답변 내용 등록 및 상태 코드 변경 서비스 호출
	            service.updateReplyNew(board);
	        } catch (Exception e) {
	            //e.printStackTrace();
	           // return "error";
	        	result ="fail";
	        }
	        return result;
  }
	 
}