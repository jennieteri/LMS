<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="card m-0 p-2" id="userChartDiv" style="width: 100%; height: 100%;">
    <h6 class="text-center">유형별 사용자 수</h6>
    <canvas id="userChart" style="width: 100%; height: 100%;"></canvas>
</div>
<script>
    fetch(`${pageContext.request.contextPath}/person/statistics`)
        .then(response => response.json())
        .then(data => {
			console.log("data",data);
			const filteredData = data.filter(item => item.USERTYPE !== '알 수 없음');
			console.log("filteredData",filteredData);
            const labels = filteredData.map(item => item.USERTYPE);
            const counts = filteredData.map(item => item.COUNT);

            const ctx = document.getElementById('userChart').getContext('2d');
			new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: '사용자 유형별 통계',
						data: counts,
						backgroundColor: [
							'rgba(75, 192, 192, 0.5)', // 약간 밝은 청록색
							'rgba(54, 162, 235, 0.5)', // 밝은 파란색
							'rgba(153, 102, 255, 0.5)', // 연한 보라색
							'rgba(255, 206, 86, 0.5)'  // 연한 노란색
						],
						borderColor: [
							'rgba(75, 192, 192, 1)', // 청록색
							'rgba(54, 162, 235, 1)', // 파란색
							'rgba(153, 102, 255, 1)', // 보라색
							'rgba(255, 206, 86, 1)'  // 노란색
						],
						borderWidth: 2
					}]
				},
				options: {
                    scales: {
                        x: {
                            beginAtZero: true
                        },
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        })
        .catch(err => console.error('사용자 통계 로드 오류:', err));
</script>