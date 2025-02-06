package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class CheatingVO  implements Serializable {
	@Size(max = 10)
	private String attenNo;
	@Size(max = 10)
	private String stuId;
	@Size(max = 10)
	private String testNo;
	@Size(max = 1)
	private String cheatCnt;
}
