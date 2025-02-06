/**
 * 
 */
function deleteBoard(snbNo) {
    if (confirm("게시글을 삭제하시겠습니까?")) {
        fetch(contextPath+ 'answer/delete/' + sibNo, {
            method: 'POST',
			headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                swal('삭제 완료',"해당 게시글이 삭제 되었습니다.",'success');
                window.location.href = contextPath+ "answerBoard/answerBoardDetail"; // 목록 페이지로 이동
            } else {
             swal('삭제 실패',"해당 게시글이 삭제되지 않아 다시 시도해주세요.",'warning');
            }
        })
        .catch(error => {
            console.error("Error:", error);
            swal('서버 오류',"다시 시도해주세요.",'warning');
        });
    }
}

