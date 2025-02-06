<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학생 관리</li>
    <li class="breadcrumb-item active" aria-current="page">학생 논문 리스트</li>
  </ol>
</nav>

<body>
<div class="container mt-5">

    <!-- 논문 테이블 -->
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                	
                    <th class="text-center">No.</th>
                    <th class="text-center">논문 제목</th>
                    <th class="text-center">학생 번호</th>
                    <th class="text-center">학생 이름</th>
                    <th class="text-center">제출일자</th>
                    <th class="text-center">상태</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="paper" items="${papers}">
                    <tr>
                        <td class="text-center">${paper.rnum}</td>
                        <td class="text-center">
                        	<a href="${pageContext.request.contextPath }/graduationpaper/${paper.gpaCd}">${paper.gpaNm}</a>
                        </td>
                        <td class="text-center">${paper.stuId}</td>
                        <td class="text-center">${paper.studentNm}</td>
                        <td class="text-center">${paper.gpaDate}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${paper.gpaStatus eq 'G003'}">대기</c:when>
                                <c:when test="${paper.gpaStatus eq 'G004'}">승인</c:when>
                                <c:when test="${paper.gpaStatus eq 'G005'}">반려</c:when>
                                <c:otherwise>알 수 없음</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty papers}">
                    <tr>
                        <td colspan="5" class="text-center">등록된 논문이 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>