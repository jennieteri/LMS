package kr.or.ddit.yguniv.log.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.log.service.LogService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.LectureListDTO;
import kr.or.ddit.yguniv.vo.LogDTO;
import kr.or.ddit.yguniv.vo.LogVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/log")
@Controller
@RequiredArgsConstructor
public class LogController {
	
	private final LogService service;
	
	//전체조회
	@GetMapping()
	public String selectlist() {
			
		return "log/logList";
	}
	//트래픽 로그 조회
	@GetMapping("trafic")
	public String selectTrafic() {
		
		return "admintrafic/adminTrafic";
	}

	
	@GetMapping("{id}")
	@ResponseBody
	public Map<String, Object> select(
	    @RequestParam(required = false, defaultValue = "1") int page
	    ,@ModelAttribute("condition") SimpleCondition simpleCondition
	    ,@PathVariable String id
	) {
		
//		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",id);
	    PaginationInfo<LogVO> paging = new PaginationInfo<>();
	    paging.setCurrentPage(page);
	    paging.setSimpleCondition(simpleCondition);
	    PaginationRenderer renderer = new BootStrapPaginationRenderer();
	    
	    List<LogVO> logList = service.getPersonLog(paging, id);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("logList", logList);
	    response.put("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
	    
	    return response;
	}
	
	@GetMapping("admin")
	@ResponseBody
	public ResponseEntity<LogDTO> getLogData() {
		
		List<LogVO> logList = service.getLogDayList();
		String totalCount = String.valueOf(service.getTotalLogCount());
		
		LogDTO dto = new LogDTO();
		
		dto.setTodayLogList(logList);
		dto.setTotalCountLog(totalCount);
		return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping("statisticspersonlogin")
	public String getLogDataModual() {
	    return "/moduleUI/personModual";
	}

	
	
	@GetMapping("trafic/{logDate}")
	@ResponseBody
	public ResponseEntity<LogDTO> getTraficData(@PathVariable String logDate) {
		
		List<LogVO> logList = service.getTraficLogList(logDate);
		List<LogVO> methodLogList = service.getTraficMethodLogList(logDate);
		
		
		LogDTO dto = new LogDTO();
		
		dto.setTraficLogList(logList);
		dto.setTraficMethodLogList(methodLogList);
		
		return ResponseEntity.ok(dto);
	}

	@GetMapping("trafic/module")
	public String getTraficData2() {
		
		return "/moduleUI/adminTraficModule";
	}
	
	@GetMapping("trafic/module/data")
	@ResponseBody
	public ResponseEntity<LogDTO> getTraficData3() {
		
		LocalDateTime dateTime = LocalDateTime.now();
		// 포맷 정의 (HHmm 형식)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		// 시간 부분만 포맷하여 문자열로 변환
		String logDate = dateTime.format(formatter);
		
		List<LogVO> logList = service.getTraficLogList(logDate);
		List<LogVO> methodLogList = service.getTraficMethodLogList(logDate);
		
		LogDTO dto = new LogDTO();
		
		dto.setTraficLogList(logList);
		dto.setTraficMethodLogList(methodLogList);
		
		return ResponseEntity.ok(dto);
	}
	
	
	
	
	
	
	

}

