/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
	
	  const cp = document.getElementById('cp').value; 
	  const principal = document.getElementById('principal').value; 
	    console.log("cp값 : " + cp);
	    console.log("principal값 : " + principal);

	    const checkBtn = document.getElementById('check');
		const insertBtn = document.getElementById('insert');
	    
	    checkBtn.addEventListener("click", () => {
			console.log(principal);
	        console.log("cp값 : " + cp);

	        const targetUrl = `${cp}/graduationpaper/list/${principal}`; 

	        console.log(`생성된 URL: ${targetUrl}`); 
	        // URL로 이동
	       window.location.href = targetUrl;
	    });

	    insertBtn.addEventListener("click", () => {
	        const targetUrl = `${cp}/graduationpaper/new`; 
	        console.log(`생성된 URL: ${targetUrl}`); 

	       window.location.href = targetUrl;
	    });


});