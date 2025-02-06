/**
 * 
 */
document.addEventListener("DOMContentLoaded", async () => {
	let cp = document.querySelector("#contextPath").value;

	// 오늘 날짜를 YYYY-MM-DD 형식으로 설정
	const today = new Date().toISOString().split('T')[0];
	const logDateInput = document.querySelector("#logData");

	console.log(today)
	logDateInput.max = today;
	logDateInput.value = today;
	// 오늘 날짜를 최대값으로 설정
	// 초기 데이터 로드
	await getTraficData(cp);

	// 날짜 변경 이벤트 리스너 추가
	logDateInput.addEventListener('change', async () => {
		await getTraficData(cp);
	});
	// Range 입력 필드와 표시 필드 연결
    const secondRange = document.getElementById('secondRange');
	const tokenRange = document.getElementById('tokenRange');
	const secondDisplay = document.getElementById('secondDisplay');
	const tokenDisplay = document.getElementById('tokenDisplay');
	
	// 초기값 설정
	secondDisplay.value = secondRange.value;
	tokenDisplay.value = tokenRange.value;
	
	// range 이벤트 리스너
	secondRange.addEventListener('input', function() {
	    secondDisplay.value = this.value;
	});
	
	tokenRange.addEventListener('input', function() {
	    tokenDisplay.value = this.value;
	});
	
	// input 직접 입력 이벤트 리스너
	secondDisplay.addEventListener('input', function() {
	    // 입력값이 범위를 벗어나면 자동으로 조정
	    if (this.value > this.max) this.value = this.max;
	    if (this.value < this.min) this.value = this.min;
	    secondRange.value = this.value;
	});
	
	tokenDisplay.addEventListener('input', function() {
    // 입력값을 숫자로 변환
    let value = parseInt(this.value) || 0;
    
    // 최소값, 최대값 범위 내로 제한
    value = Math.min(Math.max(value, tokenRange.min), tokenRange.max);
    
    // 입력필드와 range 슬라이더 값 동기화
    this.value = value;
    tokenRange.value = value;
});

});

async function getTraficData(cp) {
	const logDate = document.querySelector("#logData").value;
	const date = logDate.replace(/-/g, '');

	try {
		const resp = await fetch(`${cp}/log/trafic/${date}`);
		const data = await resp.json();
		
		console.log(data)
		//const filterData = fnRemoveUnderProperties(data);
		
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
					text: `${logDate} 사용량`,
					align: 'center',
					position: 'top',
					font: {
						size: 16,
						weight: 'bold'
					},
					padding: {
						top: 10,
						bottom: 30
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
							return `${koreanName}: ${item.logCount}회 요청`;
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
		
		
console.log(data.traficMethodLogList)
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
                padding: 20,
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
                    return `${label}: ${value}회 (${percentage}%)`;
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

	} catch (error) {
		console.error('Error:', error);
	}
}


let fnRemoveUnderProperties = function(obj) {
	if (typeof obj !== 'object' || obj === null) {
		return obj; // 객체가 아니거나 null인 경우 그대로 반환
	}

	// 객체를 복제하면서 null이 아닌 값만 유지
	if (Array.isArray(obj)) {
		return obj.map(item => fnRemoveNullProperties(item)).filter(item => item !== null);
	}

	const result = {};
	for (const key in obj) {
		if (obj[key] !== null) {
			result[key] = fnRemoveNullProperties(obj[key]);
		}
	}
	return result;
};

// TODO 트래픽 설정용 함수(관리자 페이지 이동 필요)
function bucket() {
    const second = document.querySelector("#secondDisplay").value;
    const token = document.querySelector("#tokenDisplay").value;
    let cp = document.querySelector("#contextPath").value;

    // 값 출력
    console.log("Second:", second);
    console.log("Token:", token);
    console.log("Context Path:", cp);
    fetch(`${cp}/bucket/${second}/${token}`)
        .then(resp => resp.json())
        .then(data => {
            console.log("Response:", data);
			swal("성공", "설정이 변경되었습니다.", "success")
        })
        .catch(err => {
            console.error("Error:", err);
        });

}


function bucket2() {
    const second = document.querySelector("#second").value;
    const token = document.querySelector("#token").value;
    const cp = document.querySelector("#contextPath").value;

    // 값 출력
    console.log("Second:", second);
    console.log("Token:", token);
    console.log("Context Path:", cp);

    fetch(`${cp}/bucket/second/${second}/${token}`)
        .then(resp => resp.json())
        .then(data => {
            console.log("Response:", data);
        })
        .catch(err => {
            console.error("Error:", err);
        });
 
}


    





