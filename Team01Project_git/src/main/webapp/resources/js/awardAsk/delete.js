document.querySelector('.btn-danger').addEventListener('click', () => {
	
console.log("shapDocNo",shapDocNo);
    const selectedCheckbox = document.querySelector('.check:checked');
    if (!selectedCheckbox) {
        swal("오류", "삭제할 항목을 선택하세요.", "warning");
        return;
    }

    const shapDocNo = selectedCheckbox.value; // 선택된 값 가져오기
    console.log("삭제 요청 shapDocNo:", shapDocNo);

    fetch(`${contextPath}askAward/delete/${shapDocNo}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (response.ok) {
            swal("성공", "삭제되었습니다.", "success").then(() => {
                window.location.reload();
            });
        } else {
            swal("오류", "삭제에 실패했습니다.", "error");
        }
    })
    .catch(error => {
        console.error("삭제 요청 중 오류 발생:", error);
        swal("오류", "서버 오류가 발생했습니다.", "error");
    });
});
