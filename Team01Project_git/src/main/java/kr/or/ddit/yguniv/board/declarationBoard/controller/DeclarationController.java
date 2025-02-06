package kr.or.ddit.yguniv.board.declarationBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/yguniv/declaration")
public class DeclarationController {
	
	//신고 게시물 목록 조회 
	//사용자가 신고한 게시판 게시글을 조회한다. 
	// 신고 게시판은 모든 게시판에 있는 신고된 게시글을 볼 수 있다. 
	
	@GetMapping("{dclrNo}")
	public String selectDeclarationList() {
		return "declaration/declarationList";
	}
	
	/*
	 * //신고 게시물 삭제 // 게시물 번호에 있는 사용자가 작성한 것을 삭제
	 * 
	 * @DeleteMapping("{dclrNo}/{prsId}") public String deletedeclaration() { return
	 * "declaration/declarationList"; }
	 */

}
