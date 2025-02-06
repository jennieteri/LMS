<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">강의</li>
    <li class="breadcrumb-item active" aria-current="page">강의 관리</li>
  </ol>
</nav>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<input type="hidden" id="semstrNo" value="${param.semstrNo }"/>
<div class="input-group mb-3 float-end" style="width: 180px;">
	<span class="input-group-text">학기</span>
	<select class="form-select" id="semester">
	</select>
</div>

<table class="table table-primary table-bordered table-hover">
	<thead class="text-center align-middle">
		<tr>
			<th style="width: 90px;">과목분류</th>
			<th>[과목] 강의명</th>
			<th style="width: 90px;">강의교수</th>
			<th style="width: 50px;">학점<br>(점)</th>
			<th style="width: 90px;">모집인원<br>(명)</th>
			<th style="width: 90px;">강의방식</th>
			<th style="width: 90px;">진행상태</th>
		</tr>
	</thead>
	<tbody class="table-group-divider" id="lectTbody"></tbody>
</table>
<security:authorize access="hasRole('PROFESSOR')">
	<a class="btn btn-primary float-end me-3"
		href="${pageContext.request.contextPath }/lecture/request/new">등록</a>
</security:authorize>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureRequestList.js"></script>