package kr.or.ddit.yguniv.noticeboard.exception;

/**
 * 게시판 에서만 사용할 커스텀 예외
 *
 */
public class BoardException extends RuntimeException{
	
	public BoardException() {
		super();
		
	}
	
	public BoardException(int boNo) {
		super(String.format("글번호 %d 예서 예외 발생", boNo));
		
	}

	public BoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BoardException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BoardException(String message) {
		super(message);
		
	}

	public BoardException(Throwable cause) {
		super(cause);
		
	}
	
}
