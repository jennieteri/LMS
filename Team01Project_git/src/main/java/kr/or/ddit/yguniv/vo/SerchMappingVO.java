package kr.or.ddit.yguniv.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 동적 검색조건 VO
 * @author AYS
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerchMappingVO {
	private String assubYn;			//제출여부
	private String lectNo;           // 강의 번호
    private String stuId;            // 학생 ID
    private String assigNo;         // 과제 번호
    private String searchType;       // 검색 타입 (title, content 등)
    private String searchWord;       // 검색 키워드
    private String sortColumn;       // 정렬 기준 컬럼
    private String sortDirection;    // 정렬 방향 (ASC, DESC)
    
}
