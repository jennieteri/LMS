document.addEventListener("DOMContentLoaded", function() {
	// 모달 DOM 요소와 Bootstrap Modal 객체 가져오기
	const modalElement = document.getElementById("detailModal");
	const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
	const approveBtn = document.getElementById("approveBtn");
	const rejectBtn = document.getElementById("rejectBtn");

	// 공통 요소
	const cp = document.getElementById("cp").value;
	const modalStuId = document.getElementById("modalStuId");
	const modalStuName = document.getElementById("modalStuName");
	const modalType = document.getElementById("modalType");
	const modalInst = document.getElementById("modalInst");
	const modalIssu = document.getElementById("modalIssu");
	const modalNm = document.getElementById("modalNm");
	const modalScore = document.getElementById("modalScore");
	const fileContainer = document.getElementById("fileContainer");

	// 모달 트리거 버튼 클릭 이벤트 처리
	document.querySelectorAll("[data-bs-toggle='modal']").forEach(button => {
		button.addEventListener("click", function() {
			const gdtCd = this.getAttribute("data-id");

			// 데이터 초기화
			modalStuId.innerText = "";
			modalStuName.innerText = "";
			modalType.innerText = "";
			modalInst.innerText = "";
			modalIssu.innerText = "";
			modalNm.innerText = "";
			modalScore.innerText = "";
			fileContainer.innerHTML = "<p>첨부 파일 없음</p>";

			// 서버에서 데이터 가져오기
			fetch(`${cp}/graduation/detail/${gdtCd}`)
				.then(response => {
					if (!response.ok) {
						throw new Error(`HTTP error! Status: ${response.status}`);
					}
					return response.json();
				})
				.then(data => {
					// 데이터 설정
					modalStuId.innerText = data.stuId;
					modalStuName.innerText = data.nm;
					modalType.innerText = data.gdtType === 'GT01' ? '봉사활동' : '영어인증';
					modalInst.innerText = data.gdtInst;
					modalIssu.innerText = data.gdtIssu;
					modalNm.innerText = data.gdtNm;
					modalScore.innerText = data.gdtScore;

					console.log(data);
					// 첨부 파일 데이터 처리
					if (data.atchFile && data.atchFile.fileDetails && data.atchFile.fileDetails.length > 0) {
						fileContainer.innerHTML = data.atchFile.fileDetails
							.map(file => `
                                <li>
                                    <a href="${cp}/graduation/${data.gdtCd}/atch/${data.atchFileId}/${file.fileSn}">
                                        ${file.orignlFileNm}
                                    </a>
                                </li>
                            `)
							.join("");
					} else {
						fileContainer.innerHTML = "<p>첨부 파일 없음</p>";
					}

					// gdtCd를 approve 버튼에 저장
					approveBtn.setAttribute("data-gdtCd", gdtCd);
					rejectBtn.setAttribute("data-gdtCd", gdtCd);
				})
				.catch(error => {
					console.error("오류 발생:", error);
					fileContainer.innerHTML = "<p class='text-danger'>파일 데이터를 가져오는 데 실패했습니다.</p>";
				});

			// 모달 열기
			modal.show();
		});
	});

	// 승인 버튼 클릭 이벤트
	approveBtn.addEventListener("click", () => {
		const gdtCd = approveBtn.getAttribute("data-gdtCd"); // 버튼에 저장된 gdtCd 가져오기
		if (!gdtCd) {
			console.error("gdtCd가 설정되지 않았습니다.");
			return;
		}
		swal({
			title: "승인 처리하시겠습니까?",
			//text: ,
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal => {
			if (btnVal) {
				axios.post(cp + `/graduation/${gdtCd}/edit/access`)
					.then(resp => {
						console.log(resp);
						swal({
							title: "승인완료",
							text: "졸업인증제가 성공적으로 승인되었습니다",
							icon: "success",
							button: "확인"
						}).then(() => {
							location.href = cp + '/graduation/listbyemp';
						});
					}).catch(err => {
						swal({
							title: "승인실패",
							text: err.response.data.message,
							icon: "error",
							button: "확인"
						});
					})
			};
		});
	});
	
	// 승인 버튼 클릭 이벤트
	rejectBtn.addEventListener("click", () => {
		console.log("reject");
		const gdtCd = rejectBtn.getAttribute("data-gdtCd"); // 버튼에 저장된 gdtCd 가져오기
		if (!gdtCd) {
			console.error("gdtCd가 설정되지 않았습니다.");
			return;
		}
		swal({
			title: "반려 처리하시겠습니까?",
			//text: ,
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal => {
			if (btnVal) {
				axios.post(cp + `/graduation/${gdtCd}/edit/reject`)
					.then(resp => {
						console.log(resp);
						swal({
							title: "반려완료",
							text: "졸업인증제가 성공적으로 반려되었습니다",
							icon: "success",
							button: "확인"
						}).then(() => {
							location.href = cp + '/graduation/listbyemp';
						});
					}).catch(err => {
						swal({
							title: "반려실패",
							text: err.response.data.message,
							icon: "error",
							button: "확인"
						});
					})
			};
		});
	});
	
	
	
	
});
