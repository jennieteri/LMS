<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<div class="text-center fw-bold">
<p>현재 진행중인 프로젝트가 없습니다.</p>
</div>
<input id="messageInput" type="hidden" value="${message }">

<c:if test="${not empty message }">
	<script>
		document.addEventListener("DOMContentLoaded", () => {
		    const messageInput = document.querySelector('#messageInput').value;
		    if (messageInput) {
		        swal("알림!", messageInput, "info");
		    }
		});
	</script>
</c:if>
