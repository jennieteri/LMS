<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>

* 테이블 스타일 */
.table {
    margin-top: 20px;
    background-color: white;
    border: 1px solid #ddd;
}

.table th {
    background-color: #f8f9fa;
    text-align: center;
    vertical-align: middle;
}

.table tbody tr:hover {
    background-color: #f1f1f1;
}

/* 버튼 스타일 */
.task-controls .btn {
    margin-left: 5px;
}

.task-controls .btn-primary:hover {
    background-color: #0056b3;
}

.task-controls .btn-danger:hover {
    background-color: #c82333;
}

/* 체크박스 스타일 */
#selectAll {
    cursor: pointer;
}

.task-select {
    cursor: pointer;
}

/* 수정 폼 영역 */
#form-area {
    margin-top: 20px;
    display: none;
}

#form-area.active {
    display: block;
}


</style>


<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
		{
			"imports": {
							"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
							"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
			}
		}
</script>

<div class="project-container">
<input id="attendeeCount" type="hidden" value="${attendeeCount }">
    <div class="menu-bar">
    	<!-- 상단 메뉴 -->
		<nav class="py-2 bg-primary border-bottom">
		    <div class="container d-flex flex-wrap">
	            <ul class="nav me-auto">
	            	<li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask">홈</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask">과제관리</a>
	                </li>
                <security:authorize access="hasRole('PROFESSOR')"> 
	                <li class="nav-item">
	                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTaskSubmission">제출관리</a>
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
    
    <!-- 과제 테이블 -->
    <div id="projectControl" class="task-table-wrapper pt-3" data-path="${pageContext.request.contextPath }" data-lectno="${lectNo }">
        
        <table class="table table-bordered task-table">
            <thead>
                <tr>
                	<security:authorize access="hasRole('PROFESSOR')"> 
                    <th><input type="checkbox" id="selectAll"></th>
                    </security:authorize>
                    <th>과제주제</th>
                    <th>과제제출마감일</th>
                    <th>배점</th>
                    <th>첨부파일</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty projectTaskList }">
                    <c:forEach items="${projectTaskList }" var="task">
                        <tr id="task-row-${task.taskNo}">
                        	<security:authorize access="hasRole('PROFESSOR')"> 
						    <td class="text-center"><input type="checkbox" class="task-select" value="${task.taskNo}"></td>
						    </security:authorize>
						    
						    <td class="task-title">
						    <a href="javascript:;" class="view-task-details" data-task-id="${task.taskNo}">
						        ${task.taskTitle}
						    </a>
						    </td>
						    <td class="text-center task-et">${fn:substring(task.taskEt, 0, 4)}-${fn:substring(task.taskEt, 4, 6)}-${fn:substring(task.taskEt, 6, 8)}</td>
						    <td class="text-center task-score">${task.taskScore}</td>
						    <td class="task-files" data-atch="${task.atchFileId }">
						        <c:choose>
						            <c:when test="${not empty task.atchFileId}">
						                <c:forEach items="${task.atchFile.fileDetails}" var="fd" varStatus="vs">
						                    <c:url value='/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl"/>
						                    <a href="${downUrl}">${fd.orignlFileNm}(${fd.fileFancysize})</a>
						                    ${not vs.last ? '|' : ''}
						                </c:forEach>
						            </c:when>
						            <c:otherwise>없음</c:otherwise>
						        </c:choose>
						    </td>
						</tr>
						<div id="form-area">
						</div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty projectTaskList }">
                    <tr>
                        <td colspan="5" class="text-center">등록된 과제가 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
	<security:authorize access="hasRole('PROFESSOR')">
	    <div class="task-controls text-end my-3">
	        <a href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask/new" class="btn btn-primary">등록</a>
	    	<button class="btn btn-danger" id="deleteTaskBtn">다건삭제</button>
	    </div>
    </security:authorize>
</div>

<div class="modal fade" id="taskDetailsModal" tabindex="-1" aria-labelledby="taskDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="taskDetailsModalLabel">과제 상세 정보</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- 과제 상세 정보가 이곳에 동적으로 채워질 예정 -->
                <table class="table table-bordered">
                    <tbody id="taskDetailsContent">
                        <tr>
                            <td colspan="2">로딩 중...</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
            	<security:authorize access="hasRole('PROFESSOR')">
		            <button class="btn btn-info" id="editTaskBtn">수정</button>
		            <button class="btn btn-danger" id="delTaskBtn">삭제</button>
		        </security:authorize>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>




<div class="modal fade" id="editTaskModal" tabindex="-1" aria-labelledby="editTaskModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="editTaskModalLabel">과제 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editTaskForm">
                    <input type="hidden" name="taskNo" id="editTaskNo">
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <th class="fw-bold">과제 주제</th>
                                <td>
                                    <input type="text" id="editTaskTitle" name="taskTitle" class="form-control" required>
                                </td>
                            </tr>
                            <tr>
                                <th class="fw-bold">제출 마감일</th>
                                <td>
                                    <input type="date" id="editTaskEt" name="taskEt" class="form-control" required>
                                </td>
                            </tr>
                            <tr>
                                <th class="fw-bold">배점</th>
                                <td>
                                    <input type="number" id="editTaskScore" name="taskScore" class="form-control" min="1" max="100" required>
                                </td>
                            </tr>
                            <tr>
                                <th class="fw-bold">기존 첨부 파일</th>
                                <td>
                                    <div id="existingFiles" class="mb-2">없음</div>
                                    <input type="file" id="editTaskFiles" name="uploadFiles" class="form-control mt-2" multiple>
                                </td>
                            </tr>
                            <tr>
                                <th class="fw-bold">과제 내용</th>
                                <td>
                                    <textarea id="editTaskNotes" name="taskNotes" class="form-control"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveEditTaskBtn">저장</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>




<script src="${pageContext.request.contextPath }/resources/js/projectTask/projectTaskList.js" type="module"></script>