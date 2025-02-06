package kr.or.ddit.yguniv.graduationpaper.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.graduationpaper.service.GraduationPaperService;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.GraduationPaperVO;

@Controller
@RequestMapping("/graduationpaper")
public class GraduationPaperController {
	
	public static final String MODELNAME = "graudationPaper";
	
	@Autowired
	private GraduationPaperService service;
	
	@ModelAttribute(MODELNAME)
	public GraduationPaperVO graduationpaper() {
		return new GraduationPaperVO();
	}
	
	// 논문 제출 (제출자 정보 확인 form)으로 보냄
	@GetMapping("new")
	public String createForm(Authentication auth, Model model) {
		model.addAttribute("auth", auth);
		return "graduationPaper/graduationPaperForm";
	}
	
	// 논문 제출 form으로 보냄
	@GetMapping("new1")
	public String createFormReal(Authentication auth, Model model) {
		model.addAttribute("auth", auth);
		return "graduationPaper/graduationPaperForm1";
	}
	
	//논문 메인
	@GetMapping
	public String main(Principal principal, Model model) {
		model.addAttribute("principal", principal.getName());
		return "graduationPaper/graduationPaperMain";
	}

	// 학생 자기 논문 전체조회
	@GetMapping("list/{stuId}")
	public String selectlist(@PathVariable String stuId
			, Model model
			) {
		List<GraduationPaperVO>list = service.getGraduationPapers(stuId);
		model.addAttribute("list", list);
		return "graduationPaper/graduationPaperList";
	}
	
	// 교수 학생 논문 조회
	@GetMapping("list/prof")
    public String listProfessorStudentsPapers(Authentication authentication, Model model) {
        String profId = authentication.getName(); // 로그인한 교수의 ID 가져오기
        List<GraduationPaperVO> papers = service.getProfessorStudentsPapers(profId);
        model.addAttribute("papers", papers);
        return "graduationPaper/graduationPaperListByProf"; 
    }

	// 논문 제출 완료
	@PostMapping("submit")
	public String submitPaper(GraduationPaperVO paper, RedirectAttributes redirectAttributes) {
	        // 논문 데이터 저장
	        service.insertGraduationPaper(paper);
	        GraduationPaperVO savedPaper = service.getPaperById(paper.getGpaCd());
	        // 저장된 데이터를 "제출 완료" 페이지로 전달
	        redirectAttributes.addFlashAttribute("paper", savedPaper);
	        return "redirect:/graduationpaper/complete";
	  
	}
	// 논문 다운로드
	@GetMapping("atch/{atchFileId}/{fileSn}")
	public ResponseEntity<Resource> serveFile(
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
	
	//논문 마지막 페이지
	@GetMapping("complete")
	public String completePage() {
	    return "graduationPaper/graduationPaperComplete";
	}

	// 논문 상세조회
	@GetMapping("{gpaCd}")
	public String select(@PathVariable() int gpaCd, Model model) {
		model.addAttribute("paper", service.getPaperById(gpaCd));
		return "graduationPaper/graduationPaperDetail";
	}

	// 수정
	@PutMapping("{gpaCd}")
	public String update(@PathVariable() String gpaCd) {
		return "graduationPaper/graduationPaperDetail";
	}

	// 삭제
	@DeleteMapping("{gpaCd}")
	public String delete(@PathVariable() String gpaCd) {
		return "graduationPaper/graduationPaperList";
	}
	
	@PutMapping("{gpaCd}/update")
	public ResponseEntity<String> updateGraduationPaper(@PathVariable("gpaCd") int gpaCd) {
	    try {
	        // 서비스 호출로 논문 상태 업데이트
	        service.updategraduationPaperToAccess(gpaCd);
	        return ResponseEntity.ok("논문 상태가 업데이트되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("업데이트 중 오류 발생");
	    }
	}

}

