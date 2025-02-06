<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            padding: 0;
            font-family: 'Cabin', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .error-container {
            text-align: center;
            padding: 20px;
        }

        .error-code {
            font-size: 120px; /* 숫자 크기 */
            font-weight: 800; /* 숫자 두께 */
            margin: 0;
            color: #003366; /* 연세대학교 메인 색상 */
        }

        .error-message {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin: 20px 0;
        }

        .error-description {
            font-size: 16px;
            color: #666;
            margin-bottom: 30px;
        }

        .return-link {
            text-decoration: none;
            color: white;
            background-color: #003366;
            padding: 10px 20px;
            border-radius: 4px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .return-link:hover {
            background-color: #0056b3;
        }
    </style>
<body>
    <div class="error-container">
        <div class="error-code">404</div>
        <div class="error-message">이런! 이 페이지는 졸업했나 봅니다.</div>
        <div class="error-description">학사 시스템 내 다른 페이지를 탐색하거나 홈으로 돌아가보세요.</div>
        <a href="${pageContext.request.contextPath}" class="return-link">홈으로 돌아가기</a>
    </div>
</body>
</html>
