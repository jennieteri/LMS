package kr.or.ddit.yguniv.vo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 익명 게시판 댓글
 */
@Data
@EqualsAndHashCode(of = {"abcomCd", "ambCd"})
public class AnonymousBoardCommentVO {
	
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
	private LocalDateTime abcomDate; // 댓글작성일시
	
	@NotBlank
	@Size(max = 3000)
	private String abcomNotes; // 댓글내용
	
	@NotBlank
	@Size(min = 2, max = 20)
	private String abcomPswd; // 댓글비밀번호
	
	private AnonymousBoardVO anonymousBoardVO;
}
