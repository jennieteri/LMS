<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="row">
    <div class="col-md-8" style="margin-bottom: -20px;">
		<div class="card" style="height: 340px;">
            <div class="card-body" style="position: relative; padding-top: 0;">
				<canvas id="trafficChart" style="width: 100%; height: 300px;"></canvas>
            </div>
        </div>
    </div>
    <!-- 오른쪽 상단 박스 -->
    <div class="col-md-4" style="margin-bottom: 0;">
		<div class="card" style="height: 340px;">
            <div class="card-body">
                <!-- 오른쪽 상단 내용 -->
                <canvas id="circleChart"></canvas>
            </div>
        </div>
    </div>
</div>

<!--             <div class="d-flex align-items-center p-2" style="border-bottom: 1px solid #dee2e6;"> -->
<!--                 <input type="date" class="form-control w-25" id="logData" style="height: 35px; font-size: 14px;"> -->
<!--             </div> -->
  <script>
 async function module(){
 
			const resp = await fetch(`${pageContext.request.contextPath}/log/trafic/module/data`);
			const data = await resp.json();
			
			//console.log(data)
			
			const day = data.traficLogList[0].logDate
			
			//console.log(day)
			
			const formatDate = `\${day.slice(0,4)}-\${day.slice(4,6)}-\${day.slice(6)}`;
			//console.log(formatDate); // 2024-12-12

			
			const chartData = {
				datasets: [{
					label: '기능별 요청량',
					data: data.traficLogList.map((item, index) => ({
						x: index,
						y: item.logCount,
						r: Math.sqrt(item.logCount) * 3.3, // 버블 크기 조정
					})),
					backgroundColor: 'rgba(54, 162, 235, 0.5)',
					borderColor: 'rgba(54, 162, 235, 1)',
					borderWidth: 1
				}]
			};
			const options = {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					title: {
						display: true,
						text: `\${formatDate} 사용량`,
						align: 'center',
						position: 'top',
						font: {
							size: 16,
							weight: 'bold'
						},
						padding: {
							top: 10,
							bottom: 10
						}
					},
					tooltip: {

						callbacks: {
							label: function(context) {
								const item = data.traficLogList[context.dataIndex];
								let name = item.logContNm.replace('Controller', '');

								// 영문을 한글로 매핑
								const koreanNames = {
									'LectureMaterials': '강의자료',
									'ProjectChat': '프로젝트 채팅',
									'JobTest': '취업시험',
									'Lecture': '강의',
									'Attendee': '출석',
									'AbsenceCertificateReceipt': '결석증명서',
									'Noticeboard': '공지사항',
									'ProjectTask': '프로젝트 과제',
									'ProjectPersonal': '프로젝트 개인',
									'person': '사용자',
									'SecondStepAuth': '2차인증',
									'Mypage': '마이페이지',
									'Session': '세션',
									'ProjectTeam': '프로젝트 팀',
									'AdminTrafic': '관리자 트래픽',
									'Index': '메인',
									// 필요한 만큼 매핑 추가
								};

								const koreanName = koreanNames[name] || name; // 매핑된 한글명이 없으면 원래 이름 사용
								return `\${koreanName}: \${item.logCount}회 요청`;
							}
						}
					},

					legend: {
						position: 'top',
						labels: {
							boxWidth: 10
						}
					}
				},
				scales: {
					x: {
						grid: {
							display: true,
							color: 'rgba(0, 0, 0, 0.1)',
							size: '10px'
						},
						ticks: {
							autoSkip: false
						},
						title: {
							display: true,
							text: '기능 갯수'
						}

					},
					y: {
						beginAtZero: true,
						grid: {
							display: true,
							color: 'rgba(0, 0, 0, 0.1)'
						},
						title: {
							display: true,
							text: '요청 횟수'
						}
					}
				}
			};
			// 기존 차트 제거
			if (window.myBubbleChart) {
				window.myBubbleChart.destroy();
			}

			// 새 차트 생성
			window.myBubbleChart = new Chart(
				document.getElementById('trafficChart'),
				{
					type: 'bubble',
					data: chartData,
					options: options
				}
			);
			
			
			// 원형 차트 데이터 준비
	const methodColors = {
	  '조회': 'rgba(54, 162, 235, 1)',
	  '생성': 'rgba(255, 99, 132, 1)',
	  '수정': 'rgba(255, 206, 86, 1)',
	  '삭제': 'rgba(75, 192, 192, 1)',
	  'default': 'rgba(153, 102, 255, 1)'
	};
	const circleChartData = {
		
	    labels: data.traficMethodLogList.map(item => {
	        let method = item.logMethod;

	    switch (method) {
	        case 'GET':
	            method = '조회';
	            break;
	        case 'POST':
	            method = '생성';
	            break;
	        case 'PUT':
	            method = '수정';
	            break;
	        case 'DELETE':
	            method = '삭제';
	            break;
	        default:
	            method = method; // 기본값은 영어 그대로
	            break;
	    }

	    return method;
				
	    }),


	   datasets: [{
	        data: data.traficMethodLogList.map(item => item.logCount),
	        backgroundColor: data.traficMethodLogList.map(item => {
	            let method = item.logMethod;
	            switch (method) {
	                case 'GET': return methodColors['조회'];
	                case 'POST': return methodColors['생성'];
	                case 'PUT': return methodColors['수정'];
	                case 'DELETE': return methodColors['삭제'];
	                default: return methodColors['default'];
	            }
	        }),
	        borderColor: data.traficMethodLogList.map(item => {
	            let method = item.logMethod;
	            switch (method) {
	                case 'GET': return methodColors['조회'];
	                case 'POST': return methodColors['생성'];
	                case 'PUT': return methodColors['수정'];
	                case 'DELETE': return methodColors['삭제'];
	                default: return methodColors['default'];
	            }
	        }),
	        borderWidth: 1
	    }]
	};

	const circleOptions = {
	    responsive: true,
	    maintainAspectRatio: false,
	    plugins: {
	        title: {
	            display: true,
	            text: '사용자 요청 비율',
	            font: {
	                size: 16,
	                weight: 'bold'
	            }
	        },
	        legend: {
	            position: 'bottom',
	            labels: {
	                padding: 10,
	                color: (context) => {
	                    return methodColors[context.text] || methodColors['default'];
	                }
	            }
	        },
	        tooltip: {
	            callbacks: {
	                label: function(context) {
	                    const label = context.label || '';
	                    const value = context.raw || 0;
	                    const total = context.dataset.data.reduce((acc, curr) => acc + curr, 0);
	                    const percentage = ((value / total) * 100).toFixed(1);
	                    return `\${label}: \${value}회 (\${percentage}%)`;
	                }
	            }
	        }
	    }
	};

	// 기존 원형 차트 제거
	if (window.myCircleChart) {
	    window.myCircleChart.destroy();
	}

	// 새 원형 차트 생성
	window.myCircleChart = new Chart(
	    document.getElementById('circleChart'),
	    {
	        type: 'doughnut',
	        data: circleChartData,
	        options: circleOptions
	    }
	);
 }
 
 module();

  </script>