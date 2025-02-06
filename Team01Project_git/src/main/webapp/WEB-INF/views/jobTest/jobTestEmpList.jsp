<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link href="https://cdn.jsdelivr.net/npm/sweetalert/dist/sweetalert.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert/dist/sweetalert.min.js"></script>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업 관리</li>
    <li class="breadcrumb-item active" aria-current="page">직업선호도평가 문제</li>
  </ol>
</nav>
<body>

   <table class="table align-middle">
        <thead class="text-center">
            <tr>
                <th>번호</th>
                <th>문항 유형</th>
                <th>검사 문항</th>
                <th>작업</th>
            </tr>
        </thead>
        <tbody id="jobTestTableBody">
            <c:forEach var="item" items="${list}">
                <tr data-job-test-no="${item.jobTestNo}">
                    <td class="text-center">${item.rnum}</td>
                    <td class="text-center" data-key="cocoStts" data-options="${commonCodeList}">${item.commoncode.cocoStts}</td>
                    <td class="job-test-text" data-key="jobTestText">${item.jobTestText}</td>
                    <td class="d-flex justify-content-center">
                        <button type="button" class="btn btn-info edit-btn me-2">수정</button>
                        <button type="button" class="btn btn-danger delete-btn">삭제</button>
                        <button type="button" class="btn btn-success save-btn me-2" style="display: none;">저장</button>
                        <button type="button" class="btn btn-secondary cancel-btn" style="display: none;">취소</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

  	<div class="sticky-footer" style="position: fixed; bottom: 80px; right: 20px; z-index: 9999; padding: 10px;">
    	<button type="button" class="btn btn-primary add-btn">문제 추가</button>
	</div>


    <input type="hidden" id="cp" value="${pageContext.request.contextPath }">
</body>
<script src="${pageContext.request.contextPath }/resources/js/jobtest/jobTestEmpList.js"></script>
