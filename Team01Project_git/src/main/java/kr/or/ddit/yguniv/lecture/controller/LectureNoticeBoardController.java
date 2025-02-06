package kr.or.ddit.yguniv.lecture.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.service.LectureMaterialsServiceImpl;
import kr.or.ddit.yguniv.lecture.service.LectureNoticeBoardServiceImpl;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.paging.renderer.BootStrapPaginationRenderer;
import kr.or.ddit.yguniv.paging.renderer.PaginationRenderer;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.LectureNoticeBoardVO;
import kr.or.ddit.yguniv.vo.LectureVO;

@Controller
@RequestMapping("/lecture/{lectNo}/board")
public class LectureNoticeBoardController {
	@Autowired // 강의공지게시판
	private LectureNoticeBoardServiceImpl lectBoardService;
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
	
	// 강의 공지 게시판 전체조회
	@GetMapping
	public String selectLectureBoardList(
		@PathVariable("lectNo") String lectNo
		, @Param("cnbNo") String cnbNo
		, @RequestParam(required = false, defaultValue = "1") int page
		, @ModelAttribute("condition") SimpleCondition simpleCondition
		, Model model) {
		LectureNoticeBoardVO lectNoticeVO = new LectureNoticeBoardVO();
		
		lectNoticeVO.setLectNo(lectNo);
		lectNoticeVO.setCnbNo(cnbNo);
		
		PaginationInfo<LectureNoticeBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(page);
		paging.setSimpleCondition(simpleCondition);
		
		List<LectureNoticeBoardVO> selectNoticeBoardList = lectBoardService.selectLectureNoticeBoardList(lectNoticeVO, paging);
		List<LectureNoticeBoardVO> selectLectureNoticeBoardMainList = lectBoardService.selectLectureNoticeBoardMainList(lectNoticeVO);
		
		model.addAttribute("selectNoticeBoardList", selectNoticeBoardList);
		model.addAttribute("selectLectureNoticeBoardMainList", selectLectureNoticeBoardMainList);
		
		PaginationRenderer renderer = new BootStrapPaginationRenderer();
		model.addAttribute("pagingHTML", renderer.renderPagination(paging, "fnPaging"));
		
		return "lecture/materials/lecture/lectureNoticeBoard";
	}
	
	// 강의 공지 게시판 상세조회
	@GetMapping("/detail/{cnbNo}")
	public String selectLectureBoardDetail(
			@PathVariable("cnbNo") String cnbNo
			, @PathVariable("lectNo") String lectNo
			, Model model) {
		lectBoardService.updateCnbInq(cnbNo);
		LectureNoticeBoardVO lectNoticeVO = new LectureNoticeBoardVO();
		lectNoticeVO.setCnbNo(cnbNo);
		LectureNoticeBoardVO selectLectureNoticeBoard = lectBoardService.selectLectureNoticeBoard(cnbNo);
		model.addAttribute("selectLectureNoticeBoard", selectLectureNoticeBoard);
		
		int mainBoardCount = lectBoardService.mainBoardCount(lectNo);
		model.addAttribute("mainBoardCount", mainBoardCount);
		
		return "lecture/materials/lecture/lectureNoticeBoardDetail";
	}
	
	// 강의 공지 상세조회 데이터
	@GetMapping("/boardDetail/{cnbNo}")
	@ResponseBody
	public LectureNoticeBoardVO selectBoardDetail(@PathVariable("cnbNo") String cnbNo, Model model) {
		return lectBoardService.selectLectureNoticeBoard(cnbNo);
	}
	
	// 강의 공지 등록 (비동기 처리 방식, 기존에 모달창에서 '등록' 했던 형식)
	@PostMapping("/insert")
	public ResponseEntity<Object> insertLectureBoard(
		@PathVariable String lectNo	
		, @Validated(InsertGroup.class) LectureNoticeBoardVO lectureNoticeBoardVO
		, BindingResult error
	) {
		try {
			lectureNoticeBoardVO.setLectNo(lectNo);
			lectBoardService.insertLectureNoticeBoard(lectureNoticeBoardVO);
			return ResponseEntity.status(HttpStatus.OK)
					.body("강의 공지 등록 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("강의 공지 등록 실패 : " + e.getMessage());
		}
	}
	
	// 강의 공지 등록 폼 (등록 페이지 이동)
	@PostMapping("/new")
	public String insertLectureBoard(
			@PathVariable String lectNo
			, Model model
			, Authentication id
	) {
		LectureNoticeBoardVO lectureNoticeBoardVO = new LectureNoticeBoardVO();
		lectureNoticeBoardVO.setLectNo(lectNo);
		
		int mainBoardCount = lectBoardService.mainBoardCount(lectNo);
		model.addAttribute("mainBoardCount", mainBoardCount);
		
		return "lecture/materials/lecture/lectureNoticeBoardForm";
	}
	
	
	// 강의 공지 게시판 수정 (상세 페이지에서 모달창으로 띄워줄때)
	@PostMapping("edit/{cnbNo}")
	public ResponseEntity<Object> updateLectureNoticeBoard(
		@PathVariable("cnbNo") String cnbNo
		, @Validated(UpdateGroup.class) LectureNoticeBoardVO lectureNoticeBoardVO
		, BindingResult error
	) {
		lectBoardService.updateLectureNoticeBoard(lectureNoticeBoardVO);
		
		return ResponseEntity.ok().body("강의 공지 게시판 수정 성공!!!");
	}
	
	// 강의 공지 게시판 수정 페이지로 이동
	
	@GetMapping("editform/{cnbNo}")
	public String editLectureBoard(
		@PathVariable("lectNo") String lectNo
		, @PathVariable("cnbNo") String cnbNo
		, Model model
	) {
		lectBoardService.updateCnbInq(cnbNo);
		LectureNoticeBoardVO lectNoticeVO = new LectureNoticeBoardVO();
		lectNoticeVO.setCnbNo(cnbNo);
		LectureNoticeBoardVO selectLectureNoticeBoard = lectBoardService.selectLectureNoticeBoard(cnbNo);
		model.addAttribute("selectLectureNoticeBoard", selectLectureNoticeBoard);
		
		int mainBoardCount = lectBoardService.mainBoardCount(lectNo);
		model.addAttribute("mainBoardCount", mainBoardCount);
		
		return "lecture/materials/lecture/lectureNoticeBoardEdit";
	}
	
	// 강의 공지 게시판 수정
//	@PostMapping("edit/{cnbNo}")
//	public String updateLectureNoticeBoard(
//		@PathVariable("cnbNo") String cnbNo
//		, @Validated(UpdateGroup.class) LectureNoticeBoardVO lectureNoticeBoardVO
//	) {
//		lectBoardService.updateLectureNoticeBoard(lectureNoticeBoardVO);
//		
//		return "lecture/materials/lecture/lectureNoticeBoardEdit";
//	}
	
	
	

	// 강의 공지 게시판 삭제
	@DeleteMapping("/drop/{cnbNo}")
    public ResponseEntity<String> deleteLectureNoticeBoard(@PathVariable String lectNo, @PathVariable String cnbNo) {
        // 삭제 처리 로직
		lectBoardService.deleteLectureNoticeBoard(cnbNo);
        return ResponseEntity.ok("삭제 완료");
    }
	
	
}



















