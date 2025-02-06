<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item">취업지원</li>
    <li class="breadcrumb-item active" aria-current="page">직업선호도평가 응시</li>
  </ol>
</nav>
<br>
<div class="container" style="
    margin: 50px auto;
    max-width: 800px;
    padding: 30px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;">
    <h3 style="
        font-size: 24px;
        font-weight: bold;
        color: #343a40;
        margin-bottom: 10px;">
        직업선호도평가
    </h3>
    <p style="
        font-size: 16px;
        color: #6c757d;
        line-height: 1.5;
        margin-bottom: 20px;">
        좋아하는 활동, 관심있는 직업, 선호하는 분야를 탐색하여<br>
        당신에게 적합한 직업흥미유형을 알아보세요.<br>
        쉽고 빠르게 직업 성향을 분석할 수 있습니다.
    </p>
    <button class="btn-primary" id="startTestBtn" style="
        display: inline-block;
        background-color: #007bff;
        color: white;
        padding: 12px 20px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        text-decoration: none;">
        검사 시작
    </button>
    <div class="footer-space" style="margin-top: 20px;"></div>
</div>
<input type="hidden" id="cp" value="${pageContext.request.contextPath }">

<script>
// 사용자 ID 가져오기
const cp = document.getElementById("cp").value;
const userId = '${principal}';
console.log(userId);

document.getElementById("startTestBtn").addEventListener("click", function () {
    fetch(cp+`/jobtest/checkToday/${principal}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            if (data.status === "allowed") {
                // 중복이 아니면 새 창 열기
                window.open(
                    cp+'/jobtest/list', // 검사 시작 URL
                    '직업선호도검사', // 창 이름
                    'width=800,height=600,resizable=no,scrollbars=yes'
                );
            } else {
                swal({
                    title: "이미 응시하셨습니다",
                    text: "오늘 이미 검사를 완료하셨습니다. 내일 다시 응시해주세요.",
                    icon: "warning",
                    button: "확인"
                });
            }
        })
        .catch(error => {
            console.error("오류 발생:", error);
            swal({
                title: "오류 발생",
                text: "중복 검사 중 문제가 발생했습니다. 다시 시도해주세요.",
                icon: "error",
                button: "확인"
            });
        });
});
</script>
