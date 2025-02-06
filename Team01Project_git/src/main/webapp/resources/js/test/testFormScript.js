/**
 * 
 */

const testInsertBtn = document.querySelector("#testInsertBtn")


document.addEventListener("DOMContentLoaded", () => {
	document.querySelector('input[name="testOnlineYn"][value="Y"]').addEventListener('change', function() {
		//		document.querySelector('input[name="croomNm"]').disabled = this.checked;
		document.querySelector('select[name="croomNm"]').disabled = this.checked;
	});

	document.querySelector('input[name="testOnlineYn"][value="N"]').addEventListener('change', function() {
		document.querySelector('select[name="croomNm"]').disabled = false;
		//	document.querySelector('input[name="croomNm"]').disabled = false;
	});
setMinDate()
})
function setMinDate() {
    // 오늘 날짜를 YYYY-MM-DD 형식으로 가져오기
    let today = new Date();
    let month = today.getMonth() + 1; // 월은 0부터 시작하므로 1을 더함
    let day = today.getDate();
    let year = today.getFullYear();

    // 월과 일이 10보다 작을 경우 앞에 0을 붙임
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }

    let minDate = year + '-' + month + '-' + day;
    document.getElementById('dateInput').setAttribute('min', minDate);
}

async function insertForm() {
	const lectNo = document.querySelector("#lectNo").value;
	const contextPath = document.querySelector("#contextPath").value;
	const url = `${contextPath}/test/new/test/${lectNo}`;
	const getData = getTestInfoAndDateTime();

	console.log(getData);
	console.log(contextPath);

	try {
		const response = await fetch(url, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(getData),
		});

		if (response.ok) {
			const data = await response.json();
			console.log(data.testNo);
			insertQuestion(data.testNo);
		} else {
			const errorData = await response.json();
			swal({
				title: "추가 실패",
				text: errorData.message || "알 수 없는 오류가 발생했습니다.",
				icon: "error",
				button: "확인",
			});
		}
	} catch (err) {
		console.error(err);
		swal({
			title: "추가 실패",
			text: err.message || "알 수 없는 오류가 발생했습니다.",
			icon: "error",
			button: "확인",
		});
	}
}

async function insertQuestion(testNo) {
	const contextPath = document.querySelector("#contextPath").value;
	const qurl = `${contextPath}/test/question`;
	const getData = getQuestionData();

	getData.testNo = testNo;

	console.log(getData);

	try {
		const response = await fetch(qurl, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: getData,
		});

		const responseData = await response.json();

		if (response.ok) {
			console.log("Question added successfully");

			swal({
				title: "추가완료",
				text: "시험이 성공적으로 추가되었습니다",
				icon: "success",
				button: "확인",
			}).then(() => {
				location.href = `${contextPath}/test/${testNo}`;
			});
		} else {
			// 서버에서 반환된 에러 메시지 사용
			const errorMessage = responseData.message || "알 수 없는 오류가 발생했습니다.";
			swal({
				title: "추가실패",
				text: errorMessage,
				icon: "error",
				button: "확인",
			});
		}
	} catch (err) {
		console.error(err);
		swal({
			title: "추가실패",
			text: "서버와의 통신 중 오류가 발생했습니다.",
			icon: "error",
			button: "확인",
		});
	}
}

//문제 점수는 무조건ㅇ ㅣㅂ력으로
function getTestInfoAndDateTime() {
	const testInfo = {
		lectNo: document.querySelector("#lectNo")?.value,
		//	croomCd: document.querySelector('input[name="croomNm"]')?.value,
		croomCd: document.querySelector('select[name="croomNm"]')?.value,
//		testSe: document.querySelector('select[name="testSe"]').value,
		testSe: document.querySelector('#testSe').value,
		testOnlineYn: document.querySelector('input[name="testOnlineYn"]:checked')?.value,
		testSchdl: document.querySelector('#dateDiv input[type="date"]').value,
		testDt: document.querySelector('#testDt').value,
		testEt: document.querySelector('#testEt').value
	};
	
	if (testInfo.testOnlineYn === 'Y') {
        testInfo.croomCd = null;
    }
	console.log(testInfo)

	return testInfo;
}

function autoResize(textarea) {
	textarea.style.height = 'auto';
	textarea.style.height = textarea.scrollHeight + 'px';
}

document.querySelectorAll('.option textarea').forEach(textarea => {
	textarea.addEventListener('input', function() {
		autoResize(this);
	});
});
/**
 * 
window.revFam = function(pBtn) {
	// 가장 바깥쪽의 div.question-section을 찾아서 삭제
	const questionBox = pBtn.closest('.question-header');
	if (questionBox) {
		// 요소를 DOM에서 완전히 제거
		questionBox.parentNode.removeChild(questionBox);
		// 또는 아래 방법도 가능
		// questionBox.remove();
	}
};
 */
window.revFam = function(pBtn) {
	// 가장 바깥쪽의 draggable-question div를 찾아서 삭제
	const questionBox = pBtn.closest('.draggable-question');
	if (questionBox) {
		questionBox.remove();
		updateQuestionNumbers(); // 문제 번호 다시 매기기
	}
};


function getQuestionData() {
	const questions = document.querySelectorAll('.options');
	const questionsData = [];

	questions.forEach((question, index) => {
		// 기본 question 데이터 생성
		console.log(index)
		const questionData = {
			queNo: `Q${(index + 1).toString().padStart(3, '0')}`, // 문제 번호
			queDescr: question.querySelector('#queDescr').value, // 문제 설명
			queScore: question.querySelector('#scorebox').value, // 문제 배점
			queAnswer: question.querySelector('input[placeholder="정답"]')?.value, // 정답
			queType: getQuestionType(question) // 문제 유형
		};

		// 객관식 또는 주관식일 경우에만 answerVO 생성
		if (questionData.queType === '객관식') {
			questionData.answerVO = Array.from(question.querySelectorAll('textarea')).map((textarea, idx) => ({
				anchNo: `A${(1).toString().padStart(3, '0')}`,
				anchDescr: textarea.value, // 답변 내용
			}));

			// 정답 표시된 경우 isCorrect 업데이트
			const correctAnswerIndex = parseInt(questionData.queAnswer, 10) - 1;
			if (!isNaN(correctAnswerIndex) && questionData.answerVO[correctAnswerIndex]) {
				//          questionData.answerVO[correctAnswerIndex].isCorrect = true;
			}
		} else if (questionData.queType === '주관식') {
			questionData.answerVO = [
				{
					anchNo: `A${(1).toString().padStart(3, '0')}`,
					anchDescr: question.querySelector(`input[name="q${index + 1}"]`).value // 주관식 답변 필드
				}
			];
		} else {
			questionData.answerVO = [
				{
					anchNo: `A${(1).toString().padStart(3, '0')}`,
					anchDescr: question.querySelector(`input[name="q${index + 1}"]`).value // 서술형 답변 필드
				}
			];
		}

		// 서술형은 answerVO를 추가하지 않음
		questionsData.push(questionData);
	});

	console.log(JSON.stringify(questionsData, null, 2));
	return JSON.stringify(questionsData, null, 2);
}

function getQuestionType(question) {
    if (question.querySelectorAll('textarea').length > 0) {
        return '객관식';
    } else if (question.querySelector('.score-section input[placeholder="정답"]')) {
        return '주관식';
    } else {
        return '서술형';
    }
}



let questionCount = 1;
function queBtn(que) {
	console.log(questionCount)
	const queBox = document.getElementById('queBox');
	const newSection = document.createElement('div');
	newSection.draggable = true;
	newSection.classList.add('draggable-question');

	var code = '';

	if (que == 'num') {
		code = `
            <div class="options">
                <div class="question-header">
                    <div class="answer-div">
                        <h4>${questionCount}.</h4>
                        <input type="text" class="form-control" id="queDescr">
                    </div>
                </div>
                <br>
                <textarea class="form-control col-auto" cols="20" rows="1" placeholder="1번 문항"></textarea>
                <br>
                <textarea class="form-control col-auto" cols="20" rows="1" placeholder="2번 문항"></textarea>
                <br>
                <textarea class="form-control col-auto" cols="20" rows="1" placeholder="3번 문항"></textarea>
                <br>
                <textarea class="form-control col-auto" cols="20" rows="1" placeholder="4번 문항"></textarea>
                <br>
     <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
        <p style="margin: 0;">배점</p>
        <input type="number" id="scorebox" class="form-control" placeholder="배점">
        <p style="margin: 0;">정답</p>
        <input type="text" class="form-control" placeholder="정답" style="width:100px;">
        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)"><i class="bi bi-x-lg"></i></button>
        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${questionCount}','num')"><i class="bi bi-pencil-square"></i></button>
    </div>
            </div>`;
	} else if (que == 'str') {
		code = `
            <div class="options">
                <div class="question-header">
                    <div class="answer-div">
                        <h4>${questionCount}.</h4>
                        <input type="text" class="form-control" id="queDescr">

                    </div>
<br>

                    <input class="form-control" type="text" name="q${questionCount}" placeholder="지문을 입력하세요">
                </div>
                          <div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
						    <p style="margin: 0; display: flex; align-items: center;">배점</p>
						    <input type="number" id="scorebox" class="form-control" placeholder="배점">
						    <p style="margin: 0; display: flex; align-items: center;">정답</p>
						    <input type="text" class="form-control" placeholder="정답" style="width:100px;">
                        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)" ><i class="bi bi-x-lg"></i></button>
                        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${questionCount}','str')" ><i class="bi bi-pencil-square"></i></button>
						</div>
            </div>`;
	} else {
		code = `
            <div class="options">
                <div class="question-header">
                    <div class="answer-div">
                        <h4>${questionCount}.</h4>
                        <input type="text" class="form-control" id="queDescr">

                    </div>

<br>
                    <input class="form-control" type="text" name="q${questionCount}" placeholder="지문을 입력하세요">
                </div>
 						<div class="score-section" style="display: flex; align-items: center; justify-content: flex-end; gap: 10px;">
						    <p style="margin: 0; display: flex; align-items: center;">배점</p>
						    <input type="number" id="scorebox" class="form-control" placeholder="배점">
                        <button style="width:40px;" class="btn btn-danger" onclick="revFam(this)" ><i class="bi bi-x-lg"></i></button>
                        <button style="width:40px;" class="btn btn-primary" onclick="fastInsert('${questionCount}','lStr')" ><i class="bi bi-pencil-square"></i></button>
						</div>
            </div>`;
	}

	newSection.innerHTML = code;
	queBox.appendChild(newSection);

	// 이벤트 핸들러 등록
	newSection.addEventListener('dragstart', handleDragStart);
	newSection.addEventListener('dragover', handleDragOver);
	newSection.addEventListener('drop', handleDrop);

	questionCount++;
	updateQuestionNumbers(); // 번호 업데이트
}
//드래그 앤 드롭 관련 함수들
function handleDragStart(e) {
	e.dataTransfer.setData('text/plain', Array.from(e.target.parentNode.children).indexOf(e.target));
	e.target.classList.add('dragging'); // 드래그 시작 표시
}


function handleDragOver(e) {
	e.preventDefault();
}
function handleDrop(e) {
	e.preventDefault();
	const draggedIdx = parseInt(e.dataTransfer.getData('text/plain'));
	const queBox = document.getElementById('queBox');
	const questions = Array.from(queBox.children);

	const draggedElement = questions[draggedIdx];
	const targetElement = e.target.closest('.draggable-question');

	if (targetElement) {
		const dropIdx = questions.indexOf(targetElement);
		if (dropIdx < draggedIdx) {
			queBox.insertBefore(draggedElement, questions[dropIdx]);
		} else {
			queBox.insertBefore(draggedElement, questions[dropIdx].nextSibling);
		}
	}
	document.querySelector('.dragging')?.classList.remove('dragging');

	// 번호 업데이트
	requestAnimationFrame(() => updateQuestionNumbers());
}



// 문제 번호 업데이트 함수
function updateQuestionNumbers() {
	const questions = document.querySelectorAll('#queBox .draggable-question'); // `#queBox` 내에서만 선택
	questions.forEach((question, index) => {
		const questionHeader = question.querySelector('h4'); // 문제 번호 헤더
		if (questionHeader) {
			questionHeader.textContent = `${index + 1}.`; // 번호 업데이트
		}
	});
}

 function fastInsert(questionCount,que) {
    // 해당 questionCount에 맞는 문제 div 찾기
    const questionDiv = document.querySelector(`.draggable-question:nth-child(${questionCount})`);
    
    if (!questionDiv) {
        alert("해당 문항을 찾을 수 없습니다.");
        return;
    }

    // 문제 설명 입력 (id="queDescr")
    const queDescrInput = questionDiv.querySelector("#queDescr");
    
    if (queDescrInput) {
        queDescrInput.value = `자동 입력된 문제 설명 ${questionCount}`;
    }

    // 배점 필드 자동 입력
    const scoreInput = questionDiv.querySelector("#scorebox");
    if (scoreInput) {
        scoreInput.value = "10"; // 배점 자동 입력
    }

    // 객관식 문항 정답에 1~4 중 랜덤 값 입력
    const answerInput = questionDiv.querySelector("input[placeholder='정답']");
    if (answerInput) {
        const randomAnswer = Math.floor(Math.random() * 4) + 1; // 1~4 랜덤 값
        answerInput.value = randomAnswer;
    }

    // 지문 자동 입력 (textarea 또는 input[name^='q'])
    const textInputs = questionDiv.querySelectorAll("textarea, input[name^='q']");
    textInputs.forEach((input, index) => {
        input.value = `자동 입력된 지문 ${index + 1}`;
    });

}


function insertTest() {
    // 기본 값 설정
    const defaultDate = new Date().toISOString().split('T')[0]; // 오늘 날짜 (YYYY-MM-DD 형식)
    const defaultStartTime = "09:00";
    const defaultEndTime = "22:30";

    // 강의실 선택 (기본값: 첫 번째 옵션)
    document.querySelector('select[name="croomNm"]').value = "CR002";

    // 시험 종류 선택 (기본값: 중간고사)
    //document.querySelector('select[name="testSe"]').value = "PR";

    // 온라인 여부 설정 (기본값: 온라인 시험)
    document.querySelector('input[name="testOnlineYn"][value="Y"]').checked = true;

    // 날짜 입력
    document.getElementById('dateInput').value = defaultDate;

    // 시작 시간 입력
    document.getElementById('testDt').value = defaultStartTime;

    // 종료 시간 입력
    document.getElementById('testEt').value = defaultEndTime;

    // 온라인 시험 선택 시 강의실 비활성화
    document.querySelector('select[name="croomNm"]').disabled = true;

    // 알림 메시지
    console.log("시험 정보가 입력되었습니다.");
}
















