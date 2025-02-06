function filter(role, filters) {
    const url = `${contextPath}/role/personFilter`;

    fetch(url, {
        method: "POST",
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify({ role, ...filters }), // filters 매개변수 사용
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("데이터를 가져오는데 실패");
            }
            return response.json();
        })
        .then((data) => {
            updateResultTable(data);
        })
        .catch((error) => {
            console.error("Error:", error);
        });
}

function updateResultTable(data) {
    const resultSection = document.getElementById("resultSection");
    const resultTableBody = document.getElementById("result");

    resultTableBody.innerHTML = "";

    if (!data || data.length === 0) {
        resultTableBody.innerHTML = `<tr><td colspan="3">결과가 없습니다.</td></tr>`;
    } else {
        data.forEach((item) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${item.nm || "N/A"}</td>
                <td>${item.gradeCd || item.empDept || item.deptNo || "N/A"}</td>
                <td>${item.deptCd || item.deptNo || "N/A"}</td>
            `;
            resultTableBody.appendChild(row);
        });
    }

    resultSection.style.display = "block"; // 결과 섹션 표시
}

function onRoleChange() {
    const roleSelect = document.getElementById("roleSelect").value;

    // 섹션들을 가져오기
    const studentSection = document.getElementById("studentSection");
    const professorSection = document.getElementById("professorSection");
    const staffSection = document.getElementById("staffSection");
    const studentDeptSection = document.getElementById("studentDeptSection");
    const resultSection = document.getElementById("resultSection");

    // 초기화: 모든 섹션 숨김
    studentSection.style.display = "none";
    professorSection.style.display = "none";
    staffSection.style.display = "none";
    studentDeptSection.style.display = "none";
    resultSection.style.display = "none";

    // 선택한 역할에 따라 섹션 표시
    if (roleSelect === "학생") {
        studentSection.style.display = "block";
        document.getElementById("studentGradeSelect").value = ""; // 학년 초기화
        studentDeptSection.style.display = "none";
    } else if (roleSelect === "교수") {
        professorSection.style.display = "block"; // 교수 섹션 표시
    } else if (roleSelect === "교직원") {
        staffSection.style.display = "block"; // 교직원 섹션 표시
    }
}

function onStudentGradeChange() {
    const studentGradeSelect = document.getElementById("studentGradeSelect").value;
    const studentDeptSection = document.getElementById("studentDeptSection");

    if (studentGradeSelect === "1학년" || studentGradeSelect === "2학년") {
        studentDeptSection.style.display = "block";
    } else {
        studentDeptSection.style.display = "none";
    }
}

function onStudentDeptChange() {
    const gradeCd = document.getElementById("studentGradeSelect").value;
    const deptCd = document.getElementById("studentDeptSelect").value;

    if (gradeCd && deptCd) {
        filter("학생", { gradeCd, deptCd }); // role과 함께 필터 전달
    }
}

// 이벤트 리스너 등록
document.getElementById("roleSelect").addEventListener("change", onRoleChange);
document.getElementById("studentGradeSelect").addEventListener("change", onStudentGradeChange);
document.getElementById("studentDeptSelect").addEventListener("change", onStudentDeptChange);
