<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<form:form action="${pageContext.request.contextPath}/lecture/${lectNo}/assignment" id="createForm" method="post" enctype="multipart/form-data" modelAttribute="assignment">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-lect-no="${lectNo }">
		
		<tr>
			<th>과제명</th>
			<td>
				<form:input path="assigNm" cssClass="form-control" required="true"/>
				<form:errors path="assigNm" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>배점</th>
			<td>
				<form:input type="number" path="assigScore" cssClass="form-control" required="true" min="1" max="100"/>
				<form:errors path="assigScore" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>제출마감일</th>
			<td><form:input type="date" path="assigEd"
					cssClass="form-control" required="true" />
				<form:errors path="assigEd" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="uploadFiles" multiple class="form-control"/>	
			</td>
		</tr>
		<tr>
			<th>피어리뷰여부</th>
			<td>
				<form:radiobutton path="peerYn" cssClass="form-radio" value="Y" label="해당"/>
				<form:radiobutton path="peerYn" cssClass="form-radio" value="N" label="해당없음"/>
				<form:errors path="peerYn" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<form:textarea path="assigNotes" id="editor"/>
				<form:errors path="assigNotes" cssClass="text-danger" />
			</td>
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<!-- 등록일 -->
			<form:input type="hidden" path="assigDate"/>
			<button id="dataBtn" type="button" class="btn btn-success">발표</button>
			<button type="submit" class="btn btn-primary">저장</button>
			<button type="reset" class="btn btn-danger">취소</button>
		</td>
	</tr>
	</table>
</form:form>


<script src="${pageContext.request.contextPath }/resources/js/assignment/assignmentForm.js" type="module"></script>