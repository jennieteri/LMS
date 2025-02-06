package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "croomCd")
public class ClassRoomVO implements Serializable {
	@NotBlank
	@Size(max = 10)
	private String croomCd;  // 강의실 코드
	@NotBlank
	@Size(max = 33)
	private String croomNm;  // 강의실 호실
	@NotBlank
	@Size(max = 166)
	private String croomPstn;  // 강의실 위치
	@NotNull
	@Size(min = 0, max = 999)
	private Integer croomTnope;  // 강의실 인원
}
