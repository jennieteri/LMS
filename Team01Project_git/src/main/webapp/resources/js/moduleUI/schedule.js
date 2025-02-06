document.addEventListener("DOMContentLoaded", async function(){
	let moduleData = await axios.get(`${postScriptCP.value}/module/schedule`)
								.then(({data})=>data);
	let dotwList = moduleData.dotwList;
	let etimeList = moduleData.etimeList;
	let table = `
		<table class="table table-bordered table-primary text-center align-middle mb-0">
			<thead>
				<tr>
					<th style="width: 50px;">교시</th>
	`;
	for(let dotw of dotwList){
		if(dotw.cocoCd != 'SUN'){
			table += `<th>${dotw.cocoStts}</th>`
		}
	}
	table += `
				</tr>
			</thead>
			<tbody>
	`;
	for(let etime of etimeList){
		table += `
			<tr class="table-light">
				<th style="font-size: 0.8rem;" class="${etime.edcTimeCd}"
					data-bs-toggle="tooltip" data-bs-placement="left" title="${etime.beginTime.substring(0,2)} ~ ${etime.endTime.substring(0,2)}">
					${etime.commonCodeVO.cocoStts}<br/>
				</th>
		`;
		for(let dotw of dotwList){
			if(dotw.cocoCd != 'SUN'){
				table += `
					<td data-etime="${etime.edcTimeCd }" data-dotw="${dotw.cocoCd }"
						data-schedule="${dotw.cocoCd }-${etime.edcTimeCd }"/>
				`;
			}
		}
		table += `</tr>`
	}
	table += `
			</tbody>
		</table>
	`;
	
	const fnBgColor = function(){
		const r = Math.floor(Math.random() * 56) + 180;
	    const g = Math.floor(Math.random() * 56) + 180;
	    const b = Math.floor(Math.random() * 56) + 180;
		return `rgb(${r}, ${g}, ${b})`;
	}
	
	$('.cj-schedule').each((i,v)=>{
		let $v = $(v);
		$v.html(table);
		let tooltipList = $v.find('[data-bs-toggle="tooltip"]');
    	[...tooltipList].map(el => new bootstrap.Tooltip(el));
		// 높이 조절
		if($v.attr('data-height')){
			$v.find('.table').css('height', v.dataset.height)
		}
		// 강의 시간표 수집
		if($v.attr('data-lect')){
			axios.get(`${postScriptCP.value}/mainui/schedule`)
			.then(({data})=>{
				let cnt = 0;
				let totalScore = 0;
				for(let lect of data){
					totalScore += Number(lect.lectScore);
					let bgColor = fnBgColor();
					if(lect.lectOnlineYn == 'N'){
						for(let schedule of lect.scheduleVO){
							let $td = $v.find(`td[data-schedule='${schedule.dateCd}-${schedule.edcTimeCd}']`);
							$td.css('background', bgColor);
						}
					}
					let carouselItem = `
						<div class="carousel-item ${cnt++ == 0 ? 'active' : ''}">
							<div class="card mb-0 card-${lect.lectNo} lect-cart" data-lect-no="${lect.lectNo}">
							  <div class="card-header py-3" style="background: ${bgColor};">
					`;
					if(lect.lectStatusCd == "L03"){
						carouselItem += `<a href="${postScriptCP.value}/lecture/${lect.lectNo}/materials">`;
					}
					carouselItem += `<h5 class="card-title fw-bold p-0 m-0" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">[${lect.subjectVO.subFicdCdCommonCodeVO.cocoStts}] ${lect.lectNm}</h5>`;
					if(lect.lectStatusCd == "L03"){
						carouselItem += `</a>`;
					}
					carouselItem += `
							  </div>
							  <div class="card-body pb-2 px-4">
							    <h6 class="card-title p-0 mt-3">${lect.professorVO.nm} 교수</h6>
								<span class="card-text p-0">${lect.lectScore}학점</span>
								<p class="card-text p-0 float-end">${lect.scheduleVO[0] ? lect.scheduleVO[0].classRoomVO.croomPstn : "온라인"}</p>
							  </div>
							</div>
			    		</div>
					`;
					$v.parent().find('.carousel-inner').append(carouselItem);
				}
				$v.parent().find('#total-score').text(totalScore);
			})
		}
	});
})