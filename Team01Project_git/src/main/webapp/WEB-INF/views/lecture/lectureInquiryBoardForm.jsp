<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<input id="cp" type="hidden" value="${pageContext.request.contextPath }"/>
<input id="lectNo" type="hidden" value="${lecture.lectNo }"/>
<security:authentication property="principal.username" var="id"/>
<form method="post" id="insertForm" name="insertForm">
	<input name="stuId" type="hidden" value="${id }">
	<table id="form-table" class="table table-primary align-middle table-bordered">
		<tr>
			<th class="text-center" style="width: 100px;">제목</th>
			<td class="table-light">
				<input name="libSj" class="form-control" placeholder="제목을 입력하세요"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="table-light">
				<textarea name="libCn" class="form-control ckEditor" placeholder="문의 내용을 입력하세요"></textarea>
			</td>
		</tr>
	</table>
	<a class="btn btn-secondary float-end ms-2" href="${pageContext.request.contextPath }/lecture/${lecture.lectNo }/inquiry">취소</a>
	<button type="submit" class="btn btn-primary float-end">저장</button>
</form>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureInquiryBoardForm.js"></script>