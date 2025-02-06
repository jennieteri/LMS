<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.contextPath }" var="cp"/>
<input type="hidden" id="sideCp" value="${pageContext.request.contextPath }">
<ul class="sidebar-nav" id="sidebar-nav">
	<!-- 공통 -->
	<li class="nav-item">
		<a class="nav-link collapsed" href="${cp}/mypage/selectMypage/<security:authentication property='principal.username'/>">
			<i class="bi bi-person-badge"></i>
		    <span><spring:message code="sidebar.mypage"/></span>
		</a>
	</li>
	
	<li class="nav-item">
		<a class="nav-link collapsed"  data-bs-target="#systemBoard-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-card-checklist"></i><span><spring:message code="sidebar.notice"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="systemBoard-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="${cp }/systemBoard">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.notice.system"/></span>
				</a>
			</li>
		</ul>		
		<ul id="systemBoard-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="${cp }/answer">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.notice.qna"/></span>
				</a>
			</li>
		</ul>		
	</li>

	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#a3-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-pencil-square"></i><span><spring:message code="sidebar.academic"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="a3-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="${cp }/student/studentRecords">
<!-- 				학적변동신청관리 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.academic.management"/></span>
				</a>
			</li>
			<security:authorize access="hasRole('EMPLOYEE')">
				<li>
					<a href="${cp }/student">
<!-- 					학생관리 -->
						<i class="bi bi-circle"></i><span><spring:message code="sidebar.academic.studentManagement"/></span>
					</a>
				</li>
			</security:authorize>
		</ul>
	</li>

	<li class="nav-item">
		<a class="nav-link collapsed" href="${cp }/tuition">
			<i class="bi bi-check2-square"></i><span><spring:message code="sidebar.registration"/></span>
		</a>
	</li>

<security:authorize access="!hasRole('ADMIN')">
	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#certifi-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-printer"></i><span><spring:message code="sidebar.certificate"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="certifi-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="${cp }">
<!-- 				발급 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.certificate.issuance"/></span>
				</a>
			</li>
		</ul>
	</li>
</security:authorize>

<security:authorize access="hasRole('STUDENT')">
	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#lecCart-nav" data-bs-toggle="collapse"	href="javascript:void(0);">
			<i class="bi bi-journal-check"></i><span><spring:message code="sidebar.course.application"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="lecCart-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="javascript:void(0);" onclick="dateChecker('NB04')">
<!-- 				예비수강신청 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.course.preapplication"/></span>
				</a>
			</li>
		</ul>				
		<ul id="lecCart-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
<%-- 				<a href="${cp }/lectureCart/final"> --%>
				<a href="javascript:void(0);" onclick="dateChecker('NB05')">
<!-- 			수강신청 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.course.application"/></span>
				</a>
			</li>
		</ul>
	</li>
</security:authorize>

	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#study-nav" data-bs-toggle="collapse"	href="javascript:void(0);">
			<i class="bi bi-marker-tip"></i><span><spring:message code="sidebar.course"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="study-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<security:authorize access="hasRole('STUDENT')">
				<li>
 		   <a href="javascript:void(0);" onclick="dateChecker('NB08')">
<%-- 					<a href="${cp }/attendCoeva"> --%>
<!-- 					강의평가/성적조회 -->
						<i class="bi bi-circle"></i><span><spring:message code="sidebar.course.examinationGrade"/></span>
					</a>
				</li>
			</security:authorize>
			<security:authorize access="!hasRole('STUDENT')">
			<li>
				<a href="${cp }/lecture">
<!-- 				강의관리 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.course.administer"/></span>
				</a>
			</li>
			</security:authorize>
			<security:authorize access="hasRole('PROFESSOR')">
			<li>
				<a href="${cp }/attendCoeva/profe">
<!-- 				평가조회 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.cource.detail"/></span>
				</a>
			</li>
			</security:authorize>
		</ul>
	</li>


<security:authorize access="!hasRole('ADMIN')">
	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#award-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-cash-coin"></i><span><spring:message code="sidebar.scholarship"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="award-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
		<security:authorize access="hasRole('EMPLOYEE')">
			<li>
				<a href="${cp }/askAward">
<!-- 				장학금 신청 목록 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.scholarship.application"/></span>
				</a>
			</li>
			<li>
				<a href="${cp }/award/selectAdminAwardList">
<!-- 				장학금 유형 관리 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.scholarship.detail.administer"/></span>
				</a>
			</li>
		</security:authorize>
			<li>
				<a href="${cp }/award">
<!-- 				장학금 유형 조회 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.scholarship.detail"/></span>
				</a>
			</li>
			<security:authorize access="hasRole('STUDENT')">
			<%-- <li>
				<a href="${cp }/askAward/createAwardAsk/new">
					<i class="bi bi-circle"></i><span>장학금 신청</span>
				</a>
			</li> --%>
			<li>
				<a href="${cp }/askAward/selectStudent/<security:authentication property="principal.username"/>">
<!-- 					장학금 신청 내역 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.scholarship.application"/></span>
				</a>
			</li>
			</security:authorize>
			<security:authorize access="hasRole('PROFESSOR')">
			<li>
				<a href="${cp }/recommendAward/selectPro/<security:authentication property="principal.username"/>">
<!-- 				장학금 추천 내역 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.scholarship.recommend"/></span>
				</a>
			</li>
			</security:authorize>
		</ul>
	</li>
</security:authorize>
	
<security:authorize access="hasRole('STUDENT')">
	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#com-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-person-lines-fill"></i><span><spring:message code="sidebar.job"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="com-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
				<a href="${cp }/jobboard">
<!-- 				취업정보게시판 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.job.board"/></span>
				</a>
			</li>
			<li>
				<a href="${cp }/jobtest">
<!-- 				직업선호도평가 응시 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.job.test"/></span>
				</a>
			</li>
			<li>
				<a href="${cp }/jobtestresult/resultside/<security:authentication property="principal.username"/>">
<!-- 				직업선호도평가 결과조회 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.job.testresult"/></span>
				</a>
			</li>
			
			<li>
				
				<a href="${cp }/introduce/list/<security:authentication property="principal.username"/>">
<!-- 				자소서 클리닉 -->
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.job.intoduce"/></span>
				</a>
			</li>
		</ul>
	</li>
</security:authorize>

	
<security:authorize access="hasRole('STUDENT')">

	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#graduation-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-mortarboard-fill"></i><span><spring:message code="sidebar.graduation"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="graduation-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
<!-- 			졸업인증제 -->
				<a href="${cp }/graduation">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.graduation.certification"/></span>
				</a>
			</li>
			<li>
<!-- 			논문 -->
				<a href="${cp }/graduationpaper">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.graduation.paper"/></span>
				</a>
			</li>
		</ul>
	</li>
</security:authorize>
<security:authorize access="!hasRole('ADMIN')">
	<!--  학사일정 게시판 -->
	<li class="nav-item">
		<a class="nav-link collapsed" href="${cp }/noticeboard">
			<i class="bi bi-calendar2-month-fill"></i><span><spring:message code="sidebar.academic.schedule"/></span>
		</a>
	</li>
</security:authorize>

<security:authorize access="!hasAnyRole('STUDENT', 'ADMIN')">
    <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#p1-nav" data-bs-toggle="collapse" href="javascript:void(0);">
            <!-- 학생관리 -->
            <i class="bi bi-person-lock"></i>
            <span><spring:message code="sidebar.professor.student" /></span>
            <i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="p1-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
            
            <!-- 교직원 메뉴 -->
            <security:authorize access="hasRole('EMPLOYEE')">
                <li>
                    <!-- 장학금 관리 -->
                    <a href="${cp}">
                        <i class="bi bi-circle"></i>
                        <span><spring:message code="sidebar.professor.student.scholarship" /></span>
                    </a>
                </li>
                <li>
                    <!-- 졸업인증제 관리 -->
                    <a href="${cp}/graduation/listbyemp">
                        <i class="bi bi-circle"></i>
                        <span><spring:message code="sidebar.professor.student.graduation" /></span>
                    </a>
                </li>
                <li>
                    <!-- 학생증 관리 -->
                    <a href="${cp}/studentCard">
                        <i class="bi bi-circle"></i>
                        학생증관리
                    </a>
                </li>
            </security:authorize>
            
            <!-- 교수 메뉴 -->
            <security:authorize access="hasRole('PROFESSOR')">
                <li>
                    <!-- 논문 조회 -->
                    <a href="${cp}/graduationpaper/list/prof">
                        <i class="bi bi-circle"></i>
                        <span><spring:message code="sidebar.professor.student.paper" /></span>
                    </a>
                </li>
            </security:authorize>
        </ul>
    </li>
</security:authorize>


<security:authorize access="hasRole('EMPLOYEE')">
	<!-- 교직원 권한 -->
	<li class="nav-item">
<!-- 	시험관리 -->
		<a class="nav-link collapsed" href="${cp }/test">
			<i class="bi bi-folder2-open"></i><span><spring:message code="sidebar.employee.test"/></span>
		</a>
	</li>
	<li class="nav-item">
<!-- 	취업관리 -->
		<a class="nav-link collapsed" data-bs-target="#a5-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-person-lines-fill"></i><span><spring:message code="sidebar.employee.job"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="a5-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
<!-- 			직업선호도평가 -->
				<a href="${cp }/jobtest/listemp">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.employee.jobTest"/></span>
				</a>
			</li>
			<li>
<!-- 			직업선호도평가내역조회 -->
				<a href="${cp }/jobtestresult"> 
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.employee.jobTestResult"/></span>
				</a>
			</li>
			<li>
<!-- 			취업정보게시판 -->
				<a href="${cp }/jobboard">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.employee.jobBoard"/></span>
				</a>
			</li>
			<li>
<!-- 			자소서피드백 -->
				<a href="${cp }/introduce">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.employee.introduceFeedback"/></span>
				</a>
			</li>
		</ul>
	</li>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<!-- 	관리자 탭  -->
	<!-- 	회원관리 -->
	<li class="nav-item">
		<a class="nav-link collapsed" href="${cp }/person/list">
			<i class="bi bi-person-fill-gear"></i><span><spring:message code="sidebar.admin.person"/></span>
		</a>
	</li>

	<li class="nav-item">
		<a class="nav-link collapsed" data-bs-target="#s1-nav" data-bs-toggle="collapse" href="javascript:void(0);">
			<i class="bi bi-gear-wide"></i><span><spring:message code="sidebar.admin.system"/></span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
		<ul id="s1-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
			<li>
<!-- 			웹 트래픽 관리 -->
				<a href="${cp }/log/trafic">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.admin.system.webTraffic"/></span>
				</a>
			</li>
			<li>
<!-- 			통계 및 보고서 -->
				<a href="${cp }">
					<i class="bi bi-circle"></i><span><spring:message code="sidebar.admin.system.statistics"/></span>
				</a>
			</li>
		</ul>
	</li>
	</security:authorize>
</ul>


<script src="${pageContext.request.contextPath}/resources/js/sidebar/sidebarScript.js"></script>
