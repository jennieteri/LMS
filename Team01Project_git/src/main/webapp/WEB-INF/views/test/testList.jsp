<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<style>
table {
	border-collapse: collapse; /* 테이블 경계 간격 제거 */
}

th, td {
	text-align: center; /* 중앙 정렬 */
	vertical-align: middle; /* 세로 중앙 정렬 */
}

th {
	background-color: #f2f2f2; /* 헤더 배경 색상 */
}
</style>
<table class="table table-bordered">
	<thead>
		<tr>
			<th>등록번호</th>
			<th>구분</th>
			<th>강의명</th>
			<th>교수명</th>
			<th>강의실</th>
			<th>시험일시</th>
			<th>상태</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty testList }">
			<c:forEach items="${testList }" var="test">
				<tr>
					<td>${test.testNo }</td>
					<c:if test="${test.testSe eq 'PR'}">
						<td>중간고사</td>
					</c:if>
					<c:if test="${test.testSe eq 'FT'}">
						<td>기말고사</td>
					</c:if>
					<td>${test.lectVO.lectNm }</td>
					<td>${test.lectVO.professorVO.nm }</td>
					<c:if test="${not empty test.classroomVO.croomCd }">
						<td>${test.classroomVO.croomPstn }${test.classroomVO.croomNm }</td>
						<%-- 강의실 --%>

					</c:if>
					<c:if test="${empty test.classroomVO.croomCd }">
						<td>온라인 시험</td>
						<%-- 강의실 --%>

					</c:if>

					<td>${fn:substring(test.testSchdl,0,4) }-${fn:substring(test.testSchdl,4,6) }-${fn:substring(test.testSchdl,6,8) }
						<%-- 시험일시 --%> / ${fn:substring(test.testDt,0,2) } :
						${fn:substring(test.testDt,2,4) } -
						${fn:substring(test.testEt,0,2) } :
						${fn:substring(test.testEt,2,4) }
					</td>


<%-- 					<td><a href="<c:url value='/test/${test.testNo}'/>" --%>
<!-- 						class="btn btn-primary">조회</a></td> -->
<!-- 					<td><div id="test-status"> -->
<%-- 						<c:if test="${test.testCd eq 'COMP' }"> --%>
<!-- 							<button  class="btn btn-primary" id="waiting-button" onclick="showApprovalButtons()">대기</button> -->
<!-- 							<div id="approval-buttons" style="display: none;"> -->
<%-- 								<button class="btn btn-primary" onclick="change('${test.testNo }','ok')">승인</button> --%>
<%-- 								<button class="btn btn-warning" onclick="change('${test.testNo }','no')">반려</button> --%>
<!-- 							</div> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${test.testCd eq 'OPEN' }"> --%>
<!-- 							<button  class="btn btn-primary" id="waiting-button" onclick="showApprovalButtons()">승인</button> -->
<!-- 							<div id="approval-buttons" style="display: none;"> -->
<%-- 								<button class="btn btn-primary" onclick="change('${test.testNo }','wa')">대기</button> --%>
<%-- 								<button class="btn btn-warning" onclick="change('${test.testNo }','no')">반려</button> --%>
<!-- 							</div> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${test.testCd eq 'PEND' }"> --%>
<!-- 							<button  class="btn btn-primary" id="waiting-button" onclick="showApprovalButtons()">반려</button> -->
<!-- 							<div id="approval-buttons" style="display: none;"> -->
<%-- 								<button class="btn btn-primary" onclick="change('${test.testNo }','ok')">승인</button> --%>
<%-- 								<button class="btn btn-warning" onclick="change('${test.testNo }','wa')">대기</button> --%>
<!-- 							</div> -->
<%-- 						</c:if> --%>
						
<!-- 						</div> -->
<!-- 					</td> -->
					<td>
						<div id="test-status">
							<c:if test="${test.testCd eq 'COMP' }">
								<button class="btn btn-primary" id="waiting-button">대기</button>
								<button style="display: none;" class="btn btn-success" onclick="change('${test.testNo }','ok')">승인</button>
								<button style="display: none;" class="btn btn-warning" onclick="change('${test.testNo }','no')">반려</button>
								<button style="display: none;S" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
							</c:if>
							<c:if test="${test.testCd eq 'OPEN' }">
								<button  class="btn btn-success" id="waiting-button">승인</button>
								<button style="display: none;" class="btn btn-primary" onclick="change('${test.testNo }','wa')">대기</button>
								<button style="display: none;" class="btn btn-warning" onclick="change('${test.testNo }','no')">반려</button>
								<button style="display: none;" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
							</c:if>
							<c:if test="${test.testCd eq 'PEND' }">
								<button  class="btn btn-warning" id="waiting-button" >반려</button>
								<button style="display: none;" class="btn btn-success" onclick="change('${test.testNo }','ok')">승인</button>
								<button style="display: none;" class="btn btn-primary" onclick="change('${test.testNo }','wa')">대기</button>
								<button style="display: none;" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
							</c:if>
						
						</div>
					</td>

				</tr>
			</c:forEach>
		</c:if>
	</tbody>
	
</table>
				<div class="paging-area">${pagingHTML }</div>
<!-- <div id="findBox"> -->
<!-- 	<input type="radio" name="testCd" value="COMP">대기 -->
<!-- 	<input type="radio" name="testCd" value="PEND">반려 -->
<!-- 	<input type="radio" name="testCd" value="OPEN">승인 -->
<!-- </div> -->
<div class="input-group mb-3 float-end search-area" data-pg-target="#searchform" data-pg-fn-name="fnPaging" style="width: 350px;">
    <div class="form-check form-check-inline">
        <form:radiobutton path="condition.searchType" value="" cssClass="form-check-input" id="openCode"/>
        <label class="form-check-label" for="openCode">전체</label>
    </div>
    <div class="form-check form-check-inline">
        <form:radiobutton path="condition.searchType" value="OPEN" cssClass="form-check-input" id="openCode"/>
        <label class="form-check-label" for="openCode">승인</label>
    </div>
    <div class="form-check form-check-inline">
        <form:radiobutton path="condition.searchType" value="COMP" cssClass="form-check-input" id="compCode"/>
        <label class="form-check-label" for="compCode">대기</label>
    </div>
    <div class="form-check form-check-inline">
        <form:radiobutton path="condition.searchType" value="PEND" cssClass="form-check-input" id="pendCode"/>
        <label class="form-check-label" for="pendCode">반려</label>
    </div>
    <button id="search-btn" class="btn btn-primary">검색</button>
</div>
<form:form id="searchform" method="get" modelAttribute="condition" style="display: none;">
    <form:input path="searchType"/>
    <input type="hidden" name="page"/>
</form:form>


<script src="${pageContext.request.contextPath }/resources/js/test/testPagingScript.js"></script>

<script
	src="${pageContext.request.contextPath }/resources/js/test/testScript.js"></script>






























