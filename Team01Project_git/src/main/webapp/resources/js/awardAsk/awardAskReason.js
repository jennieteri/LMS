/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const updateButton = document.getElementById("updateStatusButton");
    const statusSelect = document.getElementById("statusSelect");
    const rejectReasonModal = document.getElementById("rejectReasonModal");
    const saveRejectReasonButton = document.getElementById("saveRejectReasonButton");
    const rejectReasonInput = document.getElementById("rejectReason");

    updateButton.addEventListener("click", function () {
        const selectedStatus = statusSelect.value;

        if (selectedStatus === "A04") { // 반려 상태 선택 시
            $(rejectReasonModal).modal('show'); // 모달창 열기
        } else {
            submitForm(); // 반려 외 상태는 바로 폼 제출
        }
    });

    saveRejectReasonButton.addEventListener("click", function () {
        const rejectReason = rejectReasonInput.value.trim();

        if (!rejectReason) {
            alert("반려 사유를 입력해주세요.");
            return;
        }

        // 반려 사유를 Hidden Input으로 추가
        let hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "shapNoReason";
        hiddenInput.value = rejectReason;
        document.getElementById("updateStatusForm").appendChild(hiddenInput);

        $(rejectReasonModal).modal('hide'); // 모달 닫기
        submitForm(); // 폼 제출
    });

    function submitForm() {
        document.getElementById("updateStatusForm").submit();
    }
});
