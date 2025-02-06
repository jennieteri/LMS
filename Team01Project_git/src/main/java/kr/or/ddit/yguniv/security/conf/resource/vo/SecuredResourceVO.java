package kr.or.ddit.yguniv.security.conf.resource.vo;

import java.util.List;

import lombok.Data;

@Data
public class SecuredResourceVO {
	 private String resId;
	 private String resUrl;
	 private String resMethod;
	 private int resSort;
	 private String resParent;
	 private List<String> authorities;
}
