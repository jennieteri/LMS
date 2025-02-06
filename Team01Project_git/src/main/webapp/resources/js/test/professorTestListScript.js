/**
 * 
 */

// null 값을 제거하는 재귀 함수
function removeNullProperties(obj) {
	if (typeof obj !== 'object' || obj === null) {
		return obj; // 객체가 아니거나 null인 경우 그대로 반환
	}

	// 객체를 복제하면서 null이 아닌 값만 유지
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

document.addEventListener("DOMContentLoaded", async () => {
	const testNo = document.getElementById('testNo').value;
	const lectNo = document.getElementById('lectNo').value;

	const dataElement = document.getElementById('contextData');
	const contextPath = dataElement.dataset.contextPath;

	const url = `${contextPath}/lecture/${lectNo}/attendeeTest/professor/${testNo}/get`;
	//location.href=`${contextPath}/lecture/${lectNo}/attendeeTest/professor/${testNo}`
	console.log('Fetch URL:', url);

	try {
		const response = await fetch(url);
		if (!response.ok) {
			throw new Error(`HTTP error! Status: ${response.status}`);
		}

		const data = await response.json();
		const boxElement = document.getElementById('box');

		console.log(removeNullProperties(data.professorTestList))

		if (!data.professorTestList || data.professorTestList.length === 0) {
			boxElement.innerHTML = `
                <div class="card mb-4">
                    <div class="card-body text-center py-5">
                        <h5 class="text-muted mb-0">등록된 문항이 없습니다</h5>
                    </div>
                </div>
            `;
			return;
		}

		data.professorTestList.forEach(testVO => {
			const formattedDate = `${testVO.testSchdl.slice(0,4)}-${testVO.testSchdl.slice(4,6)}-${testVO.testSchdl.slice(6,8)}`;
    
		    // 시간 포맷팅 (HHMM -> HH:MM)
		    const startTime = `${testVO.testDt.slice(0,2)}:${testVO.testDt.slice(2,4)}`;
		    const endTime = `${testVO.testEt.slice(0,2)}:${testVO.testEt.slice(2,4)}`;
		    const formattedTime = `${startTime} ~ ${endTime}`;
			var testSe = testVO.testSe == 'PR' ? '중간고사' : '기말고사'
			let html = `
   <div class="container mt-4">
    <!-- 시험 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-light" style="margin-bottom: 20px;">
            <h5 class="mb-0">시험 정보</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-primary m-0 p-0">
                    <tbody>
                        <tr>
                               <th class="text-center" style="width: 20%">시험 번호</th>
                                <td class="table-light text-center" style="width: 30%">${testVO.testNo}</td>
                                <th class="text-center" style="width: 20%">강의명</th>
                                <td class="table-light text-center" style="width: 30%">${testVO.lectVO.lectNm}</td>
                        </tr>
                        <tr>
                             <th class="text-center">시험 종류</th>
                                <td class="table-light text-center" >${testSe}</td>
                                <th class="text-center">시험 일자 및 시간</th>
                                <td class="table-light text-center" >${formattedDate} / ${formattedTime}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 문제 정보 섹션 -->
    <div class="card mb-4">
    <div class="card-header bg-light d-flex justify-content-between align-items-center" style="margin-bottom: 20px;">
        <h5 class="mb-0">문제 정보</h5>
    </div>
    <div class="card-body">
        ${testVO.questionVO.map(question => `
            <div class="table-responsive">
                <table class="table table-bordered table-primary m-0 p-0">
                    <tbody>
                        <tr>
                            <th class="text-center" style="width: 20%">문제 번호</th>
                            <td class="table-light text-center" style="width: 30%">${question.queNo.slice(-1)} 번</td>
                            <th class="text-center" style="width: 20%">문제 종류</th>
                            <td class="table-light text-center" style="width: 30%">${question.queType}</td>
                        </tr>
                        <tr>
                            <th class="text-center">문제 지문</th>
                            <td class="table-light text-center">${question.queDescr}</td>
                            <th class="text-center">배점</th>
                            <td class="table-light text-center">${question.queScore}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <hr >
        `).join('')}
    </div>
</div>


    <!-- 응시 기록 섹션 -->
    <div class="card mb-4">
        <div class="card-header bg-light" style="margin-bottom: 20px;">
            <h5 class="mb-0">응시 기록</h5>
        </div>
        <div class="card-body">
            ${testVO.examList.map(exam => `
                <div class="border-bottom pb-3 mb-3">
                    <h6 class="mb-3">학생 ID: ${exam.stuId}</h6>
                    <div class="table-responsive">
                        <table class="table table-bordered table-primary m-0 p-0">
                            <thead class=" ">
                                <tr>
                                    <th class=" text-center">문제 번호</th>
                                    <th style="width:60%;" class="bg-primary-subtle text-center">학생 답안</th>
                                    <th class="text-center">점수</th>
                                    <th class="text-center">등록</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${exam.questionAnswerList.map(qa => `
                                    <tr>
                                        <td class="text-center table-light">${qa.queNo.slice(-1)}</td>
                                        <td class="table-light">${qa.queAnswer}</td>
                                        <td class="text-center table-light">
											${qa.quesScore > 1 ? 
									                `<span>${qa.quesScore}</span>` : 
									                `<input type="number" 
									                    class="form-control" 
									                    id="${exam.stuId}-${qa.queNo}" 
									                    name="${exam.stuId}-${qa.queNo}"
									                    min="0"
									                    max="100">`
									            }
                                        </td>
                                        <td class="text-center table-light">
                                            <button class="btn btn-primary btn-sm table-light" 
                                                    onclick="submitScore('${exam.stuId}', '${qa.queNo}')">
                                                등록
                                            </button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `).join('')}
        </div>
    </div>
</div>

            `;

			html += `</div><hr>`;
			boxElement.innerHTML += html;
		});
	} catch (error) {
		console.error('Error fetching data:', error);
	}
});

// 점수 제출 함수
function submitScore(stuId, queNo) {
	const dataElement = document.getElementById('contextData');
	const testElement = document.getElementById('testData');
	const score = document.getElementById(`${stuId}-${queNo}`).value;
	const contextPath = dataElement.dataset.contextPath;
	const testNo = document.getElementById('testNo').value;
	const lectNo = document.getElementById('lectNo').value;

	const url = `${contextPath}/lecture/${lectNo}/attendeeTest/${queNo}/score`;

	if (!score) {
		swal("실패", "점수를 입력해 주세요.", "error");
		return;
	}

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			testNo: testNo,
			lectNo: lectNo,
			stuId: stuId,
			queNo: queNo,
			quesScore: score
		})
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(data => {
			if (data.status === 'success') {
						loadProfessorTestList(); // 리스트 새로고침

/**
				swal("성공", "점수가 성공적으로 등록되었습니다.", "success")
					.then(() => {
 */
					
			} else if (data.status === 'fail') {
				swal("실패", "점수가 배점보다 높게 입력되었습니다.", "error");
			} else {
				swal("오류", "알 수 없는 오류가 발생했습니다.", "error");
			}
		})
		.catch(error => {
			console.error("Error:", error);
			swal("점수 등록 중 오류가 발생했습니다.", "서버와의 연결을 확인해주세요.", "error");
		});
}

async function loadProfessorTestList() {
	const testNo = document.getElementById('testNo').value;
	const lectNo = document.getElementById('lectNo').value;
	const dataElement = document.getElementById('contextData');
	const contextPath = dataElement.dataset.contextPath;
	const url = `${contextPath}/lecture/${lectNo}/attendeeTest/professor/${testNo}/get`;

	try {
		const response = await fetch(url);
		if (!response.ok) {
			throw new Error(`HTTP 오류! 상태: ${response.status}`);
		}

		const data = await response.json();
		const boxElement = document.getElementById('box');

		boxElement.innerHTML = ''; // 기존 내용 삭제

		if (!data.professorTestList || data.professorTestList.length === 0) {
			boxElement.innerHTML = `
                 <div class="container mt-4">
                    <!-- 시험 정보 카드 -->
                    <div class="card mb-4">
                        <div class="card-header bg-light">
                            <h5 class="mb-0">시험 정보</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered m-0 p-0">
                                    <tbody>
                                        <tr>
                                            <th class="bg-light" style="width: 20%">시험 번호</th>
                                            <td style="width: 30%">해당하는 정보가 없습니다.</td>
                                            <th class="bg-light" style="width: 20%">교과목 번호</th>
                                            <td style="width: 30%">해당하는 정보가 없습니다.</td>
                                        </tr>
                                        <tr>
                                            <th class="bg-light">시험 종류</th>
                                            <td>해당하는 정보가 없습니다.</td>
                                            <th class="bg-light">시험 종료 시간</th>
                                            <td>해당하는 정보가 없습니다.</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
            `;
			return;
		}

		data.professorTestList.forEach(testVO => {
			const formattedDate = `${testVO.testSchdl.slice(0,4)}-${testVO.testSchdl.slice(4,6)}-${testVO.testSchdl.slice(6,8)}`;
    
		    // 시간 포맷팅 (HHMM -> HH:MM)
		    const startTime = `${testVO.testDt.slice(0,2)}:${testVO.testDt.slice(2,4)}`;
		    const endTime = `${testVO.testEt.slice(0,2)}:${testVO.testEt.slice(2,4)}`;
		    const formattedTime = `${startTime} ~ ${endTime}`;

			var testSe = testVO.testSe == 'PR' ? '중간고사' : '기말고사'
			let html = `
   <div class="container mt-4">
    <!-- 시험 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-light" style="margin-bottom: 20px;">
            <h5 class="mb-0">시험 정보</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-primary m-0 p-0">
                    <tbody>
                        <tr>
                               <th class="text-center" style="width: 20%">시험 번호</th>
                                <td class="table-light text-center" style="width: 30%">${testVO.testNo}</td>
                                <th class="text-center" style="width: 20%">강의명</th>
                                <td class="table-light text-center" style="width: 30%">${testVO.lectVO.lectNm}</td>
                        </tr>
                        <tr>
                             <th class="text-center">시험 종류</th>
                                <td class="table-light text-center" >${testSe}</td>
                                <th class="text-center">시험 일자 및 시간</th>
                                <td class="table-light text-center" >${formattedDate} / ${formattedTime}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 문제 정보 섹션 -->
    <div class="card mb-4">
    <div class="card-header bg-light d-flex justify-content-between align-items-center" style="margin-bottom: 20px;">
        <h5 class="mb-0">문제 정보</h5>
    </div>
    <div class="card-body">
        ${testVO.questionVO.map(question => `
            <div class="table-responsive">
                <table class="table table-bordered table-primary m-0 p-0">
                    <tbody>
                        <tr>
                            <th class="text-center" style="width: 20%">문제 번호</th>
                            <td class="table-light text-center" style="width: 30%">${question.queNo.slice(-1)} 번</td>
                            <th class="text-center" style="width: 20%">문제 종류</th>
                            <td class="table-light text-center" style="width: 30%">${question.queType}</td>
                        </tr>
                        <tr>
                            <th class="text-center">문제 지문</th>
                            <td class="table-light text-center">${question.queDescr}</td>
                            <th class="text-center">배점</th>
                            <td class="table-light text-center">${question.queScore}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <hr>
        `).join('')}
    </div>
</div>


    <!-- 응시 기록 섹션 -->
    <div class="card mb-4">
        <div class="card-header bg-light" style="margin-bottom: 20px;">
            <h5 class="mb-0">응시 기록</h5>
        </div>
        <div class="card-body">
            ${testVO.examList.map(exam => `
                <div class="border-bottom pb-3 mb-3">
                    <h6 class="mb-3">학생 ID: ${exam.stuId}</h6>
                    <div class="table-responsive">
                        <table class="table table-bordered table-primary m-0 p-0">
                            <thead class=" ">
                                <tr>
                                    <th class=" text-center">문제 번호</th>
                                    <th style="width:60%;" class="bg-primary-subtle text-center">학생 답안</th>
                                    <th class="text-center">점수</th>
                                    <th class="text-center">등록</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${exam.questionAnswerList.map(qa => `
                                    <tr>
                                        <td class="text-center table-light">${qa.queNo.slice(-1)}</td>
                                        <td class="table-light">${qa.queAnswer}</td>
                                        <td class="text-center table-light">
											${qa.quesScore > 1 ? 
									                `<span>${qa.quesScore}</span>` : 
									                `<input type="number" 
									                    class="form-control" 
									                    id="${exam.stuId}-${qa.queNo}" 
									                    name="${exam.stuId}-${qa.queNo}"
									                    min="0"
									                    max="100">`
									            }
                                        </td>
                                        <td class="text-center table-light">
                                            <button class="btn btn-primary btn-sm table-light" 
                                                    onclick="submitScore('${exam.stuId}', '${qa.queNo}')">
                                                등록
                                            </button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `).join('')}
        </div>
    </div>
</div>

            `;

			html += `</div><hr>`;
			boxElement.innerHTML += html;
		});


	} catch (error) {
		console.error('데이터 로드 중 오류 발생:', error);
	}
}
