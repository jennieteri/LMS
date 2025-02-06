/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
    const calendarEl = document.getElementById("fullCalendar");
    const calendarIcon = document.getElementById("calendarIcon");
	const contextPath = calendarIcon.dataset.path;
	
     // FullCalendar 초기화
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: async function (fetchInfo, successCallback, failureCallback) {
            try {
                // API 호출
                const response = await fetch(`${contextPath}/noticeboard/calendar`);
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                const events = await response.json();
                successCallback(events); // 성공 시 데이터 전달
            } catch (error) {
                console.error("Failed to fetch calendar events:", error);
                failureCallback(error); // 실패 시 콜백 호출
            }
        }
    });

    // 모달이 열릴 때 FullCalendar 렌더링
    calendarIcon.addEventListener("click", function () {
        const calendarModal = new bootstrap.Modal(document.getElementById("calendarModal"));
        calendarModal.show();
        setTimeout(() => {
            calendar.render();
        }, 200); // 모달 애니메이션 후 렌더링
    });
});