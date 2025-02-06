/**
 * 
 */
let fnDelLecture;
let fnConsentLecture;
let fnReturnLecture;
let fnAbolitionLecture;
let fnConsentAbolition;
let fnReturnAbolition;
let fnAbolitionCancel;
document.addEventListener('DOMContentLoaded', function(){
	fnDelLecture = function(lectNo){
		swal({
			title: "정말로 삭제하시겠습니까?",
			text: "강의를 삭제하면 다시 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.delete(`${cp.value}/lecture/request/${lectNo}`)
				.then(resp=>{
					swal({
						title: "삭제완료",
						text: "강의가 성공적으로 삭제되었습니다",
						icon: "success",
						button: "확인"
					}).then(()=>location.href=`${cp.value}/lecture`)
				}).catch(err=>{
					swal({
						title: "삭제실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	};
	
	fnConsentLecture = function(lectNo){
		axios.put(`${cp.value}/lecture/request/${lectNo}/consent`)
		.then(resp=>{
			swal({
				title: "승인완료",
				text: "강의상태가 등록으로 변경되었습니다",
				icon: "success",
				button: "확인"
			}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
		}).catch(err=>{
			swal({
				title: "승인실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	fnReturnLecture = function(lectNo){
		axios.put(`${cp.value}/lecture/request/${lectNo}/return`
			, {
				lectNo: lectNo
				, lectReturn: lectReturn.value
			}
		)
		.then(resp=>{
			swal({
				title: "반려완료",
				text: "강의상태가 등록반려로 변경되었습니다",
				icon: "success",
				button: "확인"
			}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
		}).catch(err=>{
			swal({
				title: "반려실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	let $abolitionSelect = $('#abolitionSelect');
	let $abolitionText = $('#abolitionText');
	$abolitionSelect.on('change', function(){
		if($abolitionSelect.val() == 'self'){
			$abolitionText.prop('disabled', false);
			$abolitionText.val('');
		}else{
			$abolitionText.prop('disabled', true);
			$abolitionText.val($abolitionSelect.val());
		}
	});
	
	fnAbolitionLecture = function(lectNo){
		axios.put(`${cp.value}/lecture/${lectNo}/abolition`
			, {
				lectNo: lectNo
				, lectAbl: $abolitionText.val()
			}
		)
		.then(resp=>{
			swal({
				title: "폐강신청완료",
				text: "강의상태가 폐강대기로 변경되었습니다",
				icon: "success",
				button: "확인"
			}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
		}).catch(err=>{
			swal({
				title: "폐강신청실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	fnConsentAbolition = function(lectNo){
		swal({
			title: "정말로 폐강시키겠습니까?",
			text: "강의를 폐강시키면 다시 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "폐강"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.put(`${cp.value}/lecture/${lectNo}/abolition/consent`)
				.then(resp=>{
					swal({
						title: "폐강승인완료",
						text: "강의상태가 폐강으로 변경되었습니다",
						icon: "success",
						button: "확인"
					}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
				}).catch(err=>{
					swal({
						title: "폐강승인실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	};
	
	fnReturnAbolition = function(lectNo){
		axios.put(`${cp.value}/lecture/${lectNo}/abolition/return`)
		.then(resp=>{
			swal({
				title: "폐강반려완료",
				text: "강의상태가 등록으로 변경되었습니다",
				icon: "success",
				button: "확인"
			}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
		}).catch(err=>{
			swal({
				title: "폐강반려실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	}
	
	fnAbolitionCancel = function(lectNo){
		swal({
			title: "정말로 취소하시겠습니까?",
			icon: "warning",
			buttons: ["취소", "확인"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.delete(`${cp.value}/lecture/${lectNo}/abolition`)
				.then(resp=>{
					swal({
						title: "폐강신청취소완료",
						text: "강의상태가 등록으로 변경되었습니다",
						icon: "success",
						button: "확인"
					}).then(()=>location.href=`${cp.value}/lecture?semstrNo=${semstrNo.value}`);
				}).catch(err=>{
					swal({
						title: "폐강신청취소실패",
						text: err.response.data.message,
						icon: "error",
						button: "확인"
					});
				});
			}
		});
	}
});