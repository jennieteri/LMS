package kr.or.ddit.yguniv.employmentfield.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/employmentFiled")
public class EmploymentFieldController {
	// form으로 보냄
		@GetMapping("new")
		public String createForm() {
			return "employmentFiled/employmentFiledForm";
		}

		// 전체조회
		@GetMapping()
		public String selectlist() {

			return "employmentFiled/employmentFiledList";
		}

		// 생성
		@PostMapping()
		public String create() {
			return "employmentFiled/employmentFiledDetail";
		}

		// 상세조회
		@GetMapping("{empfiCd}")
		public String select(@PathVariable() String empfiCd) {

			return "employmentFiled/employmentFiledDetail";
		}

		// 수정
		@PutMapping("{empfiCd}")
		public String update(@PathVariable() String empfiCd) {
			return "employmentFiled/employmentFiledDetail";
		}

		// 삭제
		@DeleteMapping("{empfiCd}")
		public String delete(@PathVariable() String empfiCd) {
			return "employmentFiled/employmentFiledList";
		}
}
