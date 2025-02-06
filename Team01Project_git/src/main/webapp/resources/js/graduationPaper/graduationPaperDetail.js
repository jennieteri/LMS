document.addEventListener("DOMContentLoaded", () => {
    const applyBtn = document.querySelector('#apply');
    const cp = document.querySelector('#cp').value; // context path
    const gpaCd = document.querySelector('#gpaCd').value; // 논문 번호
    
    applyBtn.addEventListener("click", () => {
        swal({
            title: "승인 처리",
            text: "논문을 승인 처리 하시겠습니까?",
            icon: "warning",
            buttons: ["취소", "저장"]
        }).then((willSave) => {
            if (willSave) {
                // PUT 요청으로 논문 번호 포함
                fetch(`${cp}/graduationpaper/${gpaCd}/update`, {
                    method: "PUT",
                })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("서버 오류 발생");
                    }
                    return response.text();
                })
                .then((data) => {
                    swal({
                        title: "승인 완료",
                        text: "논문이 승인되었습니다.",
                        icon: "success",
                        buttons: false,
                        timer: 2000,
                    }).then(() => {
                        window.location.href = `${cp}/graduationpaper/list/prof`;
                    });
                })
                .catch((error) => {
                    swal({
                        title: "오류 발생",
                        text: "승인 처리 중 문제가 발생했습니다.",
                        icon: "error",
                    });
                    console.error("Error:", error);
                });
            }
        });
    });
});
