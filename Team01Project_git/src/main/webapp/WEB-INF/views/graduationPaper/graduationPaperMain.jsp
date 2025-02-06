<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">졸업</li>
    <li class="breadcrumb-item active" aria-current="page">논문 제출 안내</li>
  </ol>
</nav>
    
    <div class="ontainer mt-5">
        <br>
        <div class="alert alert-primary">
        <h5 class="fw-bold text-center">※ 연근대학교 논문 요건</h5>
        <br>
            졸업 자격을 충족하기 위해 <b>논문 제출</b>이 필수입니다. 아래의 안내 사항을 참고하여 논문을 제출해 주시기 바랍니다.
        <ul class="info-list">
            <li>논문은 학과가 지정한 주제를 기반으로 작성되어야 합니다.</li>
            <li>최소 3페이지 이상의 분량을 작성해야 합니다.</li>
            <li>파일 형식은 PDF형식으로 제출해야 합니다.</li>
            <li>논문 제목과 파일명은 <b>"학번_이름_논문제목.pdf"</b> 형식으로 저장하십시오.</li>
        </ul>
        <div class="important-note">
            논문 제출 기한을 넘길 경우 졸업 요건을 충족하지 못할 수 있습니다. 제출 전, 학과 담당자와 반드시 상의하시기 바랍니다.
        </div>
        <p class="contact-info">
            문의사항은 학사관리팀(학사 담당: 042-564-1234) 또는 이메일(support@univ.ac.kr)로 문의해 주십시오.
        </p>
        </div>
		<div class="container mt-5 d-flex justify-content-end">
		    <button id="check" class="btn btn-primary me-2">조회</button> <!-- 오른쪽 여백 추가 -->
		    <button id="insert" class="btn btn-primary">등록</button>
		</div>
    </div>
    
       
<input type="hidden" id="cp" value="${pageContext.request.contextPath}">
<input type="hidden" id="principal" value="${principal}"> 
    
<script src="${pageContext.request.contextPath }/resources/js/graduationPaper/graduationPaperMain.js"></script>
</body>
</html>
