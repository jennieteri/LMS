package kr.or.ddit.yguniv.absencecertificatereceipt.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

import kr.or.ddit.yguniv.absencecertificatereceipt.service.AbsenceCertificateReceiptServiceImpl;
import kr.or.ddit.yguniv.attendance.service.AttendanceServiceImpl;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/lecture/{lectNo}/absence")
public class AbsenceCertificateReceiptConstroller {
	
	@Autowired // 공결인정서
	private AbsenceCertificateReceiptServiceImpl absenceService;
	@Autowired // 출결조회
	private AttendanceServiceImpl attendanceService;
	@Autowired // 강의정보
	private LectureMaterialsServiceImpl lectureService;
	
	@ModelAttribute
	public void addLecture(@PathVariable String lectNo, Model model) {
		LectureVO lectureVO = lectureService.selectLecture(lectNo);
		if (lectureVO == null) {
			throw new PKNotFoundException("해당 강의는 존재하지 않습니다", true);
		}
		model.addAttribute("lecture", lectureVO);
	}
	
	// 공결 인증서 접수 전체조회
	@GetMapping
	public String selectAbsenceCertificateReceipt(	
		@PathVariable("lectNo") String lectNo
		, @RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Authentication id
		, Model model
	) {
		AbsenceCertificateReceiptVO absenceVO = new AbsenceCertificateReceiptVO();
		
		Collection<? extends GrantedAuthority> role = id.getAuthorities();
		if(role.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
			absenceVO.setStuId(id.getName());
		}
		absenceVO.setLectNo(lectNo);

		PaginationInfo<AbsenceCertificateReceiptVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		
		// 서비스 호출
		// List<AbsenceCertificateReceiptVO> selectAbsenceCertificateReceiptList = absenceService.selectAbsenceCertificateReceiptList(absenceVO);
		List<AbsenceCertificateReceiptVO> selectAbsenceCertificateReceiptList = absenceService.selectAbsenceCertificateReceiptList(absenceVO, paging);
		model.addAttribute("selectAbsenceCertificateReceiptList", selectAbsenceCertificateReceiptList);
		
		
		// 서비스 받은거로 페이지네이션 처리
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		// 
		List<AttendanceVO> selectAttendanceList = attendanceService.selectAttendanceList();
		model.addAttribute("selectAttendanceList", selectAttendanceList);
		
		return "lecture/materials/absenceCertificateReceipt/absenceCertificateReceiptList";
	}	
	
	
	//공결 인증서 접수 상세조회 - 여기서 처리안하고 '전체조회' 에서 모달창으로 처리
	@GetMapping("{absenceCd}")
	@ResponseBody // 뷰리졸브를 사용하지않음으로써 성능이 좋음
	public AbsenceCertificateReceiptVO selectAbsenceCertificateReceiptDetail(@PathVariable String absenceCd, Model model) {
		
		return absenceService.selectAbsenceCertificateReceipt(absenceCd);
		//return "lecture/materials/absenceCertificateReceipt/absenceCertificateReceiptDetail";
	}
	
	// 공결인증서 와 출결 정보 수정
	@PutMapping("/edit")
	public ResponseEntity<Object> updateAbsenceCertificateReceipt(
	    @PathVariable("lectNo") String lectNo
	    , @RequestBody AbsenceCertificateReceiptVO absenceVO) {

		
		// 승인 여부에 따라 처리 로직 분기
	    boolean isUpdated;
	    
	    if (absenceVO.getAttendanceVO() != null) { // 승인된 경우
	        // 공결인정서 상태와 출결 정보를 동시에 수정
	    	isUpdated = absenceService.updateAbsenceAndAttendance(absenceVO);
	    } else { // 반려된 경우
	        // 공결인정서 상태만 수정
	        isUpdated = absenceService.updateAbsenceCertificateReceipt(absenceVO);
	    }
	    
	    // 처리 결과에 따라 응답 반환
	    if (!isUpdated) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("공결 인증서 접수 및 출결 정보 수정 실패~~~");
	    }

	    // 수정이 성공한 경우
	    return ResponseEntity.ok().body("공결 인증서 접수 및 출결 정보 수정 성공!!!");
	}

	// 공결 신청서 수정(학생)
	@PostMapping("/edit/reason")
	public ResponseEntity<Object> updateAbsenceReason(
		@PathVariable String lectNo
		, @Validated(UpdateGroup.class) AbsenceCertificateReceiptVO absenceVO
		, BindingResult error
	){
		absenceService.updateAbsence(absenceVO);
		
		return ResponseEntity.ok().body("공결 인증서 접수 및 출결 정보 수정 성공!!!");
	}
	
	
	// 공결 신청서 삭제(학생) - 실제로 삭제처리하는게 아니고 상태만 '삭제' 처리로 바꾸고 안보이게함
	@PutMapping("/drop")
	public ResponseEntity<String> deleteAbsence(
		@RequestBody Map<String, String> requestData){
		String absenceCd = requestData.get("absenceCd");
		if (absenceCd == null || absenceCd.isEmpty()) {
	        return ResponseEntity.badRequest().body("absenceCd가 비어있습니다.");
	    }

	    // absenceCd로 삭제 처리
	    absenceService.deleteAbsenceCertificateReceipt(absenceCd);
		
		return ResponseEntity.ok().body("공결 인증서 접수 및 출결 정보 삭제 성공!!!");
	}
	
}
