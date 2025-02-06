package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "stuId" )
public class StudentCardVO implements Serializable{
	
	private int rnum;
	
	@NotBlank
	@Size(min = 10, max = 10)
	@JsonProperty("studentId") 
	private transient String stuId; // 학생번호
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime stuCardDate; // 요청일시 
	
	@Size(max = 10)
	@NotBlank
	private String  cocoCd; // 발급상태코드 
	

	

	// 학생 테이블 
	@ToString.Exclude
	private StudentVO studentVO;
	
	private CommonCodeVO commonCodeVO; // 공통코드
	
	@ToString.Exclude
	private DepartmentVO departmentVO;

	
	

}
