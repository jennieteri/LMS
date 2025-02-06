// 등록 후 화면 전환
let selectTab = "english";

if (location.href.indexOf("?") != -1) {
	// 케이스에 따라서
	selectTab = location.href.split("?")[1].split("=")[1];
}

setTimeout(() => {
	document.querySelector(`#${selectTab}-tab`).click();
}, 20);

/**
 * Graduation 관련 스크립트
 */
document.addEventListener("DOMContentLoaded", () => {
	const cp = document.getElementById("cp").value;
	const principal = document.querySelector("#principal").value;
	const engInsertBtn = document.getElementById("enginsert");
	const volInsertBtn = document.getElementById("volinsert");
	const modalElement = document.getElementById("detailModal");
	const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
	const updateModal = new bootstrap.Modal(document.getElementById("graduationUpdateModal"));

	// 모달 요소
	const modalStuId = document.getElementById("modalStuId");
	const modalStuName = document.getElementById("modalStuName");
	const modalType = document.getElementById("modalType");
	const modalInst = document.getElementById("modalInst");
	const modalIssu = document.getElementById("modalIssu");
	const modalNm = document.getElementById("modalNm");
	const modalScore = document.getElementById("modalScore");
	const fileContainer = document.getElementById("fileContainer");
	const modalcodeName = document.getElementById("modalcodeName");

	// 수정 버튼
	const updateBtn = document.getElementById("updateBtn");
	const deleteBtn = document.getElementById("deleteBtn");


	// 모달 트리거 버튼 클릭 이벤트 처리
	document.querySelectorAll("[data-bs-toggle='modal']").forEach(button => {
		button.addEventListener("click", function() {
			const gdtCd = this.getAttribute("data-id");
			updateBtn.setAttribute("data-gdtCd", gdtCd);
			deleteBtn.setAttribute("data-gdtCd", gdtCd);

			// 데이터 초기화
			modalStuId.innerText = "";
			modalStuName.innerText = "";
			modalType.innerText = "";
			modalInst.innerText = "";
			modalIssu.innerText = "";
			modalNm.innerText = "";
			modalScore.innerText = "";
			modalcodeName.innerText = "";
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
					modalType.innerText = data.gdtType === "GT01" ? "봉사활동" : "영어인증";
					modalInst.innerText = data.gdtInst;
					modalIssu.innerText = data.gdtIssu;
					modalNm.innerText = data.gdtNm;
					modalScore.innerText = data.gdtScore;
					modalcodeName.innerText = data.codeName;

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
				})
				.catch(error => {
					console.error("오류 발생:", error);
					fileContainer.innerHTML = "<p class='text-danger'>파일 데이터를 가져오는 데 실패했습니다.</p>";
				});

			// 모달 열기
			modal.show();
		});
	});
	deleteBtn.addEventListener("click", function() {
		console.log("삭제버튼")
		const gdtCd = deleteBtn.getAttribute("data-gdtCd");
		if (!gdtCd) {
			console.error("gdtCd가 설정되지 않았습니다.");
			return;
		}
		fetch(`${cp}/graduation/detail/${gdtCd}`)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				const cocoCd = data.cocoCd;
				if (cocoCd === 'G001') {
					swal({
						title: "삭제 불가",
						text: "승인된 항목은 삭제할 수 없습니다.",
						icon: "warning",
						button: "확인",
					});
					return;
				}
				swal({
					title: "정말 삭제하시겠습니까?",
					icon: "warning",
					buttons: ["취소", "확인"],
					dangerMode: true,
				}).then((btnVal) => {
					if (btnVal) {
						axios.delete(`${cp}/graduation/delete/${gdtCd}`, {
						}).then((resp) => {
							console.log(resp);
							swal({
								title: "삭제완료",
								text: "졸업인증제가 성공적으로 수정되었습니다",
								icon: "success",
								button: "확인",
								
							}).then(()=>{
								console.log(data);
								const stuId = data.stuId;
								const gdtType = data.gdtType;
								if(gdtType == 'GT01'){
									window.location.href = `${cp}/graduation/list/${stuId}?tab=volunteer`;
								}else{
									window.location.href = `${cp}/graduation/list/${stuId}`
								}
							});
						});
					}
				});
			});
	});
	// 수정 버튼 클릭 시 수정 폼 모달 표시
	updateBtn.addEventListener("click", function() {

		const gdtCd = updateBtn.getAttribute("data-gdtCd");
		if (!gdtCd) {
			console.error("gdtCd가 설정되지 않았습니다.");
			return;
		}

		// /detail/{gdtCd}로 데이터 가져와 수정 폼에 채우기
		fetch(`${cp}/graduation/detail/${gdtCd}`)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				const cocoCd = data.cocoCd;

				if (cocoCd === 'G001') {
					swal({
						title: "수정 불가",
						text: "승인된 항목은 수정할 수 없습니다.",
						icon: "warning",
						button: "확인",
					});
					return;
				}
				// 수정 모달에 데이터 채우기
				let updateForm = document.forms.updateForm;
				document.getElementById("update-stuId").innerText = data.stuId;
				document.getElementById("update-stuNm").innerHTML = data.nm;
				updateForm.gdtNm.value = data.gdtNm;
				document.getElementById("update-Type").innerText = data.gdtType === "GT01" ? "봉사활동" : "영어인증";
				updateForm.gdtInst.value = data.gdtInst;
				updateForm.gdtIssu.value = data.gdtIssu;
				updateForm.gdtScore.value = data.gdtScore;

				const gdtNmContainer = document.getElementById("update-gdtNm-container");
				if (data.gdtType === "GT01") {
					// 봉사활동이면 select 박스로 전환
					gdtNmContainer.innerHTML = `
                    <select name="gdtNm" class="form-select">
                        <option value="교내봉사" ${data.gdtNm === "교내봉사" ? "selected" : ""}>교내봉사</option>
                        <option value="교외봉사" ${data.gdtNm === "교외봉사" ? "selected" : ""}>교외봉사</option>
                        <option value="전문봉사" ${data.gdtNm === "전문봉사" ? "selected" : ""}>전문봉사</option>
                        <option value="교육봉사(교직과정)" ${data.gdtNm === "교육봉사(교직과정)" ? "selected" : ""}>교육봉사(교직과정)</option>
                        <option value="해외봉사" ${data.gdtNm === "해외봉사" ? "selected" : ""}>해외봉사</option>
                        <option value="헌혈" ${data.gdtNm === "헌혈" ? "selected" : ""}>헌혈</option>
                        <option value="기타" ${data.gdtNm === "기타" ? "selected" : ""}>기타</option>
                    </select>
                `;
				} else if (data.gdtType === "GT02") {
					// 영어 인증이면 input 필드 유지
					gdtNmContainer.innerHTML = `
                    <input type="text" name="gdtNm" class="form-control" value="${data.gdtNm}">
                `;
				}

				// 수정 모달 열기
				modal.hide();
				updateModal.show();
			})
			.catch(error => console.error("Graduation 데이터를 가져오는 데 실패했습니다.", error));
	});

	// 저장 버튼 클릭 이벤트
	const saveUpdateBtn = document.getElementById("saveUpdateBtn");
	// 저장 버튼 클릭 이벤트
	saveUpdateBtn.addEventListener("click", function() {
		const gdtCd = updateBtn.getAttribute("data-gdtCd"); // gdtCd를 버튼 속성에서 가져옴
		if (!gdtCd) {
			console.error("gdtCd가 설정되지 않았습니다.");
			return;
		}
		const formData = new FormData(document.querySelector("#updateForm"));

		swal({
			title: "정말 수정하시겠습니까?",
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then((btnVal) => {
			if (btnVal) {
				axios.post(`${cp}/graduation/update/${gdtCd}`, formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				})
					.then((resp) => {
						console.log(resp);
						swal({
							title: "수정완료",
							text: "졸업인증제가 성공적으로 수정되었습니다",
							icon: "success",
							button: "확인",
						}).then(() => {
							// 상세보기 모달 다시 열기
							fetch(`${cp}/graduation/detail/${gdtCd}`)
								.then(response => response.json())
								.then(data => {
									// 데이터 설정
									modalStuId.innerText = data.stuId;
									modalStuName.innerText = data.nm;
									modalType.innerText = data.gdtType === "GT01" ? "봉사활동" : "영어인증";
									modalInst.innerText = data.gdtInst;
									modalIssu.innerText = data.gdtIssu;
									modalNm.innerText = data.gdtNm;
									modalScore.innerText = data.gdtScore;

									fileContainer.innerHTML = data.atchFile && data.atchFile.fileDetails && data.atchFile.fileDetails.length > 0
										? data.atchFile.fileDetails
											.map(file => `
	                                        <li>
	                                            <a href="${cp}/graduation/${data.gdtCd}/atch/${data.atchFileId}/${file.fileSn}">
	                                                ${file.orignlFileNm}
	                                            </a>
	                                        </li>
	                                    `)
											.join("")
										: "<p>첨부 파일 없음</p>";

									// 수정 모달 닫기 및 상세보기 모달 열기
									updateModal.hide();
									modal.show();
								})
								.catch(error => console.error("Graduation 데이터를 가져오는 데 실패했습니다.", error));
						});
					})
					.catch((err) => {
						console.error(err); // 디버깅을 위해 추가
						swal({
							title: "수정 실패",
							text: err.response?.data?.message || "알 수 없는 오류가 발생했습니다.",
							icon: "error",
							button: "확인",
						});
					});
			}
		});
	});

	// 등록 버튼 클릭 이벤트
	engInsertBtn.addEventListener("click", () => {
		const url = `${cp}/graduation/neweng/${principal}`;
		location.href = url;
	});

	volInsertBtn.addEventListener("click", () => {
		const url = `${cp}/graduation/newvol/${principal}`;
		location.href = url;
	});
});