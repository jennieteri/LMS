<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 추천/ 신청서 목록</li>
    <li class="breadcrumb-item active" aria-current="page">장학금 추천/신청서 상세조회</li>
  </ol>
</nav>


<input type="hidden" id="approvalDateInput" name="shapChcDate" value="${ask.shapChcDate}" />
<input type="hidden" name="shapDocNo" value="${ask.shapDocNo}" />

<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>
<style>
/* 메인 테이블 스타일 */

th{
text-align: center;
width: 180px;
}
.table {
    width: 70%;
    margin: 0 auto;
    border-collapse: collapse;
}
th, td {
    padding: 10px;
    text-align: center;
    vertical-align: middle;
    border: 1px solid #ddd;
}




</style>
<!-- 반려 사유 모달 -->
<div id="rejectReasonModal" class="modal fade" tabindex="-1" aria-labelledby="rejectReasonModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="rejectReasonModalLabel">반려 사유 입력</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <textarea id="rejectReason" class="form-control" placeholder="반려 사유를 입력하세요." rows="4"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" id="saveRejectReasonButton" class="btn btn-primary">저장</button>
            </div>
        </div>
    </div>
</div>

<form id="updateStatusForm" action="${pageContext.request.contextPath}/askAward/updateStatus" method="POST">
    <table class="table table-primary">
			<tr>
			    <th>상태</th>
			    <td class="bg-light" colspan="3" style="text-align: center;">
			        <!-- Hidden Inputs -->
			        <input type="hidden" id="approvalDateInput" name="shapChcDate" value="${ask.shapChcDate}" />
			        <input type="hidden" name="shapDocNo" value="${ask.shapDocNo}" />
			        
			        <!-- 상태 선택 -->
			        <select id="statusSelect" name="cocoStts" class="form-select" style="width: 150px; display: inline-block;">
			            <option value="A01" ${ask.commonCodeVO.cocoCd == 'A01' ? 'selected' : ''}>대기</option>
			            <option value="A02" ${ask.commonCodeVO.cocoCd == 'A02' ? 'selected' : ''}>접수</option>
			            <option value="A03" ${ask.commonCodeVO.cocoCd == 'A03' ? 'selected' : ''}>승인</option>
			            <option value="A04" ${ask.commonCodeVO.cocoCd == 'A04' ? 'selected' : ''}>반려</option>
			        </select>
			    </td>
			</tr>



        <tr>
            <th>학기번호</th>
            <td  class="bg-light">${fn:substring(ask.semstrNo, 0, 4)}-${fn:substring(ask.semstrNo, 4, 6)}</td>
            
            <th>장학금 유형</th>
            <td  class="bg-light">${ask.awardVO.awardNm}</td>
        </tr>
      
        <tr>
            <th>학번</th>
            <td  class="bg-light">${ask.stuId}</td>
            <th>이름</th>
            <td  class="bg-light">${ask.studentVO.nm}</td>
        </tr>
        <tr>
            <th>생년월일</th>
            <td colspan="3"  class="bg-light">
                ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 0, 4)}년&nbsp;
                ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 4, 6)}월&nbsp;
                ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 6, 8)}일
            </td>
        </tr>
        <tr>
            <th>이메일</th>
            <td colspan="3"  class="bg-light">${ask.studentVO.eml}</td>
        </tr>
        <tr>
            <th>전화번호</th>
            <td colspan="3"  class="bg-light">${ask.studentVO.mbtlnum}</td>
        </tr>
        <tr>
            <th>집주소</th>
            <td colspan="3"  class="bg-light">(${ask.studentVO.zip}) &nbsp; ${ask.studentVO.rdnmadr} &nbsp; ${ask.studentVO.daddr}</td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td colspan="3"  class="bg-light">
                <c:forEach items="${ask.atchFile.fileDetails}" var="fd" varStatus="vs">
                    <c:url value='/askAward/${ask.shapDocNo}/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl"/>
                    <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
                    ${not vs.last ? '|' : ''}
                </c:forEach>
                <c:if test="${empty ask.atchFile.fileDetails}">첨부파일 없음</c:if>
            </td>
        </tr>
    </table>


    <div align="right" >
        <a href="<c:url value='/askAward'/>" class="btn btn-primary">목록</a>
        
    </div>
<script src="${pageContext.request.contextPath }/resources/js/awardAsk/awardAsKStatus.js"></script>
</form>



<%-- <script src="${pageContext.request.contextPath }/resources/js/awardAsk/awardAskReason.js"></script> --%>

