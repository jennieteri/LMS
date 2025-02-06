function deleteReply(sibNo) {
    if (confirm("정말로 답변을 삭제하시겠습니까?")) {
        fetch(contextPath + "/answer/deleteReply/" + sibNo, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        })
        .then(response => {
            if (response.ok) {
                swal("삭제 완료", "답변이 성공적으로 삭제되었습니다.", "success").then(() => {
                    // 답변 삭제 후 UI 복원
                    const adminReply = document.getElementById("adminReply");
                    const submitButton = document.getElementById("submitButton");
                    const editButton = document.getElementById("editButton");
                    const deleteButton = document.getElementById("deleteButton");
                    const statusElement = document.querySelector(".info");

                    // 답변 텍스트 영역 초기화
                    if (adminReply) {
                        adminReply.value = ""; // 텍스트 비움
                        adminReply.disabled = false; // 다시 입력 가능
                        adminReply.placeholder = "답변을 입력하세요";
                    }

                    // 등록 버튼 표시
                    if (submitButton) {
                        submitButton.style.display = "block";
                    } else {
                        // 등록 버튼이 없으면 새로 생성
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

                        // 버튼 추가
                        const buttonContainer = adminReply.parentElement;
                        buttonContainer.appendChild(newSubmitButton);
                    }

                    // 수정 및 삭제 버튼 숨기기
                    if (editButton) editButton.style.display = "none";
                    if (deleteButton) deleteButton.style.display = "none";

                    // 상태를 요청으로 변경
                    if (statusElement) {
                        statusElement.textContent = "요청";
                    }
                });
            } else {
                swal("삭제 실패", "답변 삭제 중 문제가 발생했습니다.", "warning");
            }
        })
        .catch(error => {
            console.error("삭제 오류:", error);
            swal("삭제 실패", "서버 오류가 발생했습니다. 다시 시도해주세요.", "error");
        });
    }
}