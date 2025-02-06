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

<table class="table">
	<thead>
		<tr>
			<td class="flex-fill p-2 text-center table-primary text-black">제목</td>
			<td class="flex-fill p-2 text-center border">
				<input type="text" id="contextTitle" style="width : 100%; height : 100%;" value="${selectLectureNoticeBoard.cnbNm}">
			</td>
			<td class="flex-fill p-2 text-center table-primary text-black">공지사항 고정</td>
			<td class="flex-fill p-2 text-center border">
				${selectLectureNoticeBoard.cnbMainYn}
				<input type="checkbox" id="boardMainYn" value="">
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="flex-fill p-2 text-center table-primary text-black" colspan="4">
				<form id="edit-table" data-path="${pageContext.request.contextPath}">
					<textarea class="ckEditor" id="updateEditor" rows="3"></textarea>
				</form>
			</td>
		</tr>
		<tr>
			<td class="flex-fill p-2 text-center table-primary text-black">첨부파일</td>
			<td colspan="3">
				<form id="fileForm" method="PUT" enctype="multipart/form-data">
					<input type="file" name="uploadFiles" multiple class="form-control"
						style="float: left">
					<div id="fileDetailsContainer"></div>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="fileDetailsContainer2">
					<!-- 조회 결과가 여기에 표시됩니다 -->
				</div>
			</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td class="flex-fill p-2 text-center table-primary text-black" style="text-align: right;" colspan="4">
				<button type="button" class="btn btn-primary" id="saveEditButton">수정</button>
				<button type="button" class="btn btn-secondary" id="cancleBtn">취소</button>
			</td>
		</tr>
	</tfoot>
</table>







<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureNoticeBoardEdit.js" type="module"></script>


