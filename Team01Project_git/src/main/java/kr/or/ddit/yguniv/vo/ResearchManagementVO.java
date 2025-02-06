package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 연구논문관리
 */

@Data
@EqualsAndHashCode(of = {"resmaCd", "profeId"})
public class ResearchManagementVO implements Serializable { 

	@NotBlank
	@Size(max = 10)
	private String resmaCd; // 연구논문번호
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String profeId; // 교수번호
	
	@NotBlank
	@Size(max = 200)
	private String resmaNm; // 연구논문명
	
	@NotBlank
	@Size(max = 200)
	private String resmaPurp; // 연구목적
	
	@NotBlank
	@Size(max = 3000)
	private String resmaDesc; // 연구내용
	
	@Size(min = 8, max = 8)
	private String resmaDt; // 연구작성일시
	
	private Long atchFileId; // 파일그룹번호
	
	@NotNull(groups = InsertGroup.class)
	private MultipartFile file; // 연구 논문 자료
	
	private ProfessorVO professorVO;
	private AtchFileVO atchFileVO;
	
	private List<AtchFileDetailVO> atchFileDetailList;
}
