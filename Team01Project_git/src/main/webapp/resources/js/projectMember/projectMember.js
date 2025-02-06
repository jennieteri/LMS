/**
 * 
 */
document.addEventListener("DOMContentLoaded", () => {
    const taskSelect = document.querySelector('#taskSelect');
    const contextPath = taskSelect.dataset.path;
    const lectNo = taskSelect.dataset.lectno;
    const teamList = document.querySelector('#teamList');
    const attendeeList = document.querySelector('#attendeeList');
	const leaderArea = document.querySelector('#leaderArea');
    const teamSelect = document.querySelector('#teamSelect');
    const manualBtn = document.querySelector('#manualBtn');
	const autoBtn = document.querySelector('#autoBtn');
	
	// 자동배정 버튼 클릭 시 모달로 1팀당 배정될 인원 수 입력 받는 폼 
	autoBtn.addEventListener("click", async ()=>{
		const taskNo = taskSelect.value;
		if(!taskNo){
			swal("CHECK!","과제를 선택해주세요!","info");
			return;
		}
		
		try{
			const resp =await fetch(`${contextPath}/lecture/${lectNo}/projectMember/getCount/${taskNo}`)
			if(resp.ok){
				const data = await resp.json();
				const teamCount = data.teamCount;
				const noTeamAttendeeCount = data.noTeamAttendeeCount;
				
				showAutoAssignForm(teamCount,noTeamAttendeeCount,taskNo);
			}
			else {
            swal("ERROR", "팀 데이터를 가져오는 데 실패했습니다.", "error");
       		}
		}
		catch(error){
			console.error("오류 발생:", error);
		}
	});// autoBtn Event 끝
	
	//모달생성
	function showAutoAssignForm(teamCount,noTeamAttendeeCount,taskNo) {
	    const modalContent = `
	        <div class="modal fade" id="autoAssignModal" tabindex="-1" aria-labelledby="autoAssignModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="autoAssignModalLabel">자동배정 설정</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			                <table class="table table-bordered">
			                    <tbody>
			                        <tr>
			                            <th scope="row" style="width: 50%;">현재 개설된 팀 수:</th>
			                            <td><strong>${teamCount}</strong></td>
			                        </tr>
			                        <tr>
			                            <th scope="row">
			                                <label for="memberCount" class="form-label mb-0">팀 별 배치할 인원 수</label>
			                            </th>
			                            <td>
			                                <input type="number" id="memberCount" class="form-control" min="1">
			                            </td>
			                        </tr>
									<tr>
										<th scope="row">
									        <label class="form-label mb-0">작은 팀 허용</label>
									    </th>
									    <td>
									        <div class="d-flex align-items-center gap-3">
									            <div class="form-check">
									                <input type="radio" id="allowSmallTeam" name="smallTeamOption" value="Y" class="form-check-input">
									                <label for="allowSmallTeam" class="form-check-label">허용</label>
									            </div>
									            <div class="form-check">
									                <input type="radio" id="denySmallTeam" name="smallTeamOption" value="N" class="form-check-input">
									                <label for="denySmallTeam" class="form-check-label">비허용</label>
									            </div>
									        </div>
									    </td>
									</tr>
			                    </tbody>
			                </table>
			            </div>
			            <div class="modal-footer">
			                <button type="button" id="previewBtn" class="btn btn-primary">저장</button>
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			            </div>
			        </div>
			    </div>
			</div>
	    `;
	
	    document.body.insertAdjacentHTML('beforeend', modalContent);
	    const modal = new bootstrap.Modal(document.getElementById('autoAssignModal'));
	    modal.show();
		//미리보기 버튼 클릭 이벤트
	    document.getElementById('previewBtn').addEventListener('click', () => previewAutoAssign(teamCount,noTeamAttendeeCount, taskNo));
	}//모달생성끝
	
	//미리보기 처리
	async function previewAutoAssign(teamCount,noTeamAttendeeCount, taskNo) {
	    const memberCount = document.getElementById('memberCount').value;
		const allowYn = document.querySelector('input[name="smallTeamOption"]:checked').value;
		
	    if (!memberCount || memberCount <= 0) {
	        swal("ERROR", "팀당 배치할 인원 수를 입력하세요.", "error");
	        return;
	    }
		if(memberCount>noTeamAttendeeCount){
			swal("ERROR", "배치되지않은 수강생보다 많은값의 인원을 배치할 수 없습니다.", "error");
	        return;
		}
		if((teamCount*memberCount)>noTeamAttendeeCount){
			if(allowYn == 'N'){
				swal("Error", "인원이 부족합니다. 작은 팀을 허용해주세요.", "error");
            	return;
			}
		}
	
	    try {
	        const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/getPreview`, {
	            method: 'POST',
	            headers: { 'Content-Type': 'application/json' },
	            body: JSON.stringify({ lectNo, taskNo, teamCount, memberCount, allowYn  })
	        });
	
	        if (resp.ok) {
	            const data = await resp.json();
				console.log(data);
				//TODO----
				// 배정 결과 표시
	            if (data.projectTeamList) {
		
					showPreviewModal(data.projectTeamList);
					
					swal("SUCCESS", data.message || "자동배정 완료!", "success")
					
					document.getElementById("reassignBtn").onclick = () => {
		                resetAndReassignTeams(taskNo, teamCount, memberCount, allowYn);
		            };
	            }
				
	        } else {
				const errors = await resp.json();
				
	            swal("ERROR", errors.message || "자동배정을 가져오는 데 실패했습니다.", "error");
	        }
	    } catch (error) {
	        console.error("오류 발생:", error);
	    }
	};//자동배정 처리
	
	
    // 팀원 목록 및 팀에 속하지 않은 수강생 데이터를 렌더링
    manualBtn?.addEventListener("click", async () => {
        const taskNo = taskSelect.value;
        const teamCd = teamSelect.value;
		const selectedOption = document.querySelector('#teamSelect').selectedOptions[0];
		const rnum = selectedOption.dataset.rnum;
		
        if (!taskNo || !teamCd) {
            swal("CHECK!", "과제와 팀을 선택해주세요.", "info");
            return;
        }
			
        try {
            const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/getMemberAndAttendee/${teamCd}/${taskNo}`);
            if (resp.ok) {
                const data = await resp.json();
				
                renderLeaderAndTeamList(leaderArea, teamList, data.projectMemberList, `${rnum}팀 팀원 목록`, true);
                renderList(attendeeList, data.noTeamAttendeeList, "수강생 목록", true);
				setupDropListeners();
            } else {
                console.error("데이터를 가져오는 데 실패했습니다.");
            }
        } catch (error) {
            console.error("오류 발생:", error);
        }
    });

    // 수강생 리스트 렌더링 함수
    function renderList(container, items, title, isDraggable = false) {
        container.innerHTML = ''; // 기존 내용을 초기화
		const cardHeader = container.closest('.card').querySelector('.card-header');
        if (cardHeader) {
            cardHeader.innerHTML = `<h5 class="card-title mb-0">${title}</h5>`;
        }
		
        items.forEach(item => {
            const li = document.createElement("li");
            li.classList.add("list-group-item");
            li.textContent = `${item.studentVO.nm} (${item.stuId})`;
            li.dataset.studentId = item.stuId;
			
            if (isDraggable) {
                li.setAttribute("draggable", "true");
                setupDragAndDrop(li);
            }

            container.appendChild(li);
        });
    }

	// 조장과 팀원을 나누어 렌더링하는 함수
	function renderLeaderAndTeamList(leaderContainer, teamContainer, members, teamTitle) {
	    // 조장 영역 초기화
	    leaderContainer.innerHTML = '';
	
	    // 팀원 목록 초기화
	    teamContainer.innerHTML = '';
	    const cardHeader = teamContainer.closest('.card').querySelector('.card-header');
	    if (cardHeader) {
	        cardHeader.innerHTML = `<h5 class="card-title mb-0">${teamTitle}</h5>`;
	    }
	
	    members.forEach(member => {
	        const li = document.createElement("li");
	        li.classList.add("list-group-item");
	        li.textContent = `${member.studentVO.nm} (${member.stuId})`;
	        li.dataset.studentId = member.stuId;
	
	        // 조장 여부 확인
	        if (member.leadYn === 'Y') {
	            leaderContainer.appendChild(li);
	        } else {
	            teamContainer.appendChild(li);
	        }
	
	        // 조장과 팀원 모두 드래그 가능
	        li.setAttribute("draggable", "true");
	        setupDragAndDrop(li);
	    });
	}
	
	function showPreviewModal(teamList) {
		const taskNo = taskSelect.value;
		
		const autoAssignModal = document.getElementById("autoAssignModal");
	    if (autoAssignModal) {
	        const bootstrapModal = bootstrap.Modal.getInstance(autoAssignModal);
	        if (bootstrapModal) {
	            bootstrapModal.hide();
	        }
	    }
		
	    // 팀 리스트 렌더링
	    renderAllTeams(teamList, "previewContainer");
	
	    // 부트스트랩 모달 초기화 및 표시
	    const previewModal = new bootstrap.Modal(document.getElementById("previewModal"));
	    previewModal.show();
	
	    // 완료 버튼 이벤트
	    document.getElementById("confirmBtn").onclick = async () => {
	        swal("SUCCESS", "팀 배정이 완료되었습니다.", "success").then(() => {
	            previewModal.hide();
	            location.reload(); // 페이지 새로고침
	        });
	    };
		
		// 취소 버튼 이벤트
	    document.getElementById("cancelBtn").onclick = () => {
	        swal({
	            title: "취소하시겠습니까?",
	            text: "현재 배정된 팀 구성이 초기화됩니다.",
	            icon: "warning",
	            buttons: ["아니오", "예"],
	            dangerMode: true,
	        }).then((willCancel) => {
	            if (willCancel) {
	                // 팀 초기화 로직
					 resetTeams(taskNo)
		                .then((message) => {
		                    previewModal.hide(); // 미리보기 모달 닫기
		                    swal("초기화 완료", message, "success").then(()=>{
								location.reload();
							});
		                })
		                .catch((errorMessage) => {
		                    swal("초기화 실패", errorMessage, "error").then(()=>{
								location.reload();
							});
		                });
	            }
	        });
	    };
		
	    // 다시 배정 버튼 이벤트
	    document.getElementById("reassignBtn").onclick = () => {
	        previewModal.hide();
	        // 다시 배정 로직 호출
	        reassignTeams();
	    };
	}
	
	// 팀 초기화 함수
	async function resetTeams(taskNo) {
	    try {
	        const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/reset`, {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({ taskNo }),
	        });
	
	        const data = await resp.json();
	
	        if (!resp.ok) {
	            throw new Error(data.message || "초기화에 실패했습니다.");
	        }
	
	        return data.message; 
	    } catch (error) {
	        throw error.message || "초기화 중 문제가 발생했습니다.";
	    }
	}
	
	// 다시 배정 함수
	async function resetAndReassignTeams(taskNo, teamCount, memberCount, allowYn) {
	    try {
	        // 팀 초기화 요청
	        const resetResponse = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/reset`, {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({ taskNo }),
	        });
	
	        const resetData = await resetResponse.json();
	        if (!resetResponse.ok) {
	            swal("ERROR", resetData.message || "팀 초기화에 실패했습니다.", "error");
	            return;
	        }
	
	        // 다시 배정 요청
	        const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/getPreview`, {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({ lectNo, taskNo, teamCount, memberCount, allowYn }),
	        });
	
	        if (resp.ok) {
	            const data = await resp.json();
	
	            // 배정 결과 렌더링
	            renderAllTeams(data.projectTeamList, "previewContainer");
	
	            // 미리보기 완료 메시지
	            swal("SUCCESS", data.message || "다시 배정 완료!", "success");
	        } else {
	            const errors = await resp.json();
	            swal("ERROR", errors.message || "배정을 가져오는 데 실패했습니다.", "error");
	        }
	    } catch (error) {
	        console.error("오류 발생:", error);
	        swal("ERROR", "배정 처리 중 오류가 발생했습니다.", "error");
	    }
	}
	
	
	
	//팀리스트로 팀별로
	function renderAllTeams(teamList, containerId) {
	    const container = document.getElementById(containerId);

		 // 객체형 배열을 배열로 변환
	     const teamArray = Array.isArray(teamList) ? teamList : Object.values(teamList);
	
	     if (!Array.isArray(teamArray) || teamArray.length === 0) {
	        console.error("팀 데이터가 유효하지 않습니다.");
	        container.innerHTML = "<p>배정된 팀이 없습니다.</p>";
	        return;
	     }

	    container.innerHTML = ''; // 기존 콘텐츠 초기화
		let index = 1;
		//TODO forEach 오류---------------------------------------------------------
	    teamArray.forEach((team) => {
	        // 팀 카드 생성
	        const teamCard = document.createElement('div');
	        teamCard.classList.add('card', 'mb-3');
	
	        // 카드 헤더 (팀 이름)
	        const cardHeader = document.createElement('div');
	        cardHeader.classList.add('card-header');
	        cardHeader.innerHTML = `<h5 class="card-title mb-0">${team.teamNm}(${index}번째팀)</h5>`;
	        teamCard.appendChild(cardHeader);
	
	        // 카드 바디
	        const cardBody = document.createElement('div');
	        cardBody.classList.add('card-body');
	        teamCard.appendChild(cardBody);
	
	        // 조장 리스트
	        const leaderContainer = document.createElement('ul');
	        leaderContainer.classList.add('list-group', 'droppable');
	        leaderContainer.id = `leaderArea-${team.teamCd}`;
	        const leaderTitle = document.createElement('h6');
	        leaderTitle.textContent = '조장';
	        cardBody.appendChild(leaderTitle);
	        cardBody.appendChild(leaderContainer);
	
	        // 팀원 리스트
	        const teamContainer = document.createElement('ul');
	        teamContainer.classList.add('list-group', 'droppable');
	        teamContainer.id = `teamList-${team.teamCd}`;
	        const teamTitle = document.createElement('h6');
	        teamTitle.textContent = '팀원';
	        cardBody.appendChild(teamTitle);
	        cardBody.appendChild(teamContainer);
	
	        // 멤버 추가 (조장과 팀원 나누기)
	        team.teamMember.forEach((member) => {
	            const li = document.createElement('li');
	            li.classList.add('list-group-item');
	            li.textContent = `${member.studentVO.nm} (${member.stuId})`;
	            li.dataset.studentId = member.stuId;
	            li.setAttribute('draggable', 'true');
	            setupDragAndDrop(li);
	
	            if (member.leadYn === 'Y') {
	                leaderContainer.appendChild(li);
	            } else {
	                teamContainer.appendChild(li);
	            }
	        });
	
	        // 팀 카드 컨테이너에 추가
	        container.appendChild(teamCard);
			
			index++;
	    });
	}
	


    // 드래그 앤 드롭 설정
    function setupDragAndDrop(element) {
        element.addEventListener("dragstart", (e) => {
            e.dataTransfer.setData("text/plain", e.target.dataset.studentId);
			
			// 리더 여부 확인
	        const leaderArea = document.querySelector("#leaderArea");
	        const isLeader = leaderArea && leaderArea.contains(element);
	        e.dataTransfer.setData("isLeader", isLeader ? "true" : "false");
	
	        // 최초 요청 여부 설정
	        const attendeeList = document.querySelector("#attendeeList"); // 수강생 목록
	        const teamList = document.querySelector("#teamList"); // 팀 목록
	
	        let isInitial = false; // 기본값은 false
	
	        // 수강생 목록에 있으면 isInitial을 true로 설정
	        if (attendeeList && attendeeList.contains(element)) {
	            isInitial = true;
	        }
	
	        // isInitial 값을 dataTransfer에 저장
	        e.dataTransfer.setData("isInitial", isInitial ? "true" : "false");
			
			console.log("isInitial (after check):", isInitial);
			
        });
    }

	 // 드롭 이벤트 설정
    function setupDropListeners() {
        teamList.addEventListener("dragover", (e) => {
            e.preventDefault();
        });
		
		leaderArea.addEventListener("dragover", (e) => {
            e.preventDefault();
        });
		
		leaderArea.addEventListener("drop", async (e) => {
            e.preventDefault();
            const stuId = e.dataTransfer.getData("text/plain");
            const draggedItem = document.querySelector(`[data-student-id="${CSS.escape(stuId)}"]`);
            const currentLeader = leaderArea.querySelector('.list-group-item'); // 현재 조장 확인
			const isInitial = draggedItem.dataset.isInitial === "true"; // 최초 요청인지 확인
			
			console.log("leaderArea=========>",isInitial);
            if (currentLeader) {
                swal("알림", "조장은 1명만 추가 가능합니다.", "warning");
        		return;
            }

            if (draggedItem) {
	
                leaderArea.appendChild(draggedItem); // 조장 영역에 추가

                // 서버 요청: 조장 설정
                const teamCd = teamSelect.value;
                const taskNo = taskSelect.value;

                // 서버 요청
                try {
                    const apiEndpoint = isInitial
                        ? `${contextPath}/lecture/${lectNo}/projectMember/updateMember`
                        : `${contextPath}/lecture/${lectNo}/projectMember/justUpdate`;

                    const resp = await fetch(apiEndpoint, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ stuId, teamCd, taskNo, leadYn: 'Y' }),
                    });

                    if (!resp.ok) {
                        console.error("조장 등록 중 오류 발생");
                    } else {
                        draggedItem.dataset.isInitial = "false"; // 최초 요청 처리 후 상태 변경
                    }
                } catch (error) {
                    console.error("서버 요청 오류:", error);
                }
           }
        });
		
		
        teamList.addEventListener("drop", async (e) => {
            e.preventDefault();
            const stuId = e.dataTransfer.getData("text/plain");
            const draggedItem = document.querySelector(`[data-student-id="${CSS.escape(stuId)}"]`);

			const isLeader = draggedItem.dataset.isLeader
			const isInitial = draggedItem.dataset.isInitial === "true"; // 최초 요청인지 확인
			
			console.log("teamList=========>",isInitial);
			
            if (draggedItem) {
                teamList.appendChild(draggedItem);
				
                // 서버에 업데이트 요청
				const teamCd = teamSelect.value;
				const taskNo = taskSelect.value; // taskNo 가져오기
				
				if (!taskNo) {
	                swal("ERROR", "과제를 선택해주세요.", "error");
	                return;
	            }
				let apiEndpoint = isInitial
                        ? `${contextPath}/lecture/${lectNo}/projectMember/updateMember`
                        : `${contextPath}/lecture/${lectNo}/projectMember/justUpdate`
                
				//최초요청이어도 리더에서 팀으로 이동이면 업데이트쿼리        
				if(isLeader){
					apiEndpoint = `${contextPath}/lecture/${lectNo}/projectMember/justUpdate`;
				}	


				if(apiEndpoint){
                // 서버 요청
	                try {
						
		                    const resp = await fetch(apiEndpoint, {
		                        method: 'POST',
		                        headers: { 'Content-Type': 'application/json' },
		                        body: JSON.stringify({ stuId, teamCd, taskNo, leadYn: 'N' }),
		                    });
		
		                    if (!resp.ok) {
		                        console.error("팀원 등록 중 오류 발생");
		                    } else {
		                        draggedItem.dataset.isInitial = "false"; // 최초 요청 처리 후 상태 변경
		                    }
						
	                } catch (error) {
	                    console.error("서버 요청 오류:", error);
	                }
				}
				else{
					console.log("같은 팀 내 이동으로 서버 요청 없음");
				}
            }
        });

        attendeeList.addEventListener("dragover", (e) => {
            e.preventDefault();
        });

        attendeeList.addEventListener("drop", async (e) => {
            e.preventDefault();
            const stuId = e.dataTransfer.getData("text/plain");
            const draggedItem = document.querySelector(`[data-student-id="${CSS.escape(stuId)}"]`);

            if (draggedItem) {
                attendeeList.appendChild(draggedItem);
				const taskNo = taskSelect.value; // taskNo 가져오기
				
				if (!taskNo) {
	                swal("ERROR", "과제를 선택해주세요.", "error");
	                return;
	            }

                // 서버에 업데이트 요청 (팀에서 제거)
                try {
					
                    const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/removeMember`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ stuId, taskNo })
                    });

                    if (!resp.ok) {
                        console.error("팀원 제거 중 오류 발생");
                    }else {
		                draggedItem.dataset.isInitial = "true"; // 업데이트 완료 후 초기화 최초요청으로
		            }
                } catch (error) {
                    console.error("서버 요청 오류:", error);
                }
            }
        });
    }

    // 과제 선택 시 팀 목록 갱신
    taskSelect.addEventListener("change", async () => {
        const taskNo = taskSelect.value;
        if (!taskNo) {
            teamSelect.innerHTML = '<option value="" label="팀선택"></option>';
            return;
        }

        try {
            const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectMember/getTeams/${taskNo}`);
            if (resp.ok) {
                const teams = await resp.json();

                teamSelect.innerHTML = '<option value="" label="팀선택"></option>'; // 기본 옵션 추가
                teams.forEach(team => {
                    const option = document.createElement("option");
                    option.value = team.teamCd;
                    option.textContent = `${team.rnum}팀 (${team.teamPurpo})`;
					option.dataset.rnum = team.rnum; // 추가된 데이터 속성
                    teamSelect.appendChild(option);
                });
            } else {
                console.error("팀 데이터를 가져오는 데 실패했습니다.");
            }
        } catch (error) {
            console.error("오류 발생:", error);
        }
    });






});
