package kr.or.ddit.yguniv.paging;

import java.util.List;

import lombok.Data;

@Data
public class DataTablesPaging<T>{
	private int draw;
	private int start;
	private int length;
	private int recordsTotal;
	private int recordsFiltered;
	
	public int getEnd() {
		return start + length;
	}
	
	private List<Order> orderList;
	
	private T detailCondition;
	
	@Data
	public static class Order{
		private String column;
		private String dir;
	}
	
	public PaginationInfo<T> getPaginationInfo() {
		PaginationInfo<T> paging = new PaginationInfo<>(length, 5);
		paging.setCurrentPage(start/length + 1);
		paging.setTotalRecord(recordsFiltered);
		return paging;
	}
}
