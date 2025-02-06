package kr.or.ddit.yguniv.certificate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/certificate")
public class CertificateController {
	// form으로 보냄
	@GetMapping("new")
	public String createForm() {
		return "certificate/certificateForm";
	}

	//자격증 전체 조회
	@GetMapping()
	public String selectlist() {

		return "certificate/certificateList";
	}

	// 게시글 생성
	@PostMapping()
	public String create() {
		return "certificate/certificateDetail";
	}

	// 게시글상세조회
	@GetMapping("{stuId}")
	public String select(@PathVariable() String stuId) {

		return "certificate/certificateDetail";
	}

	// 게시글수정
	@PutMapping("{stuId}")
	public String update(@PathVariable() String stuId) {
		return "certificate/certificateDetail";
	}

	// 게시글삭제
	@DeleteMapping("{stuId}")
	public String delete(@PathVariable() String stuId) {
		return "certificate/certificateList";
	}
}
