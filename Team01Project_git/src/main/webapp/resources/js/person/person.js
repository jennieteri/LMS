function showPersonDetail(personId) {
    axios.get(`../person/detail/${personId}`)
        .then(resp => {
            const person = resp.data; // 서버 응답 데이터
			console.log(person);
            const personDetailModal = new bootstrap.Modal(document.getElementById('personDetailModal'));
            personDetailModal.show();
			for(key in person){
				console.log(key);
				$(`#detail-${key}`).text(person[key]);
			}
        })
        .catch(err => {
            console.error(err);
            alert("사용자 정보실패");
        });
}



function showPersonUpdate(personId) {
    axios.get(`../person/editform/${personId}`) 
        .then(resp => {
            console.log(resp.data);
            const person = resp.data; 
            const personUpdateModal = new bootstrap.Modal(document.getElementById('personUpdateModal'));
            personUpdateModal.show();

            //매핑
            for (const key in person) {
                const element = document.getElementById(`update-${key}`);
                if (element) {
                    if (element.tagName === 'INPUT' || element.tagName === 'TEXTAREA') {
                        element.value = person[key]; // input, textarea에 값 설정
                    } else {
                        element.textContent = person[key]; // 일반 텍스트 요소에 값 설정
                    }
                } else {
                    console.log(`없어`);
                }
            }
        })
        .catch(err => {
            console.error(err);
            alert("사용자 정보를 불러오는 데 실패했습니다.");
        });
}




document.addEventListener("DOMContentLoaded", () => {
	
	const insertBtn = document.querySelector("#singleinsert-btn");
	const pwBtn = document.querySelector("#resetpwBtn");
	const delBtn = document.querySelector("#deleteBtn");
	
	insertBtn.addEventListener("click", () => {
		window.location.href = "../person/new";
		
	});
	
	pwBtn.addEventListener("click",()=>{
		console.log("나여기");
	const updateData={
		  id: document.getElementById("update-id").textContent
	};
	console.log("수정할 id" , updateData);
		swal({
			title: "비밀번호를 초기화 하시겠습니까?",
			//text: ,
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`../person/pwedit/${updateData.id}`, updateData)
				.then(resp=>{
					swal({
						title: "초기화 완료",
						text: "사용자의 비밀번호가 성공적으로 초기화되었습니다",
						icon: "success",
						button: "확인"
					});
				}).catch(err=>{
					swal({
						title: "초기화 실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				})
			}
		});
	}) //수정 버튼
	
	
	delBtn.addEventListener("click",()=>{
		console.log("삭제버튼");
		
	const updateData={
		id: document.getElementById("detail-id").textContent
	};
	console.log("삭제할 id", updateData);
		swal({
			title: "정말로 삭제하시겠습니까?",
			//text: ,
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`../person/delete/${updateData.id}`, updateData)
				.then(resp=>{
					swal({
						title: "삭제완료",
						text: "사용자가 성공적으로 삭제되었습니다",
						icon: "success",
						button: "확인"
					});
				}).catch(err=>{
					swal({
						title: "삭제실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				})
			}
		});
	})//삭제 버튼


});


document.getElementById("saveUpdateBtn").addEventListener("click", function () {
    const updatedData = {
        id: document.getElementById("update-id").textContent,  
        mbtlnum: document.getElementById("update-mbtlnum").value, 
        eml: document.getElementById("update-eml").value,    
    };

    console.log("수정 데이터:", updatedData); // 수정된 데이터 확인용

    // 수정 요청
    axios.put(`../person/edit/${updatedData.id}`, updatedData)
        .then(response => {
            console.log("수정 성공:", response.data);

            if (response.data === "OK") { // 수정 성공시
                const personDetailModal = new bootstrap.Modal(document.getElementById("personDetailModal"));
                const personDetail = updatedData; // 서버에서 반환된 수정된 데이터

                for (const key in personDetail) {
                    const detailElement = document.getElementById(`detail-${key}`);
                    if (detailElement) {
                        detailElement.textContent = personDetail[key];
                    }
                }

                const personUpdateModal = bootstrap.Modal.getInstance(document.getElementById("personUpdateModal"));
                if (personUpdateModal) {
                    personUpdateModal.hide();  
                }

                personDetailModal.show();  
            } else {
                alert("수정에 실패했습니다.");
            }
        })
        .catch(err => {
            console.error("수정 실패:", err);
            alert("수정 요청에 실패했습니다.");
        });
});


async function getPersonLog(personId) {
    try {
        const cp = document.querySelector("#cp").value;
        

        const response = await fetch(`${cp}/log/${personId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        
        const logDiv = document.querySelector("#logDiv");
        
        let logContent = `
            <h4 class="text-center">사용자 로그</h4>
            <div class="log-content mt-3">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>접속일자</th>
                            <th>접속시간</th>
                            <th>접속IP</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${data.logList.map(log => `
                            <tr>
                                <td>${log.logDate}</td>
                                <td>${log.logTime}</td>
                                <td>${log.logContNm}</td>
                                <td>${log.method}</td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
                <div class="paging-area">
                    ${data.pagingHTML}
                </div>
            </div>
        `;
        
        logDiv.innerHTML = logContent;

        // 페이징 이벤트 핸들러 추가
        document.querySelectorAll('.paging-area a').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const pageNum = e.target.dataset.page;
                getPersonLog(pageNum);
            });
        });

    } catch (error) {
        console.error('Error:', error);
        alert('로그 조회 중 오류가 발생했습니다.');
    }
}



//------------------------------------------------------------
window.onload =async function () {
	const cp = document.querySelector("#cp").value;
	const resp = await fetch(`${cp}/log/admin`)
	const data = await resp.json();
	
	console.log(data)
	const list = data.todayLogList;   // [{logDate: '20250101', logCount: 120}, ...]
	const totalCount = data.totalCountLog;
	
	// 날짜와 로그 수 데이터를 추출
	const labels = list.map(item => {
    const dateStr = item.logDate;
    return `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6)}`;
});

const values = list.map(item => item.logCount);   // logCount 값으로 data 생성
	
	/*
	const todayLog = document.querySelector("#todayLog");
	todayLog.innerHTML=totalCount;
	*/
	
	// 차트 생성
	const ctx = document.getElementById('myChart').getContext('2d');
	const myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: labels,    // 날짜 배열
	        datasets: [{
	            label: '일별 로그인 수',
	            data: values,   // 로그 수 배열
	            backgroundColor: 'rgba(75, 192, 192, 0.2)',
	            borderColor: 'rgba(75, 192, 192, 1)',
	            borderWidth: 2
	        }]
	    },
	    options: {
	        responsive: true,          // 부모 div 크기에 맞춰 반응형 설정
	        maintainAspectRatio: false // 비율 고정 해제
	    }
	});

    };

//-----------------------------------------------


/*


document.addEventListener("DOMContentLoaded", () => {
	const updateBtn = document.querySelector("#update-Btn");
	updateBtn.addEventListener("click", () => {
		const personId = document.querySelector("#personId").value;
		$.ajax({
			url: '../person/edit/' + personId, //여기로보내
			type: 'PUT',  
			success: function() {
				window.location.href = "../person/list";
			},
			error: function() {
				alert("수정 실패");
			}
		});
	});
});

*/
document.addEventListener("DOMContentLoaded", () => {
    const cp = document.querySelector("#cp").value;

    fetch(`${cp}/person/statistics`)
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
					responsive: true,
                    maintainAspectRatio: false,
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
});



