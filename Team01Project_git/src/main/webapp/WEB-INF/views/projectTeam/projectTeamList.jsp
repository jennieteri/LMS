<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
		{
			"imports": {
							"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
							"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
			}
		}
</script>

<style>

.team-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
    padding: 15px;
}

.card-title {
    font-size: 1.25rem;
    font-weight: bold;
}

.modal-body .form-label {
    font-weight: bold;
}

.team-box {
    padding: 20px;
    border: 1px solid #ddd;
    background-color: #fff;
    text-align: center;
    font-size: 18px;
    border-radius: 5px;
}

</style>
<input id="attendeeCount" type="hidden" value="${attendeeCount}">
<div class="container mt-4">
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
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTaskSubmission">제출관리</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTeam">팀관리</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectMember">팀원관리</a>
	                </li>
                </security:authorize>
	            </ul>
		    	
		    </div>
		</nav>
    </div>

    <!-- 과제 선택 및 팀생성 -->
    <div class="row g-3 align-items-center mb-3 pt-3">
        <div class="col-md-6">
            <form:select class="form-select" id="taskSelect" path="projectTaskList" data-path="${pageContext.request.contextPath}" data-lectno="${lectNo}">
                <form:option value="" label="과제선택" />
                <form:options items="${projectTaskList}" itemValue="taskNo" itemLabel="taskTitle" />
            </form:select>
        </div>
        <div class="col-md-6 text-end">
            <button id="createBtn" class="btn btn-primary">팀생성</button>
        </div>
    </div>

    <!-- 팀 리스트 및 작업 공간 -->
    <div id="teamArea" class="row g-3">
        
    </div>
</div>

<div id="createTeamArea"></div>


<!-- 팀 수정 모달 -->
<div id="editTeamModal" class="modal fade" tabindex="-1" aria-labelledby="editTeamModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="editTeamModalLabel" class="modal-title">팀 정보 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
            </div>
            <form id="editTeamForm">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="teamName" class="form-label">팀 이름</label>
                        <input type="text" id="teamName" class="form-control" placeholder="팀 이름을 입력하세요">
                    </div>
                    <div class="mb-3">
                        <label for="teamPurpose" class="form-label">팀 목적</label>
                        <textarea id="teamPurpose" class="form-control" rows="3" placeholder="팀 목적을 입력하세요"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="saveTeamBtn" class="btn btn-primary">저장</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </form>
        </div>
    </div>
</div>




<script src="${pageContext.request.contextPath }/resources/js/projectTeam/projectTeamList.js" type="module"></script>