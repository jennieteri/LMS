/**
 * 
	ckeditor 사용 스크립트 
	1. form태그 안의 테이블에 id로 form-table주고, data-path="${pageContext.request.contextPath }" 를 줘야함
	- 안줄거면 simpleUpload: { 
					uploadUrl: `${contextPath}/imageUpload`
					, headers:  csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
					
			} 이 부분의 ${contextPath}를 수정해야함.
	2. 아래의 코드를 사용하는 JSP 파일 상단에 넣어줘야함. 
		<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/ckeditor5/ckeditor5.css">
		<script type="importmap">
		{
			"imports": {
							"ckeditor5": "${pageContext.request.contextPath }resources/js/ckeditor5/ckeditor5.js",
							"ckeditor5/": "${pageContext.request.contextPath }/resources/js/ckeditor5/translations/ko.js/"
			}
		}
		</script>
	
	3. editor의 import 부분의 상대경로는 from뒤에 .또는 /이 와야하기때문에 어쩔수없이 상대경로로 잡았음
		상대경로의 기준은 views/folder(개인작업폴더)/jsp(작업파일) 임. 만약 위치가 변경된다면 알맞게 위치 변경해야함.

 */
//절대경로를 잡기위해 data-path로 값을 준걸 가져옴.
var contextPath = document.querySelector('#form-table').dataset['path'];

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
            writer.setStyle('height', '280px', editor.editing.view.document.getRoot());
        });
        editorInstance = editor;
    })
    .catch(error => {
        console.error(error);

	})//create에디터 끝
	
//데이터 처리를 한다면 이 밑으로 document.addEventListener("DOMContentLoaded",()=>{}) 등 처리하면 됨!	
	
	