<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/ resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
<input type="hidden" value="${lectNo}" id="lectNo">

<div class="input-group mb-3 float-end search-area" data-pg-target="#searchform" data-pg-fn-name="fnPaging" style="width: 350px;">
	<form:select path="condition.searchType" cssClass="form-select">
		<form:option value="" label="전체"/>
		<form:option value="title" label="게시물명"/>
		<form:option value="writer" label="작성자"/>
		<form:option value="content" label="내용"/>
	</form:select>
	<form:input path="condition.searchWord" cssClass="form-control" style="width: 150px;"/>
	<button id="search-btn" class	="btn btn-primary">검색</button>
</div>	

<div>
	<table class="table" id="lectBoardTable">
		<thead>
			<tr>
				<th class="flex-fill p-2 text-center table-primary text-black">번호</th>
				<th class="flex-fill p-2 text-center table-primary text-black">게시물명</th>
				<th class="flex-fill p-2 text-center table-primary text-black">작성자</th>
				<th class="flex-fill p-2 text-center table-primary text-black">게시일자</th>
				<th class="flex-fill p-2 text-center table-primary text-black">조회수</th>
			</tr>
			<!-- 고정 메인 공지사항 -->
			<c:forEach items="${selectLectureNoticeBoardMainList }" var="lectBoard" varStatus="status">
				<c:if test="${lectBoard.cnbMainYn == 'Y'}">
					<tr class="main-notice-row">
						<td class="flex-fill p-2 text-center">${fn:split(lectBoard.cnbNo, 'CNB')[0].replaceFirst("^0+", "") }</td>
						<td><a href="${pageContext.request.contextPath}/lecture/${lectNo}/board/detail/${lectBoard.cnbNo}">${lectBoard.cnbNm}</a></td>
						<td class="flex-fill p-2 text-center">${lectBoard.professorVO.nm }</td>
						<td class="realTime p-2 text-center" data-cnb-dt="${lectBoard.cnbDt}">${fn:split(lectBoard.cnbDt, 'T')[0] }</td>
<%-- 						<td class="realTime p-2 text-center" data-cnb-dt="${lectBoard.cnbDt}">${fn:split(lectBoard.cnbDt, 'T')[0] } ${fn:split(lectBoard.cnbDt, 'T')[1] }</td> --%>
						<td class="flex-fill p-2 text-center">${lectBoard.cnbInq }</td>
					</tr>
				</c:if>
			</c:forEach>
		</thead>
		<!-- 일반 공지사항 -->
		<tbody class="flex-fill p-2" >
			<c:forEach items="${selectNoticeBoardList }" var="lectBoard" varStatus="status">
				<tr class="normal-notice-row">
					<td class="flex-fill p-2 text-center">${fn:split(lectBoard.cnbNo, 'CNB')[0].replaceFirst("^0+", "") }</td>
					<td><a href="${pageContext.request.contextPath}/lecture/${lectNo}/board/detail/${lectBoard.cnbNo}">${lectBoard.cnbNm}</a></td>
					<td class="flex-fill p-2 text-center">${lectBoard.professorVO.nm }</td>
					<td class="realTime text-center" data-cnb-dt="${lectBoard.cnbDt}">${fn:split(lectBoard.cnbDt, 'T')[0] }</td>
					<td class="flex-fill p-2 text-center">${lectBoard.cnbInq }</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot class="table table-bordered align-middle table-bordered" >
			<tr>
				<td colspan="5">
					<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
						<div class="mx-auto">
							${pagingHTML}
						</div>
							<security:authorize access="hasRole('PROFESSOR')">
								<form action="${pageContext.request.contextPath}/lecture/${lectNo}/board/new" method="post">
							    		<button type="submit" class="btn btn-primary" style="float: right;">등록</button>
								</form>
				    		</security:authorize>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>

<form:form id="searchform" method="get" modelAttribute="condition" style="display: none;">
	<form:input path="searchType"/>
	<form:input path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>
<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureNoticeBoard.js" type="module"></script>
