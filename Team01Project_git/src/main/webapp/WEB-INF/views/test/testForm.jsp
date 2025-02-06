<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.container {
	max-width: 1000px;
	margin: auto;
	border: 1px solid #ccc;
	padding: 30px;
	border-radius: 10px;
	overflow: hidden;
}

.test-info {
	width: 45%;
	float: left;
	padding-right: 20px;
}

.date-time-section {
	width: 45%;
	float: right;
	padding: 10px;
	border-radius: 8px;
}

.date-time-section>div {
	margin-bottom: 20px;
}

.date-input, .time-input {
	width: 45%;
	padding: 8px;
	margin-top: 5px;
	border-radius: 10px;
}

.question-section {
	clear: both;
	margin-top: 30px;
	padding: 20px;
	border-radius: 10px;
}

/* 버튼 스타일링 */
button {
	padding: 10px 20px;
	margin: 10px;
	border: none;
	border-radius: 5px;
	background-color: #007bff;
	color: white;
	cursor: pointer;
}

button:hover {
	background-color: #0056b3;
}

/* 옵션 스타일링 */
.options {
	margin: 15px 0 0 20px;
}

.option {
	margin-bottom: 12px;
	padding: 8px;
}

/* 점수 입력 섹션 */
.score-section {
	margin-left: 20px;
	border-radius: 10px;
}

.score-input {
	border-radius: 10px;
	width: 80px;
	padding: 5px;
	margin-left: 10px;
}

.answer-div {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.score-section {
	display: flex;
	gap: 10px;
}

#dateInput {
	width: 362px;
}

#scorebox {
	width: 100px;
}

.draggable-question {
    padding: 10px;
    margin: 5px 0;
    border: 1px solid #ddd;
    background-color: #fff;
        cursor: default !important; /* 기본 커서로 변경 */
    user-select: none; /* 텍스트 선택 방지 */
}
/* 특정 영역에서만 드래그 허용 */
.drag-handle {
    cursor: move; /* 드래그 가능한 영역 표시 */
}
.draggable-question.dragging {
    opacity: 0.5;
    border: 2px dashed #666;
}
</style>
</head>
<%-- ${lectNo } --%>
<input type="hidden" id="lectNo" name="lectNo" value="${lectNo }">
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath }">

<div class="container">
	<div class="test-info child" id="asd">
		<div class="form-check">
			<input class="form-check-input" type="radio" name="testOnlineYn"
				id="flexRadioDefault2" value="N" checked> <label
				class="form-check-label" for="flexRadioDefault2">대면 시험 </label>
		</div>

		<div class="form-check">
			<input class="form-check-input" type="radio" name="testOnlineYn"
				value="Y" id="flexRadioDefault1" > <label
				class="form-check-label" for="flexRadioDefault1"> 온라인 시험 </label>
		</div>
		<div class="form-group">
			<label>강의실</label>
<!-- 			<input type="text" name="croomNm"	placeholder="강의실" class="form-control"> -->
			<select name="croomNm" class="form-select">
				<option value="CR001">A관101호</option>
				<option value="CR002">B관202호</option>
				<option value="CR003">C관303호</option>
			</select>
			
			<!-- 강의실을 selectBox로 부여하기 고려 -->
		
		
		</div>
<!-- 				<select name="testSe" class="form-select"> -->
<!-- 					<option value="PR">중간고사</option> -->
<!-- 					<option value="FT">기말고사</option> -->
<!-- 				</select>			 -->
		<div class="form-group">
			<label>종류</label> 
			<c:if test="${testSe =='middle' }">
				<input type="hidden" id="testSe" name="testSe" value="PR">
				<input type="text" class="form-control" value="중간고사" readonly>			
			</c:if>
			<c:if test="${testSe =='final' }">
				<input type="hidden" id="testSe" name="testSe" value="FT">
				<input type="text" class="form-control" value="기말고사" readonly>			
			</c:if>
		</div>
		
	</div>

	<div class="date-time-section child" id="dateDiv">
  <div>
    날짜<br>
    <input type="date" class="form-control" style="width: 200px;" id="dateInput">
  </div>
  <div style="display: flex; align-items: center; margin-top: 10px;">
    <div style="margin-right: 20px;">
      <label for="testDt" style="display: block;">시작시간</label>
      <input id="testDt" type="time" style="width: 200px;" class="form-control">
    </div>
    <div>
      <label for="testEt" style="display: block;">종료시간</label>
      <input id="testEt" type="time" style="width: 200px;" class="form-control">
    </div>
  </div>
    <button type="button" onclick="insertTest()"><i class="bi bi-pencil-square"></i></button>
</div>
	<div class="question-section">
    <div id="queBox"></div>
</div>

<div style="text-align: right;">
    <button type="button" onclick="queBtn('num')">객관식</button>
    <button type="button" onclick="queBtn('str')">주관식</button>
    <button type="button" onclick="queBtn('lStr')">서술형</button>
    <button type="button" onclick="insertForm()">시험전송</button>
</div>
</div>

<br>
<br>
<br>
<br>

<script>

</script>

<script src="${pageContext.request.contextPath }/resources/js/test/testFormScript.js"></script>














