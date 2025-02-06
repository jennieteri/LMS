<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.contextPath }" var="cp"/>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Bitter:ital,wght@0,100..900;1,100..900&family=Playfair+Display:ital,wght@0,400..900;1,400..900&display=swap"
	rel="stylesheet">
<style>
/* 이벤트 스타일 */
/* ===== 모달 캘린더 스타일 ===== */
#myFullCalendar .fc {
    background-color: #ffffff;
    color: #333;
    border: 1px solid #ddd;
}

#myFullCalendar .fc-day {
    background-color: #f7f7f7;
    color: #2c2c2c;
    font-weight: 500;
}

#myFullCalendar .fc-col-header-cell,.fc-col-header-cell-cushion{
    color: #1c1c1c;
    font-weight: bold;
}

#myFullCalendar .fc-day-today {
    background-color: #f8f9fa !important;
    border: 2px solid #007bff !important;
}

#myFullCalendar .fc-day-sat,
#myFullCalendar .fc-day-sun {
    background-color: #fdf2f2;
    color: #d9534f;
}

#myFullCalendar .fc-event {
    background-color: rgba(128, 128, 128, 0.4); /* 중간 회색, 50% 투명 */
    color: #333; !important; /* 이벤트 텍스트 검정색 */
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 13px;
}

#myFullCalendar .fc-event:hover {
    background-color: #81d4fa; /* hover 시 더 진한 하늘색 */
    color: #000; /* hover 시 텍스트 검정색 */
}

#myFullCalendar .fc-toolbar-title {
    font-size: 20px;
    font-weight: bold;
    color: #2c3e50;
}

#myFullCalendar .fc-button {
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 5px 10px;
    transition: background-color 0.3s ease;
}

#myFullCalendar .fc-button:hover {
    background-color: #0056b3;
    color: white;
}

#myFullCalendar .fc-today-button {
    background-color: #28a745 !important;
    color: white !important;
}
/* ===== 공통 인덱스(이벤트) 스타일 ===== */
.fc-event {
    background-color: #17a2b8; /* 이벤트 배경 색 */
    color: white; /* 텍스트 색 */
    border: none;
    border-radius: 4px;
    font-size: 13px;
    padding: 2px 5px;
}

/* 이벤트 Hover 효과 */
.fc-event:hover {
    background-color: #81d4fa; /* Hover 시 더 진한 색상 */
    cursor: pointer;
}

/* 일정 제목 스타일 */
.fc-event-title {
    font-weight: bold;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}

/*폰트스타일*/
.logo span {
	font-family: "Bitter", serif;
	font-optical-sizing: auto;
	font-weight: 500;
	font-style: normal;
	color: #000000;
}

/* 로고 이미지 비율 조정 */
.logo img {
	max-width: 60px; /* 로고 너비 조정 */
	max-height: 60px; /* 로고 높이 조정 */
	width: auto;
	height: auto;
	object-fit: contain; /* 이미지 비율을 유지하며 크기 맞추기 */
	margin-right: 15px; /* 텍스트와 로고 간 간격 */
}

/* 헤더 높이 설정 및 정렬 */
.header-nav {
	display: flex;
	align-items: center; /* 아이템 세로 정렬 */
	justify-content: flex-end; /* 아이템을 오른쪽 정렬 */
	min-height: 100px;
}

.d-flex {
	display: flex;
}

.align-items-center {
	align-items: center;
}

.justify-content-between {
	justify-content: space-between;
}

.flag-img {
	width: 20px; /* 원하는 너비 */
	height: auto; /* 비율을 유지한 채 높이를 자동으로 맞추기 */
}

.flag-item {
	margin-right: 10px; /* 우측에 10px 간격 추가 */
}

/* 부드러운 전환 효과 추가 */
.fade-transition {
    transition: opacity 0.5s ease, visibility 0.5s ease;
}

/* 숨김 상태 */
.hidden {
    opacity: 0;
    visibility: hidden;
}

/* 표시 상태 */
.visible {
    opacity: 1;
    visibility: visible;
}

.modal-header{
	background-color: #003399; 
	color: white;
}
#myCalendarIcon {
    font-size: 1.3rem; /* 크기 조정 */
    color: #003366; /* 연세대학교 색상 */
    cursor: pointer; /* 마우스 포인터 설정 */
    padding-right: 10px; /* 오른쪽 패딩 추가 */
    padding-left: 10px;
}

.flag-img {
    width: 20px; /* 모든 국기의 너비를 20px로 설정 */
    height: 20px; /* 모든 국기의 높이를 15px로 설정 */
    object-fit: contain; /* 이미지 비율 유지하며 크기 맞추기 */
    margin-right: 5px; /* 국기와 텍스트 사이의 간격 */
}

.flag-img1 {
    width: 30px; /* 모든 국기의 너비를 20px로 설정 */
    height: 13px; /* 모든 국기의 높이를 15px로 설정 */
    margin-right: 5px; /* 국기와 텍스트 사이의 간격 */
}
figure .image {
	width: 90%;
}
.notify::-webkit-scrollbar {
    width: 8px;
}

.notify::-webkit-scrollbar-track {
    background-color: #f0f0f0;
}

.notify::-webkit-scrollbar-thumb {
    background-color: #007bff;
    border-radius: 10px;
}

/* #evalBox의 스크롤바 커스텀 */
#evalBox::-webkit-scrollbar {
    width: 10px; 
}

#evalBox::-webkit-scrollbar-track {
    background-color: #f1f1f1; 
    border-radius: 10px;
}

#evalBox::-webkit-scrollbar-thumb {
    background-color: #007bff; 
    border-radius: 10px; 
    border: 2px solid #f1f1f1; 
}

#evalBox::-webkit-scrollbar-thumb:hover {
    background-color: #0056b3; 
}

</style>

</head>
<input type="hidden" id="headContextPath" name="headContextPath" value="${cp }">
<input type="hidden" id="contextPath" name="contextPath" value="${cp }">
		<security:authentication property="principal.realUser" var="user"/>
<input type="hidden" id="notifiCationUserId" name="notifiCationUserId" value="${user.id }">
<div class="d-flex align-items-center justify-content-between">
	<a href="${cp }"
		class="logo d-flex align-items-center"> <img
		src="${cp }/resources/NiceAdmin/assets/img/yglogo.png"
		alt="Logo"> <span class="d-none d-lg-block">YOUNGUN
			UNIVERSITY</span>
	</a> <i class="bi bi-list toggle-sidebar-btn"></i>
</div>
<!-- End Logo -->


<!--     <div class="search-bar"> -->
<!--       <form class="search-form d-flex align-items-center" method="POST" action="#"> -->
<!--         <input type="text" name="query" placeholder="Search" title="Enter search keyword"> -->
<!--         <button type="submit" title="Search"><i class="bi bi-search"></i></button> -->
<!--       </form> -->
<!--     </div>End Search Bar -->

<nav class="header-nav ms-auto">



	<ul class="d-flex align-items-center">

		<li class="nav-item d-block d-lg-none"><a
			class="nav-link nav-icon search-bar-toggle " href="#"> <i
				class="bi bi-search"></i>
		</a></li>
		<!-- End Search Icon-->


				
		<li class="nav-item flag-item"><a
			href="${cp }?lang=ko"><img
				src="${cp }/resources/NiceAdmin/assets/img/kr.png"
				alt="kr" class="flag-img1"></a></li>


		<li class="nav-item flag-item"><a
			href="${cp }?lang=en"><img
				src="${cp }/resources/NiceAdmin/assets/img/usa.png"
				alt="usa" class="flag-img"></a></li>

		<li class="nav-item flag-item">
			<a href="${cp }?lang=jp">
				<img src="${cp }/resources/NiceAdmin/assets/img/jp.png" alt="jp" class="flag-img">
			</a>
		</li>
		
		<li class="nav-item flag-item">
			<a href="${cp }?lang=cn">
				<img src="${cp }/resources/NiceAdmin/assets/img/cn.png" alt="cn" class="flag-img">
			</a>
		</li>
				
				
				
		<li class="nav-item flag-item">
            <i id="myCalendarIcon" class="bi bi-calendar-check" style="cursor: pointer;">
            </i>
        </li>



		<li class="nav-item dropdown">
    <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
        <i class="bi bi-bell"></i>
        <span class="badge bg-primary badge-number"></span>
    </a>
    <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications" style="max-height: 300px; overflow-y: auto; width: 300px;">
        <li class="dropdown-header">
            알람 내역 리스트
            <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2" onclick="updateAllReadNotfication()">전체 읽음</span></a>
        </li>
        <li>
            <hr class="dropdown-divider">
        </li>
        <!-- 알림 항목들이 여기에 동적으로 추가됩니다 -->
    </ul>
</li>

			<!-- End Notification Dropdown Items --></li>
		<!-- End Notification Nav -->


		<%--       <c:set value="${pageContext.request.parameterMap }" var="paramMap"/> --%>
		<%--       <c:set value="?" var="qs"/> --%>
		<%--       <c:forEach items="${paramMap }" var="entry"> --%>
		<%--          <c:if test="${entry.key != 'lang'}"> --%>
		<%--             <c:forEach items="${entry.value }" var="value"> --%>
		<%--                <c:set value="${qs }${entry.key }=${value }&" var="queryString"/> --%>
		<%--             </c:forEach> --%>
		<%--          </c:if> --%>
		<%--       </c:forEach> --%>




		<c:if test="${empty pageContext.request.queryString }">
			<c:set var="qs" value="" />
		</c:if>
		<c:if test="${not empty pageContext.request.queryString }">
			<c:set var="qs" value="${pageContext.request.queryString }&" />
		</c:if>

	<div class="session-timer d-flex align-items-center" style="margin-right: 20px;">
	    <div 
	      style="
	        border: 2px solid #003366; /* 테두리를 연세대 색상 */
	        border-radius: 30px; /* 둥글게 처리 */
	        padding: 1px 15px; /* 내부 여백 */
	        display: flex; /* 수평 정렬 */
	        align-items: center; /* 중앙 정렬 */
	        background-color: #ffffff; /* 배경색 흰색 */
	      ">
	      <i class="bi bi-clock" style="font-size: 1.2rem; color: #003366; margin-right: 8px;"></i> <!-- 아이콘 색상 연세대 색상 -->
	      <span id="sessionTime" style="font-size: 1.0rem; font-weight: bold; color: #003366;">
	          30:00
	      </span>
	    </div>
   </div>




		<li class="nav-item dropdown pe-3">
			<a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
			<c:if test="${empty user.proflPhoto }">
				<c:set value="${cp }/resources/NiceAdmin/assets/img/user.png" var="proflPhotoSrc"/>
			</c:if>
			<c:if test="${not empty user.proflPhoto }">
				<c:set value="data:image/*;base64,${user.proflPhoto }" var="proflPhotoSrc"/>
			</c:if>
			<img src="${proflPhotoSrc }" alt="Profile" class="rounded-circle">
			<span class="d-none d-md-block dropdown-toggle ps-2">
				${user.nm } 
<%-- 			<security:authorize access="hasRole('STUDENT')"> --%>
<%-- 				${user.nm } <spring:message code="header.student.name"/> --%>
<%-- 			</security:authorize> --%>
<%-- 			<security:authorize access="hasRole('PROFESSOR')"> --%>
<%-- 				${user.nm }  <spring:message code="header.professor.name"/> --%>
<%-- 			</security:authorize> --%>
<%-- 			<security:authorize access="hasRole('EMPLOYEE')"> --%>
<%-- 				${user.nm } <spring:message code="header.employee.name"/> --%>
<%-- 			</security:authorize> --%>
<%-- 			<security:authorize access="hasRole('ADMIN')"> --%>
<%-- 				${user.nm } <spring:message code="header.admin.name"/> --%>
<%-- 			</security:authorize> --%>
			</span>
		</a>
		<!-- End Profile Iamge Icon -->

			<ul
				class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
				<li class="dropdown-header">
					<h6>${user.nm }</h6> <span>${user.id }</span>
				</li>
				<li>
					<hr class="dropdown-divider">
				</li>

				<li><a class="dropdown-item d-flex align-items-center"
				
				href="<c:url value='/mypage/selectMypage/${user.id }'/>"> <i class="bi bi-person"></i> <span>마이페이지</span>
							
							
				</a></li>
				<li>
					<hr class="dropdown-divider">
				</li>

				<li>
					<form id="logoutForm"  class="dropdown-item d-flex align-items-center"  action="${cp }/logout" method="post">
					<i class="bi bi-box-arrow-right"></i>
						<button type="submit" style="display: none">로그아웃</button>
						<span style="cursor: pointer;" onclick="submitLogoutForm();">로그아웃</span>
					</form> 
				</li>

			</ul>
			<!-- End Profile Dropdown Items --></li>
		<!-- End Profile Nav -->

	</ul>
</nav>
<!-- End Icons Navigation -->

<!-- 마이 캘린더 모달 -->
<div id="myCalendarModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-center">My Calendar</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	  <div id="myFullCalendar"></div>
                	<div class="text-end">
            	<button id="addBtn" class="btn btn-primary" style="margin-top:15px; margin-bottom:5px;">일정추가</button>
            	</div>
            </div>
        </div>
    </div>
</div>

<!-- 일정 추가 모달 -->
<div id="eventModal" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일정 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="eventForm">
                    <div class="mb-3">
                        <label for="eventTitle" class="form-label">일정 제목</label>
                        <input type="text" name="myCalendarTitle" id="eventTitle" class="form-control" placeholder="제목을 입력하세요">
                    </div>
                    <div class="mb-3">
                        <label for="eventStart" class="form-label">시작 일시</label>
                        <input type="date" name="myCalendarSd" id="eventStart" class="form-control" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="eventEnd" class="form-label">종료 일시</label>
                        <input type="date" name="myCalendarEd" id="eventEnd" class="form-control" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="eventDescription" class="form-label">내용</label>
                        <textarea id="eventDescription" name="myCalendarContent" class="form-control" rows="3"></textarea>
                    </div>
                    <div class="mb-3">
                    	<select name="myCalendarCd">
                    		<option value="">일정유형</option>
                    		<option value="A001">개인일정</option>
                    		<option value="A002">학사일정</option>
                    		<option value="A003">취업일정</option>
                    		<option value="A004">기타</option>
                    	</select>
                    </div>
                    <div class="text-end">
                    <button type="button" id="saveEventBtn" class="btn btn-primary">저장</button>
                	</div>
                </form>
            </div>
        </div>
    </div>
</div>


<script>

    function submitLogoutForm() {
        // JavaScript로 폼을 제출
        document.getElementById('logoutForm').submit();
    }
</script>
<script src="${cp }/resources/js/utils/mycalendar.js"></script>
<script src="${cp }/resources/js/session/session.js"></script>
