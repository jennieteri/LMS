<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>
/* 테이블 스타일 */
.table {
    width: 100%;
    border-collapse: collapse;
}

/* 테이블 헤더 스타일 */
.table th {
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
    padding: 10px; /* 셀 안쪽 여백 */
    height: 50px;
}

/* 테이블 데이터 스타일 */
.table td {
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
    padding: 10px; /* 셀 안쪽 여백 */
    height: 50px;
}
</style>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">공지사항</li>
    <li class="breadcrumb-item active" aria-current="page">시스템 공지사항</li>
  </ol>
</nav>

<div class="search-area d-flex justify-content-end align-items-center gap-2" data-pg-target="#searchForm" data-pg-fn-name="fnPaging">
    <form:select path="condition.searchType" class="form-select" style="width: 100px;">
        <form:option value="" label="전체" />
        <form:option value="title" label="제목" />
    </form:select>
    <form:input path="condition.searchWord" class="form-control" style="width: 200px;" />
    <button id="search-btn" class="btn btn-primary">검색</button>
</div>
<br>

<table class="table table-bordered">
    <thead class="table-primary text-center">
        <tr>
            <th style="width:50px;">순번</th>
            <th style="width:300px;">제목</th>
            <th style="width:80px;">작성자</th>
            <th style="width:120px;">작성일자</th>
            <th style="width:20px;">조회수</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty boardList}">
                <c:forEach items="${boardList}" var="board">
                    <tr onclick="location.href='<c:url value='/systemBoard/${board.snbNo}'/>'" style="cursor: pointer;">
                        <td>${board.rnum}</td>
                        <td>${board.snbTtl}</td>
                        <td>${board.adminId}</td>
                        <td>${fn:split(board.snbDt, 'T')[0]} </td>
                        <td>${board.snbCount}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center">글 없음.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="5">
                <div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0;">
                    <div class="mx-auto">${pagingHTML}</div>
                    <security:authorize access="hasRole('ADMIN')">
                        <a class="btn btn-primary float-end" href="<c:url value='/systemBoard/createSystemBoard/new'/>">등록</a>
                    </security:authorize>
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

<script src="${pageContext.request.contextPath}/resources/js/utils/paging.js"></script>
