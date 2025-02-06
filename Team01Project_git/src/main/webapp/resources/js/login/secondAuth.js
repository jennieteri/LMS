/**
 * 2차 인증 
	타이머 관리 / 인증 요청 
 */

document.addEventListener("DOMContentLoaded", () => {
    const requestAuthCodeBtn = document.getElementById("requestAuthCodeBtn");
    const authSection = document.getElementById("auth-section");
    const contextPath = authSection.dataset.path;

    let timerInterval; // 타이머 변수

    // 타이머 함수
    function startTimer(duration, timerElement) {
        let time = duration;
        timerInterval = setInterval(() => {
            const minutes = Math.floor(time / 60);
            const seconds = time % 60;
            timerElement.textContent = `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
            time--;

            if (time < 0) {
                clearInterval(timerInterval);
                timerElement.textContent = "시간 초과";
                document.getElementById("authCodeInput").disabled = true;
                swal("시간 초과", "인증번호 입력 시간이 초과되었습니다. 다시 요청해주세요.", "error").then(() => {
                    requestAuthCodeBtn.disabled = false; // 재요청 버튼 활성화
                });
            }
        }, 1000);
    }

    // 인증번호 요청 버튼 클릭 이벤트
    requestAuthCodeBtn.addEventListener("click", async () => {
        try {
            const response = await fetch(`${contextPath}/2fa/request`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
            });

            if (response.ok) {
                const data = await response.json();
				//테스트기간동안만 패스처리---------------------------
				if(data.pass){
					window.location.href = `${contextPath}`;
				}
				//-------------------------------------------
                swal("성공", data.message || "저장된 인증수단에 따라 인증번호가 전송되었습니다.", "success");

                // 기존 타이머 제거
                clearInterval(timerInterval);

                // 인증번호 입력 필드와 타이머 추가
                authSection.innerHTML = `
                    <label for="authCodeInput">인증번호 입력:</label>
                    <input type="text" id="authCodeInput" class="form-control mb-2" placeholder="인증번호 5자리를 입력하세요" required>
                    <span class="timer" id="timer">3:00</span>
                    <button type="button" class="btn btn-primary mt-3" id="verifyAuthCodeBtn">인증 확인</button>
                `;

                // 타이머 시작
                const timerElement = document.getElementById("timer");
                startTimer(180, timerElement); // 180초(3분) 타이머

                // 인증 확인 버튼 이벤트 등록
                document.getElementById("verifyAuthCodeBtn").addEventListener("click", verifyAuthCode);

                // 재요청 버튼 비활성화
                requestAuthCodeBtn.disabled = true;
            } else {
                const error = await response.json();
                swal("오류", error.message, "error");
            }
        } catch (error) {
            console.error("서버오류:", error);
            swal("오류", "인증번호 요청 중 문제가 발생했습니다.", "error");
        }
    });

    // 인증번호 확인 요청 함수
    async function verifyAuthCode() {
        const authCode = document.getElementById("authCodeInput").value;

        if (!authCode) {
            swal("오류", "인증번호를 입력해주세요.", "error");
            return;
        }

        try {
            const response = await fetch(`${contextPath}/2fa`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ authCode }),
            });

            const result = await response.json();

            if (response.ok) {
                swal("성공", result.message, "success").then(() => {
                    window.location.href = `${contextPath}/index.do`;
                });
            } else {
                swal("오류", result.message, "error");
            }
        } catch (error) {
            console.error("Failed to verify auth code:", error);
            swal("오류", "인증 확인 중 문제가 발생했습니다.", "error");
        }
    }
});