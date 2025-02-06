<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!-- postscript.jsp 에서 이미 적용중 -->
<!-- <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script> -->
<script src="https://d3js.org/d3.v7.min.js"></script>

<!-- 부트스트랩 5 -->
<!-- SweetAlert2 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.min.css"
	rel="stylesheet">
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.accordion-button {
    background-color: #87CEEB;  /* 하늘색 (Windows 색상) */
    color: white;  /* 텍스트 색상 */
    font-size: 1.1rem;  /* 폰트 크기 */
    font-weight: bold;  /* 폰트 굵기 */
    padding: 12px 20px;  /* 여백 설정 */
    border-radius: 10px 10px 0 0;  /* 상단의 둥근 모서리 */
    border: none;  /* 기본 테두리 제거 */
    transition: background-color 0.3s, transform 0.2s;  /* 배경색 및 효과 부드럽게 전환 */
}
.accordion-button:focus, .accordion-button:not(.collapsed) {
    background-color: #00BFFF;  /* 클릭 시 좀 더 짙은 하늘색 */
    color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);  /* 탭 클릭 시 그림자 추가 */
}
.accordion-collapse.show {
    max-height: 500px;  /* 충분히 큰 값으로 설정 */
    border: 1px solid #ddd;  /* 펼쳐졌을 때 테두리 색 */
}
.accordion-item {
    border-radius: 10px;  /* 전체 아이템에 둥근 모서리 */
    margin-bottom: 10px;  /* 각 아코디언 항목 사이에 간격 추가 */
    border: 1px solid #ddd;  /* 아코디언 항목의 테두리 */
    overflow: hidden;  /* 내용이 넘치지 않도록 설정 */
}
.accordion-header {
    background-color: #87CEEB;  /* 하늘색 */
    border-radius: 10px 10px 0 0;  /* 상단만 둥근 모서리 */
    border: 1px solid #ddd;  /* 테두리 설정 */
    margin-bottom: 0;  /* 헤더와 본문 사이 간격 제거 */
}
.accordion-button:active {
    transform: scale(1.05);  /* 클릭 시 약간의 확대 효과 */
}
.accordion-body {
    background-color: #ffffff; /* 깔끔한 흰색 배경 */
    border-radius: 5px;  /* 둥근 모서리 */
    padding: 15px;  /* 충분한 여백 */
    color: #333;  /* 텍스트 색상 */
    font-size: 1rem;  /* 폰트 크기 */
    line-height: 1.5;  /* 줄 간격 */
    box-sizing: border-box;  /* 테두리가 여백을 포함하게 */
    max-height: 200px;  /* 아코디언 본문 최대 높이 */
    overflow: hidden;  /* 내용이 넘칠 경우 숨김 처리 */
}
.custom-scroll {
    max-height: 200px;  /* 최대 높이 */
    overflow-y: auto;  /* 수직 스크롤 */
    padding-right: 10px;  /* 스크롤 바와의 간격 */
}
.custom-scroll::-webkit-scrollbar {
    width: 8px;  /* 세로 스크롤바의 너비 */
}
.custom-scroll::-webkit-scrollbar-track {
    background: #f1f1f1;  /* 스크롤바 트랙의 배경 색 */
    border-radius: 10px;
}
.custom-scroll::-webkit-scrollbar-thumb {
    background: #888;  /* 스크롤바의 색 */
    border-radius: 10px;
}
.custom-scroll::-webkit-scrollbar-thumb:hover {
    background: #555;  /* 스크롤바에 마우스 올릴 때 색 */
}
.accordion-body.custom-scroll {
    padding-right: 15px; /* 스크롤 바가 있는 경우 우측 여백 조정 */
}
.accordion-button.collapsed {
    border-radius: 10px;  /* 닫힌 상태에서도 둥근 모서리 유지 */
}
.accordion-button:not(.collapsed) {
    border-radius: 10px 10px 0 0;  /* 버튼 클릭 시 아코디언 본문과 일관된 둥근 모서리 */
}




.left {
    max-height: 600px !important;  /* 중요도 높이기 */
    overflow-y: auto !important;
    padding-right: 15px !important;
}
.right {
    max-height: 600px !important;  /* 중요도 높이기 */
    overflow-y: auto !important;
    padding-right: 15px !important;
}
/* 공통 버튼 스타일 */
.attendance-btn {
    padding: 6px 12px;   /* 패딩을 더 줄여서 버튼 크기 축소 */
    font-size: 12px;      /* 폰트 크기를 더 줄여서 텍스트 크기 조정 */
    font-weight: bold;
    color: white;
    border: none;
    border-radius: 25px;  /* 둥근 모서리 */
    cursor: pointer;
    transition: all 0.3s ease;
    margin: 5px;
    outline: none;
    background-color: #ccc; /* 기본 버튼은 은색 */
}

/* 호버 효과 */
.attendance-btn:hover {
    transform: scale(1.05); /* 호버시 버튼 크기 확대 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 호버시 그림자 효과 */
}

/* 선택된 버튼 스타일 */
.attendance-btn.selected {
    transform: scale(1.1); /* 선택된 버튼 크기 확대 */
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2); /* 선택된 버튼에 그림자 효과 추가 */
    font-weight: 900;
}

/* 출석 버튼 (파랑) - 선택된 상태일 때만 반영 */
.attendance-btn-blue.selected {
    background: linear-gradient(45deg, #2196F3, #1976D2);
}

.attendance-btn-blue:hover {
    background: linear-gradient(45deg, #1976D2, #2196F3); /* 호버시 그라디언트 색상 반전 */
}

/* 지각 버튼 (초록) - 선택된 상태일 때만 반영 */
.attendance-btn-green.selected {
    background: linear-gradient(45deg, #4CAF50, #388E3C);
}

.attendance-btn-green:hover {
    background: linear-gradient(45deg, #388E3C, #4CAF50); /* 호버시 그라디언트 색상 반전 */
}

/* 결석 버튼 (노랑) - 선택된 상태일 때만 반영 */
.attendance-btn-yellow.selected {
    background: linear-gradient(45deg, #FFEB3B, #FBC02D);
}

.attendance-btn-yellow:hover {
    background: linear-gradient(45deg, #FBC02D, #FFEB3B); /* 호버시 그라디언트 색상 반전 */
}

/* 조퇴 버튼 (빨강) - 선택된 상태일 때만 반영 */
.attendance-btn-red.selected {
    background: linear-gradient(45deg, #F44336, #D32F2F);
}

.attendance-btn-red:hover {
    background: linear-gradient(45deg, #D32F2F, #F44336); /* 호버시 그라디언트 색상 반전 */
}
/* 테이블 셀에 일정한 크기를 지정하는 스타일 */
.cell-style {
    height: 40px;    /* 최소 높이 */
}
.chart-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>


<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
<input type="hidden" id="weekCd"/>
<div class="container">
    <!-- 왼쪽: 강의 정보 (주차별 강의 정보) -->
    <div class="left" style="width: 28%; float: left; box-sizing: border-box;">
        <div class="accordion" id="accordionExample">
            <c:forEach items="${selectOrderLectureDataList}" var="week">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading-${week.weekCd}">
                        <div class="accordion-button" data-bs-toggle="collapse"
                             data-bs-target="#collapse-${week.weekCd}" aria-expanded="false"
                             aria-controls="collapse-${week.weekCd}">
                            ${week.commonCodeVO.cocoStts} 강의
                        </div>
                    </h2>
                    <div id="collapse-${week.weekCd}" class="accordion-collapse collapse"
                         aria-labelledby="heading-${week.weekCd}" data-bs-parent="#accordionExample">
                        <div class="accordion-body custom-scroll">
                            <c:forEach items="${week.orderLectureDataList}" var="order" varStatus="status">
							    <strong>${order.lectOrder}차시) ${order.sectDt.substring(0, 4)}-${order.sectDt.substring(4, 6)}-${order.sectDt.substring(6, 8)} ${order.timeCommonCodeVO.cocoStts}교시</strong>
							    <button class="fetch-attendance-btn btn btn-primary"
							            data-week-cd="${week.weekCd}"  
							            data-lect-order="${order.lectOrder}"
							            data-lect-no="${order.lectNo}"
							            data-sect-dt="${order.sectDt}"
							            data-lect-order="${order.lectOrder}"
							            data-coco-stts="${order.timeCommonCodeVO.cocoStts}">조회
							        <input type="hidden" name="lectNo" id="lectNo" value="${order.lectNo}" />
							    </button>
							
							    <!-- 마지막 항목이 아닌 경우에만 <hr>을 추가 -->
							    <c:if test="${!status.last}">
							        <hr>
							    </c:if>
							</c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    
    
    <!-- 오른쪽: 출석 리스트 (학생별 출석 정보) -->
    <div class="right" style="width: 70%; float: right; box-sizing: border-box;">
	    <div class="container2" style="display: flex; justify-content: space-between; gap: 20px;">
		    <!-- 왼쪽에 배치될 테이블 영역 -->
		    <div class="table-container" style="flex: 1;">
		        <div class="attendance-buttons" style="display: flex; justify-content: flex-end; gap: 10px; margin-bottom: 0.8rem;">
		            <table class="table table-bordered">
		                <thead class="table table-primary align-middle table-bordered">
		                    <tr>
		                        <th class="cell-style text-center align-middle">출석</th>
		                        <th class="cell-style text-center align-middle">지각</th>
		                        <th class="cell-style text-center align-middle">결석</th>
		                        <th class="cell-style text-center align-middle">조퇴</th>
		                    </tr>
		                </thead>
		                <tbody class="">
		                    <tr id="attendance-buttons text-center align-middle">
		                        <td id="ATN1" class="cell-style text-center align-middle"></td>
		                        <td id="ATN2" class="cell-style text-center align-middle"></td>
		                        <td id="ATN3" class="cell-style text-center align-middle"></td>
		                        <td id="ATN4" class="cell-style text-center align-middle"></td>
		                    </tr>
		                </tbody>
		            </table>
		        </div>
		        <div>
		            <table class="table table-primary align-middle table-bordered">
		                <thead>
		                    <tr>
		                        <th class="cell-style text-center align-middle">강의일자</th>
		                        <th class="cell-style text-center align-middle">강의차수</th>
		                        <th class="cell-style text-center align-middle">교시</th>
		                    </tr>
		                </thead>
		                <tbody class="table table-bordered align-middle table-bordered">
		                    <tr id="targetRow">
		                        <td id="sectDtCell" class="cell-style text-center align-middle"></td>
		                        <td id="lectOrderCell" class="cell-style text-center align-middle"></td>
		                        <td id="cocoSttsCell" class="cell-style text-center align-middle"></td>
		                    </tr>
		                </tbody>
		            </table>
		        </div>
		    </div>
		
		    <!-- 오른쪽에 배치될 차트 영역 -->
		    <div class="chart-container" style="flex-shrink: 0; max-width: 400px; margin-bottom: 1rem;">
		        <svg id="pieChart" style="width: 100%; max-width: 400px;"></svg>
		    </div>
		</div>
		    
			
			
			
        <table class="table table-bordered align-middle table-bordered" >
            <thead class="table table-primary align-middle table-bordered">
                <tr>
                    <th class="cell-style text-center align-middle">학번</th>
                    <th class="cell-style text-center align-middle">학생이름</th>
                    <th class="cell-style text-center align-middle">출결상태</th>
                </tr>
            </thead>
            <tbody id="attendanceTableBody" >
				<c:forEach items="${lecture.attendeeList }" var="attendee">
				    <tr class="attendanceRow"  
				    		data-stu-id="${attendee.stuId}" data-stu-nm="${attendee.studentVO.nm }" >
				        <td class="student-id text-center align-middle">${attendee.stuId }</td>
				        <td class="student-name text-center align-middle">${attendee.studentVO.nm }</td>
				        <td class="attendance-buttons text-center align-middle">
				            <button type="button" class="attendance-btn attendance-btn-blue" data-status="ATN1">출석</button>
				            <button type="button" class="attendance-btn attendance-btn-green" data-status="ATN2">지각</button>
				            <button type="button" class="attendance-btn attendance-btn-yellow" data-status="ATN3">결석</button>
				            <button type="button" class="attendance-btn attendance-btn-red" data-status="ATN4">조퇴</button>
				        </td>
				    </tr>
				</c:forEach>
			</tbody>
        </table>
    </div>
    <security:authorize access="hasRole('PROFESSOR')">
    <div class="right mb-3" style="width: 70%; float: right; box-sizing: border-box; display: none;" id="attendanceButtons">
		<button type="button" class="btn btn-success float-end" id="saveAttendanceButton">저장</button>
    	<button type="button" class="btn btn-primary float-end me-3" id="markAllPresentButton">일괄 출석</button>
    </div>
    </security:authorize>
</div>

	<!-- 버튼: 모달을 여는 버튼 -->
  <div class="modal fade" id="pieChartModal" tabindex="-1" aria-labelledby="pieChartModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document" style="max-width: 80%; width: 80%; max-height: 80%; height: 80%;">
        <div class="modal-content" style="height: 100%; border-radius: 0;">
            <div class="modal-header">
                <h5 class="modal-title" id="pieChartModalLabel">강의 출석 그래프</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="display: flex; justify-content: center; align-items: center;">
                <!-- D3.js 원형 차트를 그릴 SVG -->
                <svg id="pieChart" style="width: 100%; max-width: 400px; max-height: 400px;"></svg>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>




<script src="https://d3js.org/d3.v7.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/attendance/attendance.js"></script>
<%-- <script src="${pageContext.request.contextPath }/resources/js/attendance/attendancedatatable.js"></script> --%>






