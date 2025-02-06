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
public class GraduationPaperVO implements Serializable{
	
	@NotNull
	private int gpaCd;  //게시글 번호
	
	@NotNull(groups = InsertGroup.class)
    @Size(max = 20)
	private String gpaNm; //논문명
	
	@NotNull
    @Size(max = 10)
	private String stuId; //학생번호
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate gpaDate; //졸업논문등록일시
	
	private String gpaDnm; //졸업논문부제목
	
	private String gpaSub; //졸업논문주제
	
	private Integer  atchFileId; //파일그룹번호
	
	private String gpaStatus; //논문 현재 상태
	
    @Size(max = 300)
	private String gpaFileNm; //첨부파일원본명
    @Size(max = 500)
	private String gpaFileSaveNm; //첨부파일저장명
	
	private int rnum; //번호
	
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
	private String profId;
	private String studentNm;
	private String professorNm;
	
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
	
	 private PersonVO student;   // 학생 정보
	 private PersonVO professor; // 교수 정보
}
