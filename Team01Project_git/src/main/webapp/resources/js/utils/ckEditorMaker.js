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
	var contextPath = postScriptCP.value;
	var editorList = document.querySelectorAll('.ckEditor');
	window.editorInstance = {};
	editorList.forEach((e,i)=>{
		const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
		const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
		ClassicEditor.create(e, {
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
	            writer.setStyle('height', e.dataset.height ?? '400px', editor.editing.view.document.getRoot());
	        });
			editorInstance[e.dataset.instance ?? i] = editor;
		})
		.catch(error => {
			console.error(error);
		});
	});
});