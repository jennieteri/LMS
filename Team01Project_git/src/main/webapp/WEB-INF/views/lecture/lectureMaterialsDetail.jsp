<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }"/>
<input type="hidden" id="id" value="<security:authentication property="principal.username"/>"/>
<input type="hidden" id="lectNo" value="${orderLectureDataVO.lectNo }"/>
<input type="hidden" id="weekCd" value="${orderLectureDataVO.weekCd }"/>
<input type="hidden" id="lectOrder" value="${orderLectureDataVO.lectOrder }"/>
<div class="d-flex flex-column justify-content-start" style="max-height: 100%;">
	<div id="viewerHeader" class="bg-primary-subtle rounded-bottom rounded-pill p-2">
		<p class="ms-3 fw-bold d-inline rounded-pill bg-primary px-2 text-white me-1">${orderLectureDataVO.lectOrder }차시</p>
		<p class="d-inline fw-bold">${orderLectureDataVO.sectNm }</p>
		<p class="d-inline fw-bold float-end mb-0 me-4 rounded-pill bg-primary px-2 text-white" id="viewTime">--:--</p>
		<p class="d-inline fw-bold float-end mb-0 me-2">학습시간</p>
	</div>
	<div id="viewerBody" class="ratio ratio-16x9 bg-black">
		<video controls autoplay src="${pageContext.request.contextPath }/video/${orderLectureDataVO.atchFileDetailList[0].atchFileId}/${orderLectureDataVO.atchFileDetailList[0].fileSn}"></video>
	</div>
</div>
<a class="btn btn-primary float-end mt-3" href="${pageContext.request.contextPath }/lecture/${lecture.lectNo}/materials">목록</a>
<script src="${pageContext.request.contextPath }/resources/js/lecture/lectureMaterialsDetail.js"></script>