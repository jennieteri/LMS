package kr.or.ddit.yguniv.studentCard.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.studentCard.service.StudentCardService;
import kr.or.ddit.yguniv.vo.StudentCardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("studentCard")
public class StudentCardController {
	
	public static final String MODELNAME = "card";
	
	@Autowired
	private StudentCardService service;
	

	
	@ModelAttribute(MODELNAME)
	public StudentCardVO studentCard() {
		return new StudentCardVO();
	}
	
//	// 교직원이 보는 학생 신청 상세 조회 
//	@GetMapping("{stuId}")
//	public String selectStudentDetail(
//			@PathVariable String stuId, 
//			Model model) {
//	    StudentCardVO studentCard = service.selectStudentCardDetail(stuId);
//	    log.info("학생 상세 정보: {}", studentCard);
//	    if (studentCard.getCommonCodeVO() != null) {
//	        log.info("발급 상태: {}", studentCard.getCommonCodeVO().getCocoStts());
//	    } else {
//	        log.warn("commonCodeVO가 null입니다.");
//	    }
//	    model.addAttribute("card", studentCard);
//	    return "studentCard/studentCardDetail";
//	}

	
	//학생이 신청하는 학생증 발급 신청
	//신청 폼
	@GetMapping("createStudentCard/new")
	public String createStudentCard(
			Model model 
			,@ModelAttribute(MODELNAME) StudentCardVO card
			) {
		model.addAttribute(MODELNAME,card);
		
		return "studentCard/studentCardForm";
	}
	
	@PostMapping("/apply")
	@ResponseBody
	public ResponseEntity<String> createStudentCard(@RequestBody StudentCardVO card) {
	    try {
	        service.createStudentCard(card);
	        return ResponseEntity.ok("학생증 신청이 완료되었습니다.");
	        
	    }catch(IllegalArgumentException e) {
	    	log.error("학생증 신청 실패 : {}", e.getMessage(), e);
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // BAD REQUEST로 반환
	    } catch (Exception e) {
	        log.error("학생증 신청 실패: {}", e.getMessage(), e); // 에러 로그
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 실패");
	    }
	}



	
	
	
//신청은 마이페이지에서 할겅미 
	
	//교직원이 보는 학생증 신청 목록 조회 LIST
	@GetMapping
	public String selectStudentList(
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute("condition") SimpleCondition simpleCondition, Model model

			) {
		
		PaginationInfo<StudentCardVO> paging = new PaginationInfo<StudentCardVO>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		model.addAttribute("card", service.selectStudentCardList(paging));
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "studentCard/studentCardList";
	};
	
	@PostMapping("/updateStatus")
    public ResponseEntity<Map<String, String>> updateStatus(@RequestParam String cocoCd, @RequestParam String stuId) {
        String updatedStatus = service.updateStatus(cocoCd, stuId);

        Map<String, String> response = new HashMap<>();
        if (updatedStatus != null) {
            response.put("status", updatedStatus.trim());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "상태 업데이트 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

	
	//학생증 신청 목록 삭제 
	

	
	
}
