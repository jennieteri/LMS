<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>연근대학교</title>
<link href="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/yglogo.png" rel="icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>
html, body{
	width: 100%;
	height: 100%;
}

body {
	font-family: Arial, sans-serif;
	text-align: center;
	align-content: center;
	background-color: #0033A0;
}

.login-container {
	display: inline-block;
	width: 300px;
	text-align: center;
}

input {
	width: 100%;
	padding: 10px;
	margin: 10px 0;
	box-sizing: border-box;
}

button {
	width: 100%;
	padding: 10px;
	background-color: #004A99;
	color: white;
	border: none;
	cursor: pointer;
}

button:hover {
	background-color: #003366;
}

a {
	text-decoration: none;
	color: #004A99;
}

a:hover {
	text-decoration: underline;
}

.timer {
            font-weight: bold;
            color: red;
            margin-left: 10px;
        }
        
</style>
</head>
<body>
	<input type="hidden" value="${error }" id="error">
	<input type="hidden" value="${exception }" id="exception">
	<div class="backGround" style="background-color: white; width: 500px; height: 700px; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 20px; border-radius: 8px; margin: auto;">
		<div class="login-container">
			<div>
				<img src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/yglogo.png" alt="연근대학교" width="100" class="mb-3">
			</div>
			<h2 style="font-weight: bolder;">2차인증페이지</h2>
			<!-- 로그인 폼  -->
			<div id="auth-section" data-path="${pageContext.request.contextPath }">
				<div class="form-floating mb-4">
					<button class="btn btn-primary mb-3" id="requestAuthCodeBtn" >인증번호요청</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath }/resources/js/login/secondAuth.js"></script>

</html>

