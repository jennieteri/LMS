package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;

@Data
public class ScheduleVO  implements Serializable {
	
	@Size(max = 10)
	private String lectNo;
	@NotBlank(message = "주 강의실을 선택해야 합니다")
	@Size(max = 10)
	private String croomCd;
	@Size(max = 4)
	private String dateCd;
	@Size(max = 4)
	private String edcTimeCd;
	
	private ClassRoomVO classRoomVO;
	private CommonCodeVO commonCodeVO;
	private EducationTimeTableCodeVO ettcVO;

}
