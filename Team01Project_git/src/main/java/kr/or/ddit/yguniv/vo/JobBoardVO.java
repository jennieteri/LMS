package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@EqualsAndHashCode(of="jobNo")
//@ToString(exclude= {"atchFileId"})
public class JobBoardVO implements Serializable{
	@NotNull
    @Size(max = 20)
	private String jobNo; //게시글 번호
	
	@NotNull
    @Size(max = 200)
	private String jobNm;//게시글 제목
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate jobDate; //작성 일시
	
   
	private String jobCate; //게시글유형
	
	private Long  jobCnt;//조회수
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate jobDt; //행사시작일시
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate jobEt; //행사종료일시
	
	@NotNull
	private String jobLimit; //행사모집인원
	
	@NotNull
	private Integer atchFileId; //파일그룹번호 
	
	private String sn;  // 문의 순번
	
	private String id; //작성자아이디
	
	private String writerNm; //작성자이름
	
	
	
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
	

// 첨부파일 한개의 bean property path 모델명.atchFile.fileDetails[0].uploadFile
	}
	
	private String jobContent;	
	
	//has many 관계 행사신청학생
	private List<EventRegistrantVO> eventRegistrants;
	
	
	//공통 코드 
	//private commoncodeVO comoncode;
	
}
