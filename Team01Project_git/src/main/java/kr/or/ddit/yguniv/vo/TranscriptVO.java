package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TranscriptVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String stuId;
	@Size(min=6,max = 6)
	private String semeCd;
	@Size(max = 3)
	private String transTotal;
}
