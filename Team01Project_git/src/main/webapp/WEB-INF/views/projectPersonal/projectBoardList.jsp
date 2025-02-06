<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>

.search-btn:hover {
    background-color: #0056b3;
}

/* 공지사항 스타일 */
.noti {
    font-weight: bold;
    background-color: #d4edda;
    color: #155724;
}

/* 테이블 스타일 */
.table-hover tbody tr:hover {
    background-color: #f1f1f1;
}

.table-primary {
    background-color: #007bff;
    color: white;
}

.paging-area {
    margin: 10px 0;
}

/* 게시글 등록 버튼 */
.text-end .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    padding: 8px 16px;
    border-radius: 5px;
}

.text-end .btn-primary:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}
</style>

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
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectChatRoom/${projectMember.projectTeam.taskNo}">채팅방</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectTaskSubmission/${projectMember.projectTeam.teamCd}">제출관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link-dark px-3 fw-bold fs-5 active text-white" href="${pageContext.request.contextPath }/lecture/${lectNo }/projectBoard/${teamCd}">게시판</a>
                </li>
            </ul>
        </div>
    </nav>

	<div id="search-box" class="search-area text-end pt-3" data-pg-target="#searchForm" data-pg-fn-name="fnPaging">
		<div class="input-group float-end mb-2" style="width: 400px;">
			<form:select path="condition.searchType" cssClass="form-select" cssStyle="width: 80px;">
				<form:option value="" label="전체" />
				<form:option value="title" label="제목" />
				<form:option value="content" label="내용" />
				<form:option value="writer" label="작성자" />
			</form:select>
			<form:input path="condition.searchWord" placeholder="검색어를 입력하세요" cssClass="form-control" cssStyle="width: 200px;"/>
			<button class="btn btn-primary">검색</button>
		</div>
	</div>

<table class="table table-hover">
	<thead class="table-primary">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일자</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<!-- 공지 게시글 상단 고정 -->
		<c:forEach items="${projectBoardList }" var="projectBoard">
			<c:if test="${projectBoard.pbNoti eq 'Y' }">
				<tr class="noti">
					<td><i class="bi bi-megaphone"></i></td>
					<td>
						[공지]<a href="<c:url value='${teamCd }/${projectBoard.pbNo }'/>">${projectBoard.pbTitle }</a>
					</td>
					<td>${projectBoard.projectMember.nm }</td>
					<td>${projectBoard.pbDate }</td>
					<td>${projectBoard.pbHit }</td>
				</tr>
			</c:if>
		</c:forEach>
		
		<!-- 일반 게시글 -->
		<c:forEach items="${projectBoardList }" var="projectBoard">
			<c:if test="${projectBoard.pbNoti eq 'N' }">
				<tr>
					<td>${projectBoard.rnum }</td>
					<td>
						<a href="<c:url value='${teamCd }/${projectBoard.pbNo }'/>">${projectBoard.pbTitle }</a>
					</td>
					<td>${projectBoard.projectMember.nm }</td>
					<td>${projectBoard.pbDate }</td>
					<td>${projectBoard.pbHit }</td>
				</tr>
			</c:if>
		</c:forEach>
		<!-- 게시글이 없을 경우 -->
		<c:if test="${empty projectBoardList }">
			<tr class="text-center">
				<td colspan="5">등록된 게시글 없음.</td>
			</tr>
		</c:if>
	</tbody>
	<tfoot>
		<!-- 페이징 -->
		<tr class="text-center">
		    <td colspan="5">
		        <div class="d-flex justify-content-between align-items-center">
		            <!-- 페이징 영역: 가운데 정렬 -->
		            <div class="paging-area mx-auto">
		                ${pagingHTML}
		            </div>
		
		            <!-- 등록 버튼: 오른쪽 정렬 -->
		            <div>
		                <a href="<c:url value='new/${teamCd }' />" class="btn btn-primary">등록</a>
		            </div>
		        </div>
		    </td>
		</tr>
	</tfoot>
</table>

<form:form id="searchForm" method="get" modelAttribute="condition">
	<form:input type="hidden" path="searchType"/>
	<form:input type="hidden" path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>



<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>