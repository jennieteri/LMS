/**
 * 
 */

async function dateChecker(cocoCd) {
var sideCp = document.querySelector("#sideCp").value;
	console.log(sideCp)
    var url = '';
    var str = '';
    
    switch (cocoCd) {
        case 'NB01':
            url = ''; str = '';
            break;
        case 'NB02':
            url = ''; str = '';
            break;
        case 'NB03':
            url = ''; str = '';
            break;
        case 'NB04':
            url = 'lectureCart/pre'; str = '예비수강신청';
            break;
        case 'NB05':
            url = 'lectureCart/final'; str = '수강신청';
            break;
        case 'NB08':
            url = 'attendCoeva'; str = '강의 평가 및 성적 조회';
            break;
        default:
            url = ''; str = '';
            break;
    }

    try {
        // fetch 요청 보내기
        const response = await fetch(`${sideCp}/dateCheck/${cocoCd}`);
        
        // 성공적으로 처리된 경우
        if (response.ok) {
            //alert("날짜 범위 확인 성공!");
            location.href = `${sideCp}/${url}`;
        } else {
	         swal({
				title: `${str}`,
				text: `현재 ${str} 기간이 아닙니다.`,
				icon: "error",
				button: "확인",
			});
        }
    } catch (error) {
        console.error("오류 발생:", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
    }
}











