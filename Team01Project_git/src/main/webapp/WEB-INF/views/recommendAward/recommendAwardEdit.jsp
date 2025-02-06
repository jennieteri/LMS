<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 추천서 목록</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 추천서 수정</li>
  </ol>
</nav>

<style>
textarea.form-control {
    height: 200px; /* 높이를 200px로 설정 */
    
    table {
    width: 100%; /* 테이블 전체 너비 */
    border-collapse: collapse; /* 테두리 중첩 */
}

tbody {
    display: block; /* tbody를 블록 요소로 설정 */
    width: 100%; /* 너비를 테이블에 맞춤 */
    height: 300px; /* 높이를 300px로 고정 */
    overflow-y: auto; /* 세로 스크롤 추가 */
}

thead, tfoot {
    display: table; /* thead와 tfoot은 테이블로 유지 */
    width: 100%;
}


th {
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
}
    

</style>


<form:form method="POST" id="updateForm" enctype="multipart/form-data"
	modelAttribute="ask">

	<table class="table table-primary">


		<tr>
		    <th>장학 유형 선택</th>
		    <td colspan="2" class="bg-light">
		        <form:select path="awardCd" class="form-select"> 
		            <form:option value="" label="유형을 선택하시오"/>
		            <c:forEach var="award" items="${awardList}">
		                <c:if test="${award.awardType == '추천서'}">
		                    <form:option value="${award.awardCd}" label="${award.awardNm}" />
		                </c:if>
		            </c:forEach>
		        </form:select>
		        <form:errors path="awardCd" element="span" class="text-danger" />
		    </td>
		</tr>

		
		
		
		  <tr>
            <th>서류번호</th>
            <td class="bg-light">
            <form:input type="text" path="shapDocNo" cssClass="form-control" readonly="true" />
            <form:errors path="shapDocNo" cssClass="text-danger" />
            </td>
            
        </tr>
		  <tr>
            <th>학기번호</th>
            <td class="bg-light">
            <form:input type="text" path="semstrNo" cssClass="form-control" readonly="true"/>
            <form:errors path="semstrNo" cssClass="text-danger" />
            </td>
            
        </tr>
        <tr>
		  <th>추천 학생 학번</th>
            <td class="bg-light">
            <form:input type="text" path="stuId" cssClass="form-control" readonly="true"/>
            <form:errors path="stuId" cssClass="text-danger" />
            </td>
		
		</tr>
        <tr>
		  <th>추천 사유</th>
            <td class="bg-light">
            <form:input type="text" path="shapRecommend" cssClass="form-control" readonly="true"/>
            <form:errors path="shapRecommend" cssClass="text-danger" />
            </td>
		
		</tr>
        <tr>
			<th>기존파일</th>
			<td class="bg-light">
			<c:forEach items="${ask.atchFile.fileDetails }" var="fd" varStatus="vs">
				<span>
					${fd.orignlFileNm }[${fd.fileFancysize }]
					<a data-atch-file-id="${fd.atchFileId }" data-file-sn="${fd.fileSn }" class="btn btn-danger" href="javascript:void(0);">
						삭제						
					</a>
					${not vs.last ? '|' : ''}
				</span>
			</c:forEach>
				</td>
		</tr>                       

		<tr>
			<th>추가서류</th>
			<td class="bg-light"><input type="file" name="uploadFiles" multiple class="form-control" /></td>
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