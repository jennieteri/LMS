/**
 * 
 */
let fnPayTuition;
let fnPaging;

document.addEventListener('DOMContentLoaded', function(){
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
	});
	search.addEventListener('show.bs.collapse', function(){
		toggleBtn();
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
	
	searchForm.addEventListener('submit', e=>{
		e.preventDefault();
		table.draw();
	});
	
	let $tuiTable = $('#tuiTable');
	let orderColumn = ['STU_ID', 'NM', 'TUIT_STATUS_CD', 'TUIT_PAY_PERIOD']
	var table = $tuiTable.DataTable({
        processing: true // 로딩 표시 활성화
        , serverSide: true // 서버사이드 렌더링 활성화
		, searching: false
		, lengthChange: false
		, paging: false
		, info: false
        , ajax: {
            url: `${cp.value}/tuition`
            , type: 'POST'
			, contentType: 'application/json'
            , data: function (d) {
				let formData = new FormData(searchForm);
				let conditionObj = {};
				formData.forEach((value, key) => {
					if (value.trim() != '') {
        				conditionObj[key] = value;
					}
			    });
				d.detailCondition = conditionObj;
				let orderList = [];
				for(let order of d.order){
					orderList.push({
						column: orderColumn[order.column]
						, dir: order.dir
					});
				}
				d.orderList = orderList;
				d.length = $('#pageLength').val();
				d.start = d.length * ($('#page').val()-1);
				return JSON.stringify(d);
            }
			, dataSrc: function(json){
				$('#tuiTable tfoot').html('<tr><td colspan="6">' + json.pageHTML + '</td></tr>')
				return json.data;
			}
        }
        , columns: [
            { data: 'stuId' }
            , { data: 'studentVO.nm' }
            , { data: 'commonCodeVO.cocoStts' }
            , { 
				data: 'tuitPayPeriod' 
				, render: function(data, type, row){
					if(data){
						return `${data.substring(0,4)}-${data.substring(4,6)}-${data.substring(6)}`
					}
					return `<button class="btn btn-primary" onclick="fnPayTuition('${row.stuId}', '${row.semstrNo}')">
								납부 처리
							</button>`;
				}
			}
            , { 
				data: null
				, render: function(data, type, row){
					return `<a class="btn btn-danger" href="${cp.value}/tuition/${row.semstrNo}/${row.stuId}/invoice" target="_blank">
								PDF<i class="bi bi-file-earmark-pdf"></i>
							</a>`;
				}
			}
            , { 
				data: 'tuitStatusCd'
				, render: function(data, type, row){
					if(data == 'PAID'){
						return `<a class="btn btn-danger" href="${cp.value}/tuition/${row.semstrNo}/${row.stuId}/confirm" target="_blank">
									PDF<i class="bi bi-file-earmark-pdf"></i>
								</a>`;
					}
					return null;
				}
			}
        ]
		, columnDefs: [
	        {
	            targets: '_all'
	            , createdCell: function (td, cellData, rowData, row, col) {
	                $(td).addClass('text-center');
	            }
	        }
	        , {
	            targets: 4
	            , orderable: false
	        }
	        , {
	            targets: 5
	            , orderable: false
	        }
		]
		, initComplete: function(settings, json) {
	        $('#tuiTable thead th').css('text-align', 'center');
	        $('#tuiTable tbody').addClass('table-light align-middle');
	    }
		, language: {
	        emptyTable: "현재 표시할 학생이 존재하지 않습니다"
	        , zeroRecords: "해당하는 학생이 존재하지 않습니다"
	        , infoEmpty: "현재 표시할 학생이 존재하지 않습니다"
	    }
    });

	$('#pageLength').on('change', function () {
	    var newLength = $(this).val();
	    document.querySelector('#page').value = 1;
	    table.page.len(newLength).draw();
	});
	
	$('#semester').on('change', function () {
	    var newSemester = $(this).val();
	    document.querySelector('#semstrNo').value = newSemester;
	    document.querySelector('#page').value = 1;
	    table.draw();
	});
	
	fnPayTuition = function(stuId, semstrNo){
		console.log('pay', stuId, semstrNo);
		swal({
			title: "납부 처리하시겠습니까?",
			text: "납부 처리된 등록금은 이전으로 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`${cp.value}/tuition/${semstrNo}/${stuId}`)
				.then(resp=>{
					swal({
						title: "처리성공",
						text: "납부 처리에 성공했습니다",
						icon: "success",
						button: "확인"
					});
					table.draw();
				})
				.catch(err=>{
					swal({
						title: "처리실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	};
})