<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
<input type="hidden" value="${mainBoardCount }" id="mainBoardCount">

<div>
	<form name="fileForm" method="Post" enctype="multipart/form-data">
		<table class="table table-primary align-middle table-bordered" id="lectBoardTable">
			<tbody>
				<tr>
					<td style="width: 15%; text-align: center;">
						제목
					</td>
					<td class="table table-bordered align-middle table-bordered">
						<input type="text" id="boardTitle" name="cnbNm" style="width: 100%;">
					</td>
					<td class="text-center align-middle" style="white-space: nowrap;">공지사항 고정</td>
					<td class="text-center align-middle bg-white" style="white-space: nowrap;">
						<input type="checkbox" id="boardMainYn" value="">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea class="ckEditor" id="editor" name="cnbNotes" rows="3" colspan="2"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 15%; text-align: center;">첨부파일</td>
					<td class="table table-bordered align-middle table-bordered" colspan="3">
						<input type="file" name="uploadFiles" multiple class="form-control">
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right;">
						<button type="button" class="btn btn-outline-primary" id="dummyData">데이터 입력</button>
						<button type="submit" class="btn btn-primary" id="submitBtn"">등록</button>
					</td>
				</tr>
		</table>
	</form>
</div>

















<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureNoticeBoardForm.js" type="module"></script>

