document.addEventListener("DOMContentLoaded", function () {
  const authForm = document.forms.authForm;
  const passwordInput = document.getElementById("pswd");

    // ENTER 키로 제출 동작 추가
    passwordInput.addEventListener("keyup", function (event) {
      if (event.key === "Enter") {
        event.preventDefault(); // 기본 제출 동작 방지
        submitButton.click(); // 버튼 클릭 이벤트 트리거
    	}
	});

    // 버튼 클릭 이벤트 처리
    authForm.addEventListener("submit", function(e) {
		e.preventDefault();
      const pswd = passwordInput.value.trim(); // 비밀번호 입력값
      const id = document.getElementById("id").value.trim(); // 사용자 ID
      const authMessage = document.getElementById("authMessage");

      console.log("Password:", pswd); // 비밀번호 확인
      console.log("ID:", id); // 사용자 ID 확인

      if (!pswd) {
        authMessage.innerText = "비밀번호를 입력하세요."; // 비밀번호 입력 확인
        return;
      }

      // 서버로 비밀번호 전송
      fetch(`${contextPath}/mypage/${id}/auth`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ pswd: pswd })
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Response:", data); // 서버 응답 확인
          if (data.success) {
            // 비밀번호 확인 성공: 수정 페이지로 이동
			authMessage.innerText = "";
			
            window.location.href = `${contextPath}/mypage/${id}/UpdateMyPage`;
          } else {
            // 비밀번호 확인 실패: 오류 메시지 표시
            authMessage.innerText = data.message || "비밀번호가 일치하지 않습니다.";
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    });
});
