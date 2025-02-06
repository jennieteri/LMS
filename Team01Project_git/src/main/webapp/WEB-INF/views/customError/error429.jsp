<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>429 - Too Many Requests</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Cabin:wght@400;700&family=Montserrat:wght@500;700&display=swap" rel="stylesheet">

    <!-- Custom CSS -->
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
            font-weight: 700; /* 숫자 두께 */
            margin: 0;
            color: #003366; /* 연세대학교 메인 색상 */
            font-family: 'Montserrat', sans-serif;
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
        
    .loading-wrap {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 300px;
    }
    .loading-spinner {
        width: 70px;
        height: 70px;
        border: 5px solid #3498db;
        border-top: 5px solid transparent;
        border-radius: 50%;
        animation: rotate 1s linear infinite;
    }

    @keyframes rotate {
        from { transform: rotate(0deg); }
        to { transform: rotate(360deg); }
    }
      .ex-box {
        padding: 10px;
        border: 1px solid black;
      }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="error-code">429</h1>
        <p class="error-message">Too Many Requests</p>
        <p class="error-description">
            요청이 너무 많습니다. 잠시 후 다시 시도해주세요.
        </p>
    <div class="loading-wrap">
      <div class="loading-spinner"></div>
      <p>페이지를 로딩 중입니다...</p>
    </div>
        <a href="${pageContext.request.contextPath}" class="return-link">홈으로 돌아가기</a>
    </div>

    <input type="hidden" id="cp" value="${pageContext.request.contextPath}">
    <script type="text/javascript">
        const cp = document.querySelector("#cp").value;
        console.log(cp);
        setTimeout(() => {
            window.location.href = `\${cp}/lectureCart/pre`;
        }, 2000);
    </script>
</body>
</html>
