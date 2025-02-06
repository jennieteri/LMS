document.addEventListener("DOMContentLoaded", function () {
    const contextPath = document.getElementById("cp").value; // 컨텍스트 경로
    const sibNo = document.getElementById("sibNo").value; // 게시글 번호
    const ansInsertDiv = document.getElementById("ansInsertDiv"); // 답변 작성 영역
    const ansZone = document.querySelector(".ansZone"); // "등록된 답변이 없습니다." 영역
    const ansInsertBtn = document.querySelector(".ansInsertBtn"); // 등록 버튼
    const ansCancelBtn = document.querySelector(".ansCancelBtn"); // 취소 버튼
    const ansSaveBtn = document.querySelector(".ansSaveBtn"); // 저장 버튼
    const ansDeleteBtn = document.querySelector(".ansDeleteBtn"); // 답변 삭제 버튼
    const editor = document.getElementById("editor");
    const deleteButton = document.querySelector('.deleteBtn'); // 게시글 삭제 버튼
    const deleteForm = document.getElementById('deleteForm'); // 게시글 삭제 폼

    // 등록 버튼 클릭 시 작성 영역 표시
    ansInsertBtn?.addEventListener("click", function () {
        ansZone.style.display = "none";
        ansInsertDiv.style.display = "block";
    });

    // 취소 버튼 클릭 시 작성 영역 숨기기
    ansCancelBtn?.addEventListener("click", function () {
        ansInsertDiv.style.display = "none";
        ansZone.style.display = "block";
        editor.value = ""; // 내용 초기화
    });

    ansSaveBtn?.addEventListener("click", function () {
        const answerContent = editor.value.trim();
        if (!answerContent) {
            swal('등록 실패', "내용을 입력하세요.", 'warning');
            return;
        }

        // 서버에 답변 저장 요청
        axios.post(`${contextPath}/answer/registerReply`, {
            sibNo: sibNo,
            sibAns: answerContent
        })
        .then(response => {
            if (response.data === "success") {
                swal("등록 성공", "답변이 등록되었습니다.", "success")
                    .then(() => location.reload()); // 페이지 새로고침
            } else {
                swal("등록 실패", "답변 등록에 실패하셨습니다.", "warning");
            }
        })
        .catch(error => {
            console.error(error);
            swal("서버 오류", "서버와 통신 중 오류가 발생했습니다.", "warning");
        });
    });

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
    
    $('.ansUpdateBtn').on('click', function () {
        const existingAnswer = $('.dispAnswer').text().trim(); // 기존 답변 내용 가져오기
        editor.value = existingAnswer; // 에디터에 답변 내용 삽입

        // textarea 크기 설정
        const textarea = document.querySelector('.textarea textarea');
        textarea.style.height = "200px"; // 등록할 때와 동일한 높이
        textarea.style.width = "100%";  // 부모 컨테이너와 동일한 너비

        $('.dispAnswer').hide(); // 기존 답변 숨김
        $('.textarea').show(); // 에디터 표시
        $('#delUpBtn').hide(); // 수정/삭제 버튼 숨김
        $('#saveCancelBtn').show(); // 저장/취소 버튼 표시
    });

    $('.cancleBtn').on('click', function(){
        $('.textarea').hide();
        $('#saveCancelBtn').hide();
        $('.dispAnswer').show();
        $('#delUpBtn').show();
    });

    if (deleteButton && deleteForm) {
        deleteButton.addEventListener('click', function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            swal({
                title: "게시글을 삭제하시겠습니까?",
                text: "삭제하면 복구할 수 없습니다.",
                icon: "warning",
                buttons: ["취소", "삭제"],
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    deleteForm.submit(); // 폼 제출
                }
            });
        });
    }

    $('.ansDeleteBtn').on('click', function () {
        const sibNo = document.getElementById("sibNo").value;

        if (!sibNo) {
            console.error("SIB_NO가 정의되지 않았습니다.");
            swal("삭제 실패", "답변을 삭제하는 것을 실패하셨습니다.", "error");
            return;
        }

        swal({
            title: "답변을 삭제하시겠습니까?",
            text: "답변을 삭제하면 복구할 수 없습니다.",
            icon: "warning",
            buttons: ["취소", "삭제"],
            dangerMode: true,
        }).then((btnVal) => {
            if (btnVal) {
                axios.post(`${contextPath}/answer/deleteReply/${sibNo}`)
                    .then((resp) => {
                        swal("삭제 완료!", "삭제가 완료되었습니다.", "success").then(() => {
                            location.reload(); // 페이지 새로고침
                        });
                    })
                    .catch((err) => {
                        swal("오류", err.response?.data?.message || "오류가 발생했습니다.", "error");
                    });
            }
        });
    });
});
