


function change(testNo, status) {
   let newButton;

    switch(status) {
        case 'ok':
            newButton = `
								<button  class="btn btn-success" id="waiting-button">승인</button>
								<button style="display: none;" class="btn btn-primary" onclick="change('${testNo }','wa')">대기</button>
								<button style="display: none;" class="btn btn-warning" onclick="change('${testNo }','no')">반려</button>
								<button style="display: none;" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
								`
	            break;
        case 'no':
            newButton = `
								<button  class="btn btn-warning" id="waiting-button" >반려</button>
								<button style="display: none;" class="btn btn-success" onclick="change('${testNo }','ok')">승인</button>
								<button style="display: none;" class="btn btn-primary" onclick="change('${testNo }','wa')">대기</button>
								<button style="display: none;" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
`;
            break;
        case 'wa':
           newButton = `
								<button class="btn btn-primary" id="waiting-button">대기</button>
								<button style="display: none;" class="btn btn-success" onclick="change('${testNo }','ok')">승인</button>
								<button style="display: none;" class="btn btn-warning" onclick="change('${testNo }','no')">반려</button>
								<button style="display: none;S" id="exit-button" class="btn btn-danger"><i class="bi bi-backspace"></i></button>
`;
            break;
    }

    let testStatusElement = document.getElementById('test-status');
    testStatusElement.innerHTML = newButton;

    console.log(testNo, status);
    testCheck(testNo, status);
}

/**
function showApprovalButtons() {
    let approvalButtons = document.getElementById('approval-buttons');
    approvalButtons.style.display = approvalButtons.style.display === 'none' ? 'block' : 'none';
}
function showApprovalButtons() {
	document.getElementById('approval-buttons').style.display = 'block';
	document.getElementById('waiting-button').style.display = 'none';
}
 */


async function testCheck(testNo, ch) {
	const url = "/yguniv/test/profe";
	const pUrl = `${url}/${testNo}/${ch}`
	console.log(pUrl);
	const resp = await fetch(pUrl)

	if (resp.ok) {
		swal({
			title: "성공",
			text: "변경되었습니다.",
			icon: "success",
			button: "확인"
		});

		//등록이 성공하면 리스트 최신화 하기 + 라디오 버튼 검색조건 추가하기
	} else {
		swal({
			title: "추가실패",
			text: "입력실패",
			icon: "error",
			button: "확인"
		});
	}
}

document.addEventListener("DOMContentLoaded",()=>{
	$(document).on('click','#waiting-button',function(){
		let $td = $(this).parents('td');
		$td.find('button').toggle();
		
	})
	$(document).on('click','#exit-button',function(){
		let $td = $(this).parents('td');
		$td.find('button').toggle();
		
	})
	
})






















