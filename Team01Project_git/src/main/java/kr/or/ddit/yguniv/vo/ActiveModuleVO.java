package kr.or.ddit.yguniv.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class ActiveModuleVO {
	private String id;
	private String modId;
	private Integer x;
	private Integer y;
	
	// ModuleVO
	private Integer w;
	private Integer h;
	@ToString.Exclude
	private String content;
	private String modNm;
	
	private ModuleVO moduleVO;
}
