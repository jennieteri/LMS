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
    .file-restriction {
        font-size: 0.9rem;
        color: #6c757d;
    }
</style>
</head>
<body>

    <div class="container mt-5">
        <!-- 페이지 헤더 -->
        <div class="section-header text-center">학위논문 등록</div>
        
        <!-- 단계 표시 -->
        <div class="step-indicator">
            <div class="step">
                <div class="icon">1</div>
                제출자 정보 확인
            </div>
            <div class="step active">
                <div class="icon">2</div>
                논문 등록
            </div>
            <div class="step">
                <div class="icon">3</div>
                제출 완료
            </div>
        </div>
        
        <!-- 논문 등록 폼 -->
        <div class="form-section">
            <br>
            <form id="graduationPaperForm" action="${pageContext.request.contextPath }/graduationpaper/submit" method="post" enctype="multipart/form-data">
                <!-- 논문 제목 -->
                <input type="hidden" id="stuId" name="stuId" class="form-control" value="${auth.principal.realUser.id}" readonly>
                <div class="mb-3">
                    <label for="gpaNm" class="form-label">제목 &nbsp<span style="color: red;">*</span></label>
                    <input type="text" id="gpaNm" name="gpaNm" class="form-control" placeholder="표지의 논문제목을 입력하세요" required>
                </div>
                
                <!-- 논문 부제목 -->
                <div class="mb-3">
                    <label for="gpaDnm" class="form-label">부제목</label>
                    <input type="text" id="gpaDnm" name="gpaDnm" class="form-control" placeholder="부제목을 입력하세요">
                </div>
                
                <!-- 논문 주제 -->
                <div class="mb-3">
                    <label for="gpaSub" class="form-label">논문 주제 &nbsp<span style="color: red;">*</span></label>
                    <input type="text" id="gpaSub" name="gpaSub" class="form-control" placeholder="1개 이상의 주제어 입력시에는 ',' (쉼표)로 구분하여 추가하세요 " required>
                </div>
                
                <!-- 논문 파일 업로드 -->
                <div class="mb-3">
                    <label for="uploadFile" class="form-label">논문 파일 (PDF 형식만) &nbsp<span style="color: red;">*</span></label>
                    <input type="file" id="uploadFiles" name="uploadFiles" class="form-control" accept="application/pdf" required>
                    <div class="file-restriction">* PDF 파일만 업로드 가능합니다.</div>
                </div>
                <br>
                <div class="d-flex justify-content-between mt-4">
				    <button class="btn btn-secondary" id="previousBtn">이전</button>
				    <button type="submit" id="submitBtn" class="btn btn-primary">제출</button>
				</div>

            </form>
        </div>
    </div>
		<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<script src="${pageContext.request.contextPath }/resources/js/graduationPaper/graduationPaperForm1.js"></script>
</body>
</html>
