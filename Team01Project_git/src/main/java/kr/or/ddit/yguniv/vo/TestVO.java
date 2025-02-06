package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringExclude;

import kr.or.ddit.yguniv.validate.OfflineInsertGroup;
import lombok.Data;

@Data
public class TestVO  implements Serializable {
	@Size(max = 10)
	private String testNo;
	@Size(max = 10)
	private String lectNo;
	@Size(max = 2)
	@NotBlank(message = "시험 종류는 공백일 수 없습니다.")
	private String testSe;
	@Size(max = 2)
	private String testCd;
	private String testCdNm;
	
	@Size(max = 1)
	@NotBlank(message = "대면, 비대면 시험을 선택해야합니다.")
	private String testOnlineYn;
	@Size(max = 10)
	@NotBlank(message = "시험일자는 공백일 수 없습니다.")
	private String testSchdl;

	@Size(max = 5) // 5준 이유 = HH:mm 으로 와서.
	@NotBlank(message = "시험시작시간은 공백일 수 없습니다.")
	private String testDt;
	@Size(max = 5)
	@NotBlank(message = "시험종료시간은 공백일 수 없습니다.")
	private String testEt;

	private String croomCd;
	private String croomCdNm;
	
	
//	시험 리스트 조회용 강의실
	private ClassRoomVO classroomVO;
	
	@ToStringExclude
	private ProfessorVO profeVO;
	
	@ToStringExclude
	private PersonVO personVO;

	@ToStringExclude
	private LectureVO lectVO;
	//has a 문제
	private List<QuestionVO> questionVO;
	
	//has many 응시기록
	private List<ExaminationRecordVO> examList;
	
	private CommonCodeVO commonVO;
	
	
	
}
