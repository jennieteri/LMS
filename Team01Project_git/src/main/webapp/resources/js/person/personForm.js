document.addEventListener("DOMContentLoaded", () => {
	let cp = document.querySelector("#cp").value;
    const form = document.getElementById("personForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const formData = new FormData(form);
        const person = {};

        // 기본 person 데이터 수집
        formData.forEach((value, key) => {
         	person[key] = value;
        });

		console.log(person)

        // 유형 가져오기
        const userType = document.getElementById("user-type").value;

		console.log(userType)

        if (userType === "professor") {
         	axios.post(`${cp}/person/create/professor`, person)
        	.then(response => {
	            console.log("성공", response.data);
	            swal({
	                title: "추가 완료",
	                text: "사용자가 성공적으로 추가되었습니다.",
	                icon: "success",
	                button: "확인"
	            }).then(() => {
	                // 성공 후 리다이렉트
	                window.location.href = '../person/list';
	            });
	        })
	        .catch(error => {
	            console.error("오류 발생", error);
	            swal({
	                title: "추가 실패",
	                text: error.response?.data?.message || "오류가 발생했습니다. 다시 시도해주세요.",
	                icon: "error",
	                button: "확인"
	            });
	        });
       } else if (userType === "employee") {
         	axios.post(`${cp}/person/create/employee`, person)
	        .then(response => {
	            console.log("성공", response.data);
	            swal({
	                title: "추가 완료",
	                text: "사용자가 성공적으로 추가되었습니다.",
	                icon: "success",
	                button: "확인"
	            }).then(() => {
	                // 성공 후 리다이렉트
	                window.location.href = '../person/list';
	            });
	        })
	        .catch(error => {
	            console.error("오류 발생", error);
	            swal({
	                title: "추가 실패",
	                text: error.response?.data?.message || "오류가 발생했습니다. 다시 시도해주세요.",
	                icon: "error",
	                button: "확인"
	            });
	        });
        } else if (userType === "student") {
           axios.post(`${cp}/person/create/student`, person)
	       .then(response => {
	            console.log("성공", response.data);
	            swal({
	                title: "추가 완료",
	                text: "사용자가 성공적으로 추가되었습니다.",
	                icon: "success",
	                button: "확인"
	            }).then(() => {
	                // 성공 후 리다이렉트
	                window.location.href = '../person/list';
	            });
	        })
	        .catch(error => {
	            console.error("오류 발생", error);
	            swal({
	                title: "추가 실패",
	                text: error.response?.data?.message || "오류가 발생했습니다. 다시 시도해주세요.",
	                icon: "error",
	                button: "확인"
	            });
	        });
        }
        console.log("전송 데이터: ", person);
        // 데이터 전송
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const idInput = document.getElementById("idInput");
    const idFeedback = document.getElementById("idFeedback");

    idInput.addEventListener("input", () => {
        const id = idInput.value.trim();

        if (id.length < 9) { // 9자리까지만 
            idFeedback.style.display = "none";
            return;
        }

        axios.get(`../person/checkId`, { params: { id } })
            .then(response => {
                if (response.data) { 
                    idFeedback.style.display = "none";
                } else { 
                    idFeedback.style.display = "inline";
                    idFeedback.textContent = "이미 사용 중인 아이디입니다.";
                }
            })
            .catch(error => {
                console.error("아이디 중복 확인 오류", error);
                idFeedback.style.display = "inline";
                idFeedback.textContent = "아이디 확인 중 문제가 발생했습니다.";
            });
    });
});

