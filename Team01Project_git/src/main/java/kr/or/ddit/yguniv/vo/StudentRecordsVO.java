package kr.or.ddit.yguniv.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.yguniv.validate.InsertGroup;
import kr.or.ddit.yguniv.validate.OnlineInsertGroup;
import kr.or.ddit.yguniv.validate.ReturnGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 학적변동기록(서류)
 */
@Data
@EqualsAndHashCode(of = "streIssuNo")
public class StudentRecordsVO {
	@Size(min = 9, max = 9)
	private String streIssuNo;  // 서류발급번호
	@Size(min = 10, max = 10)
	private String stuId;  // 신청학생번호
	@NotBlank(groups = InsertGroup.class, message = "신청학적을 선택해주세요")
	@Size(max = 4)
	private String streCateCd;  // 학적상태코드
	@Size(max = 4)
	private String streStatusCd;  // 결제상태코드
	@NotBlank(groups = InsertGroup.class, message = "신청사유를 입력해주세요")
	@Size(max = 3000)
	@ToString.Exclude
	private String streRes;  // 학적변동사유
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime reqstDt;  // 신청일시
	@Size(min = 8, max = 8)
	private String confmDe;  // 승인일자
	@NotBlank(groups = ReturnGroup.class, message = "반려사유를 입력해주세요")
	@Size(max = 3000)
	@ToString.Exclude
	private String returnRsn;  // 반려사유
	@NotBlank(groups = InsertGroup.class, message = "신청학기를 선택해주세요")
	@Size(max = 6)
	private String semstrNo;  // 학기번호
	private Integer atchFileId;  // 첨부파일
	
	private Integer rnum;  // 순번
	
	private String needFile;  // 증빙서류 검증용
	@JsonIgnore
	@AssertTrue(groups = InsertGroup.class, message = "해당 신청은 증빙서류를 제출해야 합니다")
	public boolean isUploadFilesValid() {
		boolean res = true;
		if(StringUtils.isNotBlank(needFile)) {
			res = ArrayUtils.isNotEmpty(uploadFiles);
		}
		return res;
	}
	
	// 권한별 조회용 id, role
	private String id;
	private String role;
	
	// DetailCondition용
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate reqstDe;
	
	@ToString.Exclude
	@Nullable
	@Valid
	private AtchFileVO atchFile;
	
	@JsonIgnore
	@ToString.Exclude
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
	
	private StudentVO studentVO;
	private ProfessorVO professorVO;
	private CommonCodeVO streCateCodeVO;
	private CommonCodeVO streStatusCodeVO;
	private SemesterVO semesterVO;
}
