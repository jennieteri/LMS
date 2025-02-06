/**
 * 
 */
async function showConfirm(button) {
	const testNo = button.dataset.testNo;
	const lectNo = button.dataset.lectNo;
	const contextPath = document.getElementById('contextPath').value;

	swal({
		title: "시험을 응시하시겠습니까?",
		text: "응시를 시작하려면 예를 클릭하세요.",
		icon: "warning",
		buttons: ["취소", "응시"],
	}).then(async function(isConfirm) {
		if (isConfirm) {
			try {
				const resp = await fetch(`${contextPath}/lecture/${lectNo}/attendeeTest/attendeeTest/${testNo}`);

				if (resp.ok) {
					window.open(`${contextPath}/lecture/${lectNo}/attendeeTest/${testNo}`,
						"testPopup",
						"width=1500,height=700,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
				} else {
					const errorData = await resp.json();
					swal("오류", errorData.message, "error");
				}
			} catch (error) {
				swal("오류", "네트워크 오류가 발생했습니다.", "error");
			}
		} else {
			swal("취소되었습니다!", "응시를 취소했습니다.", "error");
		}
	});
}

// 팝업 창 열기
//            window.open(`${contextPath}/lecture/${lectNo}/attendeeTest/attendeeTest/${testNo}`, 
//                       "testPopup", 
//                        "width=1000,height=700,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
function getProfessorAttendeeTestListPage(button) {
	const testNo = button.dataset.testNo;
	const lectNo = button.dataset.lectNo;
	const contextPath = document.getElementById('contextPath').value;

	console.log(contextPath);

	location.href = `${contextPath}/lecture/${lectNo}/attendeeTest/professor/${testNo}`

}
function getProfessorNewTestPage(button) {
	const lectNo = button.dataset.lectNo;
	const testSe = button.dataset.testSe;

	// Context Path 가져오기
	const contextPath = document.getElementById('contextPath').value;
	location.href = `${contextPath}/test/${lectNo}/new/${testSe}`


}
function getProfessorDetailTestPage(button) {
	const lectNo = button.dataset.lectNo;
	const testNo = button.dataset.testNo;
	// Context Path 가져오기
	const contextPath = document.getElementById('contextPath').value;

	location.href = `${contextPath}/test/${testNo}`


}

/**
async function testSSE(button){
	const lectNo = button.dataset.lectNo;
	const testNo = button.dataset.testNo;
	const contextPath = document.getElementById('contextPath').value;
	console.log(lectNo)
	console.log(testNo)
	
	const resp = await fetch(`${contextPath}/lecture/${lectNo}/attendeeTest/test/api/${testNo}/sse`)
	
	const data =resp.json();
	
	console.log(data)

}
 */















