<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>  
    
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학생관리</li>
    <li class="breadcrumb-item active" aria-current="page">학생증 신청 목록</li>
  </ol>
</nav>

<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>


<style>


.custom-btn {
    background: none; /* 배경 제거 */
    border: none; /* 버튼 테두리 제거 */
    padding: 5px; /* 버튼 내부 여백 */
    display: flex;
    align-items: center; /* 버튼 안의 요소 정렬 */
    justify-content: center;
  
}

.custom-img {
    width: 30px; /* 이미지 너비 */
    height: 30px; /* 이미지 높이 */
    margin-right: 5px; /* 이미지와 텍스트 간격 */
}


<style>
/* 테이블 스타일 */
.table {
    width: 90%;
    margin: 0 auto;
    border-collapse: collapse;
    font-size: 14px;
    table-layout: fixed;
    word-wrap: break-word;
    border: 1px solid #ddd; /* 테두리 추가 */
}

.table thead td, .table tbody td {
    border: 1px solid #ddd; /* 테두리 추가 */
    text-align: center;
    padding: 8px;
    vertical-align: middle;
    height: 50px; /* 행 높이 동일하게 설정 */
    line-height: 1.5;
}


.table img {
    max-width: 70%;
    height: auto;
    margin: 5px 0;
}

.content-container {
    text-align: center;
    margin-top: 10px;
    padding: 10px;
    border-radius: 5px;
    word-wrap: break-word;
}

.button-container {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 15px;
}
.status-complete {
    color: gray;
    background-color: #f0f0f0; /* 연한 회색 배경 */
    border: 1px solid #ccc; /* 테두리 */
    pointer-events: none; /* 클릭 불가 */
    cursor: not-allowed; /* 비활성화된 마우스 커서 */
}


</style>

</style>
	
    <tr>

		<div class="search-area d-flex justify-content-end align-items-center gap-2" data-pg-target="#searchForm" 
		data-pg-fn-name="fnPaging" style="text-align: right">
					<form:select path="condition.searchType" class="form-select" style="width: 150px;">
						<form:option value="" label="전체" />
						<form:option value="title" label="처리상태" />
						<form:option value="writer" label="학번" />
					</form:select>
					<form:input path="condition.searchWord" class="form-control" style="width: 200px;" />
					<button id="search-btn" class="btn btn-primary">검색</button>
				</div>


	</tr>
<br>
<table class="table table-bordered">
	<thead class="table-primary text-center">
	
	<tr>                        
		<th style="width:60px;">순번</th>
		<th style="width:150px">학번</th>
		<th style="width:150px">학과</th>
		<th style="width:180px">이름</th>
		<th style="width:300px">요청일시</th>
		<th style="width:80px">상태</th>
		<tr>
	
	
	</thead>
	<tbody align="center">
		<c:if test="${not empty card}">

			<c:forEach items="${card}" var="card">


				<tr data-stu-id ="${card.stuId }">

					<td>${card.rnum}</td>
					<td>${card.stuId}</td>
					<c:if test="${not empty card.departmentVO}">
					    <td>${card.departmentVO.deptNm}</td>
					</c:if>
					<c:if test="${empty card.departmentVO}">
					    <td>학과 정보 없음</td>
					</c:if>
					
					<td>${card.studentVO.nm}</td>
					<td>${fn:split(card.stuCardDate, 'T')[0]} ${fn:split(card.stuCardDate, 'T')[1]}</td>
					<td>
				        <button class="btn ${card.commonCodeVO.cocoStts == '요청' ? 'btn-success' : 
				                            (card.commonCodeVO.cocoStts == '발급' ? 'btn-danger' : 'btn-secondary')} statusBtn">
				            				${card.commonCodeVO.cocoStts}
				        </button>
				    </td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty card}">
			<tr>
				<td colspan="4">글 없음.</td>
			</tr>
		</c:if>
	</tbody>
		<tfoot>

		<tr style="text-align: center;">
					<td colspan="9">
						<div class="paging-area">${pagingHTML }</div>
		
					</td>
		
		</tr>
				
		</tfoot>

</table>

<form:form id="searchForm" method="get" modelAttribute="condition">
    <form:input path="searchType" style="display: none;" />
    <form:input path="searchWord" style="display: none;" />
    <input type="hidden" name="page" />
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/studentCard/studentCardStatus.js"></script>
          