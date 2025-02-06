<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<input type="hidden" id="sibNo" value="${board.sibNo }">
<security:authentication property="principal.username" var="id"/>
<script type="importmap">
{
	"imports": {
		"ckeditor5": "${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.js",
		"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>
<style>
    /* 제목을 중앙 정렬하는 스타일 */
    th {
        text-align: center; /* 텍스트를 가로 중앙 정렬 */
        vertical-align: middle; /* 텍스트를 세로 중앙 정렬 */
    }

    /* 테이블 테두리 스타일 */
    .table {
        border: 1px solid #ddd; /* 테이블 전체에 테두리 */
    }



    /* 테이블 본문 셀 스타일 */
    .table td {
        border: 1px solid #ddd; /* 본문 셀에 테두리 */
    }



</style>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">Q&A</li>
    <li class="breadcrumb-item active" aria-current="page">문의글 작성</li>
  </ol>
</nav>
<form:form method="POST" enctype="application/x-www-form-urlencoded" modelAttribute="board" action="${pageContext.request.contextPath }/answer/createAnswerBoard">
	<table class="table table-primary">
		 
		<!-- 제목 -->
		<tr>
			<th style="text-align: center">제목</th>
	 		<td class="bg-light">
	 			<form:input type="text" path="sibTtl" cssClass="form-control" required="true" /> 
	 			<form:errors path="sibTtl" element="span" cssClass="text-danger" />
	 		</td>
		</tr>
	

	
		<!-- 내용 -->
		<tr>
		    <td colspan="2" class="bg-light">
		       <form:textarea path="sibCn" class="form-control" id="editor" placeholder="내용 입력하세요" style="height: 500px;" />
		        
		    </td>
		</tr> 
		
		<!-- 버튼 -->
		<tr>
			<td colspan="2" align="right">
				<input type="submit" class="btn btn-primary" value="등록" />
				<a href="<c:url value='/answer'/>" class="btn btn-primary">취소</a> 
			</td>
		</tr>
		
	</table>
</form:form>
<script src="${pageContext.request.contextPath }/resources/js/board/ck.js"></script>