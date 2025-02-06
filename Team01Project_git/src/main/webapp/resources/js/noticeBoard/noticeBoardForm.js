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
	const dataBtn = document.querySelector('#dataBtn');
	
	 // "발표" 버튼 클릭 이벤트 설정
    dataBtn.addEventListener("click", () => {
        document.querySelector("input[name='ntcNm']").value = "2025학년도 1학기 정규등록 안내"; // 제목
        document.querySelector("input[name='ntcDt']").value = "2025-02-01"; // 일정 시작일
        document.querySelector("input[name='ntcEt']").value = "2025-02-10"; // 일정 종료일
        document.querySelector("input[name='ntcYn'][value='Y']").checked = true; // 주요일정 여부
        document.querySelector("select[name='cocoCd']").value = "NB01"; // 주요일정 분류
        document.querySelector("select[name='semstrNo']").value = "202501"; // 학기 분류
        document.querySelector("textarea[name='ntcDesc']").value = 
            "정규등록 기간 안내:\n" +
            "- 등록기간: 2025년 2월 1일 ~ 2월 10일\n" +
            "- 등록방법: 학사정보시스템을 통해 등록\n" +
            "- 유의사항: 등록금 미납 시 수강 신청이 제한될 수 있습니다."; // 내용

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
				
                swal("성공", `${success.message}`|| "일정이 성공적으로 저장되었습니다.", "success").then(() => {
                    window.location.href = `${contextPath}/noticeboard`;
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
		console.log("수정폼==========================>");
		const formData = new FormData(editForm);
		
		try{
			const response = await fetch(editForm.action, {
                method: editForm.method,
                body: formData,
            });

            if (response.ok) {
				const success = await response.json();
				
                swal("성공", `${success.message}`|| "일정이 성공적으로 수정되었습니다.", "success").then(() => {
                    window.location.href = `${contextPath}/noticeboard`;
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





