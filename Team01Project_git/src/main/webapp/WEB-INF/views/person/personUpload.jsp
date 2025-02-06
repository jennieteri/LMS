<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">회원 관리</li>
    <li class="breadcrumb-item active" aria-current="page">다중 사용자 추가</li>
  </ol>
</nav>

<body>
<div class="container mt-5">
   <p class="text-muted text-center mt-5">작성 형식을 유의해 업로드 폼을 다운로드하여 작성 후 업로드하세요.</p>

   <div id="noticeSection" class="alert alert-primary mt-4" role="alert" style="width: 80%; margin: 0 auto;">
      <h5>※ &nbsp; 작성 시 유의사항</h5>
      <ul>
         <li>모든 <span class="text-danger"><strong>필수 항목</strong></span>을 빠짐없이 작성해야 합니다.</li>
         <li>생년월일은 반드시 <strong>YYYY-MM-DD</strong> 형식으로 입력해야 합니다.</li>
         <li>성별 코드는 <strong>M</strong> 또는 <strong>F</strong> 중 하나를 선택하세요.</li>
         <li>학적 상태 및 학과 코드는 시스템에서 제공된 값을 정확히 입력해야 합니다.</li>
         <li>입력 항목이 올바르지 않으면 업로드에 실패할 수 있습니다.</li>
      </ul>
   </div>


   <!-- 데이터 설명 테이블 (숨김 상태로 시작) -->
   <div id="formatSection" style="margin-top: 20px;">
      <table class="table table-bordered table-sm" style="width: 80%; margin: 0 auto;">
         <thead class="table-primary">
            <tr>
               <th class="text-center">필드 이름</th>
               <th class="text-center"> 설명</th>
               <th class="text-center">필수 여부</th>
            </tr>
         </thead>
         <tbody>
            <tr>
               <td class="text-center"><strong>학번</strong></td>
               <td>학번.사용자 고유 식별자(숫자)</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>이름</strong></td>
               <td>사용자 이름</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>생년월일</strong></td>
               <td> 사용자 생년월일. <strong>YYYY-MM-DD</strong> 형식.</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>성별</strong></td>
               <td>성별 코드. <strong>M</strong> 또는 <strong>F</strong> 중 입력.</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>학년</strong></td>
               <td>학년 코드. 시스템에서 제공되는 학년 코드 사용.</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>학적</strong></td>
               <td>학적 상태 코드. 시스템에서 제공된 학적 상태 코드 입력.</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
            <tr>
               <td class="text-center"><strong>학과</strong></td>
               <td>학과 코드. 시스템에서 제공된 학과 코드 입력.</td>
               <td class="text-center"><span class="text-danger" style="font-weight:bold;">필수</span></td>
            </tr>
         </tbody>
      </table>
   </div>

   <!-- 업로드 폼 다운로드 -->
   <div class="text-center mt-4">
      <a href="${pageContext.request.contextPath}/person/templateDownload" class="btn btn-primary">
         <i class="bi bi-download"></i> 작성 업로드 폼 다운로드
      </a>
   </div>

   <!-- 파일 업로드 폼 -->
   <form id="bulkUploadForm" action="${pageContext.request.contextPath }/person/bulkUpload" method="post" enctype="multipart/form-data" style="width: 80%; margin: 20px auto;">
      <div class="mb-3">
         <label for="file" class="form-label"><strong>다중사용자 파일 업로드</strong></label>
         <input type="file" class="form-control" id="file" name="file" accept=".xls, .xlsx, .ods" required>
      </div>
      <div class="text-center">
         <button type="submit" class="btn btn-success">
            <i class="bi bi-upload"></i> 업로드
         </button>
      </div>
   </form>

   <!-- 업로드 상태 메시지 -->
   <div id="uploadResult" style="margin-top: 20px; width: 80%; margin: 0 auto;">
      <!-- 업로드 성공/실패 메시지가 여기 출력됩니다. -->
   </div>
</div>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
</body>

<script src="${pageContext.request.contextPath }/resources/js/person/personUpload.js"></script>


