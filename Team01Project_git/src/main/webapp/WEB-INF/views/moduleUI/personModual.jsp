<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 기간별 로그인 수 -->

<input type="hidden" id="lsCp" value="${pageContext.request.contextPath }">
<div class="col-md-6" id="logChartDiv" style="width: 100%; height: 70%;">
    <h6 class="text-center">기간별 로그인 수</h6>
    <canvas id="lsLogModule" style="width: 100%; height: 70%;"></canvas>
</div>

<script>
async function modualLog(){
	
	const lsCp = document.querySelector("#lsCp").value;
	const resp = await fetch(`\${lsCp}/log/admin`)
	const data = await resp.json();
	
	console.log(data)
	const list = data.todayLogList;   // [{logDate: '20250101', logCount: 120}, ...]
	const totalCount = data.totalCountLog;
	
	// 날짜와 로그 수 데이터를 추출
	const labels = list.map(item => {
    const dateStr = item.logDate;
    return `\${dateStr.slice(0, 4)}-\${dateStr.slice(4, 6)}-\${dateStr.slice(6)}`;
});

const values = list.map(item => item.logCount);   // logCount 값으로 data 생성
	
	/*
	const todayLog = document.querySelector("#todayLog");
	todayLog.innerHTML=totalCount;
	*/
	
	// 차트 생성
	const ctx = document.getElementById('lsLogModule').getContext('2d');
	const lsLogModule = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: labels,    // 날짜 배열
	        datasets: [{
	            label: '일별 로그인 수',
	            data: values,   // 로그 수 배열
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
	        responsive: true,          // 부모 div 크기에 맞춰 반응형 설정
	        maintainAspectRatio: false // 비율 고정 해제
	    }
	});
}

modualLog();
</script>