document.addEventListener("DOMContentLoaded", () => {
    const cp = document.querySelector('#contextPath').value;

	//초기 30분
    let sessionTime = 30 * 60; 
	//세션 만료 상태
    let sessionExpired = false; 
	//세션 연장 알림
    let extendAlertTimer; 
    // 타이머 업데이트 함수
    function updateSessionTimer() {
        const sessionTimeElement = document.getElementById("sessionTime");

        // 남은 시간을 분:초 형식으로 변환
        const minutes = Math.floor(sessionTime / 60);
        const seconds = sessionTime % 60;

        // 화면에 남은 시간 업데이트
        sessionTimeElement.textContent = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

        // 세션 시간이 0이면 로그아웃 처리
        if (sessionTime <= 0) {
            clearInterval(timerInterval); // 타이머 정지
            clearInterval(extendAlertTimer); // 알림 타이머 정지
            sessionExpired = true;

            swal({
                title: "세션이 만료되었습니다",
                text: "다시 로그인해주세요.",
                icon: "warning",
                buttons: "확인",
            }).then((willLogout) => {
                if (willLogout) {
                    window.location.href = cp + "/logout";
                }
            });
            return;
        }

        sessionTime--;
    }

    // 1초마다 타이머 업데이트
    const timerInterval = setInterval(updateSessionTimer, 1000);

    // 세션 연장
    function extendSession() {
        fetch(cp + '/session/extendSession', { method: 'POST' })
            .then((response) => {
                if (response.ok) {
                    sessionTime = 30 * 60; // 세션 타이머를 다시 초기화
                    sessionExpired = false; // 세션 만료 상태 초기화
                    swal({
                        title: "세션이 연장되었습니다",
                        text: "세션이 30분 연장되었습니다.",
                        icon: "success",
                        timer: 2000,
                        buttons: false,
                    });

                    // 세션 연장 알림 타이머 리셋
                    clearInterval(extendAlertTimer);
                    startExtendAlertTimer();
                } else {
                    swal({
                        title: "세션 연장 실패",
                        text: "다시 시도해주세요.",
                        icon: "error",
                        timer: 3000,
                        buttons: false,
                    });
                }
            })
            .catch((error) => {
                console.error("세션 연장 실패:", error);
                swal({
                    title: "네트워크 오류",
                    text: "인터넷 연결을 확인해주세요.",
                    icon: "error",
                    timer: 3000,
                    buttons: false,
                });
            });
    }

    // 세션 연장 알림을 주기적으로 표시하는 함수
    function startExtendAlertTimer() {
        extendAlertTimer = setInterval(() => {
            if (sessionExpired) {
                clearInterval(extendAlertTimer); // 세션이 만료되면 알림 중단
                return;
            }

            swal({
                title: "세션이 곧 만료됩니다",
                text: "세션을 연장하시겠습니까?",
                icon: "warning",
                buttons: ["아니요", "연장"],
            }).then((willExtend) => {
                if (willExtend) {
                    extendSession();
                }
            });
        }, (30-5) * 60 * 1000); // 25분 지나면 세션 연장 알람 (5분 전)
    }

    // 초기 세션 연장 알림 시작
    startExtendAlertTimer();

});
