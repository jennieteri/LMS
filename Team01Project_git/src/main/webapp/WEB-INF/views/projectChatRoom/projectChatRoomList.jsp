<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

.create{
	padding-bottom: 8px;
}

</style>

<div id="mainArea" class="container mt-4" data-path="${pageContext.request.contextPath }" data-team-cd="${projectMember.projectTeam.teamCd }" data-lect-no="${lectNo }" data-task-no="${taskNo }">
	<!-- Header Navigation -->
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
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectChatRoom/${projectMember.projectTeam.taskNo}">채팅방</a>
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
    <!-- 채팅방이 없을 경우 -->
    <c:if test="${empty projectChatRoomList}">
        <div class="alert alert-warning text-center" role="alert">
            <p>현재 생성된 채팅방이 없습니다.</p>
        </div>
    </c:if>

    <!-- 채팅방이 있을 경우 -->
    <c:if test="${not empty projectChatRoomList}">
        <table class="table table-hover align-middle">
            <thead class="table-primary text-center">
                <tr>
                    <th style="width: 20%;">팀명</th>
                    <th style="width: 50%;">채팅방 이름</th>
                    <th style="width: 10%;"></th>
                </tr>
            </thead>
            <tbody>
                <!-- 채팅방 리스트를 반복 출력 -->
                <c:forEach var="room" items="${projectChatRoomList}">
                    <tr>
                        <td class="text-center">${room.team.teamNm}</td>
                        <td class="text-center">${room.projectchatroomTitle}</td>
                        <td class="text-end">
                            <button class="btn btn-primary enter-btn d-flex justify-content-center" 
                            data-bs-toggle="tooltip" title="채팅방에 입장합니다!" data-enter="${room.teamCd}" data-task-no="${taskNo }">
                                <i class="bi bi-chat-dots me-2"></i> 입장
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

	<div class="text-end create">
	    <button id="createBtn" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createChatRoomModal">
		    <i class="bi bi-plus-square me-2"></i>등록
	    </button>
	</div>
</div>

<!-- 채팅방 생성 모달 -->
<div class="modal fade" id="createChatRoomModal" tabindex="-1" aria-labelledby="createChatRoomModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createChatRoomModalLabel">채팅방 생성</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="createChatRoomForm">
                    <div class="mb-3">
                        <label for="chatRoomName" class="form-label">채팅방 이름</label>
                        <input type="text" class="form-control" id="chatRoomName" name="chatRoomName" placeholder="채팅방 이름을 입력하세요" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="createChatRoomSubmit">생성</button>
            </div>
        </div>
    </div>
</div>





<script src="${pageContext.request.contextPath }/resources/js/projectChatRoom/projectChatRoom.js"></script>