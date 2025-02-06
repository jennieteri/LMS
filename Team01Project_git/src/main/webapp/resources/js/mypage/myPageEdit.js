document.addEventListener("DOMContentLoaded", function () {
	
  const submitButton = document.getElementById("authSubmit");
  console.log("Button:", submitButton); // 버튼이 제대로 로드되었는지 확인

  if (submitButton) {
    submitButton.addEventListener("click", function () {
      const pswd = document.getElementById("pswd").value; // 비밀번호 입력값
      const id = document.getElementById("id").value; // 사용자 ID

      console.log("Password:", pswd); // 비밀번호 확인
      console.log("ID:", id); // 사용자 ID 확인

      // 서버로 비밀번호 전송
      fetch(`${contextPath}/mypage/${id}/auth`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ pswd: pswd }),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Response:", data); // 서버 응답 확인
          if (data.success) {
            // 비밀번호 확인 성공: 수정 페이지로 이동
            window.location.href = `${contextPath}/mypage/${id}/UpdateMyPage`;
          } else {
            // 비밀번호 확인 실패: 오류 메시지 표시
            document.getElementById("authMessage").innerText = data.message;
          }
        })
        .catch((error) => {
          console.errRor("Error:", error);
          document.getElementById("authMessage").innerText = data.message || "알 수 없는 오류가 발생했습니다.";

        });
    });
  } else {
    console.error("Submit button not found!");
  }
});
