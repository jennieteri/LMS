

document.addEventListener("DOMContentLoaded", function() {
    const rows = document.querySelectorAll('.absenceRow');

    rows.forEach(row => {
        const status = row.getAttribute('data-absence-status');
        let bgColor = ''; 
        let lightBgColor = ''; 
        let textColor = ''; 
        let lightTextColor = ''; 
        let iconHtml = ''; 

        // 각 상태에 맞는 색상 설정
        if (status === "승인") {
            bgColor = '#28a745'; 
            lightBgColor = '#A0D4A0'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-check-circle"></i> 승인`; 
        } else if (status === "대기") {
            bgColor = '#007bff'; 
            lightBgColor = '#A0C9F1'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-hourglass"></i> 대기`; 
        } else if (status === "반려") {
            bgColor = '#dc3545'; 
            lightBgColor = '#F2A1A1'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-x-circle"></i> 반려`; 
        } else if (status === "삭제") {
            bgColor = '#6c757d'; 
            lightBgColor = '#B2B8B8'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-trash"></i> 삭제`; 
        }

        // 각 td에 transition 효과 추가
        const tds = row.querySelectorAll('td');
        tds.forEach((td, index) => {
            if (index !== 7) {
                td.style.transition = 'background-color 0.5s ease, color 0.5s ease'; // transition 추가
                td.style.backgroundColor = lightBgColor;
                td.style.color = lightTextColor;
            }
        });

        const targetTd = row.querySelector('td:nth-child(8)');
        targetTd.style.transition = 'background-color 0.5s ease, color 0.5s ease'; // transition 추가
        targetTd.style.backgroundColor = bgColor;
        targetTd.style.color = textColor;
        targetTd.innerHTML = iconHtml;
    });
});






/*

document.addEventListener("DOMContentLoaded", function() {
    const rows = document.querySelectorAll('.absenceRow');

    rows.forEach(row => {
        const status = row.getAttribute('data-absence-status');
        let bgColor = ''; 
        let lightBgColor = ''; 
        let textColor = ''; 
        let lightTextColor = ''; 
        let iconHtml = ''; 

        // 각 상태에 맞는 색상 설정
        if (status === "승인") {
            bgColor = '#28a745'; 
            lightBgColor = '#A0D4A0'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-check-circle"></i> 승인`; 
        } else if (status === "대기") {
            bgColor = '#007bff'; 
            lightBgColor = '#A0C9F1'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-hourglass"></i> 대기`; 
        } else if (status === "반려") {
            bgColor = '#dc3545'; 
            lightBgColor = '#F2A1A1'; 
            textColor = 'white'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-x-circle"></i> 반려`; 
        } else if (status === "삭제") {
            bgColor = '#6c757d'; 
            lightBgColor = '#B2B8B8'; 
            textColor = 'black'; 
            lightTextColor = 'black'; 
            iconHtml = `<i class="bi bi-trash"></i> 삭제`; 
        }

        // 스타일을 즉시 적용
        const tds = row.querySelectorAll('td');
        tds.forEach((td, index) => {
            if (index !== 7) {
                td.style.backgroundColor = lightBgColor;
                td.style.color = lightTextColor;
            }
        });

        const targetTd = row.querySelector('td:nth-child(8)');
        targetTd.style.backgroundColor = bgColor;
        targetTd.style.color = textColor;
        targetTd.innerHTML = iconHtml;
    });
});

*/