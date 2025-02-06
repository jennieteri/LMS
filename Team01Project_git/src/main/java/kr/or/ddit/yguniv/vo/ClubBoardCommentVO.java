package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 동아리 활동 게시판 댓글
 */

@Data
@EqualsAndHashCode(of = {"clbomCd", "clboaCd", "clubNo"})
public class ClubBoardCommentVO implements Serializable { 
	
	@NotBlank
	@Size(max = 10)
	private String clbomCd; // 댓글번호
	
	@NotBlank
	@Size(max = 10)
	private String clboaCd; // 게시판번호
	
	@NotBlank
	@Size(max = 10)
	private String clubNo; // 동아리번호
	
	@NotBlank
	@Size(max = 20)
	private String clbomWrtr; // 댓글작성자
	
	@NotBlank
	@Size(max = 3000)
	private String clbomNotes; // 댓글내용
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime clbomDate; // 댓글작성일시
	
	// private ClubBoardVO clubBoardVO
	
}
