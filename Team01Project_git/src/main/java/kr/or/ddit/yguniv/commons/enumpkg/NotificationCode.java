package kr.or.ddit.yguniv.commons.enumpkg;

public enum NotificationCode {
    OK("NO01"),
    INFO("NO02"),
    WARN("NO03"),
	DANG("NO04");

    private final String code;

    NotificationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
