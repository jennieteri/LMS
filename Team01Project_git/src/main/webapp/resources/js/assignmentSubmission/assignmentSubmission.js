/**
 *
	 제출과제 js 
	1. 리스트 출력
 	2. 채점(gradeBtn)
	
	3. 현재 로그인한 사용자의 아이디를 가져와 비교

 */

var contextPath = document.getElementById('mainDiv').dataset['path'];
var userId = document.getElementById('mainDiv').dataset['auth'];
var lectNo = document.getElementById('mainDiv').dataset.lectNo;
var Url = `${contextPath}/lecture/${lectNo}/assignmentSubmission`;

document.addEventListener("DOMContentLoaded", async ()=>{
	// 채점하기버튼
	const gradeBtn = document.querySelector('#gradeBtn');
	const totalAttendee = document.querySelector('#attendeeCount').value;
	async function getOverallChart() {
        try {
            // 서버에서 데이터 가져오기
            const resp = await fetch(`${Url}/getChart`);
            if (!resp.ok) {
                console.error("데이터 가져오기 실패:", resp.statusText);
                return;
            }

            const data = await resp.json();
			console.log(data);
            const assignmentList = data.assignmentList || []; // 과제 리스트
			
			console.log("assignmentList===>",assignmentList);
	        // 과제별 데이터 준비
	        const assignmentNames = [];
	        const submissionRates = [];
	        const gradingRates = [];
	
	        assignmentList.forEach(assignment => {
				console.log("assignment===>",assignment);
				
	            const submissions = assignment.assignmentsubmissionList || []; // 제출 리스트가 없을 경우 빈 배열 사용
				console.log("submissions===>",submissions);
	            const totalSubmissions = submissions.length; // 전체 제출 수
	
	            // 제출 및 채점 여부를 확인하기 전에 데이터가 있는지 체크
	            const completedSubmissions = submissions.filter(sub => sub.assubYn === 'Y').length; // 제출 완료 수
	            const gradedSubmissions = submissions.filter(sub => sub.assubScore !== null).length; // 채점 완료 수
	
	            const submissionRate = totalSubmissions > 0 ? Math.round((completedSubmissions / totalAttendee) * 100) : 0;
	            const gradingRate = totalSubmissions > 0 ? Math.round((gradedSubmissions / totalAttendee) * 100) : 0;
				
				const truncatedName = assignment.assigNm.length > 7
			        ? assignment.assigNm.substring(0, 7) + "..."
			        : assignment.assigNm;
			
			    assignmentNames.push(truncatedName); // 과제명 (7자 제한)
	            submissionRates.push(submissionRate); // 제출률
	            gradingRates.push(gradingRate); // 채점률
	        });
	
	        // 제출률 차트
	        const submissionCtx = document.getElementById("submissionRateChart").getContext("2d");
	        new Chart(submissionCtx, {
	            type: "bar",
	            data: {
	                labels: assignmentNames,
	                datasets: [
	                    {
	                        label: "제출률 (%)",
	                        data: submissionRates,
	                        backgroundColor: "rgba(52, 152, 219, 0.7)", // 파란색
	                        borderColor: "rgba(41, 128, 185, 1)",
	                        borderWidth: 1
	                    }
						, {
	                        label: "채점률 (%)",
	                        data: gradingRates,
	                        backgroundColor: "rgba(46, 204, 113, 0.7)", // 초록색
	                        borderColor: "rgba(39, 174, 96, 1)",
	                        borderWidth: 1
	                    }
	                ]
	            },
	            options: {
					maintainAspectRatio: false,
	                scales: {
	                    y: {
	                        beginAtZero: true,
	                        max: 100, // 퍼센트로 표시
	                        ticks: {
	                            callback: function(value) {
	                                return value + "%"; // y축에 % 표시
	                            }
	                        }
	                    }
	                },
	                plugins: {
	                    legend: { display: true, position: "top" },
	                    tooltip: {
	                        callbacks: {
	                            label: function(context) {
			                        const assignmentIndex = context.dataIndex;
			                        const originalName = assignmentList[assignmentIndex].assigNm;
			                        return `${context.dataset.label}: ${context.raw}%\n과제명: ${originalName}`;
			                    }
	                        }
	                    }
	                },
					maxBarThickness: 30 // 막대 최대 두께 제한
	            }
	        });
	
	    } catch (error) {
	        console.error("오류 발생:", error);
	    }
    }

    // 차트 데이터 가져오기 및 렌더링
    getOverallChart();
	
	if(gradeBtn){
		gradeBtn.addEventListener("click",async (e)=>{
			
			const assigScore = gradeBtn.dataset.limit;
			const assigNo = gradeBtn.dataset.assigno;
			const stuId = gradeBtn.dataset.stuid;
			const inputGrade = document.querySelector('#inputGrade').value;
			const url = `${Url}/grade`;

			if(assigScore< inputGrade){
				swal("잘못된 점수값", "해당과제의 배점보다 높은 점수를 입력할 수 없습니다!", "error");
			}
			else{
				swal({
		            title: "점수를 입력하시겠습니까?",
		            text: "한번 입력한 점수값은 성적변경신청없이 수정불가합니다.",
		            icon: "warning",
		            buttons: ["확인","취소"]
				}).then(async (willDelete)=>{
					if(!willDelete){
						try{
							let resp = await fetch(url,{
								method: 'POST'
								,headers: {
									'content-type' : 'application/json'
								}
								,body: JSON.stringify({
									lectNo: lectNo
									,assigNo: assigNo
									,stuId: stuId
									,assubScore: inputGrade
								})
							});//fetch
							if(resp.ok){
								swal("점수입력완료.","점수가 정상적으로 입력되었습니다.","success")
								.then(()=>{
									location.reload();
								})
							}else{
								let jsonData = await resp.json();
								swal("실패",jsonData.message,"error");
							}
						}catch(e){
							console.log("에러:",e);
							swal("서버 오류", "과제 제출 취소 중 오류가 발생했습니다.", "error");
						}
					}else{
						swal("취소","취소되었습니다!","success")
							.then(()=>{
								location.reload();
							})
					}
					
				})
				
			}
			
		});//gradesubmitBtn 이벤트 끝
	}
});//DOM 끝
