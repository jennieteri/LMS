<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 관리</li>
    <li class="breadcrumb-item active" aria-current="page">추천서 신청 내역</li>
  </ol>
</nav>
	<br>
	<style>

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




	</style>
<table class="table table-bordered">

			<thead class="table-primary text-center">
				<tr>
					<th>학기번호</th>

					<th>장학금명</th>
					<th>접수일시</th>
					<th>선발일시</th>
					<th>진행상태</th>
					
					<th></th>
				</tr>
			</thead>
		<tbody>
		
			<c:if test="${not empty ask }">
				<c:forEach items="${ask }" var = "askList"> 
					<tr>	
						
						
					
						<td>
					 ${fn:substring(askList.semstrNo, 0, 4)}-${fn:substring(askList.semstrNo, 4, 6)} 
					 </td>

						
						
						<td><c:url value="/recommendAward/${askList.shapDocNo }/professor" var = "url"/>
						
						<a href="${url }">${askList.awardVO.awardNm}</a> </td>
						
						
					
						
						<td>	${fn:split(askList.shapRcptDate, 'T')[0] } ${fn:split(askList.shapRcptDate, 'T')[1] }
					</td>
				<td>	${fn:split(askList.shapChcDate, 'T')[0] } ${fn:split(askList.shapChcDate, 'T')[1] }
					</td>
						
						
					<td>
					    <c:if test="${askList.commonCodeVO.cocoStts == '승인'}">
					        <button class="btn btn-success" style="color: white;">승인</button>
					    </c:if>
					    <c:if test="${askList.commonCodeVO.cocoStts == '반려'}">
					        <button class="btn btn-danger" style="color: white;">반려</button>
					    </c:if>
					    <c:if test="${askList.commonCodeVO.cocoStts == '접수'}">
					        <button class="btn btn-primary" style="color: white;">접수</button>
					    </c:if>
					    <c:if test="${askList.commonCodeVO.cocoStts != '승인' && askList.commonCodeVO.cocoStts != '반려'&& askList.commonCodeVO.cocoStts != '접수'}">
					       <button class="btn btn-warning" style="color: black;"> ${askList.commonCodeVO.cocoStts}</button>
					    </c:if>
					</td>

						
					<td align="center">
					    <c:if test="${askList.commonCodeVO.cocoStts != '승인' && askList.commonCodeVO.cocoStts != '반려'}">
					        <button class="btn btn-info update-btn" data-doc-no="${askList.shapDocNo}">수정</button>
					    </c:if>
					</td>

					
					</tr>

				</c:forEach>	
			</c:if>
			
			<c:if test="${empty ask }">
			
			<tr>
			
				<td colspan="8" style="text-align: center;"> 신청한 장학금이 없습니다. </td>
			</tr>
			</c:if>
		
		</tbody>
	
</table>

<script src="${pageContext.request.contextPath}/resources/js/awardRec/awardRecEdit.js"></script>


