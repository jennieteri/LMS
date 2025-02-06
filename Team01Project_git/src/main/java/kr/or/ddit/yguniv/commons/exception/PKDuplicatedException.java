package kr.or.ddit.yguniv.commons.exception;

/**
 * 데이터 insert 전 primary key가 중복될 경우에 대한 예외.
 */
public class PKDuplicatedException extends RuntimeException{

	public PKDuplicatedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PKDuplicatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PKDuplicatedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PKDuplicatedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PKDuplicatedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
