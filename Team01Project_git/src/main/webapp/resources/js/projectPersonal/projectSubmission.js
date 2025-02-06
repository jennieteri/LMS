/**
 * 
 */

document.addEventListener("DOMContentLoaded",()=>{
	const taskSubmitBtn= document.querySelector('#taskSubmitBtn');
	const mainDiv = document.querySelector('#mainDiv');
	const lectNo = mainDiv.dataset.lectNo;
	const teamCd = mainDiv.dataset.teamCd;
	const taskNo = mainDiv.dataset.taskNo;
	const contextPath = mainDiv.dataset.path;
	const baseUrl = `${contextPath}/lecture/${lectNo}/projectTaskSubmission`;
	const peerForm = document.querySelector('#peerForm');
	const taskForm = document.querySelector('#taskForm');
	const peerSubmitBtn = document.querySelector('#peerSubmitBtn');
	const peerBtn = document.querySelector('#peerBtn');
	
	//제출요청 url => baseUrl + form.action
	//필요데이터 
	
	taskForm?.addEventListener("submit", async (e)=>{
		e.preventDefault();
		
		const stuId = taskSubmitBtn.dataset.id;
		const currentUser = taskSubmitBtn.dataset.user;
		const leadYn = taskSubmitBtn.dataset.leadYn;
		
		if(stuId != currentUser){
			swal("요청 불가!","비정상접근:로그인사용자와 제출자가 다릅니다.","error");
			return;
		}
		
		if(leadYn == 'N'){
			swal("접근제한!","프로젝트 대표자만 제출가능합니다.","error");
			return;
		}
		
		const formData = new FormData(taskForm);
		formData.append("teamCd",teamCd);
		formData.append("taskNo",taskNo);
		try{
			const resp = await fetch(`${baseUrl}/task`,{
				method: 'POST'
				,body: formData
			});
			
			if(!resp.ok){
				swal("제출 실패: 서버응답 오류");
			}
			
			const result = await resp.json();
			swal("성공!", result.message ||"제출완료!","success" ).then(()=>{
				location.reload();
			});
			
		}catch(e){
			swal("오류 발생!","오류 발생!", "error" );
		}
		
	});
	
	peerForm?.addEventListener("submit", async (e)=>{
		e.preventDefault();
		
		const tasksubNo = peerBtn.dataset.tasksubNo;
		
		const stuId = taskSubmitBtn.dataset.id;
		const currentUser = taskSubmitBtn.dataset.user;
		
		if(stuId != currentUser){
			swal("요청 불가!","비정상접근:로그인사용자와 제출자가 다릅니다.","error");
			return;
		}
		
		const formData = new FormData(peerForm);
		
		formData.append("tasksubNo",tasksubNo);
		
		try{
			const resp = await fetch(`${baseUrl}/peer`,{
				method: 'POST'
				,body: formData
			});
			
			if(!resp.ok){
				swal("제출 실패: 서버응답 오류");
			}
			
			const result = await resp.json();
			swal("성공!", result.message ||"제출완료!","success" ).then(()=>{
				location.reload();
			});
			
		}catch(e){
			swal("오류 발생!","오류 발생!", "error" );
		}
		
		
		
	});
	
	
	
	
});// DOM 끝