package kr.or.ddit.yguniv.attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.absencecertificatereceipt.service.AbsenceCertificateReceiptServiceImpl;
import kr.or.ddit.yguniv.attendance.service.AttendanceServiceImpl;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import kr.or.ddit.yguniv.vo.LogDTO;
import kr.or.ddit.yguniv.vo.LogVO;

// url 예상도
// 강의 / 강의과정 / attendance

@Controller
@RequestMapping("/lecture/{lectNo}/attendan") // 여기서부터 애초에 조건을 나누어서 정해주면된다. lectNo로 나눈다 NM 쓰면안됨
public class AttendanceController {
	
	public static final String MODELNAME = "absence";
	
	@Autowired
	private AttendanceServiceImpl attendanService;
	
	@Autowired // 공결 인정 서류 서비스
	private AbsenceCertificateReceiptServiceImpl absenceService;

	
	@ModelAttribute(MODELNAME)
	public AbsenceCertificateReceiptVO absence() {
		return new AbsenceCertificateReceiptVO();
	}
	
	
	// 강의주차, 차수별강의자료 정보 서비스
	@Autowired
	private LectureMaterialsServiceImpl lectureService;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model) {
		LectureVO lectureVO = lectureService.selectLecture(lectNo);
		if (lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	
		
	@GetMapping
	public String selectAttendance(
		@PathVariable("lectNo") String lectNo
		, Authentication id
		, Model model) {
	    // 출결기록 정보
	    List<AttendanceVO> selectAttendanceList = attendanService.selectAttendanceList();
	    
	    // 차수별강의자료 정보
	    List<LectureWeekVO> selectOrderLectureDataList = lectureService.selectOrderLectureDataList(lectNo, id.getName());
	    
		// lectOrder 기준으로 정렬
//	    selectAttendanceList.sort(Comparator.comparing(AttendanceVO::getLectOrder));
	    
	    model.addAttribute("selectAttendanceList", selectAttendanceList);
	    model.addAttribute("selectOrderLectureDataList", selectOrderLectureDataList);
	    model.addAttribute("lectNo", lectNo);
	    
	    
	    return "lecture/materials/attendance/attendanceList";
	}
	
	// 주차 코드와 강의차수에 맞는 출결 데이터를 가져오는 서비스 메서드 호출
	@GetMapping("/data")
	@ResponseBody
	public List<AttendanceVO> getAttendance(
	        @PathVariable("lectNo") String lectNo,
	        @RequestParam("weekCd") String weekCd,
	        @RequestParam("lectOrder") String lectOrder) {
//	    return attendanService.findAttendanceByWeekAndOrder(weekCd, lectOrder);
		
		// 주차 코드와 강의 차수에 맞는 출결 데이터를 조회
	    List<AttendanceVO> attendanceList = attendanService.findAttendanceByWeekAndOrder(weekCd, lectOrder);

	    // 주차 코드와 강의 차수에 해당하는 출결 데이터만 반환
	    return attendanceList;
	}
	
	
	
	
	// 출결기록 상세조회
	@GetMapping("{lectOrder}")
	public List<AttendanceVO> selectAttendanceDetail(AttendanceVO attendanceVO) {
		return attendanService.selectAttendance(attendanceVO);
	}
	
	// 공결 접수 신청서 등록
	@PostMapping()
	public String create( @ModelAttribute(MODELNAME) AbsenceCertificateReceiptVO absenceboard
			, BindingResult errors
			, RedirectAttributes redirectAttributes
			) {
			
		try {
			String lvn = null;
			if (!errors.hasErrors()) {
				absenceService.insertAbsenceCertificateReceipt(absenceboard);
				lvn = "redirect:/lecture/{lectNo}/attendan" + absenceboard.getAbsenceCd();		
			} else {
				redirectAttributes.addFlashAttribute(MODELNAME, absenceboard);
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + MODELNAME, errors);
				lvn = "redirect:/lecture/{lectNo}/attendan/new";
			}
			return lvn;
		} catch (Throwable e) {
			throw new BoardException(e);
		}
	}
	
	// 출결기록 수정
	@PutMapping("/edit")
	public ResponseEntity<Object> updateAttendance(
		@PathVariable("lectNo") String lectNo, // URL 경로에서 lectNo를 받음
		@RequestBody List<AttendanceVO> attendanceVOList) {
		
	    for (AttendanceVO attendanceVO : attendanceVOList) {
	        boolean isUpdated = attendanService.updateAttendance(attendanceVO);
	        
	        if (!isUpdated) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                 .body("컨트롤러 출석 정보 업데이트 실패~~~");
	        }
	    }
	    return ResponseEntity.ok().body("출석 정보 업데이트 성공!!!");
	}
	
	// 출결기록 삭제
	@DeleteMapping("{stuId}")
	public String deleteAttendance(@PathVariable()String stuId) {
		return "attendance/attendanceList";
	}
	
	
	
	// 모듈 추가하기
	
//	@GetMapping("trafic/module")
//	public String getTraficData2() {
//		
//		return "moduleUI/adminTraficModule";
//	}
	


//	@GetMapping("trafic/module/data")
//	@ResponseBody
//	public ResponseEntity<LogDTO> getTraficData3() {
//		
//		LocalDateTime dateTime = LocalDateTime.now();
//		// 포맷 정의 (HHmm 형식)
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		// 시간 부분만 포맷하여 문자열로 변환
//		String logDate = dateTime.format(formatter);
//		
//		List<LogVO> logList = service.getTraficLogList(logDate);
//		List<LogVO> methodLogList = service.getTraficMethodLogList(logDate);
//		
//		LogDTO dto = new LogDTO();
//		
//		dto.setTraficLogList(logList);
//		dto.setTraficMethodLogList(methodLogList);
//		
//		return ResponseEntity.ok(dto);
//	}
	
}
