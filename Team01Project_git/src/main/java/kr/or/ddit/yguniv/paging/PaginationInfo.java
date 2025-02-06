package kr.or.ddit.yguniv.paging;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//특정 클래스에 종속되지 않음
@Getter
public class PaginationInfo<T> {
	
	//생성자와 함께 파라미터가 넘어오지 않으면 다음과 같이 설정
	public PaginationInfo() {
		this(10,5);
	}


	public PaginationInfo(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
		//사용하지 않아도 미리 생성해놓음으로 널 포익 제거
		this.variousCondition= new HashMap<String, Object>();
	}

	//검색조건 분류와 검색어 
	@Setter
	private SimpleCondition simpleCondition;
	
	//타입이 생성할때 주어진 값으로 정해지도록 제네릭 타입 부여
	@Setter
	private T detailCondition;
		
	@Setter
	private Map<String, Object> variousCondition;
	
	public void addVariousCondition(String conditionName,Object conditionValue){
		variousCondition.put(conditionName,conditionValue);
	}
	
	@Setter
	private int totalRecord; //DB로 부터 조회
	
	private int screenSize; //임의 결정
	private int blockSize; //임의 결정
	
	@Setter
	private int currentPage; // 요청 파라미터
  
	public int getTotalPage() {
		int totalPage = (totalRecord+(screenSize-1))/screenSize;
		return totalPage;
	} 
	
	
	public int getEndRow() {
		int endRow=currentPage*screenSize;
		return endRow;	
	}

	public int getStartRow() {
		int startRow = getEndRow()-(screenSize - 1);
		return startRow;
	}
	
	public int getEndPage() {
		int endPage = ((currentPage+(blockSize-1))/blockSize) * blockSize;
		return endPage;
	}
	
	public int getStartPage() {
		int startPage = getEndPage() - (blockSize-1);
		return startPage;
	}
	

}
