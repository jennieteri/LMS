package kr.or.ddit.yguniv.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 단순 키워드 검색용
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCondition {
	private String searchType;
	private String searchWord;
}
