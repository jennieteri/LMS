<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 유형 관리</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 유형 수정 </li>
  </ol>
</nav>
<style>
/* 테이블 스타일 */
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

th {
    font-size: 18px;
    font-weight: bold;
}
    tfoot td {
        text-align: right;
    }
    

</style>
<form:form id="updateForm" method="POST" enctype="multipart/form-data"
	modelAttribute="award">
	<table class="table table-primary">


		<tr>
			<th height="30px;">장학금 코드</th>
			<td class="bg-light" ><form:input path="awardCd" cssClass="form-control"
					required="true" readonly="true" />
				<form:errors path="awardCd" element="span" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>지급기관명</th>
			<td class="bg-light" ><form:input path="awardEdnstNm" cssClass="form-control" />
				<form:errors path="awardEdnstNm" element="span"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>장학금 이름</th>
			<td class="bg-light" ><form:input path="awardNm" cssClass="form-control"
					required="true" />
				<form:errors path="awardNm" element="span" cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>장학금 유형</th>
			<td class="bg-light" >
			
			
			<select class="form-select" name="awardType" id="awardType">
			        <option value="">장학금 유형을 선택하세요.</option>
			        <option value="추천서">추천서</option>
			        <option value="신청서">신청서</option>
         </select>
			
		<%-- 	
			<form:input path="awardType" cssClass="form-control"
					required="true" />
				<form:errors path="awardType" element="span" cssClass="text-danger" /> --%>
				
				
				</td>
		</tr>
		<tr>
			<th>금액</th>
			<td class="bg-light" ><form:input type="number" path="awardGiveAmt"
					cssClass="form-control" required="true" />
				<form:errors path="awardGiveAmt" element="span"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>장학금 설명</th>
			<td class="bg-light" ><form:input path="awardDetail" cssClass="form-control"
					required="true" />
				<form:errors path="awardDetail" element="span"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>장학금 혜택</th>
			<td class="bg-light" ><form:input path="awardBenefit" cssClass="form-control"
					required="true" />
				<form:errors path="awardBenefit" element="span"
					cssClass="text-danger" /></td>
		</tr>
		<tr>
			<th>제출서류</th>
			<td class="bg-light" ><form:input path="awardDocument" cssClass="form-control"
					required="true" />
				<form:errors path="awardDocument" element="span"
					cssClass="text-danger" /></td>
		</tr>
		
		
		<tr>
			<th>장학금 설명</th>
			<td class="bg-light" ><form:input path="awardDetail" cssClass="form-control"
					required="true" />
				<form:errors path="awardDetail" element="span"
					cssClass="text-danger" /></td>
		</tr>


		</tbody>

		<tfoot>
		    <tr>
		        <td class="bg-light" colspan="2" style="text-align: right;">
		            <input type="submit" value="전송" class="btn btn-primary" />
		        </td>
		    </tr>
		</tfoot>

	</table>
</form:form>








