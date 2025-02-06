<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">졸업</li>
    <li class="breadcrumb-item active" aria-current="page">졸업 인증제 제출 안내</li>
  </ol>
</nav>


    <div class="container mt-5">
        <br>
        <!-- 졸업 요건 안내 -->
        <div class="alert alert-primary">
            <h5 class="fw-bold text-center">※ 연근대학교 졸업 요건</h5>
            <br>
            <p>졸업 자격 획득을 위해 다음 요건을 충족해야 합니다:</p>
            <ul>
                <li><strong>영어점수 (TOEIC):</strong> 750점 이상 (공인 인증 성적표 제출 필수)</li>
                <li><strong>봉사활동:</strong> 75시간 이상 (인증 기관 확인서 제출 필수)</li>
            </ul>
            <p class="mt-3">궁금한 사항은 학사관리팀(학사 담당: 042-564-1234)으로 문의해 주십시오.</p>
        </div>
      </div>
       
 	<input type="hidden" id="cp" value="${pageContext.request.contextPath}">
 	<input type="hidden" id="principal" value="${principal}"> 
<div class="container mt-5 d-flex justify-content-end">	
<button type="button" class="btn btn-primary" id="check">조회</button>
</div> 

<script src="${pageContext.request.contextPath }/resources/js/graduation/graduationMain.js"></script>

