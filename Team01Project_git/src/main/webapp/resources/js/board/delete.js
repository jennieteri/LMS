document.addEventListener("DOMContentLoaded", () => {
    const deleteButtons = document.querySelectorAll('.delete-btn');

    // 플래시 메시지를 SweetAlert로 표시
    if (typeof message !== "undefined" && message !== "") {
        swal({
            title: "성공",
            text: message,
            icon: "success",
        });
    }

    if (typeof error !== "undefined" && error !== "") {
        swal({
            title: "오류",
            text: error,
            icon: "error",
        });
    }

    deleteButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            event.preventDefault(); // 기본 동작 방지
            swal({
                title: "삭제 확인",
                text: "정말로 삭제하시겠습니까?",
                icon: "warning",
                buttons: {
                    cancel: "취소",
                    confirm: "삭제"
                },
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    const form = button.closest('form'); // 버튼 주변의 form 찾기
                    if (form) {
                        form.submit(); // 폼 제출
                    } else {
                        console.error("삭제 폼을 찾을 수 없습니다.");
                    }
                }
            });
        });
    });
});
