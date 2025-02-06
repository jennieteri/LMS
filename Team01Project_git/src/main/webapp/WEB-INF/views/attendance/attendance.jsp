<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">

<div>
	<div class="mb-3">
	    <select id="lectNo" name="lectNo" class="form-select form-select-sm" onchange="loadAttendanceData()">
	        <option value="" disabled selected>강의를 선택해주세요.</option>
	        <c:forEach items="${selectLectureList}" var="list">
	            <option value="${list.lectNo}">${list.lectureVO.lectNm}</option>
	        </c:forEach>
	    </select>
	</div>
	
	<div id="pieChart">
        <canvas id="myPieChart"></canvas>
    </div>
</div>
<security:authorize access="!hasRole('STUDENT')">
	<input type="hidden" value="studentPass" id="studentPass">
</security:authorize>


<!-- d3 쓰지말고 차트.js 로 바꾸기 -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/attendance/attendanceList.js"></script>
