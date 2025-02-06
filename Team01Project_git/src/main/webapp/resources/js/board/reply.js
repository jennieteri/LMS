document.addEventListener("DOMContentLoaded", () => {
    const adminReply = document.getElementById("adminReply");
    const submitButton = document.getElementById("submitButton");
    const editButton = document.getElementById("editButton");
    const deleteButton = document.getElementById("deleteButton");
    const replyRow = document.getElementById("replyRow");
	const showReplyFormButton = document.getElementById("showReplyFormButton");

    // 초기 상태 설정
    if (adminReply && adminReply.value.trim() === "") {
        // 답변이 없으면 등록 버튼만 표시
        replyRow.style.display = "none";
        if (submitButton) submitButton.style.display = "block";
        if (editButton) editButton.style.display = "none";
        if (deleteButton) deleteButton.style.display = "none";
    } else {
        // 답변이 있으면 수정 및 삭제 버튼 표시
        replyRow.style.display = "table-row";
        if (submitButton) submitButton.style.display = "none";
        if (editButton) editButton.style.display = "inline-block";
        if (deleteButton) deleteButton.style.display = "inline-block";
		if (showReplyFormButton) showReplyFormButton.style.display = "none";
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const showReplyFormButton = document.getElementById("showReplyFormButton");
    const replyRow = document.getElementById("replyRow");

    // 댓글 달기 버튼 클릭 이벤트
    showReplyFormButton.addEventListener("click", () => {
        // 댓글 입력 폼 표시
        if (replyRow) replyRow.style.display = "table-row";

        // 댓글 달기 버튼 숨기기
        if (showReplyFormButton) showReplyFormButton.style.display = "none";
    });
});

// 공통 함수: SweetAlert 알림 표시
function showAlert(type, title, text) {
    swal(title, text, type);
}
//버튼을 클릭했을 때에 나타나게

// 수정 버튼 클릭 시 텍스트 영역 활성화
function enableEditing() {
    const adminReply = document.getElementById("adminReply");
    const submitButton = document.getElementById("submitButton");
    const editButton = document.getElementById("editButton");
    const deleteButton = document.getElementById("deleteButton");

    // 텍스트 영역 활성화
    if (adminReply) adminReply.disabled = false;

    // 등록 버튼 표시
    if (submitButton) submitButton.style.display = "block";

    // 수정 및 삭제 버튼 숨기기
    if (editButton) editButton.style.display = "none";
    if (deleteButton) deleteButton.style.display = "none";
}

function submitReply() {
    const replyContent = document.getElementById("adminReply").value.trim();

    if (replyContent === "") {
        showAlert('warning', '답변을 작성해주세요!', '작성된 글이 없습니다.');
        return;
    }

    const data = {
        sibNo: document.getElementById("sibNo").value,
        sibAns: replyContent,
    };

    fetch(contextPath + "answer/registerReply", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    })
    .then(response => response.text())
    .then(result => {
        if (result === '"success"') {
            showAlert("success", "답변 작성 완료!", "답변이 정상적으로 작성되었습니다.");
            // UI 상태 업데이트
            document.getElementById("adminReply").disabled = true;
            document.getElementById("submitButton").style.display = "none";
            document.getElementById("editButton").style.display = "inline-block";
            document.getElementById("deleteButton").style.display = "inline-block";
        } else {
            showAlert('warning', '업로드 오류', "답변 등록이 실패되었습니다. 다시 시도해주세요.");
        }
    })
    .catch(error => {
        console.error("등록 오류:", error);
        showAlert('error', '서버 오류', "서버 오류로 인해 다시 시도해주세요.");
    });
}



// 답변 수정 요청
function submitEditedReply(sibNo) {
    const replyContent = document.getElementById("adminReply").value.trim();

    if (!replyContent) {
        showAlert("warning", "작성 오류", "답변을 작성해주세요!");
        return;
    }

    const data = {
        sibNo: sibNo,
        sibAns: replyContent,
    };

    fetch(contextPath + "/answer/updateReply", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    })
    .then(response => response.text())
    .then(result => {
        if (result === "success") {
            showAlert("success", "수정 완료", "답변이 성공적으로 수정되었습니다.");
            updateUIOnEditComplete();
        } else {
            showAlert("warning", "수정 실패", "답변 수정에 실패했습니다. 다시 시도해주세요.");
        }
    })
    .catch(error => {
        console.error("수정 오류:", error);
        showAlert("error", "서버 오류", "수정 중 오류가 발생했습니다. 다시 시도해주세요.");
    });
}

function deleteReply(sibNo) {
    swal({
        title: "정말로 삭제하시겠습니까?",
        text: "삭제된 답변은 복구할 수 없습니다.",
        icon: "warning",
        buttons: ["취소", "삭제"],
        dangerMode: true,
    })
    .then((willDelete) => {
        if (willDelete) {
            // 서버에 삭제 요청
            fetch(contextPath + "answer/deleteReply/" + sibNo, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
            })
            .then(response => {
                if (response.ok) {
                    swal("삭제 완료", "답변이 성공적으로 삭제되었습니다.", "success").then(() => {
                        // UI 초기화
                        const adminReply = document.getElementById("adminReply");
                        const submitButton = document.getElementById("submitButton");
                        const editButton = document.getElementById("editButton");
                        const deleteButton = document.getElementById("deleteButton");
                        const replyRow = document.getElementById("replyRow");

                        if (adminReply) {
                            adminReply.value = "";
                            adminReply.disabled = false;
                            adminReply.placeholder = "답변을 입력하세요";
                        }
                        if (submitButton) submitButton.style.display = "block";
                        if (editButton) editButton.style.display = "none";
                        if (deleteButton) deleteButton.style.display = "none";
                        if (replyRow) replyRow.style.display = "none";
                    });
                } else {
                    swal("삭제 실패", "서버와의 통신 오류로 삭제하지 못했습니다.", "error");
                }
            })
            .catch(error => {
                console.error("삭제 오류:", error);
                swal("삭제 실패", "서버 오류가 발생했습니다.", "error");
            });
        }
    });
}



function updateUIOnDelete() {
    const adminReply = document.getElementById("adminReply");
    const submitButton = document.getElementById("submitButton");
    const editButton = document.getElementById("editButton");
    const deleteButton = document.getElementById("deleteButton");
    const statusElement = document.querySelector(".info");

    if (adminReply) {
        adminReply.value = ""; // 텍스트 비우기
        adminReply.disabled = false; // 입력 가능
        adminReply.placeholder = "답변을 입력하세요";
    }

    if (submitButton) {
        submitButton.style.display = "block"; // 등록 버튼 표시
    } else {
        const newSubmitButton = document.createElement("button");
        newSubmitButton.id = "submitButton";
        newSubmitButton.textContent = "답변 등록";
        newSubmitButton.style = `
            height: 100px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
        `;
        newSubmitButton.onclick = submitReply;

        // 부모 컨테이너에 추가
        const buttonContainer = adminReply.parentElement;
        buttonContainer.appendChild(newSubmitButton);
    }

    if (editButton) editButton.style.display = "none"; // 수정 버튼 숨기기
    if (deleteButton) deleteButton.style.display = "none"; // 삭제 버튼 숨기기
    if (statusElement) statusElement.textContent = "요청"; // 상태 초기화
}


// 이벤트 바인딩
document.addEventListener("DOMContentLoaded", () => {
    const submitButton = document.getElementById("submitButton");
    if (submitButton) {
        submitButton.addEventListener("click", submitReply);
    }

    const deleteButtons = document.querySelectorAll("[data-delete-button]");
    deleteButtons.forEach(button => {
        button.addEventListener("click", () => deleteReply(button.dataset.sibNo));
    });
});

//입략
function updateUIOnReplySubmit() {
    const adminReply = document.getElementById("adminReply");
    const submitButton = document.getElementById("submitButton");
    const editButton = document.getElementById("editButton");
    const deleteButton = document.getElementById("deleteButton");

    // 텍스트 영역 비활성화
    if (adminReply) {
        adminReply.disabled = true;
        adminReply.style.backgroundColor = "#f8f9fa"; // 비활성화 스타일 추가
    }

    // 등록 버튼 숨기기
    if (submitButton) submitButton.style.display = "none";

    // 수정 및 삭제 버튼 표시
    if (editButton) editButton.style.display = "inline-block";
    if (deleteButton) deleteButton.style.display = "inline-block";
}


// 수정 후 UI 업데이트
function updateUIOnEditComplete() {
    const adminReply = document.getElementById("adminReply");
    const submitButton = document.getElementById("submitButton");
    const editButton = document.getElementById("editButton");
    const deleteButton = document.getElementById("deleteButton");

    // 입력창 비활성화
    if (adminReply) adminReply.disabled = true;

    // 등록 버튼 숨기기
    if (submitButton) submitButton.style.display = "none";

    // 수정 및 삭제 버튼 다시 표시
    if (editButton) editButton.style.display = "block";
    if (deleteButton) deleteButton.style.display = "block";
}




// 이벤트 바인딩
document.addEventListener("DOMContentLoaded", () => {
    const submitButton = document.getElementById("submitButton");
    if (submitButton) {
        submitButton.addEventListener("click", () => {
            const sibNo = document.getElementById("sibNo").value;
            submitReply(sibNo);
        });
    }

    const editButton = document.getElementById("editButton");
    if (editButton) {
        editButton.addEventListener("click", enableEditing);
    }
});
