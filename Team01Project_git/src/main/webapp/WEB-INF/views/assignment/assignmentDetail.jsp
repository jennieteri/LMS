<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(new java.util.Date());
    request.setAttribute("today", today);
%>


<style>
.table-bordered {
    background-color: white;
    border: 1px solid #ddd;
}

.table th {
    background-color: #f8f9fa;
    font-weight: bold;
    text-align: center;
    vertical-align: middle;
}

.table td {
    vertical-align: middle;
    padding: 10px;
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
}

.card-footer {
    background-color: #f8f9fa;
    padding: 10px 15px;
    text-align: right;
}

.modal-header, .modal-footer {
    background-color: #f8f9fa;
    border-bottom: 1px solid #ddd;
    border-top: 1px solid #ddd;
}

</style>

<div class="container">

<input id="lectNo" type="hidden" value="${lectNo }">

<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" data-auth="${pageContext.request.userPrincipal.name }">
	<tr>
		<th>과제명</th>
		<td>${assignment.assigNm }</td>
	</tr>
	<tr>
		<th>배점</th>
		<td>${assignment.assigScore }점</td>
	</tr>
	<tr>
		<th>제출마감일</th>
		<td>
		${fn:substring(assignment.assigEd, 0, 4)}-${fn:substring(assignment.assigEd, 4, 6)}-${fn:substring(assignment.assigEd, 6, 8)}
		<c:choose>
			<c:when test="${assignment.assigEd ge today }">
			<!-- 제출 가능 -->
				<span class="text-success">
                    <i class="bi bi-file-earmark-check"></i> 제출 가능
                </span>
			</c:when>
			<c:otherwise>
			<!-- 제출 불가 -->
				<span class="text-danger">
                    <i class="bi bi-file-earmark-excel"></i> 마감
                </span>
			</c:otherwise>
		</c:choose>
		
		</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
			<c:forEach items="${assignment.atchFile.fileDetails }" var="fd" varStatus="vs">
			<c:url value='/atch/${fd.atchFileId }/${fd.fileSn }' var="downUrl"/>
			<a href="${downUrl }">${fd.orignlFileNm }(${fd.fileFancysize })</a>
				${not vs.last ? '|' : ''}
			</c:forEach>
			<c:if test="${empty assignment.atchFile.fileDetails}">
			    첨부파일없음
			</c:if>

		</td>
	</tr>
	
	<tr>
		<th>피어리뷰여부</th>
		<td>${assignment.peerYn }</td>
	</tr>
	<tr>
		<td colspan="2">${assignment.assigNotes }</td>
	</tr>
</table>
<div class="text-end">
	<security:authorize access="hasRole('PROFESSOR')">
		<a href="<c:url value='/lecture/${lectNo}/assignment/${assignment.assigNo }/edit' />" class="btn btn-info">수정</a>
			<form id="delForm" action="<c:url value='/lecture/${lectNo}/assignment/delete' />" method="post" style="display:inline;">
				<input type="hidden" name="assigNo" value="${assignment.assigNo }">
				<!-- 교수인경우 해당 버튼 출력 -->
			    <button type="submit" class="btn btn-danger">삭제</button>
			</form>
	</security:authorize>
	<security:authorize access="hasRole('STUDENT')">
		<c:if test="${not submissionYn}">
			<input type="hidden" name="atchFileId" value="${assignment.atchFileId }">
			<button class="btn btn-primary" id="submitBtn" 
			data-ed="${fn:substring(assignment.assigEd, 0, 4)}-${fn:substring(assignment.assigEd, 4, 6)}-${fn:substring(assignment.assigEd, 6, 8)}"
			data-assigno="${assignment.assigNo }"
			>제출
			</button>
		</c:if>
	</security:authorize>
	<a href="<c:url value='/lecture/${lectNo}/assignment' />" class="btn btn-primary">목록</a>
</div>
	<!-- 과제영역 끝 -->
		
		<!-- 제출버튼 누르면 submitFormArea에 동적으로 제출폼 생성됨(스크립트)-->
		
	<!-- 해당과제의 제출내역출력 -->	
	<c:if test="${submissionYn}">
	<!-- 제출과제영역 -->
	<h2 class="text-left">제출과제</h2>
		
		<div id="resultArea" class="card">
			<div class="card-body pt-3">
	            <table class="table table-bordered">
	                <tr>
	                    <th class="text-center">학번</th>
	                    <td>${submission.stuId}</td>
	                </tr>
	                <tr>
	                    <th class="text-center">제출일시</th>
	                    <td> ${fn:substring(submission.assubDate, 0, 4)}-${fn:substring(submission.assubDate, 4, 6)}-${fn:substring(submission.assubDate, 6, 8)}</td>
	                </tr>
	                <tr>
	                	<th class="text-center">취득점수</th>
	                	<td>
	                	<c:if test="${not empty submission.assubScore }">
	                		${submission.assubScore }
	                	</c:if>
	                	<c:if test="${empty submission.assubScore }">
	                		채점 전입니다.
	                	</c:if>
	                	</td>
	                </tr>
	                <tr>
	                    <th class="text-center">첨부파일</th>
	                    <td>
	                    	<c:forEach items="${submission.atchFile.fileDetails }" var="sfd" varStatus="svs">
								<c:url value='/atch/${sfd.atchFileId }/${sfd.fileSn }' var="downUrl"/>
								<a href="${downUrl }">${sfd.orignlFileNm }(${sfd.fileFancysize })</a>
									${not svs.last ? '|' : ''}
							</c:forEach>
							<c:if test="${empty submission.atchFile.fileDetails}">
							    첨부파일없음
							</c:if>
	                    </td>
	                </tr>
	                <c:if test="${assignment.peerYn eq 'Y' }">
	                <tr>
	                	<th class="text-center">피어리뷰제출여부</th>
	                		<c:if test="${empty submission.peerYn }">
		                	<td>미제출</td>
		                	</c:if>
	                		<c:if test="${not empty submission.peerYn }">
		                	<td>${submission.peerYn }</td>
		                	</c:if>
	                </tr>
	                </c:if>
	                 <tr>
	                    <th class="text-center">제출 내용</th>
	                    <td>${submission.assubNotes}</td>
	                </tr>
	            </table>
	            <!-- 수정 버튼 -->
	            <div class="card-footer">
	                <button class="btn btn-info" id="editSubmissionBtn"
	                data-id="${submission.stuId }"
	                data-assigno="${submission.assigNo }"
	                data-lectno="${submission.lectNo }"
	                >
	                수정</button>
	                <button class="btn btn-danger" id="delBtn"
	                data-id="${submission.stuId }"
	                data-assigno="${submission.assigNo }"
	                data-lectno="${submission.lectNo }"
	                >삭제</button>
	                <c:if test="${assignment.peerYn eq 'Y' and submission.peerYn eq 'N' or empty submission.peerYn}">
	                <button class="btn btn-primary" id="peerBtn"
	                data-id="${submission.stuId }"
	                data-assigno="${submission.assigNo }"
	                data-lectno="${submission.lectNo }"
	                >피어리뷰작성</button>
	                </c:if>
	            </div>
			</div>
		</div>
	</c:if>
</div>			
			
<!-- 제출과제 조회 -->
<div id="submitFormArea"></div>

<!-- Bootstrap 모달(피어리뷰) -->
<div class="modal fade" id="peerReviewModal" tabindex="-1" aria-labelledby="peerReviewModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="peerReviewModalLabel">피어리뷰 작성</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <!-- 모달 내부 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="savePeerReviewBtn"
        data-selectedscore=""
        data-assigno=""
        >저장</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>



<script src="${pageContext.request.contextPath }/resources/js/assignment/assignment.js"></script>