package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of="shapDocNo")
public class AwardAskVO implements Serializable{
	//장학금신청
	
	private int rnum; 
	
	@NotBlank
	@Size(max=10)
	private String shapDocNo; 
	//신청 추천 서류 번호 
	//2024010001
	
	
	@NotBlank
	@Size(min = 6, max = 6)
	private String semstrNo; //학기번호  2024-01
	
	@NotBlank
	@Size(min= 10, max= 10)
	private String stuId; //학생 번호 
	
	
	@NotBlank
	@Size(max = 10)
	private String awardCd; //장학코드
	
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime shapRcptDate; //접수 일시 
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime shapChcDate; //선발 일시
	
	@Size(min= 10, max= 10)
	private String profeId; // 교수번호 
	
	@ToString.Exclude
	private String shapRecommend; // 추천 사유 
	
	@NotBlank
	private String shapAccCd; // 장학금 진행 코드
	
	
	private String shapNoReason; // 반려사유 
	
	@Nullable
	private Integer atchFileId; // 파일그룹번호
	
			
			@JsonIgnore
			@ToString.Exclude
			@Nullable
			@Valid
			private AtchFileVO atchFile;
			
			@JsonIgnore
			@ToString.Exclude
			private MultipartFile[] uploadFiles;
			public void setUploadFiles(MultipartFile[] uploadFiles) {
				List<AtchFileDetailVO> fileDetails = Optional.ofNullable(uploadFiles)
															.map(Arrays::stream)
															.orElse(Stream.empty())
															.filter(f->!f.isEmpty())	
															.map(AtchFileDetailVO::new)
															.collect(Collectors.toList());
				if(!fileDetails.isEmpty()) {
					this.uploadFiles = uploadFiles;
					atchFile = new AtchFileVO();
					atchFile.setFileDetails(fileDetails);
				}
		
			}
			  
		    private StudentVO studentVO; // 학생정보
		    private ProfessorVO professorVO; // 교수 정보
		    private AwardVO awardVO; // 장학금 이름
		    private CommonCodeVO commonCodeVO; // 공통코드
		    private CollegeVO collegeVO; // 학부
		    private DepartmentVO departmentVO; // 학과 
		    
		  
			
}
