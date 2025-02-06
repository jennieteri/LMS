package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
public class AssignmentSubmissionVO implements Serializable{
	private int rnum;
	
	private String assigNm;
	
	private String assigEd;
	
	@Nullable
	private String lectNm;
	
	@Size(max= 10)
	@NotBlank
	private String lectNo;
	@Size(max= 10)
	@NotBlank
	private String assigNo;
	@Size(max= 10)
	@NotBlank
	private String stuId;
	@Size(max= 10)
	private String peerId;
	@Size(max= 3000)
	@NotBlank
	private String assubNotes;
	
	@Max(100)
	@Nullable
	private Integer assubScore;

	private String assubDate;
	
	@Max(100)
	@Nullable
	private int assubPeerScr;
	
	private String peerYn;
	
	@Nullable
	private Integer atchFileId;
	
	private String assubYn;
	
	// has a --> AssignmentVO
	@ToStringExclude
	private AssignmentVO assignment;
	
	// has a --> 수강생
	@ToStringExclude
	private AttendeeVO attendee;
	
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
	
	public void setAssubDate(String assubDate) {
		if(assubDate == null || assubDate.isEmpty()) {
			 // 현재 날짜를 'yyyyMMdd' 형식으로 포맷
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			this.assubDate = LocalDate.now().format(formatter);
		}else {
			this.assubDate = assubDate;
		}
	}
	
	
	
}
