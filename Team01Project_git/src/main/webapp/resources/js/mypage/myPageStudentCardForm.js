document.getElementById('confirmApplication').addEventListener('click', function () {
    swal({
        title: "학생증 신청",
        text: "학생증을 신청하시겠습니까?",
        icon: "info",
        buttons: ["취소", "확인"],
        dangerMode: true,
    }).then((willSubmit) => {
        if (willSubmit) {
            // 서버에 신청 요청 보내기
            fetch(`${contextPath}/studentCard/apply`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    studentId: studentData.id,
                    name: studentData.name,
                    brdt: studentData.brdt
                })
            })
            .then(response => {
                if (!response.ok) { // 응답 상태 확인
                    return response.text().then((errorMessage) => {
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(data => {
                swal({
                    title: "신청 완료",
                    text: "학생증 신청이 완료되었습니다.",
                    icon: "success",
                    button: "확인"
                }).then(() => {
                    location.reload(); // 페이지 새로고침
                });
            })
            .catch(error => {
                // 에러 메시지 표시
                swal({
                    title: "신청 실패",
                    text: error.message, // 백엔드에서 전달된 에러 메시지
                    icon: "error",
                    button: "확인"
                });
            });
        }
    });
});
