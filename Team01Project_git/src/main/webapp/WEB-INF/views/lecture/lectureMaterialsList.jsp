<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<input id="cp" type="hidden" value="${pageContext.request.contextPath }"/>
<input id="lectNo" type="hidden" value="${lecture.lectNo }"/>
<input id="lectSession" type="hidden" value="${lecture.lectSession }"/>
<c:set value="${lecture.lectOnlineYn }" var="online"/>

<!-- CK 에디터 스크립트 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}
</script>

<table class="table table-primary align-middle table-bordered">
	<colgroup>
        <col style="width: 70px;">
        <col style="width: auto;">
        <col style="width: 110px;">
        <col style="width: 110px;">
        <col style="width: 120px;">
        <col style="width: 90px;">
        <col style="width: 90px;">
    </colgroup>
	<thead>
		<tr class="text-center align-middle">
			<th>순서</th>
			<th>강의명</th>
		<c:if test="${online eq 'Y' }">
			<th>학습시작일</th>
			<th>학습종료일</th>
			<th>출석인정시간<br>(분)</th>
			<security:authorize access="!hasRole('PROFESSOR')">
				<th>출석여부</th>
				<th>강의보기</th>
			</security:authorize>
		</c:if>
		<c:if test="${online eq 'N' }">
			<th>수업일</th>
			<th>교시</th>
			<th>강의실</th>
			<security:authorize access="!hasRole('PROFESSOR')">
				<th>출결상태</th>
				<th>공결신청</th>
			</security:authorize>
		</c:if>
		<security:authorize access="hasRole('PROFESSOR')">
			<th colspan="2"></th>
		</security:authorize>
		</tr>
	</thead>
	<tbody class="table-group-divider">
		<c:if test="${empty lectureMaterialsList }">
			<tr class="table-light text-center">
				<td colspan="7">등록된 강의가 없습니다</td>
			</tr>
		</c:if>
		<c:forEach items="${lectureMaterialsList }" var="week">
			<tr class="table-light weekCd delWeek" data-week-cd="${week.weekCd }">
				<td class="fw-bold text-center cocoStts">${week.commonCodeVO.cocoStts }</td>
				<security:authorize access="hasRole('PROFESSOR')">
					<td colspan="4" class="leweNm fw-bold">${week.leweNm }</td>
					<td class="text-center" colspan="2">
						<button class="btn btn-outline-info week-update-btn">수정</button>
						<button class="btn btn-outline-danger week-delete-btn">삭제</button>
						<button class="btn btn-outline-primary week-update-save-btn" style="display: none;">저장</button>
						<button class="btn btn-outline-secondary week-update-cancel-btn" style="display: none;">취소</button>
					</td>
				</security:authorize>
				<security:authorize access="!hasRole('PROFESSOR')">
					<td colspan="6" class="leweNm fw-bold">${week.leweNm }</td>
				</security:authorize>
			</tr>
			<c:forEach items="${week.orderLectureDataList }" var="data">
				<tr class="table-secondary delWeek orderLecture" data-week-cd="${week.weekCd }" data-lect-order="${data.lectOrder }" data-atnd-stts="${data.attendanceVO.commonCodeVO.cocoStts }">
					<td class="text-center lectOrder" data-lect-order="${data.lectOrder }">${data.lectOrder }차시</td>
					<td>${data.sectNm }</td>
					<c:if test="${online eq 'Y' }">
						<td class="text-center sectDt">${fn:substring(data.sectDt,0,4) }-${fn:substring(data.sectDt,4,6) }-${fn:substring(data.sectDt,6,8) }</td>
						<td class="text-center sectEt">${fn:substring(data.sectEt,0,4) }-${fn:substring(data.sectEt,4,6) }-${fn:substring(data.sectEt,6,8) }</td>
						<security:authorize access="!hasRole('PROFESSOR')">
							<td class="text-center">${data.sectIdnty} / ${data.attendanceVO.atndIdnty eq null ? 0 : data.attendanceVO.atndIdnty}</td>
							<c:if test="${data.attendanceVO.atndCd eq 'ATN1'}">
								<td class="text-center fw-bold text-success">출석인정</td>
							</c:if>
							<c:if test="${data.attendanceVO.atndCd ne 'ATN1'}">
								<td class="text-center fw-bold text-danger">학습부족</td>
							</c:if>
							<td class="text-center">
								<button class="btn btn-primary" onclick="fnLectOrderDetail(this);">입장</button>
							</td>
						</security:authorize>
						<security:authorize access="hasRole('PROFESSOR')">
							<td class="text-center">${data.sectIdnty}</td>
						</security:authorize>
					</c:if>
					<c:if test="${online eq 'N' }">
						<td class="text-center">${fn:substring(data.sectDt,0,4) }-${fn:substring(data.sectDt,4,6) }-${fn:substring(data.sectDt,6,8) }</td>
						<td class="text-center">${data.timeCommonCodeVO.cocoStts }</td>
						<td class="text-center">${data.classRoomVO.croomPstn }</td>
						<security:authorize access="!hasRole('PROFESSOR')">
							<td class="text-center">
								${data.attendanceVO.commonCodeVO.cocoStts }
							</td>
							<td class="text-center">
								<c:if test="${data.attendanceVO.atndCd ne 'ATN1' }">
									<security:authorize access="!hasRole('PROFESSOR')">
										
										<!-- 조건에 통과한 값 저장 -->
										<c:set var="isDuplicate" value="false" />
										<c:set var="duplicateAbsenceCd" value="" />
										<c:set var="duplicateCocoCd" value="" />
										<c:set var="duplicateLectNo" value="" />
										<c:set var="FileAbsenceCd" value="" />
										
										<!-- 조건에 맞는 서류 조회 -->
										<c:forEach items="${absenceList}" var="absence">
										    <c:if test="${absence.lectNo == data.lectNo
										        && absence.lectOrder == data.lectOrder
										        && absence.stuId == stuId
										        && absence.cocoCd != 'CO04'}">
										    	<!-- 조건에 통과한 값 저장 -->
										    	<c:set var="isDuplicate" value="true" />
										        <c:set var="duplicateAbsenceCd" value="${absence.absenceCd}" />
										        <c:set var="duplicateCocoCd" value="${absence.cocoCd}" />
										        <c:set var="duplicateLectNo" value="${absence.lectNo}" />
										    </c:if>
										</c:forEach>
										<!-- 공결 신청 등록 ( 이미 등록한 서류가 있다면 X ) -->
										<c:if test="${isDuplicate == false }">
											<button class="btn btn-primary atnd-insert-btn" 
											data-bs-toggle="modal"
											data-bs-target="#staticBackdrop"
											>등록</button>
										</c:if>
										<!-- 공결 서류 조회 ( 이미 등록한 서류가 있다면 O ) -->
										<c:if test="${isDuplicate == true }">
										    <button type="button" 
										        class="editBtn btn 
										            ${duplicateCocoCd == 'CO03' ? 'btn-warning' : 'btn-success'} atnd-insert-btn" 
										        data-lect-order="${data.lectOrder }" 
										        data-atnd-stts="${data.attendanceVO.commonCodeVO.cocoStts }"
										        data-absenceCd="${duplicateAbsenceCd}"
										        data-bs-toggle="modal"
										        data-bs-target="#staticBackdropEdit">
										        
										        <c:if test="${duplicateCocoCd == 'CO03' }">
										            반려
										        </c:if>
										        <c:if test="${duplicateCocoCd != 'CO03' }">
										            조회
										        </c:if>
										    </button>
										</c:if>
          							</security:authorize>
								</c:if>
							</td>
						</security:authorize>
					</c:if>
					<security:authorize access="hasRole('PROFESSOR')">
						<td class="text-center" colspan="2">
							<button class="btn btn-info data-update-btn"> 수정</button>
							<button class="btn btn-danger data-delete-btn">삭제</button>
						</td>
					</security:authorize>
				</tr>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>
<security:authorize access="hasRole('PROFESSOR')">
	<button class="btn btn-primary float-end me-2" style="position: sticky; bottom: 50px;"
		data-bs-toggle="modal" data-bs-target="#insertWeekModal">등록</button>
</security:authorize>

<security:authorize access="hasRole('PROFESSOR')">
	<!-- 추가 모달 -->
	<div class="modal fade" id="insertWeekModal" tabindex="-1" aria-labelledby="insertWeekModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="insertWeekModalLabel">등록</h1>
	        <div class="ms-auto">        
		        <input type="radio" class="btn-check" name="insertOption" id="week" autocomplete="off" checked>
				<label class="btn btn-outline-secondary mx-1" for="week">주차</label>
				
				<input type="radio" class="btn-check" name="insertOption" id="order" autocomplete="off">
				<label class="btn btn-outline-secondary" for="order">차시</label>
			</div>
	      </div>
	      <div id="insertWeekDiv">
		      <div class="modal-body">
			  	<div class="input-group">
			  		<select class="form-select" id="weekCd"></select>
					<input type="text" id="leweNm" class="form-control" style="width: 250px;" placeholder="주차제목을 입력하세요"/>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-primary week-insert-btn">저장</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		      </div>
	      </div>
	      <div id="insertOrderDiv" style="display: none;">
	      	<div class="modal-body">
		      	<form name="insertDataForm">
		      		<div class="row">
		      			<div class="col">
					      	<div class="mb-3">
						    	<label for="dataWeekCd" class="form-label">차시 등록 주차</label>
						  		<select class="form-select dataWeekCd" name="weekCd"></select>
						  	</div>
						</div>
		      			<div class="col">
						  	<div class="mb-3">
						    	<label for="lectOrder" class="form-label">강의차시</label>
						    	<div class="input-group">
						  			<input type="number" class="form-control" name="lectOrder" min=1 max="${lecture.lectSession }" placeholder="강의차시입력"/>
						  			<label class="input-group-text" for="lectOrder">차시</label>
						  		</div>
						  	</div>
						</div>
				  	</div>
				  	<div>
				    	<label for="lectNm" class="form-label">차시제목</label>
				  		<input type="text" class="form-control" name="sectNm" placeholder="차시제목을 입력하세요"/>
				  	</div>
				  	<c:if test="${online eq 'Y' }">
					  	<div class="row align-middle">
			      			<div class="col">
							  	<div class="my-3">
							    	<label for="sectDt" class="form-label">학습시작일</label>
							  		<input type="date" class="form-control" name="sectDt"/>
							  	</div>
							</div>
							<div class="col-auto">
							    <div style="display: flex; align-items: center; height: 130%;">
							      <h4>~</h4>
							    </div>
							</div>
			      			<div class="col">
							  	<div class="my-3">
							    	<label for="sectEt" class="form-label">학습종료일</label>
							  		<input type="date" class="form-control" name="sectEt"/>
							  	</div>
							</div>
						</div>
					  	<div class="mb-2">
					    	<label for="sectIdnty" class="form-label">이수인정시간 : <span id="output">0</span>분</label>
					  		<input type="range" class="form-range" name="sectIdnty"
					  			min="0" max="120" step="10" value="0" oninput="document.getElementById('output').textContent = this.value"/>
					  	</div>
					  	<div>
						  <label class="form-label" for="uploadFiles">차시 강의 영상</label>
						  <input type="file" class="form-control" name="uploadFiles" accept="video/*">
						</div>
					</c:if>
				  	<c:if test="${online eq 'N' }">
				  		<div class="row align-middle">
						  	<div class="col">
						  		<div class="my-3">
							    	<label for="sectDt" class="form-label">수업일</label>
							  		<input type="date" class="form-control" name="sectDt"/>
						  		</div>
						  	</div>
						  	<div class="col">
						  		<div class="my-3">
						  			<label for="sectEtime" class="form-label">수업교시</label>
							  		<select class="form-select dataSectEtime" name="sectEtime"></select>
						  		</div>
						  	</div>
					  	</div>
					  	<div class="col">
				  			<label for="croomCd" class="form-label">강의실</label>
					  		<select class="form-select dataCroomCd" name="croomCd"></select>
				  		</div>
					</c:if>
			  	</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-warning" id="autoInsert">자동완성</button>
		        <button type="button" class="btn btn-primary data-insert-btn">저장</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		      </div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 차시 수정 모달 -->
	<div class="modal fade" id="updateDataModal" tabindex="-1" aria-labelledby="updateDataModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="updateDataModalLabel">차시 수정</h1>
	      </div>
	      <div class="modal-body">
	      	<form name="updateDataForm">
	      		<div class="row">
	      			<div class="col">
				      	<div class="mb-3">
					    	<label for="dataWeekCd" class="form-label">차시 등록 주차</label>
					  		<select class="form-select dataWeekCd" name="weekCd"></select>
					  	</div>
					</div>
	      			<div class="col">
					  	<div class="mb-3">
					    	<label for="lectOrder" class="form-label">강의차시</label>
					    	<div class="input-group">
					  			<input type="number" class="form-control" name="lectOrder" min=1 max="${lecture.lectSession }" placeholder="강의차시입력"/>
					  			<label class="input-group-text" for="lectOrder">차시</label>
					  		</div>
					  	</div>
					</div>
			  	</div>
			  	<div>
			    	<label for="sectNm" class="form-label">차시제목</label>
			  		<input type="text" class="form-control" name="sectNm" placeholder="차시제목을 입력하세요"/>
			  	</div>
			  	<c:if test="${online eq 'Y' }">
				  	<div class="row align-middle">
		      			<div class="col">
						  	<div class="my-3">
						    	<label for="sectDt" class="form-label">학습시작일</label>
						  		<input type="date" class="form-control" name="sectDt"/>
						  	</div>
						</div>
						<div class="col-auto">
						    <div style="display: flex; align-items: center; height: 130%;">
						      <h4>~</h4>
						    </div>
						</div>
		      			<div class="col">
						  	<div class="my-3">
						    	<label for="sectEt" class="form-label">학습종료일</label>
						  		<input type="date" class="form-control" name="sectEt"/>
						  	</div>
						</div>
					</div>
				  	<div class="mb-2">
				    	<label for="sectIdnty" class="form-label">이수인정시간 : <span id="updateOutput">0</span>분</label>
				  		<input type="range" class="form-range" name="sectIdnty"
				  			min="0" max="120" step="10" value="0" oninput="document.getElementById('updateOutput').textContent = this.value"/>
				  	</div>
				  	<div>
					  <label class="form-label" for="uploadFiles">차시 강의 영상</label>
					  <div class="input-group mb-3">
						  <span class="input-group-text">등록 영상</span>
						  <input type="text" class="form-control" name="uploadedFiles" disabled>
					  </div>
					  <input type="file" class="form-control" name="uploadFiles" accept="video/*">
					</div>
			  	</c:if>
			  	<c:if test="${online eq 'N' }">
			  		<div class="row align-middle">
					  	<div class="col">
					  		<div class="my-3">
						    	<label for="sectDt" class="form-label">수업일</label>
						  		<input type="date" class="form-control" name="sectDt"/>
					  		</div>
					  	</div>
					  	<div class="col">
					  		<div class="my-3">
					  			<label for="sectEtime" class="form-label">수업교시</label>
						  		<select class="form-select dataSectEtime" name="sectEtime"></select>
					  		</div>
					  	</div>
				  	</div>
				  	<div class="col">
			  			<label for="croomCd" class="form-label">강의실</label>
				  		<select class="form-select dataCroomCd" name="croomCd"></select>
			  		</div>
			  	</c:if>
		  	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary data-update-submit-btn">저장</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
</security:authorize>






<security:authentication property="principal.realUser" var="personVO"/>

<!-- 공결 신청 모달 -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog" style="max-width: 80%; width: 80%;">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">공결 신청서 작성</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="form-table" data-path="${pageContext.request.contextPath}">
	        <table class="table table-bordered mb-0">
	          <thead>
	            <tr>
	              <td class="flex-fill p-2 text-center bg-primary text-white">강의 차수</td>
	              <td id="lectOrder" class="small-text"></td>
	              <td class="flex-fill p-2 text-center bg-primary text-white">학생명</td>
	              <td id="studentName" class="small-text">${personVO.nm }</td>
	            </tr>
	            <tr>
	              <td class="flex-fill p-2 text-center bg-primary text-white">학번</td>	
	              <td id="studentId" class="small-text">${personVO.id }</td>
	              <td class="flex-fill p-2 text-center bg-primary text-white"	>상태</td>
	              <td id="absenceStatus" class="small-text"></td>
	            </tr>
	          </thead>
	          <tbody>
	            <tr>
	              <td colspan="4">
	                  <textarea name="absenceResn" id="editor" placeholder="내용을 입력해주세요."></textarea>
	              	  <input type="file" name="uploadFiles" multiple class="form-control">
	                  <input type="hidden" id="weekCd"/>
	              </td>
	            </tr>
	          </tbody>
	        </table>
        </form>
      </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-outline-primary" id="dummyData">데이터 입력</button>
          <button type="button" class="btn btn-primary" id="approveButton">등록</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>



<!-- 수정 모달 (학생용) -->
<div class="modal fade" id="staticBackdropEdit" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabelEdit" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 80%; width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabelEdit">수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	        </div>
	            <div class="modal-body">
	                <table class="border w-100">
	                    <thead>
			              <tr>
			                <td class="flex-fill p-2 text-center bg-primary text-white"><strong>강의 차수</strong></td>
			                <td id="lectOrder2" class="flex-fill p-2 text-center"></td>
			                <td class="flex-fill p-2 text-center bg-primary text-white"><strong>학생명</strong></td>
			                <td id="studentName2" class="flex-fill p-2 text-center">${personVO.nm }</td>
			              </tr>
			              <tr>
			                <td class="flex-fill p-2 text-center bg-primary text-white"><strong>학번</strong></td>
			                <td id="studentId2" class="flex-fill p-2 text-center">${personVO.id }</td>
			                <td class="flex-fill p-2 text-center bg-primary text-white"><strong>접수상태</strong></td>
			                <td id="absenceStatus2" class="flex-fill p-2 text-center"></td>
			                <input id="absenceCd2" type="hidden" value=""/>
			              </tr>
			            </thead>
			            <tbody>
					        <tr>
					          <td colspan="4">
						          <form id="edit-table" data-path="${pageContext.request.contextPath}">
						              <textarea class="form-control" id="updateEditor" rows="3"></textarea>
						          </form>
								  <form id="fileForm" method="put" enctype="multipart/form-data">
					          	  	<input type="file" name="uploadFiles" multiple class="form-control" style="float: left">
					          	  </form>
					          	  <div id="fileDetailsContainer" class="flex-fill p-4">
								      <!-- 조회 결과가 여기에 표시됩니다 -->
								  </div>
					          </td>
					        </tr>
				        </tbody>
	                </table>
				    <table id="table-header">
				        <thaed>
				        	<tr>
					        	<td class="flex-fill p-2 text-center bg-primary text-white"><strong>공결 반려사유</strong></td>
					        </tr>
					        <tr>
						        <td id="modal-return" class="flex-fill p-4"></td>
					        </tr>
					    </thaed>
					</table>
	            </div>
            <div class="modal-footer">
                <div>
	                <button type="button" class="btn btn-primary" id="saveEditButton">
	                	<c:if test="${duplicateCocoCd == 'CO03' }">
						    저장
						</c:if>
						<c:if test="${duplicateCocoCd != 'CO03' }">
		                	수정
						</c:if>
	                </button>
					<button type="button" class="absenceDeleteBtn btn btn-danger atnd-insert-btn" >삭제</button>
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            	</div>
            </div>
        </div>
    </div>
</div>	



<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureMaterialsList.js" type="module"></script>

