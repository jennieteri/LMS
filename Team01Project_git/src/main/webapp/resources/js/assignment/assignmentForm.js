/**
 * 생성 수정 비동기 처리
 */

/**
 * 입력폼 js
 */

var contextPath = document.querySelector('#form-table').dataset['path'];

var editorInstance;
	
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
	
	const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
	const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
	
	ClassicEditor.create( document.querySelector('#editor'),{
		licenseKey: 'GPL'
		,  plugins: [
            Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
            List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar, 
            FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
        ],
        toolbar: [
            'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
            'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
            'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
        ],language: 'ko' // 한국어 설정
		, simpleUpload: { 
					uploadUrl: `${contextPath}/imageUpload`
					, headers:  csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
					
			}
	})
	
	 .then(editor => {
		editor.editing.view.change(writer=>{
            writer.setStyle('height', '280px', editor.editing.view.document.getRoot());
        });
        editorInstance = editor;
    })
    .catch(error => {
        console.error(error);

	})//create에디터 끝


document.addEventListener("DOMContentLoaded",()=>{
	
	const formTable = document.getElementById("form-table");
	const createForm = document.getElementById("createForm");
	const editForm = document.querySelector('#editForm');
	const lectNo = formTable.dataset.lectNo;
	const dataBtn = document.getElementById("dataBtn");
	
	dataBtn.addEventListener("click", () => {

        document.querySelector("input[name='assigNm']").value = "컴퓨터 네트워크 이해와 응용(프로젝트발표용)";
        document.querySelector("input[name='assigScore']").value = 25;
        document.querySelector("input[name='assigEd']").value = "2025-01-31";
        document.querySelector("textarea[name='assigNotes']").value = 

		"이번 과제는 컴퓨터 네트워크의 기본 개념을 이해하고 실제 사례를 분석하는 과제입니다. \n" +
                "제출 시 다음 요구 사항을 충족해야 합니다:\n" +
                "1. IP 주소 체계에 대한 설명\n" +
                "2. 라우팅 프로토콜 비교\n" +
                "3. 네트워크 보안 사례 연구"; 
        
        // 피어리뷰 여부 라디오 버튼 선택
        document.querySelector("input[name='peerYn'][value='Y']").checked = true;

    });
	
	//첨부파일 삭제
	document.querySelectorAll("[data-atch-file-id][data-file-sn]").forEach(el=>{
    	el.addEventListener("click", async (e)=>{
    		e.preventDefault();
    		let atchFileId = el.dataset.atchFileId;
    		let fileSn = el.dataset.fileSn;
			let resp = await fetch(`${contextPath}/atch/${atchFileId}/${fileSn}`, {
    			method:"delete"
    			, headers:{
    				"accept":"application/json"
    			}
    		});
    		if(resp.ok){
    			let obj = await resp.json();
    			if(obj.success){
					el.parentElement.remove();    				
    			}
    		}
    	});
    });
	
	createForm?.addEventListener("submit", async (e)=>{
		e.preventDefault();
		
		const formData = new FormData(createForm);
		
		try{
			const response = await fetch(createForm.action, {
                method: createForm.method,
                body: formData,
            });

            if (response.ok) {
				const success = await response.json();
				
                swal("성공", `${success.message}`|| "과제가 성공적으로 저장되었습니다.", "success").then(() => {
                    window.location.href = `${contextPath}/lecture/${lectNo}/assignment`;
                });
            } else {
                const error = await response.json();
                swal("오류", `저장 실패: ${error.message}`, "error");
            }
        } catch (error) {
            console.error("폼 제출 오류:", error);
            swal("오류", "저장 중 문제가 발생했습니다.", "error");
        }
		
	});//생성폼처리
	
	editForm?.addEventListener("submit", async (e)=>{
		e.preventDefault();
		const formData = new FormData(editForm);
		
		try{
			const response = await fetch(editForm.action, {
                method: editForm.method,
                body: formData,
            });

            if (response.ok) {
				const success = await response.json();
				
                swal("성공", `${success.message}`|| "과제가 성공적으로 수정되었습니다.", "success").then(() => {
                    window.location.href = `${contextPath}/lecture/${lectNo}/assignment`;
                });
            } else {
                const error = await response.json();
                swal("오류", `수정 실패: ${error.message}`, "error");
            }
        } catch (error) {
            console.error("폼 제출 오류:", error);
            swal("오류", "저장 중 문제가 발생했습니다.", "error");
        }
		
	});//수정폼처리
	
	
	
	
	
});




