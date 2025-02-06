/**
 * 
 */
let fnLeesChange;
let fnLeesValueChange;
let fnScheduleClick;
let fnSubmit;
let fnAutoInsert;
document.addEventListener('DOMContentLoaded', function(){
	let $schedule = $('#schedule');
	
	// 온오프라인에 따라 강의실, 시간표 선택 폼 가시여부 변경
	document.querySelector('#lectOnlineYn').addEventListener('change', function(e){
		if(e.target.value == 'N'){
			$schedule.show();
		}else{
			$schedule.hide();
		}
	});
	
	// 평가기준 체크박스에 따라 입력 폼 가시여부 변경
	fnLeesChange = function(input){
		let classNm = input.value.toLowerCase();
		let $lees = $(`.${classNm}`);
		if(input.checked){
			$lees.show();
		}else{
			$lees.hide();
		}
		let inputVal = document.querySelector(`.${input.value}`);
		fnLeesValueChange(inputVal);
	};
	
	// 평가기준 퍼센트 입력시 합계 수정 로직
	fnLeesValueChange = function(input){
		let sum = 0;
		$('.leesNumber:visible').each((i,v)=>{
			sum += Number(v.value);
		});
		if(sum > 100){
			let minus = sum - 100;
			sum = 100;
			input.value = Number(input.value) - minus;
		}
		document.querySelector('#leesSum').textContent = `${sum} %`;
	};
	
	fnLeesValueChange();
	
	document.querySelectorAll('.dropdown-menu').forEach(v=>{
		v.addEventListener('click', function(e){
			e.stopPropagation();
		});
	});
	
	// 시간표 저장
	let scheduleObj = {};
	document.querySelectorAll('.scId').forEach(v=>{
		scheduleObj[v.value] = {
			etime: v.dataset['etime']
			, dotw: v.dataset['dotw']
		};
	});
	let $scheduleOut = $('.scheduleOut');
	fnScheduleClick = function(td){
		let scId = `${td.dataset['etime']}${td.dataset['dotw']}`;
		if(scId in scheduleObj){
			delete scheduleObj[scId];
			td.style.background = '';
			$(`.${scId}`).remove();
		}else{
			scheduleObj[scId] = {
				etime: td.dataset['etime']
				, dotw: td.dataset['dotw']
			};
			td.style.background = 'darkBlue';
			let $p = $('<span>', {'class': `${scId} badge rounded-pill text-bg-secondary m-1 fs-6`});
			$p.text(td.dataset['output']);
			$scheduleOut.append($p);
		}
	};
	
	// 등록 버튼 클릭
	fnSubmit = function(lectNo){
		// 기본 정보
		let lectureObj = {};
		for(let [k, v] of new FormData(document.forms['lecture']).entries()){
			lectureObj[k] = v;
		}
		lectureObj['profeId'] = id.value;
		
		// 시간표(오프라인 수업)
		if(lectureObj.lectOnlineYn == 'N'){
			let scheduleList = [];
			for(k in scheduleObj){
				let v = scheduleObj[k];
				scheduleList.push({
					croomCd: lectureObj.croomCd
					, dateCd: v.dotw
					, edcTimeCd: v.etime
				});
			}
			lectureObj.scheduleVO = scheduleList;
		}
		
		// 강의평가기준
		let lectEvalStanList = [];
		$('.leesNumber:visible').each((i,v)=>{
			lectEvalStanList.push({
				evlStdrCd: v.dataset['cocoCd']
				, rate: v.value
			})
		});
		lectureObj.lesVo = lectEvalStanList;
		
		// 주차별 계획
		let weekLecturePlanList = [];
		document.querySelectorAll('.week-lecture-plan').forEach(v=>{
			weekLecturePlanList.push({
				weekCd: v.dataset['weekCd']
				, lectPlan: v.value
			});
		});
		lectureObj.weekVO = weekLecturePlanList;
		
		if(lectNo){
			axios.put(`${cp.value}/lecture/request/${lectNo}/edit`, lectureObj)
			.then(({data})=>{
				location.href = `${cp.value}/lecture/request/${data.lectNo}`;
			}).catch(err=>{
				swal({
					title: "수정실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
			});
		}else{
			axios.post(`${cp.value}/lecture/request/new`, lectureObj)
			.then(({data})=>{
				location.href = `${cp.value}/lecture/request/${data.lectNo}`;
			}).catch(err=>{
				swal({
					title: "추가실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
			});
		}
	};
	
	fnAutoInsert = function(){
		let lecture = document.forms.lecture;
		lecture.subNo.value = 'SUB002';
		lecture.lectNm.value = '객체지향 프로그래밍의 이해';
		lecture.lectScore.value = '2';
		lecture.lectEnNope.value = '30';
		lecture.semstrNo.value = '202501';
		lecture.lectDescr.value = '객체지향 프로그래밍의 특성에 대해 이해하고 응용하자';
		document.querySelector('.week-lecture-plan[data-week-cd="W01"]').value = '강의 소개 및 객체지향 기본 개념';
		document.querySelector('.week-lecture-plan[data-week-cd="W02"]').value = '클래스와 객체';
		document.querySelector('.week-lecture-plan[data-week-cd="W03"]').value = '생성자와 소멸자';
		document.querySelector('.week-lecture-plan[data-week-cd="W04"]').value = '캡슐화 (Encapsulation)';
		document.querySelector('.week-lecture-plan[data-week-cd="W05"]').value = '상속 (Inheritance)';
		document.querySelector('.week-lecture-plan[data-week-cd="W06"]').value = '다형성 (Polymorphism)';
		document.querySelector('.week-lecture-plan[data-week-cd="W07"]').value = '추상 클래스와 인터페이스';
		document.querySelector('.week-lecture-plan[data-week-cd="W08"]').value = '중간고사';
		document.querySelector('.week-lecture-plan[data-week-cd="W09"]').value = '패키지와 접근 제어';
		document.querySelector('.week-lecture-plan[data-week-cd="W10"]').value = '예외 처리 (Exception Handling)';
		document.querySelector('.week-lecture-plan[data-week-cd="W11"]').value = '객체 협력과 설계 원칙';
		document.querySelector('.week-lecture-plan[data-week-cd="W12"]').value = '파일 입출력 및 스트림';
		document.querySelector('.week-lecture-plan[data-week-cd="W13"]').value = '컬렉션 프레임워크';
		document.querySelector('.week-lecture-plan[data-week-cd="W14"]').value = 'UML과 객체지향 설계';
		document.querySelector('.week-lecture-plan[data-week-cd="W15"]').value = '프로젝트 실습';
		document.querySelector('.week-lecture-plan[data-week-cd="W16"]').value = '기말고사 및 프로젝트 발표';
	}
})
