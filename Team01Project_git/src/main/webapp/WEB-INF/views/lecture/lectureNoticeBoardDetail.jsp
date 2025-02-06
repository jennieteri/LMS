<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
<script type="importmap">
{
	"imports": {
					"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
					"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
	}
}	
</script>

<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
<input type="hidden" value="${lectNo}" id="lectNo">
<input type="hidden" value="${cnbNo}" id="cnbNo">
<input type="hidden" value="${mainBoardCount }" id="mainBoardCount">

<div>
	<table class="table table-primary align-middle table-bordered table-sm" id="lectBoardTable">
		<tbody>
			<tr>
			    <td class="table-primary text-center align-middle p-1 w-auto" style="white-space: nowrap;">제목</td>
			    <td class="table-light align-middle table-bordered" style="white-space: nowrap;" colspan="5">${selectLectureNoticeBoard.cnbNm}</td>
			</tr>
			<tr>
			    <td class="text-center" style="white-space: nowrap;">교수명</td>
			    <td class="table-light align-middle text-center table-bordered" style="white-space: nowrap;">${selectLectureNoticeBoard.professorVO.nm}</td>
			    <td class="text-center" style="white-space: nowrap;">작성일시</td>
			    <td class="table-light align-middle text-center table-bordered	">${fn:split(selectLectureNoticeBoard.cnbDt, 'T')[0] } ${fn:split(selectLectureNoticeBoard.cnbDt, 'T')[1] }</td>
			    <security:authorize access="hasRole('PROFESSOR')">
			        <td class="text-center" style="white-space: nowrap;">공지사항 고정</td>
			        <td class="table-light align-middle text-center table-bordered" style="white-space: nowrap;">${selectLectureNoticeBoard.cnbMainYn}</td>
			    </security:authorize>
			</tr>
			<tr>
			    <td class="table table-bordered align-middle table-bordered" style="height:300px;" colspan="6">
			        <span id="absenceReason2" class="large-text"></span>
			    </td>
			</tr>
			<tr>
			    <td class="text-center" style="white-space: nowrap;">첨부파일</td>
			    <td class="table table-bordered align-middle table-bordered" colspan="5">
			        <div id="fileDetailsContainer"></div>
			    </td>
			</tr>
			<security:authorize access="hasRole('PROFESSOR')">
				<tr>
					<td style="text-align: right;" colspan="6">
						<!-- 모달창으로 수정폼을 보여줬지만 더이상 사용하지않음
						<button type="button" 
							id="editButton" 
							class="btn btn-primary" 
							data-bs-toggle="modal"
							data-bs-target="#staticBackdropEdit">
							수정
						</button>
						-->
						<button type="button" class="btn btn-primary" id="editButton">수정</button>
						<button type="button" id="dropButton" class="btn btn-danger atnd-insert-btn">삭제</button>
					</td>
				</tr>
			</security:authorize>
		</tbody>
	</table>
	<div class="d-flex justify-content-end">
		<a href="${pageContext.request.contextPath}/lecture/${lectNo}/board" class="btn btn-secondary btn-sm mb-3">
		    &lt;&lt; 뒤로가기
		</a>
	</div>
</div>



<div class="modal fade" id="staticBackdropEdit" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabelEdit" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 80%; width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabelEdit">수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	        </div>
	            <div class="modal-body">
	                <table class="table">
	                    <thead>
				        <tr>
							<td>제목</td>
							<td>
								<input type="text" id="contextTitle" value="" style="width: 100%;" >
							</td>
							<td class="align-middle text-center table-bordered" style="width: auto;" >공지사항 고정</td>
							<td>
								<input type="checkbox" id="boardMainYn" value="">
							</td>
						</tr>
			            </thead>
			            <tbody>
					        <tr>
					          <td colspan="4">
					              <p><strong>내용</strong></p>
						          <form id="edit-table" data-path="${pageContext.request.contextPath}">
						              <textarea class="ckEditor" id="updateEditor" rows="3"></textarea>
						          </form>
								  <form id="fileForm" method="PUT" enctype="multipart/form-data">
					          	      <input type="file" name="uploadFiles" multiple class="form-control" style="float: left">
								      <div id="fileDetailsContainer"></div>
					          	  </form>
					          	  <div id="fileDetailsContainer2">
								      <!-- 조회 결과가 여기에 표시됩니다 -->
								  </div>
					          </td>
					        </tr>
				        </tbody>
	                </table>
	            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveEditButton">수정</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
        </div>
    </div>
</div>	



<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureNoticeBoardDetail.js" type="module"></script>

