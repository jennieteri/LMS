<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<h1>사용자 : 교수 - 직접 등록한 시험 조회</h1>

<c:if test="${not empty detailTest}">

	<table class="table table-bordered " style="width: 50%;">
		<thead>
			<tr>
				<th>강의명</th>
				<th>구분</th>
				<th>장소 및 일시</th>
				<th>교수명</th>
				<th>상태</th>
			</tr>
			<c:if test="${not empty detailTestList}">
				<c:forEach items="${detailTestList}" var="test">
					<c:if test="${test.testNo eq detailTest.testNo}">
						<c:if test="${test.testCd eq 'COMP' or test.testCd eq 'OPEN' }">
							<input type="hidden" id="testNo" name="testNo"
								value="${test.testNo }">
							<tr>
								<td>${test.lectVO.lectNm }</td>

								<c:if test="${test.testSe eq 'PR'}">
									<td>중간고사</td>
								</c:if>
								<c:if test="${test.testSe eq 'FT'}">
									<td>기말고사</td>
								</c:if>
								<td>
									<p>${test.classroomVO.croomPstn}</p>
									<p>${fn:substring(test.testSchdl,0,4)}/${fn:substring(test.testSchdl,4,6)}/${fn:substring(test.testSchdl,6,8)}</p>
									<p>${fn:substring(test.testDt,0,2)}:${fn:substring(test.testDt,2,4)}~${fn:substring(test.testEt,0,2)}:${fn:substring(test.testEt,2,4)}</p>
								</td>
								<td>${test.lectVO.professorVO.nm }</td>
								<td><div id="test-status">
										<c:if test="${test.testCd eq 'COMP'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">대기</button>
										</c:if>
										<c:if test="${test.testCd eq 'PEND'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">반려</button>
										</c:if>
										<c:if test="${test.testCd eq 'OPEN'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">등록</button>
										</c:if>
									</div></td>
							</tr>
						</c:if>
						<c:if test="${test.testCd eq 'PEND' }">
							<%-- 시험이 반려상태면 input 태그가 보여야함 --%>
							<input type="hidden" id="testNo" name="testNo"
								value="${test.testNo }">
							<tr>
								<td><input type="text" id="lectNm" name="lectNm"
									value="${test.lectVO.lectNm }"></td>

								<c:if test="${test.testSe eq 'PR'}">
									<td>중간고사</td>
									<td>
										<select>
											<option>s</option>
											<option>a</option>
										</select>
									</td>
							
								</c:if>
								<c:if test="${test.testSe eq 'FT'}">
									<td>기말고사</td>
									
								</c:if>

								<td>
									<input type="text" class="form-control" value="${test.classroomVO.croomPstn}">
<%-- 									${fn:substring(test.testSchdl,0,4)}/${fn:substring(test.testSchdl,4,6)}/${fn:substring(test.testSchdl,6,8)} --%>
									<input type="date" class="form-control" id="dateInput" value="${fn:substring(test.testSchdl,0,4)}-${fn:substring(test.testSchdl,4,6)}-${fn:substring(test.testSchdl,6,8)}">
									<input id="testDt" type="time" class="form-control"
									value="${fn:substring(test.testDt,0,2)}:${fn:substring(test.testDt,2,4)}">
									<input id="testEt" type="time" class="form-control"
									value="${fn:substring(test.testEt,0,2)}:${fn:substring(test.testEt,2,4)}">
								</td>
								<td>${test.lectVO.professorVO.nm }</td>
								<td><div id="test-status">
										<c:if test="${test.testCd eq 'COMP'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">대기</button>
										</c:if>
										<c:if test="${test.testCd eq 'PEND'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">반려</button>
										</c:if>
										<c:if test="${test.testCd eq 'OPEN'}">
											<button class="btn btn-outline-primary" id="waiting-button"
												onclick="javascipt:void(0);">등록</button>
										</c:if>
									</div></td>
							</tr>
						</c:if>
					</c:if>
				</c:forEach>
			</c:if>

		</thead>

		<tbody>
			<c:if test="${not empty detailTest.questionVO}">
				<tr>
					<th>문제번호</th>
					<th>문제설명</th>
					<th>문제유형</th>
					<th>배점</th>
					<th>정답</th>
				</tr>
				<c:forEach items="${detailTest.questionVO }" var="que"
					varStatus="qn">
					<tr>
						<td><input type="hidden" name="queNum" value="${que.queNo }">${qn.count }
							번</td>
						<td>${que.queDescr }</td>
						<td>${que.queType }</td>
						<td>${que.queScore }점</td>
						<td><c:if test="${not empty que.queAnswer }">
								<c:if test="${que.queType ne '서술형'}">
									등록
								</c:if>
								<c:if test="${que.queType eq '서술형'}">
									미등록
								</c:if>
							</c:if> <c:if test="${empty que.queAnswer }">
									서술형						
							</c:if></td>
					</tr>
					<tr>
						<th>지문</th>
						<td colspan="4"><c:forEach items="${detailTest.questionVO}"
								var="question">
								<c:forEach items="${question.answerVO}" var="answer"
									varStatus="cn">
									<c:if test="${answer.queNo eq que.queNo }">
										<c:if test="${que.queType eq '주관식' }">
											<p>${answer.anchDescr}</p>
										</c:if>
										<c:if test="${que.queType ne '주관식' }">
										</c:if>
										<c:if test="${que.queType eq '객관식' }">
											<p>${cn.count}번:${answer.anchDescr}</p>
										</c:if>
										<c:if test="${que.queType ne '객관식' }"></c:if>
										<c:if test="${que.queType eq '서술형' }">
											<p>서술형 답안 입니다.</p>
										</c:if>
										<c:if test="${que.queType ne '서술형' }"></c:if>
									</c:if>
								</c:forEach>
							</c:forEach></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</c:if>


<%-- <script	src="${pageContext.request.contextPath }/resources/js/test/testScript.js"></script> --%>




































