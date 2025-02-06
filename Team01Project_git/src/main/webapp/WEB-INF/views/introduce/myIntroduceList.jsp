<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업지원</li>
    <li class="breadcrumb-item active" aria-current="page">자소서 클리닉</li>
  </ol>
</nav>

<h4 class="text-center"><security:authentication property="principal.realUser.nm" />
님이 제출한 자기소개서 리스트</h4>
<br>

<table class="table table-borded" >
	<thead class="table table-primary">
		<tr class="text-center">
			<th>번호</th>
			<th>제출일자</th>
			<th>취업희망분야</th>
			<th>자기소개서</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty myintroduce }">
			<c:forEach items="${myintroduce }" var="introduce">
			<tr>
				<td class="text-center">${introduce.rnum}</td>
<%-- 			 	class="text-center"	<c:url value="/introduce/detail/${introduce.stuId }" var="detailUrl"/> --%>
<%-- 			 	class="text-center"	<a href="${detailUrl }">${introduce.stuId }</a> --%>
				<td class="text-center">${introduce.intDate }</td>
				<td class="text-center">${introduce.employmentfield.empfiNm }</td>

			    <td class="text-center">
                   <!-- "자기소개서 보기" 버튼 -->
                  <form action="/yguniv/introduce/mydetail/${introduce.intCd}" method="get" style="display: inline;">
   					 <button type="submit" class="btn btn-outline-primary">상세보기</button>
				  </form>
                </td>
			</tr>
			</c:forEach>
		</c:if>
		
	</tbody>
	
	<tfoot>
		<tr>
			<td colspan="7">
				<div class="paging-area">
				${pagingHTML }
				
				</div>
			</td>
		</tr>
	</tfoot>
	
</table>
<form action="/yguniv/introduce/new" method="get" style="width: 100%;">
    <button type="submit" class="btn btn-primary" id="saveBtn" style="float: right;">등록</button>
</form>



<form:form id="searchForm" method="get" modelAttribute="condition" style="display:none;" >
	<form:input path="searchType" />
	<form:input path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>
<script
	src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>

















