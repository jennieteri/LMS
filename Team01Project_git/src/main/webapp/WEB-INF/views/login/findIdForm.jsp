<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h4>학번찾기</h4>
<form id="findForm" action="${pageContext.request.contextPath}/member/findIdCheck" method="post">
	<div class="form-group">
    	<input type="text" name="name" id="name" placeholder="이름">
    </div>
    <div class="form-group">
    	<input type="email" name="email" id="email" placeholder="이메일">
    </div>
    <button type="submit" id="id-find" onclick="findSubmit(); return false;">아이디 찾기</button>
</form>

<div class="result-box">
	<c:choose>
		<c:when test="${empty person.id}">
		<p class="inquiry">조회결과가 없습니다.</p>
		</c:when>
        <c:otherwise>
            <p>${person.id}</p>
        </c:otherwise>
	</c:choose>
</div>