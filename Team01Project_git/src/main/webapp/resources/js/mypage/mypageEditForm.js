 document.addEventListener("DOMContentLoaded", function () {
        // 폼 제출 시 비밀번호 확인
        document.querySelector("form").addEventListener("submit", function (e) {
            const password = document.getElementById("pswd").value;
            const confirmPassword = document.getElementById("confirmPswd").value;

            if (password !== confirmPassword) {
                alert('비밀번호가 일치하지 않습니다.');
                e.preventDefault(); // 폼 제출 중단
            }
        });

        // 프로필 사진 미리보기
        document.getElementById("profileImage").addEventListener("change", function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const preview = document.getElementById("profilePreview");
                    if (preview) {
                        preview.src = e.target.result;
                    } else {
                        console.error("profilePreview를 찾을 수 없습니다.");
                    }
                };
                reader.readAsDataURL(file);
            }
        });

        // 프로필 사진 제거
        document.querySelector(".btn-danger").addEventListener("click", function () {
            const preview = document.getElementById("profilePreview");
            if (preview) {
                preview.src = `${contextPath}/resources/NiceAdmin/assets/img/user.png`;
            } else {
                console.error("Element with ID 'profilePreview' not found.");
            }
            const fileInput = document.getElementById("profileImage");
            if (fileInput) {
                fileInput.value = ""; // 파일 선택 초기화
            } else {
                console.error("profileImage를 찾을 수 없습니다.");
            }
        });
    });