<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath }" var="cp"/>
<c:set value="${lecture.lectNo }" var="lectNo"/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<ul class="nav mb-3 rounded-pill bg-primary-subtle">
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" aria-current="page" href="${cp }/lecture/${lectNo }/materials">강의실홈</a>
  </li>
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" href="${cp }/lecture/${lectNo }/materials/plan">강의계획서</a>
  </li>
  <security:authorize access="!hasRole('STUDENT')">
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle text-black fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
      출결
    </a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/attendan">출결관리</a></li>
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/absence">공결신청관리</a></li>
    </ul>
  </li>
  </security:authorize>
  <li class="nav-item">
    <a class="nav-link dropdown-toggle text-black fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
      과제
    </a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/assignment">과제관리</a></li>
      <security:authorize access="!hasRole('STUDENT')">
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/assignmentSubmission">제출과제관리</a></li>
      </security:authorize>
    </ul>
  </li>
  <li class="nav-item">
    <a class="nav-link dropdown-toggle text-black fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
      프로젝트
    </a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/projectTask">프로젝트과제관리</a></li>
      <security:authorize access="hasRole('STUDENT')">
      <li><a class="dropdown-item" href="${cp }/lecture/${lectNo }/projectPersonal">나의프로젝트</a></li>
      </security:authorize>
    </ul>
  </li>
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" href="${cp }/lecture/${lectNo }/attendeeTest">시험</a>
  </li>
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" href="${cp }/lecture/${lectNo }/board">공지</a>
  </li>
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" href="${cp }/lecture/${lectNo }/inquiry">문의</a>
  </li>
      <security:authorize access="!hasRole('STUDENT')"> 
  <li class="nav-item">
    <a class="nav-link text-dark fw-bold" href="${cp }/lecture/${lectNo }/dissent">이의 관리</a>
  </li>
      </security:authorize>
</ul>