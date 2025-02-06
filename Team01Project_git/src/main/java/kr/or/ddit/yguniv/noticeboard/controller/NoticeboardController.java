package kr.or.ddit.yguniv.noticeboard.controller;




import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.atlas.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.commons.enumpkg.NotificationCode;
import kr.or.ddit.yguniv.commons.mycalendar.service.MyCalendarService;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.noticeboard.service.NoticeBoardService;
import kr.or.ddit.yguniv.notification.service.NotificationService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author AYS
 * lvn에 ?는 디테일로 갈 수 있게 해당넘버를 붙여줘야함
 * redirect: 도 고려해야할 것 같음
 */
@Slf4j
@Controller
@RequestMapping("/noticeboard")
public class NoticeboardController {
	public static final String MODELNAME = "noticeboard";

	@Autowired
	private MyCalendarService myCalendarService;
	
	@Autowired
	private NoticeBoardService service;
	
	@Autowired
	private CommonCodeServiceImpl cocoService;
	
	@ModelAttribute(MODELNAME)
	public NoticeBoardVO board() {
	    return new NoticeBoardVO();
	}
	
	//게시글전체조회(검색어 있는지 없는지 ntcNm 제목 / ntcDesc 내용)
	@GetMapping
	public String selectList(
		@RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model
	) {
		PaginationInfo<NoticeBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("boardList", service.readNoticeBoardListPaging(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		
		//renderPagination 에는 페이징처리함수명 넣어야함 현재 fnPaging자리
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		model.addAttribute("semesterList", cocoService.getSemesterList(null));
			
		return "noticeboard/noticeboardList";
	}
	
	//게시글 생성폼이동
	@GetMapping("new")
	public String createForm(Model model) {
		
		return "noticeboard/noticeboardForm";
	}
	
	@Autowired
	private NotificationService notiService;
	//게시글 생성
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> create(
			@Validated NoticeBoardVO noticeboard
			, BindingResult errors
			, Principal prin
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			if (!errors.hasErrors()) {
				service.createNoticeBoard(noticeboard);
				
				String cocoCd = noticeboard.getCocoCd();
				
				int num = noticeboard.getNtcCd();
				log.info("게시판 값{}", num);
				if("NB04".equals(cocoCd)) {
					String message = "예비수강신청 게시글이 등록되었습니다."; 
					String notiCd =  NotificationCode.INFO+""; //NotificationCode.INFO+"" NotificationCode.WARN+"" 
					String sendId = prin.getName();
					List<String> stuList = service.getStudentList();
					String url = "noticeboard/"+num; 
					String url2 = " "; 
					String head = "예비수강신청"; 
					notiService.createAndSendNotification(sendId, stuList, message, notiCd, url,head );

				}
				
				body.put("message","일정이 정상적으로 생성되었습니다!");
				
			} else {
				body.put("message","부적합한 입력값!");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
			
		}catch (Exception e) {
			body.put("message","게시글 생성 중 오류 발생");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//게시글상세조회
	@GetMapping("{ntcCd}")
	public String select(
			@PathVariable()int ntcCd
			, Model model
			, Principal user
			) {
		//캘린더 연동여부 체크
		Map<String,Object> param = new HashMap<>();
		param.put("id", user.getName());
		param.put("no", String.valueOf(ntcCd));
		param.put("type", "noti");
		//캘린더연동체크
		boolean linkedYn = myCalendarService.checkMyCal(param);
		NoticeBoardVO board = service.readNoticeBoard(ntcCd);
		
		model.addAttribute("noticeboard", board);
		model.addAttribute("linkedYn", linkedYn);
		
		return "noticeboard/noticeboardDetail";
	}
	//게시글수정폼이동
	@GetMapping("{ntcCd}/edit")
	public String updateForm(Model model, @PathVariable Integer ntcCd) {
		model.addAttribute(MODELNAME, service.readNoticeBoard(ntcCd));
		return "noticeboard/noticeboardEditForm";
	}
	
	//게시글수정
	@PostMapping("edit")
	@ResponseBody
	public ResponseEntity<Object> edit(
			@Validated NoticeBoardVO noticeboard
			, BindingResult errors
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		try {
			if (!errors.hasErrors()) {
				service.modifyNoticeBoard(noticeboard);
				body.put("message","일정이 정상적으로 수정되었습니다!");
				
			} else {
				body.put("message","부적합한 입력값!");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
			
		}catch (Exception e) {
			body.put("message","게시글 수정 중 오류 발생");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	//게시글삭제
	@PostMapping("{ntcCd}/delete")
	public String delete(@PathVariable()int ntcCd,  RedirectAttributes redirectAttributes) {
		
		try {
			service.removeNoticeBoard(ntcCd);
			return "redirect:/noticeboard";
		}catch (BoardException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/noticeboard/{ntcCd}";
		}
	}
	
	//캘린더연동처리
	@GetMapping("calendar")
	@ResponseBody
	public List<Map<String, Object>> getAll() {
		List<NoticeBoardVO> noticeBoardList = service.readNoticeBoardList();
		List<Map<String, Object>> events = new ArrayList<>();
		
		  for (NoticeBoardVO board : noticeBoardList) {
	            Map<String, Object> event = new HashMap<>();
	            event.put("title", board.getNtcNm()); // 제목
	            event.put("start", board.getNtcDt().toString()); // 시작 날짜
	            if (board.getNtcEt() != null) {
	                event.put("end", board.getNtcEt().toString()); // 종료 날짜
	            }
	            event.put("description", board.getNtcDesc()); // 내용 
	            event.put("url", "/yguniv/noticeboard/" + board.getNtcCd()); // 클릭 시 이동할 URL
	            events.add(event);
	        }
		
		  return events;
	}
	
	
	
	
	
}
