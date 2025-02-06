import {
	ClassicEditor,
	SimpleUploadAdapter,
	Bold,
	Italic,
	Underline,
	BlockQuote,
	Essentials,
	Heading,
	Image,
	ImageUpload,
	Link,
	List,
	MediaEmbed,
	Table,
	TableToolbar,
	FontSize,
	FontFamily,
	FontColor,
	Undo
} from '../../../resources/js/ckeditor5/ckeditor5.js';

// 강의 입장 버튼 클릭
window.fnLectOrderDetail = async function(btn) {
	let $tr = $(btn).parents('.orderLecture');
	let targetDate = new Date();
	let startDate = new Date($tr.find('.sectDt').text());
	let endDate = new Date($tr.find('.sectEt').text());
	endDate.setDate(endDate.getDate() + 1);

	if (!(targetDate.getTime() >= startDate.getTime() && targetDate.getTime() <= endDate.getTime())) {
		await swal({
			title: "학습기간이 아닙니다",
			text: "시청시간이 출석시간으로 인정되지 않습니다",
			icon: "warning",
			button: "확인"
		});
	}
	location.href = `${$('#cp').val()}/lecture/${$('#lectNo').val()}/materials/${$tr.data('weekCd')}/${$tr.data('lectOrder')}/viewer`;
}

document.addEventListener("DOMContentLoaded", () => {
	$('#autoInsert').on('click', function(){
		let insertDataForm = document.forms.insertDataForm;
		insertDataForm.weekCd.value = 'W01';
		insertDataForm.lectOrder.value = '1';
		insertDataForm.sectNm.value = '강의 목표, 평가 방식 및 학습 방법 안내';
		insertDataForm.sectDt.value = '2025-01-13';
		insertDataForm.sectEt.value = '2025-01-19';
		insertDataForm.sectIdnty.value = '60';
		$('#output').text('60');
	});
	
	axios.get(`${cp.value}/commoncode/ETIME`)
		.then(({ data }) => {
			$('.dataSectEtime').append('<option selected label="교시선택"/>');
			data.forEach(v => {
				$('.dataSectEtime').append(`<option value="${v.cocoCd}" label="${v.cocoStts}">`)
			});
		});

	axios.get(`${cp.value}/commoncode/classRoom`)
		.then(({ data }) => {
			$('.dataCroomCd').append('<option selected label="강의실선택"/>');
			data.forEach(v => {
				$('.dataCroomCd').append(`<option value="${v.croomCd}" label="${v.croomPstn}">`)
			});
		});

	// 주차 공통 코드 리스트 불러오기
	const getWeek = function() {
		axios.get(`${cp.value}/commoncode/WEEK`)
			.then(({ data }) => {
				$("#weekCd").empty();
				$("#weekCd").append('<option selected label="주차선택"/>');
				leweNm.value = "";
				data.forEach(v => {
					if ($(`.weekCd[data-week-cd="${v.cocoCd}"]`).length == 0) {
						$("#weekCd").append(`<option value="${v.cocoCd}" label="${v.cocoStts}">`);
					}
				});
			});
	};

	// 차시 주차 공통 코드 추가하기
	const getOrderWeek = function() {
		$(".dataWeekCd").empty();
		$(".dataWeekCd").append('<option selected label="등록주차선택"/>');
		document.querySelectorAll('.weekCd').forEach(v => {
			$('.dataWeekCd').append(`<option value="${$(v).data('weekCd')}" label="${$(v).find('.cocoStts').text()}">`);
		});
	};

	$("#insertWeekModal").on('show.bs.modal', function() {
		getWeek();
		getOrderWeek();
	});

	$("#insertWeekModal").on('hidden.bs.modal', function() {
		document.getElementById("insertDataForm").reset();
		document.getElementById("output").textContent = '0';
	});

	// 추가 모달 주차/차시 선택
	$('input[type="radio"][name="insertOption"]').on('change', function() {
		$('#insertWeekDiv').toggle();
		$('#insertOrderDiv').toggle();
	})

	// 주차 추가 버튼 클릭
	document.querySelector('.week-insert-btn')?.addEventListener('click', function() {
		axios.post(`${cp.value}/lecture/${lectNo.value}/materials/new`
			, {
				weekCd: $('#weekCd').val(),
				leweNm: $('#leweNm').val()
			}).then(resp => {
				swal({
					title: "추가완료",
					text: "주차가 성공적으로 추가되었습니다",
					icon: "success",
					button: "확인"
				}).then(() => location.reload());
			}).catch(err => {
				swal({
					title: "추가실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
			});
	});

	// 주차 수정 버튼 클릭
	$(document).on('click', '.week-update-btn', function() {
		let $tr = $(this).parents('tr');
		$tr.find('button').toggle();
		let $td = $tr.find('.leweNm');
		$td.html(`<input class="form-control" type="text" value="${$td.text()}" data-origin="${$td.text()}"/>`);
	});

	// 주차 수정 저장 버튼 클릭
	$(document).on('click', '.week-update-save-btn', function() {
		let $tr = $(this).parents('tr');
		let $td = $tr.find('.leweNm');
		let $input = $td.find('input');
		axios.put(`${cp.value}/lecture/${lectNo.value}/materials/${$tr.data("weekCd")}/edit`
			, { leweNm: $input.val() })
			.then(resp => {
				swal({
					title: "수정완료",
					text: "주차명이 성공적으로 수정되었습니다",
					icon: "success",
					button: "확인"
				});
				$td.text($input.val());
			}).catch(err => {
				swal({
					title: "수정실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
				$td.text($input.data("origin"));
			});
		$tr.find('button').toggle();
	});

	// 주차 수정 취소 버튼 클릭
	$(document).on('click', '.week-update-cancel-btn', function() {
		let $tr = $(this).parents('tr');
		$tr.find('button').toggle();
		let $td = $tr.find('.leweNm');
		let $input = $td.find('input');
		$td.text($input.data("origin"));
	});

	// 주차 삭제 버튼 클릭
	$(document).on('click', '.week-delete-btn', function() {
		let $tr = $(this).parents('tr');
		swal({
			title: "정말로 삭제하시겠습니까?",
			text: "주차를 삭제하면 주차에 해당하는 차시까지 같이 삭제됩니다",
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal => {
			if (btnVal) {
				axios.delete(`${cp.value}/lecture/${lectNo.value}/materials/${$tr.data("weekCd")}`)
					.then(resp => {
						swal({
							title: "삭제완료",
							text: "주차가 성공적으로 삭제되었습니다",
							icon: "success",
							button: "확인"
						});
						// 삭제 후 처리(주차 정보 삭제, 하위 차시 삭제, 차시 추가 버튼 삭제)
						$(`.delWeek[data-week-cd="${$tr.data('weekCd')}"]`).remove();
					}).catch(err => {
						swal({
							title: "삭제실패",
							text: err.response.data.message,
							icon: "error",
							button: "확인"
						});
					})
			}
		});
	});

	const insertDataForm = document.forms["insertDataForm"];
	const updateDataForm = document.forms["updateDataForm"];
	let formArray = [insertDataForm, updateDataForm]

	formArray.forEach(form => {
		if (form) {
			// 강의차시 검증
			form.lectOrder.addEventListener('change', function() {
				let input = form.lectOrder.value;
				if (input < 0 || Number(input) > lectSession.value) {
					swal({
						title: "입력오류",
						text: `강의 차시는 1 ~ ${lectSession.value} 사이의 값이어야 합니다`,
						icon: "error",
						button: "확인"
					});
					form.lectOrder.value = '';
				}
				if ($(`.lectOrder[data-lect-order="${input}"]`).length > 0 && this.dataset['origin'] != input) {
					swal({
						title: "입력오류",
						text: "해당 강의 차시는 이미 등록되었습니다",
						icon: "error",
						button: "확인"
					});
					form.lectOrder.value = '';
				}
			});

			// 학습 시작일 검증
			form.sectDt.addEventListener('input', function() {
				if (form.sectEt?.value) {
					let sectDt = new Date(form.sectDt.value);
					let sectEt = new Date(form.sectEt.value);
					if (sectDt - sectEt > 0) {
						swal({
							title: "입력오류",
							text: "학습시작일이 학습종료일 이후에 올 수 없습니다",
							icon: "error",
							button: "확인"
						});
						form.sectDt.value = form.sectEt.value;
					}
				}
			});

			// 학습 종료일 검증
			form.sectEt?.addEventListener('input', function() {
				if (form.sectDt.value) {
					let sectDt = new Date(form.sectDt.value);
					let sectEt = new Date(form.sectEt.value);
					if (sectDt - sectEt > 0) {
						swal({
							title: "입력오류",
							text: "학습종료일이 학습시작일 이전에 올 수 없습니다",
							icon: "error",
							button: "확인"
						});
						form.sectEt.value = form.sectDt.value;
					}
				}
			});
		}
	});

	// 차시 추가 버튼 클릭
	document.querySelector('.data-insert-btn')?.addEventListener('click', function() {
		let formData = new FormData(insertDataForm);
		formData.set('sectDt', insertDataForm.sectDt.value.replaceAll('-', ''));
		if (insertDataForm.sectEt) {
			formData.set('sectEt', insertDataForm.sectEt.value.replaceAll('-', ''));
		} else {
			formData.set('sectEt', null);
		}

		let url = `${cp.value}/lecture/${lectNo.value}/materials/orderdata/online`;
		if (updateDataForm.sectEtime) {
			url = `${cp.value}/lecture/${lectNo.value}/materials/orderdata/offline`;
		}

		axios.post(url
			, formData)
			.then(resp => {
				swal({
					title: "추가완료",
					text: "차시가 성공적으로 추가되었습니다",
					icon: "success",
					button: "확인"
				}).then(() => location.reload());
			}).catch(err => {
				swal({
					title: "추가실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
			});
	});

	// 차시 수정 버튼 클릭
	$(document).on('click', '.data-update-btn', function() {
		let $orderLecture = $(this).parents('.orderLecture');
		getOrderWeek();
		axios.get(`${cp.value}/lecture/${lectNo.value}/materials/${$orderLecture.data("weekCd")}/${$orderLecture.find('.lectOrder').data('lectOrder')}`)
			.then(resp => {
				let order = resp.data.orderLectureDataVO;
				let updateDataForm = document.forms['updateDataForm'];
				updateDataForm.weekCd.value = order.weekCd;
				updateDataForm.weekCd.setAttribute('data-origin', order.weekCd);
				updateDataForm.lectOrder.value = order.lectOrder;
				updateDataForm.lectOrder.setAttribute('data-origin', order.lectOrder);
				updateDataForm.sectNm.value = order.sectNm;
				updateDataForm.sectDt.value = order.sectDt.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
				if (updateDataForm.sectEt) {
					updateDataForm.sectEt.value = order.sectEt.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
					$('#updateOutput').text(order.sectIdnty);
					updateDataForm.sectIdnty.value = order.sectIdnty;
					updateDataForm.uploadedFiles.value = order.atchFileDetailList[0]?.orignlFileNm ?? "등록된 강의영상이 없습니다";
				}
				if (updateDataForm.sectEtime) {
					updateDataForm.sectEtime.value = order.sectEtime;
					updateDataForm.croomCd.value = order.croomCd;
				}
				$('#updateDataModal').modal('show');
			});
	});

	// 차시 수정 저장 버튼 클릭
	document.querySelector(".data-update-submit-btn")?.addEventListener('click', function() {
		let form = document.forms['updateDataForm'];
		let formData = new FormData(form);
		formData.set('sectDt', form.sectDt.value.replaceAll('-', ''));
		if (updateDataForm.sectEt) {
			formData.set('sectEt', updateDataForm.sectEt.value.replaceAll('-', ''));
		} else {
			formData.set('sectEt', null);
		}

		axios.post(`${cp.value}/lecture/${lectNo.value}/materials/${form.weekCd.dataset["origin"]}/${form.lectOrder.dataset["origin"]}/edit`
			, formData)
			.then(resp => {
				swal({
					title: "수정완료",
					text: "차시가 성공적으로 추가되었습니다",
					icon: "success",
					button: "확인"
				}).then(() => location.reload());
			}).catch(err => {
				swal({
					title: "수정실패",
					text: err.response.data.message,
					icon: "error",
					button: "확인"
				});
			});
	});

	// 차시 삭제 버튼 클릭
	$(document).on('click', '.data-delete-btn', function() {
		let $tr = $(this).parents('tr');
		swal({
			title: "정말로 삭제하시겠습니까?",
			text: "차시를 삭제하면 다시 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal => {
			if (btnVal) {
				axios.delete(`${cp.value}/lecture/${lectNo.value}/materials/${$tr.data("weekCd")}/${$tr.data("lectOrder")}`)
					.then(resp => {
						swal({
							title: "삭제완료",
							text: "차시가 성공적으로 삭제되었습니다",
							icon: "success",
							button: "확인"
						});
						// 삭제 후 처리
						$(`.delWeek[data-lect-order="${$tr.data('lectOrder')}"]`).remove();
					}).catch(err => {
						swal({
							title: "삭제실패",
							text: err.response.data.message,
							icon: "error",
							button: "확인"
						});
					})
			}
		});
	});



	/* 공결 신청 등록 ---------------------------------------------------------------------------*/
	// 강의 출결 조회 중 공결 신청 등록버튼
	// 'atnd-insert-btn' 클래스를 가진 모든 버튼을 찾습니다.
	const buttons = document.querySelectorAll('.atnd-insert-btn');

	// 각 버튼에 클릭 이벤트 리스너를 추가합니다.
	buttons.forEach(button => {
		button.addEventListener('click', function() {
			// 버튼의 부모 <tr> 요소를 찾습니다.
			const tr = button.closest('tr');

			// <tr> 요소가 없으면 실행하지 않고 종료합니다.
			if (!tr) {
				console.warn('해당 버튼은 <tr> 요소에 속해 있지 않습니다.');
				return;
			}

			// <tr> 요소에서 data- 속성 값을 가져옵니다.
			const lectOrder = tr.dataset['lectOrder'];
			const atndStts = tr.dataset['atndStts'];
			const weekCd = tr.dataset['weekCd'];

			// 강의 차수 (lectOrder)와 상태 (atndStts) 데이터를 모달에 추가합니다.
			const modal = document.querySelector('#staticBackdrop');
			if (modal) {
				modal.querySelector('#lectOrder').textContent = lectOrder || '';
				modal.querySelector('#absenceStatus').textContent = atndStts || '';
				modal.querySelector('#weekCd').textContent = weekCd || '';
			} else {
				console.error('모달 요소를 찾을 수 없습니다.');
			}
		});
	});


	var contextPath = document.querySelector('#form-table').dataset['path'];
	var editorInstance;
	var updateInstance;
	const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
	const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
	// CKEditor 초기화
	ClassicEditor.create(document.querySelector('#editor'), {
		licenseKey: 'GPL',
		plugins: [
			Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
			List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar,
			FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
		],
		toolbar: [
			'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
			'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
			'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
		],
		language: 'ko',
		simpleUpload: {
			uploadUrl: `${contextPath}/imageUpload`,
			headers: csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {},
		}
	})
		.then(editor => {
			editor.editing.view.change(writer => {
				writer.setStyle('height', '400px', editor.editing.view.document.getRoot());
			});
			editorInstance = editor; // 전역으로 설정
		})
		.catch(error => {
			console.error("CKEditor 초기화 실패:", error);
		});



	// CKEditor 초기화
	ClassicEditor.create(document.querySelector('#updateEditor'), {
		licenseKey: 'GPL',
		plugins: [
			Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
			List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar,
			FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
		],
		toolbar: [
			'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
			'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
			'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
		],
		language: 'ko',
		simpleUpload: {
			uploadUrl: `${contextPath}/imageUpload`,
			headers: csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {},
		}
	})
		.then(editor => {
			editor.editing.view.change(writer => {
				writer.setStyle('height', '400px', editor.editing.view.document.getRoot());
			});
			updateInstance = editor; // 전역으로 설정
		})
		.catch(error => {
			console.error("CKEditor 초기화 실패:", error);
		});


	// CKEditor 초기화
	$('#staticBackdrop').on('hide.bs.modal', function() {
		if (editorInstance) {
			editorInstance.setData(''); // 초기화
		} else {
			console.warn("editorInstance가 없습니다. 초기화할 수 없습니다.");
		}
	});
	
	// 더미 버튼 눌럿을때 
	document.getElementById('dummyData').addEventListener('click', function() {
		editorInstance.setData("감기 증상과 고열로 인해 일시적으로 건강이 좋지 않아 학교에 출석하지 못함.");
	});

	// 버튼 클릭 시 데이터 수집 및 알림창 출력
	document.getElementById('approveButton').addEventListener('click', function() {
		console.log(editorInstance)
		if (editorInstance) {

			var absenceResn = editorInstance.getData();
			const lectOrder = document.querySelector('#lectOrder').textContent;
			const studentId = document.querySelector('#studentId').textContent;
			const weekCd = document.querySelector('#weekCd').textContent;
			const lectNo = document.querySelector('#lectNo').value;  // `lectNo` 요소에서 값 가져오기

			// 수집한 데이터를 VO 객체로 만들어서 전송
			const absenceData = {
				lectNo: lectNo,           // 강의번호
				lectOrder: lectOrder,     // 강의 차수
				stuId: studentId,     	// 학번
				weekCd: weekCd          // 주차 코드
			};

			let absenseForm = document.querySelector('#form-table');
			let formData = new FormData(absenseForm);

			for (let key in absenceData) {
				formData.append(key, absenceData[key]);
			}

			formData.set('absenceResn', absenceResn);

			// 콘솔에도 출력 (디버깅용)
			formData.forEach((v, k) => {
				console.log("디버깅용 fromData v, k : ", v, k)
			});
			
			let fileInput = formData.get('uploadFiles'); // 파일을 가져옴
			
			console.log("등록할때 파일 들어가는 것 :  ", fileInput)
			console.log("등록할때 업로드 되는 것 :  ", absenseForm)
			
			if (!formData.get('absenceResn') || formData.get('absenceResn').trim() === "") {
				swal({
	                title: '오류',
	                text: '내용을 입력해주세요.',
	                icon: 'warning',
	                confirmButtonText: '확인'
	            });
		        return;
		    }
			// 파일이 비어있는지 확인
			if (fileInput && fileInput.name.trim() === '') {
				swal({
	                title: '알림',
	                text: '첨부파일이 비어있습니다',
	                icon: 'warning',
	                confirmButtonText: '확인'
	            });
				return; // 파일 이름이 비어 있으면 데이터 전송을 막음
			}

			console.log("url 경로 표시 : ", `${contextPath}/lecture/${lectNo}/materials/new/absence`)

			fetch(`${contextPath}/lecture/${lectNo}/materials/new/absence`, {
				method: 'POST',
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					console.log('Success:', data);
					swal({
		                title: '성공',
		                text: '공결 사유가 정상적으로 등록되었습니다.',
		                icon: 'success',
		                confirmButtonText: '확인'
		            })
						.then(() => {
							// 알림창이 닫힌 후 페이지 새로고침
							window.location.reload();
						});

					// 모달 닫기
					$('#staticBackdropEdit').modal('hide');
				})
				.catch(error => {
					console.error('Error:', error);
					swal({
		                title: '오류',
		                text: '데이터 전송 중 문제가 발생했습니다. 다시 시도해주세요.',
		                icon: 'error',
		                confirmButtonText: '확인'
		            })
				});
		}
	});

	// 조회 버튼을 눌렀을때
	document.querySelectorAll('.editBtn').forEach(button => {
		button.addEventListener('click', function() {
			const absenceCd = this.getAttribute('data-absenceCd');
			if (!absenceCd) {
				console.error("absenceCd가 없습니다.");
				return;
			}

			// AJAX 요청으로 서버에 absenceCd를 보내고 데이터 받아오기
			fetch(`${contextPath}/lecture/${lectNo.value}/absence/${absenceCd}`)
				.then(response => response.json())
				.then(data => {
					// 서버로부터 받은 데이터를 모달에 업데이트
					document.getElementById('lectOrder2').innerText = data.lectOrder;
					document.getElementById('studentName2').innerText = data.studentVO.nm;
					document.getElementById('studentId2').innerText = data.stuId;
					document.getElementById('absenceStatus2').innerText = data.prst;
					document.getElementById('modal-return').innerText = data.absenceReturn;
					//document.getElementById('absenceCd2').value = absenceCd; 
					
					//const modalReturnValue = document.getElementById("modal-return").innerHTML;

					// modal-return이 null이거나 비어있으면 <thead> 숨기기
					if (!data.absenceReturn) {
					    document.getElementById("table-header").style.display = "none";
					}
										
					const fileDetails = data.atchFile.fileDetails
					
					console.log("data.cocoCd : ", data.cocoCd);
					console.log("조회 버튼눌렀을때 :", data)
					
					//atchFileId
					renderFileDetails(fileDetails)
					
					updateInstance.setData(data.absenceResn);
					
					document.getElementById('absenceCd2').value = absenceCd;
					console.log("수정에 들어간 데이터", data);
				})
				.catch(error => {
					console.error('Error:', error);
				});
		});
	});
	
	/*
	// 서버에서 받은 파일 상세 목록 데이터를 렌더링하는 함수
	function renderFileDetails2(fileDetails) {
	    const container = document.getElementById('fileDetailsContainer'); // 출력될 컨테이너
	    container.innerHTML = ''; // 기존 내용을 초기화
	
	    if (fileDetails && fileDetails.length > 0) {
	        fileDetails.forEach((fd, index) => {
	            const isLast = index === fileDetails.length - 1; // 마지막 항목인지 확인
	
	            // 파일 상세 정보를 추가
	            container.innerHTML += `
	                <span>
	                    ${fd.orignlFileNm}[${fd.fileFancysize}]
	                    <a data-atch-file-id="${fd.atchFileId}" 
	                       data-file-sn="${fd.fileSn}" 
	                       class="btn btn-danger file-delete-btn" 
	                       href="javascript:;">
	                        삭제
	                    </a>
	                    ${isLast ? '' : '|'}
	                </span>`;
	        });
	
	        // 삭제 버튼 이벤트 추가
	        document.querySelectorAll('.file-delete-btn').forEach(deleteBtn => {
	            deleteBtn.addEventListener('click', function () {
	                const atchFileId = this.getAttribute('data-atch-file-id');
	                const fileSn = this.getAttribute('data-file-sn');
	
	                // 삭제 요청 처리
	                fetch(`${contextPath}/deleteFile`, {
	                    method: 'POST',
	                    headers: {
	                        'Content-Type': 'application/json'
	                    },
	                    body: JSON.stringify({ atchFileId, fileSn })
	                })
	                    .then(response => response.json())
	                    .then(result => {
	                        if (result.success) {
	                            swal('파일이 삭제되었습니다.');
	                            this.closest('span').remove(); // 삭제된 파일의 UI 제거
	                        } else {
	                            swal('파일 삭제에 실패했습니다.');
	                        }
	                    })
	                    .catch(error => {
	                        console.error('Error:', error);
	                        swal('파일 삭제 중 오류가 발생했습니다.');
	                    });
	            });
	        });
	    } else {
	        container.innerHTML = '<p>첨부파일 없음</p>';
	    }
	}
	*/
	
	
	// 서버에서 받은 파일 상세 목록 데이터를 렌더링하는 함수
	function renderFileDetails(fileDetails) {
	    const container = document.getElementById('fileDetailsContainer'); // 출력될 컨테이너
	    container.innerHTML = ''; // 기존 내용을 초기화
	
	    if (fileDetails && fileDetails.length > 0) {
        fileDetails.forEach((fd, index) => {
            const isLast = index === fileDetails.length - 1; // 마지막 항목인지 확인
            const downUrl = `${contextPath}/absence/atch/${fd.atchFileId}/${fd.fileSn}`; // 다운로드 URL 생성

            // 파일 상세 정보를 추가
            container.innerHTML += `
                <span>
                    ${fd.orignlFileNm}[${fd.fileFancysize}]
                    <a href="${downUrl}" class="btn btn-info" target="_blank">다운로드</a> <!-- 다운로드 버튼 추가 -->
                    <a data-atch-file-id="${fd.atchFileId}" 
                       data-file-sn="${fd.fileSn}" 
                       class="btn btn-danger file-delete-btn" 
                       href="javascript:;">
                        삭제
                    </a>
                    ${isLast ? '' : '|'}
                </span>`;
        });

        // 삭제 버튼 이벤트 추가
        document.querySelectorAll('.file-delete-btn').forEach(deleteBtn => {
            deleteBtn.addEventListener('click', function () {
                const atchFileId = this.getAttribute('data-atch-file-id');
                const fileSn = this.getAttribute('data-file-sn');

                // 삭제 요청 처리
                fetch(`${contextPath}/atch/${atchFileId}/${fileSn}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ atchFileId, fileSn })
                })
                    .then(response => response.json())
                    .then(result => {
                        if (result.success) {
							swal({
				                title: '파일 삭제 완료',
				                text: '파일이 삭제되었습니다.',
				                icon: 'seccess',
				                confirmButtonText: '확인'
				            })
                            this.closest('span').remove(); // 삭제된 파일의 UI 제거
                        } else {
							swal({
				                title: '파일 삭제 실패',
				                text: '파일 삭제에 실패했습니다.',
				                icon: 'warning',
				                confirmButtonText: '확인'
				            });
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
						swal({
				            title: '파일 삭제 오류',
				            text: '파일 삭제 중 오류가 발생했습니다.',
				            icon: 'error',
				            confirmButtonText: '확인'
				        });
                    });
            });
        });
    } else {
        container.innerHTML = '<p>첨부파일 없음</p>';
    }
	}

	
	// 조회 후 - 수정 버튼을 눌렀을때 이벤트
	document.getElementById('saveEditButton').addEventListener('click', function () {
	    // Hidden input에서 absenceCd 값을 가져옵니다.
		console.log("absenceCd : ", document.getElementById('absenceCd2'));
		
	    const absenceCd = document.getElementById('absenceCd2').value;

	
		console.log("CK에디터 업데이트 하기 : ", updateInstance.getData());
	    // CKEditor에서 사유 데이터를 가져옵니다.
	    const absenceResn = updateInstance.getData();
	
	    // absenceCd와 수정할 데이터를 준비합니다.
	    const updateData = {
	        absenceCd: absenceCd,
	        absenceResn: absenceResn,
	    };
		
		let absenseForm = document.querySelector('#fileForm');
		let formData = new FormData(absenseForm);
	
		for (let key in updateData) {
			formData.append(key, updateData[key]);
		}
	
		formData.set('absenceResn', absenceResn);
		
		if (!formData.get('absenceResn') || formData.get('absenceResn').trim() === "") {
				swal({
				    title: '오류',
				    text: '내용을 입력해주세요.',
				    icon: 'warning',
				    confirmButtonText: '확인'
				});
		        return;
		    }
		
		// 콘솔에도 출력 (디버깅용)
		formData.forEach((v, k) => {
			console.log(v, k)
		});
	
		let fileInput = formData.get('uploadFiles'); // 파일을 가져옴
		
		console.log("새로들어온 파일목록 : ", fileInput);
	    console.log("보낼 데이터:", updateData);
		
	    // 서버로 PUT 요청을 보냅니다.
	    fetch(`${contextPath}/lecture/${lectNo.value}/absence/edit/reason`, {
	        method: 'POST',
	        body: formData
	    })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error("서버에서 오류 발생");
	            }
	            return response.json();
	        })
	        .then(data => {
	            console.log("수정 성공:", data);
				swal({
				    title: '수정 완료',
				    text: '공결 사유가 정상적으로 수정되었습니다.',
				    icon: 'success',
				    confirmButtonText: '확인'
				})
	                .then(() => {
	                    window.location.reload(); // 페이지 새로고침
	                });
	        })
	        .catch(error => {
	            console.error("수정 요청 중 오류 발생:", error);
				swal({
				    title: '오류 발생',
				    text: '수정 중 문제가 발생했습니다.',
				    icon: 'error',
				    confirmButtonText: '확인'
				});
	        });
	});


	// 삭제 버튼 눌렀을때 이벤트
	document.querySelectorAll('.absenceDeleteBtn').forEach(button => {
		button.addEventListener('click', function() {
			const absenceCd = document.getElementById('absenceCd2')?.value;

			if (!absenceCd) {
				swal({
				    title: '오류 발생',
				    text: '공결 처리 번호가 비어 있습니다.',
				    icon: 'error',
				    confirmButtonText: '확인'
				});
				return;
			}

			console.log('absenceCd to send:', absenceCd); // absenceCd 확인

			swal({
				title: "정말 삭제하시겠습니까?",
				text: "이 작업은 되돌릴 수 없습니다.",
				icon: "warning",
				buttons: {
			        confirm: {
			            text: "삭제",
			            value: true,
			            visible: true,
			            className: "",
			            closeModal: true,
			        },
			        cancel: {
			            text: "취소",
			            value: null,
			            visible: true,
			            className: "",
			            closeModal: true,
			        }
			    }
			}).then((willDelete) => {
				if (willDelete) {
					fetch(`${contextPath}/lecture/${lectNo.value}/absence/drop`, {
						method: 'PUT',
						headers: {
							'Content-Type': 'application/json',
						},
						body: JSON.stringify({ absenceCd }), // JSON 확인
					})
						.then(response => {
							console.log('Response status:', response.status);
							if (!response.ok) {
								throw new Error(`HTTP 오류 발생! 상태 코드: ${response.status}`);
							}
							return response.json();
						})
						.then(data => {
							console.log('Response data:', data);
							swal({
							    title: '삭제 완료',
							    text: '삭제되었습니다.',
							    icon: 'success',
							    confirmButtonText: '확인'
							})
								.then(() => window.location.reload());
						})
						.catch(error => {
							console.error("Fetch error:", error);
							swal({
							    title: '오류 발생',
							    text: '삭제 중 문제가 발생했습니다.',
							    icon: 'error',
							    confirmButtonText: '확인'
							})
						});
				}
			});
			document.querySelector(".swal-button--confirm").style.backgroundColor = "red";
			document.querySelector(".swal-button--cancel").style.color = "white";
		});
	});
});



