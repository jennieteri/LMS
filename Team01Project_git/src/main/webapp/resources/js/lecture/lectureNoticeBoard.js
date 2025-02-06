

document.addEventListener("DOMContentLoaded", function() {
    // 테이블 행을 가져옵니다.
    const rows = document.querySelectorAll(".main-notice-row");

    rows.forEach(function(row) {
        // 먼저 transition을 적용한 후 배경색을 설정하여 서서히 변화하도록 합니다.
        row.style.transition = "background-color 0.5s ease"; // 배경색이 서서히 변하도록 transition 설정
        row.style.borderTop = "1px solid #ccc"; // 위쪽 선
        row.style.borderBottom = "1px solid #ccc"; // 아래쪽 선

        // 각 행의 모든 td에 스타일을 적용합니다.
        const tds = row.querySelectorAll("td"); // 각 행에서 모든 td를 찾습니다.

        tds.forEach(function(td) {
            // td의 배경색을 더 진한 회색으로 설정합니다.
            td.style.transition = "background-color 0.5s ease"; // td 배경색에 transition을 추가
            td.style.backgroundColor = "#e0e0e0"; // td 배경색을 더 진한 회색으로 설정

            // 링크가 있는 td를 제외한 나머지 td에는 텍스트 색상과 배경색을 적용합니다.
            if (td.querySelector("a") === null) {  // td 안에 <a>가 없을 경우
                td.style.color = "#000"; // 텍스트 색상을 검정색으로 변경
            } else {
                // <a> 태그가 있는 경우, <a>에 대한 스타일을 추가
                const link = td.querySelector("a");
                link.style.display = "block"; // <a> 태그가 td 크기를 차지하게 설정
                link.style.width = "100%"; // <a> 태그가 td 전체 너비를 차지하도록 설정
                
                // <a> 태그의 텍스트 색상을 검정색으로 변경
                link.style.color = "#000"; // 검정색으로 텍스트 색상을 변경
                link.style.textDecoration = "none"; // 기본적으로 밑줄 제거

                // 마우스 오버 시 스타일을 추가
                link.addEventListener("mouseover", function() {
                    link.style.textDecoration = "underline"; // 마우스 올렸을 때 밑줄 표시
                });

                // 마우스 아웃 시 밑줄 제거
                link.addEventListener("mouseout", function() {
                    link.style.textDecoration = "none"; // 마우스를 떼면 밑줄 제거
                });
            }
        });

        // 일정 시간 후 배경색을 변경하여 서서히 보이도록 설정
        setTimeout(function() {
            row.style.backgroundColor = "#b0b0b0"; // 배경색을 연한 회색으로 변경
        }, 100); // 100ms 후 배경색이 서서히 변경되도록 설정
    });
});

// 일반 공지사항 (밑줄 강조)

document.addEventListener("DOMContentLoaded", function() {
    // 테이블 행을 가져옵니다.
    const rows = document.querySelectorAll(".normal-notice-row");

    rows.forEach(function(row) {
        // 각 행의 모든 a 태그에 스타일을 적용합니다.
        const links = row.querySelectorAll("a"); // 해당 행의 모든 a 태그를 찾습니다.

        links.forEach(function(link) {
            // 기본적으로 a 태그에는 밑줄이 없도록 설정
            link.style.textDecoration = "none"; // 기본적으로 밑줄 제거

            // 마우스 오버 시 스타일을 추가
            link.addEventListener("mouseover", function() {
                link.style.textDecoration = "underline"; // 마우스 올렸을 때 밑줄 표시
            });

            // 마우스 아웃 시 밑줄 제거
            link.addEventListener("mouseout", function() {
                link.style.textDecoration = "none"; // 마우스를 떼면 밑줄 제거
            });
        });
    });
});
