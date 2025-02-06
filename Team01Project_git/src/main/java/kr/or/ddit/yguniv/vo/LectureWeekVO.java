package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import kr.or.ddit.yguniv.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 강의 주차(강의자료게시판)
 */
@Data
@EqualsAndHashCode
public class LectureWeekVO implements Serializable {
	@NotBlank(groups = InsertGroup.class, message = "주차를 선택해야 합니다")
	@Size(max = 4, groups = InsertGroup.class, message = "주차코드는 4글자를 초과할 수 없습니다")
	private String weekCd;  // 주차코드
	@Size(max = 10, message = "강의번호는 10글자를 초과할 수 없습니다")
	private String lectNo;  // 강의번호
	@NotBlank(groups = Default.class, message = "주차설명은 공백일 수 없습니다")
	@Size(max =300, groups = Default.class, message = "주차설명이 너무 깁니다")
	private String leweNm;  // 주차설명
	
	private CommonCodeVO commonCodeVO;
	private LectureVO lectureVO;
	private List<OrderLectureDataVO> orderLectureDataList;
}
