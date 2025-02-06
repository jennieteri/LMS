/**
 * 스크랩처리 - mycalendar 연동
 */


document.addEventListener("DOMContentLoaded", function () {
    const favoriteIcon = document.getElementById("favoriteIcon");
    const contextPath = document.querySelector('#form-table').dataset.path;
	const methodYn = favoriteIcon.dataset.yn;
	const urlAttirbute = favoriteIcon.dataset.url;
	
	
    // 초기 상태 설정
    let isFavorite = false;

	if(methodYn == 'Y'){
		isFavorite = true;
	}

    favoriteIcon.addEventListener("click", async function () {
        let boardNo = favoriteIcon.dataset.bono;
		let createUrl =`${contextPath}/mycalendar/job/${boardNo}`;
		let deletUrl =`${contextPath}/mycalendar/job`;
		if(urlAttirbute == 'noti'){
			createUrl = `${contextPath}/mycalendar/noti/${boardNo}`;
			deletUrl = `${contextPath}/mycalendar/noti`;
		}
		
        // 게시글 번호가 없을 경우 예외 처리
        if (!boardNo) {
            swal("오류", "게시글 번호가 누락되었습니다.", "error");
            return;
        }

        try {
            // 연동 또는 연동 해제 요청 URL 및 메서드 설정
            const url = isFavorite
                ? `${createUrl}` // 삭제 요청 URL
                : `${deletUrl}`; // 생성 요청 URL
            const method = isFavorite ? "DELETE" : "POST";

            // 서버 요청 전송
            const response = await fetch(url, {
                method: method,
                headers: {
                    "Content-Type": "application/json",
                },
                body: !isFavorite ? JSON.stringify({ boardNo: boardNo }) : null, // DELETE는 body 없음
            });

            const result = await response.json();

            if (response.ok) {
                if (isFavorite) {
                    // 연동 해제 처리
                    swal("성공", result.message || "마이캘린더에서 연동이 삭제되었습니다.", "success");
                    favoriteIcon.classList.remove("bi-calendar-check-fill");
                    favoriteIcon.classList.add("bi-calendar-check");
                } else {
                    // 연동 처리
                    swal("성공", result.message || "마이캘린더에 연동되었습니다.", "success");
                    favoriteIcon.classList.remove("bi-calendar-check");
                    favoriteIcon.classList.add("bi-calendar-check-fill");
                }

                // 상태 토글
                isFavorite = !isFavorite;
            } else {
                throw new Error(result.message || "처리에 실패했습니다.");
            }
        } catch (error) {
            console.error("Failed to toggle calendar link:", error);
            swal("오류", "캘린더 연동 처리 중 문제가 발생했습니다.", "error");
        }
    });
});