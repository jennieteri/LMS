package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

//시스템 공지사항 게시판 
@Data
@ToString
@EqualsAndHashCode(of = "snbNo")
public class SystemNoticeBoardVO implements Serializable{
	
	private int rnum;
	
	//공지번호 
	@Size(min= 1, max= 10)
	private String snbNo;
	
	//관리자 아이디
	@Size(min = 10, max = 10)
	private String adminId;
	
	
	// 공지제목
	@ToString.Exclude
	@Size(min=1, max = 200, message = "200자를 초과하셨습니다.")
	private String snbTtl;
	
	// 공지내용
	@ToString.Exclude
	@NotBlank(message = "내용이 입력되지 않았습니다.")
	private String snbCn;
	
	// 공지등록일시
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime snbDt;
	
	// 공지 조회수
	private int snbCount;
	
	
	
	
	
	@ToString.Exclude
	private AdminVO admin;
	
	// 파일그룹 번호 
	@Nullable
	private Integer atchFileId;
	
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

	
	
	
	
	
	
}
