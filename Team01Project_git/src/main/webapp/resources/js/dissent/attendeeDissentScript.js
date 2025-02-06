/**
 * 학생이 이의신청 페이지에 들어온다. 가장 베스트는 이번 학기에 들은 강의를 선택하고 해당 학기에서 교수에게 받은 점수를 조회할 수 있어야 한다
 */

/**
const url = "../dissent"
const upBtn = document.getElementById("upBtn")
const selDiv = document.getElementById("selDiv");
const disForm = document.getElementById("disForm");
const dataElement = document.getElementById('contextData');
const contextPath = dataElement.dataset.contextPath;

document.addEventListener("DOMContentLoaded", () => {
	const $lectNo = $('[name="lectNo"]');
	const $profeNm = $('[name="profeNm"]');

	$lectNo.on("change", async event => {
		// 선택된 옵션의 value 값 가져오기

		const selLectNo = event.target.value;
		const nmUrl = `${contextPath}/dissent/${selLectNo}`
		//       console.log("선택된 강의번호 : ", selLectNo);
		//      console.log("선택: ", nmUrl);

		const resp = await fetch(nmUrl);
		const pars = await resp.json();
		const data = await pars.profeList;

		console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		console.log(data);

		const $profeNm = $('#profeNm');

		// 리스트 크기가 1이므로 첫 번째 항목의 이름만 표시
		if (data && data.length > 0) {
			$profeNm.text(data[0].personVO.nm);
		}


		//	const stuId= $('#score')// 로그인 한 정보
		const stuId = document.querySelector("#stuId").value;


		const rrrurl = `${url}/atten/${stuId}/${selLectNo}`

		console.log(rrrurl)
		// 서버에 해당 강의번호에 대한 데이터 요청
		const scresp = await fetch(rrrurl);
		const scpars = await scresp.json();
		const scdata = await scpars.attenOne;

		console.log(stuId)
		console.log(scpars)
		console.log(scdata)

		var $lectScore = $('#lectScore');
		var $attenScore = $('#attenScore');
		var $assigScore = $('#assigScore');
		var $prTestScore = $('#prTestScore');
		var $ftTestScore = $('#ftTestScore');
		var $etcScore = $('#etcScore');

		var lectScore = scdata.lectVO.lectScore
		var attenScore = scdata.attenVO.attenAtndScore
		var assigScore = scdata.attenVO.assigScore
		var prTestScore = scdata.attenVO.prTestScore
		var ftTestScore = scdata.attenVO.ftTestScore
		var etcScore = scdata.attenVO.etcScore

		$lectScore.text(lectScore);  // 학점
		$attenScore.text(attenScore);  // 출석점수
		$assigScore.text(assigScore);  // 과제점수
		$prTestScore.text(prTestScore);  // 시험점수
		$ftTestScore.text(ftTestScore);  // 시험점수
		$etcScore.text(etcScore);  // 시험점수





	});//체인지 이벤ㅌ ㅡ끝

	const objcCn = document.querySelector("#objcCn"); // 다른 필드
	const lectNo = document.querySelector("#lectNo"); // form:select로 렌더링된 select 필드
	const stuId = document.querySelector("#stuId"); // 다른 필드
	const disForm = document.querySelector("#disForm"); // 폼 요소

	disForm.addEventListener("submit", async (e) => {
	e.preventDefault();

	const formData = new FormData(disForm);
	const jsonData = {};
	formData.forEach((value, key) => {
		jsonData[key] = value;
	});

	const upResp = await fetch(`${contextPath}/dissent`, {
		method: 'post',
		headers: {
			'Content-type': 'application/json'
		},
		body: JSON.stringify(jsonData)
	});

	if (upResp.ok) {
		// 새로운 강의 목록 데이터 가져오기
		const lectListResp = await fetch(`${contextPath}/dissent/newList`);
		const lectListData = await lectListResp.json();
	    
		// select 요소 가져오기
		const lectNoSelect = document.querySelector('[name="lectNo"]');
	    
		// select 옵션 초기화
		lectNoSelect.innerHTML = '<option value="">선택</option>';
	    
		// 새로운 옵션 추가
		lectListData.attenLectList.forEach(lect => {
			const option = document.createElement('option');
			option.value = lect.lectVO.lectNo;
			option.textContent = lect.lectVO.lectNm;
			lectNoSelect.appendChild(option);
		});

		// 나머지 필드 초기화
		document.getElementById('profeNm').textContent = '';
		document.getElementById('lectScore').textContent = '';
		document.getElementById('attenScore').textContent = '';
		document.getElementById('assigScore').textContent = '';
		document.getElementById('prTestScore').textContent = '';
		document.getElementById('ftTestScore').textContent = '';
		document.getElementById('etcScore').textContent = '';
		document.getElementById('objcCn').value = '';

		swal({
			content: {
				element: "img",
				attributes: {
					src: "../resources/NiceAdmin/assets/img/yglogo.png",
					alt: "Custom image",
					style: "width: 80px; height: 80px; display: block; margin: 0 auto;",
				},
			},
			title: "등록!",
			text: "이의 신청이 등록되었습니다.",
		});
	}
	const data = await upResp.json();
	console.log(data);
});



});


 */
async function dissentBtn(stuId, lectNo) {
	try {
		const coeva = document.querySelector("#coevaBox").value;
		const contextPath = document.querySelector("#contextPath").value;

		if (!stuId || !lectNo || !coeva) {
			throw new Error("Required parameters are missing");
		}

		const response = await fetch(`${contextPath}/lecture/${lectNo}/dissent`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				stuId: stuId,
				lectNo: lectNo,
				objcCn: coeva
			})
		});

		const result = await response.json();

		if (response.ok) {
			swal({
				title: '성공!',
				text: result.message,
				icon: 'success',
				confirmButtonText: '확인'
			}).then(() => {
				window.close();  // swal 알림창의 확인 버튼을 클릭한 후에 창이 닫힘
			});
		} else {
			throw new Error(result.message);
		}

	} catch (error) {
		swal({
			title: '오류',
			text: error.message || '이의신청 처리 중 오류가 발생했습니다.',
			icon: 'error',
			confirmButtonText: '확인'
		});
	}
}



function insertStr(){
	const coevaBox = document.querySelector("#coevaBox")
	coevaBox.value="특정 점수가 유난히 낮습니다."
}






















