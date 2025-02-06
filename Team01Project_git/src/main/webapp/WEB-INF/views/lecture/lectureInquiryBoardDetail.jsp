<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
		"ckeditor5": "${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.js",
		"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>
<security:authentication property="principal.username" var="id"/>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<input type="hidden" id="lectNo" value="${lecture.lectNo }">
<input type="hidden" id="libNo" value="${lib.libNo }">
<input type="hidden" id="id" value="${id }">

<table class="table table-primary align-middle table-bordered">
	<tr>
		<th class="text-center">제목</th>
		<td class="table-light libSjText" colspan="3">${lib.libSj }</td>
		<td class="table-light libSjInput" colspan="3" style="display: none;">
			<input class="form-control" value="${lib.libSj }" id="libSj" placeholder="제목을 입력하세요"/>
		</td>
	</tr>
	<tr>
		<th class="text-center" style="width: 100px;">글번호</th>
		<td class="table-light" style="width: 200px;">${lib.libNo }</td>
		<th class="text-center" style="width: 100px;">작성일시</th>
		<td class="table-light" style="width: 200px;">${fn:split(lib.libDt, 'T')[0] } ${fn:split(lib.libDt, 'T')[1] }</td>
	</tr>
	<tr>
		<th class="text-center">작성자</th>
		<td class="table-light">${lib.studentVO.nm }</td>
		<th class="text-center">조회수</th>
		<td class="table-light">${lib.libHit }</td>
	</tr>
	<tr>
		<td class="table-light align-top libCnText" colspan="4" style="height: 200px;">${lib.libCn }</td>
		<td class="table-light align-top libCnInput" colspan="4" style="height: 200px; display: none;">
			<textarea class="form-control" id="libEditor" placeholder="문의 내용을 입력하세요"></textarea>
		</td>
	</tr>
</table>
<c:if test="${not empty lib.libAnsDt }">
	<table class="table table-primary align-middle table-bordered mb-2">
		<tr>
			<th class="text-center" style="width: 100px;">답변자</th>
			<td class="table-light" style="width: 200px;">${lib.professorVO.nm } 교수님</td>
			<th class="text-center" style="width: 100px;">답변일시</th>
			<td class="table-light" style="width: 200px;">${fn:split(lib.libAnsDt, 'T')[0] } ${fn:split(lib.libAnsDt, 'T')[1] }</td>
		</tr>
		<tr>
			<td class="table-light align-top dispAnswer" colspan="4" style="height: 200px;">${lib.libAnsCn }</td>
			<td class="table-light textarea" colspan="4" style="display: none;">
				<textarea class="form-control" id="editor" placeholder="답변을 입력하세요"></textarea>
			</td>
		</tr>
	</table>
	<div id="delUpBtn">
		<a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/lecture/${lecture.lectNo }/inquiry">목록</a>
		<c:if test="${id eq lib.profeId }">
			<button class="btn btn-danger float-end ms-2 ansDeleteBtn">삭제</button>
			<button class="btn btn-info float-end ansUpdateBtn">수정</button>
		</c:if>
	</div>
	<div id="saveCancelBtn" style="display: none;">
		<button class="btn btn-secondary float-end ms-2 cancleBtn mb-3">취소</button>
		<button class="btn btn-primary float-end ansSaveBtn">저장</button>
	</div>
</c:if>
<c:if test="${empty lib.libAnsDt }">
	<div class="ansZone">
		<h3 class="text-center">등록된 답변이 없습니다</h3>
		<div id="libDelUpBtn">
			<a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/lecture/${lecture.lectNo }/inquiry">목록</a>
			<security:authorize access="hasRole('PROFESSOR')">
				<button class="btn btn-primary float-end ms-2 ansInsertBtn">등록</button>
			</security:authorize>
			<c:if test="${id eq lib.stuId }">
				<button class="btn btn-danger float-end ms-2 libDeleteBtn">삭제</button>
				<button class="btn btn-info float-end libUpdateBtn">수정</button>
			</c:if>
		</div>
		<div id="libSaveCancelBtn" style="display: none;">
			<button class="btn btn-secondary float-end ms-2 libCancleBtn">취소</button>
			<button class="btn btn-primary float-end libSaveBtn">저장</button>
		</div>
	</div>
	<div id="ansInsertDiv" style="display: none;">
		<table id="form-table" class="table table-primary align-middle table-bordered">
			<tr>
				<th class="text-center" style="width: 100px;">답변</th>
				<td class="table-light textarea">
					<textarea class="form-control" id="editor" placeholder="답변을 입력하세요"></textarea>
				</td>
			</tr>
		</table>
		<button class="btn btn-secondary float-end ms-2 ansCancelBtn">취소</button>
		<button class="btn btn-primary float-end mb-3 ansSaveBtn">저장</button>
	</div>
</c:if>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureInquiryBoardDetail.js" type="module"></script>
