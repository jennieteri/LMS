<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h4>학생증 상세조회</h4>
<script>
    const contextPath = "${pageContext.request.contextPath}/";
</script>

<form id="updateStatusForm" action="${pageContext.request.contextPath}/studentCard/updateStatus" method="POST">
   
    <input type="hidden" name="stuId" value="${card.stuId}" />

    <table class="table table-bordered">
        <tbody>
            <tr>
                <td>발급상태</td>
                <td>
                    <select id="statusSelect" name="cocoCd" class="form-select">
                        
                        <option value="">${card.commonCodeVO.cocoStts}</option>
                        <option value="ST01" ${card.commonCodeVO.cocoCd == 'ST01' ? 'selected' : ''}>요청</option>
                        <option value="ST02" ${card.commonCodeVO.cocoCd == 'ST02' ? 'selected' : ''}>발급</option>
                        <option value="ST03" ${card.commonCodeVO.cocoCd == 'ST03' ? 'selected' : ''}>완료</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>학번</td>
                <td>${card.stuId}</td>
            </tr>
            <tr>
                <td>이름</td>
                <td>${card.studentVO.nm}</td>
            </tr>
            <tr>
                <td>학년 / 학과</td>
                <td>2학년 / 컴퓨터 공학</td>
            </tr>
            <tr>
                <td>생년월일</td>
                <td>
                    ${fn:substring(fn:replace(card.studentVO.brdt, '-', ''), 0, 4)}년&nbsp;
                    ${fn:substring(fn:replace(card.studentVO.brdt, '-', ''), 4, 6)}월&nbsp;
                    ${fn:substring(fn:replace(card.studentVO.brdt, '-', ''), 6, 8)}일
                </td>
            </tr>
            <tr>
                <td>전화번호</td>
                <td>${card.studentVO.mbtlnum}</td>
            </tr>
            <tr>
                <td>이메일</td>
                <td>${card.studentVO.eml}</td>
            </tr>
            <tr>
                <td>요청일시</td>
                <td>${fn:split(card.stuCardDate, 'T')[0]} ${fn:split(card.stuCardDate, 'T')[1]}</td>
            </tr>
            <tr>
                <td>증명사진</td>
                <td>
                    <c:if test="${not empty card.studentVO.proflPhoto}">
                        <img src="data:image/png;base64,${card.studentVO.proflPhoto}" alt="증명사진" style="width: 150px; height: auto;" />
                    </c:if>
                    <c:if test="${empty card.studentVO.proflPhoto}">
                        없음
                    </c:if>
                </td>
            </tr>
        </tbody>
    </table>
</form>
<div>
<a class="btn btn-primary float-end ms-2 mb-3" href="${pageContext.request.contextPath }/studentCard">목록</a>
${card.commonCodeVO.cocoStts }
<c:if test="${card.commonCodeVO.cocoStts =='완료'}">
<button class="btn btn-danger float-end ms-2 ansDeleteBtn">삭제</button>
</c:if>
</div>

<script src="${pageContext.request.contextPath }/resources/js/studentCard/studentCardStatus.js" type="module"></script>