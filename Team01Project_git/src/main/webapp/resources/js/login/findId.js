document.addEventListener("DOMContentLoaded", function () {
    const findButton = document.getElementById("findButton");
    const result = document.getElementById("result");
    const cp = document.getElementById("cp").value;

    findButton.addEventListener("click", function () {
        const nm = document.getElementById("nm").value.trim();
        const brdt = document.getElementById("brdt").value.trim();

        if (!nm) {
            result.value = "이름을 입력하세요.";
            return;
        }
        if (!brdt) {
            result.value = "생년월일을 입력하세요.";
            return;
        }

        const url = `${cp}/findLogin?nm=${encodeURIComponent(nm)}&brdt=${encodeURIComponent(brdt)}`;

        fetch(url, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("서버 오류 발생");
                }
            })
            .then((data) => {
                if (data.id) {
                    result.value = `${nm}님의 학번은\n${data.id} 입니다.`;
                } else {
                    result.value = `${nm}님의 학번을 찾을 수 없습니다.`;
                }
            })
            .catch((error) => {
                result.value = `${nm}님의 학번을 찾을 수 없습니다.`;
                console.error(error);
            });
    });
});
