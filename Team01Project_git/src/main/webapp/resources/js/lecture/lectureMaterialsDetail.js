/**
 * 
 */
document.addEventListener("DOMContentLoaded", function(){
	const viewTime = document.getElementById('viewTime');
	axios.get(`${cp.value}/attendance/${lectNo.value}/${weekCd.value}/${lectOrder.value}/${id.value}`)
	.then(({data})=>{
		console.log(data);
		if(data.updatePossible){
			let totalSeconds = 0;
			if(data.atndIdnty){
				totalSeconds = data.atndIdnty * 60;
			}
			
			setInterval(() => {
				totalSeconds++;
				const min = Math.floor(totalSeconds / 60);
				const sec = totalSeconds % 60;
			    viewTime.textContent = `${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`;
			}, 100); // 1초마다 실행
			
			// 페이지 종료시 학습시간 업데이트 이벤트
			window.addEventListener('beforeunload', (e)=>{
				let atndIdnty = String(Math.floor(totalSeconds / 60));
				let atndCd = 'ATN3'; 
				if(Number(atndIdnty) >= Number(data.orderVO.sectIdnty)){
					atndCd = 'ATN1';	
				}
				const blob = new Blob([JSON.stringify(
						{
							lectNo: lectNo.value
							, weekCd: weekCd.value
							, lectOrder: lectOrder.value
							, stuId: id.value
							, atndIdnty: atndIdnty
							, atndCd: atndCd
						})], {type: 'application/json; charset=UTF-8'});
		    	navigator.sendBeacon(`${cp.value}/attendance/${lectNo.value}/${weekCd.value}/${lectOrder.value}/${id.value}`
		    		, blob);
		    });
		}else{
			viewTime.textContent = '학습기간이 아닙니다';
		}
	});
	
})