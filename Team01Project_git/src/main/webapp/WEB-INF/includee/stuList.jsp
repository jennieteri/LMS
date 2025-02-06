<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="list-group">
  <li class="list-group-item list-group-item-primary fw-bold text-center">담당교수</li>
  <li class="list-group-item text-center">${lecture.professorVO.nm } 교수님</li>
  <c:set value="${lecture.attendeeList }" var="attendeeList"/>
  <li class="list-group-item list-group-item-primary fw-bold text-center">수강생(${attendeeList.size() }명)</li>
  <c:forEach items="${attendeeList }" var="attendee">
  	<li class="list-group-item">${attendee.studentVO.nm }(${attendee.stuId })</li>
  </c:forEach>
</ul>