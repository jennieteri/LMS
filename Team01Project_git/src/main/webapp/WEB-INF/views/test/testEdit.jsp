<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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


<c:if test="${not empty detailTest}">
<c:if test="${detailTest.testCd eq 'PEND'}">
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath }">
<input type="hidden" id="lectNo" name="lectNo" value="${detailTest.lectNo }">
<input type="hidden" id="testNo" name="testNo" value="${detailTest.testNo }">
	<div class="container">
		<div class="test-info child" id="asd">
		<c:if test="${detailTest.testOnlineYn eq 'N' }">
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn"
						id="flexRadioDefault2" value="N" checked> <label
						class="form-check-label" for="flexRadioDefault2">대면 시험 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn"
						value="Y" id="flexRadioDefault1"> <label
						class="form-check-label" for="flexRadioDefault1"> 온라인 시험 </label>
				</div>
			</c:if>
			<c:if test="${detailTest.testOnlineYn eq 'Y' }">
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn"
						id="flexRadioDefault2" value="N"> <label
						class="form-check-label" for="flexRadioDefault2">대면 시험 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn"
						value="Y" id="flexRadioDefault1" checked> <label
						class="form-check-label" for="flexRadioDefault1"> 온라인 시험 </label>
				</div>
			</c:if>
			
			<div class="form-group">
				<label>강의실</label>
<%-- 				 <input type="text" name="croomNm"	placeholder="강의실" class="form-control"  value="${detailTest.croomCd }"> --%>
			<select name="croomNm" class="form-select">
				<option value="CR001">A관101호</option>
				<option value="CR002">B관202호</option>
				<option value="CR003">C관303호</option>
			</select>
				<!-- 강의실을 selectBox로 부여하기 고려 -->
			</div>
			<div class="form-group">
				<label>종류:</label> <select id="testSe"  name="testSe" class="form-select">
					<c:if test="${detailTest.testSe eq 'PR' }">
						<option value="PR" selected="selected">중간고사</option>
						<option value="FT">기말고사</option>
					</c:if>

					<c:if test="${detailTest.testSe eq 'FT' }">
						<option value="PR">중간고사</option> 
						<option value="FT" selected="selected">기말고사</option>
					</c:if>

				</select>
			</div>
			
		</div>

		<div class="date-time-section child" id="dateDiv">
			<div>
				날짜<br> 
				<input type="date" class="form-control" id="dateInput"  style="width: 200px;" value="${fn:substring(detailTest.testSchdl,0,4)}-${fn:substring(detailTest.testSchdl,4,6)}-${fn:substring(detailTest.testSchdl,6,8)}">
			</div>
			<div style="display: flex; align-items: center; margin-top: 10px;">
    <div style="margin-right: 20px;">
        <label for="testDt" style="display: block;">시작시간</label>
        <input id="testDt" type="time" class="form-control" style="width: 200px;" 
            value="${fn:substring(detailTest.testDt,0,2)}:${fn:substring(detailTest.testDt,2,4)}">
    </div>
    <div>
        <label for="testEt" style="display: block;">종료시간</label>
        <input id="testEt" type="time" class="form-control" style="width: 200px;"
            value="${fn:substring(detailTest.testEt,0,2)}:${fn:substring(detailTest.testEt,2,4)}">
    </div>
</div>

		</div>

		<div class="question-section">
			<div id="queBox">
				<c:forEach items="${detailTest.questionVO }" var="que"	varStatus="qno">
					<c:if test="${que.queType eq '객관식'}">
						<%-- 				${que } --%>
						<div draggable="true" class="draggable-question">
							<div class="options">
								<div class="question-header">
									<div class="answer-div">
										<h4>${qno.count}.</h4>
										<input type="text" class="form-control"
											value="${que.queDescr }" id="queDescr">
										
									</div>
								</div>
									<br>
								<c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
									<textarea class="form-control col-auto" cols="20" rows="1"
										placeholder="${ansNo.count }번 문항">${ans.anchDescr }</textarea>
									<br>
								</c:forEach>
							</div>
							
						     <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
						        <p style="margin: 0;">배점</p>
						        <input type="number" id="scorebox" class="form-control" value="${que.queScore }" placeholder="배점">
						        <p style="margin: 0;">정답</p>
						        <input type="text" class="form-control" placeholder="정답" value="${que.queAnswer }" style="width:100px;">
						        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)"><i class="bi bi-x-lg"></i></button>
						        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${qno.count}')"><i class="bi bi-pencil-square"></i></button>
						    </div>
						</div>
					</c:if>
					<c:if test="${que.queType eq '주관식'}">
						<%-- 				${que } --%>
						<div draggable="true" class="draggable-question">

							<div class="options">
								<div class="question-header">
									<div class="answer-div">
										<h4>${qno.count}.</h4>
										<input type="text" class="form-control"
											value="${que.queDescr }" id="queDescr">
										
									</div>
									<br>
									<c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
										<textarea class="form-control col-auto" cols="20" rows="1"
											placeholder="${ansNo.count }번 문항">${ans.anchDescr }</textarea>
										<br>
									</c:forEach>
								</div>
							</div>
							 <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
        <p style="margin: 0;">배점</p>
        <input type="number" id="scorebox" class="form-control" value="${que.queScore }" placeholder="배점">
        <p style="margin: 0;">정답</p>
        <input type="text" class="form-control" placeholder="정답" value="${que.queAnswer }" style="width:100px;">
        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)"><i class="bi bi-x-lg"></i></button>
        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${qno.count}')"><i class="bi bi-pencil-square"></i></button>
    </div>
						</div>
					</c:if>
					<c:if test="${que.queType eq '서술형'}">
						<div draggable="true" class="draggable-question">

							<div class="options">
								<div class="question-header">
									<div class="answer-div">
										<h4>${qno.count}.</h4>
										<input type="text" class="form-control" id="queDescr" value="${que.queDescr }">
									</div>
									<br>
									<c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
										<textarea class="form-control col-auto" cols="20" rows="1"	placeholder="${ansNo.count }번 문항">${ans.anchDescr }</textarea>
										<br>
									</c:forEach>
								</div>
							</div>
							 <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
        <p style="margin: 0;">배점</p>
        <input type="number" id="scorebox" class="form-control" value="${que.queScore }" placeholder="배점">
        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)"><i class="bi bi-x-lg"></i></button>
        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${qno.count}')"><i class="bi bi-pencil-square"></i></button>
    </div>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div style="text-align: right;">
<button type="button" onclick="queBtn('num')">객관식</button>
<button type="button" onclick="queBtn('str')">주관식</button>
<button type="button" onclick="queBtn('lStr')">서술형</button>
<button type="button" onclick="insertForm()">등록</button>
<button type="button" style="width:69.45px; height:44px;"  class="btn btn-danger" onclick="deleteTestBtn()">삭제</button>
		</div>
	</div>
	</c:if>
<%-- 등록상태, 대기상태의 경우에는 수정하지 못하도록 --%>
<c:if test="${detailTest.testCd eq 'COMP' or detailTest.testCd eq 'OPEN' }">
<input type="hidden" id="contextPath" name="contextPath" value="${pageContext.request.contextPath }">
<input type="hidden" id="lectNo" name="lectNo" value="${detailTest.lectNo }">
<input type="hidden" id="testNo" name="testNo" value="${detailTest.testNo }">
	<div class="container">
		<div class="test-info child" id="asd">
		<c:if test="${detailTest.testOnlineYn eq 'N' }">
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn" id="flexRadioDefault2" value="N" checked disabled>
					<label class="form-check-label" for="flexRadioDefault2">대면 시험 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn" value="Y" id="flexRadioDefault1" disabled> 
					<label class="form-check-label" for="flexRadioDefault1"> 온라인 시험 </label>
				</div>
			</c:if>
			<c:if test="${detailTest.testOnlineYn eq 'Y' }">
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn" id="flexRadioDefault2" value="N" disabled>
						 <label class="form-check-label" for="flexRadioDefault2">대면 시험 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="radio" name="testOnlineYn" value="Y" id="flexRadioDefault1" checked disabled>
					<label class="form-check-label" for="flexRadioDefault1"> 온라인 시험 </label>
				</div>
			</c:if>
			<div class="form-group">
				<label>강의실</label>
				<div class="form-control">
					<c:if test="${detailTest.testOnlineYn eq 'Y' }">
						온라인 시험
					</c:if>
					<c:if test="${detailTest.testOnlineYn eq 'N' }">
						${detailTest.croomCdNm }
					</c:if>
				</div>

				<!-- 강의실을 selectBox로 부여하기 고려 -->
			</div>
			<div class="form-group">
				<label>종류</label> <select name="testSe" class="form-select">
					<c:if test="${detailTest.testSe eq 'PR' }">
						<option value="PR" selected="selected">중간고사</option>
	
					</c:if>

					<c:if test="${detailTest.testSe eq 'FT' }">

						<option value="FT" selected="selected">기말고사</option>
					</c:if>

				</select>
			</div>
			
			
		</div>

		<div class="date-time-section child" id="dateDiv">
			<div>
				날짜<br> <input type="date" class="form-control" id="dateInput" readonly="readonly"  style="width: 200px;"
					value="${fn:substring(detailTest.testSchdl,0,4)}-${fn:substring(detailTest.testSchdl,4,6)}-${fn:substring(detailTest.testSchdl,6,8)}">
			</div>
			<div style="display: flex; align-items: center; margin-top: 10px;">
    <div style="margin-right: 20px;">
        <label for="testDt" style="display: block;">시작시간</label>
        <input id="testDt" type="time" class="form-control" style="width: 200px;" readonly  
            value="${fn:substring(detailTest.testDt,0,2)}:${fn:substring(detailTest.testDt,2,4)}">
    </div>
    <div>
        <label for="testEt" style="display: block;">종료시간</label>
        <input id="testEt" type="time" class="form-control" style="width: 200px;" readonly 
            value="${fn:substring(detailTest.testEt,0,2)}:${fn:substring(detailTest.testEt,2,4)}">
    </div>
</div>

		</div>

		<div class="question-section">
			<div id="queBox">
				<c:forEach items="${detailTest.questionVO }" var="que"
					varStatus="qno">
					<c:if test="${que.queType eq '객관식'}">
						<%-- 				${que } --%>
						<div draggable="true" class="draggable-question">
							<div class="options">
							    <div class="question-header">
    <div class="answer-div" style="display: flex; justify-content: space-between; align-items: flex-start; width: 100%; margin-bottom: 10px;">
        <div style="display: flex; gap: 10px; flex: 1;">
            <h4 style="margin: 0;">${qno.count}.</h4>
            <input type="text" class="form-control" value="${que.queDescr}" id="queDescr" readonly="readonly" style="flex: 1; width: 100%;">
        </div>
    </div>
</div>

							    <c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
							        <textarea class="form-control col-auto" cols="20" rows="1" placeholder="${ansNo.count }번 문항" readonly="readonly">${ans.anchDescr }</textarea>
							        <br>
							    </c:forEach>
							</div>

 <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
    <span style="display: flex; align-items: center;">배점</span>
        <input type="number" id="scorebox" class="form-control" placeholder="배점"  value="${que.queScore}"readonly  >
    <span style="display: flex; align-items: center;">정답</span>
        <input type="text" class="form-control" placeholder="정답" style="width:100px;"  value="${que.queAnswer}" readonly >
    </div>	
						</div>
					</c:if>

					<c:if test="${que.queType eq '주관식'}">
						<%-- 				${que } --%>
						<div draggable="true" class="draggable-question">

							<div class="options">
								<div class="question-header">
    <div class="answer-div" style="display: flex; align-items: center; width: 100%; margin-bottom: 10px;">
        <h4 style="margin: 0; min-width: 30px;">${qno.count}.</h4>
        <input type="text" class="form-control" value="${que.queDescr}" id="queDescr" readonly="readonly" style="flex: 1;">
    </div>
</div>

									<c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
										<textarea class="form-control col-auto" cols="20" rows="1" placeholder="${ansNo.count }번 문항" readonly="readonly">${ans.anchDescr }</textarea>
										<br>
									</c:forEach>
							</div>
							           
 <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
    <span style="display: flex; align-items: center;">배점</span>
        <input type="number" id="scorebox" class="form-control" placeholder="배점"  value="${que.queScore}" readonly >
    <span style="display: flex; align-items: center;">정답</span>
        <input type="text" class="form-control" placeholder="정답" style="width:100px;"  value="${que.queAnswer}" readonly >
    </div>	
						</div>
					</c:if>
					<c:if test="${que.queType eq '서술형'}">
						<div draggable="true" class="draggable-question">

							<div class="options">
								<div class="question-header">
    <div class="answer-div" style="display: flex; align-items: center; width: 100%; margin-bottom: 10px;">
        <h4 style="margin: 0; min-width: 30px;">${qno.count}.</h4>
        <input type="text" class="form-control" value="${que.queDescr}" id="queDescr" readonly="readonly" style="flex: 1;">
    </div>
</div>

									<c:forEach items="${que.answerVO }" var="ans" varStatus="ansNo">
										<textarea class="form-control col-auto" cols="20" rows="1"	placeholder="${ansNo.count }번 문항" readonly="readonly">${ans.anchDescr }</textarea>
										<br>
									</c:forEach>
							</div>
							           
 <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
    <span style="display: flex; align-items: center;">배점</span>
        <input type="number" id="scorebox" class="form-control" placeholder="배점"  value="${que.queScore}" readonly >
    </div>	
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>

	</c:if>
</c:if>


<script
	src="${pageContext.request.contextPath }/resources/js/test/testEditScript.js"></script>


































