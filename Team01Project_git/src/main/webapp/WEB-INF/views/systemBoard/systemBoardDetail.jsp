<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">시스템 공지사항</li>
    <li class="breadcrumb-item active" aria-current="page">상세보기</li>
  </ol>
</nav>

<style>
/* 테이블 스타일 */
.table {
    width: 100%;
    margin: 0 auto;
    border-collapse: collapse;
    font-size: 14px;
    table-layout: fixed;
    word-wrap: break-word;
    border: 1px solid #ddd; /* 테두리 추가 */
}

.table thead td, .table tbody td {
    text-align: center;
    padding: 8px;
    vertical-align: middle;
    height: 50px;
    line-height: 1.5;
    border: 1px solid #ddd; /* 각 셀에 테두리 추가 */
}

/* 테이블 헤더 배경색 및 스타일 */
.table thead {
    background-color: #007bff;
    color: white;
    font-weight: bold;
}

/* 이미지 스타일 */
.table img {
    max-width: 70%;
    height: auto;
    margin: 5px 0;
}

/* 콘텐츠 컨테이너 */
.content-container {
    text-align: center;
    margin-top: 10px;
    padding: 10px;
    border-radius: 5px;
    word-wrap: break-word;
    white-space: pre-line;
}

/* 버튼 컨테이너 */
.button-container {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 15px;
}
</style>


<div class="fixed-container">
    <table class="table table-primary">
        <thead>
            <tr>
                <td style="width: 20%;">게시물 제목</td>
                <td class="bg-light" colspan="4" style="text-align: left;">${systemBoard.snbTtl}</td>
                <td style="width: 15%; text-align: center;">
                    <security:authorize access="hasRole('ADMIN')">
                        <button class="btn btn-primary">팝업등록</button>
                    </security:authorize>
                </td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>작성일시</td>
                <td class="bg-light">${fn:split(systemBoard.snbDt, 'T')[0]} ${fn:split(systemBoard.snbDt, 'T')[1]}</td>
                <td>작성자</td>
                <td class="bg-light">${systemBoard.adminId}</td>
                <td>조회수</td>
                <td class="bg-light">${systemBoard.snbCount}</td>
            </tr>
            <tr>
                <td colspan="6" class="bg-light">
                    <c:forEach items="${systemBoard.atchFile.fileDetails}" var="fd">
                        <c:if test="${fn:toLowerCase(fd.fileExtsn) == 'jpg' || 
                                     fn:toLowerCase(fd.fileExtsn) == 'jpeg' || 
                                     fn:toLowerCase(fd.fileExtsn) == 'png' || 
                                     fn:toLowerCase(fd.fileExtsn) == 'gif'}">
                            <div style="margin-top: 10px;">
                                <img src="<c:url value='/systemBoard/${systemBoard.snbNo}/atch/${fd.atchFileId}/${fd.fileSn}' />" 
                                     alt="${fd.orignlFileNm}" />
                            </div>
                        </c:if>
                    </c:forEach>
                    <!-- CKEditor의 HTML 콘텐츠 렌더링 -->
                   <div class="content-container">
					    <c:out value="${systemBoard.snbCn}" escapeXml="false" />
					</div>



                </td>
            </tr>
            <tr>
                <td>첨부파일</td>
                <td colspan="5" class="bg-light" style="text-align: left;">
                    <c:forEach items="${systemBoard.atchFile.fileDetails}" var="fd" varStatus="vs">
                        <c:url value='/systemBoard/${systemBoard.snbNo}/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl" />
                        <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
                        ${not vs.last ? '|' : ''}
                    </c:forEach>
                    <c:if test="${empty systemBoard.atchFile.fileDetails}">
                        첨부파일 없음
                    </c:if>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<div class="button-container">
    <security:authorize access="hasRole('ADMIN')">
        <a href="<c:url value='/systemBoard/${snbNo}/edit' />" class="btn btn-info">수정</a>
       <form action="${pageContext.request.contextPath}/systemBoard/delete/${snbNo}" method="post" id="deleteForm">
		    <button type="button" class="btn btn-danger delete-btn">삭제</button>
		</form>




    </security:authorize>
    <a href="<c:url value='/systemBoard' />" class="btn btn-primary">목록</a>
</div>


<script src="${pageContext.request.contextPath}/resources/js/board/delete.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/board/ck.js"></script>
