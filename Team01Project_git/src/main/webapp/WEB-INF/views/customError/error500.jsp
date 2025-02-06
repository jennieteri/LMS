<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<style>
    body {
        margin: 0;
        font-family: 'Arial', sans-serif;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
        background-color: #f8f9fa;
    }

    .error-container {
        text-align: center;
    }

    .error-container .error-code {
        font-size: 120px; /* 숫자 크기 */
        font-weight: 600; /* 숫자 두께 */
        color: #003366;
        margin: 0;
    }

    .error-container .error-title {
        font-size: 24px;
        font-weight: bold;
        color: #333;
        margin: 16px 0;
    }

    .error-container .error-message {
        font-size: 16px;
        color: #555;
        margin-bottom: 24px;
    }

    .error-container .btn {
        display: inline-block;
        padding: 10px 20px;
        background-color: #003366;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    .error-container .btn:hover {
        background-color: #0056b3;
    }
</style>

<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <div class="error-title">서버 내부 오류가 발생했습니다.</div>
        <div class="error-message">요청을 처리하던 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.</div>
        <a href="${pageContext.request.contextPath}" class="btn">홈으로 돌아가기</a>
    </div>
    
    
    <input type="hidden" id="cp" value="${pageContext.request.contextPath}">
</body>
</html>
