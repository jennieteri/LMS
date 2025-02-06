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

import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(of="gdtCd")
public class GraduationVO implements Serializable{
	@NotNull(groups = InsertGroup.class)
	private int gdtCd; //인증자료번호
	
	@NotNull(groups = InsertGroup.class)
    @Size(max = 10)
	private String stuId; //학생번호 
	
	@NotNull(groups = InsertGroup.class)
    @Size(max = 10)
	private String empId; //담당교직원번호
	
	@Size(min = 4, max = 4)
	private String cocoCd; //졸업인증자료처리상태
	
	
	@NotNull(groups = InsertGroup.class)
    @Size(max = 10)
	private String gdtType; //인증서류종류
	
	@NotNull(groups = UpdateGroup.class)
    @Size(max = 30)
	private String gdtNm;  //인증서류명
	
	@NotNull(groups = UpdateGroup.class)
    @Size(max = 30)
	private String gdtInst; //발급담당기관
	
	@NotNull(groups = UpdateGroup.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String gdtIssu; //서류발급일자
	
	@NotNull(groups = UpdateGroup.class)
	private Long  gdtScore; //자료할당점수
	
	
	private Integer  atchFileId; //파일그룹번호
	
	@Size(max = 300)
	private String gpaFileNm; //첨부파일원본명
	
	@Size(max = 500)
	private String gpaFileSaveNm; //첨부파일저장명
	
	private String codeName; // 대기, 반려, 승인 공통코드
	
	private int rnum; //번호
	
	private String nm; //이름
	
	
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
	
	@JsonIgnore
	@ToString.Exclude
	@NotNull(groups = UpdateGroup.class)
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
