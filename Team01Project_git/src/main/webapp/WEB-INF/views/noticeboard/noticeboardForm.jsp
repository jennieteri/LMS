<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%
    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    request.setAttribute("currentYear", currentYear);
%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active">학사일정</li>
    <li class="breadcrumb-item active" aria-current="page">등록</li>
  </ol>
</nav>

<form:form action="${pageContext.request.contextPath}/noticeboard" id="createForm" method="post" enctype="multipart/form-data" modelAttribute="noticeboard">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }">
		<tr>
			<th>제목</th>
			<td><form:input type="text" path="ntcNm" 
					cssClass="form-control" />
				<form:errors path="ntcNm" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>일정시작일</th>
			<td><form:input type="date" path="ntcDt"
					cssClass="form-control"/>
				<form:errors path="ntcDt" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>일정종료일</th>
			<td><form:input type="date" path="ntcEt"
					cssClass="form-control" />
				<form:errors path="ntcEt" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="uploadFiles" multiple class="form-control"/>	
			</td>
		</tr>
		<tr>
			<th>주요일정여부</th>
			<td>
			<form:radiobutton path="ntcYn" cssClass="form-radio" value="Y" label="해당"/>
			<form:radiobutton path="ntcYn" cssClass="form-radio" value="N" label="해당없음"/>
			<form:errors path="ntcYn" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>주요일정분류</th>
			<td>
				<form:select path="cocoCd">
					<form:option value="">해당없음</form:option>
					<form:option value="NB01">정규등록</form:option>
					<form:option value="NB02">추가등록</form:option>
					<form:option value="NB03">강의등록신청</form:option>
					<form:option value="NB04">예비수강신청</form:option>
					<form:option value="NB05">본수강신청</form:option>
					<form:option value="NB06">중간고사</form:option>
					<form:option value="NB07">기말고사</form:option>
					<form:option value="NB08">성적확인/이의신청</form:option>
				</form:select>
			</td>
		</tr>
		<tr>
			<th>학기분류</th>
			<td>
				<form:select path="semstrNo" id="semstrSelect">
					<form:option value="">해당없음</form:option>
					<form:option value="${currentYear}01">1학기</form:option>
					<form:option value="${currentYear}02">2학기</form:option>
				</form:select>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<form:textarea path="ntcDesc" id="editor"/>
				<form:errors path="ntcDesc" cssClass="text-danger" />
			</td>
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<!-- 현재 로그인한 사용자의 ID -->
			<input type="hidden" name="prsId" value="${pageContext.request.userPrincipal.name }"/>
			<button id="dataBtn" type="button" class="btn btn-success">발표</button>
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="<c:url value='/noticeboard' />" class="btn btn-danger">목록</a>
		</td>
	</tr>
	</table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/noticeBoard/noticeBoardForm.js" type="module"></script>