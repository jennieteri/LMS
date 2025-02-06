<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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