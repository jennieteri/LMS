<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>   
    
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 유형 관리</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 유형 상세 조회 </li>
  </ol>
</nav>

<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<script>
 
    
    const message = "${message}";
    const error = "${error}";
</script>
<style>

th{
text-align: center;
width: 180px;
}
.table {
    width: 70%;
    margin: 0 auto;
    border-collapse: collapse;
}
th, td {
    padding: 10px;
    text-align: center;
    vertical-align: middle;
    border: 1px solid #ddd;
}

</style>

<table class="table table-primary">
		
		<tr>
		<th height="30px;">장학금 코드</th>
		<td class="bg-light">${award.awardCd}</td>
		</tr>
		<th>장학금 유형</th>
		<td class="bg-light">${award.awardType}</td>
		</tr>
		<tr>
		<th>지급기관명</th>
		<td class="bg-light">${award.awardEdnstNm}</td>
		</tr>
		<tr>
		<th>장학금 이름</th>
		<td class="bg-light">${award.awardNm}</td>
		</tr>
		<tr>
		<th>금액</th>
		<td class="bg-light">${award.awardGiveAmt}</td>
		</tr>
		<tr>
		<th >장학금 설명</th>
		<td class="bg-light">${award.awardDetail}</td>
		</tr>
		<tr>
		<th>장학금 혜택</th>
		<td class="bg-light">${award.awardBenefit}</td>
		</tr>
		<tr>
		<th >제출 서류</th>
		<td class="bg-light">${award.awardDocument}</td>
		</tr>

    </table>

<br>
<div style="display: flex; justify-content: flex-end; gap: 10px;">

    <!-- ADMIN 사용자에게만 표시 -->
    
        <a href="<c:url value='/award/${awardCd}/edit' />" class="btn btn-info">수정</a>
        
   

 		<form action="${pageContext.request.contextPath}/award/delete/${awardCd}" method="post" id="deleteForm">
		    <button type="button" class="btn btn-danger" id="deleteButton">삭제</button>
		</form>
    
    <!-- 모든 사용자에게 표시 -->
    <a href="<c:url value='/award/selectAdminAwardList' />" class="btn btn-primary">목록</a>

</div>

<script src="${pageContext.request.contextPath }/resources/js/award/awardDelete.js"></script>