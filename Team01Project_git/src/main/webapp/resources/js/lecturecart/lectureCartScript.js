

let fnGetLectureCartList;
let fnInsertCart;
let fnDeleteCart;
let fnShowLecturePaper;
let fnEnterKeyEvent;
// null 값을 제거하는 재귀 함수
let fnRemoveNullProperties = function(obj) {
	if (typeof obj !== 'object' || obj === null) {
		return obj; // 객체가 아니거나 null인 경우 그대로 반환
	}

	// 객체를 복제하면서 null이 아닌 값만 유지
	if (Array.isArray(obj)) {
		return obj.map(item => fnRemoveNullProperties(item)).filter(item => item !== null);
	}

	const result = {};
	for (const key in obj) {
		if (obj[key] !== null) {
			result[key] = fnRemoveNullProperties(obj[key]);
		}
	}
	return result;
};

document.addEventListener("DOMContentLoaded", async () => {
	const cp = document.querySelector("#cp").value;
	let table;
	let totalScore = 0;
	
	// 강의 목록 가져오기
	fnGetLectureCartList = async function() {
		const $dummyTable = $("#parentTable"); // 강의 데이터 테이블
		const $studentTable = $("#studentTable"); // 장바구니 데이터 테이블
	
		$.ajax({
			url: `${cp}/lectureCart`,
			type: "GET",
			success: function(data) {
				const cleanData = fnRemoveNullProperties(data);
				const lectureList = cleanData.lectureList;
				const studentLectureList = cleanData.studentLectureList;
				const studentLectNos = new Set(cleanData.studentLectureList.map(lecture => lecture.lectNo));
	
				// $dummyTable 데이터테이블 초기화
				table = $dummyTable.DataTable({
					language: {
						emptyTable: "등록된 강의가 없습니다",
						zeroRecords: "검색조건에 해당하는 강의가 존재하지 않습니다",
						lengthMenu: "_MENU_",
						info: "총 _TOTAL_ 건의 강의 중 _START_ ~ _END_ 표시",
						infoEmpty: "강의 없음",
						infoFiltered: "(전체 _MAX_ 건 중 검색결과)"
					},
					data: lectureList,
					dom: 'lrtip', // 기본 검색창 제거
					searching: true,
					lengthChange: false,
					classes: {
						header: 'fw-bold',  // 헤더 텍스트 굵게
						sInfo: 'text-primary mb-2', // 정보 텍스트 스타일
						sPageButton: 'btn btn-outline-primary' // 페이지네이션 버튼 스타일
					},
					rowCallback: function(row, data) {
						$(row).addClass('text-center align-middle'); // 행 내용 수직 중앙 정렬
					},
					columns: [
						{ data: null, name: 'lectNm', title: '강의명'
							, render: function(data, type, row){
								return `[${row.subjectVO.subFicdCd}]<br>${row.lectNm}`
							} 
						},
						{ data: 'professorVO.nm', name: 'professorVO.nm', title: '교수명' },
						{ data: 'subjectVO.departmentVO.deptNm', name: 'subjectVO.departmentVO.deptNm', title: '학과' },
						{ data: 'subjectVO.gradeCd', name: 'subjectVO.gradeCd', title: '학년' },
						{ data: 'lectScore', title: '학점' },
						{ data: 'lectEnNope', title: '정원' },
						{
						    data: null,
						    title: '시간표',
						    render: function(data, type, row) {
						        if (row.lectOnlineYn === 'Y') {
						            return '온라인';
						        }else{
									let scheduleStr = '';
									for(let schedule of row.scheduleVO){
										scheduleStr += `${schedule.commonCodeVO.cocoStts} ${schedule.ettcVO.commonCodeVO.cocoStts} / `;
									}
							        return `${scheduleStr.slice(0,-2)}<br>${row?.scheduleVO[0]?.classRoomVO?.croomPstn}`;
								}
						    }
						},
						{
							data: null,
							title: '',
							render: function(data, type, row) {
								if (studentLectNos.has(row.lectNo)) {
									return `
										<button class="btn btn-secondary" onclick="fnShowLecturePaper('${row.lectNo}')"><i class="bi bi-file-text"></i></button>
										<button class="btn btn-warning btn-${row.lectNo}" onclick="fnDeleteCart('${row.lectNo}')"><i class="bi bi-bag-dash-fill"></i></button>
										<button class="btn btn-primary btn-${row.lectNo}" onclick="fnInsertCart('${row.lectNo}')" style="display: none;"><i class="bi bi-bag-plus-fill"></i></button>
									`;
								} else {
									return `
										<button class="btn btn-secondary" onclick="fnShowLecturePaper('${row.lectNo}')"><i class="bi bi-file-text"></i></button>
										<button class="btn btn-warning btn-${row.lectNo}" onclick="fnDeleteCart('${row.lectNo}')" style="display: none;"><i class="bi bi-bag-dash-fill"></i></button>
										<button class="btn btn-primary btn-${row.lectNo}" onclick="fnInsertCart('${row.lectNo}')"><i class="bi bi-bag-plus-fill"></i></button>
									`;
								}
							}
						}
					],
					columnDefs: [
						{
				            targets: '_all'
				            , createdCell: function (td, cellData, rowData, row, col) {
				                $(td).addClass('text-center');
				            }
				        }
				        , {
				            targets: [5, 6, 7]
				            , orderable: false
				        }
					]
					, initComplete: function(settings, json) {
				        $('#parentTable thead th').css('text-align', 'center');
				        $('#parentTable tbody').addClass('table-light');
				    }
					, createdRow: function(row, data, dataIndex) {
				        $(row).attr('id', data.lectNo);
						$(row).hover(
							function(){
								let scheduleList = data.scheduleVO;
								for(let schedule of scheduleList){
									let $td = $('.cj-schedule').find(`td[data-schedule='${schedule.dateCd}-${schedule.edcTimeCd}']`)
									if($td.is('[data-lectNo]')){
										$td.css('border', '3px solid red');
									}else{
										$td.css('border', '3px solid darkBlue');
									}
								}
							}
							, function(){
								let scheduleList = data.scheduleVO;
								for(let schedule of scheduleList){
									let $td = $('.cj-schedule').find(`td[data-schedule='${schedule.dateCd}-${schedule.edcTimeCd}']`)
									$td.css('border', '');
								}
							}
						);
				    }
				});
				
				studentLectureList.forEach(row=>{
					totalScore += row.lectScore;
					let bgColor = fnBgColor();
					let scheduleList = row.scheduleVO;
					for(let schedule of scheduleList){
						let $td = $('.cj-schedule').find(`td[data-schedule='${schedule.dateCd}-${schedule.edcTimeCd}']`)
						$td.css('background', bgColor);
						$td.attr('data-lectNo', row.lectNo);
					}
					
					let html = `
						<div class="card mb-2 card-${row.lectNo}">
						  <div class="card-header py-3" style="background: ${bgColor};">
						    <h5 class="card-title fw-bold p-0 m-0">[${row.subjectVO.subFicdCd}] ${row.lectNm}
								<button class="btn float-end p-0" onclick="fnDeleteCart('${row.lectNo}')"><i class="bi bi-x-lg"></i></button>
							</h5>
						  </div>
						  <div class="card-body pb-2">
						    <h6 class="card-title p-0 mt-2">${row.professorVO.nm} 교수</h6>
							<span class="card-text p-0">${row.lectScore}학점 / ${row.lectEnNope}명</span>
					`;
					if (row.lectOnlineYn === 'Y') {
			            html += `<p class="card-text p-0 float-end">온라인</p>`;
			        }else{
				        html += `<p class="card-text p-0 float-end">${row.joinSchedule} ${row?.scheduleVO[0]?.classRoomVO?.croomPstn}</p>`;
					}
					html += `
						  </div>
						</div>
					`;
					$('#cartDiv').append(html);
				});
				$('#total-score').text(totalScore);
			},
			error: function(request, status, error) {
				console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
	}
	
	$('#resetButton').on('click', function() {
		$('#searchInput').val('');
		table.search('').columns().search('').draw();
	});

	// 검색 기능 추가
	$('#searchButton').on('click', function() {
		var searchColumn = $('#searchColumn').val(); // 선택된 검색 조건
		var searchTerm = $('#searchInput').val(); // 입력된 검색어
			console.log(searchColumn)
			console.log(searchTerm)
		// 선택된 열에서 검색 수행
		table.column(`${searchColumn}:name`).search(searchTerm).draw();
	});
	
	$('#pageLength').on('change', function () {
	    var newLength = $(this).val();
	    table.page.len(newLength).draw();
	});
	
	fnInsertCart = async function(lectNo) {
		let $tr = $(`#${lectNo}`);
		let insertOk = true;
		$('.cj-schedule').find('td').each((i,v)=>{
			if($(v).css('border').indexOf('solid rgb(255, 0, 0)') != -1){
				swal({
		            title: "추가 실패",
		            text: "선택한 강의와 시간표가 겹치는 강의가 존재합니다",
		            icon: "error",
		            button: "확인"
		        });
				insertOk = false;
				return;
			}
		});
		
		if(insertOk && (totalScore + Number($tr.find('td:eq(4)').text())) > 18){
			swal({
	            title: "추가 실패",
	            text: "신청 가능 학점을 초과하였습니다",
	            icon: "error",
	            button: "확인"
	        });
			insertOk = false;
		}
		
		if(!insertOk){
			return;
		}
		
		totalScore += Number($tr.find('td:eq(4)').text());
		$('#total-score').text(totalScore);
		
	    const stuId = document.getElementById("stuId").value;
		
		axios.post(`${cp}/lectureCart`
			, {
				lectNo: lectNo
	            , stuId: stuId
			})
		.then(({data})=>{
			$(`.btn-${lectNo}`).toggle();
			
			let bgColor = fnBgColor();
			let scheduleList = data.lectureVO.scheduleVO;
			for(let schedule of scheduleList){
				let $td = $('.cj-schedule').find(`td[data-schedule='${schedule.dateCd}-${schedule.edcTimeCd}']`)
				$td.css('background', bgColor);
				$td.attr('data-lectNo', lectNo);
			}
			
			let html = `
				<div class="card mb-2 card-${lectNo}">
				  <div class="card-header py-3" style="background: ${bgColor};">
				    <h5 class="card-title fw-bold p-0 m-0">${$tr.find('td:eq(0)').html().replace("<br>", " ")}
						<button class="btn float-end p-0" onclick="fnDeleteCart('${lectNo}')"><i class="bi bi-x-lg"></i></button>
					</h5>
				  </div>
				  <div class="card-body pb-2">
				    <h6 class="card-title p-0 mt-2">${$tr.find('td:eq(1)').text()} 교수</h6>
					<span class="card-text p-0">${$tr.find('td:eq(4)').text()}학점 / ${$tr.find('td:eq(5)').text()}명</span>
		        	<p class="card-text p-0 float-end">${$tr.find('td:eq(6)').html().replace("<br>", " ")}</p>
				  </div>
				</div>
			`;
			$('#cartDiv').append(html);
		}).catch(err=>{
			swal({
	            title: "처리 실패",
	            text: err.resp.data.message,
	            icon: "error",
	            button: "확인"
	        });
		});
	}
	
	fnDeleteCart = async function(lectNo) {
	    const stuId = document.getElementById("stuId").value;
	    
		axios.delete(`${cp}/lectureCart?lectNo=${lectNo}&stuId=${stuId}`)
		.then(resp=>{
			$(`.btn-${lectNo}`).toggle();
			$(`.card-${lectNo}`).remove();
			let $td = $('.cj-schedule').find(`td[data-lectNo='${lectNo}']`);
			$td.css('background', '');
			$td.removeAttr('data-lectNo');
			
			let $tr = $(`#${lectNo}`);
			totalScore -= $tr.find('td:eq(4)').text();
			$('#total-score').text(totalScore);
		}).catch(err=>{
			console.log(err);
			swal({
	            title: "처리 실패",
	            text: err?.resp?.data?.message,
	            icon: "error",
	            button: "확인"
	        });
		});
	}
	
	fnShowLecturePaper = function(lectNo) {
		window.open(`${cp}/lectureCart/lecturePaper/${lectNo}`,
			"testPopup",
			"width=800,height=700,scrollbars=yes,resizable=yes,toolbar=no,menubar=no");
	}
	
	const fnBgColor = function(){
		const r = Math.floor(Math.random() * 56) + 180;
	    const g = Math.floor(Math.random() * 56) + 180;
	    const b = Math.floor(Math.random() * 56) + 180;
		return `rgb(${r}, ${g}, ${b})`;
	}
	
	await fnGetLectureCartList();
	
	fnEnterKeyEvent = function(){
		var searchColumn = $('#searchColumn').val(); // 선택된 검색 조건
		var searchTerm = $('#searchInput').val(); // 입력된 검색어
		// 선택된 열에서 검색 수행
		table.column(`${searchColumn}:name`).search(searchTerm).draw();
	}
	
	const cartDiv = document.getElementById("cartDiv");
	const noneText = document.getElementById("noneText");
	const observer = new MutationObserver(() => {
		if (cartDiv.children.length === 0) {
			noneText.style.display = 'block';
		} else {
			noneText.style.display = 'none';
		}
	});
	observer.observe(cartDiv, { childList: true });
})








