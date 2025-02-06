/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
	
	  const cp = document.getElementById('cp').value; 
	  const principal = document.getElementById('principal').value; 
	    console.log("cp값 : " + cp);
	    console.log("principal값 : " + principal);

	    const checkBtn = document.getElementById('check');
	    
	    // 버튼 클릭 이벤트
	    checkBtn.addEventListener("click", () => {
	        console.log("cp값 : " + cp);

	        const targetUrl = `${cp}/graduation/list/${principal}`; // 동적 URL 생성

	        console.log(`생성된 URL: ${targetUrl}`); // URL 확인

	        // URL로 이동
	       window.location.href = targetUrl;
	    });
});