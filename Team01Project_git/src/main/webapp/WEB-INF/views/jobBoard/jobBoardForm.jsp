<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업정보게시판</li>
    <li class="breadcrumb-item active" aria-current="page">취업정보게시판 등록</li>
  </ol>
</nav>

<form action="${pageContext.request.contextPath}/jobboard/create" id="createForm" method="post" enctype="multipart/form-data" >
	<input type="hidden" name="id" value="${person.username }" class="form-control" >
	<table class="table table-primary" id="form-table" data-path="${pageContext.request.contextPath }">
		
	<security:authentication property="principal" var="person"/>	
		<tr>
			<th class="text-center align-middle">제목</th>
			<td class="table-light"><input type="text" name="jobNm" 
					class="form-control" required/>
		</tr>
		<tr>
			<th class="text-center align-middle">카테고리</th>
			<td class="table-light">
			    <select class="form-select" name="jobCate" id="jobCate" required >
			        <option value="">카테고리 유형을 선택하세요</option>
			        <option value="채용공고">채용공고</option>
			        <option value="채용설명회">채용설명회</option>
			    </select>
			</td>
		</tr>
		<tr>
			<th class="text-center align-middle">공고 시작 일자</th>
			<td class="table-light"><input type="date" name="jobDt"
					class="form-control" required/>
		</tr>
		<tr>
		<tr>
			<th class="text-center align-middle">공고 마감 일자</th>
			<td class="table-light"><input type="date" name="jobEt"
					class="form-control" required  />
		</tr>
		<tr>
			<th class="text-center align-middle">제한 인원 수</th>
			<td class="table-light"><input type="text" name="jobLimit"
					class="form-control" placeholder="제한 인원 수를 작성해주세요. ex) 제한없음, 00명" required />
		</tr>
		<tr>
			<th class="text-center align-middle">첨부파일</th>
			<td class="table-light">
				<input type="file" name="uploadFiles" multiple class="form-control" required />	
			</td>
		</tr>
		<tr>
			<td colspan="2" class="table-light">
				<textarea name="jobContent" class="tinymce-editor" required >
				</textarea> 
			</td>
		</tr>  
		
		 <tr style="height: 20px;"></tr>
		
		<tr>
		<td class="table-light" colspan="2" style="text-align: right;">
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="/yguniv/jobboard/" class="btn btn-secondary">닫기</a>
		</td>
	</tr>
	</table>
</form>

<script src="${pageContext.request.contextPath }/resources/js/jobboard/jobBoardForm.js"></script>

