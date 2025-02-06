package kr.or.ddit.yguniv.noticeboard.controller.advice;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판에서 발생한 예외(BoardException) 을 처리하기 위한 advice
 */
@Slf4j
@ControllerAdvice("kr.or.ddit.board.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandlingAdvice {
	@ExceptionHandler(BoardException.class)
	public String boardExceptionHandling(BoardException exception, Model model, HttpServletResponse resp) {
		log.error(exception.getMessage(), exception);
		resp.setStatus(500);
		model.addAttribute("status", 500);
		model.addAttribute("message", exception.getMessage());
		return "errors/exceptionView";
	}
}