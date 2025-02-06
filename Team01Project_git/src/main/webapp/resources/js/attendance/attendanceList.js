function loadAttendanceData() {
    // 선택된 강의 번호 가져오기
    var contextPath = document.getElementById("contextPath").value;
    var lectNo = document.getElementById("lectNo").value;
	
	fetch(`${contextPath}/aten/list?lectNo=${lectNo}`)
		.then(response => response.json())
		.then(data => {
			const chartData = data.map(item => ({
	            atndCd: mapAttendanceCode(item.atndCd), // 코드 값을 라벨로 변환
	            count: item.count
            }));
			 drawArcChart(chartData);
		})
		.catch(error => {
			console.error('Error:', error);  // 에러 발생 시 콘솔에 출력
		});
}


// 'ATN1', 'ATN2', 'ATN3', 'ATN4'를 '출석', '지각', '결석', '조퇴'로 매핑
function mapAttendanceCode(code) {
    const attendanceLabels = {
        'ATN1': '출석',
        'ATN2': '지각',
        'ATN3': '결석',
        'ATN4': '조퇴'
    };
    return attendanceLabels[code] || '알 수 없음'; // 매칭되지 않는 코드에 대한 처리
}


function drawArcChart(chartData) {
    const ctx = document.getElementById('myPieChart').getContext('2d');
    
    // 기존 차트가 있으면 삭제
    if (window.myPieChart instanceof Chart) {
        window.myPieChart.destroy();
    }
    
    // 새 차트 생성
    window.myPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: chartData.map(d => d.atndCd),
            datasets: [{
                data: chartData.map(d => d.count),
                backgroundColor: chartData.map(d => {
                    const colorMap = {
                        '출석': '#2196F3',
                        '지각': '#4CAF50',
                        '결석': '#FFEB3B',
                        '조퇴': '#F44336'
                    };
                    return colorMap[d.atndCd] || '#000';
                }),
                borderColor: chartData.map(d => {
                    const colorMap = {
                        '출석': '#2196F3',
                        '지각': '#4CAF50',
                        '결석': '#FFEB3B',
                        '조퇴': '#F44336'
                    };
                    return colorMap[d.atndCd] || '#000';
                }),
                borderWidth: 1
            }]
        },
        options: {
            responsive: true
        }
    });
}
	








