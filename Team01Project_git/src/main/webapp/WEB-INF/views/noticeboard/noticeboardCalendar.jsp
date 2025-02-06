<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>

<style>
#semestertable{
	width: 700px;
}
</style>    

<table border="1" id="semestertable">
	<tr align="center">
		<td><button data-semester="first">1학기</button></td>
		<td></td>
		<td><button data-semester="second">2학기</button></td>
	</tr>
</table>

<div id="Wrapper">
        <div id='calendar'></div>
</div>

<script>
	document.addEventListener("DOMContentLoaded",()=>{
		const path = `${pageContext.request.contextPath}`;
		const calendarEl = document.querySelector('#calendar');
		const headerToolbar = {
	            left: 'prevYear,prev,next,nextYear today',
	            center: 'title',
	            right: 'dayGridMonth,dayGridWeek,timeGridDay'
	        }
		
		const calendarOption = {
	            height: '700px', // calendar 높이 설정
	            expandRows: true, // 화면에 맞게 높이 재설정
	            slotMinTime: '09:00', // Day 캘린더 시작 시간
	            slotMaxTime: '18:00', // Day 캘린더 종료 시간
	            // 맨 위 헤더 지정
	            headerToolbar: headerToolbar,
	            initialView: 'dayGridMonth',  // default: dayGridMonth 'dayGridWeek', 'timeGridDay', 'listWeek'
	            locale: 'kr',        // 언어 설정
	            selectable: true,    // 영역 선택
	            selectMirror: true,  // 오직 TimeGrid view에만 적용됨, default false
	            editable: true,      // event(일정) 
	            dayMaxEventRows: true,  // Row 높이보다 많으면 +숫자 more 링크 보임!
	          
	            nowIndicator: true,
	            events: `${path}/yguniv/calendar/all`
	            
	        }
		
	    const semester = document.getElementById("semestertable");
	    semester.addEventListener("click", (event) => {
	    	let when = event.target.dataset.semester;
	    	let where = `/univ/calendar/\${when}Semester`;
	    	
	    	calendarOption.events = where;
	    	const calendar = new FullCalendar.Calendar(calendarEl, calendarOption);
	    	calendar.render();
	    });
	    
	 	// 캘린더 생성
        const calendar = new FullCalendar.Calendar(calendarEl, calendarOption);
        // 캘린더 그리깅
        calendar.render();
	    
	
	})
	
</script>