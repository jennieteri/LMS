<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>   
 
<style>
/* 팀원 목록 스타일 */
.team-list {
    margin: 0;
    padding: 0;
    list-style-type: none;
}

.team-list li {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
}

.team-list li:last-child {
    border-bottom: none;
}

.team-list li .name {
    font-size: 16px;
    font-weight: bold;
}

.team-list li .status {
    font-size: 14px;
    font-weight: bold;
}

.status.submitted {
    color: #28a745; /* 초록색 */
}

.status.not-submitted {
    color: #dc3545; /* 빨간색 */
}

</style>

<div id="mainDiv" class="container" 
    data-team-cd = "${projectMember.teamCd }" data-task-no = "${projectMember.projectTeam.taskNo}" data-lect-no="${lectNo }" data-path="${pageContext.request.contextPath }">
    <!-- 네비게이션 -->
    <nav class="py-2 bg-primary border-bottom">
        <div class="container d-flex flex-wrap">
            <ul class="nav me-auto">
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectPersonal">홈</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectDuty/${projectMember.projectTeam.teamCd}">일감</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectChatRoom/${projectMember.projectTeam.taskNo}">채팅방</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectTaskSubmission/${projectMember.projectTeam.teamCd}">제출관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectBoard/${projectMember.projectTeam.teamCd}">게시판</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- 메인 섹션 -->
	<div class="row pt-3">
	    <!-- 왼쪽 섹션: 상호평가 제출현황 -->
	    <div class="col-md-5">
	        <div class="card h-100">
	            <div class="card-header bg-primary text-white">
	                <h5 class="text-center mb-0">상호평가 제출현황</h5>
	            </div>
	            <div class="card-body pt-3" style="height: 100%;">
	                <ul class="team-list">
	                    <c:forEach var="member" items="${projectMemberList}">
	                        <li class="pb-3">
	                            <span class="name">${member.nm} (${member.stuId})</span>
	                            <span class="status ${member.peerYn eq 'Y' ? 'submitted' : 'not-submitted'}">
	                                <c:if test="${member.peerYn eq 'Y'}">제출완료</c:if>
	                                <c:if test="${member.peerYn eq 'N'}">
	                                    미제출
	                                </c:if>
	                            </span>
	                        </li>
	                    </c:forEach>
	                </ul>
	            </div>
	            <div class="card-footer text-end">
                 	<c:if test="${pageContext.request.userPrincipal.name eq projectMember.stuId and submitted }">
                        <button id="peerBtn" class="btn btn-primary" data-bs-toggle="modal" 
                            data-bs-target="#peerModal" style="margin-right: 10px;"
                            data-tasksub-no="${projectTaskSubmission.tasksubNo}">
                            평가제출
                        </button>
                    </c:if>
                 </div>
	        </div>
	    </div>
	
	    <!-- 오른쪽 섹션: 내 프로젝트 팀 과제 제출현황 -->
	    <div class="col-md-7">
	        <div class="card h-100">
	            <div class="card-header bg-success text-white">
	                <h5 class="text-center mb-0">내 프로젝트 팀 과제 제출현황</h5>
	            </div>
	            <div class="card-body pt-3" style="height: 100%;">
	                <ul class="team-list">
	                    <c:if test="${submitted}">
	                        <div class="text-center">
	                            <c:forEach items="${projectTaskSubmission.atchFile.fileDetails}" var="fd" varStatus="vs">
	                                <p>${fd.orignlFileNm} (${fd.fileFancysize})</p>
	                                ${not vs.last ? '|' : ''}
	                            </c:forEach>
	                        </div>
	                    </c:if>
	                     <c:if test="${not submitted}">
	                        제출과제없음
	                    </c:if>
	                </ul>
	            </div>
	            <div class="card-footer text-end">
	                <c:if test="${submitted}">
	                    <button class="btn btn-primary" disabled="disabled">제출완료</button>
	                </c:if>
	                <c:if test="${not submitted and projectMember.leadYn eq 'Y'}">
	                    <button class="btn btn-primary" data-bs-toggle="modal" 
	                        data-bs-target="#taskModal">과제 제출</button>
	                </c:if>
	            </div>
	        </div>
	    </div>
	</div>
    <!-- 카드끝 -->
    
</div>
 
<!-- 상호평가 제출 모달 -->
<div id="peerModal" class="modal" tabindex="-1" style="background-color: rgba(0, 0, 0, 0.5);">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">상호평가 제출</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="peerForm" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <label for="peerFiles" class="form-label">첨부 파일: 양식: ex)이름_학번_상호평가</label>
                    <input type="file" name="uploadFiles" id="peerFiles" class="form-control" required>
                </div>
                <div class="modal-footer">
                    <button id="peerSubmitBtn" type="submit" class="btn btn-primary"
                    data-id="${projectMember.stuId }" data-user="${pageContext.request.userPrincipal.name }"
                    >제출</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 과제 제출 모달 -->
<div id="taskModal" class="modal" tabindex="-1" style="background-color: rgba(0, 0, 0, 0.5);">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">과제 제출</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="taskForm" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <label for="taskFiles" class="form-label">첨부 파일:</label>
                    <input type="file" name="uploadFiles" id="taskFiles" class="form-control" required>
                </div>
                <div class="modal-footer">
                    <button id="taskSubmitBtn" type="submit" class="btn btn-primary"
                    data-id="${projectMember.stuId }" data-user="${pageContext.request.userPrincipal.name }"
                    data-lead-yn = "${projectMember.leadYn }"
                    >제출</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>
 
 
 
<script src="${pageContext.request.contextPath }/resources/js/projectPersonal/projectSubmission.js"></script> 
    

     