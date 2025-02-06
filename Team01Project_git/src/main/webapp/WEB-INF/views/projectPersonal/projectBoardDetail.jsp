<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

   
<!-- Header Navigation -->
    <nav class="py-2 bg-primary border-bottom">
        <div class="container d-flex flex-wrap">
            <ul class="nav me-auto">
            	<li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectPersonal">홈</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectDuty/${teamCd}">일감</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectChatRoom/${taskNo}">채팅방</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectTaskSubmission/${teamCd}">제출관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectBoard/${teamCd}">게시판</a>
                </li>
            </ul>
        </div>
    </nav>
<div class="pt-3">   
 
<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-lectno="${lectNo }" >
	<tr>
		<th class="text-center bg-light" style="width: 150px; vertical-align: middle;" >제목</th>
		<td class="py-3 px-4">${projectBoard.pbTitle}</td>
	</tr>
	<tr>
		<th class="text-center bg-light" style="width: 150px;">작성자</th>
		<td class="py-3 px-4">${projectBoard.projectMember.nm }(${projectBoard.stuId })</td>
	<tr>
		<th class="text-center bg-light" style="width: 150px;">작성일자</th>
		<td class="py-3 px-4">${projectBoard.pbDate}</td>
	</tr>
	<tr>
		<th class="text-center bg-light" style="width: 150px;">첨부파일</th>
		<td class="py-3 px-4">
			<c:forEach items="${projectBoard.atchFile.fileDetails }" var="fd" varStatus="vs">
			<c:url value='/atch/${fd.atchFileId }/${fd.fileSn }' var="downUrl"/>
			<a href="${downUrl }">${fd.orignlFileNm }(${fd.fileFancysize })</a>
			${not vs.last ? '|' : ''}
			</c:forEach>
			<c:if test="${empty projectBoard.atchFile.fileDetails}">
			    첨부파일없음
			</c:if>
		</td>
	</tr>
	<tr style="min-height: 400px;">
		<td class="py-3 px-4" colspan="2" style="min-height: 400px;">${projectBoard.pbCn}</td>
	</tr>
	
</table>
<div class="text-end">
	<c:if test="${projectBoard.stuId eq pageContext.request.userPrincipal.name }">
	<a href="<c:url value='/lecture/${lectNo}/projectBoard/edit/${teamCd }/${projectBoard.pbNo }' />" class="btn btn-primary">수정</a>
	<form id="deleteForm" action="<c:url value='/lecture/${lectNo}/projectBoard/delete' />" method="post" style="display:inline;">
	    <input type="hidden" name="pbNo" value="${projectBoard.pbNo }">
	    <input type="hidden" name="teamCd" value="${projectBoard.teamCd }">
	    <button type="submit" class="btn btn-danger">삭제</button>
	</form>
	</c:if>
	<a href="<c:url value='/lecture/${lectNo}/projectBoard/${teamCd }' />" class="btn btn-primary">목록</a>
</div>
 </div>


<script src="${pageContext.request.contextPath }/resources/js/projectPersonal/projectBoardForm.js"></script>
 