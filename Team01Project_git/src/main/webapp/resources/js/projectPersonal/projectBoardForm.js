/**
 * 생성 / 수정 비동기처리 후 swal창
 */


document.addEventListener("DOMContentLoaded",()=>{
	const contextPath = document.querySelector('#form-table').dataset.path;
	const lectNo = document.querySelector('#form-table').dataset.lectno;
	const teamCd = document.querySelector('#form-table').dataset.teamcd;
	
	const createForm = document.querySelector('#createForm');
	const editForm = document.querySelector('#editForm');
	const deleteForm = document.getElementById('deleteForm');
	
	deleteForm?.addEventListener("submit",async(e)=>{
		e.preventDefault();
		swal({
	            title: "삭제하시겠습니까?"
	            ,text: "한번 삭제한 데이터는 복구가 어렵습니다."
	            ,icon: "warning"
	            ,buttons: ["취소","확인"]
				,dangerMode: true, // 경고 스타일 활성화
			}).then(async (willDelete)=>{
				if(willDelete){
					try{
						const formData = new FormData(deleteForm); 
						const resp = await fetch(deleteForm.action, {
			                method: "POST",
			                body: formData,
			            });
						 if (resp.ok) {
			                const data = await resp.json();
			                if (data.success) {
			                    swal({
			                        title: "성공",
			                        text: data.message || successMessage,
			                        type: "success",
			                    }).then(() => {
			                        window.location.href = `${contextPath}/lecture/${lectNo}/projectBoard/${data.targetTeamCd}`;
			                    });
			                } else {
			                    swal({
			                        title: "오류",
			                        text: data.message || "저장 중 오류가 발생했습니다.",
			                        type: "error",
			                    });
			                }
			            } else {
			                throw new Error("응답이 실패했습니다.");
			            }
					}catch(e){
						console.log("에러:",e);
						swal("서버 오류", "게시글 삭제 중 오류가 발생했습니다.", "error");
					}
				}else{
					swal("취소","취소되었습니다!","success")
						.then(()=>{
							location.reload();
						})
				}
				
			})
	});//삭제

	
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
	
	// 공통 fetch 요청 함수
    const handleSubmit = async (form, successMessage) => {
        try {
            const formData = new FormData(form); // FormData 객체 생성
            const response = await fetch(form.action, {
                method: "POST",
                body: formData,
            });

            if (response.ok) {
                const data = await response.json();
                if (data.success) {
                    swal({
                        title: "성공",
                        text: data.message || successMessage,
                        type: "success",
                    }).then(() => {
                        window.location.href = `${contextPath}/lecture/${lectNo}/projectBoard/${teamCd}/${data.targetPbNo}`;
                    });
                } else {
                    swal({
                        title: "오류",
                        text: data.message || "저장 중 오류가 발생했습니다.",
                        type: "error",
                    });
                }
            } else {
                throw new Error("응답이 실패했습니다.");
            }
        } catch (err) {
            console.error("Error:", err);
            swal({
                title: "서버 오류",
                text: "서버와 통신 중 오류가 발생했습니다. 잠시 후 다시 시도하세요.",
                type: "error",
            });
        }
    };
	
	 // 생성 및 수정 폼 제출 처리
        createForm?.addEventListener("submit", (e) => {
            e.preventDefault();
            handleSubmit(createForm, "게시글이 성공적으로 저장되었습니다!");
        });

        editForm?.addEventListener("submit", (e) => {
            e.preventDefault();
            handleSubmit(editForm, "게시글이 성공적으로 수정되었습니다!");
        });
	


});//DOM끝