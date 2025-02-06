package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 등록금
 */
@Data
@EqualsAndHashCode(of = {"semstrNo", "stuId"})
public class TuitionVO implements Serializable{
	@NotBlank
	@Size(min = 6, max = 6)
	private String semstrNo;  // 학기번호
	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId;  // 학생번호
	@NotBlank
	@Size(min = 6, max = 6)
	private String awardCd;  // 장학금코드
	@NotBlank
	@Size(max = 30)
	private String tuitVrActno;  // 가상계좌번호
	@NotNull
	private Long tuitTuition;  // 등록금액
	@NotNull
	private Long tuitNetTuition;  // 실제납부액(등록금액 - 장학금)
	@NotBlank
	private String tuitStatusCd;  // 납부상태코드
	private String tuitPayPeriod;  // 납부일자
	
	private SemesterVO semesterVO; 
	private StudentVO studentVO;
	private AwardVO awardVO;
	private CommonCodeVO commonCodeVO;
	
	private NoticeBoardVO regularRegist;  // 정규등록일자
	private NoticeBoardVO additionalRegist;  // 추가등록일자
}
