function submitEditedReply(sibNo) {
    const replyContent = document.getElementById("adminReply").value.trim();

    if (!replyContent) {
        swal({
            title: "작성 오류",
            text: "답변을 작성해주세요!",
            icon: "warning",
            buttons: true
        });
        return;
    }

    const data = {
        sibNo: document.getElementById("sibNo").value, // sibNo를 HTML에서 가져옴
        sibAns: replyContent
    };

    // 로딩 메시지 표시
    swal({
        title: "수정 중...",
        text: "잠시만 기다려주세요.",
        icon: "info",
        buttons: false,
        closeOnClickOutside: false,
        closeOnEsc: false
    });

    fetch(`${contextPath}/answer/updateReply`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            return response.text().then(errorMessage => {
                throw new Error(errorMessage);
            });
        }
    })
    .then(result => {
        if (result === "success") {
            swal({
                title: "수정 완료",
                text: "답변이 성공적으로 수정되었습니다.",
                icon: "success",
                buttons: true
            }).then(() => {
                location.reload();
            });
        } else {
            swal({
                title: "수정 실패",
                text: "답변 수정에 실패했습니다. 다시 시도해주세요.",
                icon: "error",
                buttons: true
            });
        }
    })
    .catch(error => {
        console.error("수정 오류:", error);
        swal({
            title: "서버 오류",
            text: error.message || "서버와의 통신 중 오류가 발생했습니다.",
            icon: "error",
            buttons: true
        });
    });
}
