package kr.or.ddit.yguniv.paging.renderer;

import kr.or.ddit.yguniv.paging.PaginationInfo;

public interface PaginationRenderer {
	/**
	 * Pagination 내의 startPage와 endpage로 페이지 이동 링크 생성 
	 * @param paging
	 * @param fnName
	 * @return
	 */
	public String renderPagination(PaginationInfo paging,String fnName);
}
