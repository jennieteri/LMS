<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px auto;
        max-width: 900px;
        background-color: #f7f9fc;
        color: #333;
    }
    h3 {
        text-align: center;
        color: #4a90e2;
        margin-bottom: 20px;
    }
    .summary {
        margin-bottom: 20px;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    .summary h4 {
    	text-align: center;
        margin: 0;
        color: #343a40;
    }
    .summary p {
        font-size: 13px;
        color: #555;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 30px;
    }
    th, td {
        padding: 12px;
        border: 1px solid #ddd;
        text-align: center;
    }
    th {
        background-color: #eaf3ff;
        color: #555;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .chart-container {
        margin: 30px auto;
        text-align: center;
    }
    .chart {
        max-width: 700px;
        margin: 0 auto;
    }
    .btn {
        display: inline-block;
        padding: 10px 20px;
        font-size: 16px;
        color: white;
        background-color: #4a90e2;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        text-align: center;
        text-decoration: none;
    }
    .btn:hover {
        background-color: #357abd;
    }
    #closeBtn{
       text-align:right;
    }
</style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <h3>${auth.realUser.nm}님의 직업선호도평가 결과</h3>

    <div class="summary">
        <h4>상위 유형:  <c:out value="${topType}" /></h4>
        <p>가장 적합한 유형은 <strong><c:out value="${topType}" /></strong>입니다.<br>
        아래 추천 직업과 점수를 확인하세요.</p>
        
    </div>

    <table>
        <thead>
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
                <td>연구원, 과학자, 기술 연구원, 환경가</td>
            </tr>
            <tr>
                <td>관습형</td>
                <td><c:out value="${result.jtrCon}" /></td>
                <td>회계사, 기획자, 법무사, 행정공무원</td>
            </tr>
            <tr>
                <td>현실형</td>
                <td><c:out value="${result.jtrRea}" /></td>
                <td>엔지니어, 건축가, 자동차 정비사, IT인프라 전문가</td>
            </tr>
            <tr>
                <td>진취형</td>
                <td><c:out value="${result.jtrEnt}" /></td>
               	<td>기업가, 세일즈맨, 마케터, 프로젝트 매니저, 정치가</td>
            </tr>
        </tbody>
    </table>

    <!-- 차트 -->
    <div class="chart-container">
        <h3>유형별 점수 그래프</h3>
        <canvas id="jobChart" class="chart"></canvas>
    </div>

   <button style="float: right;" type="button" class="btn btn-primary" id="closeBtn" onclick="window.close();">닫기</button>


    <script>
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

        const ctx = document.getElementById('jobChart')
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
                        min: 30         
                    }
                }
            }
        });
    </script>
</body>
</html>
