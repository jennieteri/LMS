<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<div class="input-group mb-3 float-end search-area" data-pg-target="#searchform" data-pg-fn-name="fnPaging" style="width: 350px;">
	<form:select path="condition.searchType" cssClass="form-select">
		<form:option value="" label="전체"/>
		<form:option value="title" label="제목"/>
		<form:option value="writer" label="작성자"/>
		<form:option value="content" label="내용"/>
	</form:select>
	<form:input path="condition.searchWord" cssClass="form-control" style="width: 150px;"/>
	<button id="search-btn" class="btn btn-primary">검색</button>
</div>
<table class="table table-primary align-middle table-bordered table-hover">
	<thead>
		<tr class="text-center align-middle">
			<th style="width: 50px;">번호</th>
			<th>제목</th>
			<th style="width: 100px;">작성자</th>
			<th style="width: 110px;">날짜</th>
			<th style="width: 50px;">조회</th>
			<th style="width: 110px;">답변여부</th>
		</tr>
	</thead>
	<tbody class="table-group-divider">
		<c:if test="${not empty libList }">
			<c:forEach items="${libList }" var="lib">
				<tr class="table-light">
					<td class="text-center">${lib.sn }</td>
					<td style="max-width: 200px;" class="text-truncate">
						<a href='<c:url value="/lecture/${lecture.lectNo }/inquiry/${lib.libNo }"/>'>${lib.libSj }</a>
					</td>
					<td class="text-center">${lib.studentVO.nm }</td>
					<td class="text-center">${fn:split(lib.libDt, 'T')[0] }</td>
					<td class="text-center">${lib.libHit }</td>
					<td class="text-center">${empty lib.libAnsDt ? '답변대기중' : '답변완료' }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty libList }">
			<tr class="table-light">
				<td colspan="6" class="text-center">등록된 문의글이 없습니다</td>
			</tr>
		</c:if>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="6">
				<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
					<div class="mx-auto">
						${pagingHTML}
					</div>
					<security:authorize access="hasRole('STUDENT')">
						<div>
							<a class="btn btn-primary" style="position: absolute; right: 0px; top: 0px;" href='<c:url value="/lecture/${lecture.lectNo }/inquiry/new"/>'>등록</a>
						</div>
					</security:authorize>
				</div>			
			</td>
		</tr>
	</tfoot>
</table>
<form:form id="searchform" method="get" modelAttribute="condition" style="display: none;">
	<form:input path="searchType"/>
	<form:input path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>
<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
