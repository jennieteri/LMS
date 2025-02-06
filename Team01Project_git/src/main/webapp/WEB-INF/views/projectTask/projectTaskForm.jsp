<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<style>

  #form-table {
    width: 90%; /* 테이블의 너비를 전체 화면에 가까이 설정 */
    margin: 0 auto; /* 테이블을 화면 중앙에 배치 */
    border-collapse: collapse; /* 테이블 셀 경계 제거 */
  }

  .form-control {
    width: 100%; /* 입력 요소를 셀 너비에 맞춤 */
    font-size: 1.2rem; /* 입력 필드 크기를 키움 */
  }

 .btn{
 	margin-top: 10px;
 }
</style>


<form:form action="${pageContext.request.contextPath}/projectTask" id="createForm" method="post" enctype="multipart/form-data" modelAttribute="projectTask">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-lectno="${lectNo }">
		
		<tr>
			<th>과제주제</th>
			<td><form:input type="text" path="taskTitle" 
					cssClass="form-control" />
				<form:errors path="taskTitle" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>과제제출마감일</th>
			<td><form:input type="date" path="taskEt"
					cssClass="form-control"/>
				<form:errors path="taskEt" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>배점</th>
			<td><form:input type="number" path="taskScore"
					cssClass="form-control" />
				<form:errors path="taskScore" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="uploadFiles" multiple class="form-control"/>	
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<form:textarea cols="5" path="taskNotes" id="editor"/>
				<form:errors path="taskNotes" cssClass="text-danger" /></td>
				
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<button id="dataBtn" type="button" class="btn btn-success">발표</button>
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="${pageContext.request.contextPath }/lecture/${lectNo}/projectTask" class="btn btn-danger">취소</a>
		</td>
	</tr>
	</table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/projectTask/projectTaskForm.js" type="module"></script>