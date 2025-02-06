<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item active" aria-current="page">등록 및 납부</li>
  </ol>
</nav>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<form:form modelAttribute="condition" name="searchForm" method="get">
	<input type="hidden" id="page" name="page" value="1"/>
	<input type="hidden" id="semstrNo" name="semstrNo"/>
	<div class="collapse" id="search" style="position: relative;">
		<div id="detailConditionDiv" class="mb-3">
			<div class="row g-3 mb-3">
				<div class="col">
					<label for="stuId" class="form-label">학번</label>
					<input name="stuId" class="form-control"
						<security:authorize access="hasRole('STUDENT')">
							value="<security:authentication property="principal.username"/>"
						</security:authorize>
					/>
				</div>
				<div class="col">
					<label for="studentVO.nm" class="form-label">이름</label>
					<form:input path="studentVO.nm" cssClass="form-control"/>
				</div>
				<div class="col">
					<label for="tuitStatusCd" class="form-label">납부상태</label>
					<form:select path="tuitStatusCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${tuitionStatusList }" itemLabel="cocoStts" itemValue="cocoCd" />
					</form:select>
				</div>
			</div>
		</div>
	</div>
</form:form>
<button class="btn btn-primary float-end mb-3 <security:authorize access="hasRole('STUDENT')">d-none</security:authorize>"
	data-bs-toggle="collapse" data-bs-target="#search" aria-expanded="false" aria-controls="search"><i class="bi bi-search"></i></button>
<div class="float-end me-2 fade-transition hidden" id="searchDiv">
	<button class="btn btn-secondary me-1" id="searchBtn">검색</button>
	<button class="btn btn-warning" id="resetBtn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
<div class="row">
	<select id="pageLength" class="form-select" style="width: 80px;">
	    <option value="10">10</option>
	    <option value="25">25</option>
	    <option value="50">50</option>
	    <option value="100">100</option>
	</select>
	<div class="col">
		<div class="input-group" style="width: 180px;">
			<span class="input-group-text">학기</span>
			<select class="form-select" id="semester">
				<option value="" label="전체"/>
				<c:forEach items="${semesterList }" var="semester">
					<option value="${semester.semstrNo }" label="${semester.label }"/>
				</c:forEach>
			</select>
		</div>
	</div>
</div>
<table class="table table-bordered table-primary" id="tuiTable">
	<thead>
		<tr>
			<th>학번</th>
			<th>이름</th>
			<th>납부상태</th>
			<th>납부일자</th>
			<th>등록금고지서</th>
			<th>납부확인서</th>
		</tr>
	</thead>
</table>
<div id="pdf-viewer"></div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.3.122/pdf.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/tuition/tuitionList.js"></script>