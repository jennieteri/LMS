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
document.addEventListener('DOMContentLoaded', function() {
	var contextPath = cp.value;
	const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
	const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
	var editorInstance;
	const editor = document.querySelector('#editor');
	ClassicEditor.create(editor, {
		licenseKey: 'GPL'
		, plugins: [
			Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
			List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar,
			FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
		],
		toolbar: [
			'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
			'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
			'fontSize', 'fontFamily', 'fontColor'
		]
		, language: 'ko'
		, simpleUpload: {
			uploadUrl: `${contextPath}/imageUpload`
			, headers: csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
		}
	})
	.then(editor => {
		editor.editing.view.change(writer=>{
            writer.setStyle('height', '200px', editor.editing.view.document.getRoot());
        });
		editorInstance = editor;
	})
	.catch(error => {
		console.error(error);
	});
	
	var libEditorInstance;
	const libEditor = document.querySelector('#libEditor');
	ClassicEditor.create(libEditor, {
		licenseKey: 'GPL'
		, plugins: [
			Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
			List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar,
			FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
		],
		toolbar: [
			'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
			'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
			'fontSize', 'fontFamily', 'fontColor'
		]
		, language: 'ko'
		, simpleUpload: {
			uploadUrl: `${contextPath}/imageUpload`
			, headers: csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
		}
	})
	.then(editor => {
		editor.editing.view.change(writer=>{
            writer.setStyle('height', '200px', editor.editing.view.document.getRoot());
        });
		libEditorInstance = editor;
	})
	.catch(error => {
		console.error(error);
	});
	
	const ansInsertBtn = document.querySelector(".ansInsertBtn");
const ansInsertDiv = document.getElementById("ansInsertDiv");

if (ansInsertBtn) {
    ansInsertBtn.addEventListener("click", function () {
        ansInsertDiv.style.display = "block"; // 답변 작성 영역 표시
    });
} else {
    console.error("답변 등록 버튼이 HTML에 존재하지 않습니다.");
}

	
	const $ansInsertDiv = $('#ansInsertDiv');
	
	$('.ansInsertBtn').on('click', function(){
		$('.ansZone').hide();
		$ansInsertDiv.find('.textarea').append(editor);
		$ansInsertDiv.show();
	});
	
	$('.ansCancelBtn').on('click', function(){
		$ansInsertDiv.hide();
		editorInstance.setData("");
		$('.ansZone').show();
	});
	
	$('.ansUpdateBtn').on('click', function(){
		$('.dispAnswer').hide();
		$('#delUpBtn').hide();
		editorInstance.setData($('.dispAnswer').html());
		$('.textarea').show();
		$('#saveCancelBtn').show();
	});
	
	$('.cancleBtn').on('click', function(){
		$('.textarea').hide();
		$('#saveCancelBtn').hide();
		$('.dispAnswer').show();
		$('#delUpBtn').show();
	});
	
	const sendAnswer = function(){
		axios.post(`${contextPath}/lecture/${lectNo.value}/inquiry/${libNo.value}/answer`
			,{
				libAnsCn: editorInstance.getData()
				, profeId: id.value
			})
		.then(resp=>{
			location.reload();
		})
		.catch(err=>{
			swal({
				title: "등록실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	};
	
	$('.ansSaveBtn').on('click', sendAnswer);
	
	$('.ansDeleteBtn').on('click', function(){
		swal({
			title: "정말로 삭제하시겠습니까?",
			text: "답변을 삭제하면 다시 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.delete(`${contextPath}/lecture/${lectNo.value}/inquiry/${libNo.value}/answer`)
				.then(resp=>{
					location.reload();
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
	});
	
	$('.libUpdateBtn').on('click', function(){
		$('.libSjText').hide();
		$('.libCnText').hide();
		$('#libDelUpBtn').hide();
		libSj.value = $('.libSjText').text(); 
		libEditorInstance.setData($('.libCnText').html());
		$('.libSjInput').show();
		$('.libCnInput').show();
		$('#libSaveCancelBtn').show();
	});
	
	$('.libCancleBtn').on('click', function(){
		$('.libSjInput').hide();
		$('.libCnInput').hide();
		$('#libSaveCancelBtn').hide();
		$('.libSjText').show();
		$('.libCnText').show();
		$('#libDelUpBtn').show();
	});
	
	$('.libSaveBtn').on('click', function(){
		axios.put(`${contextPath}/lecture/${lectNo.value}/inquiry/${libNo.value}/edit`
			, {libSj: libSj.value, libCn: libEditorInstance.getData()})
		.then(resp=>{
			location.reload();
		})
		.catch(err=>{
			swal({
				title: "수정실패",
				text: err.response.data.message,
				icon: "error",
				button: "확인"
			});
		});
	});
	
	$('.libDeleteBtn').on('click', function(){
		swal({
			title: "정말로 삭제하시겠습니까?",
			text: "문의을 삭제하면 다시 복구할 수 없습니다",
			icon: "warning",
			buttons: ["취소", "삭제"],
			dangerMode: true,
		}).then(btnVal=>{
			if(btnVal){
				axios.delete(`${contextPath}/lecture/${lectNo.value}/inquiry/${libNo.value}`)
				.then(resp=>{
					location.href = `${contextPath}/lecture/${lectNo.value}/inquiry`;
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
	});
});