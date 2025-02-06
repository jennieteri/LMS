<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h6 class="fw-bold">[${lecture.subjectVO.subFicdCdCommonCodeVO.cocoStts }]<br>${lecture.lectNm }</h6>
<c:if test="${empty lecture.professorVO.proflPhoto }">
	<c:set value="${pageContext.request.contextPath }/resources/NiceAdmin/assets/img/user.png" var="proflPhotoSrc"/>
</c:if>
<c:if test="${not empty lecture.professorVO.proflPhoto }">
	<c:set value="data:image/*;base64,${lecture.professorVO.proflPhoto }" var="proflPhotoSrc"/>
</c:if>
<img width="150px" src="${proflPhotoSrc }">
<h5 class="mt-3 fw-bold">교수 ${lecture.professorVO.nm }</h5>