package kr.or.ddit.yguniv.introduce.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.introduce.service.IntroduceService;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.CertificateVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/introduce")
public class IntroduceController {
	
	public static final String MODELNAME = "introduce";
	
	@Autowired
	private IntroduceService service;
	
	@ModelAttribute(MODELNAME)
	public IntroduceVO introduce() {
		return new IntroduceVO();
	}
	
	
		// 새로운 자기소개서 form으로 보냄
		@GetMapping("new")
		public String createForm(Model model, Principal principal) {
			model.addAttribute("stuId", principal.getName());
			return "introduce/introduceForm";
		}

		// 교직원 학생 자기소개서 리스트 전체조회
		@GetMapping
		public String selectlist(
				@RequestParam(required = false, defaultValue = "1") int page
				, @ModelAttribute("condition") SimpleCondition simpleCondition
				, Model model
				) {
			PaginationInfo<IntroduceVO>paging = new PaginationInfo<>();
			paging.setCurrentPage(page);
			paging.setSimpleCondition(simpleCondition);
			model.addAttribute("list", service.selectintroduceListPaging(paging));
			PaginationRenderer renderer = new BootStrapPaginationRenderer();
			model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
			return "introduce/introduceList";
		}
		
		
		@PostMapping("create/introduce")
		public ResponseEntity<?> saveIntroduce(@ModelAttribute IntroduceVO introduce) {
		    service.insertIntroduce(introduce);
		    return ResponseEntity.ok(introduce.getIntCd()); // PK 반환
		}

		
		@PostMapping("create/certificate")
		public ResponseEntity<?> saveCertificate(
		        @RequestParam("intCd") int intCd,
		        @RequestParam("certNm") String certNm,
		        @RequestParam("certDate") String certDate,
		        @RequestParam(value = "uploadFiles", required = false) MultipartFile uploadFile) {
		    CertificateVO certificate = new CertificateVO();
		    certificate.setIntCd(intCd);
		    certificate.setCertNm(certNm);
		    certificate.setCertDate(certDate);

		    if (uploadFile != null && !uploadFile.isEmpty()) {
		        AtchFileVO atchFile = new AtchFileVO();
		        atchFile.setFileDetails(Collections.singletonList(new AtchFileDetailVO(uploadFile)));
		        certificate.setAtchFile(atchFile);
		    }

		    service.insertCertificate(certificate);
		    return ResponseEntity.ok("자격증 저장 완료");
		}

		
		// 자기 자신 자소서 리스트 조회
		@GetMapping("list/{stuId}")
		public String viewIntroduceList(
				Principal principal
				,@RequestParam(required = false, defaultValue = "1") int page
				, @PathVariable String stuId
				, @ModelAttribute("condition") SimpleCondition simpleCondition
				,Model model
				) {
		    String userId = principal.getName(); 
		    PaginationInfo<IntroduceVO>paging = new PaginationInfo<>();
		    paging.setCurrentPage(page);
		    paging.setSimpleCondition(simpleCondition);
		    
		    List<IntroduceVO> introduceList = service.selectIntroduceByUserId(userId,paging);
		    
		    
		    model.addAttribute("principal", principal);
		    model.addAttribute("myintroduce", introduceList);
		    PaginationRenderer renderer = new BootStrapPaginationRenderer();
		    model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		    return "introduce/myIntroduceList";
		}

		
		//학생 자신 자소서 상세보기
		@GetMapping("mydetail/{intCd}")
		public String viewIntroduceDetail(@PathVariable("intCd") int intCd, Model model) {
		    IntroduceVO introduce = service.selectMyIntroduce(intCd);
		    model.addAttribute("introduce", introduce);
		    return "introduce/introduceDetail";
		}

		
		
		
		// 교직원 자소서 상세보기 
	    @GetMapping("detail/{intCd}")
	    public String viewIntroduce(@PathVariable("intCd") int intCd,
	                                @RequestParam(required = false, defaultValue = "false") boolean edited,
	                                Model model) {
	    	
	    	IntroduceVO introduce = service.selectintroduce(intCd);

	       
	        model.addAttribute("introduce", introduce);

	        return "introduce/introduceDetail";
	    }
		
	    
	    @PostMapping("/edit/{intCd}")
	    public ResponseEntity<IntroduceVO> updateIntroduce(
	        @PathVariable int intCd,
	        @RequestBody IntroduceVO introduce) {
	        introduce.setIntCd(intCd); 
	        IntroduceVO updatedIntroduce = service.updateintroduce(introduce);
	        if (updatedIntroduce != null) {
	            return ResponseEntity.ok(updatedIntroduce); // 수정 성공 시 수정된 데이터 반환
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 수정 실패 시 오류 반환
	        }
	    }
	    
	    
		@GetMapping("{intCd}/atch/{atchFileId}/{fileSn}")
		public ResponseEntity<Resource> serveFile(
		        @PathVariable("intCd") int intCd,
		        @PathVariable("atchFileId") int atchFileId,
		        @PathVariable("fileSn") int fileSn) {
		    // 1. 파일 정보 조회
		    AtchFileDetailVO fileDetail = service.download(atchFileId, fileSn);
	
		    Resource resource = fileDetail.getSavedFile();
		    if (resource == null || !resource.exists()) {
		        throw new RuntimeException("파일을 찾을 수 없습니다.");
		    }
	
		    String contentType = fileDetail.getFileMime();
		    if (contentType == null) {
		        contentType = "application/octet-stream";
		    }
	
		    // 4. 파일 반환
		    return ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_TYPE, contentType)
		            .body(resource);
		}

	    
	    
	    
	    
//		// 교직원 학생 자소서 수정 폼
//		//학생은 한번 제출한 자소서는 수정이 불가하오니 신중하게 적어주시길 바랍니다. 메세지 출력하기
//	    @PostMapping("edit")
//	    public String updateIntroduce(@ModelAttribute IntroduceVO introduce, RedirectAttributes redirectAttributes, Model model) {
//	        // 기존 데이터 가져오기
//	        IntroduceVO originalData = service.selectintroduce(introduce.getStuId());
//
//	        // 자소서 수정 처리
//	        IntroduceVO updatedIntroduce = service.updateintroduce(introduce);
//
//	        if (updatedIntroduce != null) { // 수정 성공 여부 판단
//	            redirectAttributes.addFlashAttribute("message", "자소서가 성공적으로 수정되었습니다.");
//	            model.addAttribute("introduce", originalData); // 기존 데이터
//	            model.addAttribute("editedIntroduce", updatedIntroduce); // 수정된 데이터
//	        } else {
//	            redirectAttributes.addFlashAttribute("message", "자소서 수정에 실패하였습니다.");
//	            return "redirect:/introduce/detail/" + introduce.g``etStuId(); // 실패 시 상세 페이지로 리다이렉트
//	        }
//
//	        return "introduce/introduceDetail"; // 수정 성공 시 수정 전/후 데이터를 포함한 페이지로 이동
//	    }


	    
	    //교직원 학생 자소서 수정 폼
	    @GetMapping("edit/{intCd}")
	    public String editIntroduceForm(@PathVariable("intCd") int intCd, Model model) {
	        // 기존 데이터 가져오기
	        IntroduceVO introduce = service.selectintroduce(intCd);
	        if (introduce == null) {
	            // 데이터가 없으면 에러 페이지로 리다이렉트
	            return "redirect:/introduce?error=notFound";
	        }

	        // 기존 데이터를 모델에 추가
	        model.addAttribute("introduce", introduce);

	        // 첨삭 폼 뷰로 이동
	        return "introduce/introduceEditForm";
	    }
	    
	    
	


	    
	    
}



		
//
//		// 삭제
//		@DeleteMapping("{stuId}")
//		public String delete(@PathVariable() String stuId) {
//			return "introduce/introduceList";
//		}

