<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>
/* 전체 페이지 기본 설정 */
body {
    font-family: 'Arial', sans-serif;
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
				
<table id="contextTable" class="table table-hover"  data-path="${pageContext.request.contextPath }">
	<thead>
		<tr class="table-primary text-center">
			<!-- 과제번호는 rownum으로 처리 -->
			<th>번호</th>
			<!--강의번호를 가지고 강의명 추출-->
			<th>과제명</th>
			<th>배점</th>
			<th>등록일자</th>
			<th>제출마감일</th>
		</tr>
	</thead>
	<tbody class="table-group-divider">
		<c:if test="${not empty assignmentList }">
			<c:forEach items="${assignmentList }" var="assignment">
				<tr class="table-light">
					<td class="fw-bold text-center">${assignment.rnum }</td>
					<td class="fw-bold"><a href="<c:url value='/lecture/${lectNo}/assignment/${assignment.assigNo }'/>">${assignment.assigNm }</a></td>
					<td class="text-center">${assignment.assigScore}점</td>
					<td class="text-center">${fn:substring(assignment.assigDate, 0, 4)}-${fn:substring(assignment.assigDate, 4, 6)}-${fn:substring(assignment.assigDate, 6, 8)}</td>
					<td class="text-center">${fn:substring(assignment.assigEd, 0, 4)}-${fn:substring(assignment.assigEd, 4, 6)}-${fn:substring(assignment.assigEd, 6, 8)}</td>
				</tr>
				
			</c:forEach>
		</c:if>
		<c:if test="${empty assignmentList }">
			<tr class="text-center">
				<td colspan="9">해당강의 등록된 과제 없음.</td>
			</tr>
		</c:if>
	</tbody>
	
	
	<tfoot>
		 <!-- 페이징 area -->
	    <tr class="text-center">
	        <td colspan="9">
	            <div class="d-flex justify-content-between align-items-center">
	                <!-- 페이징 -->
	                <div class="paging-area mx-auto">
	                    ${pagingHTML}
	                </div>
	                <!-- 등록버튼 -->
	                <security:authorize access="hasRole('PROFESSOR')">
	                    <a href="<c:url value='/lecture/${lectNo}/assignment/new'/>" class="btn btn-primary">
	                        <i class="bi bi-plus-square me-2"></i>등록
	                    </a>
	                </security:authorize>
	            </div>
	        </td>
	    </tr>
	</tfoot>
</table>



<!-- 검색처리 -->
<form:form id="searchForm" method="get" modelAttribute="condition">
	<form:input type="hidden" path="searchType"/>
	<form:input type="hidden" path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>

<!-- MODAL -->






<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/projectBoard/projectBoardList.js"></script>