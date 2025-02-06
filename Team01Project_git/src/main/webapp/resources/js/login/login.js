document.addEventListener("DOMContentLoaded", function () {
    const exception = document.getElementById("exception").value; // JSP에서 hidden input으로 전달된 값 읽기
    
    if (exception) { // 예외 메시지가 존재할 경우
        swal({
            title: "로그인 실패",
            text: decodeURIComponent(exception), // URI 디코딩
            icon: "error",
            button: "확인"
        });
    }
});

function fnLogin(role) {
    const loginForm = document.forms["loginForm"];
    
    if (role === 'freshman') { // 신입생 처리
        loginForm.id.value = '2024100012'; // 신입생 샘플 ID
        loginForm.pswd.value = 'java'; // 신입생 샘플 비밀번호
    } else {
        loginForm.id.value = `2024${role}00001`; // 다른 사용자 ID 생성 규칙
        loginForm.pswd.value = 'java';
    }

    loginForm.submit(); // 폼 제출
}

