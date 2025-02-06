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

.id, .pswd {
	border-radius: 50%;
}

/* 입력 필드 스타일 */
.input-label {
  font-weight: bold;
  margin-bottom: 0;
  width: 80px; /* 라벨의 고정 너비 */
  text-align: right; /* 오른쪽 정렬 */
}

.input-field {
  height: 36px; /* 입력 필드 높이 */
  font-size: 14px;
  padding: 5px 10px;
  border-radius: 4px;
  width: 60%; /* 입력 필드 너비 조정 */
}

/* 버튼 스타일 */
.button-group {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

.button-sm {
  font-size: 14px;
  padding: 5px 15px;
  width: auto;
}

/* 결과 표시 스타일 */
.result-container {
  margin-top: 10px;
}

.result-field {
  height: 40px;
  font-weight: bold;
  font-size: 25px;
  text-align:center;
  color: #333;
  padding: 5px 10px;
}



</style>
</head>
<body>

	<input type="hidden" value="${pageContext.request.contextPath }" id="cp">
	<input type="hidden" value="${error }" id="error">
	<input type="hidden" value="${exception }" id="exception">

	
	<div class="backGround" style="background-color: white; width: 600px; height:800px; display: flex; flex-direction: column; justify-content: center; 
																		         align-items: center; padding: 20px; border-radius: 8px; margin: auto;">
		<div class="login-container">
			<div>
				<img src="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/yglogo.png" alt="연근대학교" width="100" class="mb-3">
			</div>
			<h2 style="font-weight: bolder;">통합 로그인</h2>
			<br>
			<!-- 로그인 폼  -->
			<div class="form-floating mb-5">
				<form action="${pageContext.request.contextPath }/login" method="post" name="loginForm">
					<!-- 학번/ 사번 입력 -->
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="floatingInput" placeholder="학번/ 사번" name="id">
						<label for="floatingInput">학번 / 교번</label>
					</div>
					<!--비밀번호 입력   -->
					<div class="form-floating mb-4">
						<input type="password" class="form-control" id="floatingPassword" placeholder="비밀번호" name="pswd">
						<label for="floatingPassword">비밀번호</label>
					</div>
					<button class="mb-3" type="submit">로그인</button>
				</form>
				
				<!-- 학번 비밀번호 찾기  -->    
				<br>
				<p>
					<a  id="yjClr" class="mb-3 d-block" data-bs-toggle="modal" data-bs-target="#exampleModal" >학번 찾기 ></a><a href="#">비밀번호 변경 및 찾기 ></a>

				</p>
			</div>
		</div>
	</div>
	
	<!-- 학번 찾기 모달  -->
		<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header d-flex justify-content-center" style="background-color: #003399;">
			    <h1 class="modal-title fs-5" id="exampleModalLabel" style="color: white; text-align: center">학번 찾기</h1>
			    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

		     <div class="modal-body">
				  <div class="form-group d-flex align-items-center mb-3">
				    <label for="nm" class="input-label me-2">이름:</label>
				    <input type="text" id="nm" name="nm" class="form-control input-field" required>
				  </div>
				  <div class="form-group d-flex align-items-center mb-3">
				    <label for="brdt" class="input-label me-2">생년월일:</label>
				    <input type="date" id="brdt" name="brdt" class="form-control input-field" placeholder="YYYY-MM-DD" required>
				  </div>
				  <div class="button-group d-flex justify-content-end mt-3">
					    <button type="button" id="findButton" class="btn btn-primary button-sm me-2">확인</button>
					    <button type="button" class="btn btn-secondary button-sm" data-bs-dismiss="modal">취소</button>
					</div>

				 <div class="result-container">
				    <textarea name="result" id="result" class="form-control result-field" rows="3" readonly></textarea>
				</div>

				</div>
		
		    </div>
		  </div>
		</div>

	
	<script src="${pageContext.request.contextPath }/resources/js/login/login.js"></script>
	<script src="${pageContext.request.contextPath }/resources/js/login/findId.js"></script>
	

 
</body>
</html>


