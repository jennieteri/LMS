/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const calendarEl = document.getElementById("myFullCalendar");
    const myCalendarIcon = document.getElementById("myCalendarIcon");
    const contextPath = document.querySelector("#contextPath").value;
    const addBtn = document.getElementById("addBtn");

    const modal = document.getElementById("myCalendarModal");
    document.body.appendChild(modal);

    const eventModal = document.getElementById("eventModal");
    document.body.appendChild(eventModal);

    const eventModalInstance = new bootstrap.Modal(eventModal);

    // 일정 추가 모달 초기화 함수
    function resetEventModal() {
        document.getElementById("eventForm").reset(); // 폼 초기화
        eventModalInstance.hide(); // 모달 닫기
    }

    // 일정추가 버튼 클릭 이벤트
    addBtn.addEventListener("click", function () {
        document.getElementById("eventStart").removeAttribute("readonly");
        document.getElementById("eventEnd").removeAttribute("readonly");
        eventModalInstance.show();
    });

    // FullCalendar 초기화
    const calendar = new FullCalendar.Calendar(calendarEl, {
		themeSystem: "standard", 
        initialView: "dayGridMonth",
        locale: "ko",
        headerToolbar: {
            left: "prev,next",
            center: "title",
            right: "today",
        },
        editable: true, // 일정 추가 및 삭제 가능
        selectable: true, // 날짜 선택 가능
        events: async function (fetchInfo, successCallback, failureCallback) {
            try {
                const response = await fetch(`${contextPath}/mycalendar`);
                const events = await response.json();
                successCallback(events);
            } catch (error) {
                console.error("Failed to fetch events:", error);
                failureCallback(error);
            }
        },
		// 일정 설명 툴팁 추가
	    eventDidMount: function (info) {
	        // Bootstrap Tooltip 초기화
	        const tooltip = new bootstrap.Tooltip(info.el, {
	            title: info.event.extendedProps.description || "상세 설명 없음",
	            placement: "top",
	            trigger: "hover",
	            container: "body",
	        });
	    },
        // 일정 추가 이벤트
        select: function (info) {
            document.getElementById("eventStart").value = info.startStr;
            document.getElementById("eventEnd").value = info.endStr;
            eventModalInstance.show();

            document.getElementById("saveEventBtn").addEventListener("click",async function () {
	                
					const title = document.getElementById("eventTitle").value;
	                const description = document.getElementById("eventDescription").value;
	                const start = document.getElementById("eventStart").value;
	                const end = document.getElementById("eventEnd").value;
					const type = document.querySelector("select[name='myCalendarCd']").value;
					
					if (!title || !start || !end || !type) {
				        alert("모든 필드를 입력하세요!");
				        return;
				    } 
	                try {
				        // 일정 추가 요청
				        const response = await fetch(`${contextPath}/mycalendar`, {
				            method: "POST",
				            headers: {
				                "Content-Type": "application/json",
				            },
				            body: JSON.stringify({
				                myCalendarTitle: title,
				                myCalendarContent: description,
				                myCalendarSd: start,
				                myCalendarEd: end,
				                myCalendarCd: type,
				            }),
				        });
				
				        if (!response.ok) {
				            throw new Error("일정 추가에 실패했습니다.");
				        }
				
				        const data = await response.json();
				
				        // 성공 메시지 표시
				        swal("성공!", data.message || "일정이 성공적으로 추가되었습니다.", "success");
				
				        // 캘린더 이벤트 다시 로드
				        await refreshCalendarEvents();
				    } catch (error) {
				        console.error("Failed to add event:", error);
				        swal("오류 발생!", "일정을 추가하는 중 문제가 발생했습니다.", "error");
				    } finally {
				        // 모달 닫기 및 초기화
				        resetEventModal();
				        calendar.unselect();
				    }
				},
				
	            { once: true }
	        );
        },
        // 일정 삭제 이벤트
        eventClick: async function (info) {
		    swal({
		        title: `"${info.event.title}" 일정을 삭제하시겠습니까?`,
		        text: "삭제 후 복구할 수 없습니다.",
		        icon: "warning",
		        buttons: ["취소", "삭제"], // 취소 및 삭제 버튼
		        dangerMode: true, // 삭제 버튼 강조
		    }).then(async (willDelete) => {
		        if (willDelete) {
		            try {
		                // DELETE 요청을 PathVariable로 전송
		                const response = await fetch(`${contextPath}/mycalendar/${info.event.id}`, {
		                    method: "DELETE",
		                });
		
		                if (response.ok) {
		                    const result = await response.json();
		                    // 일정 삭제
		                    info.event.remove();
		                    swal("삭제 완료!", result.message || "일정이 성공적으로 삭제되었습니다.", "success");
		                } else {
		                    throw new Error("일정 삭제에 실패했습니다.");
		                }
		            } catch (error) {
		                console.error("Failed to delete event:", error);
		                swal("삭제 실패", "일정을 삭제하는 중 문제가 발생했습니다.", "error");
		            }
		        } else {
		            swal("취소됨", "일정 삭제가 취소되었습니다.", "info");
		        }
		    });
		}//삭제
		
    });

	// 캘린더 이벤트 새로고침 함수
	async function refreshCalendarEvents() {
	    try {
	        const response = await fetch(`${contextPath}/mycalendar`);
	        if (!response.ok) {
	            throw new Error("이벤트를 불러오는 데 실패했습니다.");
	        }
	
	        const events = await response.json();
	
	        // 캘린더 이벤트 리셋 및 새 데이터 추가
	        calendar.removeAllEvents();
	        calendar.addEventSource(events);
	        calendar.refetchEvents(); // 데이터베이스에서 다시 가져오기
	    } catch (error) {
	        console.error("Failed to refresh events:", error);
	        alert("캘린더 이벤트를 갱신하는 중 오류가 발생했습니다.");
	    }
	}

    // 모달이 열릴 때 FullCalendar 렌더링
    myCalendarIcon.addEventListener("click", function () {
        const calendarModalInstance = new bootstrap.Modal(modal);
        calendarModalInstance.show();
        setTimeout(() => {
            calendar.render();
        }, 200); // 모달 애니메이션 후 렌더링
    });
});