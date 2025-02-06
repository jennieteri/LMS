<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>
/* 테이블 스타일 */
.table {

    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
}

.table th, .table td {
    text-align: center; /* 가로 중앙 정렬 */
    vertical-align: middle; /* 세로 중앙 정렬 */
    border: 1px solid #ddd; /* 테두리 */
}



/* Popover 헤더 중앙 정렬 */
.popover.custom-popover .popover-header {
  text-align: center !important; /* 중앙 정렬 */

}





</style>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">공지사항</li>
    <li class="breadcrumb-item active" aria-current="page">Q&A</li>
  </ol>
</nav>


	
      <tr>
      
            
            <div class="search-area d-flex justify-content-end align-items-center gap-2" data-pg-target="#searchForm" data-pg-fn-name="fnPaging"
            style="text-align: right;">
               <form:select path="condition.searchType" class="form-select form-select" style="width: 100px;">
                  <form:option value="" label="전체" />
                  <form:option value="title" label="제목" />
                
               </form:select>
               <form:input path="condition.searchWord" class="form-control form-control" style="width: 200px;"/>
               
               
               <button id="search-btn" class="btn btn-primary btn">검색</button>
            </div>
         
      </tr>
      <br>
<table class="table table-bordered">

 <thead class="table-primary text-center">
      <tr>
             
      <th style="width:40px">순번</th>
      <th style="width:250px" >문의제목</th>
      <th style="width:40px">작성자</th>
      <th style="width:100px">문의등록일자</th>
      <th style="width:20px" >상태</th>
      <tr>
   </thead>


<tbody align="center" style="height: 50px;">
      <c:if test="${not empty board}">

         <c:forEach items="${board}" var="board">

            
            <tr onclick="location.href='<c:url value='/answer/${board.sibNo}'/>'" style="cursor: pointer;">

               <td>${board.rnum}</td>

               <td>${board.sibTtl}</td>
               <td>${board.nm}</a></td>

               
               <td>${fn:split(board.sibDt, 'T')[0] } ${fn:split(board.sibDt, 'T')[1] }</td>
               <td>
				   <button class="btn 
				      <c:choose>
				         <c:when test="${board.sibSttsYn == '요청'}">btn-warning</c:when>
				         <c:when test="${board.sibSttsYn == '완료'}">btn-success</c:when>
				         <c:otherwise>btn-secondary</c:otherwise>
				      </c:choose>">
				      ${board.sibSttsYn}
				   </button>
				</td>

               
            </tr>
         </c:forEach>
      </c:if>
      
      <c:if test="${empty board}">
         <tr>
            <td colspan="8">글 없음.</td>
         </tr>
      </c:if>
   
   
   
   </tbody>
 
   
   
   	<tfoot>
		<tr>
			<td colspan="6">
				<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
				<div class="mx-auto">
				${pagingHTML }
				</div>
				<security:authorize access="!hasRole('ADMIN')">
				<div>
					<a class="btn btn-primary" style="position: absolute; right: 0px; top: 0px;" href="<c:url value='/answer/createAnswerBoard/new'/>" >등록</a>
				</div>
				</security:authorize>
				</div>
			</td>

		</tr>
	</tfoot>
   


<tbody align="center">
   
   </tbody>

</table>

<form:form id="searchForm" method="get" modelAttribute="condition" >
   <form:input path="searchType" type="hidden"/>
   <form:input path="searchWord" type="hidden"/>
   <input type="hidden" name="page" />
</form:form>




<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>

