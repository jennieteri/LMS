package kr.or.ddit.yguniv.vo;	

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 출결기록
 */

@Data
//@EqualsAndHashCode( of = "lectNm")
public class AttendanceVO { 

	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId; // 학번
	
	@NotBlank
	@Size(max = 10)
	private String weekCd; // 강의번호
	
	@NotBlank
	@Size(max = 2)
	private int lectOrder; // 강의차수
	
	@NotBlank
	@Size(max = 4)
	private String lectNo; // 주차코드
	
	@NotBlank
	@Size(max = 4)	
	private String atndCd; // 출결상태코드
	
	private int count;
	
//	@NotBlank
//	@Size(max = 1)
//	private String atndRcognYn; // 출석인정여부
	
	@NotBlank
	@Size(min = 4, max = 4)
	private String atndIdnty; // 시청시간
	
	
	private int studentCount;
	
	// 수강기록을 수정할 수 있는 일자인지 확인
	private boolean updatePossible;
	public void setUpdatePossible(int status) {
		this.updatePossible = (status == 1);
	}
	
	// mapper에 등록되어있는 attendanceMap을 참고하여 가져오기 때문에 필요가 없다.
//	private String lectNm; // 강의 과정명
//	
//	private String cocoStts; // 공통코드상태명
//	
//	private String nm; // 학생이름
//	
//	private String lectOnlineYn; // 해당수업 온라인여부
//	
//	private PersonVO personVO;
	private StudentVO studentVO;
	private OrderLectureDataVO orderVO;
	private CommonCodeVO commonCodeVO;
	private LectureVO lectureVO;
}
