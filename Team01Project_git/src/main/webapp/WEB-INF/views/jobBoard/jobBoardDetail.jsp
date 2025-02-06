<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업정보게시판</li>
    <li class="breadcrumb-item active" aria-current="page">상세보기</li>
  </ol>
</nav>

<input type="hidden" id="cp" value="${pageContext.request.contextPath }">
<input type="hidden" id="detail-id" value="${jobboard.jobNo }"/>

<table class="table table-primary" id="form-table" data-path="${pageContext.request.contextPath }">
	<tr>
		<th class="text-center">제목</th>
		<td class="table-light">${jobboard.jobNm}</td>
		<th class="text-center">카테고리</th>
		<td class="table-light">${jobboard.jobCate}</td>
	</tr>
	<tr>
		<th class="table-primary text-center">공고 시작일</th>
		<td class="table-light">${jobboard.jobDt}</td>
		<th class="table-primary text-center">공고 마감일</th>
		<td class="table-light">${jobboard.jobEt}</td>
	</tr>
	<tr>
		<th class="table-primary text-center">작성자</th>
		<td class="table-light">${jobboard.writerNm}</td>
		<th class="table-primary text-center">작성일</th>
		<td class="table-light">${jobboard.jobDate}</td>
	</tr>

	<!-- 첨부파일 -->
	<tr>
		<th class="table-primary text-center">첨부파일</th>
		<td colspan="3" class="table-light"><c:forEach
				items="${jobboard.atchFile.fileDetails}" var="fd" varStatus="vs">
				<c:url value='/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl" />
				<a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
                ${not vs.last ? '|' : ''}
            </c:forEach> <c:if test="${empty jobboard.atchFile.fileDetails}">
                첨부파일 없음
            </c:if>
        </td>
	</tr>


	<!-- 내용 -->
    <tr>
        <td class="table-light" colspan="4">
            <!-- 게시글 내용 -->
            <div>${jobboard.jobContent}</div>

            <!-- 첨부된 이미지 파일을 내용에 추가 -->
            <c:forEach items="${jobboard.atchFile.fileDetails}" var="fd">
                <!-- 이미지 파일 확장자 검사 -->
                <c:if test="${fn:toLowerCase(fd.fileExtsn) == 'jpg' || 
                             fn:toLowerCase(fd.fileExtsn) == 'jpeg' || 
                             fn:toLowerCase(fd.fileExtsn) == 'png' || 
                             fn:toLowerCase(fd.fileExtsn) == 'gif'}">
                    <div style="margin-top: 10px;">
                        <img src="<c:url value='/jobboard/${jobboard.jobNo}/atch/${fd.atchFileId}/${fd.fileSn}' />" 
                             alt="${fd.orignlFileNm}" 
                             style="max-width: 100%; height: 100%;" />
                    </div>
                </c:if>
            </c:forEach>
        </td>
    </tr>
</table>

<form id="applyForm">
<!-- 신청하기에 들어가는 jobNo, stuId가져오는 폼 -->
<security:authentication property="principal" var="person"/>
	<table style="display:none;" >
	<tr>
	 	<th>jobNo</th>
        <td id="apply-jobno">${jobboard.jobNo}</td>
        <th>로그인한사용자id</th>
        <td id="apply-id">${person.realUser.id}</td>
     </tr>
	</table>
</form>

<div class="btn-area" style="text-align: right; margin-bottom:10px;">
    <security:authorize access="hasRole('EMPLOYEE')">
        <a href="<c:url value='/jobboard/${jobboard.jobNo}/edit' />" class="btn btn-info" style="margin-right: 10px;">수정</a>
        <button type="submit" id="deleteBtn" class="btn btn-danger" style="margin-right: 10px;">삭제</button>
    </security:authorize>
        <c:if test="${jobboard.jobCate == '채용공고'}">
    <security:authorize access="hasRole('STUDENT')">
       
            <c:if test="${linkedYn}">
            	<button class="btn btn-secondary">
            		<i class="bi bi-calendar-check-fill" id="favoriteIcon" data-bono="${jobboard.jobNo}" data-yn="Y" data-url="job"></i>
            	</button>
            </c:if>
            <c:if test="${not linkedYn}">
            	<button class="btn btn-secondary">
            		<i class="bi bi-calendar-check" id="favoriteIcon" data-bono="${jobboard.jobNo}" data-yn="N" data-url="job"></i>
            	</button>
            </c:if>

    </security:authorize>
</c:if>
        <c:if test="${jobboard.jobCate == '채용설명회'}">
        <security:authorize access="hasRole('STUDENT')">
                <c:choose>
                    <c:when test="${isRegistered == 1}">
                        <button type="submit" id="cancelBtn" class="btn btn-danger">취소하기</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" id="applyBtn" class="btn btn-primary">신청하기</button>
                    </c:otherwise>
                </c:choose>
        </security:authorize>
    </c:if>
    <a href="<c:url value='/jobboard' />" class="btn btn-primary">목록</a>
    

</div>

<script src="${pageContext.request.contextPath }/resources/js/jobboard/jobBoardDelete.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/utils/mycalendarLinked.js"></script>
