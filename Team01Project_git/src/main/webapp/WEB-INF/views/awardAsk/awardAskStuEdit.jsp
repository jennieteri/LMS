<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>
<input type="hidden" id="shapDocNo" value="${ask.shapDocNo }">
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 신청 내역</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 신청 내역 수정</li>
  </ol>
</nav>


<style>


    
    <style>
/* 테이블 스타일 */
.table {
    width: 70%;
    margin: 0 auto;
    border-collapse: collapse;
}

th, td {
    padding: 10px;
    vertical-align: middle;
    border: 1px solid #ddd;
}

th {
    font-size: 18px;
    font-weight: bold;
}

</style>
    

</style>
<form:form method="POST" id="updateForm" enctype="multipart/form-data"
	modelAttribute="ask">

	<table class="table table-primary">


		<tr>
		    <th>장학 유형 선택</th>
		    <td colspan="2"  class="bg-light">
		        <form:select path="awardCd" class="form-select"> 
		            <form:option value="" label="유형을 선택하시오"/>
		            <c:forEach var="award" items="${awardList}">
		                <c:if test="${award.awardType == '신청서'}">
		                    <form:option value="${award.awardCd}" label="${award.awardNm}" />
		                </c:if>
		            </c:forEach>
		        </form:select>
		        <form:errors path="awardCd" element="span" class="text-danger" />
		    </td>
		</tr>

		
		<tr>
		
		
		  <th>학번</th>
            <td  class="bg-light">
            <form:input type="text" path="stuId" cssClass="form-control" readonly="true"/>
            <form:errors path="stuId" cssClass="text-danger" />
            </td>
		
		</tr>
		
		 
		 <tr>
            <th>학기번호(년도/학기)</th>
            <td  class="bg-light">
            
            	<form:select path="semstrNo" Class="form-select" aria-label="Default select example">
            	<option selected>학기 번호를 선택하세요.</option>
            		<form:option value="202501" label="2025-01" />
            		<form:option value="202502" label="2025-02" />
            		<form:option value="202601" label="2026-01" />
            		<form:option value="202602" label="2026-02" />
            	</form:select>
            </td>
            
        </tr>  
        <tr>
			<th>기존파일</th>
			<td  class="bg-light">
			<c:forEach items="${ask.atchFile.fileDetails }" var="fd" varStatus="vs">
				<span>
					${fd.orignlFileNm }[${fd.fileFancysize }]
					<a data-atch-file-id="${fd.atchFileId }" data-file-sn="${fd.fileSn }" class="btn btn-danger" href="javascript:;">
						삭제						
					</a>
					${not vs.last ? '|' : ''}
				</span>
			</c:forEach>
				</td>
		</tr>                       

		<tr>
			<th>추가서류</th>
			<td  class="bg-light"><input type="file" name="uploadFiles" multiple class="form-control" /></td>
		</tr>

		<tr>
			<td colspan="2" align="right" class="bg-light">
				<input type="submit" value="전송" class="btn btn-primary" /> 
				<input type="button" value="삭제" class="btn btn-danger" id="delete-btn"/> 
			</td>
		</tr>


	</table>
	
	


</form:form>
<!-- 이거 첨부파일 삭제  -->
<script src="${pageContext.request.contextPath }/resources/js/board/boardEdit.js"></script>



