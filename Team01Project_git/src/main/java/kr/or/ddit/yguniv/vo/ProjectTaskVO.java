package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(of = "taskNo")
public class ProjectTaskVO implements Serializable {
	
	private int rnum;
	
	@Size(max = 10)
	private String taskNo;
	@NotBlank
	@Size(max = 10)
	private String lectNo;
	@NotBlank
	@Size(max = 100)
	private String taskTitle;
	@NotBlank
	@Size(max = 3000)
	private String taskNotes;

	@NotBlank
	private String taskEt;

	private Integer taskScore;

	private String taskStatus;
	
	@Nullable
	private Integer atchFileId;
	
	//has a
	@ToStringExclude
	private LectureVO lecture;
	
	//has many
	@ToStringExclude
	private List<ProjectTaskSubmissionVO> projectTaskSubmission;
	
	//첨부파일
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
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
	public void setTaskEt(String taskEt) {
		this.taskEt = taskEt.replaceAll("-", "");
	}
	
}
