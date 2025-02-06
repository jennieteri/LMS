<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">강의</li>
    <li class="breadcrumb-item" aria-current="page">강의 관리</li>
    <li class="breadcrumb-item active" aria-current="page">강의 상세조회</li>
  </ol>
</nav>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<input type="hidden" id="semstrNo" value="${lectVo.semstrNo }"/>
<c:if test="${empty lectVo }">
   등록된 강의계획서가 없습니다.
</c:if>
<c:if test="${not empty lectVo }">
	<div class="container mt-4">
		<div class="card mb-4">
			<div class="card-header">
				<h5 class="mb-0">기본 정보</h5>
			</div>
			<div class="card-body">
				<table class="table table-bordered table-primary mt-4 mb-0 align-middle">
					<colgroup>
						<col style="width: 15%; min-width: 110px;">
						<col style="width: 35%;">
						<col style="width: 15%; min-width: 110px;">
						<col style="width: 35%;">
					</colgroup>
					<tbody>
						<tr>
							<th class="text-center">과목명</th>
							<td class="bg-light">[${lectVo.subjectVO.subFicdCdCommonCodeVO.cocoStts }] ${lectVo.subjectVO.subNm }</td>
							<th class="text-center">개설학과</th>
							<td class="bg-light">${lectVo.subjectVO.departmentVO.deptNm }</td>
						</tr>
						<tr>
							<th class="text-center">강의명</th>
							<td class="bg-light">${lectVo.lectNm }</td>
							<th class="text-center">수강학년</th>
							<td class="bg-light">${lectVo.subjectVO.gradeCdCommonCodeVO.cocoStts }</td>
						</tr>
						<tr>
							<th class="text-center">학점</th>
							<td class="bg-light">${lectVo.lectScore }점</td>
							<th class="text-center">강의방식</th>
							<td class="bg-light">${lectVo.lectOnlineYn eq 'Y' ? '온라인' : '오프라인' }</td>
						</tr>
						<c:if test="${lectVo.lectOnlineYn ne 'Y' }">
							<tr>
								<th class="text-center">강의실</th>
								<td class="bg-light">${lectVo.scheduleVO[0].classRoomVO.croomPstn }</td>
								<th class="text-center">강의시간</th>
								<td class="bg-light">
									<c:forEach items="${lectVo.scheduleVO }" var="schedule" varStatus="stat">
										<span class="badge rounded-pill text-bg-secondary m-1 fs-6">
											${schedule.commonCodeVO.cocoStts }-${schedule.ettcVO.commonCodeVO.cocoStts }
										</span>
									</c:forEach>
								</td>
							</tr>
						</c:if>
						<tr>
							<th class="text-center">강의설명</th>
							<td colspan="3" class="bg-light">${lectVo.lectDescr }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="card mb-4">
			<div class="card-header bg-light">
				<h5 class="mb-0">담당 교수 정보</h5>
			</div>
			<div class="card-body">
				<table class="table table-bordered table-primary mt-4 mb-0">
					<colgroup>
						<col style="width: 15%; min-width: 110px;">
						<col style="width: 35%;">
						<col style="width: 15%; min-width: 110px;">
						<col style="width: 35%;">
					</colgroup>
					<tbody>
						<tr>
							<th class="text-center">성명</th>
							<td class="bg-light">${lectVo.professorVO.nm }</td>
							<th class="text-center">소속학과</th>
							<td class="bg-light">${lectVo.subjectVO.departmentVO.deptNm }</td>
						</tr>
						<tr>
							<th class="text-center">이메일</th>
							<td class="bg-light">${lectVo.professorVO.eml }</td>
							<th class="text-center">연락처</th>
							<td class="bg-light">${lectVo.professorVO.mbtlnum }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="card mb-4">
			<div class="card-header bg-light">
				<h2 class="h5 mb-0">평가 비율</h2>
			</div>
			<div class="card-body">
				<table class="table table-bordered table-primary mt-4 mb-0" style="table-layout: fixed;">
					<thead>
						<tr>
							<c:forEach items="${lectVo.lesVo }" var="les">
								<th class="text-center">${les.commonCodeVO.cocoStts }</th>
							</c:forEach>
							<th class="text-center">합계</th>
						</tr>
					</thead>
					<tbody class="table-light">
						<tr>
							<c:forEach items="${lectVo.lesVo }" var="les">
								<td class="text-center">${les.rate } %</td>
							</c:forEach>
							<td class="text-center">100 %</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	
		<div class="card mb-4">
			<div class="card-header bg-light">
				<h2 class="h5 mb-0">주차별 계획</h2>
			</div>
			<div class="card-body">
				<table class="table table-bordered table-primary mt-4 mb-0">
					<thead>
						<tr class="text-center align-middle">
							<th style="width: 80px;">주차</th>
							<th>내용</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lectVo.weekVO }" var="week">
							<tr>
								<td class="text-center fw-bold align-middle">${week.commonCodeVO.cocoStts }</td>
								<td class="table-light">${week.lectPlan }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</c:if>
<a class="btn btn-primary float-end me-3" style="position: sticky; bottom: 50px;"
	href="${pageContext.request.contextPath }/lecture?semstrNo=${lectVo.semstrNo }">목록</a>
<security:authentication property="principal.username" var="id"/>
<!-- 등록 대기일 경우 -->
<c:if test="${lectVo.lectStatusCd eq 'L01' }">
	<c:if test="${id eq lectVo.profeId }">
		<button class="btn btn-danger float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnDelLecture('${lectVo.lectNo }');">삭제</button>
		<a class="btn btn-info float-end me-2" style="position: sticky; bottom: 50px;"
			href="${pageContext.request.contextPath }/lecture/request/${lectVo.lectNo }/edit">수정</a>
	</c:if>
	<security:authorize access="hasRole('EMPLOYEE')">
		<button class="btn btn-success float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnConsentLecture('${lectVo.lectNo }');">승인</button>
		<button class="btn btn-warning float-end me-2" style="position: sticky; bottom: 50px;"
			data-bs-toggle="modal" data-bs-target="#requestReturnModal">반려</button>
		<!-- 등록 반려 모달 -->
		<div class="modal fade" id="requestReturnModal" tabindex="-1" aria-labelledby="requestReturnModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="requestReturnModalLabel">반려사유</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <div class="input-group">
				  <span class="input-group-text">반려사유</span>
				  <textarea class="form-control" rows="10" id="lectReturn" placeholder="반려사유를 입력하세요"></textarea>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-warning" onclick="fnReturnLecture('${lectVo.lectNo }');">반려</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		      </div>
		    </div>
		  </div>
		</div>
	</security:authorize>
</c:if>
<!-- 등록 일 경우 -->
<c:if test="${lectVo.lectStatusCd eq 'L02' }">
	<c:if test="${id eq lectVo.profeId }">
		<button class="btn btn-danger float-end me-2" style="position: sticky; bottom: 50px;"
			data-bs-toggle="modal" data-bs-target="#abolitionModal">폐강</button>
		<!-- 폐강 신청 모달 -->
		<div class="modal fade" id="abolitionModal" tabindex="-1" aria-labelledby="abolitionModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="abolitionModalLabel">폐강사유</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		      	<select class="form-select mb-3" id="abolitionSelect">
		      		<option value="self" label="직접 입력"/>
		      		<option value="수강생 미달" label="수강생 미달"/>
		      		<option value="수업 준비 미흡" label="수업 준비 미흡"/>
		      		<option value="개인 건강 악화" label="개인 건강 악화"/>
		      	</select>
		        <div class="input-group">
				  <span class="input-group-text">폐강사유</span>
				  <textarea class="form-control" rows="5" id="abolitionText" placeholder="폐강사유를 입력하세요"></textarea>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-danger" onclick="fnAbolitionLecture('${lectVo.lectNo }');">폐강</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		      </div>
		    </div>
		  </div>
		</div>
	</c:if>
</c:if>
<!-- 폐강 대기일 경우 -->
<c:if test="${lectVo.lectStatusCd eq 'L04' }">
	<c:if test="${id eq lectVo.profeId }">
		<button class="btn btn-warning float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnAbolitionCancel('${lectVo.lectNo }');">폐강신청취소</button>
	</c:if>
	<security:authorize access="hasRole('EMPLOYEE')">
		<button class="btn btn-success float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnConsentAbolition('${lectVo.lectNo }');">승인</button>
		<button class="btn btn-warning float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnReturnAbolition('${lectVo.lectNo }');">반려</button>
	</security:authorize>
</c:if>
<!-- 등록 반려일 경우 -->
<c:if test="${lectVo.lectStatusCd eq 'L07' }">
	<c:if test="${id eq lectVo.profeId }">
		<button class="btn btn-danger float-end me-2" style="position: sticky; bottom: 50px;"
			onclick="fnDelLecture('${lectVo.lectNo }');">삭제</button>
		<a class="btn btn-info float-end me-2" style="position: sticky; bottom: 50px;"
			href="${pageContext.request.contextPath }/lecture/request/${lectVo.lectNo }/edit">수정</a>
	</c:if>
</c:if>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureRequestDetail.js"></script>
	