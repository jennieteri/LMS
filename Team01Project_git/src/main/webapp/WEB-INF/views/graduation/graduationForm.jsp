<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">졸업인증제</li>
    <li class="breadcrumb-item active" aria-current="page">영어 점수 등록</li>
  </ol>
</nav>

<div class="container mt-5">
    <form id="engForm">
        <input type="hidden" id="cp" value="${pageContext.request.contextPath}">
        <input type="hidden" name="stuId" value="<security:authentication property='principal.realUser.id' />">
        <input type="hidden" name="gdtType" value="GT02" />

        <!-- 인증서류명 -->
        <div class="mb-3">
            <label for="gdtNm" class="form-label">인증서류명</label>
            <input type="text" class="form-control" id="gdtNm" name="gdtNm" placeholder="예: TOEIC" required>
        </div>

        <!-- 발급기관 -->
        <div class="mb-3">
            <label for="gdtInst" class="form-label">발급기관</label>
            <input type="text" class="form-control" id="gdtInst" name="gdtInst" placeholder="예: 한국토익위원회" required>
        </div>

        <!-- 발급일자 -->
        <div class="mb-3">
            <label for="gdtIssu" class="form-label">발급일자</label>
            <input type="date" class="form-control" id="gdtIssu" name="gdtIssu" required>
        </div>

        <!-- 인증 점수 -->
        <div class="mb-3">
            <label for="gdtScore" class="form-label">인증 점수</label>
            <input type="number" class="form-control" id="gdtScore" name="gdtScore" placeholder="예: 850" required>
        </div>

        <!-- 첨부파일 -->
        <div class="mb-3">
            ※ 영어 점수 등록 시, 아래 사항을 반드시 준수해 주시기 바랍니다:
            <ul>
                <li>반드시 <strong>성적표</strong> 또는 <strong>증명서</strong>를 첨부해 주시기 바랍니다.</li>
                <li>첨부파일은 <strong>학번-TOEIC.pdf</strong> 형식으로 제출해 주십시오.</li>
                <li>예: <strong>2024100017-TOEIC.pdf</strong></li>
                <li>첨부파일이 없거나 형식이 맞지 않으면 등록이 승인되지 않습니다.</li>
            </ul>
            <input type="file" multiple class="form-control" name="uploadFiles" required>
        </div>

        <!-- 등록 버튼 -->
        <div class="d-flex justify-content-end">
            <button type="button" id="submitBtn" class="btn btn-primary">등록</button>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/resources/js/graduation/graduationForm.js"></script>
