package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 강의공지게시판
 */

@Data
@EqualsAndHashCode
public class LectureNoticeBoardVO implements Serializable{
	
	@NotBlank
	@Size(max = 10)
	private String cnbNo; // 게시글번호
	
	@NotBlank
	@Size(max = 10)
	private String lectNo; // 강의번호
	
	@NotBlank	
	@Size(max = 10)
	private String cnbNm; // 게시글제목
	
	@NotBlank
	@Size(max = 3000)
	private String cnbNotes; // 게시글내용
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime cnbDt; // 작성일시
	
	private Integer cnbInq; // 게시글조회수
	
	@Size(max = 1)
	private String cnbMainYn; // 게시글메인고정
	
	// 페이지네이션 페이지 값
	private int page = 1;  // 기본값 1
	private int size = 10; // 기본값 10
	
	private Integer atchFileId; // 파일그룹번호
	
	//@JsonIgnore
	@ToString.Exclude
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
//	@NotNull(groups = InsertGroup.class)
	@JsonIgnore
	@ToString.Exclude
	private MultipartFile[] uploadFiles; // 공결 인증 제출 서류
	
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
	
	private LectureVO lectureVO;
	private ProfessorVO professorVO;
	private StudentVO studentVO;
}
