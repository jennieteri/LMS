/**
 * 
 */

document.addEventListener("DOMContentLoaded",()=>{
	const mainDiv = document.querySelector('#mainDiv');
	const contextPath = mainDiv.dataset.path;
	const lectNo = mainDiv.dataset.lectNo;
	const gradeBtn = document.querySelector('#gradeBtn');
	
	gradeBtn?.addEventListener("click", async ()=>{
		const maxScore = gradeBtn.dataset.maxScore;
		const tasksubNo = gradeBtn.dataset.tasksubNo;
		
		const scoreInput = document.querySelector('#score'+tasksubNo);
		const score = scoreInput.value
		
		if(score>maxScore){
			swal("잘못된 점수!","배점보다 높은 점수를 입력할 수 없습니다!","error");
			return;
		}
		swal({
		    title: "CHECK!",
		    text: "한번 입력한 점수는 수정할 수 없습니다!",
		    icon: "info",
		    buttons: ["취소", "확인"]
		}).then(async (willProceed) => {
		    if (!willProceed) return;
		
			const requestData = {
			    tasksubNo: tasksubNo,
			    tasksubScore: score
			};

			try{
				const resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTaskSubmission/grade`,{
					method: 'POST'
					,headers: {
			            'Content-Type': 'application/json' // JSON 데이터 전송
			        }
					,body: JSON.stringify(requestData)
				})
				// 응답 처리
			    if (resp.ok) {
			        const result = await resp.json();
			        swal("성공!", result.message ||"점수가 성공적으로 저장되었습니다!", "success").then(()=>{
						window.location.reload();			
					})
			    } else {
			        const error = await resp.json();
			        swal("오류!", error.message ||'서버에서 오류가 발생했습니다', "error");
			    }
				
			}catch(e){
				console.error(e);
	   			swal("오류!", "네트워크 오류가 발생했습니다. 다시 시도해주세요.", "error");
			}		    

		});
		
		
	});
	
	
	
});//DOM 끝
