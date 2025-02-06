<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>



<style>

.card {
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 20px;
}

.table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

.table th, .table td {
    
    padding: 15px;
    border: 1px solid #ddd;
}

.table th {
    background-color: #007bff;
    color: white;
    font-size: 14px; /* 작게 설정 */
    font-weight: bold;
    width: 20%; /* 20%로 설정 */
    padding: 10px;
    text-align: center;
}

.table td {
    font-size: 16px; /* 더 크고 읽기 쉽게 설정 */
    color: #555;
    padding: 10px;
    width: 80%; /* 나머지 공간 차지 */
    padding-left: 25px; /* 왼쪽 여백 추가 */
}

.table .content-row td {
    
    height: 150px;
    vertical-align: middle;
}

.text-end .btn {
    font-size: 14px;
    padding: 10px 20px;
    border-radius: 5px;
    margin-left: 10px;
}

.text-end .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    color: white;
}

.text-end .btn-primary:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.text-end .btn-danger {
    background-color: #dc3545;
    border-color: #dc3545;
    color: white;
}

.text-end .btn-danger:hover {
    background-color: #c82333;
    border-color: #bd2130;
}
</style>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active">학사일정</li>
    <li class="breadcrumb-item active" aria-current="page">${noticeboard.ntcNm}</li>
  </ol>
</nav>


<div class="card">
    <table class="table" id="form-table" data-path="${pageContext.request.contextPath }">
        <tr>
            <th>제목</th>
            <td colspan="4">
            ${noticeboard.ntcNm}
            <c:if test="${linkedYn}">
                 <i class="bi bi-calendar-check-fill float-end" id="favoriteIcon" style="font-size: 1.5rem; cursor: pointer;"
                    data-bono="${noticeboard.ntcCd}" data-yn="Y" data-url="noti"></i>
             </c:if>
             <c:if test="${not linkedYn}">
                 <i class="bi bi-calendar-check float-end" id="favoriteIcon" style="font-size: 1.5rem; cursor: pointer;"
                    data-bono="${noticeboard.ntcCd}" data-yn="N" data-url="noti"></i>
             </c:if>
            </td>
        </tr>
        <tr>
            <th>일정</th>
            <td>${noticeboard.ntcDt} ~ ${noticeboard.ntcEt}</td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td colspan="3">
                <c:forEach items="${noticeboard.atchFile.fileDetails}" var="fd" varStatus="vs">
                    <c:url value='/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl"/>
                    <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
                    ${not vs.last ? '|' : ''}
                </c:forEach>
                <c:if test="${empty noticeboard.atchFile.fileDetails}">첨부파일 없음</c:if>
            </td>
        </tr>
        <tr class="content-row">
            <td colspan="4">${noticeboard.ntcDesc}</td>
        </tr>
    </table>
</div>

	<div class="text-end">
	<security:authorize access="hasRole('EMPLOYEE')">
	    <a href="<c:url value='/noticeboard/${ntcCd}/edit' />" class="btn btn-primary">수정</a>
	    <form action="<c:url value='/noticeboard/${noticeboard.ntcCd}/delete' />" method="post" style="display:inline;">
	        <button type="submit" class="btn btn-danger">삭제</button>
	    </form>
    </security:authorize>
	    <a href="<c:url value='/noticeboard' />" class="btn btn-primary">목록</a>
	</div>

<script src="${pageContext.request.contextPath}/resources/js/utils/mycalendarLinked.js"></script>











