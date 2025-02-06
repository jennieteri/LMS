<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

    

<script type="importmap">
{
	"imports": {
		"ckeditor5": "${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.js",
		"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>


<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">Q&A</li>
    <li class="breadcrumb-item active" aria-current="page">게시글 수정</li>
  </ol>
</nav>
<form:form id="updateForm" method="POST" enctype="multipart/form-data" 
	modelAttribute="board">
<table class="table table-primary" >

     
      
        <tr>
			<th style="text-align: center;">작성자</th>
			<td class="bg-light">
			<form:input path="nm" cssClass="form-control"
					required="true" readonly="true"/>
				<form:errors path="nm" element="span" cssClass="text-danger" /></td>
		</tr>
        <tr>
            <th style="text-align: center;">제목</th>
            <td class="bg-light">
                <form:input type="text" path="sibTtl" required="true" cssClass="form-control" />
                <form:errors path="sibTtl" cssClass="text-danger" />
            </td>
        </tr>
      
        <tr>

           
            
            <tr>
			    <td colspan="2" class="bg-light">
			       <form:textarea path="sibCn" class="form-control" id="editor" placeholder="내용 입력하세요" style="height: 500px;" />
			        
			    </td>
			</tr> 
            
        </tr>


</tbody>

<tfoot>
<tr>
			<td colspan="2" align="right">
				<input type="submit" value="전송" class="btn btn-primary" /> 
				
			</td>
		</tr>

</tfoot>
</table>
</form:form>
<form action="<c:url value='/answer/delete/${board.sibNo}' />" method="post" id="deleteForm">
<!-- 	POST 요청에 포함된 hidden "_method" 파라미터로 브라우저가 지원하지 않는 put/delete 등의 요청 메소드를 대신 표현할 수 있음. -->
<!-- 	단, 서버측에서 해당 파라미터로 요청의 메소드를 변경할 수 있는 Filter 등이 필요함.(web.xml 참고) -->
<!-- <input type="hidden" name="_method" required value="delete"/> -->
</form>

<script src="${pageContext.request.contextPath }/resources/js/board/boardEdit.js"></script>






