function ran() {
    const groups = document.querySelectorAll('tbody tr');
    groups.forEach(group => {
        const radios = group.querySelectorAll('input[type="radio"]');
        if (radios.length === 0) return; 

        const randomIndex = Math.floor(Math.random() * radios.length);

        radios.forEach(radio => {
            radio.checked = false; 
        });

        radios[randomIndex].checked = true; 
    });

    
}


function submitTest() {
    // 유형별 점수 초기화
    let scores = {
        JT04: 0, // 진취형
        JT03: 0, // 탐구형
        JT02: 0, // 예술형
        JT06: 0, // 사회형
        JT05: 0, // 현장형
        JT01: 0, // 관습형
        JT07: 0  
    };

    // 모든 선택된 라디오 버튼을 가져오기
    const selectedRadios = document.querySelectorAll('input[type="radio"]:checked');

    // 선택된 라디오 버튼으로 점수 계산
    selectedRadios.forEach(radio => {
    
        const type = radio.dataset.type; // 유형
        const value = parseInt(radio.value, 10);
        scores[type] += value; // 점수 누적
console.log(scores[type]);
    });

    const cp = document.getElementById("cp").value;
    const userId = document.getElementById("testResults").value;

    const data = {
        stuId: userId,
        jtrRea: scores['JT04'], // 진취형
        jtrInq: scores['JT03'], // 탐구형
        jtrArt: scores['JT02'], // 예술형
        jtrSoc: scores['JT06'], // 사회형
        jtrEnt: scores['JT05'], // 현장형
        jtrCon: scores['JT01']  // 관습형
    };

    console.log("Calculated Scores:", scores);
    console.log("Submitting Data:", data);

    // 데이터 전송
    fetch(`${cp}/jobtest/submit`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(responseData => {
        console.log(responseData);
        if (responseData.status === "success") {
            swal({
                title: "등록 완료",
                text: "응시가 성공적으로 등록되었습니다.",
                icon: "success",
                button: "확인"
            }).then(() => {
                window.location.href = `${cp}/jobtestresult/result/${userId}`;
            });
        } else {
            swal({
                title: "등록 실패",
                text: "응시 등록 중 오류가 발생했습니다. 다시 시도해주세요.",
                icon: "error",
                button: "확인"
            });
        }
    })
    .catch(error => {
        console.error("Error:", error);
        swal({
            title: "오류 발생",
            text: "응시 등록 중 문제가 발생했습니다.",
            icon: "error",
            button: "확인"
        });
    });
}
