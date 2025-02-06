<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="contextData" 
   			  data-context-path="${pageContext.request.contextPath}" >
	<input type="hidden" value="${testNo }" id="testNo" name="testNo">
	<input type="hidden" value="${lectNo }" id="lectNo" name="lectNo">
</div>
<%-- ${lectNo } --%>
<%-- ${testNo } --%>
${professorTestList }
<div id="box">



</div>

<script src="${pageContext.request.contextPath }/resources/js/test/professorTestListScript.js"></script>
     

