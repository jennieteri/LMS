<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">자소서 클리닉</li>
    <li class="breadcrumb-item active" aria-current="page">자기소개서 상세보기</li>
  </ol>
</nav>

<table class="table table-bordered" id="form-table" data-path="${pageContext.request.contextPath }" style="width: 100%; border-collapse: collapse; margin: 20px 0; font-size: 16px;">
    <tr>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">학번</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.stuId}</td>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">학년</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.commoncode.cocoStts}</td>
    </tr>
    <tr>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">이름</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.person.nm}</td>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">취업희망분야</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.employmentfield.empfiNm}</td>
    </tr>
   <tr>
    <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">자격증</th>
    <td colspan="3" style="padding: 12px; border: 1px solid #dee2e6;">
        <c:choose>
            <c:when test="${introduce.certificate != null and not empty introduce.certificate}">
                <ul>
                    <c:forEach var="cert" items="${introduce.certificate}">
                        <li>
                            <strong>자격증명:</strong> ${cert.certNm != null ? cert.certNm : '없음'}<br />
                            <strong>취득일:</strong> ${cert.certDate != null ? cert.certDate : '없음'}
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                자격증 없음
            </c:otherwise>
        </c:choose>
    </td>
</tr>

    <tr>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">작성일자</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.intDate}</td>
        <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">첨삭일자</th>
        <td style="padding: 12px; border: 1px solid #dee2e6;">${introduce.intUdate}</td>
    </tr>
    <tr>
<tr>
    <th class="text-center" style="padding: 12px; border: 1px solid #dee2e6;">첨부파일</th>
    <td colspan="3" style="padding: 12px; border: 1px solid #dee2e6;">
        <c:forEach items="${introduce.certificate}" var="cert">
            <div>
                <!-- 첨부파일 정보 -->
                <c:if test="${not empty cert.atchFile && not empty cert.atchFile.fileDetails}">
                    <ul>
                        <c:forEach items="${cert.atchFile.fileDetails}" var="file">
                            <li>
                                <!-- file_stre_cours 활용 -->
                                <a href="${pageContext.request.contextPath}/atch/${cert.atchFile.atchFileId}/${file.fileSn}" style="text-decoration: none; color: #007bff; transition: color 0.2s ease;">
                                   ${file.orignlFileNm}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${empty cert.atchFile || empty cert.atchFile.fileDetails}">
                    <p style="color: #6c757d;">첨부파일이 없습니다.</p>
                </c:if>
            </div>
        </c:forEach>
    </td>
</tr>

</table>

<table class="table table-bordered" style="width: 100%; border-collapse: collapse; margin: 20px 0; font-size: 16px;">
    <tbody>
        <tr>
            <th style="padding: 12px; border: 1px solid #dee2e6;">지원동기</th>
        </tr>
        <tr>
            <td id="originalIntQue1" style="padding: 12px; border: 1px solid #dee2e6;">${introduce.intQue1}</td>
        </tr>
        <tr>
            <th style="padding: 12px; border: 1px solid #dee2e6;">입사 후 포부</th>
        </tr>
        <tr>
            <td id="originalIntQue2" style="padding: 12px; border: 1px solid #dee2e6;">${introduce.intQue2}</td>
        </tr>
        <tr>
            <th style="padding: 12px; border: 1px solid #dee2e6;">성격 및 장단점</th>
        </tr>
        <tr>
            <td id="originalIntQue3" style="padding: 12px; border: 1px solid #dee2e6;">${introduce.intQue3}</td>
        </tr>
    </tbody>
</table>

<!-- 통합 피드백 영역 -->
<div class="feedback-section" style="margin-top: 20px; padding: 15px; background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px;">
    <h3 style="margin-bottom: 10px; font-size: 18px; font-weight: bold; color: #343a40; text-align: center;">통합 피드백</h3>
    <div>
        <c:if test="${empty introduce.intFeed}">
            <p style="color: #6c757d; font-style: italic;">피드백 사항이 존재하지 않습니다.</p>
        </c:if>
        <c:if test="${not empty introduce.intFeed}">
            <p style="color: #d9534f;">${introduce.intFeed}</p>
        </c:if>
    </div>
</div>

<div class="btn-area" style="margin-top: 20px; text-align: right;">
<security:authorize access="hasRole('EMPLOYEE')">
    <a href="<c:url value='/introduce/edit/${introduce.intCd}' />" class="btn btn-info">피드백 작성</a>
    <a href="<c:url value='/introduce' />" class="btn btn-primary">목록</a>
</security:authorize>

<security:authorize access="hasRole('STUDENT')">
    <a href="<c:url value='/introduce/list/${introduce.stuId }' />" class="btn btn-primary">목록</a>
</security:authorize>
</div>
