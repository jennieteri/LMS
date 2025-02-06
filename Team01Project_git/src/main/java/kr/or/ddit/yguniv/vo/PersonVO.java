package kr.or.ddit.yguniv.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.file.ProfileImage;
import kr.or.ddit.yguniv.validate.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "id")
public class PersonVO implements Serializable{

	@NotBlank
	@NotBlank(groups = RoleGroup.class)
	@Size(min = 10, max = 10)
	private String id;

	@NotBlank
	@ToString.Exclude
	private String pswd; //비밀번호 

	@NotBlank
	private String nm;//이름 

	@NotBlank
	@Size(max = 8)
	private String brdt; // 생년월일 

	@NotBlank
	@Size(max=2)
	private String sexdstnCd; // 성별코드 

	@NotBlank
	private String zip; // 집주소

	private String rdnmadr; // 도로명 주소 

	@NotBlank
	private String daddr; // 상세주소

	@NotBlank
	@Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}")
	private String mbtlnum; // 휴대폰번호 

	@NotBlank
	@Email
	private String eml;// 이메일

	@NotBlank
	private String emlRcptnAgreYn; //이메일수신동의여부

	@NotBlank
	private String smsRcptnAgreYn; //sms 수신 동의 여부

	@NotBlank
	private String crtfcMnCd; // 인증수단 코드

	@NotBlank
	private String lastConectDe; // 최종 접속 일자 

	@Min(0)
	private int pswdFailrCo; // 비밀번호 실패 횟수
	
	@NotNull
	private String personYn; // 사용자 삭제 여부
	

	private String proflPhoto; // 데이터베이스 지원
	
	private MultipartFile proflImage; // 학생으로부터 전송된 파일 지원
	
	public void setProflImage(MultipartFile proflImage) {
        if (proflImage == null || proflImage.isEmpty()) return;

        this.proflImage = proflImage;
        try {
            // MultipartFile을 Base64로 변환하여 저장
            this.proflPhoto = ProfileImage.imgToBase64(proflImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 파일 변환 중 오류 발생");
        }
    }

	public PersonVO() {
		// TODO Auto-generated constructor stub
	}

//	private String personType; // 교수, 교직원, 학생 유형
	
	@NotEmpty(groups = RoleGroup.class)
	private List<String> personType; // 교수, 교직원, 학생 유형
	
	private String streCateCd; // 학생일 경우 학적 상태
	
	@ToString.Exclude
	private DepartmentVO departmentVO;

}