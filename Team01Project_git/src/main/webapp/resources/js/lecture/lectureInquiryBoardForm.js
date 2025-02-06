document.addEventListener('DOMContentLoaded', function() {
	$('#insertForm').on('submit', function(e){
		e.preventDefault();
		let form = document.forms['insertForm'];
		let data = {
			libSj: form.libSj.value
			, libCn: editorInstance[0].getData()
			, stuId: form.stuId.value};
		console.log(data)
		axios.post(`${cp.value}/lecture/${lectNo.value}/inquiry/new`
			, data)
		.then(({data})=>{
			location.href = `${cp.value}/lecture/${lectNo.value}/inquiry/${data.libNo}`
		}).catch(err=>{
			swal({
				title: "등록실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	});
});