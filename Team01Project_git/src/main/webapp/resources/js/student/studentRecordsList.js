/**
 * 
 */
let fnPaging;
let fnDetail;
let fnInsertModalOpen;
let fnStreCateChange;
let fnStreResSelChange;
let fnInsertFormSubmit;
let fnCancelStuRec;
let fnConsentStuRec;
let fnReturnStuRec;

document.addEventListener('DOMContentLoaded', function(){
	let search = document.getElementById('search');
	let searchDiv = document.getElementById('searchDiv');
	let searchBtn = document.getElementById('searchBtn');
	let resetBtn = document.getElementById('resetBtn');
	let show = document.getElementById('show');
	let searchForm = document.forms['searchForm'];
	let insertForm = document.forms['insertForm'];
	
	// 검색 컬랩스 이벤트에 따른 전송 버튼 가시성 수정
	const toggleBtn = function(){
		if (searchDiv.classList.contains('hidden')) {
	        searchDiv.classList.remove('hidden');
	        searchDiv.classList.add('visible');
	    } else {
	        searchDiv.classList.remove('visible');
	        searchDiv.classList.add('hidden');
	    }
	}
	search.addEventListener('hide.bs.collapse', function(){
		toggleBtn();
		show.value = "N";
	});
	search.addEventListener('show.bs.collapse', function(){
		toggleBtn();
		show.value = "Y";
	});
	
	// 검색버튼으로 검색
	searchBtn.addEventListener('click', function(){
		searchForm.requestSubmit();
	});
	
	// reset버튼으로 초기화
	resetBtn.addEventListener('click', function(){
		$('#search').find('input, select').each((i,v)=>{
			v.value = '';
		});
	});
	
	// 페이지 버튼 누르면 동작
	fnPaging = function(page){
		document.querySelector('#page').value = page;
		searchForm.requestSubmit();
	};
	
	// 신청 클릭시 상세보기
	fnDetail = function(tr, id){
		let streIssuNo = tr.dataset['streIssuNo'];
		axios(`${cp.value}/student/studentRecords/${streIssuNo}`)
		.then(({data})=>{
			let html = `
				<tr>
      				<th class="text-center">학번</th>
      				<td class="table-light text-center">${data.stuId}</td>
      				<th class="text-center">이름</th>
      				<td class="table-light">${data.studentVO.nm}</td>
      			</tr>
      			<tr>
      				<th class="text-center">신청일시</th>
      				<td class="table-light text-center">${data.reqstDt.substring(0,10)}<br>${data.reqstDt.substring(11)}</td>
      				<th class="text-center">신청학적</th>
      				<td class="table-light">${data.streCateCodeVO.cocoStts}</td>
      			</tr>
      			<tr>
      				<th class="text-center">처리일자</th>
			`;
			if(data.confmDe){
				html += `
	      				<td class="table-light text-center">${data.confmDe?.substring(0,4)}-${data.confmDe?.substring(4,6)}-${data.confmDe?.substring(6)}</td>
				`;
			}else{
				html += `
	      				<td class="table-light text-center">처리대기중</td>
				`;
			}
			html += `
      				<th class="text-center">결재상태</th>
      				<td class="table-light">${data.streStatusCodeVO.cocoStts}</td>
      			</tr>
      			<tr>
      				<th class="text-center">신청사유</th>
      				<td class="table-light" colspan="3">${data.streRes}</td>
      			</tr>
			`;
			if(data.atchFile){
				html += `
	      			<tr>
	      				<th class="text-center">첨부파일</th>
	      				<td class="table-light" colspan="3">
				`;
				for({atchFileId, fileSn, orignlFileNm, fileFancysize} of data.atchFile.fileDetails){
					html += `
						<a href='${cp.value}/atch/${atchFileId}/${fileSn}'>${orignlFileNm}(${fileFancysize})</a>&nbsp;
					`
				}
				html += `
						</td>
	      			</tr>
				`;
			}
			if(data.returnRsn){
				html += `
	      			<tr>
	      				<th class="text-center">반려사유</th>
	      				<td class="table-light" colspan="3">${data.returnRsn}</td>
	      			</tr>
				`;
			}
			$('#detailModal').find('tbody').html(html);
			$('#detailModal').attr('data-stre-issu-no', data.streIssuNo);
			
			if(data.streStatusCd == 'PS01'){
				$('.btn-prof').show();
			}else if(data.streStatusCd == 'PS02'){
				$('.btn-emp').show();
			}else{
				$('.btn-prof').hide();
				$('.btn-emp').hide();
			}
			
			new bootstrap.Modal('#detailModal').show();
		});
	};
	
	// 등록 모달 열기 전 처리되지 않은 이전 신청이 있는지 조회(있으면 상세화면으로)
	fnInsertModalOpen = function(id){
		axios.get(`${cp.value}/student/studentRecords/prevrequest/${id}`)
		.then(({data})=>{
			if(!data.streIssuNo){
				new bootstrap.Modal('#insertModal').show();
			}else{
				swal({
					title: "등록된 신청이 존재합니다",
					text: "처리되지 않은 신청을 조회하시겠습니까?",
					icon: "warning",
					buttons: ["취소", "확인"]
				}).then(btnVal=>{
					if(btnVal){
						$(`tr[data-stre-issu-no="${data.streIssuNo}"]`).trigger('click');
					}
				});
			}
		});
	};
	
	fnStreCateChange = function(select){
		let $streResSel = $('#streResSel');
		$streResSel.find('option').each((i,v)=>{
			if(!v.classList.contains('self')){
				if(v.classList.contains(select.value)){
					$(v).show();
				}else{
					$(v).hide();
				}
			}
		});
		$streResSel.val('');
		$streResSel.trigger('change');
	};
	
	let fileDiv = new bootstrap.Collapse('#fileDiv', {toggle: false});
	let fileEx = bootstrap.Tooltip.getInstance('#fileEx')
	fnStreResSelChange = function(select){
		let content = select.value;
		let streRes = insertForm.streRes;
		if(content != ''){
			streRes.disabled = true;
			let needFile = $(select).find(`option[value='${content}']`).data('needFile');
			if(needFile){
				fileDiv.show();
				fileEx.setContent({'.tooltip-inner': needFile});
			}else{
				fileDiv.hide();
			}
		}else{
			streRes.disabled = false;
			fileDiv.hide();
		}
		streRes.value = content;
		$('#fileDiv').find('input').val('');
	};
		
	fnInsertFormSubmit = function(btn){
		swal({
			title: "정말로 신청하시겠습니까?",
			text: "승인된 학적변동 신청은 어떠한 사유로도 취소가 불가합니다",
			icon: "warning",
			buttons: ["취소", "신청"]
		}).then(btnVal=>{
			if(btnVal){
				let formData = new FormData(insertForm);
				formData.set('streRes', insertForm.streRes.value);
				if($('#fileDiv').hasClass('show')){
					formData.set('needFile', 'Y');
				}
				axios.post(`${cp.value}/student/studentRecords/new`, formData)
				.then(resp=>{
					swal({
						title: "신청성공",
						text: "학적변동 신청이 완료되었습니다",
						icon: "success",
						button: "확인"
					}).then(()=>location.reload());
				}).catch(err=>{
					swal({
						title: "신청실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				})
			}
		});
	};
	
	fnCancelStuRec = function(btn){
		swal({
			title: "학적변동신청 취소",
			text: "정말로 취소하시겠습니까?",
			icon: "warning",
			dangerMode: true,
			buttons: ["닫기", "취소"]
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`${cp.value}/student/studentRecords/${$('#detailModal').data('streIssuNo')}/cancel`)
				.then(({data})=>{
					if(data.result){
						swal({
							title: "취소성공",
							text: "학적변동 신청이 취소되었습니다",
							icon: "success",
							button: "확인"
						}).then(()=>location.reload());
					}else{
						swal({
							title: "취소실패",
							text: "학적변동 신청 취소에 실패했습니다\r\n잠시 후 다시 시도해 주세요",
							icon: "error",
							button: "확인"
						});
					}
				}).catch(err=>{
					swal({
						title: "신청실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	};
	
	fnConsentStuRec = function(streStatusCd){
		axios.put(`${cp.value}/student/studentRecords/${$('#detailModal').data('streIssuNo')}/consent?streStatusCd=${streStatusCd}`)
		.then(({data})=>{
			if(data.result){
				swal({
					title: "승인성공",
					text: "학적변동 신청을 승인하였습니다",
					icon: "success",
					button: "확인"
				}).then(()=>location.reload());
			}else{
				swal({
					title: "승인실패",
					text: "학적변동 신청 승인에 실패했습니다\r\n잠시 후 다시 시도해 주세요",
					icon: "error",
					button: "확인"
				});
			}
		}).catch(err=>{
			swal({
				title: "승인실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	let returnRsn = document.querySelector('#returnRsn');
	document.querySelector('#returnStuRecModal').addEventListener('hidden.bs.modal', function(){
		returnRsn.value = '';
	});
	
	fnReturnStuRec = function(streStatusCd){
		axios.put(`${cp.value}/student/studentRecords/${$('#detailModal').data('streIssuNo')}/return`
				, {returnRsn: $('#returnRsn').val(), streStatusCd: streStatusCd})
		.then((resp)=>{
			swal({
				title: "반려성공",
				text: "학적변동 신청을 반려하였습니다",
				icon: "success",
				button: "확인"
			}).then(()=>location.reload());
		}).catch(err=>{
			swal({
				title: "반려실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
});
