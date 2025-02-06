<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">졸업</li>
    <li class="breadcrumb-item active" aria-current="page">논문 리스트</li>
  </ol>
</nav>


<div class="container mt-5">
    <table class="table table-bordered table-hover mt-4">
        <thead class="table-primary">
            <tr>
                <th scope="col" class="text-center">No.</th>
                <th scope="col" class="text-center">제목</th>
                <th scope="col" class="text-center">논문 상태</th>
                <th scope="col" class="text-center">지도 교수</th>
                <th scope="col" class="text-center">등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="list" items="${list}">
                <tr>
                    <td class="text-center">${list.rnum}</td>
                    <td class="text-center">${list.gpaNm}</td>
                    <td class="text-center">${list.gpaStatus}</td>
                    <td class="text-center">${list.professorNm}</td>
                    <td class="text-center">${list.gpaDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
