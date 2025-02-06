/**
	  과제 스크립트 

 */

document.addEventListener("DOMContentLoaded", () => {
	const submitBtn = document.querySelector('#submitBtn');
	const submitFormArea = document.querySelector('#submitFormArea');
	const userId = document.querySelector('#form-table').dataset.auth;
	const contextPath = document.querySelector('#form-table').dataset.path;
	const lectNo = document.querySelector('#lectNo').value;
	const resultArea = document.querySelector('#resultArea');
	const delForm = document.querySelector('#delForm');
	
	delForm?.addEventListener("submit", async (event) => {
        event.preventDefault(); // 기본 폼 제출 동작 방지

        const assigNo = delForm.querySelector("input[name='assigNo']").value;
        // 삭제 확인
        const confirmDelete = await swal({
            title: "삭제 확인",
            text: "정말로 삭제하시겠습니까?",
            icon: "warning",
            buttons: ["취소", "삭제"],
            dangerMode: true,
        });

        if (!confirmDelete) return;

        try {
            const response = await fetch(`${contextPath}/lecture/${lectNo}/assignment/delete`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // JSON Content-Type
                },
                body: JSON.stringify({ assigNo }), // JSON으로 직렬화
            });

            if (response.ok) {
				const data = await response.json();
                swal("삭제 완료", data.message||"과제가 성공적으로 삭제되었습니다.", "success").then(()=>{
					location.href=`${contextPath}/lecture/${lectNo}/assignment`;
				});
                // 성공 처리
            } else {
                const errorData = await response.json();
                swal("삭제 실패", errorData.message || "삭제 중 문제가 발생했습니다.", "error");
            }
        } catch (error) {
            console.error("삭제 중 오류:", error);
            swal("오류 발생", "서버와 통신 중 문제가 발생했습니다.", "error");
        }
    });
	
	
	document.body.addEventListener("click", async (e) => {
	    if (e.target.classList.contains("file-delete-btn")) {
	        e.preventDefault();
	        const el = e.target;
	        const atchFileId = el.dataset.atchFileId;
	        const fileSn = el.dataset.fileSn;
	
	        let resp = await fetch(`${contextPath}/atch/${atchFileId}/${fileSn}`, {
	            method: "DELETE",
	            headers: {
	                "Accept": "application/json",
	            },
	        });
	
	        if (resp.ok) {
	            let obj = await resp.json();
	            if (obj.success) {
	                el.parentElement.remove();
	            } else {
	                swal("실패", "파일 삭제에 실패했습니다.", "error");
	            }
	        } else {
	            swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
	        }
	    }
	});


	if(submitBtn) {
		submitBtn.addEventListener("click", () => {
			const endDate = new Date(submitBtn.dataset.ed);
			const today = new Date();
			const assigNo = submitBtn.dataset.assigno;

			if(today <= endDate){
				submitFormArea.innerHTML = `
				<h2 class="text-center">과제 제출 양식</h2>
                <form id="submissionForm" enctype="multipart/form-data" method="POST" action="${contextPath}/lecture/${lectNo}/assignmentSubmission">
                    <table class="table table-bordered">
                        <tr class="text-center">
                            <th>학번</th>
                            <td>
                                <input type="text" class="form-control" id="stuId" name="stuId" value="${userId}" readonly />
                            </td>
                        </tr>
                        <tr class="text-center">
                            <th>첨부파일</th>
                            <td>
                                <input type="file" class="form-control" name="uploadFiles" multiple required />
                            </td>
                        <tr class="text-center">
                            <th>내용</th>
                            <td>
                                <textarea class="form-control" name="assubNotes" rows="4" placeholder="과제 내용을 입력하세요." required></textarea>
                            </td>
                        </tr>
                        </tr>
                    </table>
                    <div class="text-end">
						<input type="hidden" name="assigNo" value="${assigNo}" >
                        <button type="submit" class="btn btn-primary">저장</button>
                    </div>
                </form>
			`;
			}
			else {
				swal("실패", "제출마감일이 지났습니다.", "error");
			}


		});//submitBtn 이벤트
	}
	//과제제출처리
	document.body.addEventListener("submit", async (e) => {
		if (e.target && e.target.id == "submissionForm") {
			e.preventDefault();

			const submissionForm = e.target;
			// 폼 데이터 생성
			const formData = new FormData(submissionForm);

			try {
				// fetch로 데이터 전송
				const response = await fetch(`${contextPath}/lecture/${lectNo}/assignmentSubmission`, {
					method: "POST"
					, body: formData
				});

				// 응답 처리
				if (response.ok) {

					const result = await response.json();
					swal("성공", result.message || "과제가 성공적으로 제출되었습니다.", "success").then(() => {
						submitFormArea.innerHTML = ""; // 제출 완료 후 폼 제거
						window.location.reload();
					})
				} else {
					const error = await response.json();
					swal("실패", error.message || "제출 중 문제가 발생했습니다.", "error");
				}
			} catch (err) {
				swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
			}
		}

	});

	if (resultArea) {
	    document.body.addEventListener("click", async (e) => {
	        if (e.target && e.target.id === 'editSubmissionBtn') {
	            const stuId = e.target.dataset.id;
	            const assigNo = e.target.dataset.assigno;
	            const lectNo = e.target.dataset.lectno;
	
	            try {
	                let resp = await fetch(`${contextPath}/lecture/${lectNo}/assignmentSubmission/detail/${assigNo}/${stuId}`);
	                if (resp.ok) {
	                    let data = await resp.json();
	                    console.log(data);
	
	                    let fileDetailsHTML = '';
	                    if (data.atchFile && data.atchFile.fileDetails.length > 0) {
	                        fileDetailsHTML = data.atchFile.fileDetails.map((fd, index) => `
	                            <span>
	                                ${fd.orignlFileNm} [${fd.fileFancysize}]
	                                <a 
	                                    data-atch-file-id="${fd.atchFileId}" 
	                                    data-file-sn="${fd.fileSn}" 
	                                    class="btn btn-danger btn-sm file-delete-btn" 
	                                    href="javascript:;">
	                                    X
	                                </a>
	                                ${index < data.atchFile.fileDetails.length - 1 ? '|' : ''}
	                            </span>
	                        `).join('');
	                    } else {
	                        fileDetailsHTML = '기존첨부파일없음';
	                    }
	
	                    resultArea.innerHTML = `
	                        <form action="${contextPath}/lecture/${lectNo}/assignmentSubmission/edit" id="editSubmissionForm" method="POST" enctype="multipart/form-data">
	                            <table class="table table-bordered">
	                                <tr class="text-center">
	                                    <th>기존파일</th>
	                                    <td>
	                                        ${fileDetailsHTML}
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th class="text-center">첨부파일</th>
	                                    <td>
	                                        <input type="file" class="form-control" name="uploadFiles" multiple />
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th class="text-center">내용</th>
	                                    <td>
	                                        <textarea class="form-control" name="assubNotes" rows="4" required>${data.assubNotes}</textarea>
	                                    </td>
	                                </tr>
	                            </table>
	                            <div class="text-end">
									<input type="hidden" name="stuId" value="${userId}">
									<input type="hidden" name="lectNo" value="${lectNo}">
									<input type="hidden" name="assigNo" value="${data.assigNo}">
	                                <button id="editSubmitBtn" type="submit" class="btn btn-primary"
										>저장</button>
	                                <button type="button" id="cancelEditBtn" class="btn btn-secondary">취소</button>
	                            </div>
	                        </form>
	                    `;
	                } else {
	                    console.error(`HTTP 오류 발생: ${resp.status}`);
	                    swal("실패", "데이터를 가져오는 데 실패했습니다.", "error");
	                }
	            } catch (error) {
	                console.error("통신 오류:", error);
	                swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
	            }
	        }
	
	        // 취소 버튼 처리
	        if (e.target && e.target.id === 'cancelEditBtn') {
	            window.location.reload(); // 페이지 새로고침
	        }
	    });
	}
	
	//수정요청이벤트
	 document.body.addEventListener("submit", async (e) => {
        if (e.target && e.target.id == 'editSubmissionForm') {
            e.preventDefault();
			console.log(e.target);
			
            const editForm = e.target;
            const formData = new FormData(editForm);

            try {
                const response = await fetch(editForm.action, {
                    method: "POST"
                    ,body: formData
                });

                if (response.ok) {
                    const result = await response.json();
                    swal("성공", result.message || "수정이 성공적으로 완료되었습니다.", "success").then(() => {
                        window.location.reload(); // 수정 완료 후 새로고침
                    });
                } else {
                    const error = await response.json();
                    swal("실패", error.message || "수정 중 문제가 발생했습니다.", "error");
                }
            } catch (error) {
                swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
                console.error("통신 오류:", error);
            }
	
        }


    });
		//제출회수처리
		
		document.body.addEventListener("click",(e)=>{
			if(e.target  && e.target.id == 'delBtn'){
				swal({
		            title: "제출회수하시겠습니까?",
		            text: "제출회수한 과제는 복구할 수 없습니다.",
		            icon: "warning",
		            buttons: {
	                cancel: "취소", // 취소 버튼
	                confirm: {
	                    text: "확인", // 확인 버튼
	                    value: true,
	                	},
	            	}
				}).then(async (willDelete)=>{
					if(willDelete){
						const stuId = e.target.dataset.id;
						const assigNo = e.target.dataset.assigno;
						const lectNo = e.target.dataset.lectno;
						assignmentSubmission = {
							"stuId" : stuId
							,"assigNo" : assigNo
							,"lectNo" : lectNo
						}
						try{
							let resp = await fetch(`${contextPath}/lecture/${lectNo}/assignmentSubmission/${assigNo}`,{
								method: 'PUT'
								,headers: {
									'content-type':'application/json'
								},body: JSON.stringify(assignmentSubmission)
							})
							if(resp.ok){
								const result = await resp.json();
		                        swal("성공", result.message || "과제가 성공적으로 회수되었습니다.", "success").then(() => {
		                            window.location.reload(); // 페이지 새로고침
		                        });
							}else {
		                        const error = await resp.json();
		                        swal("실패", error.message || "제출 회수 중 문제가 발생했습니다.", "error");
	                    	}
						}catch(e){
							swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
	                    	console.error("통신 오류:", e);
						}
					}else{
						swal("취소완료","취소하였습니다!","info")
					}
				})
			}
		});
		
		//피어리뷰 제출폼 불러오기
		const peerBtn = document.querySelector('#peerBtn');
		
		if(peerBtn){
			document.body.addEventListener("click",async (e)=>{
				if (e.target && e.target.id === "peerBtn") {
				const stuId = e.target.dataset.id;
				const assigNo = e.target.dataset.assigno;
				const lectNo = e.target.dataset.lectno;
				
				try{
					let resp = await fetch(`${contextPath}/lecture/${lectNo}/assignmentSubmission/peer/${assigNo}/${stuId}`)
					if(resp.ok){
						const data = await resp.json();
						const modalBody = document.querySelector('#peerReviewModal .modal-body');
						//피어리뷰대상정보 저장
						document.querySelector("#savePeerReviewBtn").dataset.assigno = data.assigNo;
						
						if(data.assubYn == 'N'){
							swal("피어리뷰작성불가","피어리뷰대상이 과제제출하지않았습니다.","info").then(()=>{
								window.location.reload();
							});
						}
						let fileDetailsHTML = '';
	                    if (data.atchFile && data.atchFile.fileDetails.length > 0) {
	                        fileDetailsHTML = data.atchFile.fileDetails.map((fd, index) => `
	                            <span>
						            <a href="${contextPath}/atch/${fd.atchFileId}/${fd.fileSn}">
						                ${fd.orignlFileNm} [${fd.fileFancysize}]
						            </a>
						            ${index < data.atchFile.fileDetails.length - 1 ? ' | ' : ''}
						        </span>
	                        `).join('');
	                    } else {
	                        fileDetailsHTML = '첨부파일없음';
	                    }
						
						modalBody.innerHTML = `
							<table class="table table-bordered">
							<tr>
								<th>첨부파일</th>
								<td>
									${fileDetailsHTML}
								</td>
							</tr>
							<tr>
								<td colspan="2">${data.assubNotes}</td>
							</tr>
							<tr>
								<th>평가점수</th>
								<td class="text-center">
					                ${[1, 2, 3, 4, 5].map(
					                  (i) =>
					                    `<span class="star" style="font-size: 2rem; cursor: pointer;" data-score="${i}">☆</span>`
					                ).join("")}
			              		</td>
							</tr>
							</table>
						
						`;//모달바디 끝
						
						 // 별점 클릭 이벤트 추가
				         modalBody.addEventListener("click", (event) => {
		                    if (event.target && event.target.classList.contains("star")) {
		                        const selectedScore = event.target.dataset.score;
		
		                        // 모든 별 초기화
		                        document.querySelectorAll(".star").forEach((star) => {
		                            star.textContent = "☆";
		                            star.style.color = "black";
		                        });
		
		                        // 선택된 점수만큼 별 채우기
		                        for (let i = 0; i < selectedScore; i++) {
		                            const star = document.querySelector(`.star[data-score="${i + 1}"]`);
		                            star.textContent = "★";
		                            star.style.color = "gold";
		                        }
								 // 별점 점수를 데이터 속성에 저장
                        		document.querySelector("#savePeerReviewBtn").dataset.selectedscore = selectedScore;
		                        console.log("선택된 점수:", selectedScore);
		                    }
		                });
				
				        // 모달 표시
				        const peerReviewModal = new bootstrap.Modal(document.getElementById("peerReviewModal"));
				        peerReviewModal.show();
					}else{
						swal("실패", "데이터를 가져오는 데 실패했습니다.", "error");
					}
				}catch(e){
					console.error("통신 오류:", e);
      				swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
				}
				
				}
				if(e.target.id == 'savePeerReviewBtn'){
					const assubPeerScr = e.target.dataset.selectedscore;
					
					//피어리뷰저장버튼 누른 사람 id값 받아오기
					const stuId = document.querySelector('#peerBtn').dataset.id;
					const lectNo = document.querySelector('#peerBtn').dataset.lectno;
					const assigNo = e.target.dataset.assigno;
					
					if(!assubPeerScr){
						swal("실패", "평가 점수를 입력하지않았습니다.", "warning");
            			return;
					}
					const peerData = {
						stuId: stuId
						,assigNo: assigNo
						,assubPeerScr: assubPeerScr
					};
					
					try{
						let resp = await fetch(`${contextPath}/lecture/${lectNo}/assignmentSubmission/peer/${assigNo}/${stuId}`,{
							method: 'POST'
							,headers:{
								'Content-type': 'application/json'
							}
							,body: JSON.stringify(peerData)
						})
						if(resp.ok){
							const result = await resp.json();
							swal("성공", result.message || "피어리뷰가 저장되었습니다.", "success").then(() => {
			                    const peerReviewModal = bootstrap.Modal.getInstance(document.getElementById("peerReviewModal"));
			                    peerReviewModal.hide();
			                    window.location.reload(); // 새로고침
			                });
						}else{
							const error = await resp.json();
                			swal("실패", error.message || "피어리뷰 저장 중 문제가 발생했습니다.", "error");
						}
						
					}catch(e){
						 swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
           				 console.error("통신 오류:", e);
					}
					
					
				}	
			});//피어리뷰모달창
		}








});//DOM 끝