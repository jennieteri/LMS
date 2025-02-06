/**
 * 
 */

const id = document.getElementById("id").value;
const contextPath = document.getElementById("contextPath").value;
const baseUrl = `${contextPath}/attendCoeva`;
document.addEventListener('DOMContentLoaded', async function() {
	await getSemesterList();
});

async function getSemesterList() {
	try {
		const response = await fetch(`${baseUrl}/${id}`);
		const data = await response.json();
		const semesterList = data.lectList;

		const selectElement = document.querySelector('.form-select');
		selectElement.innerHTML = '';

		semesterList.forEach(semester => {
			const option = document.createElement('option');
			option.value = semester.semstrNo;
			option.textContent = `${semester.year}-${semester.semester}`;
			selectElement.appendChild(option);
		});

		const semBox = document.getElementById('semBox');
		if (semBox) {
			semBox.addEventListener('change', async (e) => {
				const semVal = e.target.value;
				await getAttendCoevaList(semVal);
			});
			semBox.dispatchEvent(new Event('change'));
		}

		return semesterList;
	} catch (error) {
		console.error('Error:', error);
	}
}

async function getAttendCoevaList(semVal) {
    try {
        const response = await fetch(`${baseUrl}/${id}/${semVal}`);
        const data = await response.json();
        const semesterLectureList = data.semesterLectureList;

        const tableBody = document.querySelector('.table-responsive tbody');
        tableBody.innerHTML = '';

        let totalScore = 0;
        let totalCredits = 0;
        let allEvaluated = true; // 모든 강의평가 완료 여부 확인

        semesterLectureList.forEach((list) => {
            let engScore = list.attenScore;
            if (engScore >= 4.3) engScore = 'A+';
            else if (engScore >= 4.0) engScore = 'A';
            else if (engScore >= 3.7) engScore = 'B+';
            else if (engScore >= 3.3) engScore = 'B';
            else if (engScore >= 3.0) engScore = 'C+';
            else if (engScore >= 2.7) engScore = 'C';
            else if (engScore >= 2.3) engScore = 'D+';
            else if (engScore >= 2.0) engScore = 'D';
            else engScore = 'F';

            // 강의 평가가 완료되지 않은 경우 체크
            if (!list.attenCoeva) {
                allEvaluated = false;
            }

            // 총 평점 계산을 위해 숫자 점수 사용
            totalScore += list.attenScore * list.lectureVO.lectScore;
            totalCredits += list.lectureVO.lectScore;
            const row = `
                <tr>
                    <td class="text-center table-light align-middle">${list.lectureVO.semesterVO.year || ''}</td>
                    <td class="text-center table-light align-middle">${list.lectureVO.semesterVO.semester || ''}</td>
                    <td class="text-center table-light align-middle">${list.lectureVO.subjectVO.subFicdCdNm || ''}</td>
                    <td class="text-center table-light align-middle">${list.lectureVO.lectNm || ''}</td>
                    <td class="text-center table-light align-middle">${list.lectureVO.lectScore || ''}</td>
                    <td class="text-center table-light align-middle">
                        ${list.attenCoeva ? '<button class="btn btn-outline-primary">평가 완료 <i class="bi bi-check2-circle"></i></button>' :
                            `<button class="btn btn-primary" onclick="getDetail('${list.stuId}', '${list.lectureVO.lectNo}', '${list.lectureVO.semesterVO.semstrNo}')">등록</button>`}
                    </td>
                    <td class="text-center table-light align-middle">${list.attenCoeva ? engScore : '평가 미등록'}</td>
                    <td class="text-center table-light align-middle">
                        ${list.attenCoeva ? `<button class="btn btn-primary" onclick="getDissentPage('${list.stuId}', '${list.lectureVO.lectNo}', '${list.lectureVO.semesterVO.semstrNo}')"> 이의신청 <i class="bi bi-file-earmark-text"></i></button>` : '평가 미등록'}
                    </td>
                </tr>
            `;
            tableBody.insertAdjacentHTML('beforeend', row);
        });

        // 평가 완료 여부에 따라 GPA 또는 미등록 표시
        const semScoreDiv = document.getElementById('semScoreDiv');
        if (allEvaluated) {
            const gpa = (totalScore / totalCredits).toFixed(2);
            semScoreDiv.innerHTML = `<strong>평균 학점 :</strong> ${gpa} |  <strong> 이수 학점 :</strong> ${totalCredits}`;
        } else {
            semScoreDiv.innerHTML = `<strong>평균 학점 :</strong> 평가 미완료   | <strong> 이수 학점 :</strong> 평가 미완료 `;
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

//현재 성적을 등록하는 곳이 없음. 이의신청 기간이 끝나고 나면 성적을 조회할 수 있도록?
async function getDetail(stuId, lectNo, semstrNo) {
	// 모달 요소를 가져옵니다.
	const modal = new bootstrap.Modal(document.getElementById('detailModal'));
	const lectureInfo = document.querySelector('.modal-body .border .row');
	const applicationContent = document.querySelector('.modal-body .mb-3:nth-child(2) .border p');

	try {
		// 서버에서 상세 정보를 가져옵니다.
		const response = await fetch(`${baseUrl}/${stuId}/${lectNo}/${semstrNo}`);
		const data = await response.json();
		const detail = data.semesterLectureDetail;

		//console.log(detail)
		//const notNullList = removeNullProperties(detail);
		//console.log(notNullList)

		// 모달 내용을 업데이트합니다.
		lectureInfo.innerHTML = `
    <div class="col-md-6">
        <p class="mb-1"><strong>년도-학기</strong>: &nbsp;${detail.lectureVO.semesterVO.year} - ${detail.lectureVO.semesterVO.semester}</p>
        <p class="mb-1"><strong>학년</strong>: &nbsp;${detail.lectureVO.subjectVO.gradeCdNm}</p>               
    </div>
    <div class="col-md-6">
        <p class="mb-1"><strong>교수명</strong>: &nbsp;${detail.lectureVO.professorVO.nm}</p>
        <p class="mb-1"><strong>강의명</strong>: &nbsp;${detail.lectureVO.lectNm}</p>
    </div>
    <div class="col-md-6">
        <p class="mb-1"><strong>구분</strong>: &nbsp;${detail.lectureVO.subjectVO.subFicdCdNm}</p>
    </div>
    <div class="col-md-6">
        <p class="mb-1"><strong>학점</strong>: &nbsp;${detail.lectureVO.lectScore} 학점</p>
    </div>
    <input type="hidden" id="stuId" name="stuId" value="${stuId}">
    <input type="hidden" id="lectNo" name="lectNo" value="${lectNo}">
    <input type="hidden" id="semstrNo" name="semstrNo" value="${semstrNo}">
`;


		//   applicationContent.innerHTML = data.applicationContent || 's내용 없음';

		// 모달을 표시합니다.
		modal.show();
	} catch (error) {
		console.error('Error fetching detail:', error);
		alert('상세 정보를 불러오는 데 실패했습니다.');
	}

}

async function saveEvaluation() {
	const stuId = document.getElementById('stuId').value;
	const lectNo = document.getElementById('lectNo').value;
	const semstrNo = document.getElementById('semstrNo').value;
	const coevaBox = document.getElementById('coevaBox').value;

	if (!coevaBox.trim()) {
		swal({
			title: "평가 내용이 없습니다!",
			text: "평가 내용을 입력하세요.",
			icon: "warning",
			button: "확인"
		});
		return;
	}

	try {
		const response = await fetch(baseUrl, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				stuId: stuId,
				lectNo: lectNo,
				attenCoeva: coevaBox,
			}),
		});

		const responseData = await response.json();

		if (response.ok) {
			swal({
				title: "강의 평가",
				text: responseData.message,
				icon: "success",
				button: "확인"
			}).then(() => {
				document.getElementById('coevaBox').value = '';
				const modal = bootstrap.Modal.getInstance(document.getElementById('detailModal'));
				getAttendCoevaList(semstrNo);
				modal.hide();
			});
		} else {
			swal({
				title: "강의 평가",
				text: responseData.message,
				icon: "error",
				button: "확인"
			});
		}
	} catch (error) {
		console.error('Error saving evaluation:', error);
		swal({
			title: "저장 중 오류 발생",
			text: "네트워크 오류가 발생했습니다. 다시 시도해주세요.",
			icon: "error",
			button: "확인"
		});
	}
}


async function getDissentPage(stuId,lectNo,semstrNo){
	
	window.open(`${contextPath}/lecture/{lectNo}/dissent/new/${stuId}/${lectNo}/${semstrNo}`,
						"testPopup",
						"width=800,height=750,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
	
}




function removeNullProperties(obj) {
	if (typeof obj !== 'object' || obj === null) {
		return obj;
	}

	if (Array.isArray(obj)) {
		return obj.map(item => removeNullProperties(item)).filter(item => item !== null);
	}

	const result = {};
	for (const key in obj) {
		if (obj[key] !== null) {
			result[key] = removeNullProperties(obj[key]);
		}
	}
	return result;
}
































































