package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *	상담
 */
@Data
@EqualsAndHashCode
@ToString(exclude = {"dscsnTopic", "dscsnNotes", "dscsnReason", "dscsnReturn"})
public class DiscussionVO implements Serializable {
	@NotBlank
	@Size(max = 10)
	private String dscsnCd;  // 상담번호
	@NotBlank
	@Size(min = 10, max = 10)
	private String profeId;  // 교수번호
	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId;  // 학생번호
	@NotBlank
	@Size(min = 8, max = 8)
	private String dscsnSchdl;  // 상담일자
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime dscsnDt;  // 상담시작시간
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime dscsnEt;  // 상담종료시간
	@Size(max = 500)
	private String dscsnTopic;  // 상담일지제목
	private String dscsnNotes;  // 상담일지내용
	@NotBlank
	@Size(max = 1)
	private String dscsnOnlineYn;  // 온라인상담여부
	@NotBlank
	@Size(max = 3000)
	private String dscsnReason;  // 상담사유
	@Size(max = 3000)
	private String dscsnReturn;  // 반려사유
	@NotBlank
	@Size(min = 4, max = 4)
	private String dscsnStatusCd;  // 상담상태코드
	
	private ProfessorVO professorVO;
	private StudentVO studentVO;
	private CommonCodeVO commonCodeVO; 
}
