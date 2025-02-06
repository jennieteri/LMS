<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>



<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 유형 조회</li>
  </ol>
</nav>


<div class="container">
    <div class="row row-cols-1 row-cols-md-3 g-4">

        <!-- 신청서: 학생, 교직원, 관리자만 볼 수 있음 -->
        <security:authorize access="hasAnyRole('ROLE_STUDENT', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')">
            <c:forEach var="award" items="${award}">
                <c:if test="${award.awardType eq '신청서'}">
                    <div class="col">
                        <div class="card h-100">
                         <div class="card-header" style="background-color: blue;">
						   </div>
						  
                            <div class="card-body">
                                <h5 class="card-title">${award.awardNm}</h5>
                                <p class="card-text">${award.awardDetail}</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">유형: ${award.awardType}</li>
                                <li class="list-group-item">금액: <fmt:formatNumber value="${award.awardGiveAmt}" type="number" pattern="#,###"/> 원</li>
                                <li class="list-group-item">혜택: ${award.awardBenefit}</li>
                                <li class="list-group-item">제출 서류: ${award.awardDocument}</li>
                            </ul>
                            <div class="card-footer" style="text-align: right">
                                <!-- 신청서의 신청하기 버튼: 학생만 볼 수 있음 -->
                                <security:authorize access="hasRole('ROLE_STUDENT')">
                                    <a href="${pageContext.request.contextPath}/askAward/createAwardAsk/new?awardCd=${award.awardCd}&awardNm=${award.awardNm}" 
                                       class="card-link">신청하기</a>
                                </security:authorize>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </security:authorize>

        <!-- 추천서: 교수, 교직원, 관리자만 볼 수 있음 -->
        <security:authorize access="hasAnyRole('ROLE_PROFESSOR', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')">
            <c:forEach var="award" items="${award}">
                <c:if test="${award.awardType eq '추천서'}">
                    <div class="col">
                        <div class="card h-100">
                         <div class="card-header" style="background-color: blue;">
						   </div>
                            <div class="card-body">
                                <h5 class="card-title">${award.awardNm}</h5>
                                <p class="card-text">${award.awardDetail}</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">유형: ${award.awardType}</li>
                                <li class="list-group-item">금액: <fmt:formatNumber value="${award.awardGiveAmt}" type="number" pattern="#,###"/> 원</li>
                                <li class="list-group-item">혜택: ${award.awardBenefit}</li>
                                <li class="list-group-item">제출 서류: ${award.awardDocument}</li>
                            </ul>
                            <div class="card-footer" style="text-align: right">
                                <!-- 추천서의 신청하기 버튼: 교수만 볼 수 있음 -->
                                <security:authorize access="hasRole('ROLE_PROFESSOR')">
                                    <a href="${pageContext.request.contextPath}/recommendAward/createRecAward/new?awardCd=${award.awardCd}&awardNm=${award.awardNm}" 
                                       class="card-link">신청하기</a>
                                </security:authorize>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </security:authorize>

    </div>
</div>
