document.addEventListener('DOMContentLoaded', function () {
    const deleteButton = document.getElementById('deleteButton'); // 삭제 버튼 가져오기
    const deleteForm = document.getElementById('deleteForm'); // 삭제 form 가져오기

    deleteButton.addEventListener('click', function () {
        swal({
            title: "삭제 확인",
            text: "정말로 이 신청서를 삭제하시겠습니까?",
            icon: "warning",
            buttons: ["취소", "삭제"],
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                deleteForm.submit(); // 사용자가 확인을 누르면 form 제출
            }
        });
    });
});
