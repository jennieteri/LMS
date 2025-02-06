document.addEventListener("DOMContentLoaded", () => {
	
	const contextPath = document.getElementById('contextPath').value;
	const lectNo = document.getElementById('lectNo').value;
	const mainBoardCount = document.getElementById('mainBoardCount').value; 
	//const cnbNo = document.getElementById('cnbNo').value;
	
	console.log("contextPath : ", contextPath)
	console.log("lectNo : ", lectNo)
	console.log("mainBoardCount : ", mainBoardCount)
	
	if (mainBoardCount === '3') {
	    // 체크박스 클릭 이벤트 리스너 추가
	    document.getElementById('boardMainYn').addEventListener('click', function(event) {
	        this.checked = false;  // 체크 해제
	        swal({
	            icon: 'warning',
	            title: '초과',
	            text: '메인 공지사항이 한도를 초과했습니다. (3개)'
	        });
	        // 클릭된 체크박스의 상태를 원래대로 유지하지 않도록 클릭을 취소
	        event.preventDefault();  // 클릭 이벤트 취소
	    });
	}
	
	// '데이터 입력' 버튼 눌럿을때
	document.getElementById("dummyData").addEventListener("click", function(event) {
		document.getElementById('boardTitle').value = "2025학년도 1학기 개강 일정 안내";
		editorInstance[0].setData(`
			안녕하세요, 학생 여러분!<br><br>
			
			2025학년도 1학기 개강 일정에 대해 아래와 같이 안내드립니다.<br><br>
			
			개강일: 2025년 3월 2일 (월요일)<br>
			수업 시작 시간: 오전 9시<br>
			신입생 오리엔테이션: 2025년 2월 28일 (금요일), 오후 2시<br>
			강의실 배정 및 시간표 확인: 학교 웹사이트에서 2월 20일부터 확인 가능합니다.<br><br>
			각 학과별 필수 사항은 해당 학과 사무실을 통해 별도로 안내드릴 예정이니, 빠짐없이 확인해 주세요.<br><br>
			
			개강 전까지 필요한 서류 제출 및 준비 사항에 대해 미리 확인하여, 학기 시작에 차질이 없도록 준비 바랍니다.<br><br>
			
			감사합니다.<br><br>
			
			[연근 대학교]
		`);
	});

	// '등록' 버튼 눌럿을때
	document.getElementById("submitBtn").addEventListener("click", function(event) {
	    // 클릭 시 동작을 처리
	    event.preventDefault(); // 기본 동작을 막고
		
		let fileForm = document.forms.fileForm; // 업로드 한 파일
		let formData = new FormData(fileForm);
		
		// 얘는 안들어갈수도 있으니까 직접 넣어줘야함
		let boardContent = editorInstance[0].getData(); // 게시글 내용
		formData.set('cnbNotes', boardContent); // 'append'는 중복 데이터가 들어갈수도 있으니까 'set'으로 
		
		let cnbMainYn = '';
		
		// 공지사항 고정 데이터
		if (document.getElementById('boardMainYn').checked) {
			cnbMainYn = 'Y';
		} else {
		    cnbMainYn = 'N';
		}
		formData.set('cnbMainYn', cnbMainYn); 
		
		for(let k of formData){
			console.log(k)
		}
		
		console.log(formData.get('cnbNm'));
		
		fetch(`${contextPath}/lecture/${lectNo}/board/insert`, {
		    method: 'POST',
		    body: formData
		})
		.then(response => response.json())  // JSON 형식으로 파싱
		.then(data => {
		    console.log("Success:", data);
			if (!formData.get('cnbNm') || formData.get('cnbNm').trim() === "") {
		        swal("오류", "제목을 입력해주세요.", "error");
		        return;
		    }
			if (!formData.get('cnbNotes') || formData.get('cnbNotes').trim() === "") {
		        swal("오류", "내용을 입력해주세요.", "error");
		        return;
		    }
			if (!formData.get('uploadFiles').name) {
		        swal("오류", "첨부파일을 넣어주세요.", "error");
		        return;
		    }
		    swal("성공", "강의 공지사항 등록 완료.", "success")
		    .then(() => {
		        window.location.href = `${contextPath}/lecture/${lectNo}/board`;
		    });
		})
		.catch(error => {
		    console.error('Error:', error);
		    swal("오류", "데이터 전송 중 문제가 발생했습니다. 다시 시도해주세요.", "error");
		});

	});


}); 