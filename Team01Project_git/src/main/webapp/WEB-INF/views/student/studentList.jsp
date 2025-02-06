<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학적관리</li>
    <li class="breadcrumb-item active" aria-current="page">학생 관리</li>
  </ol>
</nav>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<form:form modelAttribute="condition" name="searchForm" method="get">
	<input type="hidden" id="page" name="page" value="1"/>
	<div class="collapse" id="search" style="position: relative;">
		<div id="detailConditionDiv" class="mb-3">
			<div class="row g-3 mb-3">
				<div class="col">
					<label for="stuId" class="form-label">학번</label>
					<form:input path="stuId" cssClass="form-control"/>
				</div>
				<div class="col">
					<label for="nm" class="form-label">이름</label>
					<form:input path="nm" cssClass="form-control"/>
				</div>
				<div class="col">
					<label for="gradeCd" class="form-label">학년</label>
					<form:select path="gradeCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${gradeList }" itemLabel="cocoStts" itemValue="cocoCd" />
					</form:select>
				</div>
			</div>
			<div class="row g-3 mb-3">
				<div class="col">
					<label for="deptCd" class="form-label">학과</label>
					<form:select path="deptCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${departmentList }" itemLabel="deptNm" itemValue="deptNo" />
					</form:select>
				</div>
				<div class="col">
					<label for="streCateCd" class="form-label">학적</label>
					<form:select path="streCateCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${streCateList }" itemLabel="cocoStts" itemValue="cocoCd" />
					</form:select>
				</div>
			</div>
		</div>
	</div>
</form:form>
<button class="btn btn-primary float-end mb-3" data-bs-toggle="collapse" data-bs-target="#search" 
		aria-expanded="false" aria-controls="search"><i class="bi bi-search"></i></button>
<div class="float-end me-2 fade-transition hidden" id="searchDiv">
	<button class="btn btn-secondary me-1" id="searchBtn">검색</button>
	<button class="btn btn-warning" id="resetBtn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
<select id="pageLength" class="form-select" style="width: 80px;">
    <option value="10">10</option>
    <option value="25">25</option>
    <option value="50">50</option>
    <option value="100">100</option>
</select>
<table class="table table-bordered table-primary table-hover" id="stuTable">
	<thead>
		<tr>
			<th>학번</th>
			<th>이름</th>
			<th>학년</th>
			<th>학과</th>
			<th>학적</th>
			<th>담당교수</th>
		</tr>
	</thead>
</table>
<!-- 학생 상세조회 모달 -->
<div class="modal fade" id="detailStuModal" tabindex="-1" aria-labelledby="detailStuModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="detailStuModalLabel">학생 상세조회</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      	<div class="row">
      		<div class="col">
      			<h4>기본정보</h4>
      			<div class="card p-3 m-0" id="basicInfo"></div>
      		</div>
      		<div class="col">
      			<h4>학기별성적</h4>
				<div class="card p-3" id="transcript"></div>      		
      			<h4>학사경고이력</h4>
				<div class="card p-3 m-0">
					<div id="academic" class="mb-2"></div>
					<div>
	        			<button type="button" class="btn btn-warning float-end" data-bs-target="#insertAcademicModal" data-bs-toggle="modal">등록</button>
					</div>
				</div>      		
      		</div>
      	</div>
      </div>
      <div class="modal-footer">
      	<button id="expulsionBtn" class="btn btn-danger" onclick="fnExpulsion(this)">제적</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
<!-- 학생 학사경고 등록 모달 -->
<div class="modal fade" id="insertAcademicModal" tabindex="-1" aria-labelledby="insertAcademicModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="insertAcademicModalLabel">학사경고 등록</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      	<form name="insertAcademicForm">
      		<div class="row">
      			<div class="col">
		      		<div class="mb-3">
					  <label for="stuId" class="form-label">학번</label>
					  <input type="text" class="form-control" name="stuId" readonly>
					</div>
      			</div>
      			<div class="col">
		      		<div class="mb-3">
					  <label for="nm" class="form-label">이름</label>
					  <input type="text" class="form-control" name="nm" readonly>
					</div>
      			</div>
      		</div>
      		<div>
			  <label for="nm" class="form-label">학사경고사유</label>
			  <textarea class="form-control" name="proRes" placeholder="학사경고사유를 입력하세요"></textarea>
			</div>
      	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="fnInsertAcademic(this);">저장</button>
        <button type="button" class="btn btn-secondary" data-bs-target="#detailStuModal" data-bs-toggle="modal">취소</button>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath }/resources/js/student/studentList.js"></script>