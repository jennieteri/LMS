package kr.or.ddit.yguniv.commons.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class PKNotFoundExceptionHandler{
	
	@ExceptionHandler(PKNotFoundException.class)
	public ResponseEntity<Object> handlePKNotFoundException(PKNotFoundException ex) {
		if(ex.json) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND) // 상태 코드 설정
					.body(Collections.singletonMap("message", ex.getMessage()));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
