<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<security:authentication property="principal.username" var="id"/>
<security:authentication property="principal.realUser" var="ujUser"/>

<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<input type="hidden" id="sibNo" value="${board.sibNo }">
<input type="hidden" id="id" value="${id }">
<style>
.textarea textarea {
    width: 100%; /* 부모의 가로 크기 */
    height: 200px; /* 등록할 때와 동일한 높이 */
    resize: none; /* 사용자가 크기 조정 불가능하게 설정 (선택 사항) */
}

</style>

<table class="table table-bordered table-primary">
    <!-- 문의 게시글 상세 -->
    <tr>
        <td colspan="1" style="text-align: center; font-weight: bold;">상태</td>
        <td class="bg-light" colspan="3">${board.sibSttsYn}</td>
    </tr>
    <tr>
        <td style="text-align: center; font-weight: bold;">제목</td>
        <td class="bg-light" colspan="3">${board.sibTtl}</td>
    </tr>
    <tr>
        <td style="text-align: center; font-weight: bold;">작성일시</td>
        <td class="bg-light">${fn:split(board.sibDt, 'T')[0]} ${fn:split(board.sibDt, 'T')[1]}</td>
        <td style="text-align: center; font-weight: bold;">작성자</td>
        <td class="bg-light">${board.nm}</td>
    </tr>
    <tr>
        <td colspan="4" class="bg-light" style="height: 200px; text-align: left; vertical-align: top;">${board.sibCn}</td>
    </tr>
</table>
<!-- 게시글 수정/삭제 버튼 -->
<c:if test="${id eq board.userId and empty board.sibAns}"> 
	
    <div class="mb-3 text-end">
        <a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/answer">목록</a>
        <a class="btn btn-info ms-2" href="${pageContext.request.contextPath }/answer/${board.sibNo}/edit">수정</a>
        <form id="deleteForm" action="${pageContext.request.contextPath }/answer/delete/${board.sibNo}" method="post" style="display:inline;">
    		<button type="button" class="btn btn-danger deleteBtn">삭제</button>
		</form>

    </div>
</c:if>

<c:if test="${not empty board.sibAns }">
	<table class="table table-primary align-middle table-bordered mb-2">
		<tr>
			<th class="text-center" style="width: 100px;">답변자</th>
			<td class="table-light" style="width: 200px;">관리자</td>
			<th class="text-center" style="width: 100px;">답변일시</th>
			<td class="table-light" style="width: 200px;">${fn:split(board.sibTime, 'T')[0] } ${fn:split(board.sibTime, 'T')[1] }</td>
		</tr>
		
		<tr>
			<td class="table-light align-top dispAnswer" colspan="4" style="height: 200px;">${board.sibAns }</td>
			<td class="table-light textarea" colspan="4" style="display: none;">
				<textarea class="form-control" id="editor" placeholder="답변을 입력하세요"></textarea>
			</td>
		</tr>
	</table>
	
	<div id="delUpBtn">
		<c:forEach items="${ujUser.personType}" var="pType">
		     <c:if test='${pType eq "ADMIN"}'>
					<a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/answer">목록</a>
		       		<button class="btn btn-danger float-end ms-2 ansDeleteBtn" data-sibNo="${board.sibNo}">삭제</button>
					<button class="btn btn-info float-end ansUpdateBtn">수정</button>
		     </c:if>
		</c:forEach>
	</div>
	
	
	
	
	<div id="saveCancelBtn" style="display: none;">
		<a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/answer">목록</a>
		<button class="btn btn-secondary float-end ms-2 cancleBtn mb-3">취소</button>
		<button class="btn btn-primary float-end ansSaveBtn">저장</button>
	</div>
</c:if>
<c:if test="${empty board.sibAns }">
	<div class="ansZone">
		<h3 class="text-center">등록된 답변이 없습니다</h3>
		<div id="libDelUpBtn" class="d-flex justify-content-end">
		    <security:authorize access="hasRole('ADMIN')">
		        <button class="btn btn-primary ms-2 ansInsertBtn">등록</button>
		        <a class="btn btn-primary ms-2" href="${pageContext.request.contextPath }/answer">목록</a>
		    </security:authorize>
		    <!-- 관리자 답변 -->
		    <c:if test="${id eq admin}">
		        <button class="btn btn-danger ms-2 ansDeleteBtn">삭제</button>
		        <button class="btn btn-info ms-2 ansUpdateBtn">수정</button>
		    </c:if>
		</div>

		<div id="libSaveCancelBtn" style="display: none;">
			<button class="btn btn-secondary float-end ms-2 cancleBtn mb-3">취소</button>
			<button class="btn btn-primary float-end libSaveBtn">저장</button>
		</div>
	</div>
	<div id="ansInsertDiv" style="display: none;">
		<table id="form-table" class="table table-primary align-middle table-bordered">
			<tr>
				<th class="text-center" style="width: 100px;">답변</th>
				<td class="table-light textarea">
					 <textarea class="form-control" id="editor" style="height: 200px; width: 100%;" placeholder="답변을 입력하세요"></textarea>
				</td>
			</tr>
		</table>
		<button class="btn btn-secondary float-end ms-2 ansCancelBtn">취소</button>
		<button class="btn btn-primary float-end mb-3 ansSaveBtn">저장</button>
	</div>
</c:if>



<%-- <script src="${pageContext.request.contextPath }/resources/js/board/reply.js"></script> --%>

<script src="${pageContext.request.contextPath }/resources/js/board/BoardDetail.js" type="module"></script>
<script src="${pageContext.request.contextPath }/resources/js/board/answerDelete.js"></script>
<%--  <script src="${pageContext.request.contextPath }/resources/js/board/answerEdit.js"></script> --%>
<%-- <script src="${pageContext.request.contextPath }/resources/js/board/answerReplyDelete.js"></script>
 --%>