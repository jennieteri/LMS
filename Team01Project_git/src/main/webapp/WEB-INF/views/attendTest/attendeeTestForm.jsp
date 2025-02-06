<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>시험</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            font-family: 'Arial', sans-serif;
        }

        .container-fluid {
            display: flex;
            min-height: 100vh;
        }

        #leftsideNev {
                   position: fixed;
        top: 0;
        left: 0;
        width: 220px;
        height: 100vh;
        background-color: #e9ecef;
        border-right: 1px solid #dee2e6;
        padding: 1rem;
        overflow-y: auto;  /* 내부 스크롤 추가 */
        }

        .main-content {
            flex-grow: 1;
        padding: 2rem;
        padding-left: 240px;  /* 네비게이션 바 너비만큼 여백 추가 */
        background-color: white;
        }

        .timer-box {
           background-color: #dc3545;
        color: white;
        padding: 1rem;
        border-radius: 8px;
        text-align: center;
        margin-top: 1rem;
        margin-bottom: 1rem;
        }

        .question-nav-button {
            width: 100%;
            padding: 0.75rem;
            border-radius: 8px;
            background-color: #007bff;
            color: white;
            border: none;
            transition: background-color 0.2s;
             margin-bottom: 0.5rem;
        }

        .question-nav-button:hover {
            background-color: #0056b3;
        }

        .submit-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 0.75rem;
            border-radius: 8px;
            width: 100%;
            margin-top: 1rem;
        }

        .question-card {
                 background-color: #fff;
        border: 1px solid #dee2e6;
        border-radius: 12px;
        padding: 1.5rem;
        margin-bottom: 2rem;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #007bff;
        color: white;
        padding: 1rem;
        border-radius: 12px 12px 0 0;
        }
  /* 문제와 선택지 간격 조정 */
    .card-body {
        padding-top: 1rem;
    }
        .list-group-item {
            padding: 1rem;
            border-radius: 6px;
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- 좌측 네비게이션 -->
        <div id="leftsideNev">
            <div class="row g-2">
                <c:forEach items="${studentTestVo.questionVO}" var="que" varStatus="qNum">
                    <div class="col-4">
                        <!-- 버튼을 한 줄에 3개씩 배치 -->
                        <button class="question-nav-button"
                                onclick="scrollToQuestion('question-${qNum.count}')">
                            <h4>${qNum.count}</h4>
                        </button>
                    </div>
                </c:forEach>
            </div>
            <input type="hidden" id="testDt" value="${studentTestVo.testDt }">
            <input type="hidden" id="testEt" value="${studentTestVo.testEt }">
            <div class="timer-box">
                <h5 class="mb-0">남은 시간</h5>
                <div id="timer" class="h3 mb-0">00:00:00</div>
            </div>
<!--             <button type="submit" class="submit-btn">시험 제출</button> -->
            <button type="button" class="submit-btn" onclick="insertAnswer()"> 자동입력</button>
            <button type="button" class="submit-btn" onclick="showTestConfirm()">시험 제출</button>
        </div>

        <!-- 메인 콘텐츠 -->
        <div class="main-content">
            <form:form modelAttribute="newQuestion" method="POST">
                <c:forEach items="${studentTestVo.questionVO}" var="que" varStatus="qNum">
                    <div id="question-${qNum.count}" class="question-card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h4 class="mb-0">문제 ${qNum.count}</h4>
                            <span class="badge bg-light text-dark">${que.queType}</span>
                        </div>
                        <div class="card-body">
                            <p class="lead">${que.queDescr}</p>
                            <c:choose>
                                <c:when test="${que.queType eq '객관식'}">
                                    <div class="list-group">
                                        <c:forEach items="${que.answerVO}" var="ans" varStatus="aNum">
                                            <label class="list-group-item">
                                                <input type="radio" class="form-check-input me-3"
                                                       name="${que.queNo}" value="${aNum.count}">
                                                ${aNum.count}. ${ans.anchDescr}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:when test="${que.queType eq '주관식'}">
                                    <input type="text" class="form-control" name="${que.queNo}">
                                </c:when>
                                <c:when test="${que.queType eq '서술형'}">
                                    <textarea class="form-control" rows="4" name="${que.queNo}"></textarea>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </form:form>
        </div>
    </div>

    <!-- JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/test/attendeeTestFormScript.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

    <!-- 스크롤 이동 함수 -->
    <script>
        function scrollToQuestion(id) {
            const element = document.getElementById(id);
            if (element) {
                window.scrollTo({
                    top: element.offsetTop - 20,
                    behavior: 'smooth'
                });
            }
        }
    </script>
</body>
</html>
