package kr.or.ddit.yguniv.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
public class AtchFileVO {
	private Integer atchFileId;
	private String creatDt;
	private boolean useAt;

	@ToString.Exclude
	@Nullable
	@Valid
	private List<@NotNull AtchFileDetailVO> fileDetails;
}
