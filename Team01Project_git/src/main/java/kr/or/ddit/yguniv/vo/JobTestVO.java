package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="jobTestNo")
public class JobTestVO implements Serializable{
	@NotNull
	@Size(max = 10)
	private String jobTestNo; //문제번호
	
	@NotNull
	@Size(max = 50)
	private String jobTestText; //문제내용
	
	@NotNull
	@Size(max = 200)
	private String jobTestType; //선호도유형
	
	private Integer rnum;  // 페이징 순번
	
	//검사결과
	private List<JobTestResultVO> jobTestResult;
	private CommonCodeVO commoncode; //공통코드 가져오기 
}
