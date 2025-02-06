<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업정보게시판</li>
    <li class="breadcrumb-item active" aria-current="page">취업정보게시판 수정</li>
  </ol>
</nav>

<br>
<form:form id="editForm" method="post" enctype="multipart/form-data" modelAttribute="jobboard">
    <table id="form-table" data-path="${pageContext.request.contextPath }" class="table table-primary">

        <!-- 제목 -->
        <tr>
            <th class="text-center align-middle">제목</th>
            <td class="table-light">
                <form:input path="jobNm" class="form-control" />
            </td>
        </tr>
        
        <!-- 카테고리 -->
        <tr>
            <th class="text-center align-middle">카테고리</th>
            <td class="table-light">
                <form:select path="jobCate" class="form-select">
                    <form:option value="" label="카테고리 유형을 선택하세요" />
                    <form:option value="채용공고" label="채용공고" />
                    <form:option value="채용설명회" label="채용설명회" />
                </form:select>
            </td>
        </tr>
        
        <!-- 공고 시작일자 -->
        <tr>
            <th class="text-center align-middle">공고 시작 일자</th>
            <td class="table-light">
                <form:input path="jobDt" type="date" class="form-control" />
            </td>
        </tr>
        
        <!-- 공고 마감일자 -->
        <tr>
            <th class="text-center align-middle">공고 마감 일자</th>
            <td class="table-light">
                <form:input path="jobEt" type="date" class="form-control" />
            </td>
        </tr>
        
        <!-- 제한 인원 수 -->
        <tr>
            <th class="text-center align-middle">제한인원 수</th>
            <td class="table-light">
                <form:input path="jobLimit" class="form-control" placeholder="제한없음 및 제한-인원수를 작성해주세요." />
            </td>
        </tr>
        
        <!-- 첨부 파일 목록 -->
        <tr>
            <th class="text-center align-middle">첨부파일</th>
            <td class="table-light">
                <c:if test="${not empty jobboard.atchFile and not empty jobboard.atchFile.fileDetails}">
    <c:forEach items="${jobboard.atchFile.fileDetails}" var="fd" varStatus="vs">
        <span>
            ${fd.orignlFileNm}[${fd.fileFancysize}]
            <a data-atch-file-id="${fd.atchFileId}" data-file-sn="${fd.fileSn}" class="btn btn-danger" href="javascript:;">
                삭제
            </a>
            ${not vs.last ? '|' : ''}
        </span>
    </c:forEach>
</c:if>


                <!-- 새 파일 업로드 -->
                <div>
                    <input type="file" name="uploadFiles" multiple class="form-control" />
                </div>
            </td>
        </tr>
        
        <!-- 내용 -->
        <tr>
            <td colspan="3" class="table-light">
                <form:textarea path="jobContent" class="tinymce-editor"></form:textarea>
            </td>
        </tr>
        
        <!-- 버튼 -->
        <tr>
            <td colspan="2" style="text-align: right;" class="table-light">
                <button type="submit" class="btn btn-primary">저장</button>
                <a href="<c:url value='/jobboard/ ' />" class="btn btn-secondary">닫기</a>
            </td>
        </tr>
    </table>
</form:form>



<script src="${pageContext.request.contextPath }/resources/js/jobboard/jobBoardForm.js"></script>

