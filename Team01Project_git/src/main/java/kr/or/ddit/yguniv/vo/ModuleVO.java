package kr.or.ddit.yguniv.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class ModuleVO {
	private String modId;
	private Integer w;
	private Integer h;
	@ToString.Exclude
	private String content;
	private String modNm;
	private String icon;
}
