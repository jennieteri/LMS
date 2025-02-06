document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("engForm");
    const submitBtn = document.getElementById("submitBtn");
    const cp = document.getElementById("cp").value;

    submitBtn.addEventListener("click", () => {
        const formData = new FormData(form);

        fetch(cp + "/graduation/create/eng", {
            method: "POST",
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    swal({
                        title: "등록 성공!",
                        text: data.message,
                        icon: "success",
                        button: "확인",
                    }).then(() => {
                        // 성공 시 리스트로 이동
                        window.location.href = cp + `/graduation/list/${formData.get("stuId")}`;
                    });
                } else {
                    swal({
                        title: "등록 실패",
                        text: data.message,
                        icon: "error",
                        button: "확인",
                    });
                }
            })
            .catch(error => {
                swal({
                    title: "오류 발생",
                    text: "요청 처리 중 문제가 발생했습니다.",
                    icon: "error",
                    button: "확인",
                });
            });
    });

});
