document.addEventListener("DOMContentLoaded", function () {
    const statusMap = {
        "요청": "ST01",
        "발급": "ST02",
        "완료": "ST03"
    };

    const statusCodeMap = {
        "ST01": { name: "요청", class: "btn btn-success statusBtn" },
        "ST02": { name: "발급", class: "btn btn-danger statusBtn" },
        "ST03": { name: "완료", class: "btn btn-secondary statusBtn status-complete" }
    };

    function getStatusCode(status) {
        return statusMap[status] || new Error("알 수 없는 상태: " + status);
    }

    function getStatusName(code) {
        return statusCodeMap[code]?.name || "알 수 없음";
    }

    function getButtonClass(code) {
        return statusCodeMap[code]?.class || "btn btn-warning statusBtn";
    }

    // 완료 상태 버튼에 스타일 및 클릭 방지 적용 함수
    function disableCompleteButton(button) {
        button.classList.add("status-complete");
        button.style.backgroundColor = "#6c757d";  // 동일한 회색 색상
        button.style.color = "#fff";
        button.style.cursor = "not-allowed";
        button.setAttribute("disabled", "disabled");
    }

    // 모든 상태 버튼에 이벤트 리스너 추가
    const statusButtons = document.querySelectorAll(".statusBtn");

    statusButtons.forEach(button => {
        const initialStatus = button.textContent.trim();

        // 처음부터 완료 상태인 버튼은 클릭 방지 및 스타일 적용
        if (initialStatus === "완료") {
            disableCompleteButton(button);
        }

        button.addEventListener("click", function () {
            const currentStatus = this.textContent.trim();

            // 완료 상태 버튼은 클릭 방지
            if (currentStatus === "완료") {
                console.log("완료 상태 버튼은 클릭할 수 없습니다.");
                return;
            }

            console.log("버튼 클릭됨:", currentStatus);

            const stuId = this.closest("tr").dataset.stuId;
            const currentStatusCode = getStatusCode(currentStatus);

            // 상태 업데이트 요청
            fetch(`${contextPath}studentCard/updateStatus?timestamp=${new Date().getTime()}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    cocoCd: currentStatusCode,
                    stuId: stuId
                }),
                cache: 'no-cache'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("상태 변경 실패");
                    }
                    return response.json();
                })
                .then(data => {
                    const newStatus = data.status;
                    console.log("서버에서 반환된 상태:", newStatus);

                    // 버튼 상태 및 스타일 즉시 업데이트
                    this.textContent = getStatusName(newStatus);
                    this.className = getButtonClass(newStatus);

                    // 완료 상태일 때 즉시 회색으로 변경하고 클릭 방지
                    if (newStatus === "ST03") {
                        disableCompleteButton(this);
                    }
                })
                .catch(error => {
                    console.error("상태 변경 중 오류 발생:", error);
                    swal("오류", "상태 변경 중 오류 발생", "warning");
                });
        });
    });
});
