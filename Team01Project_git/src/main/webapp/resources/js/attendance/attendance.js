
document.addEventListener("DOMContentLoaded", ()=>{
	let filters = [];
	
	let filterWeekCd = null;  // 필터링된 weekCd
	let filterLectOrder = null;  // 필터링된 lectOrder
	
	const lectNo = document.querySelector('#lectNo').value;
	
	// 모든 학생 ID를 배열로 수집
	var studentIds = [];
	
	document.querySelectorAll('.student-id').forEach(function(cell) {
	    var stuId = cell.getAttribute('data-stu-id');
	    if (stuId) {
	        studentIds.push(stuId);
	    }
	});
	
	
	document.querySelector('.fetch-attendance-btn').addEventListener('click', function() {
	    // 여기서 서버 요청을 통해 데이터를 받아와서 테이블을 채울 수 있습니다.
	    // 데이터를 받아오는 로직이 끝난 후 해당 tbody를 표시합니다.
	
	    // 예시: 데이터가 성공적으로 로드된 경우
	    document.getElementById('attendanceTableBody').style.display = 'table-row-group';  // <tbody>를 표시
	});
	
	
	
	
	// 일자 강의차수 교시 보여주는 테이블
	// 모든 "조회" 버튼에 클릭 이벤트 리스너를 추가합니다.
	document.querySelectorAll('.fetch-attendance-btn').forEach(button => {
	    button.addEventListener('click', function(event) {
	        event.preventDefault(); // 기본 버튼 동작을 막습니다.
	
	        // 클릭된 버튼에서 data-* 속성 값 가져오기
	        const sectDt = this.getAttribute('data-sect-dt');  // 일자
	        const lectOrder = this.getAttribute('data-lect-order');  // 강의차수
	        const cocoStts = this.getAttribute('data-coco-stts');  // 교시
	        filterWeekCd = this.getAttribute('data-week-cd');  // 교시
        	filterLectOrder = this.getAttribute('data-lect-order');

	        // 값을 업데이트하는 함수 호출
	        updateTargetRow(sectDt, lectOrder, cocoStts);
		
			const buttonsContainer = document.getElementById('attendanceButtons');
			if (buttonsContainer) {
			    buttonsContainer.style.display = 'block';  // 버튼들이 보이도록 설정
			} else {
			    console.log("attendanceButtons 요소를 찾을 수 없습니다.");
			}

	    });
	});

	// 출결률 정보 담기
	let result = [0, 0, 0, 0]; 
	
	// targetRow를 업데이트하는 함수
	function updateTargetRow(sectDt, lectOrder, cocoStts) {
	    // targetRow의 각 셀을 찾아서 값 업데이트
		
		let year = sectDt.substring(0, 4);
		let month = sectDt.substring(4, 6);
		let day = sectDt.substring(6, 8);
		
		// 'YYYY-MM-DD' 형식으로 결합
		let formattedDate = `${year}-${month}-${day}`;
		
		// 변환된 날짜를 요소에 출력
		document.getElementById('sectDtCell').textContent = formattedDate;
	    document.getElementById('lectOrderCell').textContent = lectOrder;
	    document.getElementById('cocoSttsCell').textContent = cocoStts;
	}
	
	document.querySelectorAll('.fetch-attendance-btn').forEach(button => {
	    button.addEventListener('click', function(event) {
	        event.preventDefault();  // 기본 동작 막기
			const cp = document.querySelector('#contextPath').value;
			filters = [];
	        // 클릭된 버튼에서 data-* 속성 값 가져오기
	        filterWeekCd = this.getAttribute('data-week-cd');  // 주차 코드
	        filterLectOrder = this.getAttribute('data-lect-order');  // 강의차수
			
			result = [0, 0, 0, 0];
			
			filters.push({ 
				lectNo: lectNo
				, weekCd: filterWeekCd
				, lectOrder: filterLectOrder });
			attendanceStatusData = [];
			
			
			axios.get(`${cp}/lecture/${lectNo}/attendan/${filterLectOrder}`)
			.then(resp=>{
				resp.data.attendanceVOList.forEach(vo=>{
					let atndCode = vo.atndCd; // ATN1, ATN2, ATN3, ATN4 중 하나

	                // 각 atndCode에 대해 result 배열 업데이트
	                switch (atndCode) {
	                    case 'ATN1':
	                        result[0]++;  // ATN1 (출석)
	                        break;
	                    case 'ATN2':
	                        result[1]++;  // ATN2 (지각)
	                        break;
	                    case 'ATN3':
	                        result[2]++;  // ATN3 (결석)
	                        break;
	                    case 'ATN4':
	                        result[3]++;  // ATN4 (조퇴)
	                        break;
	                    default:
	                        break;
	                }
					
					let tr = document.querySelector(`.attendanceRow[data-stu-id='${vo.stuId}']`);
					$(tr).find(`button[data-status!='${vo.atndCd}']`).removeClass('selected');
					$(tr).find(`button[data-status='${vo.atndCd}']`).addClass('selected');
				})
				document.querySelector('#ATN1').textContent = result[0];  // ATN1 (출석)
	            document.querySelector('#ATN2').textContent = result[1];  // ATN2 (지각)
	            document.querySelector('#ATN3').textContent = result[2];  // ATN3 (결석)
	            document.querySelector('#ATN4').textContent = result[3];  // ATN4 (조퇴)

				createPieChart(result);
			})
	    });
	});
	
	
	
	
	
	
	// 출석 상태를 저장할 배열
	let attendanceStatusData = [];
	
	// 출석 상태 버튼 클릭 시 상태를 임시 저장
	document.querySelectorAll('.attendance-btn').forEach(button => {
	    button.addEventListener('click', function(event) {
	        event.preventDefault();
	        // 클릭한 버튼의 출석 상태 (ATND_CD)
	        const selectedStatus = this.getAttribute('data-status');
	        
	        // 클릭한 버튼의 부모 행 (학생 출결 상태가 표시된 tr)
	        const tr = this.closest('tr');  // 해당 버튼이 포함된 tr 요소를 찾아냄
	        const studentId = tr.getAttribute('data-stu-id');  // 학생 ID (tr에 data-stu-id 속성 있다고 가정)
	
	        // 해당 학생의 출석 상태가 이미 선택되었는지 확인
	        let statusObj = attendanceStatusData.find(item => item.stuId === studentId);

			
	
	        if (!statusObj) {
	            // 해당 학생의 출석 상태가 아직 저장되지 않았다면 새로 추가
	            statusObj = {
	                stuId: studentId
	                , atndCd: selectedStatus
					, lectNo: `${lectNo}`
					, lectOrder: `${filterLectOrder}`
	            };
	            attendanceStatusData.push(statusObj);
	        } else {
	            // 이미 저장된 학생이라면 상태 변경
	            statusObj.atndCd = selectedStatus;
	        }
	
	        // 해당 학생의 모든 버튼에서 'selected' 클래스 제거
	        const buttons = tr.querySelectorAll('.attendance-btn');
	        buttons.forEach(btn => btn.classList.remove('selected'));
	
	        // 클릭한 버튼에만 'selected' 클래스 추가
	        this.classList.add('selected');
	    });
	});
	
	// 상태 저장 버튼 클릭 시, 서버로 출석 상태 업데이트
	document.getElementById('saveAttendanceButton').addEventListener('click', function() {
	    console.log("내가 저장할 임시데이터 : ", attendanceStatusData);
		
		if (!lectNo || !filterLectOrder) {
			swal({
                title: '강의를 선택해주세요.',	
                text: '선택된 강의가 없습니다.',
                icon: 'warning',
                confirmButtonText: '확인'
            });
            return;
        }
	    
	    // 출석 상태 중복 체크 후 업데이트
	    updateAttendanceStatus(attendanceStatusData);
	});
	
	// 출석 상태 업데이트 요청 (서버로 전송)
	
	function updateAttendanceStatus(attendanceData) {
		
	    const cp = document.querySelector('#contextPath').value;
	    const url = `${cp}/lecture/${lectNo}/attendan/edit`; // 실제 URL 수정
	
	    // attendanceData 배열을 그대로 JSON으로 전송
	    fetch(url, {
	        method: 'PUT',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(attendanceData) // JSON 배열로 변환하여 보냄
	    })
	    .then(response => {
	        console.log('Status:', response.status); // HTTP 상태 코드 확인
	        console.log('Headers:', response.headers.get('Content-Type')); // 응답 Content-Type 확인
	        return response.json();
	    })
	    .then(data => {
	        console.log('출석 상태 업데이트 성공:', data);
	        swal({
                title: '출석 저장 성공',
                text: '선택한 출석 정보가 성공적으로 저장되었습니다.',
                icon: 'success',
                confirmButtonText: '확인'
            }).then(() => {
				const cp = document.querySelector('#contextPath').value;
		
			axios.get(`${cp}/lecture/${lectNo}/attendan/${filterLectOrder}`)
				.then(resp=>{
					let result = [0, 0, 0, 0];	
					resp.data.attendanceVOList.forEach(vo=>{
						let atndCode = vo.atndCd; // ATN1, ATN2, ATN3, ATN4 중 하나
	
		                // 각 atndCode에 대해 result 배열 업데이트
		                switch (atndCode) {
		                    case 'ATN1':
		                        result[0]++;  // ATN1 (출석)
		                        break;
		                    case 'ATN2':
		                        result[1]++;  // ATN2 (지각)
		                        break;
		                    case 'ATN3':
		                        result[2]++;  // ATN3 (결석)
		                        break;
		                    case 'ATN4':
		                        result[3]++;  // ATN4 (조퇴)
		                        break;
		                    default:
		                        break;
		                }
						
						let tr = document.querySelector(`.attendanceRow[data-stu-id='${vo.stuId}']`);
						$(tr).find(`button[data-status!='${vo.atndCd}']`).removeClass('selected');
						$(tr).find(`button[data-status='${vo.atndCd}']`).addClass('selected');
					})
					document.querySelector('#ATN1').textContent = result[0];  // ATN1 (출석)
		            document.querySelector('#ATN2').textContent = result[1];  // ATN2 (지각)
		            document.querySelector('#ATN3').textContent = result[2];  // ATN3 (결석)
		            document.querySelector('#ATN4').textContent = result[3];  // ATN4 (조퇴)
	
					createPieChart(result);
				})
				
		        //window.location.reload(); // 페이지 새로 고침
		    });
	    })
	    .catch(error => {
	        console.error('출석 상태 업데이트 오류:', error);
	        swal({
                title: '출석 저장 실패',
                text: '출석 상태를 저장하지 못했습니다.',
                icon: 'error',
                confirmButtonText: '확인'
            });
	    });
	}

	
	// 출석 상태 기록용, 버튼을 누를 때마다 저장되고 중복은 처리 안됨
	function updateAttendanceStatusInArray(stuId, lectOrder, lectNo, atndCd) {
	    
		// 중복 체크 후 출석 상태 추가
	    const existingEntryIndex = attendanceStatusData.findIndex(update => 
	        update.stuId === stuId &&
	        update.lectOrder === lectOrder &&
	        update.lectNo === lectNo &&
	        update.atndCd === atndCd 
	    );
	
	    if (existingEntryIndex === -1) {  // 중복이 없는 경우
	        attendanceStatusData.push({
	            stuId: stuId,
	            lectOrder: lectOrder,
	            lectNo: lectNo,
	            atndCd: atndCd
	        });
	    } else {  // 중복이 있는 경우, 출석 상태만 업데이트
	        attendanceStatusData[existingEntryIndex].atndCd = atndCd;
	    }
	
	    console.log('현재 attendanceStatusData 배열:', attendanceStatusData);
	}
	
	// 출석 버튼 클릭 시 업데이트 (for문으로 배열 순회하여 업데이트)
	document.querySelectorAll('.attendance-btn').forEach(button => {
	    button.addEventListener('click', function() {
			
	        const tr = this.closest('tr');
	        const stuId = tr.getAttribute('data-stu-id');
	        const atndCd = this.getAttribute('data-status');
	        const lectOrder = filterLectOrder;
	        
	        // 중복 체크 후 출석 상태 기록
	        updateAttendanceStatusInArray(stuId, lectOrder, lectNo, atndCd);
	    });
	});
	
	// 전부 출석 버튼 클릭 시 처리
	document.getElementById('markAllPresentButton').addEventListener('click', function() {
	    filterWeekCd = filters[0].weekCd;
	    filterLectOrder = filters[0].lectOrder;
	
	    if (filterWeekCd && filterLectOrder) {  // 필터링된 주차와 회차가 있는 경우
	        const rows = document.querySelectorAll('.attendanceRow');
	
	        rows.forEach((row, index) => {
				
		
	            const stuId = row.dataset.stuId;
				let statusObj = attendanceStatusData.find(item => item.stuId === stuId);
				
				if (!statusObj) {
			        // 해당 학생의 출석 상태가 아직 저장되지 않았다면 새로 추가
				    statusObj = {
				        stuId: stuId
				        , atndCd: 'ATN1'
						, lectNo: lectNo
						, lectOrder: filterLectOrder
			        };
			        attendanceStatusData.push(statusObj);
			    } else {
			        // 이미 저장된 학생이라면 상태 변경
			        statusObj.atndCd = 'ATN1';
			    }
				
				
				// 해당 학생의 모든 버튼에서 'selected' 클래스 제거
		        const buttons = row.querySelectorAll('.attendance-btn');
		        buttons.forEach(btn => btn.classList.remove('selected'));
		
		        // 클릭한 버튼에만 'selected' 클래스 추가
		        row.querySelectorAll('.attendance-btn')[0].classList.add('selected');	
	        });
	    } else {
	        alert('조회된 데이터가 없습니다. 먼저 조회를 해주세요.');
	    }
	});





	
	// 차트 데이터


    // 모달 열기 버튼에 클릭 이벤트 리스너 추가
    /*
	document.getElementById('.fetch-attendance-btn').addEventListener('click', function() {
	    // result 배열이 모두 0인지 체크
	    if (result.every(value => value === 0)) {
	        swal("알림", "해당 강의에는 학생이 없습니다.", "warning");
	    } else {
	        createPieChart(result);  // result 배열을 차트 생성 함수에 전달
	    }
	});
	*/
	
// 원형 차트를 그리는 함수 (애니메이션 포함)
function createPieChart(result) {
    const width = 200;
    const height = 200;
    const radius = Math.min(width, height) / 2;

    // 0이 아닌 값만 필터링하여 새로운 배열로 생성
    const filteredResult = result
        .map((value, index) => ({
            value,
            label: ['출석', '지각', '결석', '조퇴'][index]
        }))
        .filter(d => d.value > 0);  // 0이거나 0보다 큰 항목은 모두 포함

    // 도넛 차트를 위한 innerRadius 값 설정 (innerRadius > 0 이면 도넛 형태)
    const innerRadius = 35;  // 100으로 설정하여 가운데 부분을 비우기

    const svg = d3.select("#pieChart")
                  .html('')  // 기존의 차트를 지움
                  .attr("width", width)
                  .attr("height", height)
                .append("g")
                  .attr("transform", `translate(${width / 2}, ${height / 2})`); // 원을 중앙에 배치

    const pie = d3.pie().value(d => d.value);
    const arc = d3.arc().innerRadius(innerRadius).outerRadius(radius);  // 도넛 차트 형태로 설정

    // 각 항목의 색상과 그라데이션 정의
    const colorScale = {
        '출석': ['#2196F3', '#1976D2'],
        '지각': ['#4CAF50', '#388E3C'],
        '결석': ['#FFEB3B', '#FBC02D'],
        '조퇴': ['#F44336', '#D32F2F']
    };

    // 애니메이션 진행 중에는 마우스 이벤트를 비활성화
    const paths = svg.selectAll("path")
                     .data(pie(filteredResult))
                     .enter().append("path")
                     .attr("d", arc)
                     .attr("fill", (d) => {
                         const color = colorScale[d.data.label]; // 해당 항목의 색상 선택
                         const gradient = svg.append("defs")
                                             .append("linearGradient")
                                             .attr("id", "gradient-" + d.data.label)
                                             .attr("x1", "0%")
                                             .attr("y1", "100%")
                                             .attr("x2", "100%")
                                             .attr("y2", "0%");
                         gradient.append("stop")
                                 .attr("offset", "0%")
                                 .attr("stop-color", color[0]);
                         gradient.append("stop")
                                 .attr("offset", "100%")
                                 .attr("stop-color", color[1]);
                         return `url(#gradient-${d.data.label})`;  // 그라데이션 색상 적용
                     })
                     .attr("stroke", "white")
                     .attr("stroke-width", 2)
                     .attr("opacity", 0)  // 초기 투명도 설정
                     .style("pointer-events", "none")  // 애니메이션 중에는 마우스 이벤트 비활성화
                     .transition()
                     .duration(1000)
                     .attr("opacity", 1)  // 서서히 보이도록 설정
                     .attrTween("d", function(d) {
                        const i = d3.interpolate({ startAngle: 0, endAngle: 0 }, d);
                        return function(t) { return arc(i(t)); };
                     })
                     .on("end", function() {
                         // 애니메이션 끝난 후에만 마우스 이벤트를 활성화
                         d3.select(this).style("pointer-events", "all");
                     });

    // 텍스트 레이블 추가 (그래프의 각 조각에 퍼센트 표시)
    svg.selectAll("text")
       .data(pie(filteredResult))
       .enter().append("text")
       .attr("transform", (d) => `translate(${arc.centroid(d)})`)  // 각 조각의 중심에 텍스트를 배치
       .attr("text-anchor", "middle")
       .attr("font-size", "12px")
       .attr("font-weight", "bold")
       .attr("fill", "#fff")
       .style("text-shadow", "4px 4px 4px rgba(0,0,0,0.7)")  // 검은색 그림자 추가
       .text((d) => {
           const percentage = Math.round((d.data.value / d3.sum(filteredResult.map(d => d.value))) * 100);
           return `${d.data.label} (${percentage}%)`;  // 퍼센트를 추가
       });

    // 전체 출석률 계산
    const total = result.reduce((acc, value) => acc + value, 0);
    let attendanceRateText = "출결 정보가 없음"; // 기본 텍스트 설정

    if (total > 0) {
        const attendanceRate = (result[0] / total) * 100;  // 출석률 계산 (출석만 반영)
        attendanceRateText = `${Math.round(attendanceRate)}%`;  // 출석률 %를 표시
    }

    console.log('출석률: ', attendanceRateText);
    
    // 가운데 텍스트로 '출석률' 먼저 표시
    svg.append("text")
       .attr("text-anchor", "middle")
       .attr("font-size", "12px")
       .attr("font-weight", "bold")
       .attr("fill", "#000")
       .style("text-shadow", "2px 2px 4px rgba(255,255,255,0.7)")  // 흰색 그림자 추가
       .text("출석률");  // '출석률' 텍스트 먼저 추가

    // 출석률 텍스트 추가
    svg.append("text")
       .attr("text-anchor", "middle")
       .attr("font-size", "18px")
       .attr("font-weight", "bold")
       .attr("fill", "#000")
       .attr("dy", "1.5em")  // '출석률' 텍스트 아래로 약간 내려서 퍼센트 표시
       .style("text-shadow", "2px 2px 4px rgba(255,255,255,0.7)")  // 흰색 그림자 추가
       .text(attendanceRateText); // '출결 정보가 없음' 또는 출석률 %를 표시
}















});