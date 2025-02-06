package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class LectureCartVO  implements Serializable {
	@Size(max = 10)
	@NotBlank
	private String stuId;
	@Size(max = 10)
	@NotBlank
	private String lectNo;
	@Size(max = 4)
	private String cartSttusCd;
}
