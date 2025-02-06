<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/sweetalert/dist/sweetalert.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert/dist/sweetalert.min.js"></script>
    <style>
        body {
            font-family: 'Noto Sans', Arial, sans-serif;
            margin: 20px auto;
            max-width: 800px;
            background-color: #f7f9fc;
            color: #333;
            line-height: 1.6;
        }
        h2 {
            text-align: center;
            color: #4a90e2;
            margin-bottom: 30px;
        }
        .description {
            text-align: center;
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 15px;
            border-bottom: 1px solid #eee;
            text-align: center;
        }
        th {
            background-color: #f1f5f9;
            font-size: 14px;
            color: #555;
            font-weight: bold;
        }
        td {
            font-size: 13px;
            color: #555;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .radio-group {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .radio-group label {
            font-size: 13px;
            color: #555;
            cursor: pointer;
        }
        .radio-group input[type="radio"] {
            margin-right: 5px;
        }
        .btn-primary {
            display: block;
            margin: 20px auto;
            padding: 12px 25px;
            font-size: 16px;
            background-color: #4a90e2;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        .btn-primary:hover {
            background-color: #357abd;
        }
    </style>
</head>
<body>
    <h2>직업 선호도 검사</h2>           
    <p class="description">아래 문항에 대해 각 항목에 가장 적합한 선택지를 골라주세요.<br>검사 결과는 이후의 직업 추천에 활용됩니다.</p>
    <button type="button" class="btn-primary" onclick="ran()">랜덤 선택</button>
<form id="testForm" action="/yguniv/jobtest/submit" method="post">
    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>검사 문항</th>
                <th colspan="4">답변</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}">
                <tr>
                    <td>${item.rnum}</td>
                    <td>${item.jobTestText}</td>
                    <td>
                        <div class="radio-group">
                            <label>
                                <input type="radio" name="answer_${item.jobTestNo}" value="30" 
                                    data-type="${item.jobTestType}" required>
                                좋아함
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="radio-group">
                            <label>
                                <input type="radio" name="answer_${item.jobTestNo}" value="15" 
                                    data-type="${item.jobTestType}" required>
                                관심없음
                            </label>
                        </div>
                    </td>
                    <td>
                        <div class="radio-group">
                            <label>
                                <input type="radio" name="answer_${item.jobTestNo}" value="5" 
                                    data-type="${item.jobTestType}" required>
                                싫어함
                            </label>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        
    </table>
    
    <input type="hidden" id="cp" value="${pageContext.request.contextPath }">
    <input type="hidden" id="testResults" name="testResults" value="${principal }"> 
    <button type="button" class="btn-primary" onclick="submitTest()">제출</button>
</form>
</body>
<script src="${pageContext.request.contextPath }/resources/js/jobtest/jobTestList.js"></script>
