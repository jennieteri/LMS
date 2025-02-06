	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 관리</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 추천/ 신청서 목록</li>
  </ol>
</nav>

		<div class="search-area" style="display: flex; justify-content: flex-end; gap: 10px; align-items: center; margin-bottom: 20px;" data-pg-target="#searchForm" data-pg-fn-name="fnPaging">
		    <form:select path="condition.searchType" class="form-select" style="width: 100px;">
		        <form:option value="" label="전체" />
		        <form:option value="title" label="상태코드" />
		        <form:option value="writer" label="학번" />
		    </form:select>
		    <form:input path="condition.searchWord" class="form-control" style="width: 200px;" />
		    <button id="search-btn" class="btn btn-primary">검색</button>
		</div>


<style>
/* 테이블 스타일 */
.table {
    width: 100%;
    margin: 20px auto;
    border-collapse: collapse;
    font-size: 16px; /* 기본 글자 크기 */
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
}

.table th, .table td {
    padding: 15px; /* 패딩 추가 */
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
    border: 1px solid #ddd; /* 테두리 */
}

.table th {
    font-weight: bold; /* 굵은 글씨 */
}
.popover.custom-popover {
  --bs-popover-max-width: 300px !important; /* 최대 너비를 300px로 설정 */
  --bs-popover-border-color: black !important;
  --bs-popover-header-bg: blue !important;
  --bs-popover-header-color: white;
  --bs-popover-body-padding-x: 1rem !important;
  --bs-popover-body-padding-y: 0.5rem !important;
  
  min-width: 250px !important; /* 최소 너비를 설정 */
  max-width: 300px !important; /* 최대 너비를 설정 */
  width: 100% !important; /* 너비를 100%로 설정 */
  height: 100px; /* 최대 높이를 설정 */
}

/* Popover 헤더 중앙 정렬 */
.popover.custom-popover .popover-header {
  text-align: center !important; /* 중앙 정렬 */
  font-weight: bold; /* 글씨 굵게 */
}





</style>


<table class="table table-bordered">
    <thead class="table-primary">
        <tr>
            <th>순번</th>
            <th>유형</th>
            <th>학기번호</th>
            <th>학번</th>
            <th>이름</th>
            <th>접수일자</th>
            <th>선발일자</th>
            <th>상태코드</th>
            
        </tr>
    </thead>
    <tbody>
        <c:if test="${not empty ask}">
            <c:forEach items="${ask}" var="ask">
                <tr>
                    <td>${ask.rnum}</td>
                    <td>${ask.awardVO.awardType}</td>
                    <td>${fn:substring(ask.semstrNo, 0, 4)}-${fn:substring(ask.semstrNo, 4, 6)}</td>
                    <td>${ask.stuId}</td>
                    <td>
                    <c:url value="/askAward/${ask.shapDocNo }" var="detailUrl"/>
                    	<a href="${detailUrl }">${ask.studentVO.nm}</a>
                    
                    
                    </td>
                    <td>${fn:split(ask.shapRcptDate, 'T')[0]} ${fn:split(ask.shapRcptDate, 'T')[1]}</td>
                    <td>${fn:split(ask.shapChcDate, 'T')[0]} ${fn:split(ask.shapChcDate, 'T')[1]}</td>
                    <td>
                        <c:choose>
                            <c:when test="${ask.commonCodeVO.cocoStts == '승인'}">
                                <button class="btn btn-success">승인</button>
                            </c:when>
                            <c:when test="${ask.commonCodeVO.cocoStts == '반려'}">
                                    <button type="button" class="btn btn-danger"
									        data-bs-toggle="popover"
									        data-bs-placement="left"
									        data-bs-custom-class="custom-popover"
									        data-bs-title="반려사유"
									        data-bs-content="${fn:escapeXml(ask.shapNoReason)}">
									  반려
									</button>


                            </c:when>
                            
                            <c:when test="${ask.commonCodeVO.cocoStts == '접수'}">
                                <button class="btn btn-primary">접수</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-warning">${ask.commonCodeVO.cocoStts}</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty ask}">
            <tr>
                <td colspan="8">신청 목록 없음</td>
            </tr>
        </c:if>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="8">
                <div class="paging-area">${pagingHTML}</div>
            </td>
        </tr>
    </tfoot>
</table>


<form:form id="searchForm" method="get" modelAttribute="condition">
	<form:input path="searchType"  style="display: none;" />
	<form:input path="searchWord" style="display: none;"/>
	<input type="hidden" name="page" />
</form:form>


<script>
document.addEventListener('DOMContentLoaded', function () {
	  var popoverTriggerList = [].slice.call(
	    document.querySelectorAll('[data-bs-toggle="popover"]')
	  );
	  popoverTriggerList.forEach(function (popoverTriggerEl) {
	    new bootstrap.Popover(popoverTriggerEl, {
	      customClass: popoverTriggerEl.getAttribute('data-bs-custom-class'),
	      container: 'body', // Popover를 body에 렌더링
	      html: true,
	      trigger: 'hover' // 마우스 오버 시 보이게
	    });
	  });
	});


</script>

<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>



