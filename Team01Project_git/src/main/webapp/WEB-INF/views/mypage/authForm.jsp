<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<security:authentication property="principal.username" var="id"/>
<input type="hidden" value="${my.id }" id="id" />
<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>

    
    <title>비밀번호 확인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            height: 100vh; /* 화면 전체 높이 */
            display: flex;
            justify-content: center; /* 가로 중앙 정렬 */
            align-items: center; /* 세로 중앙 정렬 */
        }

        .form-container {
            background-color: white;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }

        .form-container form {
            display: flex;
            align-items: center; /* 입력창과 버튼을 수직 가운데 정렬 */
            justify-content: center;
        }

        .form-container label {
            font-weight: bold;
            margin-right: 10px;
            font-size: 16px;
        }

        .form-container input {
            width: 150px; /* 입력창 크기 조정 */
            padding: 8px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 14px;
            margin-right: 10px;
        }

        .form-container button {
            padding: 8px 15px;
            font-size: 14px;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            color: white;
            cursor: pointer;
        }

        .form-container button:hover {
            background-color: #0056b3;
        }

        .form-container .error {
            color: #dc3545;
            font-size: 14px;
            margin-top: 15px;
            text-align: center;
        }
    </style>

    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/mypage/${id}/auth">
            <label for="pswd">비밀번호:</label>
            <input type="password" name="pswd" id="pswd" required>
            <button type="submit" class="btn btn-primary">확인</button>
        </form>
        <c:if test="${not empty message}">
            <div class="error">${message}</div>
        </c:if>
    </div>

