/**
 * 
 */
document.addEventListener("DOMContentLoaded",()=>{
	
   
   document.getElementById("bulkUploadForm").onsubmit = async function (event) {
	    event.preventDefault();
	    const cp = document.querySelector('#cp').value;

	    try {
	        const formData = new FormData(this);

	        const response = await fetch(this.action, {
	            method: "POST",
	            body: formData,
	        });

	        const data = await response.json();

	        if (data.success) {
	            swal({
	                title: "등록 전 사용자 정보를 검토해주세요",
	                text: data.message,
	                icon: "info",
	                buttons: ["취소", "저장"],
	            }).then(async (userConfirmed) => {
	                if (userConfirmed) {
	                    const confirmFormData = new FormData();
	                    confirmFormData.append("file", formData.get("file"));

	                    const confirmResponse = await fetch(cp + '/person/confirmUpload', {
	                        method: 'POST',
	                        body: confirmFormData,
	                    });

	                    const confirmData = await confirmResponse.json();
	                    if (confirmData.success) {
	                        swal({
	                            title: "저장 완료!",
	                            text: confirmData.message,
	                            icon: "success",
	                            buttons:  "확인",
	                        }).then(()=>{
		                        location.href=cp+'/person/list';
	                        });
	                    } else {
	                        swal({
	                            title: "저장 실패",
	                            text: confirmData.message,
	                            icon: "error",
	                        });
	                    }
	                }
	            });
	        } else {
	            swal({
	                title: "오류 발생",
	                text: data.message,
	                icon: "error",
	            });
	        }
	    } catch (error) {
	        console.error('요청 처리 중 오류:', error);
	        swal({
	            title: "오류 발생",
	            text: "요청 처리 중 문제가 발생했습니다. 관리자에게 문의하세요.",
	            icon: "error",
	        });
	    }
	};

});