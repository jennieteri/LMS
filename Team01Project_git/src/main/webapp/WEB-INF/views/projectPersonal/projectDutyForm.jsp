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

<form:form action="${pageContext.request.contextPath}/lecture/${lectNo }/projectDuty" id="createForm" method="post" modelAttribute="projectDuty">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }">
		<tr>
			<th>제목</th>
			<td>
				<form:input type="text" path="dutyTitle" cssClass="form-control"/>
				<form:errors path="dutyTitle" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>담당자</th>
			<td>
			<form:select path="dutyId">
				<form:options items="${projectMemberList }" itemLabel="nm" itemValue="stuId"/>
			</form:select>
			<form:errors path="dutyId" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>일감진척도</th>
			<td>
				<form:select path="dutyPrgsRtprgs">
					<form:option selected="selected" value="0">0 %</form:option>
					<form:option  value="10">10 %</form:option>
					<form:option  value="20">20 %</form:option>
					<form:option  value="30">30 %</form:option>
					<form:option  value="40">40 %</form:option>
					<form:option  value="50">50 %</form:option>
					<form:option  value="60">60 %</form:option>
					<form:option  value="70">70 %</form:option>
					<form:option  value="80">80 %</form:option>
					<form:option  value="90">90 %</form:option>
					<form:option  value="100">100 %</form:option>
				</form:select>
				<form:errors path="dutyPrgsRtprgs" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<form:textarea path="dutyCn" id="editor"/>
				<form:errors path="dutyCn" cssClass="text-danger" />
			</td>
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<input type="hidden" name="dutyTeam" value="${projectMember.teamCd }" >
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="<c:url value='/lecture/${lectNo}/projectDuty/${projectMember.teamCd }' />" class="btn btn-danger">취소</a>
		</td>
	</tr>
	</table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/useckeditor5/editor.js" type="module"></script>