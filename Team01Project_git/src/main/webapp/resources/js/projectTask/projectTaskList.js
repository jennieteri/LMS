/**
 *  체크박스 선택 후 수정버튼 누르면 해당 과제 수정형식으로 변경
 *	저장버튼 클릭시 해당 과제 수정처리
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

document.addEventListener("DOMContentLoaded", () => {
	const contextPath = document.getElementById("projectControl").dataset.path;
	const lectNo = document.getElementById("projectControl").dataset.lectno;
    const editTaskBtn = document.getElementById("editTaskBtn");
	const selectAllCheckbox = document.getElementById("selectAll");
    const taskCheckboxes = document.querySelectorAll(".task-select");
	const deleteTaskBtn = document.getElementById("deleteTaskBtn");
	const delTaskBtn = document.getElementById("delTaskBtn");

	var editors = {};
	
	//다중삭제
	deleteTaskBtn?.addEventListener("click",()=>{
		const selectedTasks = document.querySelectorAll(".task-select:checked");

	    if (selectedTasks.length == 0) {
	        swal("알림", "삭제할 프로젝트를 선택하세요.", "warning");
	        return;
	    }
		
		swal({
	        title: "삭제하시겠습니까?",
	        text: "한번 삭제한 프로젝트는 복구가 불가능할 수 있습니다.",
	        icon: "warning",
	        buttons: ["취소", "삭제"],
	        dangerMode: true,
	    }).then((willCancel) => {
	        if (willCancel) {
	           // 삭제 처리
            const deletePromises = Array.from(selectedTasks).map((checkbox) => {
                const taskNo = checkbox.value;
                return fetch(`${contextPath}/lecture/${lectNo}/projectTask/${taskNo}`, {
                    method: "DELETE",
                    headers: {
                        "Accept": "application/json",
                    },
                });
            });

            Promise.all(deletePromises)
                .then((responses) => {
                    const allSuccessful = responses.every((res) => res.ok);

                    if (allSuccessful) {
                        swal("삭제되었습니다!", {
                            icon: "success",
                        }).then(() => {
                            window.location.reload(); // 페이지 새로고침
                        });
                    } else {
                        swal("실패", "일부 프로젝트 삭제 중 문제가 발생했습니다.", "error");
                    }
                })
                .catch((err) => {
                    console.error("통신 오류:", err);
                    swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
                });
	        }
	    });
	});//삭제버튼 눌렀을 때 이벤트 끝
	
	//단건삭제
	delTaskBtn?.addEventListener("click",()=>{
		
		swal({
	        title: "삭제하시겠습니까?",
	        text: "한번 삭제한 프로젝트는 복구가 불가능할 수 있습니다.",
	        icon: "warning",
	        buttons: ["취소", "삭제"],
	        dangerMode: true,
	    }).then((willCancel) => {
		        if (willCancel) {
		          // 삭제 처리
	            fetch(`${contextPath}/lecture/${lectNo}/projectTask/${taskNo}`, {
	                method: "DELETE",
	                headers: {
	                    "Accept": "application/json",
	                },
	            })
	                .then((response) => {
	                    if (response.ok) {
	                        swal("삭제되었습니다!", {
	                            icon: "success",
	                        }).then(() => {
	                            window.location.reload(); // 페이지 새로고침
	                        });
	                    } else {
	                        swal("실패", "프로젝트 삭제 중 문제가 발생했습니다.", "error");
	                    }
	                })
	                .catch((err) => {
	                    console.error("통신 오류:", err);
	                    swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
	                });
	        }
	    });
	});
	
	
	
	// 기존파일 삭제처리
	document.querySelectorAll("[data-atch-file-id][data-file-sn]").forEach(el=>{
    	el.addEventListener("click", async (e)=>{
    		e.preventDefault();
    		let atchFileId = el.dataset.atchFileId;
    		let fileSn = el.dataset.fileSn;
			let resp = await fetch(`${contextPath}/atch/${atchFileId}/${fileSn}`, {
    			method:"delete"
    			, headers:{
    				"accept":"application/json"
    			}
    		});
    		if(resp.ok){
    			let obj = await resp.json();
    			if(obj.success){
					el.parentElement.remove();    				
    			}
    		}
    	});
    });
	//모달창 상세보기 이벤트
	 document.body.addEventListener("click", async (e) => {
        if (e.target && e.target.classList.contains("view-task-details")) {
            const taskNo = e.target.dataset.taskId;
            console.log(taskNo)
            try {
                const response = await fetch(`${contextPath}/lecture/${lectNo}/projectTask/${taskNo}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json",
                    },
                });

                if (!response.ok) {
                    swal("실패", "과제 정보를 가져오는 데 실패했습니다.", "error");
                    return;
                }

                const taskData = await response.json();
                
                // 모달 내용 업데이트
                const taskDetailsContent = document.getElementById("taskDetailsContent");
                taskDetailsContent.innerHTML = `
	                     <tr>
	                    <th>주제</th>
	                    <td>${taskData.taskTitle}</td>
	                </tr>
	                <tr>
	                    <th>제출 마감일</th>
	                    <td>${taskData.taskEt.slice(0, 4)}-${taskData.taskEt.slice(4, 6)}-${taskData.taskEt.slice(6, 8)}</td>
	                </tr>
	                <tr>
	                    <th>배점</th>
	                    <td>${taskData.taskScore}점</td>
	                </tr>
	                <tr>
	                    <th>첨부 파일</th>
	                    <td>
	                        ${
	                            taskData.atchFile && taskData.atchFile.fileDetails.length
	                                ? taskData.atchFile.fileDetails.map(
	                                      (fd) => `
	                                <a href="${contextPath}/atch/${fd.atchFileId}/${fd.fileSn}" target="_blank">
	                                    ${fd.orignlFileNm} (${fd.fileFancysize})
	                                </a>
	                            `
	                                  ).join(", ")
	                                : "없음"
	                        }
	                    </td>
	                </tr>
	                <tr>
	                    <th>내용</th>
	                    <td>${taskData.taskNotes}</td>
	                </tr>
	            `;
				if(editTaskBtn){
					document.getElementById("editTaskBtn").dataset.taskId = taskData.taskNo;
				}
				if(delTaskBtn){
					document.getElementById("delTaskBtn").dataset.taskId = taskData.taskNo;
				}
				
                // 모달 표시
                const modal = new bootstrap.Modal(document.getElementById("taskDetailsModal"));
                modal.show();
            } catch (err) {
                console.error(err);
                swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
            }
        }
    });
	
	
	
	
	// 전체 선택 / 해제 이벤트
    selectAllCheckbox?.addEventListener("change", () => {
        const isChecked = selectAllCheckbox.checked;
        taskCheckboxes.forEach((checkbox) => {
            checkbox.checked = isChecked;
        });
    });

    // 개별 체크박스 상태 변경 시 전체 선택 체크박스 상태 갱신
    taskCheckboxes.forEach((checkbox) => {
        checkbox.addEventListener("change", () => {
            const allChecked = Array.from(taskCheckboxes).every((box) => box.checked);
            const noneChecked = Array.from(taskCheckboxes).every((box) => !box.checked);

            if (allChecked) {
                selectAllCheckbox.checked = true;
                selectAllCheckbox.indeterminate = false; // 중간 상태 해제
            } else if (noneChecked) {
                selectAllCheckbox.checked = false;
                selectAllCheckbox.indeterminate = false; // 중간 상태 해제
            } else {
                selectAllCheckbox.indeterminate = true; // 중간 상태
            }
        });
    });
	
    // 상세 모달의 수정 버튼 클릭 이벤트
    document.body.addEventListener("click", async (e) => {
        if (e.target && e.target.id === "editTaskBtn") {
            const taskNo = e.target.dataset.taskId;

            try {
                // 서버에서 과제 데이터 가져오기
                const response = await fetch(`${contextPath}/lecture/${lectNo}/projectTask/${taskNo}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!response.ok) {
                    swal("실패", "과제 정보를 가져오는 데 실패했습니다.", "error");
                    return;
                }

                const taskData = await response.json();
                const taskEtFormatted = `${taskData.taskEt.slice(0, 4)}-${taskData.taskEt.slice(4, 6)}-${taskData.taskEt.slice(6, 8)}`;

                // 수정 모달에 데이터 채우기
				document.getElementById("editTaskNo").value = taskNo;
                document.getElementById("editTaskTitle").value = taskData.taskTitle;
                document.getElementById("editTaskEt").value = taskEtFormatted;
                document.getElementById("editTaskScore").value = taskData.taskScore;
                document.getElementById("editTaskNotes").value = taskData.taskNotes;

				const existingFiles = document.getElementById("existingFiles");
                existingFiles.innerHTML = taskData.atchFile?.fileDetails?.length
                    ? taskData.atchFile.fileDetails.map(fd => `
                        <span>
                            ${fd.orignlFileNm} (${fd.fileFancysize})
                            <a href="javascript:;" data-atch-file-id="${fd.atchFileId}" data-file-sn="${fd.fileSn}" class="btn btn-danger btn-sm file-delete-btn">삭제</a>
                        </span>
                    `).join("<br>")
                    : "없음";
				
				//이미지 업로드 처리할때 보안때문에 html에서 제공하는 token 사용
				const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
				//만약 meta 설정이 없는 경우 null로 초기화 있다면, token값 가져옴
				const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
				
				//Editor의 종류중 사용할 Editor 생성 ClassicEditor권장 다른 종류 사용하고싶다면, 문서확인 후 사용
				ClassicEditor.create( document.querySelector(`#editTaskNotes`),{
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
			        editors = editor;
			    })
			    .catch(error => {
			        console.error(error);
			
				})
				
				
                // 상세 모달 닫기
                const detailsModal = bootstrap.Modal.getInstance(document.getElementById("taskDetailsModal"));
                detailsModal.hide();

                // 수정 모달 표시
                const editModal = new bootstrap.Modal(document.getElementById("editTaskModal"));
                editModal.show();

                // 저장 버튼 이벤트 처리
                document.getElementById("saveEditTaskBtn").addEventListener("click", async () => {
                    const formData = new FormData(document.getElementById("editTaskForm"));
                    formData.append("taskNo", taskNo);

                    try {
                        const saveResponse = await fetch(`${contextPath}/lecture/${lectNo}/projectTask/edit/${taskNo}`, {
                            method: "POST",
                            body: formData,
                        });

                        if (saveResponse.ok) {
                            swal("성공", "과제가 성공적으로 수정되었습니다!", "success").then(() => {
                                window.location.reload();
                            });
                        } else {
                            const error = await saveResponse.json();
                            swal("실패", error.message || "과제 수정 중 문제가 발생했습니다.", "error");
                        }
                    } catch (error) {
                        console.error(error);
                        swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
                    }
                });
            } catch (error) {
                console.error(error);
                swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
            }
        }
    });









});