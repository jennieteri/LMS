
document.addEventListener("DOMContentLoaded", async () => {
	const getDummyUrl = `${url}/profe/new/dissent/list` //요청 url
	const $dummyTable = $("#parentTable"); // table
	await dataTableFunction(getDummyUrl, $dummyTable); //데이터 테이블은 jquery객체를 넘겨야함
})


//
async function dataTableFunction(sendUrl, parentTable) {

	$.ajax({
		url: sendUrl,
		type: "GET",
		success: function(data) {
			//	console.log(data) 전체 데이터
			//	console.log(data[0].lectVO.lectNo) 리스트=> 배열로 반환
			parentTable.dataTable({
				data: data,
				columns: [ 		//VO가 has 관계를 지녀 그 안의 데이터를 가져올 경우에는 . 닷노테이션 접근
					{
						data: 'lectVO.lectNo', title: '강의번호', //조건마다 다른 형태를 표시해야하면 render:function if(T?F?){return <tag>} else{return <tag>}
						render: function(data, type, row) {
							if (row.lectVO.lectNo == 'L003') { 
								return `<button class="btn btn-primary" >${row.lectVO.lectNo}</button>`;
							} else { return `<button class="btn btn-primary" >asdasdasd</button>` }
						}
					},   // 강의번호
					{ data: 'lectVO.lectNm', title: '강의명' },     // 강의명
					{ data: 'personVO.id', title: '학번' },         // 학번
					{ data: 'personVO.nm', title: '이름' },         // 이름
					{
						data: null,
						title: '조회버튼',
						render: function(data, type, row) {
							// 버튼 클릭 시 row 데이터 전달
							return `<button class="btn btn-primary" 
                                onclick="showDetails('${row.lectVO.lectNo}', '${row.personVO.id}')">조회</button>`;
						}
					}
				]
			});

		}, error: function(request, status, error) {
			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}


function showDetails(a, b) {
	console.log(a, b)
}
