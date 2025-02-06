<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>


<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<div>
	<form id="attendanceForm">
		<div id="result"></div>
		<!-- 선택된 값이 표시될 곳 -->
		<div class="input-group mb-3 float-end search-area"
			data-pg-target="#searchform" data-pg-fn-name="fnPaging"
			style="width: 350px;">
			<form:select path="condition.searchType" cssClass="form-select">
				<form:option value="" label="전체" />
				<form:option value="id" label="학번" />
				<form:option value="name" label="작성자" />
				<form:option value="content" label="내용" />
				<form:option value="sttsType" label="서류접수상태" />
			</form:select>
			<form:input path="condition.searchWord" cssClass="form-control"
				style="width: 150px;" />
			<button id="search-btn" class="btn btn-primary">검색</button>
		</div>
	</form>
</div>
<style>
/* 작은 텍스트 설정 */
.small-text {
	font-size: 0.8rem; /* 더 작은 크기 */
	color: #6c757d; /* 약간 회색으로 색상도 조정 */
}

/* 큰 텍스트 설정 (사유는 크게 표시) */
.large-text {
	font-size: 1rem; /* 기본 텍스트보다 조금 큰 크기 */
	display: block; /* 사유가 여러 줄로 보이도록 */
	white-space: pre-wrap; /* 내용이 길어질 경우 줄 바꿈을 허용 */
	word-wrap: break-word; /* 긴 단어를 잘라서 줄 바꿈 */
	color: #000; /* 글씨 색상은 검정색 */
}

/* 테이블 스타일 */
.table {
	width: 100%;
	margin-bottom: 1rem;
	color: #212529;
}

.table td {
	padding: 0.3rem; /* 각 셀의 padding을 더 작게 설정 */
}
/* form 태그 내부의 모든 img 태그 크기 조정 */
#form-table img {
    max-width: 100%;    /* 부모 요소에 맞게 크기 제한 */
    max-height: 100%;   /* 부모 요소의 높이에 맞게 크기 제한 */
    width: auto;        /* 비율 유지 */
    height: auto;       /* 비율 유지 */
}

</style>

<%-- <form> --%>
<!-- 	<label><input type="radio" name="status" value="" checked>전체</label> -->
<!-- 	<label><input type="radio" name="status" value="CO01">대기</label> -->
<!-- 	<label><input type="radio" name="status" value="CO02">승인</label> -->
<!-- 	<label><input type="radio" name="status" value="CO03">반려</label> -->
<!-- 	<label><input type="radio" name="status" value="CO04">삭제</label> -->
<%-- </form> --%>


<input type="hidden" value="${pageContext.request.contextPath}"
	id="contextPath">
<input type="hidden" value="${lectNo}" id="lectNo">

<div>
	<table class="table table-primary align-middle table-bordered"
		id="absenceTable">
		<thead>
			<tr>
				<th class="text-center">접수번호</th>
				<th class="text-center">강의차수</th>
				<th class="text-center">강의일자</th>
				<th class="text-center">교시</th>
				<th class="text-center">학번</th>
				<th class="text-center">이름</th>
				<th class="text-center">출결상태</th>
				<!--         <th>공결사유</th> -->
				<th class="text-center">서류접수상태</th>
				<th class="text-center">처리방법</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${selectAbsenceCertificateReceiptList}"
				var="absence">
				<tr class="absenceRow table table-bordered align-middle table-bordered"
					data-absence-status="${absence.PRST}">
					<td class="text-center"  transition: background-color 0.5s ease, color 0.5s ease;>${absence.absenceCd }</td>
					<td class="text-center">${absence.lectOrder}회차</td>
					<td class="text-center">${absence.orderVO.sectDt}</td>
					<td class="text-center">${absence.TEIN}</td>
					<td class="text-center">${absence.stuId}</td>
					<td class="text-center">${absence.studentVO.nm}</td>
					<td class="text-center">${absence.ATST}</td>
					<%--         <td class="absenceResn">${absence.absenceResn}</td> --%>
					<td class="text-center">${absence.PRST}</td>
					<td class="d-flex justify-content-center"><security:authorize access="!hasRole('PROFESSOR')">
							<button type="button" class="editBtn"
								data-absenceCd="${absence.absenceCd}" data-bs-toggle="modal"
								data-bs-target="#staticBackdropEdit">수정</button>
							<button type="button" class="absenceDeleteBtn"
								data-absenceCd="${absence.absenceCd}"
								data-cocoCd="${absence.cocoCd}" data-lectNo="${absence.lectNo}">
								삭제</button>
						</security:authorize> <!-- 교수용 승인, 반려 버튼 --> <security:authorize
							access="hasRole('PROFESSOR')">
							<button class="viewBtn btn btn-primary" type="button"
								data-bs-toggle="modal" data-bs-target="#staticBackdrop"
								data-absenceCd="${absence.absenceCd}"
								data-lectOrder="${absence.lectOrder}"
								data-studentName="${absence.studentVO.nm}"
								data-studentId="${absence.stuId}"
								<%-- data-absenceReason="${absence.absenceResn}" --%>
				    			data-absenceStatus="${absence.PRST}"
								data-weekCd="${absence.weekCd}" data-lectNo="${absence.lectNo}">
								<c:set value="${absence.ATST}" var="studentATST" />
								조회
							</button>
						</security:authorize></td>
				</tr>
			</c:forEach>
		</tbody>



		<tfoot>
			<tr>
				<td colspan="10">
					<div class="d-flex justify-content-between align-items-center"
						style="position: relative; z-index: 0">
						<div class="mx-auto">${pagingHTML}</div>
						<%--<security:authorize access="hasRole('STUDENT')"> --%>
						<!--	<div> -->
						<%--		<a class="btn btn-primary" style="position: absolute; right: 0px; top: 0px;" href='<c:url value="/lecture/${lecture.lectNo }/inquiry/new"/>'>등록</a> --%>
						<!--	</div> -->
						<%--</security:authorize> --%>
					</div>
				</td>
			</tr>
		</tfoot>


	</table>
</div>


<!-- 수정 모달 (학생용) -->
<div class="modal fade" id="staticBackdropEdit"
	data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabelEdit" aria-hidden="true">
	<div class="modal-dialog" style="max-width: 80%; width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabelEdit">수정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<table class="table">
					<tbody>
						<tr>
							<td><strong>강의 차수:</strong></td>
							<td id="lectOrder" class="small-text"></td>
							<td><strong>학생명:</strong></td>
							<td id="studentName" class="small-text"></td>
						</tr>
						<tr>
							<td><strong>학번:</strong></td>
							<td id="studentId" class="small-text"></td>
							<td><strong>상태:</strong></td>
							<td id="absenceStatus" class="small-text"></td>
						</tr>
					</tbody>
				</table>
				<p>
					<strong>사유:</strong>
				</p>
				<div class="mb-3">
					<textarea class="form-control" id="editor" rows="3"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="saveEditButton">수정</button>
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>


<!-- 승인 처리 (교수용) -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog" style="max-width: 80%; width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">승인 처리</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<table class="border w-100">
					<thead>
						<tr>
							<td class="flex-fill p-2 text-center bg-primary text-white"><strong>강의 차수</strong></td>
							<td id="lectOrder2" class="flex-fill p-2 text-center border"></td>
							<td class="flex-fill p-2 text-center bg-primary text-white"><strong>학생명</strong></td>
							<td id="studentName2" class="flex-fill p-2 text-center border"></td>
						</tr>
						<tr>
							<td class="flex-fill p-2 text-center bg-primary text-white"><strong>학번</strong></td>
							<td id="studentId2" class="flex-fill p-2 text-center border"></td>
							<td class="flex-fill p-2 text-center bg-primary text-white"><strong>상태</strong></td>
							<td id="absenceStatus2" class="flex-fill p-2 text-center border"></td>
						</tr>
					</thead>
		    		<tbody class="flex-fill p-2">
						<tr> <!-- 공결 사유 -->
							<td colspan="4" style="height: 250px;">
								<form:form id="form-table" style="margin: 10px;"
									data-path="${pageContext.request.contextPath }" method="post"
									enctype="multipart/form-data">
									<p>
										<span id="absenceReason2" class="large-text"></span>
									</p>
								</form:form>
							</td>
						</tr>
						<tr class="flex-fill p-2 border">
							<td id="fileDetailsContainer" colspan="4" style="padding: 10px;"></td>
						</tr>
				        <tr class="flex-fill p-2 border">
				            <td class="preReturn p-2 text-center bg-primary text-white" colspan="4">반려 사유</td>
				        </tr>
				        <tr class="flex-fill p-2 border">
				            <td id="absenceReturnTable" colspan="4">
				                <textarea class="preReturn" id="absenceReturn" style="width: 100%; height: 150px;"></textarea>
				            </td>
				        </tr>
			    	</tbody>
					<tfoot>
					<tr>
						<td class="flex-fill p-2 text-end" colspan="4">
							<p id="modalDate"></p>
							<input type="hidden" value="" id="absenceATST">
							<button type="button" class="btn btn-outline-primary" id="dummyData">데이터 입력</button>
							<button type="button" class="btn btn-primary" id="rejectButton">반려</button>
							<button type="button" class="btn btn-primary" id="approveButton">승인</button>
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
						</td>
					</tr>
					</tfoot>
			</table>
		</div>
	</div>
</div>







<form:form id="searchform" method="get" modelAttribute="condition"
	style="display: none;">
	<form:input path="searchType" />
	<form:input path="searchWord" />
	<input type="hidden" name="page" />
</form:form>

<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/absenceCertificateReceipt/absencecertificatereceipt.js" type="module"></script>
<script src="${pageContext.request.contextPath }/resources/js/absenceCertificateReceipt/absenceStyle.js"></script>
