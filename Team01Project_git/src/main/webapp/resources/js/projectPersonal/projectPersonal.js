/**
 *  projectPerson.js 
	진척도 처리
 */

document.addEventListener("DOMContentLoaded",()=>{
	const mainArea = document.querySelector('#mainArea');
	const contextPath = mainArea.dataset.path;
	const lectNo = mainArea.dataset.lectno;
	const progressRange = document.getElementById('progressRange');
	const progressPercentage = document.getElementById('progressPercentage');
	const progressRangeValue = progressRange.value;
	
	const teamCd = mainArea.dataset.teamcd;
	const leadYn = mainArea.dataset.leadyn;
	
	const saveButton = document.getElementById("saveTeamNameBtn");
    const newTeamNameInput = document.getElementById("newTeamName");
	const charLimitWarning = document.getElementById("charLimitWarning");
	
	newTeamNameInput.addEventListener("input", function () {
	    const inputLength = newTeamNameInput.value.length;
	
	    // 10자를 초과한 경우 경고 메시지 표시 및 스타일 변경
	    if (inputLength > 10) {
	        charLimitWarning.classList.remove("d-none"); // 경고 메시지 표시
	        newTeamNameInput.classList.add("is-invalid"); // 입력 필드 스타일 변경
	    } else {
	        charLimitWarning.classList.add("d-none"); // 경고 메시지 숨김
	        newTeamNameInput.classList.remove("is-invalid"); // 입력 필드 스타일 복원
	    }
	});
	
	//진척도 변경 이벤트
	progressRange.addEventListener("change", async ()=>{
		if(leadYn != 'Y'){
			swal("권한없음!","진척도는 팀대표자만 수정가능합니다!","info");
			progressRange.value= progressRangeValue;
			return;
		}
		
		const teamProge = progressRange.value;
		progressPercentage.textContent = `${teamProge}%`;
		
		 try {
            const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectPersonal/updateProg`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
					teamCd: teamCd
                   , teamProge: teamProge
				   , lectNo: lectNo
                })
            });

            if (resp.ok) {
                const result = await resp.json();
				swal("변경완료!",`${result.message}`,"info");
				
            } else {
                swal("오류","진척도 업데이트 중 오류가 발생했습니다.","error");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            swal("오류","서버와 통신하는 중 오류가 발생했습니다.","error");
        }
		
	})
	
	
	 // 저장 버튼 클릭 이벤트
    saveButton.addEventListener("click",  async function () {
        const newTeamName = newTeamNameInput.value.trim();

        if (newTeamName === "") {
            swal("입력 필요", "새 팀명을 입력해주세요.", "warning");
            return;
        }
		
		 if (newTeamName.length > 10) {
	        swal("입력 제한", "팀명은 10자 이내로 입력해주세요.", "warning");
	        return;
	    }
        
         try {
	        const response = await fetch(`${contextPath}/lecture/${lectNo}/projectPersonal/updateTeamNm`, {
	            method: "POST",
	            headers: {
	                "Content-Type": "application/json",
	            },
	            body: JSON.stringify({
	                teamCd: teamCd,
	                teamNm: newTeamName,
	            }),
	        });
	
	        if (response.ok) {
	            const data = await response.json();
	            swal("수정 완료", data.message || "팀명이 성공적으로 수정되었습니다.", "success").then(()=>{
					location.reload();
				});
	            
	        } else {
				const errors = await response.json();
	            swal("수정 실패", errors.message || "팀명 수정에 실패했습니다. 다시 시도해주세요.", "error");
	        }
	    } catch (error) {
	        swal("오류 발생", "서버와 통신 중 오류가 발생했습니다.", "error");
	        console.error("Error:", error);
	    }
	});
	
	
	
	
	
});//DOM끝