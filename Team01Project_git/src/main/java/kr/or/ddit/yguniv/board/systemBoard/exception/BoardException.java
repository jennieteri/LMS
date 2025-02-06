package kr.or.ddit.yguniv.board.systemBoard.exception;

/**
 * 게시판 에서만 사용할 커스텀 예외
 *
 */
public class BoardException extends RuntimeException{
	
	public BoardException() {
		super();
		
	}
	
	public BoardException(String sibNo) {
		super(String.format("글번호 %c 예서 예외 발생", sibNo));
		
	}

	public BoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BoardException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BoardException(String sibNo, String message) {
		super("SibNo: " + sibNo + ", Message: " + message);
		
	}

	public BoardException(Throwable cause) {
		super(cause);
		
	}
	
}
