package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(of="jobNo")
@Data
public class EventRegistrantVO implements Serializable{
	
	@NotNull
    @Size(max = 20)
	private String jobNo; //게시글번호 
	
	   
    @NotNull
    @Size(max = 10)
	private String stuId; //학생번호
	
	//취업정보게시판 
	//학생
}
