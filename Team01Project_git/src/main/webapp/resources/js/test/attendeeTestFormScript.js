  /**
 * 
 */// 전송을 누르면 데이터를 전송하고 컨트롤러는 값을 등록하고 시험 페이지는 닫혀야함. 씨ㅏ
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("#newQuestion");

    // 제출 이벤트 처리
    form.addEventListener("submit", (e) => {
        e.preventDefault(); // 기본 제출 동작 방지

        // 답안 입력 확인 및 미입력 문제로 이동
        const unansweredQuestion = checkAllQuestionsAnswered();
        if (unansweredQuestion) {
            swal("미입력 답안", `${unansweredQuestion}번 문제의 답안을 입력해주세요.`, "warning")
                .then(() => {
                    scrollToQuestion(`question-${unansweredQuestion}`);
                });
            return;
        }

        // FormData 객체로 데이터 수집
        const formData = new FormData(form);

        // JSON 데이터 배열 생성
        const jsonData = [];
        formData.forEach((value, key) => {
            jsonData.push({
                queNo: key,
                queAnswer: value
            });
        });

        console.log("JSON Data:", jsonData);

        // JSON 데이터 전송
        fetch(form.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(jsonData),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log("Response:", data);
                if (data.result == "ok") {
                    window.close();
                } else {
                    swal("실패", "제출이 실패했습니다.", "error");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
                swal("오류", "서버와의 통신 중 문제가 발생했습니다.", "error");
            });
    });
});

function showTestConfirm() {
    const form = document.querySelector("#newQuestion");

    // SweetAlert confirm 창 띄우기
    swal({
        title: "제출하시겠습니까?",
        text: "제출 시 수정 및 재시험이 불가능합니다.",
        icon: "warning",
        buttons: ["취소", "제출"],
        dangerMode: true,
    }).then((isConfirm) => {
        if (isConfirm) {
            // 확인 버튼 클릭 시 폼 제출
            form.dispatchEvent(new Event("submit"));
        } else {
            // 취소 버튼 클릭 시
            swal("취소되었습니다!", "제출이 취소되었습니다.", "error");
        }
    });
}

function checkAllQuestionsAnswered() {
    const questions = document.querySelectorAll('[id^="question-"]');
    for (let i = 0; i < questions.length; i++) {
        const question = questions[i];
        const questionNumber = i + 1; // 1부터 시작하는 문제 번호
        const inputs = question.querySelectorAll('input[type="radio"], input[type="text"], textarea');
        
        let answered = false;
        for (let input of inputs) {
            if (input.type === 'radio' && input.checked) {
                answered = true;
                break;
            } else if ((input.type === 'text' || input.tagName === 'TEXTAREA') && input.value.trim() !== '') {
                answered = true;
                break;
            }
        }
        
        if (!answered) {
            return questionNumber;
        }
    }
    return null;
}

function scrollToQuestion(questionId) {
    document.getElementById(questionId).scrollIntoView({ behavior: 'smooth' });
}
// 페이지 로드 시 타이머 시작
window.onload = function() {
    // HTML의 hidden input에서 시간 가져오기
    const testDt = document.getElementById("testDt").value;
    const testEt = document.getElementById("testEt").value;

    // 타이머 시작
    startTimer(testDt, testEt);
};

// 타이머 시작 함수
function startTimer(startTime, endTime) {
    const startHour = parseInt(startTime.substring(0, 2), 10);
    const startMinute = parseInt(startTime.substring(2), 10);
    const endHour = parseInt(endTime.substring(0, 2), 10);
    const endMinute = parseInt(endTime.substring(2), 10);

    // 시간 설정
    const dateTime = new Date();
    dateTime.setHours(startHour, startMinute, 0);

    const endDate = new Date();
    endDate.setHours(endHour, endMinute, 0);

    let realTime = endDate - dateTime;

    // 타이머 ID 저장
    let timerId;

    // 타이머 갱신 함수
    function updateTimer() {
        if (realTime <= 0) {
            document.getElementById("timer").textContent = "시간 종료";
            clearInterval(timerId); // 타이머 멈춤
            autoSubmitTest(); // 자동 제출
            return;
        }

        // 남은 시간 계산
        const hours = Math.floor((realTime / (1000 * 60 * 60)) % 24);
        const minutes = Math.floor((realTime / (1000 * 60)) % 60);
        const seconds = Math.floor((realTime / 1000) % 60);

        document.getElementById("timer").textContent =
            `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

        realTime -= 1000;
    }

    // 1초마다 타이머 업데이트
    updateTimer();
    timerId = setInterval(updateTimer, 1000);
}

function forceConfirm() {
    if (confirm("시험을 제출하시겠습니까?")) {
        alert("시험이 제출되었습니다.");
        // 제출 로직 추가
    }
}


function autoSubmitTest() {
    const form = document.querySelector("#newQuestion");
    const formData = new FormData(form);

    // 미입력 값 설정
    const jsonData = [];
    const questions = document.querySelectorAll('[id^="question-"]');
    questions.forEach((question, index) => {
        // 문제 번호 생성 (Q001, Q002 형태)
        const queNo = `Q${String(index + 1).padStart(3, '0')}`;

        const inputs = question.querySelectorAll('input, textarea');
        let answered = false;

        inputs.forEach(input => {
            if ((input.type === 'radio' && input.checked) || (input.value && input.value.trim() !== "")) {
                answered = true;
            }
        });

        // 미입력 시 기본값 설정
        if (!answered) {
            if (inputs[0]?.type === 'radio') {
                // 객관식
                jsonData.push({ queNo: queNo, queAnswer: "5" });
            } else {
                // 주관식 및 서술형
                jsonData.push({ queNo: queNo, queAnswer: "미입력" });
            }
        } else {
            // 입력된 값 추가
            formData.forEach((value, key) => {
                if (key === queNo) {
                    jsonData.push({ queNo: queNo, queAnswer: value });
                }
            });
        }
    });

    // 제출 요청 전송
    fetch(form.action, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(jsonData),
    })
        .then(response => response.json())
        .then(data => {
            if (data.result === "ok") {
                // SweetAlert 메시지 표시 후 창 닫기
                swal("시간 초과", "현재까지 입력한 답안을 등록합니다.", "error")
                    .then(() => {
                        window.close(); // 확인 버튼 클릭 후 창 닫기
                    });
            } else {
                swal("실패", "제출이 실패했습니다.", "error");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            swal("오류", "서버와의 통신 중 문제가 발생했습니다.", "error");
        });
}




function insertAnswer() {
    // 문제 카드를 모두 선택
    const questions = document.querySelectorAll(".question-card");

    questions.forEach((question) => {
        // 문제 번호 가져오기
        const questionId = question.id;

        // 문제 유형을 확인
        const questionType = question.querySelector(".badge").innerText;

        if (questionType === "객관식") {
            // 객관식 답변 자동 선택 (랜덤 선택)
            const radioButtons = question.querySelectorAll('input[type="radio"]');
            if (radioButtons.length > 0) {
                const randomIndex = Math.floor(Math.random() * radioButtons.length);
                radioButtons[randomIndex].checked = true;
            }
        } else if (questionType === "주관식") {
            // 주관식 답변 자동 입력
            const textInput = question.querySelector('input[type="text"]');
            if (textInput) {
                textInput.value = "자동 입력 주관식";
            }
        } else if (questionType === "서술형") {
            // 서술형 답변 자동 입력
            const textarea = question.querySelector("textarea");
            if (textarea) {
                textarea.value = "자동입력 서술형 답변";
            }
        }
    });

}














