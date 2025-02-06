package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "pbNo")
public class ProjectBoardVO implements Serializable {
	
	private int rnum;
	
	private int pbNo;
	
	private String pbTitle;
	
	private String lectNo;
	
	private String stuId;
	
	private String teamCd;
	
	private String pbCn;
	
	private String pbDate;
	public void setPbDate(String pbDate) {
		pbDate.replace("-", "");
		this.pbDate = pbDate;
	}
	
	
	private String pbNoti;
	
	private String pbDelyn;
	
	private int pbHit;
	
	private Integer atchFileId;
	
	//has a
	private ProjectMemberVO projectMember;
	
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
