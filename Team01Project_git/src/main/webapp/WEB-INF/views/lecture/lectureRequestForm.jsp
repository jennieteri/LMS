<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">강의</li>
    <li class="breadcrumb-item" aria-current="page">강의 관리</li>
    <li class="breadcrumb-item active" aria-current="page">강의 등록</li>
  </ol>
</nav>
<input type="hidden" id="id" value="<security:authentication property="principal.username"/>"/>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
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
				<form name="lecture">
					<tbody>
						<tr>
							<th class="text-center">개설과목</th>
							<td class="bg-light">
								<select class="form-select" name="subNo">
									<option value="" label="과목을 선택하세요"/>
									<c:forEach items="${subjectList }" var="subject">
										<option value="${subject.subNo }" label="${subject.subNm }" ${subject.subNo eq lectVo.subNo ? 'selected' : ''}/>
									</c:forEach>
								</select>
							</td>
							<th class="text-center">강의명</th>
							<td class="bg-light">
								<input class="form-control" name="lectNm" placeholder="강의명을 입력하세요" value="${lectVo.lectNm }"/>
							</td>
						</tr>
						<tr>
							<th class="text-center">학점</th>
							<td class="bg-light">
								<input type="number" class="form-control" name="lectScore" min="1" max="3" value="${lectVo.lectScore }"/>
							</td>
							<th class="text-center">모집인원</th>
							<td class="bg-light">
								<input type="number" class="form-control" name="lectEnNope" min="1" value="${lectVo.lectEnNope }"/>
							</td>
						</tr>
						<tr>
							<th class="text-center">강의학기</th>
							<td class="bg-light">
								<select class="form-select" name="semstrNo">
									<option value="" label="학기를 선택하세요"/>
									<c:forEach items="${semesterList }" var="semester">
										<option value="${semester.semstrNo }" label="${fn:substring(semester.semstrNo,0,4) }-${fn:substring(semester.semstrNo,4,6) }" 
												${semester.semstrNo eq lectVo.semstrNo ? 'selected' : '' }/>
									</c:forEach>
								</select>
							</td>
							<th class="text-center">강의방식</th>
							<td class="bg-light" id="lectOnlineYn">
								<div class="form-check form-check-inline">
								  <input class="form-check-input" name="lectOnlineYn" type="radio" id="online" value="Y" ${lectVo.lectOnlineYn eq 'Y' or empty lectVo ? 'checked':'' }>
								  <label class="form-check-label" for="online">온라인</label>
								</div>
								<div class="form-check form-check-inline">
								  <input class="form-check-input" name="lectOnlineYn" type="radio" id="offline" value="N" ${lectVo.lectOnlineYn eq 'N' ? 'checked':'' }>
								  <label class="form-check-label" for="offline">오프라인</label>
								</div>
							</td>
						</tr>
						<tr id="schedule" ${lectVo.lectOnlineYn eq 'Y' or empty lectVo ? 'style="display: none;"':'' }>
							<th class="text-center">강의실</th>
							<td class="bg-light">
								<select class="form-select" name="croomCd">
									<option value="" label="주 강의실을 선택하세요"/>
									<c:forEach items="${croomList }" var="croom">
										<option value="${croom.croomCd }" label="${croom.croomPstn }" ${croom.croomCd eq lectVo.scheduleVO[0].croomCd ? 'selected' : '' }/>
									</c:forEach>
								</select>
							</td>
							<th class="text-center">강의시간</th>
							<td class="bg-light scheduleOut">
								<c:forEach items="${lectVo.scheduleVO }" var="schedule" varStatus="stat">
									<span class="${schedule.edcTimeCd }${schedule.dateCd } badge rounded-pill text-bg-secondary m-1 fs-6">
										${schedule.commonCodeVO.cocoStts }-${schedule.ettcVO.commonCodeVO.cocoStts }
									</span>
								</c:forEach>
								<button class="btn btn-secondary float-end" type="button" data-bs-toggle="modal" data-bs-target="#scheduleModal">
									<i class="bi bi-calendar-week"></i>
								</button>
							</td>
						</tr>
						<tr>
							<th class="text-center">강의설명</th>
							<td colspan="3" class="bg-light">
								<textarea class="form-control" name="lectDescr" placeholder="강의에 대한 설명을 입력하세요">${lectVo.lectDescr }</textarea>
							</td>
						</tr>
					</tbody>
				</form>
			</table>
		</div>
	</div>
	
	<div class="card mb-4">
		<div class="card-header bg-light">
			<h2 class="h5 mb-0 d-inline">평가 비율</h2>
			<div class="float-end">
				<c:set value="" var="lesStr"/>
				<c:forEach items="${lectVo.lesVo }" var="les">
					<c:set value="${lesStr }${les.evlStdrCd }${les.rate } " var="lesStr"/>
				</c:forEach>
				<c:forEach items="${leesList }" var="lees">
					<div class="form-check form-check-inline">
					  <input class="form-check-input" name="evlStdrCd" type="checkbox" value="${lees.cocoCd }"
							onchange="fnLeesChange(this);" ${fn:contains(lesStr,lees.cocoCd) or empty lectVo ? 'checked' : '' } ${lees.cocoCd eq 'ATT' ? 'disabled' : '' }>
					  <label class="form-check-label" for="${fn:toLowerCase(lees.cocoCd) }">${lees.cocoStts }</label>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="card-body">
			<table class="table table-bordered table-primary mt-4 mb-0" style="table-layout: fixed;">
				<thead>
					<tr>
						<c:forEach items="${leesList }" var="lees">
							<th class="text-center ${fn:toLowerCase(lees.cocoCd) }" ${fn:contains(lesStr,lees.cocoCd) or empty lectVo ? '' : 'style="display: none;"' }>
								${lees.cocoStts }
							</th>
						</c:forEach>
						<th class="text-center">합계</th>
					</tr>
				</thead>
				<tbody class="table-light">
					<tr>
						<c:forEach items="${leesList }" var="lees">
							<td class="text-center ${fn:toLowerCase(lees.cocoCd) }" ${fn:contains(lesStr,lees.cocoCd) or empty lectVo ? '' : 'style="display: none;"' }>
								<div class="input-group">
									<input type="number" class="form-control leesNumber" data-coco-cd="${lees.cocoCd }" 
											onchange="fnLeesValueChange(this);" min="0" max="100" value="${fn:substringBefore(fn:substringAfter(lesStr,lees.cocoCd),' ')}"/>
									<span class="input-group-text">%</span>
								</div>
							</td>
						</c:forEach>
						<td class="text-center align-middle" id="leesSum">${sum } %</td>
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
					<c:forEach items="${weekList }" var="week" varStatus="index">
						<tr>
							<td class="text-center fw-bold align-middle">${week.cocoStts }</td>
							<td class="table-light">
								<input class="form-control week-lecture-plan" data-week-cd="${week.cocoCd }" placeholder="${week.cocoStts } 계획을 입력하세요"
										value="${lectVo.weekVO[index.index].lectPlan }"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<a class="btn btn-secondary float-end my-4" style="position: sticky; bottom: 50px;" href="${pageContext.request.contextPath }/lecture${not empty lectVo ? '/request/' : ''}${lectVo.lectNo}">취소</a>
	<button class="btn btn-primary float-end me-2 my-4" style="position: sticky; bottom: 50px;" onclick="fnSubmit('${lectVo.lectNo}');">저장</button>
	<button class="btn btn-warning float-end me-2 my-4" style="position: sticky; bottom: 50px;" onclick="fnAutoInsert();">자동입력</button>
</div>

<!-- 시간표 모달 -->
<div class="modal fade" id="scheduleModal" tabindex="-1" aria-labelledby="scheduleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-body">
        <table class="table table-bordered table-primary text-center align-middle mb-0">
			<thead>
				<tr>
					<th style="width: 80px;">교시</th>
					<c:forEach items="${dotwList }" var="dotw">
						<c:if test="${dotw.cocoCd ne 'SUN' }">
							<th>${dotw.cocoStts }</th>
						</c:if>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:set value="" var="scheduleStr"/>
				<c:forEach items="${lectVo.scheduleVO }" var="schedule">
					<c:set value="${scheduleStr }${schedule.edcTimeCd }${schedule.dateCd } " var="scheduleStr"/>
					<input type="hidden" class="scId" value="${schedule.edcTimeCd }${schedule.dateCd }"
							data-etime="${schedule.edcTimeCd }" data-dotw="${schedule.dateCd }"/>
				</c:forEach>
				<c:forEach items="${etimeList }" var="etime">
					<tr class="table-light">
						<th style="font-size: 0.8rem;" class="${etime.edcTimeCd }">
							${etime.commonCodeVO.cocoStts }<br/>
							(${fn:substring(etime.beginTime,0,2) }~${fn:substring(etime.endTime,0,2) })
						</th>
						<c:forEach items="${dotwList }" var="dotw">
							<c:if test="${dotw.cocoCd ne 'SUN' }">
								<c:set value="${etime.edcTimeCd }${dotw.cocoCd }" var="scId"/>
								<td style="cursor: pointer;${fn:contains(scheduleStr,scId) ? 'background: darkBlue;' : '' }"
									onclick="fnScheduleClick(this);"
									data-etime="${etime.edcTimeCd }" data-dotw="${dotw.cocoCd }"
									data-output="${dotw.cocoStts }-${etime.commonCodeVO.cocoStts }"/>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureRequestForm.js"></script>
