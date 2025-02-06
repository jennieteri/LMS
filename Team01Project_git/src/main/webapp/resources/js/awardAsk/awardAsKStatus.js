document.addEventListener("DOMContentLoaded", function () {
    const statusSelect = document.getElementById("statusSelect");
    const approvalDateInput = document.getElementById("approvalDateInput");
    const form = document.getElementById("updateStatusForm");

    if (!statusSelect || !approvalDateInput || !form) {
        console.error("필수 요소가 DOM에 없습니다.");
        return;
    }

    // 상태 변경 시 자동으로 처리
    statusSelect.addEventListener("change", function () {
        const selectedStatus = statusSelect.value;

        if (!selectedStatus) {
            alert("상태를 선택하세요.");
            return;
        }

        // 승인 상태(A03) 처리
        if (selectedStatus === "A03") {
            const currentDate = new Date().toISOString().slice(0, 19).replace("T", " ");
            approvalDateInput.value = currentDate; // 현재 날짜 설정
            form.submit(); // 폼 제출
        }
        // 반려 상태(A04) 처리: 모달 창 열기
        else if (selectedStatus === "A04") {
            $(rejectReasonModal).modal("show"); // 모달 표시
        } else {
            // 승인 외 상태 처리: 승인일자 초기화 후 폼 제출
            approvalDateInput.value = "";
            form.submit();
        }
    });

    // 모달에서 "저장" 버튼 클릭 시 반려 사유 처리
    document.getElementById("saveRejectReasonButton").addEventListener("click", function () {
        const rejectReason = document.getElementById("rejectReason").value.trim();  // 수정된 부분

        if (!rejectReason) {
            alert("반려 사유를 입력하세요.");
            return;
        }

        // 숨겨진 필드에 반려 사유 설정
        let rejectReasonHiddenInput = document.getElementById("rejectReasonHiddenInput");
        if (!rejectReasonHiddenInput) {
            rejectReasonHiddenInput = document.createElement("input");
            rejectReasonHiddenInput.type = "hidden";
            rejectReasonHiddenInput.name = "shapNoReason";
            rejectReasonHiddenInput.id = "rejectReasonHiddenInput";
            form.appendChild(rejectReasonHiddenInput);
        }
        rejectReasonHiddenInput.value = rejectReason;

        $(rejectReasonModal).modal("hide"); // 모달 닫기
        form.submit(); // 폼 제출
    });
});
