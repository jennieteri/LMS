document.addEventListener("DOMContentLoaded", function () {
	    
	    const contextPath = document.getElementById("contextPath").value;


		
	    console.log("contextPath:", contextPath);
	    console.log("Final Redirect URL:", contextPath + "/mypage/" + id + "/UpdateMyPage");

	    if (isFreshman) {
	        swal({
	            title: "신입생 확인",
	            text: "신입생입니다. 정보 수정 페이지로 이동합니다.",
	            icon: "info",
	            button: "확인"
	        }).then(() => {
		console.log("리다이렉션 실행");
	            const redirectUrl = contextPath + "/mypage/" + id + "/UpdateMyPage";
	            window.location.href = redirectUrl;
	        });
	    }
	});

