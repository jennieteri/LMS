/**
 * 
 */
let fnPaging;
let fnDetailStu;
let fnInsertAcademic;
let fnExpulsion;

document.addEventListener("DOMContentLoaded", function(){
	let search = document.getElementById('search');
	let searchDiv = document.getElementById('searchDiv');
	let searchBtn = document.getElementById('searchBtn');
	let resetBtn = document.getElementById('resetBtn');
	let searchForm = document.forms['searchForm'];
	let insertAcademicForm = document.forms['insertAcademicForm'];
	let $stuTable = $('#stuTable');
	let detailStuModal = new bootstrap.Modal('#detailStuModal');
	let insertAcademicModal = new bootstrap.Modal('#insertAcademicModal');
	
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
	
	let orderColumn = ['STU_ID', 'NM', 'GRADE_CD', 'DEPT_CD', 'STRE_CATE_CD']
	var table = $stuTable.DataTable({
        processing: true // 로딩 표시 활성화
        , serverSide: true // 서버사이드 렌더링 활성화
		, searching: false
		, lengthChange: false
		, paging: false
		, info: false
        , ajax: {
            url: `${cp.value}/student`
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
				for(order of d.order){
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
				$('#stuTable tfoot').html('<tr><td colspan="6">' + json.pageHTML + '</td></tr>')
				return json.data;
			}
        }
        , columns: [
            { data: 'stuId' }
            , { data: 'nm' }
            , { data: 'gradeCocoVO.cocoStts' }
            , { data: 'departmentVO.deptNm' }
            , { data: 'streCateCocoVO.cocoStts' }
            , { data: 'professor.nm' }
        ]
		, columnDefs: [
	        {
	            targets: '_all'
	            , createdCell: function (td, cellData, rowData, row, col) {
	                $(td).addClass('text-center');
	            }
	        }
	        , {
	            targets: 5
	            , orderable: false
	        }
		]
		, initComplete: function(settings, json) {
	        $('#stuTable thead th').css('text-align', 'center');
	        $('#stuTable tbody').addClass('table-light');
	    }
		, createdRow: function(row, data, dataIndex) {
	        $(row).attr('onclick', `fnDetailStu(${data.stuId});`);
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
	
	fnDetailStu = function(id){
		axios.get(`${cp.value}/student/${id}`)
		.then(({data})=>{
			$('#detailStuModal').attr('data-stu-id', data.id);
			if(data.streCateCd == "SC04"){
				$('#expulsionBtn').hide();
			}else{
				$('#expulsionBtn').show();
			}
			let photoSrc = data.proflPhoto ? `data:image/*;base64,${data.proflPhoto}` : `${cp.value }/resources/NiceAdmin/assets/img/user.png`;
			let basicInfoHTML = `
				<table class="table table-bordered table-primary align-middle m-0">
					<colgroup>
						<col width="100px">
						<col width="60px">
						<col width="120px">
					</colgroup>
		      		<tbody>
		      			<tr>
		      				<th class="bg-white" rowspan="6">
								<img width="100%" src="${photoSrc}"/></th>
		      				<th class="text-center">학번</th>
		      				<td class="table-light">${data.id}</td>
		      			</tr>
		      			<tr>
		      				<th class="text-center">이름</th>
		      				<td class="table-light">${data.nm}</td>
		      			</tr>
		      			<tr>
		      				<th class="text-center">학년</th>
		      				<td class="table-light">${data.gradeCocoVO.cocoStts}</td>
		      			</tr>
		      			<tr>
		      				<th class="text-center">학과</th>
		      				<td class="table-light">${data.departmentVO.deptNm}</td>
		      			</tr>
		      			<tr>
		      				<th class="text-center">학적</th>
		      				<td class="table-light">${data.streCateCocoVO.cocoStts}</td>
		      			</tr>
		      			<tr>
		      				<th class="text-center">담당교수</th>
		      				<td class="table-light">${data.professor.nm}</td>
		      			</tr>
						<tr>
							<th class="text-center">전화번호</th>
							<td class="table-light" colspan="2">${data.mbtlnum ?? '등록된 전화번호가 없습니다'}</td>
						</tr>
						<tr>
							<th class="text-center">이메일</th>
							<td class="table-light" colspan="2">${data.eml ?? '등록된 이메일이 없습니다'}</td>
						</tr>
						<tr>
							<th class="text-center">주소</th>
							<td class="table-light" colspan="2">${data.zip ? '('+data.zip+')' : ''} ${data.rdnmadr ?? '등록된 주소가 없습니다'} ${data.daddr ?? ''}</td>
						</tr>
		      		</tbody>
		      	</table>
			`;
			$('#basicInfo').html(basicInfoHTML);
			
			let transcriptHTML = `
				<table class="table table-bordered table-primary align-middle m-0 text-center">
					<thead>
						<tr>
							<th style="width: 100px">학기</th>
							<th style="width: 100px">성적</th>
						</tr>
					</thead>
					<tbody class="table-light">
			`;
			if(data.transcriptList.length > 0){
				for(let transcript of data.transcriptList){
					transcriptHTML += `
						<tr>
							<td>${transcript.semeCd.substring(0,4)}-${transcript.semeCd.substring(4)}</td>
							<td>${transcript.transTotal}점</td>
						</tr>
					`;
				}		
			}else{
				transcriptHTML += `
					<tr>
						<td colspan="2">등록된 최종성적이 없습니다</td>
					</tr>
				`;
			}
			transcriptHTML += `
					</tbody>
				</table>
			`;
			$('#transcript').html(transcriptHTML);
			
			let academicHTML = `
				<table class="table table-bordered table-primary align-middle m-0">
					<thead class="text-center">
						<th style="width: 100px">경고번호</th>
						<th style="width: 110px">부여일자</th>
						<th>사유</th>
					</thead>
					<tbody class="table-light">
			`;
			if(data.academicProbationList.length > 0){
				for(let academic of data.academicProbationList){
					academicHTML += `
						<tr>
							<td class="text-center">${academic.proNo}</td>
							<td class="text-center">${academic.proDate.substring(0,4)}-${academic.proDate.substring(4,6)}-${academic.proDate.substring(6)}</td>
							<td>${academic.proRes}</td>
						</tr>
					`;
				}		
			}else{
				academicHTML += `
					<tr>
						<td class="text-center" colspan="3">등록된 학사경고가 없습니다</td>
					</tr>
				`;
			}	
			academicHTML += `
					</tbody>
				</table>
			`;
			$('#academic').html(academicHTML);
			
			insertAcademicForm.stuId.value = data.id;
			insertAcademicForm.nm.value = data.nm;

			detailStuModal.show();
		}).catch((err)=>{
			swal({
				title: "조회실패",
				text: "잠시 후 다시 시도해주세요",
				icon: "error",
				button: "확인"
			});
		});
	};
	
	document.querySelector('#insertAcademicModal').addEventListener('hidden.bs.modal', function(){
		document.forms.insertAcademicForm.proRes.value = '';
	});
	
	fnInsertAcademic = function(){
		axios.post(`${cp.value}/student/academicProbation`
			, new FormData(document.forms.insertAcademicForm))
		.then(({data})=>{
			insertAcademicModal.hide();
			fnDetailStu(data.academicProbationVO.stuId);
		}).catch(err=>{
			swal({
				title: "등록실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	fnExpulsion = function(){
		swal({
			title: "학생을 제적시키시겠습니까?",
			text: "학적이 제적으로 변경되면 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`${cp.value}/student/expulsion/${insertAcademicForm.stuId.value}`)
				.then(({data})=>{
					swal({
						title: "작업성공",
						text: "학적을 제적으로 변경하였습니다",
						icon: "success",
						button: "확인"
					});
					fnDetailStu(insertAcademicForm.stuId.value);
					table.draw();
				}).catch(err=>{
					swal({
						title: "작업실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	};
})
















