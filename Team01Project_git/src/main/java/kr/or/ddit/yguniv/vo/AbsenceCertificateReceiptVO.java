package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 공결인정서접수
 */

@Data
@EqualsAndHashCode
public class AbsenceCertificateReceiptVO implements Serializable{

	@Size(max = 10)
	private String absenceCd;

	@NotBlank
	@Size(min = 10, max = 10)
	private String stuId; // 학번
	
	@NotBlank
	@Size(max = 4)
	private String weekCd; // 주차코드
	
	private int lectOrder; // 강의차수
	
	@NotBlank
	private String lectNo; // 강의번호
	
	@NotBlank
	private String absenceResn; // 공결사유
		
	private Integer atchFileId; // 파일그룹번호
	
	@Size(max = 4)
	private String cocoCd; // 서류 처리상태 코드
	
	private String TEIN; // 교시 코드
	private String ATST; // 출결 상태
	private String PRST; // 서류 접수 상태
	
	@Size(min = 8, max = 8)
	private String absenceDt;
	
	@Size(max = 3000)
	private String absenceReturn;
	
	// 페이지네이션 페이지 값
	private int page = 1;  // 기본값 1
    private int size = 10; // 기본값 10
	
	private boolean approved; // 승인 여부 (승인: true, 반려: false)

    // 이 메서드를 통해 승인 여부를 확인
    public boolean isApproved() {
        return approved;
    }
	
	//@JsonIgnore
	@ToString.Exclude
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
//	@NotNull(groups = InsertGroup.class)
	@JsonIgnore
	@ToString.Exclude
	private MultipartFile[] uploadFiles; // 공결 인증 제출 서류
	
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
	
	private AttendanceVO attendanceVO;
	private AttendeeVO attendeeVO;
	private StudentVO studentVO;
	private OrderLectureDataVO orderVO;
	
	private CommonCodeVO commonCodeVO;
}
