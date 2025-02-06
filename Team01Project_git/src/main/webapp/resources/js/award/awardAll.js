document.addEventListener("DOMContentLoaded", () => {
	
	console.log ("DOMContentLoaded 이벤트 실행됨");
	// 첫 번째 데이터를 자동으로 표시
    const firstButton = document.querySelector(".award-btn");
    if (firstButton) {
        const awardCd = firstButton.getAttribute("data-award-cd");
        const awardType = firstButton.getAttribute("data-award-type");
        const awardNm = firstButton.getAttribute("data-award-nm");
        const awardEdnstNm = firstButton.getAttribute("data-award-ednst-nm");
        const awardGiveAmt = firstButton.getAttribute("data-award-give-amt");
        const awardDetail = firstButton.getAttribute("data-award-detail");
        const awardBenefit = firstButton.getAttribute("data-award-benefit");
        const awardDocument = firstButton.getAttribute("data-award-document");

        // 첫 번째 데이터를 showContent 함수로 전달
        showContent({
            awardCd,
            awardType,
            awardNm,
            awardEdnstNm,
            awardGiveAmt,
            awardDetail,
            awardBenefit,
            awardDocument,
        });
    }
	
    // 등록 버튼
    document.getElementById("register-btn").addEventListener("click", () => {
        window.location.href = `${contextPath}award/insertAward/new`;
    });

    // 수정 버튼
    document.getElementById("edit-btn").addEventListener("click", () => {
        const selectedAward = getSelectedAward(); // 선택된 장학금 가져오기
        if (selectedAward) {
            window.location.href = `${contextPath}award/${selectedAward}/edit`; // 수정 URL로 이동
        }
    });

    // 삭제 버튼
    document.getElementById("delete-btn").addEventListener("click", () => {
        const selectedAward = getSelectedAward(); // 선택된 장학금 가져오기
        if (selectedAward) {
            if (confirm("해당 데이터를 삭제하시겠습니까?")) {
                fetch(`${contextPath}award/delete/${selectedAward}`, {
                    method: "POST", // POST 방식으로 삭제 요청
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => {
                        if (response.ok) {
                            alert("삭제되었습니다.");
                            location.reload(); // 페이지 새로고침
                        } else {
                            alert("삭제에 실패했습니다.");
                        }
                    })
                    .catch((error) => console.error("삭제 요청 중 오류:", error));
            }
        }
    });

    // 각 버튼 클릭 시 데이터를 표시
    const awardButtons = document.querySelectorAll(".award-btn");
    awardButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const awardCd = button.getAttribute("data-award-cd");
            const awardType = button.getAttribute("data-award-type");
            const awardNm = button.getAttribute("data-award-nm");
            const awardEdnstNm = button.getAttribute("data-award-ednst-nm");
            const awardGiveAmt = button.getAttribute("data-award-give-amt");
            const awardDetail = button.getAttribute("data-award-detail");
            const awardBenefit = button.getAttribute("data-award-benefit");
            const awardDocument = button.getAttribute("data-award-document");

            showContent({
                awardCd,
                awardType,
                awardNm,
                awardEdnstNm,
                awardGiveAmt,
                awardDetail,
                awardBenefit,
                awardDocument,
            });
        });
    });
});

function showContent(data) {
    const resultBody = document.getElementById("resultBody");
    resultBody.innerHTML = "";

    // 한글 라벨 매핑
    const labels = {
        awardCd: "장학금 코드",
        awardType: "장학금 유형",
        awardNm: "장학금 이름",
        awardEdnstNm: "지급 기관명",
        awardGiveAmt: "장학금 금액",
        awardDetail: "장학금 설명",
        awardBenefit: "장학 혜택",
        awardDocument: "제출 서류"
    };

    Object.entries(data).forEach(([key, value]) => {
        const row = document.createElement("tr");

        const cellLabel = document.createElement("td");
        cellLabel.textContent = labels[key] || key; // 라벨이 없으면 기본 key 출력

        const cellValue = document.createElement("td");
         // 장학금 금액에 대해 포맷 처리
        if (key === "awardGiveAmt" && value) {
            // 숫자 포맷 처리 후 '원' 추가
            const formattedValue = parseInt(value, 10).toLocaleString("ko-KR") + "원";
            cellValue.textContent = formattedValue;
        } else {
            cellValue.textContent = value || "";
        }

        row.appendChild(cellLabel);
        row.appendChild(cellValue);

        resultBody.appendChild(row);
    });
}

function getSelectedAward() {
    const resultBody = document.getElementById("resultBody");
    const rows = resultBody.querySelectorAll("tr");
    
    if (rows.length === 0) {
        alert("선택된 장학금이 없습니다.");
        return null;
    }

    const awardCdRow = Array.from(rows).find(row => 
        row.querySelector("td:first-child")?.textContent === "장학금 코드"
    );

    if (!awardCdRow) {
        alert("장학금 코드를 찾을 수 없습니다.");
        return null;
    }

    const awardCd = awardCdRow.querySelector("td:nth-child(2)").textContent.trim(); // 장학금 코드 가져오기
    return awardCd;
}
