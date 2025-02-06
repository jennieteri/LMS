<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">자소서 클리닉</li>
    <li class="breadcrumb-item active" aria-current="page">자기소개서 작성</li>
  </ol>
</nav>

<body>
    <div class="container mt-5">
        <div>
        <h4 class="text-center">자기소개서 작성</h4>
        <br>
          <p > ※&emsp;한번 제출한 자소서는 수정이 불가하오니 신중하게 작성해주시길 바랍니다. </p>
        </div>
        
        <!-- 비동기로 보낼 거임 -->
        <form id="introduceForm" enctype="multipart/form-data">
            <!-- 학번 -->
            <div class="mb-3">
                <input type="hidden" id="stuId" name="stuId" value="${stuId}">
            </div>

            <!-- 취업 희망 분야 -->
            <div class="mb-3">
                <label for="empfiCd" class="form-label">취업 희망 분야</label>
                <select class="form-control" id="empfiCd" name="empfiCd" required>
                    <option value="">-- 선택 --</option>
                    <option value="EM001">정보기술</option>
                    <option value="EM002">마케팅</option>
                    <option value="EM003">인사관리</option>
                    <option value="EM004">금융</option>
                    <option value="EM005">의료</option>
                    <option value="EM006">제조업</option>
                    <option value="EM007">교육</option>
                    <option value="EM008">법률</option>
                    <option value="EM009">건설</option>
                    <option value="EM010">예술</option>
                </select>
            </div>

            <!-- 자기소개서 항목 -->
            <div class="mb-3">
                <label for="intQue1" class="form-label">지원 동기</label>
               <textarea class="form-control intlimit" id="intQue1" name="intQue1" rows="4" placeholder="600자 이하로 작성해주세요." required></textarea>
            </div>

            <div class="mb-3">
                <label for="intQue2" class="form-label">입사 후 포부</label>
                <textarea class="form-control intlimit" id="intQue2" name="intQue2" rows="4" placeholder="600자 이하로 작성해주세요." required></textarea>
            </div>

            <div class="mb-3">
                <label for="intQue3" class="form-label">성격 및 장단점</label>
                <textarea class="form-control intlimit" id="intQue3" name="intQue3" rows="4" placeholder="600자 이하로 작성해주세요." required></textarea>
            </div>

			<div class="certificates-container mt-5">
				<h4 class="text-center">자격증 추가</h4>
				<br>
			<div>
					<p>
						※&emsp;자격증 정보 입력 시, 아래 사항을 반드시 준수해 주시기 바랍니다: <br>
						<br> 1. 실제 취득한 자격증 정보만 입력해 주시고, 관련 증빙 서류(첨부파일)를 함께 제출해 주시기
						바랍니다.
					<br>
						<br> 2. 첨부파일은 반드시 <strong>학번-자격증명</strong> 형식으로 이름을 지정하여 제출해 주십시오. <br> &emsp;&emsp; ex) <strong>2024100017-정보처리기사.pdf</strong>
						<br>
						<br> 3. 제출된 증빙 서류는 자격증 확인 절차에 활용되며, 허위 정보 제출 시 불이익을 받을 수
						있습니다.
					</p>

				</div>
				<div id="certificatesContainer">
				<button type="button" class="btn btn-primary mt-3"
					onclick="addCertificate()">자격증 추가</button>
			</div>
			</div>
        	<br>
        	<br>

			<!-- 제출 버튼 -->
            <div class="mt-4 text-end">
                <button type="button" class="btn btn-success" id="saveBtn">저장</button>
                <a href="<c:url value='/introduce/list/${stuId }' />" class="btn btn-secondary">취소</a>
            </div>
        </form>
    </div>

<script src="${pageContext.request.contextPath }/resources/js/introduce/introduceForm.js"></script>

</body>
</html>
