<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
   <style>
/* 부모 컨테이너 크기 조정 */
.search-area {
    display: flex;
    justify-content: flex-end; /* 오른쪽 정렬 */
    align-items: center; /* 세로 정렬 */
    gap: 10px; /* 요소 간 간격 */
}

/* 셀렉트박스 크기 */
.search-area .form-select {
    width: 100px; /* 드롭다운 메뉴의 너비 */
}

/* 검색어 입력 필드 크기 */
.search-area .form-control {
    width: 200px; /* 검색창 너비 */
}


</style>
   
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 관리</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 유형 관리</li>
  </ol>
</nav>


		<div class="search-area" data-pg-target="#searchForm" data-pg-fn-name="fnPaging">
  
    <form:select path="condition.searchType" class="form-select">
        <form:option value="title" label="전체" />
        <form:option value="writer" label="내용" />
    </form:select>

    <form:input path="condition.searchWord" class="form-control" placeholder="검색어 입력" />

    <button id="search-btn" class="btn btn-primary">검색</button>
</div>


	<br>
	
	
<table class="table table-bordered">
  <thead class="table-primary text-center">
	
		<tr>                        
		<th style="width:10px;">순번</th>
		<th style="width:50px">장학금 코드</th>
		<th style="width:130px">장학금 지급 기관</th>
		<th style="width:150px">장학금 이름</th>
		<th style="width:90px">장학금 유형</th>
		<th style="width:20px">금액</th>
		<tr>
	</thead>
	<tbody align="center">
		<c:if test="${not empty award}">

			<c:forEach items="${award}" var="award">


				<tr onclick="location.href='<c:url value='/award/${award.awardCd}/detail'/>'" style="cursor: pointer;">	

					<td>${award.rnum}</td>
					<td>${award.awardCd}</td>
					<td>${award.awardNm}</td>
					<td>${award.awardEdnstNm}</td>
					<td>${award.awardType}</td>
				
					<td><fmt:formatNumber value="${award.awardGiveAmt}" type="number" pattern="#,###"/> 원</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty award}">
			<tr>
				<td colspan="5">글 없음.</td>
			</tr>
		</c:if>
	</tbody>

	<tfoot>
		<tr>
			<td colspan="6">
				<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
				<div class="mx-auto">
				${pagingHTML }
				</div>
				
				<div>
					<a class="btn btn-primary" style="position: absolute; right: 0px; top: 0px;" href="<c:url value='/award/insertAward/new'/>" >등록</a>
				</div>
		
				</div>
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