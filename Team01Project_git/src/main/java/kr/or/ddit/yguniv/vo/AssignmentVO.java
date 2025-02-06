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
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.yguniv.validate.DeleteGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "assigNo")
public class AssignmentVO implements Serializable{
	
	private int rnum;
	
	@Nullable
	private String lectNm;
	
	@Size(max= 10, groups = {DeleteGroup.class}, message="과제 번호를 입력해주세요")
	private String assigNo; //시퀀스로 생성
	@Size(max= 10)
	@NotBlank
	private String lectNo;
	@Size(max= 200)
	@NotBlank
	private String assigNm;
	@Size(max= 3000)
	@NotBlank
	private String assigNotes;
	
	@Max(100)
	@Nullable
	private Integer assigScore;
	
	private String assigDate;
	
	@Nullable
	private Integer atchFileId;
	
	private String peerYn;
	
	@Nullable
	private String assigEd;
	
	//has many -->AssignmentSubmissionVO
	@ToStringExclude
	private List<AssignmentSubmissionVO> assignmentsubmissionList;
	
	//has a --> LectureVO
	@ToStringExclude
	private LectureVO lecture;
	
	//첨부파일
	
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
	
	public void setAssigDate(String assigDate) {
		if(assigDate == null || assigDate.isEmpty()) {
			 // 현재 날짜를 'yyyyMMdd' 형식으로 포맷
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	        this.assigDate = LocalDate.now().format(formatter);
		}else {
			this.assigDate = assigDate;
		}
	}
	
	
	
}
