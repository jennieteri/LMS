package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  동아리 활동 게시판
 */

@Data
@EqualsAndHashCode(of = {"clboaCd", "clubNo"})
public class ClubBoardVO implements Serializable {
	
	@NotBlank
	@Size(max = 10)
	private String clboaCd; // 게시판번호
	
	@NotBlank
	@Size(max = 10)	
	private String clubNo; // 동아리번호
	
	@NotBlank
	@Size(max = 20)	
	private String clboaWrtr; // 작성자
	
	@NotBlank
	@Size(max = 100)
	private String clboaNm; // 활동제목
	
	@NotBlank
	@Size(max = 3000)
	private String clboaNotes; // 활동내용
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime clboaDate; // 동록일시
	
	@NotBlank
	private Long atchFileId; // 파일그룹번호
	
	@NotNull(groups = InsertGroup.class)
	private MultipartFile file; // 동아리 활동 게시물 첨부 파일
	
	private ClubVO clubVO;
	private AtchFileVO atchFileVO;
	private List<AtchFileDetailVO> atchFileDetailList;
}
