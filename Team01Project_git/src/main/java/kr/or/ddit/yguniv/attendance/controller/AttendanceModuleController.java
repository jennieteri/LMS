package kr.or.ddit.yguniv.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.absencecertificatereceipt.service.AbsenceCertificateReceiptServiceImpl;
import kr.or.ddit.yguniv.attendance.dao.AttendanceMapper;
import kr.or.ddit.yguniv.attendance.service.AttendanceServiceImpl;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import kr.or.ddit.yguniv.vo.LectureVO;

@Controller
@RequestMapping()
public class AttendanceModuleController {
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
	
	
	@GetMapping("/aten/module")
	public String getTraficData2(
		Model model
	) {
		List<AttendanceVO> selectLectureList = attendanService.selectLectureList();
		
		model.addAttribute("selectLectureList", selectLectureList);
		
		return "/attendance/attendance";
	}
	
	
	@GetMapping("/aten/list")
	@ResponseBody
	public ResponseEntity<Object> selectListCount(
	    @RequestParam String lectNo, 
	    Authentication id) {
		boolean isStudent = id.getAuthorities().stream()
	            .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));
		
		List<AttendanceVO> selectLectureList;
	    if (isStudent) {
	        // 학생이면 학생 ID를 전달하여 쿼리 실행
	        selectLectureList = attendanService.selectListCount(lectNo, id.getName());
	    } else {
	        // 학생이 아니면 학생 ID 없이 쿼리 실행
	        selectLectureList = attendanService.selectListnonstuCount(lectNo);
	    }
		
	    //List<AttendanceVO> selectLectureList = attendanService.selectListCount(lectNo, id.getName());
	    return ResponseEntity.ok(selectLectureList);
	}

}
