package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.OfflineInsertGroup;
import kr.or.ddit.yguniv.validate.OnlineInsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 강의 차수별 강의자료(강의자료게시판)
 */
@Data
@EqualsAndHashCode(of = {"lectOrder", "weekCd", "lectNo"})
public class OrderLectureDataVO implements Serializable {
	@NotNull(groups = InsertGroup.class, message = "강의 차시는 공백일 수 없습니다")
	@Min(1) @Max(99)
	private Long lectOrder;  // 강의차수순번
	@NotBlank(groups = Default.class, message = "등록 주차를 선택해야 합니다")
	@Size(max = 4, groups = Default.class, message = "주차코드는 4글자를 초과할 수 없습니다")
	private String weekCd;  // 주차코드
	@NotBlank
	@Size(max = 10)
	private String lectNo;  // 강의번호
	@NotBlank(groups = Default.class, message = "차시제목은 공백일 수 없습니다")
	@Size(max = 300, groups = Default.class, message = "차시제목이 너무 깁니다")
	private String sectNm;  // 강의차수제목
	@NotBlank(groups = Default.class, message = "학습 시작일을 지정해야 합니다")
	@Size(min = 8, max = 8, groups = Default.class, message = "날자 형식에 맞지 않습니다(YYYYMMDD)")
	private String sectDt;  // 강의차수시작일
	@NotBlank(groups = OnlineInsertGroup.class, message = "학습 종료일을 지정해야 합니다")
	@Size(min = 8, max = 8, groups = OnlineInsertGroup.class, message = "날자 형식에 맞지 않습니다(YYYYMMDD)")
	private String sectEt;  // 강의차수종료일
	@NotBlank(groups = OnlineInsertGroup.class, message = "이수인정시간을 설정해야 합니다")
	private String sectIdnty;  // 강의이수인정시청시간
	private Integer atchFileId;  // 강의자료파일그룹번호
	@NotBlank(groups = OfflineInsertGroup.class, message = "강의실을 설정해야 합니다")
	private String croomCd;  // 강의실코드
	@NotBlank(groups = OfflineInsertGroup.class, message = "수업 교시를 설정해야 합니다")
	private String sectEtime;  // 강의시간표코드
	
	private Long originLectOrder;  // 업데이트 시 사용할 원본강의차시
	
	@JsonIgnore
	@ToString.Exclude
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
	@JsonIgnore
	@ToString.Exclude
	@NotNull(groups = OnlineInsertGroup.class, message = "강의영상을 등록해야 합니다")
	private MultipartFile[] uploadFiles;
	
	public void setUploadFiles(MultipartFile[] uploadFiles) {
		List<AtchFileDetailVO> fileDetails = Optional.ofNullable(uploadFiles)
													.map(Arrays::stream)
													.orElse(Stream.empty())
													.filter(f->!f.isEmpty())	
													.map(AtchFileDetailVO::new)
													.collect(Collectors.toList());
		if(!fileDetails.isEmpty()) {
			this.uploadFiles = uploadFiles;
			atchFile = new AtchFileVO();
			atchFile.setFileDetails(fileDetails);
		}
	}
	
	@AssertTrue(message = "강의영상은 MP4/WebM 형식만 지원합니다")
	public boolean isVideo() {
		if(atchFile == null) {
			return true;
		}
		return atchFile.getFileDetails().stream()
				.allMatch(f->f.getFileExtsn().equalsIgnoreCase("mp4") || f.getFileExtsn().equalsIgnoreCase("webm"));
	}
	
	private LectureWeekVO lectureWeekVO;
	private List<AtchFileDetailVO> atchFileDetailList;
	private ClassRoomVO classRoomVO;
	private CommonCodeVO timeCommonCodeVO;
	private AttendanceVO attendanceVO;
}
