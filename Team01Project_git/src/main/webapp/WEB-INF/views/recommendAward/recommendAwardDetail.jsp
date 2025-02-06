<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">장학금 추천서 등록</li>
    <li class="breadcrumb-item active" aria-current="page">추천서 상세보기</li>
  </ol>
</nav>
<input type="hidden" value="${ask.shapDocNo }" id="shapDocNo" />
<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>
<style>
/* 메인 테이블 스타일 */
.table {
    width: 80%;
    margin: 0 auto;
    border-collapse: collapse;
    text-align: center;
   
}

.table th, .table td {
    text-align: center;
    vertical-align: middle;
    padding: 10px;
    border: 1px solid #ddd;
}

/* 하단 승인일자/반려사유 테이블 스타일 */
#approvalAndReason {
    width: 70%; /* 메인 테이블과 동일한 너비 */
    margin: 20px auto; /* 하단으로 간격 추가 */
    text-align: center;
}

#approvalAndReason table {
    width: 100%;
    border-collapse: collapse;
}

#approvalAndReason th, #approvalAndReason td {
    text-align: center;
    vertical-align: middle;
    padding: 10px;

}
</style>


<table class="table table-primary" >
				<tr>
				    <th>상태</th>
				    	<td class="bg-light">${ask.commonCodeVO.cocoStts}</td>
                
                <th>장학금 유형</th>
                <td class="bg-light">${ask.awardVO.awardNm}</td>
            </tr>
            <tr>
                <th>교번</th>
                <td class="bg-light">${ask.profeId }</td>
                <th>교수 이름</th>
                <td class="bg-light">${ask.professorVO.nm}</td>
            </tr>
           
            
           
            <tr>
                <th>학번</th>
                <td class="bg-light">${ask.stuId}</td>
                <th>학생 이름</th>
                <td class="bg-light">${ask.studentVO.nm}</td>
            </tr>
            
     
        
            <tr>
                <th>생년월일</th>
            
               <td colspan="3" class="bg-light"> 
				    ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 0, 4)}년&nbsp;
				    ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 4, 6)}월&nbsp;
				    ${fn:substring(fn:replace(ask.studentVO.brdt, '-', ''), 6, 8)}일
				</td>

                
                  
            </tr>
            <tr>
                <th>집 주소</th>
            <td colspan="4" class="bg-light">(${ask.studentVO.zip}) &nbsp;  ${ask.studentVO.rdnmadr} &nbsp;  ${ask.studentVO.daddr}</td>
                 
            </tr>
            <tr>
                <th>전화번호</th>
            <td colspan="4" class="bg-light">
            ${ask.studentVO.mbtlnum}
            </td>
                 
            </tr>
           
            <tr>
                <th>이메일</th>
            <td colspan="4" class="bg-light">${ask.studentVO.eml}</td>
                 
            </tr>
           
            <tr>
                <th>추천 사유</th>
                <td colspan="3" class="bg-light">${ask.shapRecommend }</td>
                
            </tr>
           
           
            <tr>
                <th>첨부파일</th>
	                <td colspan="3" class="bg-light">
		              <c:forEach items="${ask.atchFile.fileDetails }" var="fd" varStatus="vs">
				
				
				<c:url value='/recommendAward/${ask.shapDocNo}/atch/${fd.atchFileId}/${fd.fileSn}' var="downUrl"/>
				
				<a href="${downUrl }">${fd.orignlFileNm }(${fd.fileFancysize })</a>
				${not vs.last ? '|' : ''}
			</c:forEach>
			
					<c:if test="${empty ask.atchFile.fileDetails}">
					    첨부파일없음
					</c:if>
					</td>
            </tr>
</table>



 
<br>
			<div align="right">
			    <c:if test="${ask.commonCodeVO.cocoStts != '승인' && ask.commonCodeVO.cocoStts != '반려'}">
			        <form action="<c:url value='/recommendAward/delete/${ask.shapDocNo}' />" method="post" style="display:inline;">
			            <button type="submit" class="btn btn-danger" id="deleteButton">삭제</button>
			        </form>
			    </c:if>
			    <a href="<c:url value='/recommendAward/selectPro/${ask.profeId}' />" class="btn btn-primary">목록</a>
			</div>

<script>
    // 상태 값을 확인하여 표시/숨김 처리
    const status = "${ask.commonCodeVO.cocoStts}"; // 상태 값 가져오기
    const approvalAndReasonDiv = document.getElementById("approvalAndReason");

    if (status === "반려") {
        approvalAndReasonDiv.style.display = "block"; // 상태가 승인 또는 반려일 때 표시
    } else {
        approvalAndReasonDiv.style.display = "none"; // 상태가 아닐 경우 숨김
    }
</script>

<!-- 이거는 게시물 삭제  -->
<script src="${pageContext.request.contextPath }/resources/js/awardRec/awardRecDelete.js"></script>

