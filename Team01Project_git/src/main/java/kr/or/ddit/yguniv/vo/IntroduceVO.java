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
@EqualsAndHashCode(of="intCd")
public class IntroduceVO implements Serializable{
	
	@NotNull
	private int intCd; //자기소개서 번호
	@NotNull
    @Size(max = 10)
	private String stuId; //학생번호 
	
	@NotNull
    @Size(max = 10)
	private String empfiCd; //취업분야코드
	
	@NotNull
    @Size(max = 30)
	private String intOtherNn; //기타취업분야
	
	@NotNull
    @Size(max = 3000)
	private String intQue1; //지원동기
	
	@NotNull
    @Size(max = 3000)
	private String intQue2; //입사후 포부
	
	@NotNull
    @Size(max = 3000)
	private String intQue3; //성격및 장단점
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate intDate; //작성일시
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate intUdate; //수정일시
	
	private int rnum; //번호
	
	@Size(max = 3000)
	private String intFeed; //피드백 항목
	
	private EmploymentFieldVO employmentfield; //취업분야
	List<CertificateVO> certificate; //자격증
	
	@NotNull
	private Integer atchFileId; //파일그룹번호
	
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
	
	
	
	
	
	
	
	private StudentVO student; //학생
	private PersonVO person; //사용자
	private CommonCodeVO commoncode; //공통코드 가져오기 
}
