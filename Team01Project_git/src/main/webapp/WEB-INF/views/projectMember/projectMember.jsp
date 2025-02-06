<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>

.droppable {
    min-height: 50px;
    border: 1px dashed #ccc;
    padding: 10px;
    background-color: #f8f9fa;
    border-radius: 5px;
}

.list-group-item {
    cursor: grab;
    transition: background-color 0.2s;
}

.list-group-item:hover {
    background-color: #f1f1f1;
}

.list-group-item:active {
    cursor: grabbing;
    opacity: 0.8;
}

/* 버튼 영역 정렬 */
.buttons-container {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}


</style>
<input id="attendeeCount" type="hidden" value="${attendeeCount}">
<div class="container mt-4">
    <!-- 상단 메뉴 -->
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
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTeam">팀관리</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectMember">팀원관리</a>
	                </li>
                </security:authorize>
	            </ul>
		    </div>
		</nav>
    </div>

    <!-- 현재 프로젝트 섹션 -->
    <div class="row g-3 align-items-center mb-3 pt-3">
        <div class="col-auto">
            <form:select class="form-select" id="taskSelect" path="projectTaskList" data-path="${pageContext.request.contextPath}" data-lectno="${lectNo}">
                <form:option value="" label="과제선택" />
                <form:options items="${projectTaskList}" itemValue="taskNo" itemLabel="taskTitle" />
            </form:select>
        </div>
        <div class="col-auto">
            <select class="form-select" id="teamSelect">
                <option value="" label="팀선택(수동)" />
            </select>
        </div>
        <div class="col">
        <div class="d-flex justify-content-end gap-2">
            <button id="autoBtn" class="btn btn-primary">전체자동배정</button>
            <button id="manualBtn" class="btn btn-success">수동배정</button>
        </div>
    </div>
    </div>
		
    <!-- 콘텐츠 섹션 -->
    <div class="row g-3">
        <!-- 팀원 목록 -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">팀원 목록</h5>
                </div>
                <div class="card-body overflow-auto" style="height: 100%; min-height: 300px;">
                	<div class="team-leader-area pt-3">
		                <h6>조장</h6>
		                <ul id="leaderArea" class="list-group droppable"></ul>
		            </div>
		            <div class="team-members-area pt-3">
		                <h6>팀원</h6>
		                <ul id="teamList" class="list-group droppable" style="min-height: 100px;"></ul>
		            </div>
                </div>
            </div>
        </div>
        <!-- 팀에 속하지 않은 수강생 -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">수강생 목록</h5>
                </div>
                <div class="card-body overflow-auto" style="height: 100%; min-height: 300px;">
                    <ul id="attendeeList" class="list-group droppable"></ul>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="previewModal" tabindex="-1" aria-labelledby="previewModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="previewModalLabel">팀 배정 미리보기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <!-- 팀 리스트가 렌더링될 컨테이너 -->
        <div id="previewContainer"></div>
      </div>
      <div class="modal-footer">
        <button id="confirmBtn" type="button" class="btn btn-success">완료</button>
        <button id="reassignBtn" type="button" class="btn btn-primary">다시 배정</button>
        <button id="cancelBtn" class="btn btn-danger">취소</button>
      </div>
    </div>
  </div>
</div>



  
<script src="${pageContext.request.contextPath }/resources/js/projectMember/projectMember.js"></script>