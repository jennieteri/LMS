/**
 * 채팅방 WebSocket 및 이전 대화 기록 관리
	날짜가 다를 때 첫번째 메시지에는 날짜가 나와야한다.
 */
document.addEventListener("DOMContentLoaded", () => {
    const messageContainer = document.getElementById("message-container");
    const messageInput = document.getElementById("message-input");
    const sendButton = document.getElementById("send-btn");
	const mainDiv = document.getElementById("mainDiv");
	const lectNo = mainDiv.dataset.lectNo;
    const taskNo = messageContainer.dataset.taskNo;
    const teamCd = messageContainer.dataset.teamCd;
    const contextPath = messageContainer.dataset.path;
    const userId = messageContainer.dataset.user;
	// 지난 기록 가져오는 url
    const chatUrl = `${contextPath}/projectChatMessage/${taskNo}/${teamCd}`; // 이전 대화 내역 조회 API URL
    
	// 웹소켓 연결 url
	const wsUrl = `${contextPath}/ws-chat`;

    let stompClient;

	const baseUrl = `${contextPath}/lecture/${lectNo}`;
	const memberChart = document.querySelector('#memberChart');
	const projectChart = document.querySelector('#projectChart');
	
	async function memberChartRendering(teamCd){
		const resp = await fetch(`${baseUrl}/projectDuty/getChart/${teamCd}`)
		
		if (!resp.ok) {
            console.error("데이터 로드 실패:", resp.statusText);
            return;
        }
		
		const data = await resp.json();
		
			// 반환된 데이터에서 dutyId, dutyTitle, projectMember.nm 조합 생성
	    const projectDutyList = data.projectDutyList;
	    const progressData = projectDutyList.map(duty => duty.dutyPrgsRtprgs); // 각 dutyPrgsRtprgs 추출
	
	    // 차트 렌더링
	    const ctx = memberChart.getContext("2d");
		    new Chart(ctx, {
			    type: "bar",
			    data: {
			        labels: projectDutyList.map(duty => duty.projectMember.nm), // 멤버 이름만 라벨에 표시
			        datasets: [{
			            label: "진행률 (%)",
			            data: progressData,
			            backgroundColor: "#3498db",
			            borderWidth: 1
			        }]
			    },
			    options: {
			        responsive: true,
			        plugins: {
			            tooltip: {
			                callbacks: {
			                    label: function(context) {
			                        const duty = projectDutyList[context.dataIndex];
			                        return `ID: ${duty.dutyId}\n이름: ${duty.projectMember.nm}\n제목: ${duty.dutyTitle}\n진척도: ${context.raw}%`;
			                    }
			                }
			            }
			        },
			        scales: {
			            y: {
			                beginAtZero: true,
			                max: 100
			            }
			        }
			    }
			});
			
	}
	memberChartRendering(teamCd);
	
	async function projectChartRendering(teamCd) {
	    const resp = await fetch(`${baseUrl}/projectTeam/getChart/${teamCd}`);
	    
	    if (!resp.ok) {
	        console.error("데이터 로드 실패:", resp.statusText);
	        return;
	    }
	
	    const data = await resp.json();
	
	    // 프로젝트 목적 및 마감일 추출
	    const projectPurpose = data.projectTeam?.teamPurpo || "Unknown Project"; // 프로젝트 목적
	    const projectDeadline = data.projectTeam?.projectTask?.taskEt; // 프로젝트 마감일 (예: "20250117")
	
	    if (!projectDeadline) {
	        console.error("프로젝트 마감일(projectTask.taskEt)이 없습니다.");
	        return;
	    }
	
	    console.log("프로젝트 목적:", projectPurpose);
	    console.log("프로젝트 마감일:", projectDeadline);
	
	    // 현재 날짜 및 시작일 계산
	    const currentDate = new Date();
	    const deadlineDate = new Date(
	        projectDeadline.substring(0, 4), // 년
	        projectDeadline.substring(4, 6) - 1, // 월 (0부터 시작)
	        projectDeadline.substring(6, 8) // 일
	    );
	    const startDate = new Date(currentDate);
	    startDate.setMonth(startDate.getMonth() - 1); // 오늘로부터 한 달 전으로 시작일 설정
	
	    const totalDuration = Math.ceil((deadlineDate - startDate) / (1000 * 60 * 60 * 24)); // 전체 기간
	    const elapsedDuration = Math.ceil((currentDate - startDate) / (1000 * 60 * 60 * 24)); // 경과 기간
	    const remainingDuration = totalDuration - elapsedDuration > 0 ? totalDuration - elapsedDuration : 0; // 남은 기간
	
	    console.log("전체 기간:", totalDuration);
	    console.log("경과 기간:", elapsedDuration);
	    console.log("남은 기간:", remainingDuration);
	
	    // 차트 데이터 생성
	    const labels = ["경과 기간", "남은 기간"];
	    const progressData = [elapsedDuration, remainingDuration];
	
	    // 차트 렌더링
	    const ctx = projectChart.getContext("2d");
	    new Chart(ctx, {
	        type: "doughnut", // 도넛 차트
	        data: {
	            labels: labels,
	            datasets: [{
	                label: `프로젝트: ${projectPurpose}`,
	                data: progressData,
	                backgroundColor: ["#ff9800", "#4caf50"], // 경과 기간(주황색), 남은 기간(녹색)
	                borderWidth: 1
	            }]
	        },
	        options: {
	            responsive: true,
				scales: {
				            y: {
				                beginAtZero: true,
				                max: 100
				            }
				        },
	            plugins: {
	                legend: {
	                    display: true,
	                    position: "top"
	                },
	                tooltip: {
	                    callbacks: {
	                        label: function(context) {
	                            const label = context.label || "";
	                            const value = context.raw || 0;
	                            const percentage = ((value / totalDuration) * 100).toFixed(2); // 퍼센트 계산
	                            return `${label}: ${value}일 (${percentage}%)`;
	                        }
	                    }
	                }
	            }
	        }
	    });
	}
	
	
	projectChartRendering(teamCd);




    // WebSocket 및 STOMP 설정
    const connectWebSocket = () => {
        const socket = new SockJS(wsUrl);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            console.log("WebSocket 연결 성공");

            // 채팅방 구독
            stompClient.subscribe("/topic/chat", (messageOutput) => {
                const message = JSON.parse(messageOutput.body);
                displayMessage(message);
            });

            // 입장 메시지 전송
            sendJoinMessage();
        }, (error) => {
            console.error("WebSocket 연결 실패:", error);
            swal("오류", "WebSocket 연결에 문제가 발생했습니다. 10초 후 다시 시도합니다.", "error");

            // 재연결 시도
            setTimeout(connectWebSocket, 10000);
        });
    };
    
	// 전역 변수 선언
	let lastMessageDate = null;
	 // 메시지 화면에 표시
    const displayMessage = (message) => {
	    const messageDate = new Date(message.projectchatmessageDt).toLocaleDateString(); // 메시지의 날짜
	    const currentDate = new Date().toLocaleDateString(); // 현재 날짜
		// person 객체가 존재하고 nm 값이 있으면 이름 출력
    	const senderName = message.person?.nm || "알 수 없음";
		
	    // 날짜가 달라질 경우 날짜 표시 추가
	    if (lastMessageDate !== messageDate) {
	        const dateElement = document.createElement("div");
	        dateElement.classList.add("date-divider");
	        dateElement.textContent = messageDate === currentDate ? "오늘" : messageDate; // 오늘이면 "오늘"로 표시
	        messageContainer.appendChild(dateElement);
	        lastMessageDate = messageDate; // 마지막 날짜 업데이트
	    }
	
	    const messageElement = document.createElement("div");
	    messageElement.classList.add("message");
	
	    // 발신자와 수신자 구분
	    if (message.projectchatmessageContent.includes("입장하셨습니다")) {
	        messageElement.classList.add("system-message"); // 입장 메시지 스타일
			
			// 입장 메시지는 발신자 이름을 생략하고 내용만 표시
	        const bubbleElement = document.createElement("div");
	        bubbleElement.classList.add("bubble");
	        bubbleElement.textContent = message.projectchatmessageContent;
	        messageElement.appendChild(bubbleElement);
	
	        messageContainer.appendChild(messageElement);
	        scrollToBottom();
	        return; // 이후 로직 실행 방지
    
	    } else if (message.projectchatmessageSender == `${userId}`) {
	        messageElement.classList.add("sent");

			//삭제처리를위한 클릭이벤트추가
			messageElement.addEventListener("click", () => {
            confirmDeleteMessage(message.projectchatmessageId, messageElement);
        	});

	    } else {
	        messageElement.classList.add("received");
	    }
	
	    // 보낸 사람 ID와 시간 표시
	    const senderElement = document.createElement("div");
	    senderElement.classList.add("sender");

		
	    const messageTime = new Date(message.projectchatmessageDt).toLocaleTimeString('ko-KR', {
	        hour: '2-digit',
	        minute: '2-digit',
	    }); // 시간 포맷 지정
	    senderElement.textContent = `${senderName} (${messageTime})`;
	
	    // 말풍선 요소 생성
	    const bubbleElement = document.createElement("div");
	    bubbleElement.classList.add("bubble");
	    bubbleElement.textContent = message.projectchatmessageContent;
	
	    // ID와 말풍선 추가
	    messageElement.appendChild(senderElement);
	    messageElement.appendChild(bubbleElement);
	    messageContainer.appendChild(messageElement);
	
	    scrollToBottom();
	};

	// 메시지 전송
    const sendMessage = () => {
        const content = messageInput.value.trim();

        if (!content) {
            swal("알림", "메시지를 입력하세요.", "warning");
            return;
        }

        const message = {
            taskNo: taskNo,
            teamCd: teamCd,
            projectchatmessageContent: content,
            projectchatmessageSender: userId, // 사용자 ID 설정
        };

        // 메시지 전송 (STOMP 사용)
        stompClient.send("/app/chat/send", {}, JSON.stringify(message));

        // 입력창 초기화
        messageInput.value = "";
    };

    // 채팅 메시지 영역 스크롤을 항상 아래로 유지
    const scrollToBottom = () => {
        messageContainer.scrollTop = messageContainer.scrollHeight;
    };

    // 이벤트 리스너 설정
    sendButton.addEventListener("click", sendMessage);
    messageInput.addEventListener("keypress", (e) => {
        if (e.key == "Enter") {
            sendMessage();
        }
    });


    // 이전 대화 내역 가져오기
    const fetchChatHistory = async () => {
        try {
            messageContainer.innerHTML = "<p>로딩 중...</p>"; // 로딩 메시지 표시

            const response = await fetch(chatUrl);
            if (!response.ok) throw new Error("메시지 내역 조회 실패");

            const messages = await response.json();
            messageContainer.innerHTML = ""; // 로딩 완료 후 초기화

            messages.forEach((message) => {
                displayMessage(message);
            });

            scrollToBottom();
        } catch (error) {
            console.error("메시지 내역 조회 실패:", error);
            swal("오류", "채팅 내역을 불러오지 못했습니다. 다시 시도해주세요.", "error");
        }
    };

   // 입장 메시지 전송
    const sendJoinMessage = () => {
        const joinMessage = {
            taskNo: taskNo,
            teamCd: teamCd,
            projectchatmessageSender: userId,
            projectchatmessageContent: "", // 서버에서 "입장하셨습니다" 메시지 생성
        };

        stompClient.send(`/app/chat/join`, {}, JSON.stringify(joinMessage));
    };

	//삭제처리
	const confirmDeleteMessage = async (messageId, messageElement) => {
	    swal({
	        title: "삭제 확인",
	        text: "이 메시지를 삭제하시겠습니까?",
	        icon: "warning",
	        buttons: ["취소", "삭제"],
	        dangerMode: true,
	    }).then(async (willDelete) => {
	        if (willDelete) {
	            try {
	                const response = await fetch(`${contextPath}/projectChatMessage/${messageId}`, {
	                    method: "DELETE",
	                    headers: {
	                        "Content-Type": "application/json",
	                    },
	                });
	
	                if (response.ok) {
	                    swal("삭제 완료", "메시지가 삭제되었습니다.", "success");
	                    messageContainer.removeChild(messageElement); // 메시지 UI에서 제거
	                } else {
	                    swal("삭제 실패", "메시지 삭제에 실패했습니다. 다시 시도해주세요.", "error");
	                }
	            } catch (error) {
	                console.error("메시지 삭제 실패:", error);
	                swal("오류 발생", "메시지를 삭제하는 중 문제가 발생했습니다.", "error");
	            }
	        }
	    });
	};
    

    // 초기 메시지 로드
    fetchChatHistory();

    // WebSocket 연결
    connectWebSocket();

});//DOM 끝