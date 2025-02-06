/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const streCateCd = document.getElementById("streCateCd").value;
    const userId = document.getElementById("userId").value;
    const cp = document.getElementById("cp").value;

    if (streCateCd === 'SC06') {
        // 신입생이면 개인정보 수정 페이지로 리다이렉트
        swal({
            title: "신입생 확인",
            text: "신입생입니다. 개인정보를 수정해야 합니다.",
            icon: "info",
            button: "확인"
        }).then(() => {
            location.href = `${cp}/mypage/${userId}/UpdateMyPage`;
        });
    }
});
