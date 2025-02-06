<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학적관리</li>
    <li class="breadcrumb-item active" aria-current="page">학적변동신청 관리</li>
  </ol>
</nav>
<security:authentication property="principal.realUser" var="user"/>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<form:form modelAttribute="condition" name="searchForm" method="get">
	<input type="hidden" id="page" name="page"/>
	<input type="hidden" id="show" name="show" value="${param.show }"/>
	<div class="collapse ${param.show eq 'Y' ? 'show' : '' }" id="search" style="position: relative;">
		<div id="detailConditionDiv" class="mb-3">
			<security:authorize access="!hasRole('STUDENT')">
				<div class="row g-3 mb-3">
					<div class="col">
						<label for="stuId" class="form-label">학번</label>
						<form:input path="stuId" cssClass="form-control"/>
					</div>
					<div class="col">
						<label for="studentVO.nm" class="form-label">이름</label>
						<form:input path="studentVO.nm" cssClass="form-control"/>
					</div>
					<div class="col">
						<label for="studentVO.deptCd" class="form-label">학과</label>
						<form:select path="studentVO.deptCd" cssClass="form-select">
							<option value="" label="선택안함"/>
							<form:options items="${departmentList }" itemLabel="deptNm" itemValue="deptNo" />
						</form:select>
					</div>
				</div>
			</security:authorize>
			<div class="row g-3 mb-3">
				<div class="col">
					<label for="semstrNo" class="form-label">신청학기</label>
					<form:select path="semstrNo" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${semesterList }" itemLabel="label" itemValue="semstrNo" />
					</form:select>
				</div>
				<div class="col">
					<label for="streCateCd" class="form-label">신청학적</label>
					<form:select path="streCateCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${streCateCodeList }" itemLabel="cocoStts" itemValue="cocoCd" />
					</form:select>
				</div>
				<div class="col">
					<label for="streStatusCd" class="form-label">결재상태</label>
					<form:select path="streStatusCd" cssClass="form-select">
						<option value="" label="선택안함"/>
						<form:options items="${streStatusCodeList }" itemLabel="cocoStts" itemValue="cocoCd"/>
					</form:select>
				</div>
			</div>
		</div>
	</div>
</form:form>
<button class="btn btn-primary float-end mb-3" data-bs-toggle="collapse" data-bs-target="#search" 
		aria-expanded="false" aria-controls="search"><i class="bi bi-search"></i></button>
<div class="float-end me-2 fade-transition ${param.show eq 'Y' ? 'visible' : 'hidden' }" id="searchDiv">
	<button class="btn btn-secondary me-1" id="searchBtn">검색</button>
	<button class="btn btn-warning" id="resetBtn"><i class="bi bi-arrow-clockwise"></i></button>
</div>
<table class="table table-bordered table-primary table-hover">
	<colgroup>
		<col width="50px">
		<col width="130px">
		<col width="120px">
		<col width="150px">
		<col width="130px">
		<col width="90px">
		<col width="180px">
	</colgroup>
	<thead>
		<tr class="text-center align-middle">
			<th>순번</th>
			<th>학번</th>
			<th>이름</th>
			<th>학과</th>
			<th>신청학기</th>
			<th>신청학적</th>
			<th>결재상태</th>
		</tr>
	</thead>
	<tbody class="table-light text-center">
		<c:if test="${stuRecList.size() eq 0 }">
			<tr><td colspan="7">조회된 내용이 없습니다</td></tr>
		</c:if>
		<c:forEach items="${stuRecList }" var="stuRec">
			<c:choose>
				<c:when test="${stuRec.streStatusCd eq 'PS04' }">
					<c:set value="text-success" var="statusColor"/>
				</c:when>
				<c:when test="${fn:contains(stuRec.streStatusCodeVO.cocoStts, '반려') }">
					<c:set value="text-danger" var="statusColor"/>
				</c:when>
				<c:otherwise>
					<c:set value="" var="statusColor"/>
				</c:otherwise>
			</c:choose>
			<tr onclick="fnDetail(this, '${user.id}');" data-stre-issu-no="${stuRec.streIssuNo }" style="cursor: pointer;">
				<td>${stuRec.rnum }</td>
				<td>${stuRec.stuId }</td>
				<td>${stuRec.studentVO.nm }</td>
				<td>${stuRec.studentVO.departmentVO.deptNm }</td>
				<td>${stuRec.semesterVO.label }</td>
				<td data-stre-cate-cd="${stuRec.streCateCd }">${stuRec.streCateCodeVO.cocoStts }</td>
				<td class="${statusColor }" data-stre-status-cd="${stuRec.streStatusCd }">${stuRec.streStatusCodeVO.cocoStts }</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				<div class="d-flex justify-content-between align-items-center" style="position: relative; z-index: 0">
					<div class="mx-auto">
						${pagingHTML}
					</div>
					<security:authorize access="hasRole('STUDENT')">
						<div>
							<button class="btn btn-primary" style="position: absolute; right: 0px; top: 0px;"
									onclick="fnInsertModalOpen('${user.id}')">등록</button>
						</div>
					</security:authorize>
				</div>
			</td>
		</tr>
	</tfoot>
</table>
<!-- 학적변동 신청 상세조회 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="detailModalLabel">학적변동 신청 상세조회</h1>
      </div>
      <div class="modal-body">
      	<table class="table table-bordered table-primary mb-0 align-middle">
      		<colgroup>
      			<col width="90px">
      			<col width="130px">
      			<col width="90px">
      			<col>
      		</colgroup>
      		<tbody><!-- 비동기로 innerHtml 랜더링 --></tbody>
      	</table>
      </div>
      <div class="modal-footer">
      	<security:authorize access="hasRole('STUDENT')">
      		<button class="btn btn-danger btn-prof btn-emp" style="display: none;" onclick="fnCancelStuRec(this);">취소</button>
      	</security:authorize>
      	<security:authorize access="hasRole('PROFESSOR')">
      		<button class="btn btn-primary btn-prof" style="display: none;" onclick="fnConsentStuRec('PS02');">승인</button>
      		<button class="btn btn-warning btn-prof" style="display: none;" data-bs-target="#returnStuRecModal" data-bs-toggle="modal">반려</button>
      	</security:authorize>
      	<security:authorize access="hasRole('EMPLOYEE')">
      		<button class="btn btn-primary btn-emp" style="display: none;" onclick="fnConsentStuRec('PS04');">승인</button>
      		<button class="btn btn-warning btn-emp" style="display: none;" data-bs-target="#returnStuRecModal" data-bs-toggle="modal">반려</button>
      	</security:authorize>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="returnStuRecModal" tabindex="-1" aria-labelledby="returnStuRecModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="returnStuRecModalLabel">반려사유</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="input-group">
		  <span class="input-group-text">반려사유</span>
		  <textarea class="form-control" rows="10" id="returnRsn" placeholder="반려사유를 입력하세요"></textarea>
		</div>
      </div>
      <div class="modal-footer">
        <security:authorize access="hasRole('PROFESSOR')">
        	<button type="button" class="btn btn-warning" onclick="fnReturnStuRec('PS03');">반려</button>
        </security:authorize>
        <security:authorize access="hasRole('EMPLOYEE')">
        	<button type="button" class="btn btn-warning" onclick="fnReturnStuRec('PS05');">반려</button>
        </security:authorize>
        <button type="button" class="btn btn-secondary" data-bs-target="#detailModal" data-bs-toggle="modal">취소</button>
      </div>
    </div>
  </div>
</div>
<!-- 학적변동 신청 모달 -->
<div class="modal fade" id="insertModal" tabindex="-1" aria-labelledby="insertModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="insertModalLabel">학적변동 신청</h1>
      </div>
      <div class="modal-body">
      	<form name="insertForm">
	      	<div class="row g-3 mb-3">
				<div class="col">
					<label for="semstrNo" class="form-label">신청학기</label>
					<select name="semstrNo" class="form-select">
						<option value="" label="학기를 선택하세요" selected disabled hidden/>
						<c:forEach items="${semesterList }" var="semester">
							<option value="${semester.semstrNo }" label="${semester.label }"/>
						</c:forEach>
					</select>
				</div>
				<div class="col">
					<label for="streCateCd" class="form-label">신청학적</label>
					<select name="streCateCd" class="form-select" onchange="fnStreCateChange(this);">
						<option value="" label="학적을 선택하세요" selected disabled hidden/>
						<c:set value="${user.streCateCd eq 'SC02' ? 'SR02' : 'SR01SR03'}" var="streCateCodeStr"/>
						<c:forEach items="${streCateCodeList }" var="streCateCode">
							<c:if test="${fn:contains(streCateCodeStr, streCateCode.cocoCd) }">
								<option value="${streCateCode.cocoCd }" label="${streCateCode.cocoStts }"/>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div>
					<label for="streResSel" class="form-label">신청사유</label>
					<select class="form-select mb-2" id="streResSel" onchange="fnStreResSelChange(this);">
						<option class="self" label="직접입력" value=""/>
						<option class="SR01" style="display: none;" label="일반 휴학" value="일반 휴학"/>
						<option class="SR01" style="display: none;" label="군입대 휴학" value="군입대 휴학"
								data-need-file="의무복무기간이 기재된 소속부대(장)의 직인이 날인되어있는 증명서, 군복무를 증명할 수 있는 서류(복무확인서 원본, 군인신분증 사본 등)"/>
						<option class="SR01" style="display: none;" label="출산 휴학" value="출산 휴학"
								data-need-file="출산예정일이 기재된 병원(장)의 직인이 날인되어있는 병원진단서(소견서)"/>
						<option class="SR01" style="display: none;" label="육아 휴학" value="육아 휴학"
								data-need-file="다음 서류 중 1개 이상 제출 - 자녀 확인이 가능한 주민등록등(초)본, 가족관계증명서, 출생신고서 등(산모수첩은 증빙서류로 인정되지 않음)"/>
						<option class="SR01" style="display: none;" label="질병 휴학" value="질병 휴학"
								data-need-file="병원(장)의 직인이 날인되어있는 4주 이상의 병원진단서(소견서)"/>
						<option class="SR02" style="display: none;" label="일반 복학" value="일반 복학"/>
						<option class="SR02" style="display: none;" label="군제대 복학" value="군제대 복학"
								data-need-file="전역증, 소집해제증명서, 병적증명서, 주민등록초본(병역사항기재)"/>
						<option class="SR03" style="display: none;" label="취업" value="취업"/>
						<option class="SR03" style="display: none;" label="창업" value="창업"/>
						<option class="SR03" style="display: none;" label="타대학 신입학" value="타대학 신입학"/>
						<option class="SR03" style="display: none;" label="타대학 편입" value="타대학 편입"/>
						<option class="SR03" style="display: none;" label="가계 곤란" value="가계 곤란"/>
					</select>
					<textarea name="streRes" class="form-control" rows="5"></textarea>
				</div>
				<div id="fileDiv" class="collapse">
					<label for="streRes" class="form-label">
						증빙서류<i id="fileEx" class="bi bi-question-circle-fill ms-2" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-title="title"></i>
					</label>
					<input type="file" class="form-control" name="uploadFiles"/>
				</div>
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="fnInsertFormSubmit(this);">저장</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath }/resources/js/student/studentRecordsList.js"></script>
