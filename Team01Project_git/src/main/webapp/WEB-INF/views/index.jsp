<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<div id="grid" class="grid-stack-item"></div>
<div class="position-fixed bottom-0 end-0 pe-2 pb-5">
	<div id="modDiv" class="card p-1 fade-transition hidden m-0" style="width: 110px; height: 600px; overflow-y: scroll;">
		<c:forEach items="${modList }" var="m">
			<div class="text-center modDrag fw-bold mx-auto my-2" style="width: 80px;"
				data-w="${m.w }" data-h="${m.h }" data-content="${m.content }" data-mod-id="${m.modId }">
				<i class="bi bi-${m.icon }" style="font-size: 60px; line-height: 1;"></i>
				${m.modNm }
			</div>
		</c:forEach>
	</div>
	<div style="z-index: 1000;">
		<button id="addBtn" class="btn btn-outline-primary float-end">
			<i class="bi bi-plus-lg"></i>
		</button>
	</div>
</div>
<div class="position-fixed top-0 end-0 pe-2 pt-5 mt-2 trash fade-transition hidden">
	<i class="bi bi-trash-fill text-danger" style="font-size: 48px;"></i>
</div>
<security:authentication property="principal.realUser" var="user"/>
<security:authorize access="hasRole('STUDENT')">
	<c:if test="${user.streCateCd eq 'SC06' }">
		<script>
			location.href="${pageContext.request.contextPath }/mypage/${user.id}/UpdateMyPage?fresh=true"
		</script>
	</c:if>
</security:authorize>
<script src="${pageContext.request.contextPath }/resources/js/moduleUI/gridStack.js"></script>