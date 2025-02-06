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
		<tr class="text-center">
			<th>번호</th>
			<th>제목</th>
			<th>담당자</th>
			<th>수정일시</th>
			<th>상태</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty projectDutyList }">
			<c:forEach items="${projectDutyList }" var="duty">
				<tr>
					<td class="text-center">${duty.rnum }</td>
					
					<td>
						<a href="<c:url value='get/${duty.dutyNo }'/>">${duty.dutyTitle }</a>
					</td>
					
					<td class="text-center">${duty.projectMember.nm }</td>
					
					<td class="text-center">${duty.dutyDate }</td>
					<c:choose>
					<c:when test="${duty.dutyPrgsRtprgs eq 100 }">
					<td class="text-center">완료</td>
					</c:when>
					<c:otherwise>
					<td class="text-center">진행중</td>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty projectDutyList }">
			<tr class="text-center">
				<td colspan="5">등록된 일감 없음.</td>
			</tr>
		</c:if>
	</tbody>
	<tfoot>
		<!-- 검색 페이징 area -->
		<tr class="text-center pt-3">
			<td colspan="5">
				<div class="d-flex justify-content-between align-items-center">
					<div class="paging-area mx-auto">
						${pagingHTML }
					</div>
						
					<div>
						<a href="<c:url value='new/${projectMember.teamCd }' />" class="btn btn-primary">등록</a>
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