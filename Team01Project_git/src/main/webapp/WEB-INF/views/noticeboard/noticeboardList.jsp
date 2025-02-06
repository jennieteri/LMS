<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active">학사일정</li>
  </ol>
</nav>

<button class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#semesterModal">학기변경</button>
<!-- 캘린더 아이콘 -->
<button id="calendarIcon" class="btn btn-outline-secondary ms-2 mb-2 float-end" data-path="${pageContext.request.contextPath }">
    <i class="bi bi-calendar-check"></i>
</button>
<div id="search-box" class="search-area text-end" data-pg-target="#searchForm" data-pg-fn-name="fnPaging">
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
	<thead class="table-primary text-center">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>시작일자</th>
			<th>종료일자</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty boardList }">
			<c:forEach items="${boardList }" var="board">
				<tr>
					<td class="text-center">${board.rnum }</td>
					<td>
						<!-- a태그 안 url 수정해야함 -->
						<a href="<c:url value='/noticeboard/${board.ntcCd }'/>">${board.ntcNm }</a>
					</td>
					<td class="text-center">${board.ntcDt }</td>
					<td class="text-center">${board.ntcEt }</td>
					<td class="text-center">${board.ntcInqCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty boardList }">
			<tr>
				<td colspan="5">글 없음.</td>
			</tr>
		</c:if>
	</tbody>
	<tfoot>
		<!-- 검색 페이징 area -->
		<tr align="center">
			<td colspan="5">
				<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
					<div class="mx-auto">
						${pagingHTML}
					</div>
					<!-- (교직원)등록가능 -->
					<security:authorize access="hasRole('EMPLOYEE')">
						<div style="position: absolute; right: 0px; top: 0px;">
							<a href="<c:url value='/noticeboard/new'/>" class="btn btn-primary">등록</a>
						</div>
					</security:authorize>
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

<!-- 캘린더 모달 -->
<div id="calendarModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">전체 일정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="fullCalendar"></div>
            </div>
        </div>
    </div>
</div>

<div id="semesterModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">진행 학기 변경</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<c:forEach items="${semesterList }" var="semester">
	            	<input type="radio" class="btn-check" name="options-base" id="${semester.semstrNo }" autocomplete="off" ${semester.semstrYn eq 'Y' ? 'checked' : ''}>
					<label class="btn" for="${semester.semstrNo }">${semester.label }</label>
            	</c:forEach>
            </div>
            <div class="modal-footer">
				<button type="button" class="btn btn-success">추가</button>
				<button type="button" class="btn btn-primary">저장</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
        </div>
    </div>
</div>



<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/noticeBoard/noticeBoardCalendar.js"></script>