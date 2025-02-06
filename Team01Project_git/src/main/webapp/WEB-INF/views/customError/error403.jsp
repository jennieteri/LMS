<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Forbidden</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Cabin:wght@400;700&family=Montserrat:wght@500;700&display=swap" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
        body {
            margin: 0;
            font-family: 'Cabin', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .error-container {
            text-align: center;
        }

        .error-code {
            font-size: 96px;
            font-weight: 700;
            color: #003366; /* 연세대 색상 */
            margin: 0;
        }

        .error-message {
            font-size: 24px;
            font-weight: bold;
            margin: 16px 0;
        }

        .error-description {
            font-size: 16px;
            color: #666;
            margin-bottom: 24px;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            background-color: #003366;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">403</div>
        <p class="error-message">접근이 제한되었습니다.</p>
        <p class="error-description">
            이 페이지에 접근할 권한이 없습니다.<br>
            올바른 권한으로 로그인했는지 확인하시거나, 관리자에게 문의하세요.
        </p>
        <a href="${pageContext.request.contextPath}" class="btn">홈으로 돌아가기</a>
    </div>
</body>
</html>
