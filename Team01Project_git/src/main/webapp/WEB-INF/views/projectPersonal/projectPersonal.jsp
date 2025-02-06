<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

<style>
        .progress-container {
            margin-top: 20px;
        }

        .team-members {
            list-style-type: none;
            padding: 0;
            margin: 0;
            max-height: 400px;
            overflow-y: auto;
        }

        .team-members li {
            padding: 5px 0;
            border-bottom: 1px solid #ddd;
            display: flex;
            align-items: center;
        }

        .team-members li i {
            color: gold;
        }

        .announcement-section {
            margin-top: 20px;
        }

        .progress-bar-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .announcement-section .card {
            min-height: 150px;
        }
</style>
    <!-- Header Navigation -->
<div id="mainArea" class="container" data-path="${pageContext.request.contextPath }" data-lectno="${lectNo }" data-teamcd="${projectMember.projectTeam.teamCd }" data-leadyn="${projectMember.leadYn }">
    <nav class="py-2 bg-primary border-bottom">
        <div class="container d-flex flex-wrap">
            <ul class="nav me-auto">
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white " href="${pageContext.request.contextPath }/lecture/${lectNo }/projectPersonal">홈</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectDuty/${projectMember.projectTeam.teamCd}">일감</a>
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
    <div class="row pt-3">
        <!-- Left Section -->
        <div class="col-md-6">
            <div class="card m-0" style="height: 100%;">
            	<div class="card-header fw-bold">
                    나의 프로젝트과제
                </div>
                <div class="card-body pt-3">
                    
                    <p class="card-text">주제: ${projectMember.projectTeam.teamPurpo }</p>
                    <p class="card-text">${projectMember.projectTeam.teamNotes }</p>

                    <div class="progress-container">
                        <label for="progressRange" class="form-label">프로젝트 진척도</label>
                        <div class="progress-bar-container">
                            <input type="range" id="progressRange" class="form-range" min="0" max="100" step="10" value="${projectMember.projectTeam.teamProge }">
                            <span id="progressPercentage">${projectMember.projectTeam.teamProge }%</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Right Section -->
        <div class="col-md-6">
            <div class="card m-0" style="height: 100%;">
                <div class="card-header fw-bold">
                    ${projectMember.projectTeam.teamNm } 팀 구성원
                    <c:if test="${projectMember.leadYn eq 'Y' }">
                    <button type="button" class="btn btn-info btn-outline-secondary ms-3" data-bs-toggle="modal" data-bs-target="#editTeamNameModal" id="editTeamNameBtn">
				        수정
				    </button>
                    </c:if>
                </div>
                <div class="card-body pt-3">
                    
                    <ul class="team-members">
                        <c:forEach items="${projectMemberList }" var="projectMemberVO">
                            <c:choose>
                                <c:when test="${projectMemberVO.leadYn eq 'Y' }">
                                    <li>${projectMemberVO.nm }(${projectMemberVO.stuId })<i class="fas fa-star"></i></li>
                                </c:when>
                                <c:when test="${projectMemberVO.leadYn eq 'N' or empty projectMemberVO.leadYn and projectMemberVO.stuId eq pageContext.request.userPrincipal.name }">
                                	<li>${projectMemberVO.nm }(${projectMemberVO.stuId })<i class="bi bi-arrow-left-short"></i></li>
                                </c:when>
                                <c:otherwise>
                                    <li>${projectMemberVO.nm }(${projectMemberVO.stuId })</li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- Announcement Section -->
    <div class="announcement-section">
        <h5 class="pd-4"><i class="bi bi-megaphone"></i> &lt; 공지사항 > </h5>
        <div class="card">
            <div class="card-body">
            <c:if test="${empty projectBoard }">
            	<br>
            	<p>설정된 공지가 없습니다.</p>
            </c:if>
                <h6 class="card-title">${projectBoard.pbTitle }</h6>
                ${projectBoard.pbCn }
            </div>
        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="editTeamNameModal" tabindex="-1" aria-labelledby="editTeamNameModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editTeamNameModalLabel">팀명 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editTeamNameForm">
                    <div class="mb-3">
                        <label for="newTeamName" class="form-label">새 팀명(10자이내)</label>
                        <input type="text" class="form-control" id="newTeamName" value="${projectMember.projectTeam.teamNm}" required>
                    	<small id="charLimitWarning" class="text-danger d-none">10자를 초과했습니다!</small>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveTeamNameBtn">저장</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>



<script src="${pageContext.request.contextPath }/resources/js/projectPersonal/projectPersonal.js"></script>


