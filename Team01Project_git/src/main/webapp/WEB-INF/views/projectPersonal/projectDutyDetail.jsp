<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>일감상세보기</h2>
<!-- Header Navigation -->
    <nav class="py-2 bg-primary border-bottom">
        <div class="container d-flex flex-wrap">
            <ul class="nav me-auto">
            	<li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectPersonal">홈</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectDuty/${projectMember.projectTeam.teamCd}">일감</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectChatRoom/${projectMember.projectTeam.taskNo}">채팅방</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectTaskSubmission/${projectMember.projectTeam.teamCd}">제출관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectBoard/${projectMember.projectTeam.teamCd}">게시판</a>
                </li>
            </ul>
        </div>
    </nav>
<div class="pt-3">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" >
		<tr>
			<th class="text-center bg-light" style="width: 150px; vertical-align: middle;">제목</th>
			<td class="py-3 px-4">${projectDuty.dutyTitle}</td>
		</tr>
		<tr>
			<th class="text-center bg-light" style="width: 150px; vertical-align: middle;">상태</th>
			<c:choose>
			<c:when test="${projectDuty.dutyPrgsRtprgs eq 100 }">
			<td class="py-3 px-4">완료</td>
			</c:when>
			<c:otherwise>
			<td class="py-3 px-4">진행중</td>
			</c:otherwise>
			</c:choose>
		<tr>
			<th class="text-center bg-light" style="width: 150px; vertical-align: middle;">일감진척도</th>
			<td class="py-3 px-4">${projectDuty.dutyPrgsRtprgs}%</td>
		</tr>
		<tr>
			<th class="text-center bg-light" style="width: 150px; vertical-align: middle;">담당자</th>
			<td class="py-3 px-4">${projectDuty.projectMember.nm}(${projectDuty.dutyId })</td>
		</tr>
		<tr>
			<td colspan="2" class="py-3 px-4">${projectDuty.dutyCn}</td>
		</tr>
		
	</table>
	<div class="text-end">
		<a href="<c:url value='/lecture/${lectNo}/projectDuty/edit/${projectDuty.dutyNo }' />" class="btn btn-primary">수정</a>
		<form action="<c:url value='/lecture/${lectNo}/projectDuty/delete/${projectDuty.dutyNo }' />" method="post" style="display:inline;">
		    <input type="hidden" name="dutyTeam" value="${projectDuty.dutyTeam }">
		    <button type="submit" class="btn btn-danger">삭제</button>
		</form>
		<a href="<c:url value='/lecture/${lectNo}/projectDuty/${projectDuty.dutyTeam }' />" class="btn btn-primary">목록</a>
	</div>
</div>
