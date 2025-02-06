package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import kr.or.ddit.yguniv.validate.AnswerGroup;
import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 강의 문의게시판
 */
@Data
@EqualsAndHashCode(of = "libNo")
@ToString(exclude = {"libSj", "libCn", "libAnsCn"})
public class LectureInquiryBoardVO implements Serializable {
	private Long libNo;  // 문의번호
	@Size(max = 10)
	private String lectNo;  // 강의번호
	@Size(min = 10, max = 10)
	private String stuId;  // 학생번호
	@NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "문의 제목을 입력해 주세요")
	@Size(max = 500, groups = {InsertGroup.class, UpdateGroup.class}, message = "제목이 너무 깁니다")
	private String libSj;  // 문의제목
	@NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "문의 내용을 입력해 주세요")
	@Size(max = 3000, groups = {InsertGroup.class, UpdateGroup.class}, message = "내용이 너무 깁니다")
	private String libCn;  // 문의내용
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime libDt;  // 문의등록일시
	@NotEmpty(groups = AnswerGroup.class, message = "답변자가 존재하지 않습니다")
	@Size(min = 10, max = 10, groups = AnswerGroup.class, message = "답변자 값은 10자여야 합니다")
	private String profeId;  // 답변작성자
	@NotBlank(groups = AnswerGroup.class, message = "답변 내용을 입력해 주세요")
	@Size(max = 3000, groups = AnswerGroup.class, message = "답변 내용이 너무 깁니다")
	private String libAnsCn;  // 답변내용
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime libAnsDt;  // 답변일시
	private Long libHit;  // 조회수
	
	
	private AttendeeVO attendeeVO;
	private ProfessorVO professorVO;
	private StudentVO studentVO;
	
	
	private String sn;  // 문의 순번
}
