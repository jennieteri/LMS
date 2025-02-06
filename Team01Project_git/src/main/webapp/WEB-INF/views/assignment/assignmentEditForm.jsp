<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<form:form action="${pageContext.request.contextPath}/lecture/${lectNo}/assignment/edit" id="editForm" method="post" enctype="multipart/form-data" modelAttribute="assignment">
	<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-lect-no="${lectNo }">
		<tr>
			<th>강의명</th>
			<td>
				<form:input path="lectNm" cssClass="form-control" readonly="true"/>
				<form:errors path="lectNm" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>과제명</th>
			<td>
				<form:input path="assigNm" cssClass="form-control" required="true"/>
				<form:errors path="assigNm" cssClass="text-danger" />
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<form:textarea path="assigNotes" id="editor"/>
				<form:errors path="assigNotes" cssClass="text-danger" />
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
			<td>
				<input class="form-control" type="date" name="assigEd" value="${fn:substring(assignment.assigEd, 0, 4)}-${fn:substring(assignment.assigEd, 4, 6)}-${fn:substring(assignment.assigEd, 6, 8)}">
			</td>
		</tr>
		
		<tr>
			<th>기존파일</th>
			<td>
				<c:forEach items="${assignment.atchFile.fileDetails }" var="fd" varStatus="vs">
					<span>
						${fd.orignlFileNm }[${fd.fileFancysize }]
						<a data-atch-file-id="${fd.atchFileId }" data-file-sn="${fd.fileSn }" class="btn btn-danger btn-sm" href="javascript:;">
							X						
						</a>
							${not vs.last ? '|' : ''}
						</span>
				</c:forEach>		
				<c:if test="${empty assignment.atchFile.fileDetails}">
				    기존첨부파일없음
				</c:if>
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
			<form:select path="peerYn" cssClass="form-select" required="true">
				<form:option value="N" label="해당없음" />
				<form:option value="Y" label="해당" />
			</form:select>
			<form:errors path="peerYn" cssClass="text-danger" />
			</td>
		</tr>
		<tr class="text-end">
		<td colspan="2">
			<form:input type="hidden" path="assigNo"/>
			<form:input type="hidden" path="atchFileId"/>
			<form:input type="hidden" path="assigDate"/>
			<button type="submit" class="btn btn-primary">저장</button>
			<button type="reset" class="btn btn-danger">취소</button>
		</td>
	</tr>
	</table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/assignment/assignmentForm.js" type="module"></script>