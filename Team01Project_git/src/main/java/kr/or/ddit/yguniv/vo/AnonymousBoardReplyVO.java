package kr.or.ddit.yguniv.vo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 익명 게시판 대댓글
 */
@Data
@EqualsAndHashCode(of = {"abrepCd", "abcomCd", "ambCd"})
public class AnonymousBoardReplyVO {
	
	@NotBlank
	@Size(max = 10)
	private String abrepCd; // 대댓글번호
	
	@NotBlank
	@Size(max = 10)
	private String abcomCd; // 댓글번호
	
	@NotBlank
	@Size(max = 10)
	private String ambCd; // 게시글번호
	
	@NotBlank	
	@Size(min = 2, max = 30)
	private String ambNickNm; // 닉네임
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime abrepDate; // 대댓글작성일시
	
	@NotBlank
	@Size(max = 3000)
	private String abrepNotes; // 대댓글내용
	
	@NotBlank
	@Size(min = 2, max = 20)
	private String abrepPswd; // 대댓글비밀번호
	
	private AnonymousBoardCommentVO anonymousBoardCommentVO;
}
