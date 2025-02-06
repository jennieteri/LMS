<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(new java.util.Date());
    request.setAttribute("today", today);
%>

<style>

.card {
    border: 1px solid #ddd;
    border-radius: 8px;
    margin-bottom: 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.card-header {
    background-color: #f8f9fa;
    font-weight: bold;
    padding: 10px 15px;
    border-bottom: 1px solid #ddd;
}

.card-footer {
    background-color: #f8f9fa;
    padding: 10px 15px;
    border-top: 1px solid #ddd;
    text-align: right;
}

.table-bordered {
    border: 1px solid #ddd;
}

.text-success {
    color: #28a745 !important;
    font-weight: bold;
}

.text-danger {
    color: #dc3545 !important;
    font-weight: bold;
}

.btn-primary:hover {
    background-color: #0056b3;
    border-color: #004085;
}
</style>
<input id="attendeeCount" type="hidden" value="${attendeeCount }">
<div id="mainDiv" class="container mt-4" data-path="${pageContext.request.contextPath }" data-lect-no="${lectNo }" data-auth="${pageContext.request.userPrincipal.name }">
    
    <!-- 차트 -->
    <h3>전체 과제별 제출률 및 채점률</h3>
    <div style="height: 300px; width: 100%;">
	    <canvas id="submissionRateChart" style="width: 100%;"></canvas>
	</div>
    
    <div class="accordion" id="assignmentAccordion">
        <c:forEach var="assignment" items="${assignmentList}" varStatus="status">
            <div class="accordion-item">
                <!-- 과제 제목 -->
                <h2 class="accordion-header" id="heading-${assignment.assigNo}">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" 
                            data-bs-target="#collapse-${assignment.assigNo}" 
                            aria-expanded="false" aria-controls="collapse-${assignment.assigNo}">
                        과제명: ${assignment.assigNm}
                    </button>
                </h2>

                <!-- 제출 과제 리스트 -->
                <div id="collapse-${assignment.assigNo}" class="accordion-collapse collapse" 
                     aria-labelledby="heading-${assignment.assigNo}" data-bs-parent="#assignmentAccordion">
                    <div class="accordion-body">
                        <h2>제출된 과제</h2>
                        <c:if test="${empty assignment.assignmentsubmissionList}">
                            <p class="text-center text-muted">제출된 과제가 없습니다.</p>
                        </c:if>

                        <c:forEach var="submission" items="${assignment.assignmentsubmissionList}">
                        <c:if test="${submission.assubYn eq 'Y'}">
                            <div class="card mb-3">
                                <div class="card-body pt-3">
                                    <div class="row mb-2">
                                        <div class="col-md-3 text-end fw-bold">학번</div>
                                        <div class="col-md-9">${submission.stuId}</div>
                                    </div>
                                    <div class="row mb-2">
                                        <div class="col-md-3 text-end fw-bold">제출일</div>
                                        <div class="col-md-9">${fn:substring(submission.assubDate, 0, 4)}-${fn:substring(submission.assubDate, 4, 6)}-${fn:substring(submission.assubDate, 6, 8)}</div>
                                    </div>
                                    <div class="row mb-2">
                                        <div class="col-md-3 text-end fw-bold">제출마감일</div>
                                        <div class="col-md-9">
                                            ${fn:substring(assignment.assigEd, 0, 4)}-${fn:substring(assignment.assigEd, 4, 6)}-${fn:substring(assignment.assigEd, 6, 8)}
                                            <c:choose>
                                                <c:when test="${assignment.assigEd ge today}">
                                                    <span class="text-success ms-2"><i class="bi bi-check-circle"></i> 제출 가능</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger ms-2"><i class="bi bi-x-circle"></i> 마감</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="row mb-2">
                                        <div class="col-md-3 text-end fw-bold">과제점수</div>
                                        <div class="col-md-9">
                                            <input id="inputGrade" type="number" max="${assignment.assigScore}" min="0" value="${submission.assubScore}" class="form-control form-control-sm" style="width: 100px; display: inline-block;">
                                            / ${assignment.assigScore}
                                        </div>
                                    </div>
                                    <div class="row mb-2">
                                        <div class="col-md-3 text-end fw-bold">첨부파일</div>
                                        <div class="col-md-9">
                                            <c:forEach items="${submission.atchFile.fileDetails}" var="sufd" varStatus="suvs">
                                                <c:url value='/atch/${sufd.atchFileId}/${sufd.fileSn}' var="downUrl" />
                                                <a href="${downUrl}" class="text-primary">${sufd.orignlFileNm}(${sufd.fileFancysize})</a>
                                                ${not suvs.last ? '|' : ''}
                                            </c:forEach>
                                            <c:if test="${empty submission.atchFile.fileDetails}">
                                                <span class="text-muted">첨부파일 없음</span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3 text-end fw-bold">내용</div>
                                        <div class="col-md-9">${submission.assubNotes}</div>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <button id="gradeBtn" class="btn btn-primary btn-sm"
                                            data-limit="${assignment.assigScore}"
                                            data-stuid="${submission.stuId}"
                                            data-assigno="${submission.assigNo}"
                                            ${not empty submission.assubScore ? 'disabled="disabled"' : ''}>
                                        ${not empty submission.assubScore  ? '채점완료' : '채점'}
                                    </button>
                                </div>
                            </div>
                        </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="${pageContext.request.contextPath }/resources/js/assignmentSubmission/assignmentSubmission.js"></script>