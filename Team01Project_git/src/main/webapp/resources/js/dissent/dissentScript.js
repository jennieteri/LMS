/**
 * 
 */
const url = "../yguniv/dissent"
const upBtn = document.getElementById("upBtn")
const selDiv = document.getElementById("selDiv");

	const dataElement = document.getElementById('contextData');
	const contextPath = dataElement.dataset.contextPath;
function dissentPagg(page) {
	console.log(page);
	searchForm.page.value = page;
	searchForm.requestSubmit();
}

document.addEventListener("DOMContentLoaded", async () => {
	const lectNo = document.getElementById("lectNo").value;
})
function calculateTotalScore(lesVo, scores) {
    let totalScore = 0;
    lesVo.forEach(item => {
        let score = 0;
        switch (item.evlStdrCd) {
            case "ASS":
                score = scores.assigScore;
                break;
            case "ATT":
                score = scores.attenAtndScore;
                break;
            case "ETC":
                score = scores.etcScore;
                break;
            case "FIN":
                score = scores.ftTestScore;
                break;
            case "MID":
                score = scores.prTestScore;
                break;
            default:
                console.error("Unknown evaluation standard code:", item.evlStdrCd);
                return;
        }
        totalScore += score * (item.rate / 100);
    });
    return Math.round(totalScore * 10) / 10; // 소수점 첫째자리까지 반올림
}
// 4.5 점수 기준으로 성적 변환 함수 (기존 점수를 4.5로 변환)
function convertToGrade(score) {
    // 점수를 4.5 기준으로 변환
    const scoreOn4_5Scale = (score / 100) * 4.5;
    
    let grade = '';
    if (scoreOn4_5Scale >= 4.3) {
        grade = 'A+';
    } else if (scoreOn4_5Scale >= 4.0) {
        grade = 'A';
    } else if (scoreOn4_5Scale >= 3.7) {
        grade = 'B+';
    } else if (scoreOn4_5Scale >= 3.3) {
        grade = 'B';
    } else if (scoreOn4_5Scale >= 3.0) {
        grade = 'C+';
    } else if (scoreOn4_5Scale >= 2.7) {
        grade = 'C';
    } else if (scoreOn4_5Scale >= 2.3) {
        grade = 'D+';
    } else if (scoreOn4_5Scale >= 2.0) {
        grade = 'D';
    } else {
        grade = 'F';
    }

    return grade;
}


//1인 조회용도
async function findOne(stu, lec) {
	const lectNo = document.getElementById("lectNo").value;
    const testDiv = document.getElementById("testDiv");
    const findUrl = `${contextPath}/lecture/${lectNo}/dissent/${stu}/${lec}`;

    console.log(findUrl);
    var resp = await fetch(findUrl);
    var pars = await resp.json();
    console.log(pars);
    var data = await pars.dissOne;
    console.log(data);
    var lesData = await data.lectVO.lesVo;
    console.log(lesData);

    const totalScore = calculateTotalScore(data.lectVO.lesVo, data.attenVO);
/**
   const grade = totalScore >= 90 ? 'A' :
                  totalScore >= 80 ? 'B' :
                  totalScore >= 70 ? 'C' :
                  totalScore >= 60 ? 'D' : 'F';
 */ 

 const grade = convertToGrade(totalScore);
let code = `
<table class="table table-bordered" style="margin-bottom: 1rem; border: 1px solid #d3d3d3;">
    <tr>
        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">상세 정보</th>
    </tr>
    <tr>
        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 25%;">강의명</th>
        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 25%;">학점</th>
        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 25%;">학번</th>
        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 25%;">이름</th>
    </tr>
    <tr>
        <input type='hidden' name='lectNo' id='lectNo' value='${data.lectNo}'></input>
        <input type='hidden' name='stuId' id='stuId' value='${data.attenVO.stuId}'></input>
        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${data.lectVO.lectNm}</td>
        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${data.lectVO.lectScore}</td>
        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${data.attenVO.stuId}</td>
        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${data.nm}</td>
    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">항목</th>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">점수</th>
                    </tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">출석</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">
            <input type='hidden' name='attenVO.attenAtndScore' id='attenAtndScore' value='${data.attenVO.attenAtndScore}'>
            ${data.attenVO.attenAtndScore}
        </td>
    </tr>
    <tr>
    <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">과제</th>
    <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">
        <input type='number' class="form-control form-control-sm"  name='attenVO.assigScore' id='assigScore' 
               style="width: 80%; margin: 0 auto; height: 30px; padding: 0.25rem 0.5rem;" value='${data.attenVO.assigScore}'>
    </td>
</tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; ">중간고사</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">
            <input type='number' class="form-control" style="width: 80%; margin: 0 auto; height: 30px; padding: 0.25rem 0.5rem;" name='attenVO.prTestScore' id='prTestScore' value='${data.attenVO.prTestScore}'></input>
        </td>
    </tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; ">기말고사</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">
            <input type='number' class="form-control" style="width: 80%; margin: 0 auto; height: 30px; padding: 0.25rem 0.5rem;" name='attenVO.ftTestScore' id='ftTestScore' value='${data.attenVO.ftTestScore}'></input>
        </td>
    </tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; ">기타</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">
            <input type='number' class="form-control" style="width: 80%; margin: 0 auto; height: 30px; padding: 0.25rem 0.5rem;" name='attenVO.etcScore' id='etcScore' value='${data.attenVO.etcScore}'></input>
        </td>
    </tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; ">총점</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">${totalScore}</td>
    </tr>
    <tr>
        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; ">성적</th>
        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">${grade}</td>
    </tr>
    <tr>
        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">이의 내용</th>
    </tr>
    <tr>
        <td colspan="4" style="padding: 1rem;">${data.objcCn}</td>
    </tr>
    <tr>
        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">답변 작성</th>
    </tr>
    <tr>
        <td colspan="4" style="padding: 1rem;">
            <textarea id="answerCn" name="answerCn" class="form-control" style="width: 100%; min-height: 100px;">${data.answerCn || ''}
				</textarea>
        </td>
    </tr>
    <tr>
	    <td colspan="4" style="text-align: right; padding: 0.5rem;">
			<button type="button" class="btn btn-primary" onclick="insertStr();" id="strBtn"><i class="bi bi-pencil-square"></i></button>
		   <button type="button" class="btn btn-primary" onclick="updateForm();" id="upBtn">등록</button>
		</td>   
	</tr>
	${data.answerCn? '<input type="hidden" id="doubleCheck" value="doubleCheck"' : ''}
</table>
`;

selDiv.innerHTML = code;

    console.log(document.getElementById("selForm")); // 확인용
}

//선택창 비우기
function selDivClean() {
	const selDiv = document.querySelector("#selDiv")
	selDiv.replaceChildren();
}
async function updateForm() {
    const formd = document.getElementById("selForm");
    const formData = new FormData(formd);
    const jsonData = {};
	const lectNo = document.getElementById("lectNo").value;
	
	const doubleCheck = document.getElementById("doubleCheck").value;
	
	if(doubleCheck){
		swal("수정 실패", "답변이 존재합니다.", "error");
		return;
	}



    formData.forEach((value, key) => {
        if (key.startsWith("attenVO.")) {
            const attenKey = key.replace("attenVO.", "");
            if (!jsonData.attenVO) jsonData.attenVO = {};
            jsonData.attenVO[attenKey] = value;
        } else {
            jsonData[key] = value;
        }
    });

    try {
        const upResp = await fetch(`${contextPath}/lecture/${lectNo}/dissent`, {
            method: "put",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(jsonData),
        });

        const da = await upResp.json();
        if (upResp.ok) {
            swal("수정 완료", "이의신청 내용이 성공적으로 수정되었습니다.", "success").then(() => {
                getDissentList(); // 테이블 업데이트
            });
        } else {
            swal("수정 실패", da.message || "이의신청 수정 중 오류가 발생했습니다.", "error");
        }
    } catch (error) {
        console.error("Error:", error);
        swal("오류 발생", "네트워크 오류 또는 서버 오류가 발생했습니다.", "error");
    }
}



async function getDissentList() {
	const lectNo = document.getElementById("lectNo").value;
	const getDissentListUrl = `${contextPath}/lecture/${lectNo}/dissent/profe/new/dissent/list`
	var selDiv = document.querySelector("#selDiv")
	//	var dissentList = await fetch(getDissentListUrl)

//	console.log(dissentList)
	const response = await fetch(getDissentListUrl);
	const dissentList = await response.json(); // JSON 형태로 변환
	//console.log(dissentList);
	//여기서 예시 함수 불러서 해보기
	// 테이블 내용 초기화
	const tbody = document.getElementById("tp");
	tbody.innerHTML = ""; // 기존 테이블 비우기

	// 새 데이터로 테이블 채우기
	const rows = dissentList.map(diss => `
    <tr>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.studentVO.deptCd}</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.studentVO.gradeCd}</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.personVO.id }</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">${diss.personVO.nm }</td>
                            <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">
                                <button id="findBtn" class="btn btn-primary btn-sm" 
                                        style="margin: 0; padding: 0.25rem 0.5rem;"
                                        onclick="findOne('${diss.personVO.id }','${diss.lectVO.lectNo }');">
                                    상세
                                </button>
                            </td>
                        </tr>
`).join("");

tbody.innerHTML = rows;

	cl = `	
                <table class="table table-bordered" style="margin-bottom: 1rem; border: 1px solid #d3d3d3;">
                    <tr>
                        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">상세 정보</th>
                    </tr>
                    <tr>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">강의명</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">학번</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">이름</th>
                        <th style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF; width: 20%;">학점</th>
                    </tr>
                    <tr>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                        <td style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">항목</th>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">점수</th>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">출석</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">과제</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">중간고사</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">기말고사</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">기타</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">총점</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
                        <th colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">성적(A)</th>
                        <td colspan="2" style="text-align: center; padding: 0.5rem; vertical-align: middle;">미선택</td>
                    </tr>
                    <tr>
				        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">이의 내용</th>
				    </tr>
				    <tr>
				        <td colspan="4" style="padding: 1rem;">미선택</td>
				    </tr>
                    <tr>
                        <th colspan="4" style="text-align: center; padding: 0.5rem; vertical-align: middle; background-color: #CFE2FF;">답변</th>
                    </tr>
                    <tr>
                        <td colspan="4" style="padding: 1rem;">
                            <textarea id="answerCn" name="answerCn" class="form-control" style="width: 100%; min-height: 100px;"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right; padding: 0.5rem;">
				            <button type="button" class="btn btn-primary" onclick="insertStr();" id="strBtn"><i class="bi bi-pencil-square"></i></button>
						    <button type="button" class="btn btn-primary" onclick="updateForm();" id="upBtn">등록</button>
						</td>
                    </tr>
                </table>
    `;
	selDiv.innerHTML = cl;
}



// 여부 타입에 대하여 N을 자동으로 기입 함수 	설정이 실패 했을 때 디폴트 값이 0이 되는 함수

async function makeTag(parseData, parentTag) {
	let code = '<table class="table table-bordered">';
	for (let key in parseData) {
		if (typeof parseData[key] === 'object' && parseData[key] !== null) {
			code += `<tr><td>${key} : <input type='text' class='bind-target' id='${key}' name='${key}' value='Object'></input></td></tr>`;
			code += await makeTag(parseData[key], parentTag);
		} else {
			let value = parseData[key] ? parseData[key] : '비어있음';
			code += `<tr><td>${key} : <input type='text' class='bind-target' id='${key}' name='${key}' value='${value}'></input></td></tr>`;
		}
	}
	code += '</table>';

	console.log(code);
	return code;
}
async function makeProperty(parseData, parentTag) {
	let code = '';
	for (let key in parseData) {
		if (typeof parseData[key] === 'object' && parseData[key] !== null) {
			code += `<tr><td>${key} : <input type='text' class='bind-target' id='${key}' name='${key}' value='Object'></input></td></tr>`;
			code += await makeTag(parseData[key], parentTag);
		} else {
			let value = parseData[key] ? parseData[key] : '비어있음';
			code += `<tr><td>${key} : <input type='text' class='bind-target' id='${key}' name='${key}' value='${value}'></input></td></tr>`;
		}
	}
	code += '</table>';

	console.log(code);
	return code;
}



document.addEventListener("DOMContentLoaded", async () => {
	const lectNo = document.getElementById("lectNo").value;
	const getDummyUrl = `${contextPath}/lecture/${lectNo}/dissent/profe/new/dissent/list` //요청 url
	const $dummyTable = $("#parentTable"); // table
	//await dataTableFunction(getDummyUrl, $dummyTable); //데이터 테이블은 jquery객체를 넘겨야함
})

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


function insertStr(){
	const answerCn = document.querySelector("#answerCn")
	answerCn.value="한 학기동안 고생했습니다."
}




/**

async function dataTableFunction(sendUrl, parentTable) {

	$.ajax({
		url: sendUrl,
		type: "GET",
		success: function(data) {
			//	console.log(data) 전체 데이터
			//	console.log(data[0].lectVO.lectNo) 리스트=> 배열로 반환
			parentTable.dataTable({
				data: data,
				columns: [ 		//VO가 has 관계를 지녀 그 안의 데이터를 가져올 경우에는 . 닷노테이션 접근
					{
						data: 'lectVO.lectNo', title: '강의번호', //조건마다 다른 형태를 표시해야하면 render:function if(T?F?){return <tag>} else{return <tag>}
						render: function(data, type, row) {
							if (row.lectVO.lectNo == 'L003') { 
								return `<button class="btn btn-primary" >${row.lectVO.lectNo}</button>`;
							} else { return `<button class="btn btn-primary" >asdasdasd</button>` }
						}
					},   // 강의번호
					{ data: 'lectVO.lectNm', title: '강의명' },     // 강의명
					{ data: 'personVO.id', title: '학번' },         // 학번
					{ data: 'personVO.nm', title: '이름' },         // 이름
					{
						data: null,
						title: '조회버튼',
						render: function(data, type, row) {
							// 버튼 클릭 시 row 데이터 전달
							return `<button class="btn btn-primary" 
                                onclick="showDetails('${row.lectVO.lectNo}', '${row.personVO.id}')">조회</button>`;
						}
					}
				]
			});

		}, error: function(request, status, error) {
			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}


function showDetails(a, b) {
	console.log(a, b)
}





 */ 