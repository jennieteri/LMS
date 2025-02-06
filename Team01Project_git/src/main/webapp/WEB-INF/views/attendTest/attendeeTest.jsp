<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<input type="hidden" id="contextPath" name="contextPath"
	value="${pageContext.request.contextPath}">
<!-- 시험 정보가 없을 때도 표시되는 기본 섹션 -->
<c:if test="${empty attendeeTestList}">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-6">
            <!-- 중간고사 섹션 -->
            <div class="card mb-4 border">
                <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">중간고사</h5>
						</div>
                <div class="card-body text-center" style="margin-top: 15px;">
                    <p>등록된 시험 정보가 없습니다.</p>
                    <security:authorize access="hasRole('PROFESSOR')">
                    
                        <button class="btn btn-secondary" data-lect-no="${lectNo}" data-test-se="middle"
                            onclick="getProfessorNewTestPage(this)">시험 등록</button>
                    </security:authorize>
                </div>
            </div>

            <!-- 기말고사 섹션 -->
            <div class="card mb-4 border">
                <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">기말고사</h5>
						</div>
                <div class="card-body text-center" style="margin-top: 15px;">
                    <p>등록된 시험 정보가 없습니다.</p>
                    <security:authorize access="hasRole('PROFESSOR')">
                        <button class="btn btn-secondary" data-lect-no="${lectNo}" data-test-se="final"
                            onclick="getProfessorNewTestPage(this)">시험 등록</button>
                    </security:authorize>
                </div>
            </div>
        </div>
    </div>
</div>
</c:if>

<c:if test="${not empty attendeeTestList}">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<!-- 중간고사/기말고사 체크 변수 설정 -->
				<c:set var="hasMidterm" value="false" />
				<c:set var="hasFinal" value="false" />

				<c:forEach items="${attendeeTestList}" var="attendeeTest">
					<c:if test="${attendeeTest.testSe == 'PR'}">
						<c:set var="hasMidterm" value="true" />
					</c:if>
					<c:if test="${attendeeTest.testSe == 'FT'}">
						<c:set var="hasFinal" value="true" />
					</c:if>
				</c:forEach>

<!-- 중간고사 섹션 -->
<c:if test="${hasMidterm}">
    <c:set var="hasOpenMidterm" value="false" />
    <c:forEach items="${attendeeTestList}" var="attendeeTest">
        <c:if test="${attendeeTest.testSe == 'PR'}">
            <security:authorize access="hasRole('PROFESSOR')">
                <div class="card mb-4 border">
                    <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">중간고사</h5>
						</div>
                    <div class="card-body">
                        <div class="border rounded p-3 mb-3" style="margin-top: 15px;">
                            <div class="row mb-2">
                                <div class="col-4 fw-bold">상태</div>
                                <div class="col-8">${attendeeTest.testCdNm}</div>
                                <div class="col-4 fw-bold">시험 일자</div>
                                <div class="col-8">${fn:substring(attendeeTest.testSchdl,0,4)}-${fn:substring(attendeeTest.testSchdl,4,6)}-${fn:substring(attendeeTest.testSchdl,6,8)}</div>
                                
                            </div>
                            <div class="row mb-2">
                                <div class="col-4 fw-bold">시험 시간</div>
                                <div class="col-8">${fn:substring(attendeeTest.testDt,0,2)}:${fn:substring(attendeeTest.testDt,2,4)}~${fn:substring(attendeeTest.testEt,0,2)}:${fn:substring(attendeeTest.testEt,2,4)}</div>
                            </div>
                            <div class="row">
                                <div class="col-4 fw-bold">시험 종류</div>
                                <div class="col-8">중간고사</div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end mt-3">
                        <c:if test="${not empty longQuestion}">
                            <button class="btn btn-secondary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}"
                                onclick="getProfessorAttendeeTestListPage(this)">서술형 채점</button>
                        </c:if>
                            <button class="btn btn-secondary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}" style="margin-left: 10px;"
                                onclick="getProfessorDetailTestPage(this)">시험 상세 조회</button>
                        </div>
                    </div>
                </div>
            </security:authorize>

            <c:if test="${attendeeTest.testCd eq 'OPEN'}">
                <c:set var="hasOpenMidterm" value="true" />
                <security:authorize access="hasRole('STUDENT')">
                    <div class="card mb-4 border">
                        <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">중간고사</h5>
						</div>
                        <div class="card-body">
                            <div class="border rounded p-3 mb-3" style="margin-top: 15px;">
                                <div class="row mb-2">
                                    <div class="col-4 fw-bold">시험 일자</div>
                                    <div class="col-8">${fn:substring(attendeeTest.testSchdl,0,4)}-${fn:substring(attendeeTest.testSchdl,4,6)}-${fn:substring(attendeeTest.testSchdl,6,8)}</div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-4 fw-bold">시험 시간</div>
                                    <div class="col-8">${fn:substring(attendeeTest.testDt,0,2)}:${fn:substring(attendeeTest.testDt,2,4)}~${fn:substring(attendeeTest.testEt,0,2)}:${fn:substring(attendeeTest.testEt,2,4)}</div>
                                </div>
                                <div class="row">
                                    <div class="col-4 fw-bold">시험 종류</div>
                                    <div class="col-8">중간고사</div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end mt-3">
                                <button class="btn btn-primary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}"
                                    onclick="showConfirm(this)">응시</button>
                            </div>
                        </div>
                    </div>
                </security:authorize>
            </c:if>
        </c:if>
    </c:forEach>

    <security:authorize access="hasRole('STUDENT')">
        <c:if test="${!hasOpenMidterm}">
            <div class="card mb-4 border">
                <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">중간고사</h5>
						</div>
                <div class="card-body text-center" style="margin-top: 15px;">
                    <p>등록된 시험 정보가 없습니다.</p>
                </div>
            </div>
        </c:if>
    </security:authorize>
</c:if>

<!-- 기말고사 섹션 -->
<c:if test="${hasFinal}">
    <c:set var="hasOpenFinal" value="false" />
    <c:forEach items="${attendeeTestList}" var="attendeeTest">
        <c:if test="${attendeeTest.testSe == 'FT'}">
            <security:authorize access="hasRole('PROFESSOR')">
                <div class="card mb-4 border">
                    <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">기말고사</h5>
						</div>
                    <div class="card-body">
                        <div class="border rounded p-3 mb-3" style="margin-top: 15px;">
                            <div class="row mb-2">
                                <div class="col-4 fw-bold">상태</div>
                                <div class="col-8">${attendeeTest.testCdNm}</div>
                                <div class="col-4 fw-bold">시험 일자</div>
                                <div class="col-8">${fn:substring(attendeeTest.testSchdl,0,4)}-${fn:substring(attendeeTest.testSchdl,4,6)}-${fn:substring(attendeeTest.testSchdl,6,8)}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-4 fw-bold">시험 시간</div>
                                <div class="col-8">${fn:substring(attendeeTest.testDt,0,2)}:${fn:substring(attendeeTest.testDt,2,4)}~${fn:substring(attendeeTest.testEt,0,2)}:${fn:substring(attendeeTest.testEt,2,4)}</div>
                            </div>
                            <div class="row">
                                <div class="col-4 fw-bold">시험 종류</div>
                                <div class="col-8">기말고사</div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end mt-3">
                                                <c:if test="${not empty longQuestion}">
                        
                            <button class="btn btn-secondary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}"
                                onclick="getProfessorAttendeeTestListPage(this)">서술형 채점</button>
                                                </c:if>
                            <button class="btn btn-secondary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}" style="margin-left: 10px;"
                                onclick="getProfessorDetailTestPage(this)">시험 상세 조회</button>
                        </div>
                    </div>
                </div>
            </security:authorize>

            <c:if test="${attendeeTest.testCd eq 'OPEN'}">
                <c:set var="hasOpenFinal" value="true" />
                <security:authorize access="hasRole('STUDENT')">
                    <div class="card mb-4 border">
                        <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">기말고사</h5>
						</div>
                        <div class="card-body">
                            <div class="border rounded p-3 mb-3" style="margin-top: 15px;">
                                <div class="row mb-2">
                                    <div class="col-4 fw-bold">시험 일자</div>
                                    <div class="col-8">${fn:substring(attendeeTest.testSchdl,0,4)}-${fn:substring(attendeeTest.testSchdl,4,6)}-${fn:substring(attendeeTest.testSchdl,6,8)}</div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-4 fw-bold">시험 시간</div>
                                    <div class="col-8">${fn:substring(attendeeTest.testDt,0,2)}:${fn:substring(attendeeTest.testDt,2,4)}~${fn:substring(attendeeTest.testEt,0,2)}:${fn:substring(attendeeTest.testEt,2,4)}</div>
                                </div>
                                <div class="row">
                                    <div class="col-4 fw-bold">시험 종류</div>
                                    <div class="col-8">기말고사</div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end mt-3">
                                <button class="btn btn-primary" data-test-no="${attendeeTest.testNo}" data-lect-no="${attendeeTest.lectNo}"
                                    onclick="showConfirm(this)">응시</button>
                            </div>
                        </div>
                    </div>
                </security:authorize>
            </c:if>
        </c:if>
    </c:forEach>

    <security:authorize access="hasRole('STUDENT')">
        <c:if test="${!hasOpenFinal}">
            <div class="card mb-4 border">
                <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">기말고사</h5>
						</div>
                <div class="card-body text-center" style="margin-top: 15px;">
                    <p>등록된 시험 정보가 없습니다.</p>
                </div>
            </div>
        </c:if>
    </security:authorize>
</c:if>

<c:if test="${!hasFinal}">
    <div class="card mb-4 border">
        <div class="card-header bg-primary text-white text-center py-3">
						    <h5 class="mb-0" style="font-size: 1.5rem; font-weight: 600;">기말고사</h5>
						</div>
        <div class="card-body text-center" style="margin-top: 15px;">
            <p>등록된 시험 정보가 없습니다.</p>
            <security:authorize access="hasRole('PROFESSOR')">
               <button class="btn btn-secondary" data-lect-no="${lectNo}" data-test-se="final"
                            onclick="getProfessorNewTestPage(this)">시험 등록</button>
            </security:authorize>
        </div>
    </div>
</c:if>


				<%--                 <security:authorize access="hasRole('PROFESSOR')"> --%>
				<!--                     <div class="container mt-5"> -->
				<!--                         <div class="row justify-content-center"> -->
				<!--                             <div class="col-12 col-md-8 col-lg-6"> -->
				<%--                                 <button class="btn btn-secondary" data-lect-no="${lectNo}" --%>
				<!--                                     onclick="getProfessorNewTestPage(this)">시험 등록</button> -->
				<!--                             </div> -->
				<!--                         </div> -->
				<!--                     </div> -->
				<%--                 </security:authorize> --%>
			</div>
		</div>
	</div>
</c:if>

<script
	src="${pageContext.request.contextPath}/resources/js/test/attendeeTestScript.js"></script>
