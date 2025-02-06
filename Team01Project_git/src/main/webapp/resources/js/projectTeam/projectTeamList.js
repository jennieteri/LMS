/**
 * 
 */

import {
		ClassicEditor,
		SimpleUploadAdapter,
		Bold,
		Italic,
		Underline,
		BlockQuote,
		Essentials,
		Heading,
		Image,
		ImageUpload,
		Link,
		List,
		MediaEmbed,
		Table,
		TableToolbar,
		FontSize,
		FontFamily,
		FontColor,
		Undo
	
	} from '../../../resources/js/ckeditor5/ckeditor5.js';


document.addEventListener("DOMContentLoaded",()=>{
	const taskSelect= document.querySelector('#taskSelect');
	const contextPath = taskSelect.dataset.path;
	const lectNo = taskSelect.dataset.lectno;
	const teamArea = document.querySelector('#teamArea');
	const createBtn = document.querySelector('#createBtn');
	const createTeamArea = document.querySelector('#createTeamArea');
	const saveTeamBtn = document.getElementById('saveTeamBtn');
	
	saveTeamBtn.addEventListener("click",saveTeam);
	
	var editors = {};
	
	//팀 수정삭제
	teamArea.addEventListener("click",(e)=>{
		const action = e.target.dataset.action;
		const teamCd = e.target.dataset.teamcd;
		const taskNo = e.target.dataset.taskno;
		
		if(action == "edit"){
			openEditModal(teamCd);
		}
		else if(action == "delete"){
			deleteTeam(teamCd,taskNo);
		}
		
		
	});
	
	//과제선택변경시 해당과제 팀불러오는 이벤트
	taskSelect.addEventListener("change",async ()=>{
		const taskNo = taskSelect.value;
		if(taskNo==""){
			teamArea.innerHTML = "";
			return;
			
		}
		   // 초기화
        teamArea.innerHTML = "";

        try {
            const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/${taskNo}`);
            if (!resp.ok) {
                throw new Error("서버 통신 오류");
            }

            const data = await resp.json();
            // 리스트를 순회하며 team-box 요소 추가
            data.forEach(team => {
                const teamDiv = document.createElement("div");
                teamDiv.className = "team-box d-flex justify-content-between align-items-center";
                teamDiv.innerHTML = `<span>${team.rnum}팀 (${team.teamPurpo})</span>
									<div>
									<button class="btn btn-primary me-2" data-action="edit" data-taskno="${team.taskno}" data-teamcd="${team.teamCd}">수정</button>
									<button class="btn btn-danger" data-action="delete" data-taskno="${team.taskNo}" data-teamcd="${team.teamCd}">삭제</button>	
									</div>
										`; // 팀 이름 출력
                teamArea.appendChild(teamDiv);
            });
        } catch (error) {
            console.error(error);
            swal("오류 발생", "서버 통신 중 오류가 발생했습니다.", "error").then(() => {
                window.location.reload();
            });
        }
    });
	
	createBtn.addEventListener("click",async ()=>{
		//프로젝트과제목록, 총 수강생 가져오기
		const taskNo = document.getElementById("taskSelect").value;

	    if (!taskNo) {
	        swal("입력 오류", "먼저 과제를 선택하세요!", "error");
	        return;
	    }

		try{
			const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/getForm`);
			if (!resp.ok) {
                throw new Error("서버 통신 오류");
            }
            const data = await resp.json();

			const autoForm = `
				 <div class="auto-form">
	                <form id="creatTeamForm" method="POST">
	                    <table class="table table-primary align-middle table-bordered table-hover">
	                        <tr>
	                            <th>팀원 수</th>
	                            <td>
	                                <input type="number" id="memberCount" name="memberCount" min="1" max="${data.attendeeCount}" required>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th>팀 갯수</th>
	                            <td>
	                                <select id="teamCount" name="teamCount">
	                                    <option value="">팀 갯수를 선택하세요</option>
	                                </select>
	                            </td>
	                        </tr>
	                        <tr class="text-end">
	                            <td colspan="2">
	                                <button id="submitBtn" type="button" class="btn btn-primary" data-taskno="${taskNo}">저장</button>
									<a href="javascript:window.location.reload();" class="btn btn-danger">취소</a>
	                            </td>
	                        </tr>
	                    </table>
	                </form>
	            </div>
			
			`;
		createTeamArea.innerHTML = autoForm;

		 // 팀원 수 변경 시 팀 갯수 계산
        const memberCountInput = document.getElementById("memberCount");
        const teamCountSelect = document.getElementById("teamCount");

        memberCountInput.addEventListener("input", () => {
            const memberCount = parseInt(memberCountInput.value, 10);
            const attendeeCount = data.attendeeCount;

            // 팀 갯수 계산
            if (memberCount > 0 && memberCount <= attendeeCount) {
                const teamCount = Math.ceil(attendeeCount / memberCount);

                // 팀 갯수 셀렉트 박스 업데이트
                teamCountSelect.innerHTML = `<option value="">팀 갯수를 선택하세요</option>`;
                for (let i = 1; i <= teamCount; i++) {
                    const option = document.createElement("option");
                    option.value = i;
                    option.textContent = `${i}팀`;
                    teamCountSelect.appendChild(option);
                }
            } else {
                teamCountSelect.innerHTML = `<option value="">팀 갯수를 선택하세요</option>`;
            }
        });	

		 document.getElementById("submitBtn").addEventListener("click", async () => {
            const taskNo = document.getElementById("submitBtn").dataset.taskno;
            const memberCount = document.getElementById("memberCount").value;
            const teamCount = document.getElementById("teamCount").value;
	

            // 폼 유효성 검사
            if (!taskNo || !memberCount || !teamCount) {
                swal("입력오류","모든 필드를 올바르게 입력하세요.","error").then(()=>{
	
	                return;
				})
            }

            const formData = {
				lectNo: lectNo
                ,taskNo: taskNo
                ,memberCount: memberCount
                ,teamCount: teamCount
            };

            try {
                const response = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/createTeam`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(formData),
                });

                if (!response.ok) {
                    throw new Error("서버 응답 오류");
                }

                // 응답 데이터 처리
		        const result = await response.json();
		        swal("성공", result.message, "success").then(()=>{
			        // 필요 시 후속 작업 추가 (예: 새로고침 또는 페이지 이동)
			        taskSelect.value= taskNo;
					// change 이벤트 강제로 발생
				    const changeEvent = new Event("change");
				    taskSelect.dispatchEvent(changeEvent);
					createTeamArea.innerHTML="";

				});
		    } catch (error) {
		        console.error("저장 오류:", error);
		        swal("오류", result.message, "error");
		    }
        });

			
		}catch(error){
			console.error("데이터 가져오기 오류:", error);
            alert("데이터를 가져오는 중 문제가 발생했습니다.");
		}
		
	});

async function openEditModal(teamCd){
	
	const editTeamModal = document.querySelector('#editTeamModal');
	const editTeamModalBody = document.querySelector('.modal-body');
	
	try{
		const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/edit/${teamCd}`)
		if(resp.ok){
			let data = await resp.json();
			
			const projectTeam = data.projectTeam;
            const projectTaskList = data.projectTaskList;

            // 팀 수정 폼 동적으로 생성
            editTeamModalBody.innerHTML = `
                    <div class="mb-3">
                        <label for="taskSelect" class="form-label">과제 선택</label>
                        <select id="taskSelect" name="taskNo" class="form-select">
                            ${projectTaskList
                                .map(
                                    (task) =>
                                        `<option value="${task.taskNo}" ${
                                            task.taskNo === projectTeam.taskNo ? "selected" : ""
                                        }>${task.taskTitle}</option>`
                                )
                                .join("")}
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="teamPurpo" class="form-label">팀 주제</label>
                        <input type="text" id="teamPurpo" name="teamPurpo" class="form-control" value="${
                            projectTeam.teamPurpo
                        }" required>
                    </div>
                    <div class="mb-3">
                        <label for="teamNotes" class="form-label">팀 내용</label>
                        <textarea id="editor" name="teamNotes" class="form-control" required>${
                            projectTeam.teamNotes
                        }</textarea>
						<input type="hidden" name="teamCd" value="${projectTeam.teamCd}">
                    </div>
            `;
			
			//이미지 업로드 처리할때 보안때문에 html에서 제공하는 token 사용
				const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
				//만약 meta 설정이 없는 경우 null로 초기화 있다면, token값 가져옴
				const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
				
				//Editor의 종류중 사용할 Editor 생성 ClassicEditor권장 다른 종류 사용하고싶다면, 문서확인 후 사용
				ClassicEditor.create( document.querySelector('#editor'),{
					licenseKey: 'GPL' //5버전 기본 라이센스키 ; 필수값
					//사용할 플러그인 플러그인종류는 문서확인
					,  plugins: [
			            Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
			            List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar, 
			            FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
			        ],
					// 툴바 메뉴
			        toolbar: [
			            'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
			            'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
			            'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
			        ]
					,language: 'ko' // 한국어 설정
					//이미지 업로드 어댑터 config설정
					//이미지 url은 noticeboard폴더에 해당 처리 controller를 만들어두었음.
					//컨트롤러에서 반환값은 url임으로 반환값이 어떻게 오는지 알아야 데이터를 처리할 수 있음.
					, simpleUpload: { 
								uploadUrl: `${contextPath}/imageUpload`
								, headers:  csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
								
						}
				})
				//promise객체가 반환됨.
				 .then(editor => {
			        
			    })
			    .catch(error => {
			        console.error(error);
			
				})//create에디터 끝
			
			
			 // 모달창 열기
            const bootstrapModal = new bootstrap.Modal(editTeamModal);
            bootstrapModal.show();		
			
		}else{
			swal("서버오류","오류","error");
		}
		
	}catch(e){
		swal("오류","데이터가져오는중 문제발생","error");
	}
	
}//Open Modal 끝

async function deleteTeam(teamCd,taskNo){
	swal({
		title: "삭제하시겠습니까?"
		,text: "한번 삭제한 팀은 복구할 수 없습니다."
		,icon: "warning"
		,buttons:{
			cancel: "취소" // 취소 버튼
        ,confirm: {
            text: "확인" // 확인 버튼
            ,value: true
        	}
    	}
	}).then(async(willDelete)=>{
		if(willDelete){
			try{
				let resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/${teamCd}`,{
					method: 'DELETE'
					,headers: {
						'content-type':'application/json'
					}
				})
				if(resp.ok){
					const result = await resp.json();
					swal("성공", result.message || "팀이 성공적으로 삭제되었습니다.", "success").then(() => {
                        taskSelect.value= taskNo;
						// change 이벤트 강제로 발생
					    const changeEvent = new Event("change");
					    taskSelect.dispatchEvent(changeEvent);
                    });
				}else{
					const error = await resp.json();
					swal("실패", error.message || "작업 중 문제가 발생했습니다.", "error");
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

async function saveTeam() {
    const form = document.getElementById("editTeamForm");
    const formData = new FormData(form);
	const taskNo= formData.get("taskNo");
	const teamCd = formData.get("teamCd");
	
    // 요청에 필요한 데이터 추출
    const payload = {
		teamCd: teamCd
        ,taskNo: formData.get("taskNo")
        ,teamPurpo: formData.get("teamPurpo")
        ,teamNotes: formData.get("teamNotes")
    };

    try {
        const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTeam/${teamCd}`, {
            method: "PUT", // PUT 메서드 사용
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        });

        if (resp.ok) {
            const result = await resp.json();
            swal("성공", result.message, "success").then(() => {
             	taskSelect.value= taskNo;
				// change 이벤트 강제로 발생
			    const changeEvent = new Event("change");
			    taskSelect.dispatchEvent(changeEvent);

				const editTeamModal = bootstrap.Modal.getInstance(document.getElementById("editTeamModal"));
                editTeamModal.hide();
            });
        } else {
            throw new Error("서버 응답 오류");
        }
    } catch (error) {
        console.error("팀 정보 저장 오류:", error);
        swal("오류", "팀 정보를 저장하는 중 문제가 발생했습니다.", "error");
    }
}




	
});//DOM 끝


//function fnGetDetail(teamCd){
	
//}






