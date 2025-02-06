package kr.or.ddit.yguniv.dissent;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.or.ddit.yguniv.dissent.dao.DissentMapper;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.SemesterVO;

@ControllerAdvice(value = "kr.or.ddit.yguniv.dissent.controller")
public class DissentControllerAdvice {
	
	@Inject
	private DissentMapper dissentMapper;
	
	@ModelAttribute("semstrList")
	public List<SemesterVO> semstrList(){
		return dissentMapper.selectSemesterList();
	}
	@ModelAttribute("lectList")
	public List<LectureVO> lectList(){
		return dissentMapper.selectLectureList();
	}
}
