package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;
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
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "ntcCd")
public class NoticeBoardVO implements Serializable{
	private int rnum;
	
	private int ntcCd;
	@NotBlank
	@Size(max= 10)
	private String prsId;
	
	@NotBlank
	@Size(max= 2)
	private String ntcYn;
	
	@Nullable
	private Integer atchFileId;
	
	@NotBlank
	@Size(max= 100)
	private String ntcNm;
	
	@NotBlank
	@Size(max= 3000)
	private String ntcDesc;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ntcDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ntcEt;
	
	private int ntcInqCnt;
	
	private String cocoCd;  // 주요일정코드
	private String semstrNo;  // 학기번호
	
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
}
