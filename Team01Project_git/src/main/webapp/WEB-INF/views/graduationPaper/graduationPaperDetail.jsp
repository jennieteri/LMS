<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">학생 논문 리스트</li>
    <li class="breadcrumb-item active" aria-current="page">논문 상세보기</li>
  </ol>
</nav>

<body>
<br>
<div class="container mt-5">
    <!-- 안내 문구 -->
    <div class="alert alert-primary text-center" role="alert">
    <h4 >안내문</h4>
        현재 보고 계신 논문은 학생이 제출한 최종본입니다. 내용을 충분히 검토하신 후 평가 및 승인 여부를 결정 해주시고,
        <br>
        공정하고 객관적인 평가를 위해 논문 심사 기준을 준수해 주시길 바랍니다.
    </div>
    <input type="hidden" value="${paper.gpaCd }" id="gpaCd">
    <div class="details-container">
        <table class="table table-bordered">
            <thead class="table-secondary">
                <tr>
                    <th colspan="2" class="text-center">논문 정보</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th class="text-center">제목</th>
                    <td>${paper.gpaNm}</td>
                </tr>
                <tr>
                    <th class="text-center">부제목</th>
                    <td>${paper.gpaDnm != null ? paper.gpaDnm : "없음"}</td>
                </tr>
                <tr>
                    <th class="text-center">주제</th>
                    <td>${paper.gpaSub != null ? paper.gpaSub : "없음"}</td>
                </tr>
                <tr>
                    <th class="text-center">학생 번호</th>
                    <td>${paper.stuId}</td>
                </tr>
                <tr>
                    <th class="text-center">논문 등록일자</th>
                    <td>${paper.gpaDate}</td>
                </tr>
                <tr>
                    <th class="text-center">상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${paper.gpaStatus eq 'G003'}">승인 대기</c:when>
                            <c:when test="${paper.gpaStatus eq 'G004'}">승인</c:when>
                            <c:when test="${paper.gpaStatus eq 'G005'}">반려</c:when>
                            <c:otherwise>알 수 없음</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </tbody>
        </table>

		<!-- 첨부 파일 -->
		<table class="table table-bordered mt-4">
		    <thead class="table-secondary">
		        <tr>
		            <th class="text-center">No.</th>
		            <th class="text-center">첨부파일명</th>
		            <th class="text-center"></th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:if test="${not empty paper.atchFile.fileDetails}">
		            <c:forEach var="file" items="${paper.atchFile.fileDetails}">
		                <tr>
		                    <td class="text-center">${file.fileSn}</td>
		                    <td>${file.orignlFileNm}</td>
		                    <td class="text-center">
		                        <a href="${pageContext.request.contextPath}/graduationpaper/atch/${paper.atchFile.atchFileId}/${file.fileSn}">
		                            논문 파일 다운로드
		                        </a>
		                    </td>
		                </tr>
		            </c:forEach>
		        </c:if>
		        <c:if test="${empty paper.atchFile.fileDetails}">
		            <tr>
		                <td colspan="3" class="text-center">첨부 파일이 없습니다.</td>
		            </tr>
		        </c:if>
		    </tbody>
		</table>


		<div style="text-align: right; margin-top: 20px;">
		    <button class="btn btn-primary" id="apply">승인</button>
		    <a href="${pageContext.request.contextPath}/graduationpaper/list/prof" class="btn btn-warning" id="reject">반려</a>
		    <a href="${pageContext.request.contextPath}/graduationpaper/list/prof" class="btn btn-secondary">목록</a>
		</div>

    </div>
</div>
</body>
<input type="hidden" value="${pageContext.request.contextPath }" id="cp">
<script src="${pageContext.request.contextPath }/resources/js/graduationPaper/graduationPaperDetail.js"></script>