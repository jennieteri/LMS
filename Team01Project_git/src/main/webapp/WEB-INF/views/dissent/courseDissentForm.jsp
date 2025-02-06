<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>이의 등록</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.main-title {
	background-color: #0d6efd;
	color: white;
	padding: 1.5rem 0;
	margin-bottom: 2rem;
}

.content-section {
	background: #ffffff;
	border: 1px solid #dee2e6;
	border-radius: 4px;
	padding: 1.5rem;
	margin-bottom: 1.5rem;
}

.score-table {
	width: 100%;
	border-collapse: collapse;
}

.score-table th, .score-table td {
	padding: 8px;
	text-align: center;
}

.score-table th {
	font-weight: 600;
}

.form-group {
	margin-bottom: 1rem;
}

.form-label {
	font-weight: 600;
	margin-bottom: 0.5rem;
}

.table {
	margin-bottom: 0;
}

.table th {
	font-weight: 600;
	vertical-align: middle;
}

.form-select.border-0:focus {
	box-shadow: none;
}

.form-control-plaintext {
	margin-bottom: 0;
}
</style>
</head>
<c:set value="${semesterLectureDetail }" var="coeva"></c:set>
<body class="bg-light">
	<div class="main-title">
		<h2 class="text-center mb-0"><strong>이의 평가</strong></h2>
	</div>
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath }">
	<div class="container">
		<div class="content-section">
			<table class="table table-bordered table-primary">
			    <tr>
			        <th class="text-center" style="width: 15%; ">강의명</th>
			        <td class="text-center" style="width: 35%; background-color:white;">
			            <input type="text" class="form-control-plaintext px-2" value="${coeva.lectureVO.lectNm }" readonly>
			        </td>
			        <th class="text-center" style="width: 15%; ">학번</th>
			        <td class="text-center" style="width: 35%; background-color: white;">
			            <input type="text" class="form-control-plaintext px-2" value="${stuId }" readonly>
			        </td>
			    </tr>
			    <tr>
			        <th class="text-center" style="">교수명</th>
			        <td class="text-center" style=" background-color: white;">
			            <input type="text" class="form-control-plaintext px-2" value="${coeva.lectureVO.professorVO.nm }" readonly>
			        </td>
			        <th class="text-center" style="">이름</th>
			        <td class="text-center" style=" background-color: white;">
			            <input type="text" class="form-control-plaintext px-2" value="${stuNm }" readonly>
			        </td>
			    </tr>
			</table>

		</div>



		<div class="content-section">
			<table class="table table-bordered table-primary score-table">
				<tr>
					<th>학점</th>
					<th>출석</th>
					<th>과제</th>
					<th>중간</th>
					<th>기말</th>
					<th>기타</th>
					<th>성적</th>
				</tr>
				<tr>
					<td style="background-color: white;">${coeva.lectureVO.lectScore } </td>
					<td style="background-color: white;">${coeva.attenAtndScore }</td>
					<td style="background-color: white;">${coeva.assigScore }</td>
					<td style="background-color: white;">${coeva.prTestScore }</td>
					<td style="background-color: white;">${coeva.ftTestScore }</td>
					<td style="background-color: white;">${coeva.etcScore }</td>
					<td style="background-color: white;">${coeva.attenScore }</td>
				</tr>
			</table>
		</div>
		<c:if test="${not empty sVo }">
			<div class="content-section">
				<label class="form-label fw-bold" style="font-size: 1.5rem;">신청 사유</label>
				<textarea class="form-control" rows="5" id="coevaBox" readonly="readonly"
					placeholder="이의 평가는 교수님이 직접 확인합니다.">${sVo.objcCn }</textarea>
			</div>
			
			<c:if test="${not empty sVo.answerCn }">
				<div class="content-section">
				<label class="form-label fw-bold" style="font-size: 1.5rem;">교수 답변</label>
					<textarea class="form-control" rows="5" id="coevaBox" readonly="readonly"
						placeholder="이의 평가는 교수님이 직접 확인합니다.">${sVo.answerCn }</textarea>
				</div>
			
			</c:if>
			<c:if test="${empty sVo.answerCn }">
				<div class="content-section">
				<label class="form-label fw-bold" style="font-size: 1.5rem;">교수 답변</label>
					<textarea class="form-control" rows="5" id="coevaBox" readonly="readonly"
						placeholder="등록된 답변이 없습니다."></textarea>
				</div>			
			</c:if>

		</c:if>
		<c:if test="${empty sVo }">
			<div class="content-section">
				<label class="form-label fw-bold" style="font-size: 1.5rem;">신청 사유</label>
				<textarea class="form-control" rows="5" id="coevaBox"
					placeholder="이의 평가는 교수님이 직접 확인합니다."></textarea>
			</div>
		<div class="text-center">
			<button type="submit" class="btn btn-primary" onclick="dissentBtn('${stuId}','${coeva.lectureVO.lectNo}')">등록</button>
			<button type="submit" class="btn btn-primary" onclick="insertStr()">평가 입력</button>
		</div>
		</c:if>
		
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/js/dissent/attendeeDissentScript.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

</body>
</html>




<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script> -->


