<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">직업선호도평가</li>
    <li class="breadcrumb-item active" aria-current="page">직업선호도평가 결과</li>
  </ol>
</nav>


<body class="bg-light">
    <div class="container mt-5">
        <div class="text-center mb-4">
            <h3 class="text-dark"><c:out value="${result.student.nm}" />님의 직업선호도평가 결과</h3>
        </div>

        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h4 class="card-title text-primary">상위 유형: <c:out value="${topType}" /></h4>
                <p class="card-text">가장 적합한 유형은 <span style="font-weight: bold;"><c:out value="${topType}" /></span>
입니다.<br>
                    아래 추천 직업과 점수를 확인하세요.</p>
            </div>
        </div>

        <!-- 테이블 -->
        <div class="table-responsive mb-4">
            <table class="table table-bordered text-center align-middle">
                <thead class="table-primary">
                    <tr>
                        <th>유형</th>
                        <th>점수</th>
                        <th>추천 직업</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>사회형</td>
                        <td><c:out value="${result.jtrSoc}" /></td>
                        <td>사회복지사, 상담사, 교사, 사회활동가</td>
                    </tr>
                    <tr>
                        <td>예술형</td>
                        <td><c:out value="${result.jtrArt}" /></td>
                        <td>디자이너, 예술가, 작곡가, 무대 디자이너, 광고 디렉터</td>
                    </tr>
                    <tr>
                        <td>탐구형</td>
                        <td><c:out value="${result.jtrInq}" /></td>
                        <td>연구원, 과학자, 데이터 분석가, 기술 연구원, 환경가</td>
                    </tr>
                    <tr>
                        <td>관습형</td>
                        <td><c:out value="${result.jtrCon}" /></td>
                        <td>회계사, 기획자, 법무사, 행정공무원, 데이터 관리 전문가</td>
                    </tr>
                    <tr>
                        <td>현실형</td>
                        <td><c:out value="${result.jtrRea}" /></td>
                        <td>엔지니어, 기술자, 건축가, 자동차 정비사, IT인프라 전문가</td>
                    </tr>
                    <tr>
                        <td>진취형</td>
                        <td><c:out value="${result.jtrEnt}" /></td>
                        <td>기업가, 세일즈맨, 마케터, 프로젝트 매니저, 정치가</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- 차트 -->
        <div class="card shadow-sm">
            <div class="card-body text-center">
                <h4 class="card-title">유형별 점수 그래프</h4>
                <div class="chart-container mx-auto" style="max-width: 400px;">
                    <canvas id="jobChart"></canvas>
                </div>
            </div>
        </div>

<security:authorize access="hasRole('EMPLOYEE')">
    <div class="btn-area1" style="float: right; margin-left: 3px;">
        <a href="<c:url value='/jobtestresult' />" 
           class="btn btn-primary" 
           style="cursor: pointer;">
            목록
        </a>
    </div>
</security:authorize>

<!-- STUDENT 역할 확인 -->
<security:authorize access="hasRole('STUDENT') or hasRole('EMPLOYEE')">
    <div class="clearfix" style="margin-left: 20px;">
        <button class="btn btn-primary" 
                style="float: right; cursor: pointer;" 
                onclick="window.history.back();">
            홈으로 가기
        </button>
    </div>
</security:authorize>


</body>


	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
    document.addEventListener("DOMContentLoaded",function(){
    	
 
        const data = {
            labels: ['사회형', '예술형', '탐구형', '현실형', '관습형', '진취형'],
            datasets: [{
                label: '직업 선호도 점수',
                data: [
                    ${result.jtrSoc}, 
                    ${result.jtrArt}, 
                    ${result.jtrInq}, 
                    ${result.jtrRea}, 
                    ${result.jtrEnt}, 
                    ${result.jtrCon}
                ],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        };

        const ctx = document.getElementById('jobChart');
        new Chart(ctx, {
            type: 'radar',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    }
                },
                scales: {
                    r: {
                        beginAtZero: false,
                        min : 30
                        
                    }
                }
            }
        });
    });
    </script>