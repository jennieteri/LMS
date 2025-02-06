package kr.or.ddit.yguniv.paging.renderer;

import kr.or.ddit.yguniv.paging.PaginationInfo;

public class BootStrapPaginationRenderer implements PaginationRenderer {

//Disabled and active states		
	@Override
	public String renderPagination(PaginationInfo paging, String fnName) {
		int startPage = paging.getStartPage();
		int endPage = paging.getEndPage();
		int totalPage = paging.getTotalPage();
		int blockSize = paging.getBlockSize();
		int currentPage = paging.getCurrentPage();
		endPage = endPage > totalPage ? totalPage : endPage;
		
		StringBuffer html = new StringBuffer();
		
		html.append("<ul class='pagination justify-content-center mb-0'>");
		
		String pattern = "<li class='page-item %s'><a class='page-link' href='javascript:void(0)' onclick='"+fnName+"(%d);'>%s</a></li>";
		
		if(startPage > blockSize) { 
			html.append(String.format(pattern, "", startPage-blockSize, "이전"));
		}else {
			html.append(String.format(pattern, "disabled", startPage-blockSize, "이전"));
		}
		for(int page = startPage; page <= endPage; page++) {
			if(page == currentPage) {//선택 페이지와 block의 칸이 같으면 active 활성화 (색 채우기)
				html.append(String.format(pattern, "active", page, page));
			}else {
				html.append(String.format(pattern, "", page, page));
			}
		}
		if(endPage < totalPage) {
			 //endPage '5' < totalPage '7'= true→ if → 다음 버튼에 활성화 속성 추가 
			html.append(String.format(pattern, "", endPage+1, "다음"));
		}else {
			//endPage '10' < totalPage ''7' = false→ else → 다음 버튼에 disabled 속성 추가 
			html.append(String.format(pattern, "disabled", endPage+1, "다음"));
		}
		
		return html.toString();
	}

}
