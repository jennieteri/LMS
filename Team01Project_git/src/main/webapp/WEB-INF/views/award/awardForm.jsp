<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 유형 관리</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 유형 추가</li>
  </ol>
</nav>

<style>
/* 테이블 스타일 */
.table {
    width: 80%;
    margin: 0 auto;
    border-collapse: collapse;
}

th, td {
    padding: 10px;
    text-align: center;
    vertical-align: middle;
    border: 1px solid #ddd;
}

th {
    font-size: 18px;
    font-weight: bold;
}

/* 버튼 오른쪽 정렬 */
td.align-right {
    text-align: right; /* 오른쪽 정렬 */
}

</style>

<form:form method="post" enctype="multipart/form-data" modelAttribute="award"
           action="${pageContext.request.contextPath}/award/insertAward" id="createForm">
   <table class="table table-primary">
      <tr>
         <th width="200px;">지급기관명</th>
         <td class="bg-light"><form:input type="text" path="awardEdnstNm" 
               cssClass="form-control" />
            <form:errors path="awardEdnstNm" cssClass="text-danger" /></td>
      </tr>
      <tr>
         <th>장학금이름</th>
         <td class="bg-light"><form:input type="text" path="awardNm" 
               cssClass="form-control" />
            <form:errors path="awardNm" cssClass="text-danger" /></td>
      </tr>
      <tr>
         <th>장학금 유형</th>
         <td class="bg-light">
            <form:select path="awardType" class="form-select" id="awardType">
                <form:option value="" label="장학금 유형을 선택하세요." />
                <form:option value="추천서" label="추천서" />
                <form:option value="신청서" label="신청서" />
            </form:select>
            <form:errors path="awardType" cssClass="text-danger" />
         </td>
      </tr>
      <tr>
         <th>금액</th>
         <td class="bg-light"><form:input type="text" path="awardGiveAmt"
               cssClass="form-control" />
            <form:errors path="awardGiveAmt" cssClass="text-danger" /></td>
      </tr>
      <tr>
         <th>제출 서류</th>
         <td class="bg-light"><form:input type="text" path="awardDocument"
               cssClass="form-control" />
            <form:errors path="awardDocument" cssClass="text-danger" /></td>
      </tr>
      <tr>
         <th>장학혜택</th>
         <td class="bg-light">
             <form:input type="text" path="awardBenefit" cssClass="form-control" />
            <form:errors path="awardBenefit" cssClass="text-danger" />
         </td>
      </tr>
      <tr>
         <td colspan="2" class="bg-light">
            <form:textarea path="awardDetail" class="tinymce-editor"></form:textarea>
            <form:errors path="awardDetail" cssClass="text-danger" element="span" />
         </td>    
      </tr>
      <tr>
         <td colspan="2" class="align-right">
            <input type="submit" class="btn btn-primary" value="저장" />
         </td>
      </tr>
   </table>
</form:form>
