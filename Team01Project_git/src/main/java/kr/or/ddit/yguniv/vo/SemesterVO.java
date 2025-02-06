package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SemesterVO implements Serializable{
	@NotBlank
	@Size(min = 6, max = 6)
	private String semstrNo;  // 학기코드(202401)
	private String semstrYn;  // 현재학기여부
	
	// 학기코드에서 년도와 학기를 추출하는 메서드
    public String getYear() {
        if (semstrNo != null && semstrNo.length() == 6) {
            return semstrNo.substring(0, 4); // 앞 4자리는 년도
        }
        return null;
    }

    public String getSemester() {
        if (semstrNo != null && semstrNo.length() == 6) {
            return semstrNo.substring(4, 6); // 뒤 2자리는 학기
        }
        return null;
    }
    
    public String getLabel() {
    	if (semstrNo != null && semstrNo.length() == 6) {
    		return String.format("%s-%s", semstrNo.substring(0, 4), semstrNo.substring(4, 6));
    	}
    	return null;
    }
}
