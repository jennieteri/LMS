/**
 *  입장 시 팀번호 검증
 */

document.addEventListener("DOMContentLoaded",()=>{
	const contextPath = document.querySelector('#mainArea').dataset.path;
	const lectNo = document.querySelector('#mainArea').dataset.lectNo;
	const teamCd = document.querySelector('#mainArea').dataset.teamCd;
	const taskNo = document.querySelector('#mainArea').dataset.taskNo;
	const enterBtns = document.querySelectorAll('.enter-btn');
	const createChatRoomSubmit = document.querySelector('#createChatRoomSubmit');
	
	//채팅방 입장 접근성제한
	 enterBtns.forEach((btn) => {
        btn.addEventListener("click", () => {
            const enterRoom = btn.dataset.enter;
			
            if (teamCd !== enterRoom) {
                swal("입장 불가", "본인 팀 채팅방만 입장 가능합니다.", "error");
                return;
            }

            // 입장 가능한 경우 처리 로직
            const chatRoomUrl = `${contextPath}/lecture/${lectNo}/projectChatRoom/${taskNo}/${teamCd}`;
            window.location.href = chatRoomUrl;
        });
	});
	
	//채팅방 개설
	createChatRoomSubmit.addEventListener("click", async ()=>{
		const url = `${contextPath}/lecture/${lectNo}/projectChatRoom`;
		//vo ==> projectchatroomTitle
		const chatRoomName = document.querySelector('#chatRoomName').value.trim();
		
		if (!chatRoomName) {
        	swal("알림", "채팅방 이름을 입력하세요.", "warning"); // 경고창
        	return;
    	}
		
		const data = {
			teamCd: teamCd
			,projectchatroomTitle: chatRoomName
			,taskNo: taskNo
		};
		
		try {
	        const response = await fetch(`${url}`, {
	            method: "POST",
	            headers: {
	                "Content-Type": "application/json"
	            },
	            body: JSON.stringify(data)
	        });
	
	        if (response.ok) {
				const success = await response.json();
	            swal("성공", `${success.message}` || "채팅방이 생성되었습니다.", "success") // 성공 메시지
	                .then(() => {
	                    location.reload(); // 페이지 새로고침으로 리스트 갱신
	                });
	        } else {
	            const error = await response.json();
	            swal("실패", `채팅방 생성 실패: ${error.message}`, "error"); // 실패 메시지
	        }
	    } catch (error) {
	        console.error("채팅방 생성 오류:", error);
	        swal("오류", "채팅방 생성 중 오류가 발생했습니다.", "error"); // 오류 메시지
	    }
		
	});
	
	
	
	
});//DOM끝