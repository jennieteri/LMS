package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of="id", callSuper = false)
public class AdminVO extends PersonVO implements Serializable {
	
	public String getAdminId() {
		return getId();
	}
	
	public void setAdminId(String id) {
		setId(id);
	}
	
}
