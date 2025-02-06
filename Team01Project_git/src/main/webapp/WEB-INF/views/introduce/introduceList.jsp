<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업관리</li>
    <li class="breadcrumb-item active" aria-current="page">자소서 피드백</li>
  </ol>
</nav>

		<div class="input-group mb-3 float-end search-area" data-pg-target="#searchForm" data-pg-fn-name="fnPaging" style="width: 350px;">
				<form:select path="condition.searchType" class="form-select" >
					<form:option value="" label="전체" />
					<form:option value="number" label="학번" />
					<form:option value="name" label="이름" />
					</form:select>
				<form:input path="condition.searchWord" class="form-control" placeholder="검색어를 입력하세요" style="width: 150px;"/>
					<button id="search-btn" class="btn btn-primary">검색</button>
 		</div>


<table class="table table-bordered table-primary" >
	<thead>
		<tr class="text-center">
			<th>번호</th>
			<th>학번</th>
			<th>학년</th>
			<th>이름</th>
			<th>희망취업분야</th>
			<th>제출일자</th>
			<th>자기소개서</th>
		</tr>
	</thead>
	<tbody class="table-group-divider">
		<c:if test="${not empty list }">
			<c:forEach items="${list }" var="introduce">
			<tr class="table-light">
				<td class="text-center">${introduce.rnum }</td>
				<td class="text-center">${introduce.stuId }</td>
<%-- 			 	class="text-center"	<c:url value="/introduce/detail/${introduce.stuId }" var="detailUrl"/> --%>
<%-- 			 	class="text-center"	<a href="${detailUrl }">${introduce.stuId }</a> --%>
				<td class="text-center">${introduce.commoncode.cocoStts }</td>
				<td class="text-center">${introduce.person.nm }</td>
				<td class="text-center">${introduce.employmentfield.empfiNm }</td>
				<td class="text-center">${introduce.intDate }</td>
			    <td class="text-center">
                   <!-- "자기소개서 보기" 버튼 -->
                  <form action="/yguniv/introduce/detail/${introduce.intCd}" method="get" style="display: inline;">
   					 <button type="submit" class="btn btn-outline-primary">상세보기</button>
				  </form>

                </td>
			</tr>
			
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5">검색 조건 결과가 없습니다.</td>
			</tr>
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


<form:form id="searchForm" method="get" modelAttribute="condition" style="display:none;" >
	<form:input path="searchType" />
	<form:input path="searchWord"/>
	<input type="hidden" name="page" />
</form:form>
<script
	src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>

<script src="${pageContext.request.contextPath }/resources/js/jobboard/jobBoardList.js"></script>
















