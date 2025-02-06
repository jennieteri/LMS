let fnGetPosition;
document.addEventListener('DOMContentLoaded', function(){
	let grid = document.querySelector('#grid');
	
	let gridOption = {
		cellHeight: 100
        , cellWidth: 100
	    , minRow: 6  // 최소 행수
	    , margin: 3  // 위젯 간격
	    , removable: '.trash'  // trash 클래스에 가져가면 삭제
	    , disableResize: true  // 크기조정 막기
	};
	let gridInst = GridStack.init(gridOption, grid);
	
	gridInst.on('dragstart', function(event, el) {
        document.querySelector('.trash').classList.remove('hidden');
        document.querySelector('.trash').classList.add('visible');
    });

    gridInst.on('dragstop', function(event, el) {
        document.querySelector('.trash').classList.remove('visible');
        document.querySelector('.trash').classList.add('hidden');
    });
	
	let modules = [];
	axios.get(`${cp.value}/mainui/my`)
	.then(({data})=>{
		for(let mod of data){
			mod.id = mod.modId
			modules.push(mod);
		}
		gridInst.load(modules);
	    const items = grid.querySelectorAll('.grid-stack-item');
	    items.forEach((item, index) => {
	        const content = item.querySelector('.grid-stack-item-content');
	        if (content) {
				$(content).load(`${cp.value}${content.innerText}`);
	        }
	    });
	});
	
	$("#addBtn").on('click', function(){
		$('#modDiv').toggleClass('hidden visible');
	});
	
	$(".modDrag").draggable({
		helper: "clone",
		cursor: "move",
		revert: "invalid"
	});
	
	$('#grid').droppable({
		accept: ".modDrag",
	    drop: function (event, ui) {
			let icon = ui.helper[0];
			if(!$(`.grid-stack-item[gs-id='${icon.dataset.modId}']`)[0]){
				gridInst.addWidget({w: icon.dataset.w, h: icon.dataset.h, id: icon.dataset.modId});
				let $addItem = $(`.grid-stack-item[gs-id='${icon.dataset.modId}']`).find('.grid-stack-item-content');
				$addItem.load(`${cp.value}${icon.dataset.content}`, function(){
					$addItem.find('script').each(function(){
						eval($(this).text());
					});
				});
			}else{
				swal({
					title: "모듈추가실패"
					, text: "이미 추가된 모듈입니다"
					, icon: "warning"
					, button: "확인"
				});
			}
	    }
	});
	
	// 페이지 종료시 수정된 메인UI 저장 이벤트
	window.addEventListener('beforeunload', (e)=>{
		let modList = [];
		let isChange = false;
		let cnt = 0;
		let dispMod = gridInst.save();
		if(dispMod.length != modules.length){
			isChange = true;
		}
		for(let mod of dispMod){
			modList.push({modId: mod.id, x: mod.x, y: mod.y})
			for(let pastMod of modules){
				if(!isChange && mod.id == pastMod.d){
					isChange = (mod.x != pastMod.x) || (mod.y != pastMod.y);
					cnt++;
					break;
				}
			}
		}
		if(modules.length != cnt){
			isChange = true;
		}
		if(isChange){
			const blob = new Blob([JSON.stringify(modList)], {type: 'application/json; charset=UTF-8'}); 
			navigator.sendBeacon(`${cp.value}/mainui/my`, blob);
		}
    });
})