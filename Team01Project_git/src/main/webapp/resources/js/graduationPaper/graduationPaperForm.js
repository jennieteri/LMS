/**
 * 
 */
document.addEventListener("DOMContentLoaded",()=>{
	const nextBtn = document.querySelector('#next');
	const cp = document.getElementById('cp').value;
	nextBtn.addEventListener("click",()=>{
		console.log("다음");
		window.location.href = cp+`/graduationpaper/new1`;
	})
})