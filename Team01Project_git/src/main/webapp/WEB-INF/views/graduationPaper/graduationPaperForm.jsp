<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
        .step-indicator {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .step {
            text-align: center;
            flex: 1;
            position: relative;
        }
        .step:not(:last-child)::after {
            content: "→";
            position: absolute;
            top: 50%;
            right: -15px;
            transform: translateY(-50%);
            font-size: 20px;
            color: #ccc;
        }
        .step .icon {
            width: 50px;
            height: 50px;
            margin: 0 auto 10px auto;
            border: 2px solid #ccc;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            color: #999;
        }
        .step.active .icon {
            border-color: #007bff;
            color: #007bff;
        }
        .step.active {
            font-weight: bold;
            color: #007bff;
        }
        .section-header {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
	    .info-box {
	        background: #f0f4f8; /* 밝은 회색-파란색 배경 */
	        border: 1px solid #ccc;
	        padding: 15px;
	        border-radius: 5px;
	        margin-bottom: 20px;
	    }
	    .form-check-label {
	        color: #333;       /* 텍스트 색상 명확히 */
	    }
	    .form-check-input {
	        margin-right: 5px; /* 버튼과 라벨 간격 */
	    }

        .form-section {
            margin-top: 30px;
        }
    </style>
</head>
<body>

    <div class="container mt-5">
        <!-- 페이지 헤더 -->
        <div class="section-header text-center">제출자 정보 확인</div>
        
        <!-- 단계 표시 -->
        <div class="step-indicator">
            <div class="step active">
                <div class="icon">1</div>
                제출자 정보 확인
            </div>
            <div class="step">
                <div class="icon">2</div>
                논문 등록
            </div>
            <div class="step">
                <div class="icon">3</div>
                제출 완료
            </div>
        </div>
        
        <!-- 개인정보 동의 -->
      <div class="info-box">
            <strong>개인정보 수집 및 이용에 대한 동의</strong>
            <ul>
                <li>수집된 정보는 학위논문 제출 절차를 위해 활용됩니다.</li>
                <li>개인정보는 관련 법령에 따라 안전하게 관리됩니다.</li>
                <li>수집되는 항목: 아이디, 이름, 학번, 이메일, 소속 등.</li>
            </ul>
            <div class="mt-3">
                <label>개인정보 수집 및 이용에 대해</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="consent" id="consentYes" value="yes">
                    <label class="form-check-label" for="consentYes">동의</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="consent" id="consentNo" value="no">
                    <label class="form-check-label" for="consentNo">비동의 합니다.</label>
                </div>
            </div>
        </div>
	</div>
	  <div class="form-section">
	   <br>
	   <form id="graduationPaperForm">
	       <!-- 제출자 정보 -->
	       <div class="row mb-3">
	           <div class="col-md-6">
	               <label for="studentId" class="form-label">학번</label>
	               <input type="text" id="studentId" name="stuId" class="form-control" value="${auth.principal.realUser.id}" disabled>
	           </div>
	           
	           <div class="col-md-6">
	               <label for="name" class="form-label">이름(국문)</label>
	               <input type="text" id="name" class="form-control" value="${auth.principal.realUser.nm }" disabled>
	           </div>
	       </div>
	       
	       <div class="row mb-3">
	           <div class="col-md-6">
	               <label for="studentph" class="form-label">연락처</label>
	               <input type="text" id="studentId" name="stuId" class="form-control" value="${auth.principal.realUser.mbtlnum}">
	           </div>
	           
	           <div class="col-md-6">
	               <label for="studentemail" class="form-label">이메일</label>
	               <input type="text" id="name" class="form-control" value="${auth.principal.realUser.eml}">
	           </div>
	       </div>
	       
	       <!-- 안내 메시지 -->
		<div class="alert alert-light mt-3" role="alert">
		    <ul class="mb-0">
		        <li>마이페이지에 등록된 연락처/이메일입니다. 연락처/이메일이 바뀌었으면 수정하시기 바랍니다.</li>
		        <li>이 정보는 제출하신 논문과 관련된 연락을 위해서만 사용됩니다.</li>
		    </ul>
		</div>
			
	    </form>
	</div>
		<div class="d-flex justify-content-end mt-4 custom-align-right">
		    <button class="btn btn-primary" id="next">다음</button>
		</div>

</body>
		
       <input type="hidden" id="cp" value="${pageContext.request.contextPath}">
<script src="${pageContext.request.contextPath }/resources/js/graduationPaper/graduationPaperForm.js"></script>
