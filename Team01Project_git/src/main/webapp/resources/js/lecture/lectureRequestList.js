/**
 * 
 */
document.addEventListener('DOMContentLoaded', function(){
	let semester = document.querySelector('#semester');
	let lectTbody = document.querySelector('#lectTbody');
	
	const fnLectureList = function(semstrNo){
		axios.get(`${cp.value}/lecture/${semstrNo}`)
		.then(({data})=>{
			lectTbody.innerHTML = '';
			if(data.length == 0){
				lectTbody.insertAdjacentHTML('beforeend',
				`<tr class="table-light align-middle">
					<td colspan="8" class="text-center">등록된 강의가 없습니다</td>
				</tr>`);
			}
			data.forEach(lect=>{
				let textColor = '';
				if(lect.lectStatusCd == 'L01'){
					textColor = 'text-primary';
				}
				if(lect.lectStatusCd == 'L04' || lect.lectStatusCd == 'L07'){
					textColor = 'text-danger';
				}
				let returnText = '';
				if(lect.lectReturn){
					returnText = `data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="반려사유 : ${lect.lectReturn}"`;
				}
				if(lect.lectAbl){
					returnText = `data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="폐강사유 : ${lect.lectAbl}"`;
				}
				
				lectTbody.insertAdjacentHTML('beforeend',
				`<tr class="table-light align-middle" style="cursor: pointer;" onclick="location.href='${cp.value}/lecture/request/${lect.lectNo }'">
					<td class="text-center">${lect.subjectVO.subFicdCdCommonCodeVO.cocoStts}</td>
					<td>[${lect.subjectVO.subNm}] ${lect.lectNm}</td>
					<td class="text-center">${lect.professorVO.nm }</td>
					<td class="text-center">${lect.lectScore }</td>
					<td class="text-center">${lect.lectEnNope }</td>
					<td class="text-center">${lect.lectOnlineYn == 'Y' ? '온라인' : '오프라인' }</td>
					<td class="text-center ${textColor}" ${returnText}>
						${lect.commonCodeVO.cocoStts }
					</td>
				</tr>`)
			});
			const tooltips = document.querySelectorAll('[data-bs-toggle="tooltip"]');
			tooltips.forEach(tooltip => {
			    new bootstrap.Tooltip(tooltip);
			});
		});
	};
	
	semester.addEventListener('change', function(){
		fnLectureList(this.value);
	});
	
	axios.get(`${cp.value}/commoncode/semester`)
	.then(({data})=>{
		data.forEach(({semstrNo})=>{
			semester.insertAdjacentHTML('beforeend', `<option value="${semstrNo}" label="${semstrNo.substr(0,4)}-${semstrNo.substr(4)}"/>`);
		});
		if(semstrNo.value){
			semester.value = semstrNo.value;
		}
		semester.dispatchEvent(new Event('change'));
	});
})