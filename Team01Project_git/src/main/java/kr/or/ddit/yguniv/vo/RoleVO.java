package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleVO implements Serializable{
	private String personType;
	private String personTypeNm;
}
