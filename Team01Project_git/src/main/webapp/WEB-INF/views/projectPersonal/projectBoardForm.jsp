<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>
<h2>프로젝트게시판 폼</h2>

<form:form action="${pageContext.request.contextPath}/lecture/${lectNo }/projectBoard" id="createForm" method="post" enctype="multipart/form-data" modelAttribute="projectBoard">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-lectno="${lectNo }" data-teamcd="${teamCd }">
		<tr>
			<th>제목</th>
			<td><form:input type="text" path="pbTitle" cssClass="form-control" />
				<form:errors path="pbTitle" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
			<input type="file" name="uploadFiles" multiple class="form-control"/>
			</td>
		</tr>
		<tr>
			<th>공지여부</th>
			<td>
			<form:radiobutton path="pbNoti" cssClass="form-radio" value="Y" label="설정"/>
			<form:radiobutton path="pbNoti" cssClass="form-radio" value="N" label="미설정"/>
			<form:errors path="pbNoti" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<form:textarea path="pbCn" id="editor"/>
				<form:errors path="pbCn" cssClass="text-danger" />
			</td>
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<input type="hidden" name="teamCd" value="${teamCd}"/>
			<!-- 현재 로그인한 사용자의 ID -->
			<input type="hidden" name="stuId" value="${pageContext.request.userPrincipal.name }"/>
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="<c:url value='/lecture/${lectNo}/projectBoard/${teamCd }' />" class="btn btn-danger">취소</a>
		</td>
	</tr>
	</table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/useckeditor5/editor.js" type="module"></script>
<script src="${pageContext.request.contextPath }/resources/js/projectPersonal/projectBoardForm.js"></script>
