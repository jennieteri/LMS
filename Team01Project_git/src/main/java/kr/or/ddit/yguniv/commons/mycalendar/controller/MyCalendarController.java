package kr.or.ddit.yguniv.commons.mycalendar.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.commons.mycalendar.service.MyCalendarService;
import kr.or.ddit.yguniv.vo.MyCalendarVO;

@Controller
@RequestMapping("/mycalendar")
public class MyCalendarController {
	
	@Autowired
	MyCalendarService service;
	
	//전체조회 사용자ID받아서
	@GetMapping
	@ResponseBody
	public List<Map<String, Object>> selectMycalendar(
			Principal user
			){
		String id = user.getName();
		if(id==null||id.isEmpty()) {
			throw new  PKNotFoundException("필수값누락: 사용자ID");
		}
		List<MyCalendarVO> events = service.readMyCalendarList(id);
        List<Map<String, Object>> response = new ArrayList<>();
        for (MyCalendarVO event : events) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", event.getMyCalendarNo()); // 일정 ID
            map.put("title", event.getMyCalendarTitle()); // 일정 제목
            map.put("start", event.getMyCalendarSd().toString()); // 시작 날짜
            map.put("end", event.getMyCalendarEd() != null ? event.getMyCalendarEd().toString() : null); // 종료 날짜
            map.put("description", event.getMyCalendarContent()); // 일정 내용
            response.add(map);
        }
        
         return response;
       
    }
	
	//사용자 캘린더 모듈
	@GetMapping("calendarmodual")
	public String selectMycalendarModual(){
		return "/moduleUI/myCalenderModual";
	}
	
	//일정추가
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> insertMycalendar(
			Principal user
			,@RequestBody @Validated MyCalendarVO myCalendar
			,BindingResult error
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		myCalendar.setPrsId(id);
		
		if(!error.hasErrors()) {
			service.createMyCalendar(myCalendar);
			body.put("message", "일정이 성공적으로 생성되었습니다.");
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	@PostMapping("job")
	@ResponseBody
	public ResponseEntity<Object> jobBoardLinkedMyCalendar(
			Principal user
			,@RequestBody MyCalendarVO myCalendar
			,BindingResult error
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		myCalendar.setPrsId(id);
		
		if(!error.hasErrors()) {
			// 수정에서 게시글연동처리
			service.linkedMycalendar(myCalendar,"job");
			body.put("message", "마이캘린더에 연동되었습니다.");
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
								.map(FieldError :: getDefaultMessage)
								.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
	
	@DeleteMapping("job/{boardNo}")
	@ResponseBody
	public ResponseEntity<Object> jobBoardDeleteMyCalendar(
			Principal user
			,@PathVariable String boardNo
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		MyCalendarVO myCalendar = new MyCalendarVO();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		myCalendar.setBoardNo(boardNo);
		myCalendar.setPrsId(id);
		service.deleteLinked(myCalendar, "job");
		
		return ResponseEntity.status(status).body(body);
	}
	
	@PostMapping("noti")
	@ResponseBody
	public ResponseEntity<Object> noticeBoardLinkedMyCalendar(
			Principal user
			,@RequestBody MyCalendarVO myCalendar
			,BindingResult error
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		myCalendar.setPrsId(id);
		
		if(!error.hasErrors()) {
			// 수정에서 게시글연동처리
			service.linkedMycalendar(myCalendar,"noti");
			body.put("message", "마이캘린더에 연동되었습니다.");
		}else {
			status = HttpStatus.BAD_REQUEST;
			String errorMessage = error.getFieldErrors().stream()
					.map(FieldError :: getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			body.put("message", errorMessage);
		}
		return ResponseEntity.status(status).body(body);
	}
	@DeleteMapping("noti/{boardNo}")
	@ResponseBody
	public ResponseEntity<Object> noticeDeleteMyCalendar(
			Principal user
			,@PathVariable String boardNo
			) {
		String id = user.getName();
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		MyCalendarVO myCalendar = new MyCalendarVO();
		
		if(id==null || id.isEmpty()) {
			status = HttpStatus.UNAUTHORIZED;
			body.put("message","로그인이 필요합니다!");
			return ResponseEntity.status(status).body(body);
		}
		myCalendar.setBoardNo(boardNo);
		myCalendar.setPrsId(id);
		service.deleteLinked(myCalendar, "noti");
		
		return ResponseEntity.status(status).body(body);
	}
	
	
	
	/*수정은 없어도될듯
	@PutMapping
	public String updateMycalendar() {
		return "";
	}
	*/
	@DeleteMapping("{myCalendarNo}")
	@ResponseBody
	public ResponseEntity<Object> deleteMycalendar(
			@PathVariable String myCalendarNo
			) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> body = new HashMap<>();
		if(myCalendarNo==null||myCalendarNo.isEmpty()) {
			body.put("message","필수값누락:해당일정번호없음");
		}
		service.deleteMyCalendar(myCalendarNo);
		body.put("message","일정삭제완료!");
		
		
		return ResponseEntity.status(status).body(body);
	}
	
	
}
