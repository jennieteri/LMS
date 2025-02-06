/**
 * 
 */
document.addEventListener("DOMContentLoaded",()=>{
	const previousBtn = document.querySelector('#previousBtn');
	const submitBtn = document.querySelector('#submitBtn');
	const cp = document.querySelector('#cp').value;
	const form = document.querySelector('#graduationPaperForm');
	
	previousBtn.addEventListener("click", ()=>{
	    history.back();
	});

/*	
submitBtn.addEventListener("click", (event) => {
    event.preventDefault(); 

    const formData = new FormData(form);

    axios.post(`${cp}/graduationpaper/submit`, formData)
        .then(response => {
            if (response.data.success) { 
                window.location.href = `${cp}/graduationpaper/complete`;
            } else {
                alert("논문 제출 실패: " + response.data.message);
            }
        })
        .catch(error => {
            console.error("논문 제출 실패:", error);
            alert("논문 제출 중 문제가 발생했습니다. 다시 시도해 주세요.");
        });
  */
});

	
	


