<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<style>
/* ===== 캘린더 전체 스타일 ===== */
#myFullCalendar1 .fc {
    background-color: #ffffff; /* 캘린더 배경 흰색 */
    color: #333; /* 기본 텍스트 색상 */
    border: 1px solid #ddd; /* 캘린더 테두리 */
}

/* ===== 날짜 셀 스타일 ===== */
#myFullCalendar1 .fc-day {
    background-color: #f7f7f7; /* 연한 회색 */
    color: #2c2c2c; !important; /* 날짜 텍스트 진한 회색 */
    font-weight: 500; /* 날짜 텍스트 살짝 두껍게 */
}

/* ===== 요일 스타일 ===== */
#myFullCalendar1 .fc-col-header-cell {
    background-color: #e9ecef; /* 요일 셀 배경 연한 회색 */
    color: #1c1c1c; /* 요일 텍스트 더 진한 회색 */
    font-weight: bold; /* 요일 텍스트 굵게 */
}
#myFullCalendar1 .fc-col-header-cell-cushion,.fc-daygrid-day-number{
    color: black; /* 요일 텍스트 더 진한 회색 */
}

/* ===== 오늘 날짜 강조 ===== */
#myFullCalendar1 .fc-day-today {
    background-color: #e9ecef; 
    border: 2px solid #e9ecef; 
    border-radius: 5px;
    color: #2c2c2c; /* 오늘 날짜 텍스트 진한 회색 */
}

/* ===== 주말 날짜 스타일 ===== */
#myFullCalendar1 .fc-day-sat,
#myFullCalendar1 .fc-day-sun {
    background-color: #f0f8ff; /* 파스텔 블루 */
    color: #2c2c2c; /* 주말 텍스트 진한 회색 */
}

/* ===== 선택된 날짜 범위 강조 ===== */
#myFullCalendar1 .fc-highlight {
    background-color: #d6e9ff; /* 파스텔 블루 */
    opacity: 0.2; /* 반투명 */
}

/* ===== 이벤트(인덱스) 스타일 ===== */
#myFullCalendar1 .fc-event {
   background-color: rgba(128, 128, 128, 0.4); /* 중간 회색, 50% 투명 */
    color: #333; !important; /* 이벤트 텍스트 검정색 */
    border-radius: 5px;
    padding: 5px;
    font-size: 13px;
    border: 1px solid #81d4fa; /* 약간 더 진한 테두리 */
}

/* 이벤트 Hover 스타일 */
#myFullCalendar1 .fc-event:hover {
    background-color: #81d4fa; /* hover 시 더 진한 하늘색 */
    color: #000; /* hover 시 텍스트 검정색 */
}

/* ===== 캘린더 헤더 스타일 ===== */
#myFullCalendar1 .fc-toolbar {
    background-color: #f7f9fc; /* 연한 회색 */
    border-bottom: 2px solid #0056a6; /* 연세대 진한 파랑 */
    padding: 10px;
}

/* 캘린더 타이틀 스타일 */
#myFullCalendar1 .fc-toolbar-title {
    font-size: 20px;
    font-weight: bold;
    color: #2c3e50; /* 진한 회색 */
}

/* ===== 네비게이션 버튼 (prev, next, today) ===== */
#myFullCalendar1 .fc-button {
    background-color: #9cc6f5; /* 파스텔 하늘색 */
    color: white;
    border: none;
    border-radius: 4px;
    padding: 5px 10px;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

/* 네비게이션 버튼 Hover 및 클릭 상태 */
#myFullCalendar1 .fc-button:hover {
    background-color: #6ba7e5; /* hover 시 더 진한 파스텔 하늘색 */
    color: white;
}

#myFullCalendar1 .fc-button:active,
#myFullCalendar1 .fc-button.fc-button-active {
    background-color: #0056a6 !important; /* 클릭 시 진한 파란색 */
    color: white !important;
}

/* ===== 월, 주, 일 버튼 ===== */
#myFullCalendar1 .fc-button-group .fc-button {
    background-color: #9cc6f5; /* 파스텔 하늘색 */
    color: white;
    border: none;
    border-radius: 4px;
}

#myFullCalendar1 .fc-button-group .fc-button:hover {
    background-color: #6ba7e5; /* hover 시 더 진한 하늘색 */
    color: white;
}

/* ===== 현재 날짜 버튼 스타일 ===== */
#myFullCalendar1 .fc-today-button {
    background-color: #81c784; /* 연한 초록색 */
    color: white;
    border: none;
}

#myFullCalendar1 .fc-today-button:hover {
    background-color: #4caf50; /* hover 시 더 진한 초록색 */
    color: white;
}
/* 요일 텍스트 스타일 */
#myFullCalendar .fc-col-header-cell {
    color: #1c1c1c !important; /* 요일 텍스트 더 진한 회색 */
    font-weight: bold !important; /* 굵게 표시 */
}

/* 날짜 텍스트 스타일 */
#myFullCalendar .fc-daygrid-day-number {
    color: #2c2c2c;  /* 날짜 텍스트 진한 회색 */
    font-weight: normal; /* 기본 굵기 */
}

</style>


<div id="myFullCalendar1" style="width:100%"></div>

<script>
	const calendarElother = document.getElementById("myFullCalendar1");
	
    // FullCalendar 초기화
    const calendar = new FullCalendar.Calendar(calendarElother, {
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
                const response = await fetch(`${pageContext.request.contextPath}/mycalendar`);
                const events = await response.json();
                successCallback(events);
            } catch (error) {
                console.error("Failed to fetch events:", error);
                failureCallback(error);
            }
        }
		
    });
    calendar.render();
</script>