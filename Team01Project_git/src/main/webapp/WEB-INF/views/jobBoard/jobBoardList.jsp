<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
	
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업지원</li>
    <li class="breadcrumb-item active" aria-current="page">취업정보게시판</li>
  </ol>
</nav>
<div class="container mt-4">
   <br>
    <div class="row">
        <!-- 채용 공고 수 -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                   <h5 class="card-title text-center">현재 진행중인 채용 공고</h5>
      			  	<p class="card-text display-7 text-center fw-bold">
                        <c:choose>
                            <c:when test="${statistics[0].JOB_POSTING != null}">
                                ${statistics[0].JOB_POSTING}
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>

        <!-- 채용 설명회 수 -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">현재 진행중인 채용 설명회</h5>
                   <p class="card-text display-7 text-center fw-bold">
                        <c:choose>
                            <c:when test="${statistics[0].JOB_BRIEFING != null}">
                                ${statistics[0].JOB_BRIEFING}
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>
        
                <!-- 채용 설명회 수 -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h6 class="card-title text-center">곧 마감되는 채용공고</h6>
                  <p class="card-text display-7 text-center fw-bold">
                        <c:choose>
                            <c:when test="${statistics[0].LAST_CLOSE_COUNT_POSTING != null}">
                                ${statistics[0].LAST_CLOSE_COUNT_POSTING}
                            </c:when>
                          <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>
        
                        <!-- 채용 설명회 수 -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">곧 마감되는 채용설명회</h5>
               <p class="card-text display-7 text-center fw-bold">
                        <c:choose>
                            <c:when test="${statistics[0].LAST_CLOSE_COUNT_BRIEFING != null}">
                                ${statistics[0].LAST_CLOSE_COUNT_BRIEFING}
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>
       </div>
       </div>
        
<div class="search-area-container" style="float: right;">
	<div class="search-area" data-pg-target="#searchForm"
		data-pg-fn-name="fnPaging">
		<form:select path="condition.searchType" class="form-select"
			style="width: auto; display: inline-block;">
			<form:option value="" label="전체" />
			<form:option value="title" label="제목" />
			<form:option value="category" label="카테고리" />
		</form:select>
		<form:input path="condition.searchWord" class="form-control"
			placeholder="검색어를 입력하세요"
			style="width: 200px; display: inline-block; margin: 0 5px;" />
		<button id="search-btn" class="btn btn-primary">검색</button>
	</div>
</div>
<br>
<br>
<table class="table table-bordered">
	<thead class="table table-primary">
		<tr>
			<th class="text-center">번호</th>
			<th class="text-center">유형</th>
			<th class="text-center" id="jobboardCt">제목</th>
			<th class="text-center">시작일자</th>
			<th class="text-center">종료일자</th>
			<th class="text-center">조회수</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty list }">
			<c:forEach items="${list }" var="job">
				<tr>
					<td class="text-center">${job.sn }</td>
					<td class="text-center">${job.jobCate }</td>
					<td><c:url value="/jobboard/${job.jobNo }" var="detailUrl" />
						<a href="${detailUrl }">${job.jobNm }</a></td>
					<td class="text-center">${job.jobDt }</td>
					<td class="text-center">${job.jobEt }</td>
					<td class="text-center">${job.jobCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="5">등록된 글이 없습니다.</td>
			</tr>
		</c:if>
<tr>
	<td colspan="7">
		<div class="paging-area">${pagingHTML }</div>
	</td>
</tr>
	</tbody>
</table>

<security:authorize access="hasRole('EMPLOYEE')">
    <div class="text-end mt-3" style="margin-bottom: 100px; position: relative; z-index: 100;">
        <button id="insert-btn" class="btn btn-primary">등록</button>
    </div>
</security:authorize>


<form:form id="searchForm" method="get" modelAttribute="condition"
	style="display:none;">
	<form:input path="searchType" />
	<form:input path="searchWord" />
	<input type="hidden" name="page" />
</form:form>
<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jobboard/jobBoardList.js"></script>
