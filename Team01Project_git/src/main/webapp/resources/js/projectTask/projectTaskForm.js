/**
 * 
 */

var contextPath = document.querySelector('#form-table').dataset['path'];
var lectNo = document.querySelector('#form-table').dataset['lectno'];
//전역변수로 editorInstance 선언 나중에 editorInstance의 메소드 사용할때 필요
var editorInstance;
	//필요한 자원 import하는 구문
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
	
	//이미지 업로드 처리할때 보안때문에 html에서 제공하는 token 사용
	const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
	//만약 meta 설정이 없는 경우 null로 초기화 있다면, token값 가져옴
	const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
	
	//Editor의 종류중 사용할 Editor 생성 ClassicEditor권장 다른 종류 사용하고싶다면, 문서확인 후 사용
	ClassicEditor.create( document.querySelector('#editor'),{
		licenseKey: 'GPL' //5버전 기본 라이센스키 ; 필수값
		//사용할 플러그인 플러그인종류는 문서확인
		,  plugins: [
            Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
            List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar, 
            FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
        ],
		// 툴바 메뉴
        toolbar: [
            'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
            'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
            'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
        ]
		,language: 'ko' // 한국어 설정
		//이미지 업로드 어댑터 config설정
		//이미지 url은 noticeboard폴더에 해당 처리 controller를 만들어두었음.
		//컨트롤러에서 반환값은 url임으로 반환값이 어떻게 오는지 알아야 데이터를 처리할 수 있음.
		, simpleUpload: { 
					uploadUrl: `${contextPath}/imageUpload`
					, headers:  csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
					
			}
	})
	//promise객체가 반환됨.
	 .then(editor => {
		editor.editing.view.change(writer=>{
            writer.setStyle('height', '350px', editor.editing.view.document.getRoot());
        });
        editorInstance = editor;
		
    })
    .catch(error => {
        console.error(error);

	})//create에디터 끝
	
document.addEventListener("DOMContentLoaded",()=>{
	const createForm = document.querySelector('#createForm');
	const dataBtn = document.querySelector('#dataBtn');
	
	dataBtn.addEventListener("click", () => {

        // 폼 필드 값 설정
        document.querySelector("input[name='taskTitle']").value = "AI 기반 추천 시스템 설계(프로젝트발표용)"; // 과제 주제
        document.querySelector("input[name='taskEt']").value = "2025-02-15"; // 제출 마감일
        document.querySelector("input[name='taskScore']").value = 50; // 배점
        document.querySelector("textarea[name='taskNotes']").value = 
            "프로젝트 설명:\n" +
            "- 본 과제는 AI 기반 추천 시스템을 설계하고, 이를 구현하는 프로젝트입니다.\n" +
            "- 팀 단위로 진행되며, 다음 요구 사항을 충족해야 합니다:\n" +
            "    1. 데이터 수집 및 전처리 과정 포함\n" +
            "    2. 추천 알고리즘 설계 (협업 필터링 또는 콘텐츠 기반 필터링)\n" +
            "    3. 결과 평가 및 보고서 작성\n\n" +
            "제출물:\n" +
            "- 프로젝트 소스 코드\n" +
            "- 프로젝트 보고서 (PDF)";

    });
	
	
	createForm.addEventListener("submit",async (e)=>{
		e.preventDefault();

		const formData = new FormData(createForm);
		try{
			let resp = await fetch(`${contextPath}/lecture/${lectNo}/projectTask`,{
				method: 'POST'
				,body: formData
			});
			if(resp.ok){
				const result = await resp.json();
				swal("성공",result.message || "프로젝트가 생성되었습니다!","success").then(()=>{
					window.location.href=`${contextPath}/lecture/${lectNo}/projectTask`;					
				})
			}else{
				const error = await resp.json();
				swal("실패", error.message || "프로젝트 생성 중 문제가 발생했습니다.", "error");
			}
		}catch(e){
			swal("실패", "서버와의 통신에 문제가 발생했습니다.", "error");
		}	
		
		
	});//form 이벤트 끝
});//DOM 	
	
	
	
	




