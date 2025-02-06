package kr.or.ddit.yguniv.jobboard.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.mycalendar.service.MyCalendarService;
import kr.or.ddit.yguniv.jobboard.service.JobBoardService;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import lombok.extern.slf4j.Slf4j;
import retrofit2.http.Path;

@RequestMapping("/jobboard")
@Controller
@Slf4j
public class JobBoardController {
	public static final String MODELNAME ="jobboard";
	
	@Autowired
	private MyCalendarService myCalendarService;
	@Autowired
	private JobBoardService service;
	
	@ModelAttribute(MODELNAME)
	public JobBoardVO board() {
		return new JobBoardVO();
	}
	
	//form으로 보냄
	@GetMapping("new")
	public String createForm(Model model) {
		return "jobBoard/jobBoardForm";
	}
	
	
	//게시글전체조회(검색어 있는지 없는지 ntcNm 제목 / ntcDesc 내용)
	@GetMapping
	public String selectlist(
		@RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
			) {
			PaginationInfo<JobBoardVO>paging = new PaginationInfo<>();
			paging.setCurrentPage(page);
			paging.setSimpleCondition(simpleCondition);
			model.addAttribute("list", service.selectJobBoardListPaging(paging));
			PaginationRenderer renderer = new BootStrapPaginationRenderer();
		    List<Map<String, Object>> statistics = service.jobBoardStatics();
		    model.addAttribute("statistics", statistics); 
			model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "jobBoard/jobBoardList";
	}
	
	@GetMapping("jobboardlistmodual")
	public String selectlistModual(
		@RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
			) {
			PaginationInfo<JobBoardVO>paging = new PaginationInfo<>();
			paging.setCurrentPage(page);
			paging.setSimpleCondition(simpleCondition);
			model.addAttribute("list", service.selectJobBoardListPaging(paging));
			PaginationRenderer renderer = new BootStrapPaginationRenderer();
		    List<Map<String, Object>> statistics = service.jobBoardStatics();
		    model.addAttribute("statistics", statistics); 
			model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "/jobBoard/jobBoardListModual";
	}
	
	//게시글 생성
	@PostMapping("create")
	public String create(
			@ModelAttribute(MODELNAME) JobBoardVO board
			, BindingResult errors
			, RedirectAttributes redirectAttributes
			) {
		try {
			String lvn = null;
			if (!errors.hasErrors()) {
				service.insertJobBoard(board);
				lvn = "redirect:/jobboard/" + board.getJobNo();
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, board);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
				lvn = "redirect:/jobboard/new";
			}
			return lvn;
		}catch (Throwable e) {
			throw new BoardException(e);
		}
	}
	
	@GetMapping("{jobNo}/atch/{atchFileId}/{fileSn}")
	public ResponseEntity<Resource> serveFile(
	        @PathVariable("jobNo") String jobNo,
	        @PathVariable("atchFileId") int atchFileId,
	        @PathVariable("fileSn") int fileSn) {
	    // 1. 파일 정보 조회
	    AtchFileDetailVO fileDetail = service.download(atchFileId, fileSn);

	    // 2. 파일 존재 여부 확인
	    Resource resource = fileDetail.getSavedFile();
	    if (resource == null || !resource.exists()) {
	        throw new RuntimeException("파일을 찾을 수 없습니다.");
	    }

	    // 3. MIME 타입 설정
	    String contentType = fileDetail.getFileMime();
	    if (contentType == null) {
	        contentType = "application/octet-stream";
	    }

	    // 4. 파일 반환
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_TYPE, contentType)
	            .body(resource);
	}

	
	
	//게시글상세조회
	@GetMapping("{jobNo}")
	public String select(
			@PathVariable("jobNo")String jobNo
			, Model model
			, Principal user
			) {
		//캘린더 연동여부 체크
		Map<String,Object> param = new HashMap<>();
		param.put("id", user.getName());
		param.put("no", jobNo);
		param.put("type", "job");
		//상세조회
		JobBoardVO board = service.selectJobBoard(jobNo);
		String stuId = user.getName();
		//캘린더연동체크
		boolean linkedYn = myCalendarService.checkMyCal(param);
		int isRegistred = service.isUserRegisteredForJobBoard(stuId, jobNo);
		
		model.addAttribute("isRegistered", isRegistred);
		model.addAttribute("jobboard", board);
		model.addAttribute("linkedYn", linkedYn);
		
		return "jobBoard/jobBoardDetail";
	}
	
	//게시글 수정 폼 이동 
	@GetMapping("{jobNo}/edit")
	public String update(Model model,@PathVariable()String jobNo) {
		model.addAttribute(MODELNAME, service.selectJobBoard(jobNo));
		return "jobBoard/jobBoardEditForm";
	}
	
	//게시글 수정
	@PostMapping("{jobNo}/edit")
	public String update(@ModelAttribute(MODELNAME) JobBoardVO board
			 ,BindingResult errors
			 , RedirectAttributes redirectAttributes) {
		 String lvn= null;
			if (!errors.hasErrors()) {
				try {
					service.updateJobBoard(board);
					lvn = "redirect:/jobboard/{jobNo}";
				} catch (BoardException e) {
					redirectAttributes.addFlashAttribute(MODELNAME, board);
					redirectAttributes.addFlashAttribute("message", e.getMessage());
					lvn = "redirect:/jobboard/{jobNo}";
				}
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, board);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
				return "redirect:/jobboard/{jobNo}/edit";
			}
			board.setAtchFile(null);
			return lvn;
	}

	@PutMapping("{jobNo}/delete")
	@ResponseBody
	public ServiceResult delete(@PathVariable String jobNo, @RequestBody JobBoardVO board) {
		board.setJobNo(jobNo);
	    ServiceResult result = service.deletejobBoard(board);

	    if (result == ServiceResult.FAIL) {
	        throw new RuntimeException("게시글 삭제 실패");
	    }

	    return result; 
	}
	
	
}

