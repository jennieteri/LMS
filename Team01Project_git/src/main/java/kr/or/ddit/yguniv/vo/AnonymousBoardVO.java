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
 * 익명게시판
 */
@Data
@EqualsAndHashCode(of = {"ambCd"})
public class AnonymousBoardVO implements Serializable{ 
	@NotBlank
	@Size(max = 10)
	private String ambCd; // 게시글 번호
	
	@NotBlank
	@Size(min = 2, max = 30)
	private String ambNickNm; // 닉네임
	
	@NotBlank
	@Size(max = 100)
	private String ambTtl; // 게시글제목
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime ambDate; // 게시글작성일시
	
	@NotBlank
	@Size(max = 3000)
	private String ambNotes; // 게시글내용
	
	@NotBlank
	@Size(min = 2, max = 20)
	private String ambPswd; // 게시글비밀번호
	
	private Long atchFileId; // 파일그룹번호
	
	@NotNull(groups = InsertGroup.class)
	private MultipartFile file; // 게시물 첨부파일
	
	private AtchFileVO atchFileVO;
	private List<AtchFileDetailVO> atchFileDetailList;
}
