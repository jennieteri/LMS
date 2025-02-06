package kr.or.ddit.yguniv.commons.exception;

/**
 * 데이터 insert 전 학기가 중복될 경우에 대한 예외.
 */
public class SemesterDuplicatedException extends RuntimeException{

	public SemesterDuplicatedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SemesterDuplicatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SemesterDuplicatedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SemesterDuplicatedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SemesterDuplicatedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
