document.addEventListener("DOMContentLoaded", () => {
	const cp = document.querySelector("#cp").value;
    const delBtn = document.querySelector("#deleteBtn");
    const applyBtn = document.querySelector("#applyBtn");
	const cancelBtn = document.querySelector('#cancelBtn');

    if (delBtn) {
        delBtn.addEventListener("click", (e) => {
            e.preventDefault();
            console.log("삭제 버튼 클릭됨");

            const updateData = {
                jobNo: document.getElementById("detail-id").value
            };
			console.log(updateData);
            swal({
                title: "정말로 삭제하시겠습니까?",
                icon: "warning",
                buttons: ["취소", "삭제"],
                dangerMode: true,
            }).then((btnVal) => {
                if (btnVal) {
					console.log(cp+`/jobboard/${updateData.jobNo}/delete`);
                    axios.put(cp+`/jobboard/${updateData.jobNo}/delete`, updateData)
                        .then(() => {
                            swal({
                                title: "삭제 완료",
                                text: "게시글이 성공적으로 삭제되었습니다.",
                                icon: "success",
                                button: "확인"
                            }).then(() => {
                                location.href = cp+'/jobboard';
                            });
                        })
                        .catch((err) => {
                            swal({
                                title: "삭제 실패",
                                text: err.response?.data?.message || "오류가 발생했습니다.",
                                icon: "error",
                                button: "확인"
                            });
                        });
                }
            });
        });
    } else {
        console.log("#deleteBtn 버튼이 학생 권한에서는 표시되지 않습니다.");
    }

    if (applyBtn) {
        applyBtn.addEventListener("click", () => {
            console.log("신청 버튼 클릭됨");
            const updateData = {
                jobNo: document.getElementById("apply-jobno").textContent,
                stuId: document.getElementById("apply-id").textContent
            };

            axios.post(cp+`/eventregistrant`, updateData)
                .then(() => {
                    swal({
                        title: "신청 완료",
                        text: "채용설명회가 성공적으로 신청되었습니다.",
                        icon: "success",
                        button: "확인"
                    }).then(() => {
                        location.href = cp+`/jobboard/${updateData.jobNo}`;
                    });
                })
                .catch((err) => {
                    if (err.response && err.response.status === 409) {
                        swal({
                            title: "신청 실패",
                            text: "이미 신청하셨습니다.",
                            icon: "warning",
                            button: "확인"
                        });
                    } else {
                        swal({
                            title: "신청 실패",
                            text: "오류가 발생했습니다. 다시 시도해주세요.",
                            icon: "error",
                            button: "확인"
                        });
                    }
                });
        });
    } else {
        console.log("#applyBtn 버튼이 없는 상태입니다.");
    }

    if (cancelBtn) {
        cancelBtn.addEventListener("click", (e) => {
            e.preventDefault();
            console.log("cancelBtn 버튼 클릭됨");

           const updateData = {
                jobNo: document.getElementById("apply-jobno").textContent,
                stuId: document.getElementById("apply-id").textContent
            };
			console.log(updateData);
            swal({
                title: "정말로 취소 하시겠습니까?",
                icon: "warning",
                buttons: ["취소", "확인"],
                dangerMode: true,
            }).then((btnVal) => {
                if (btnVal) {
					console.log(cp+`/eventregistrant`);
                    axios.delete(cp+`/eventregistrant`, {
						data : updateData
					})
                        .then(() => {
                            swal({
                                title: "취소 완료",
                                text: "신청이 성공적으로 취소되었습니다.",
                                icon: "success",
                                button: "확인"
                            }).then(() => {
                                location.href = cp+'/jobboard';
                            });
                        })
                        .catch((err) => {
                            swal({
                                title: "삭제 실패",
                                text: err.response?.data?.message || "오류가 발생했습니다.",
                                icon: "error",
                                button: "확인"
                            });
                        });
                }
            });
        });
    };
});
