<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
    <li class="breadcrumb-item">시스템 공지사항</li>
    <li class="breadcrumb-item active" aria-current="page">공지사항 작성</li>
  </ol>
</nav>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="systemBoard" action="${pageContext.request.contextPath}/systemBoard/createSystemBoard" id="form-table" data-path="${pageContext.request.contextPath}">
    <table class="table table-primary">
        <tr>
            <th style="text-align: center">제목</th>
            <td class="bg-light">
                <form:input type="text" path="snbTtl" cssClass="form-control" required="true" />
                <form:errors path="snbTtl" element="span" cssClass="text-danger" />
            </td>
        </tr>
        <tr>
            <th style="text-align: center">작성자</th>
            <td class="bg-light">
                <form:input path="adminId" cssClass="form-control" required="true" />
                <form:errors path="adminId" element="span" cssClass="text-danger" />
            </td>
        </tr>
        <tr>
            <th style="text-align: center">파일</th>
            <td class="bg-light"><input type="file" name="uploadFiles" multiple class="form-control" /></td>
        </tr>
        <tr>
           
            <td colspan="2" class="bg-light">
               <form:textarea path="snbCn" id="editor" placeholder="내용을 입력하세요" class="form-control" style="height: 150px;"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right" class="bg-light">
                <a href="<c:url value='/systemBoard' />" class="btn btn-warning">취소</a>
                <input type="submit" class="btn btn-primary" value="저장" />
            </td>
        </tr>
    </table>
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/board/ck.js"></script>
