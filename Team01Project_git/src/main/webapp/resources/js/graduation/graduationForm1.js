document.addEventListener("DOMContentLoaded", () => {
    const volForm = document.getElementById("volForm");
    const submitBtn1 = document.getElementById("submitBtn1");
    const cp = document.getElementById("cp").value;

    if (volForm && submitBtn1 && cp) {
        submitBtn1.addEventListener("click", (event) => {
            event.preventDefault(); // 기본 제출 동작 방지
            console.log("제출 버튼 클릭됨");

            const formData = new FormData(volForm);

            fetch(`${cp}/graduation/create/vol`, {
                method: "POST",
                body: formData,
            })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("네트워크 응답이 실패했습니다.");
                    }
                    return response.json();
                })
                .then((data) => {
                    if (data.success) {
                        swal({
                            title: "등록 성공!",
                            text: data.message || "봉사 점수가 성공적으로 등록되었습니다.",
                            icon: "success",
                            button: "확인",
                        }).then(() => {
                            // 성공 시 리스트로 이동
                            const stuId = formData.get("stuId");
                            window.location.href = `${cp}/graduation/list/${stuId}?tab=volunteer`;
                        });
                    } else {
                        swal({
                            title: "등록 실패",
                            text: data.message || "등록에 실패했습니다. 다시 시도해 주세요.",
                            icon: "error",
                            button: "확인",
                        });
                    }
                })
                .catch((error) => {
                    console.error("오류 발생:", error);
                    swal({
                        title: "오류 발생",
                        text: "요청 처리 중 문제가 발생했습니다. 관리자에게 문의하세요.",
                        icon: "error",
                        button: "확인",
                    });
                });
        });
    } else {
        console.error("필수 요소가 없습니다. volForm, submitBtn1 또는 cp 값을 확인하세요.");
    }
});
