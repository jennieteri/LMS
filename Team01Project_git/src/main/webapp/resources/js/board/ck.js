
    document.addEventListener("DOMContentLoaded", () => {
        // form-table의 data-path로부터 컨텍스트 경로 가져오기
        const contextPath = document.querySelector('#form-table').dataset['path'];

        // CKEditor 초기화
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

        // CSRF 토큰 설정 (필요한 경우)
        const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
        const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;

        // CKEditor 인스턴스 생성
        ClassicEditor.create(document.querySelector('#editor'), {
            licenseKey: 'GPL', // 필수 라이선스 키
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
                headers: csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
            }
        }).then(editor => {
            // 에디터 높이 설정
            editor.editing.view.change(writer => {
                writer.setStyle('height', '280px', editor.editing.view.document.getRoot());
            });
        }).catch(error => {
            console.error(error);
        });
    });

