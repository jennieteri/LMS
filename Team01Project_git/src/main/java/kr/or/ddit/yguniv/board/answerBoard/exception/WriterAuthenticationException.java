package kr.or.ddit.yguniv.board.answerBoard.exception;

/**
 * 작성자 인증에 실패한 경우에 대한 예외 정의
 */
public class WriterAuthenticationException extends BoardException {

    public WriterAuthenticationException() {
        super();
    }

    public WriterAuthenticationException(String sibNo) {
    	super(String.format("글번호 %s 예서 예외 발생", sibNo));
    }


    public WriterAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WriterAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterAuthenticationException(String sibNo, String message) {
        super("SibNo: " + sibNo + ", Message: " + message);
    }

    public WriterAuthenticationException(Throwable cause) {
        super(cause);
    }
}
