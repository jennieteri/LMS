package kr.or.ddit.yguniv.graduation.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Update;
import org.checkerframework.checker.units.qual.mol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.graduation.service.GraduationService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.GraduationVO;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
@RequestMapping("/graduation")
public class GraduationController {
	
	public static final String MODELNAME="graduation";
	
	@Autowired
	private GraduationService service;
	
	
	@ModelAttribute(MODELNAME)
	public GraduationVO graduation() {
		return new GraduationVO();
	}
	
	
	// form으로 보냄
	@GetMapping("new")
	public String createForm() {
		return "graduation/graduationForm";
	}
	
	//졸업인증제 승인
	@PostMapping("{gdtCd}/edit/access")
	public String updateToAccess(@ModelAttribute(MODELNAME) GraduationVO graduation
			,BindingResult errors
			, RedirectAttributes redirectAttributes
			) {
		service.updategraduationToAccess(graduation);
		return "graduation/graduationForm";
	}
	

	// 삭제
	@DeleteMapping("delete/{gdtCd}")
	public String delete(@PathVariable() int gdtCd) {
		service.deletegraduation(gdtCd);
		return "graduation/graduationList";
	}
	
	
	@PostMapping("update/{gdtCd}")
	@ResponseBody
	public ResponseEntity<?> updateGraduation(
	        @PathVariable int gdtCd,
	        @Validated(UpdateGroup.class) GraduationVO graduation,
	        BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        // 유효성 검증 실패 시 오류 메시지 반환
	        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
	    }
	    try {
	        graduation.setGdtCd(gdtCd);
	        service.updategraduation(graduation);
	        return ResponseEntity.ok("수정 성공");
	    } catch (IllegalStateException e) {
	        // 승인된 항목 수정 시도 시의 예외 처리
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        // 기타 예외 처리
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("수정 중 오류가 발생했습니다. 다시 시도해주세요.");
	    }
	}


	//졸업인증제 반려
	@PostMapping("{gdtCd}/edit/reject")
	public String updateToReject(@ModelAttribute(MODELNAME) GraduationVO graduation
			,BindingResult errors
			, RedirectAttributes redirectAttributes
			) {
		service.updategraduationToReject(graduation);
		return "graduation/graduationForm";
	}
	
	//교직원 학생 졸업인증제 상세보기
	@GetMapping("detail/{gdtCd}")
	@ResponseBody
	public GraduationVO viewGraduationDetail(@PathVariable("gdtCd") int gdtCd, Model model,Principal prin) {
		GraduationVO graduation = service.selectgraduationByCd(gdtCd);
		 if (graduation == null) {
		        throw new PKNotFoundException("졸업인증제 정보를 찾을 수 없습니다.");
		    }

		    return graduation; 
	}
	
	// 교직원 학생 졸업 인증제 전체조회 
	@GetMapping("listbyemp")
	public String graduationListByEmp(
			Model model, Principal principal
			,@RequestParam(required = false, defaultValue = "1") int page
			,@ModelAttribute("condition") SimpleCondition simpleCondition
			) {
		PaginationInfo<GraduationVO>paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		List<GraduationVO>list = service.selectgraduationListByEmp(paging);
		model.addAttribute("list", list);
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnpaging"));
		return "graduation/graduationListEmp";
	}

	
	// 전체조회
	@GetMapping()
	public String main(Model model, Principal principal) {
		model.addAttribute("principal", principal.getName());
		return "graduation/graduationMain";
	}
	
	// 영어 생성 폼으로 보냄
	@GetMapping("neweng/{stuId}")
	public String createEng(@PathVariable("stuId") String stuId) {
		return "graduation/graduationForm";
	}
	
	// 영어 생성 폼으로 보냄
	@GetMapping("newvol/{stuId}")
	public String createVol(@PathVariable("stuId") String stuId) {
		return "graduation/graduationForm1";
	}

	// 상세조회
	@GetMapping("list/{stuId}")
	public String graduationListById(@PathVariable("stuId") String stuId, Model model, Principal prin) {
		List<GraduationVO>list = service.selectgraduationList(stuId);
		model.addAttribute("list",list);
		model.addAttribute("principal", prin.getName());
		int totalScore=service.selectTotalVolunteerScore(stuId);
		model.addAttribute("totalScore", totalScore);
		return "graduation/graduationList";
	}
	
	// 상세조회
	@GetMapping("listmodual")
	public String graduationListByIdModual(Model model, Principal prin) {
		String stuId = prin.getName();
		List<GraduationVO>list = service.selectgraduationList(stuId);
		model.addAttribute("list",list);
		model.addAttribute("principal", prin.getName());
		int totalScore=service.selectTotalVolunteerScore(stuId);
		model.addAttribute("totalScore", totalScore);
		return "/graduation/graduationMainModual";
	}
	
	
	//영어 점수 등록
	@PostMapping("create/eng")
	@ResponseBody
	public Map<String, Object> createEng(@ModelAttribute GraduationVO graduation, BindingResult errors) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        if (!errors.hasErrors()) {
	            service.insertgraduation(graduation);
	            result.put("success", true);
	            result.put("message", "영어 점수가 성공적으로 등록되었습니다.");
	        } else {
	            result.put("success", false);
	            result.put("message", "입력값이 유효하지 않습니다.");
	        }
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "오류가 발생했습니다: " + e.getMessage());
	    }
	    return result;
	}
	
	
	//봉사 점수 등록
		@PostMapping("create/vol")
		@ResponseBody
		public Map<String, Object> createVol(@ModelAttribute GraduationVO graduation, BindingResult errors) {
		    Map<String, Object> result = new HashMap<>();
		    try {
		        if (!errors.hasErrors()) {
		            service.insertgraduation(graduation);
		            result.put("success", true);
		            result.put("message", "봉사 점수가 성공적으로 등록되었습니다.");
		        } else {
		            result.put("success", false);
		            result.put("message", "입력값이 유효하지 않습니다.");
		        }
		    } catch (Exception e) {
		        result.put("success", false);
		        result.put("message", "오류가 발생했습니다: " + e.getMessage());
		    }
		    return result;
		}

	@GetMapping("{gdtCd}/atch/{atchFileId}/{fileSn}")
	public ResponseEntity<Resource> serveFile(
	        @PathVariable("gdtCd") int gdtCd,
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
	            .header(HttpHeaders.CONTENT_DISPOSITION, 
	                    "attachment; filename=\"" + fileDetail.getOrignlFileNm() + "\"")
	            .body(resource);
	}
	
	
}

