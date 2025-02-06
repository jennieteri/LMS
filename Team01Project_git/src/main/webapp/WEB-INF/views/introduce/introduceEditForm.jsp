<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">자기소개서 상세보기</li>
    <li class="breadcrumb-item active" aria-current="page">자기소개서 피드백</li>
  </ol>
</nav>
<br>
<form action="<c:url value='/introduce/edit' />" method="post">
 <table class="table table-bordered">
 
    <!-- 학생 정보 -->
    <tr>
        <th class="text-center" style="width: 20%;">학번</th>
        <td>${introduce.stuId}</td>
        <th class="text-center" style="width: 20%;">학년</th>
        <td>${introduce.commoncode.cocoStts}</td>
    </tr>
    <tr>
        <th class="text-center">이름</th>
        <td>${introduce.person.nm}</td>
        <th class="text-center">취업희망분야</th>
        <td>${introduce.employmentfield.empfiNm}</td>
    </tr>
    <!-- 자격증 -->
    <tr>
        <th class="text-center">자격증</th>
        <td colspan="3">
            <c:choose>
                <c:when test="${not empty introduce.certificate}">
                    <ul>
                        <c:forEach var="cert" items="${introduce.certificate}">
                            <li>
                                자격증명: ${cert.certNm} <br> 취득일: ${cert.certDate}
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <span>자격증 없음</span>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<!-- 자기소개서 답변 -->
<table class="table table-bordered">
    <thead>
        <tr class="text-center">
            <th style="width: 20%;">항목</th>
            <th>답변</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td class="align-middle text-center" style="font-weight: bold;">지원동기</td>
            <td>${introduce.intQue1}</td>
        </tr>
        <tr>
            <td class="align-middle text-center" style="font-weight: bold;">입사 후 포부</td>
            <td>${introduce.intQue2}</td>
        </tr>
        <tr>
            <td class="align-middle text-center" style="font-weight: bold;">성격 및 장단점</td>
            <td>${introduce.intQue3}</td>
        </tr>
    </tbody>
</table>

<!-- 피드백 -->
<table class="table table-bordered">
    <tr>
        <th class="align-middle text-center" style="width: 20%;">피드백 사항</th>
        <td>
            <textarea id="intFeed" name="intFeed" rows="5" class="form-control" placeholder="피드백 사항을 작성해주세요">${introduce.intFeed}</textarea>
        </td>
    </tr>
</table>


    <input type="hidden" name="stuId" value="${introduce.stuId}" />

    <div class="btn-area" style="text-align:right;">
        <button type="submit" id="saveButton" class="btn btn-primary">저장</button>
        <a href="<c:url value='/introduce' />" class="btn btn-secondary">취소</a>
    </div>
</form>
<script>
document.getElementById('saveButton').addEventListener('click', function (e) {
    e.preventDefault(); 

    const stuid = document.querySelector('input[name="stuId"]').value;
    const intFeed = document.getElementById('intFeed').value;

    swal({
        title: "정말로 등록하시겠습니까?",
        text: "작성된 내용이 등록됩니다.",
        icon: "warning",
        buttons: ["취소", "등록"],
        dangerMode: true,
    }).then((btnVal) => {
        if (btnVal) {
            fetch(`/yguniv/introduce/edit/${introduce.intCd}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ stuid, intFeed })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('등록에 실패하였습니다.');
                }
                return response.json();
            })
            .then(data => {
                if (data) {
                    // 수정된 데이터 업데이트
                    document.getElementById('intFeed').value = data.intFeed;

                    swal({
                        title: "등록 완료",
                        text: "피드백 등록 완료되었습니다.",
                        icon: "success",
                    }).then(() => {
                        // 상세보기 페이지로 이동
                        window.location.href = `/yguniv/introduce/detail/${introduce.intCd}`;
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                swal({
                    title: "오류 발생",
                    text: "서버와의 통신에 실패하였습니다.",
                    icon: "error",
                });
            });
        }
    });
});

</script>

