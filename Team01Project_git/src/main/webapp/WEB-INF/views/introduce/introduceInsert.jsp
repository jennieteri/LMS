<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>자기소개서 추가</title>
    <style>
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group textarea {
            resize: vertical;
        }

        .form-actions {
            text-align: center;
        }

        .form-actions button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .btn-submit {
            background-color: #007bff;
            color: white;
        }

        .btn-submit:hover {
            background-color: #0056b3;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
            margin-left: 10px;
        }

        .btn-cancel:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>자기소개서 추가</h2>
        <form action="<c:url value='/introduce/insert' />" method="post">
            <div class="form-group">
                <label for="stuId">학번</label>
                <input type="text" id="stuId" name="stuId" required>
            </div>

            <div class="form-group">
                <label for="empfiCd">취업 희망 분야 코드</label>
                <select id="empfiCd" name="empfiCd" required>
                    <option value="">-- 선택 --</option>
                    <c:forEach items="${employmentFields}" var="field">
                        <option value="${field.empfiCd}">${field.empfiNm}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="intQue1">지원 동기</label>
                <textarea id="intQue1" name="intQue1" rows="4" required></textarea>
            </div>

            <div class="form-group">
                <label for="intQue2">입사 후 포부</label>
                <textarea id="intQue2" name="intQue2" rows="4" required></textarea>
            </div>

            <div class="form-group">
                <label for="intQue3">성격 및 장단점</label>
                <textarea id="intQue3" name="intQue3" rows="4" required></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">저장</button>
                <a href="<c:url value='/introduce/list' />" class="btn-cancel">취소</a>
            </div>
        </form>
    </div>
</body>
</html>
