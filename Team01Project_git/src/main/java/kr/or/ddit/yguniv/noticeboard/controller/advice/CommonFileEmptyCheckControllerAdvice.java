package kr.or.ddit.yguniv.noticeboard.controller.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice(basePackages = "kr.or.ddit.ygniv.noticeboard")
public class CommonFileEmptyCheckControllerAdvice {

	@InitBinder
	public void fileEmptyCheck(WebDataBinder binder) {
		binder.setBindEmptyMultipartFiles(false);
	}

}
