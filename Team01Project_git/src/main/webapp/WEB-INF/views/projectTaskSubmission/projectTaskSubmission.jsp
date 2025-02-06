<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new java.util.Date());
    request.setAttribute("today", today);
%>

<style>

.card {
    border: 1px solid #ddd;
    border-radius: 8px;
    margin-bottom: 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.card-header {
    background-color: #f8f9fa;
    font-weight: bold;
    padding: 10px;
}

.card-footer {
    background-color: #f8f9fa;
    padding: 10px;
    text-align: right;
}
</style>

<div id="mainDiv" class="container mt-4" data-path="${pageContext.request.contextPath }" data-lect-no="${lectNo }">
    <div class="menu-bar">
    	<!-- 상단 메뉴 -->
		<nav class="py-2 bg-primary border-bottom">
		    <div class="container d-flex flex-wrap">
	            <ul class="nav me-auto">
	            	<li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask">홈</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask">과제관리</a>
	                </li>
                <security:authorize access="hasRole('PROFESSOR')"> 
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTaskSubmission">제출관리</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTeam">팀관리</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectMember">팀원관리</a>
	                </li>
                </security:authorize>
	            </ul>
		    	
		    </div>
		</nav>
    </div>
    
    <c:if test="${empty projectTaskSubmissionList}">
        <p class="text-center text-muted">제출된 프로젝트가 없습니다.</p>
    </c:if>
    <c:if test="${not empty projectTaskSubmissionList}">
        <div class="accordion pt-3" id="projectAccordion">
            <c:forEach var="projectSubmission" items="${projectTaskSubmissionList}" varStatus="status">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading-${projectSubmission.tasksubNo}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapse-${projectSubmission.tasksubNo}"
                                aria-expanded="false" aria-controls="collapse-${projectSubmission.tasksubNo}">
                            프로젝트과제명: ${projectSubmission.projectTask.taskTitle}
                        </button>
                    </h2>
                    <div id="collapse-${projectSubmission.tasksubNo}" class="accordion-collapse collapse"
                         aria-labelledby="heading-${projectSubmission.tasksubNo}" data-bs-parent="#projectAccordion">
                        <div class="accordion-body">
                            <c:forEach var="submission" items="${projectTaskSubmissionList}" varStatus="subStatus">
                                <div class="card mb-3">
                                    <div class="card-body pt-3">
                                        <div class="row mb-2">
                                            <div class="col-md-3 text-end fw-bold">팀명</div>
                                            <div class="col-md-9">${submission.projectTeam.teamNm}</div>
                                        </div>
                                        <div class="row mb-2">
                                            <div class="col-md-3 text-end fw-bold">제출일</div>
                                            <div class="col-md-9">${submission.tasksubDate}</div>
                                        </div>
                                        <div class="row mb-2">
                                            <div class="col-md-3 text-end fw-bold">점수</div>
                                            <div class="col-md-9">
                                                <input id="score${submission.tasksubNo }" type="number" max="100" min="0" value="${submission.tasksubScore}" class="form-control form-control-sm" style="width: 100px; display: inline-block;">
                                                / ${projectSubmission.projectTask.taskScore }
                                            </div>
                                        </div>
                                        <div class="row mb-2">
                                            <div class="col-md-3 text-end fw-bold">첨부파일</div>
                                            <div class="col-md-9">
                                                <c:forEach items="${submission.atchFile.fileDetails}" var="fd" varStatus="vs">
                                                    <c:url value='/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl"/>
                                                    <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
                                                    ${not vs.last ? '|' : ''}
                                                </c:forEach>
                                                <c:if test="${empty submission.atchFile.fileDetails}">
                                                    첨부파일없음
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer">
                                    	<c:if test="${empty submission.tasksubScore }">
                                        <button id="gradeBtn" class="btn btn-primary btn-sm" data-max-score="${projectSubmission.projectTask.taskScore }"
                                        data-tasksub-no="${submission.tasksubNo }"
                                        >
                                            채점
                                        </button>
                                        </c:if>
                                        <c:if test="${not empty submission.tasksubScore }">
                                        <button class="btn btn-primary btn-sm" disabled="disabled">채점완료</button>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<script src="${pageContext.request.contextPath }/resources/js/projectTaskSubmission/projectTaskSubmission.js"></script>

