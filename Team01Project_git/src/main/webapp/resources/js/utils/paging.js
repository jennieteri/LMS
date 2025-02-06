/**
 *  data-pg-target : 입력 UI 와 한세트인 전송 form 의 식별자
 *  data-pg-fn-name : window 객체의 메소드로 정의할 글로벌 함수 이름(ex: paging)
 *  두가지 data 속성을 입력 UI 엘리먼트를 대상으로 설정하고, 해당 규칙으로 페이징과 검색을 지원함.
 *  비동기 페이징과 검색이 필요한 경우, 전송 form 에 대한 submit 이벤트 핸들러로 consumer 가 직접 처리한다.
 */

//paging처리 함수

function fnPaging(page){
		console.log(page);
		searchForm.page.value = page;
		searchForm.requestSubmit();
	}


document.addEventListener("DOMContentLoaded", ()=>{
	
	let $searchArea = $("[data-pg-target][data-pg-fn-name]").filter((index, el)=>{
		let targetSelector = el.dataset.pgTarget;
		let $searchForm = $(targetSelector);
		let filtered = $searchForm.length == 1;
		if(filtered){
			let fnName = el.dataset.pgFnName;
			window[fnName] = function(page){
				$searchForm.find("input[name=page]").val(page);
				$searchForm.submit();
			}
		}
		return filtered;
	});
	
	
	$searchArea.find("#search-btn").on("click", function(){
		let $parent = $(this).parents(".search-area:first");
		let targetSelector = $parent?.data("pgTarget");
		let $searchForm = $(targetSelector);
		
		$parent.find(":input[name]").each(function(index, ipt){
			$searchForm.find(`[name="${ipt.name}"]`).val(ipt.value);
			$searchForm.submit(); 	
		});
	});
	
	document.querySelector('#searchType').addEventListener('change', function(){
		document.querySelector('#searchWord').value = '';
	});
});