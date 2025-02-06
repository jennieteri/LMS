document.addEventListener("DOMContentLoaded", () => {
    console.log("DOMContentLoaded 실행됨");

    // 모든 수정 버튼 가져오기
    const updateButtons = document.querySelectorAll(".update-btn");

    // 각 수정 버튼에 이벤트 리스너 추가
    updateButtons.forEach((button) => {
        button.addEventListener("click", (event) => {
            event.preventDefault();

            // 클릭된 버튼에서 shapDocNo 가져오기
            const shapDocNo = button.getAttribute("data-doc-no");
            console.log("선택된 shapDocNo:", shapDocNo);

            if (!shapDocNo) {
                swal("오류", "수정할 장학금을 선택하세요.", "warning");
                return;
            }

            // 수정 화면으로 이동
            const editUrl = `${contextPath}askAward/${shapDocNo}/student/edit`;
            console.log("수정 URL로 이동:", editUrl);

            window.location.href = editUrl;
        });
    });
});
