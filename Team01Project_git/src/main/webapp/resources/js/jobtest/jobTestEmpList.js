const cp = document.getElementById("cp").value;
console.log("Context Path:", cp);

document.addEventListener("DOMContentLoaded", () => {
    const hardcodedOptions = [
        { value: "JT01", label: "진취형" },
        { value: "JT02", label: "탐구형" },
        { value: "JT03", label: "예술형" },
        { value: "JT04", label: "사회형" },
        { value: "JT05", label: "현장형" },
        { value: "JT06", label: "관리형" },
        { value: "JT07", label: "관습형" },
    ];

    // 수정 버튼 클릭 시
    $(document).on('click', '.edit-btn', function () {
        console.log("Edit button clicked.");
        const $tr = $(this).closest('tr');
        $tr.find('.edit-btn, .delete-btn').hide(); // 수정/삭제 버튼 숨기기
        $tr.find('.save-btn, .cancel-btn').show(); // 저장/취소 버튼 표시

        $tr.find('[data-key]').each(function () {
            const $td = $(this);
            const key = $td.data('key');
            const text = $td.text().trim();
            console.log(`Key: ${key}, Text: ${text}`);

			//진취,사회는 select박스
            if (key === 'cocoStts') {
                const selectBox = createSelectBox(text, hardcodedOptions);
                $td.html(selectBox);
            } else {
                $td.html(`<input type="text" class="form-control" value="${text}" data-origin="${text}">`);
            }
        });
    });

    // 취소 버튼 클릭 시
    $(document).on('click', '.cancel-btn', function () {
        console.log("Cancel button clicked.");
        const $tr = $(this).closest('tr');
        $tr.find('.edit-btn, .delete-btn').show(); // 수정/삭제 버튼 표시
        $tr.find('.save-btn, .cancel-btn').hide(); // 저장/취소 버튼 숨기기

		//복원
        $tr.find('[data-key]').each(function () {
            const $td = $(this);
            const originText = $td.find('input, select').data('origin');
            console.log(`Restoring original text: ${originText}`);
            $td.text(originText);
        });
    });

    // 수정 저장 버튼 클릭 시
    $(document).on('click', '.save-btn', function () {
        console.log("Save button clicked.");
        const $tr = $(this).closest('tr');
        const jobTestNo = $tr.data('job-test-no');
        console.log(`JobTestNo: ${jobTestNo}`);
        const updatedData = { jobTestNo };

        $tr.find('[data-key]').each(function () {
            const $td = $(this);
            const key = $td.data('key');
            if (key === 'cocoStts') {
                updatedData["jobTestType"] = $td.find('select').val();
            } else {
                updatedData[key] = $td.find('input').val().trim();
            }
        });

        console.log("Updated Data to send:", updatedData);



		//업데이트
        axios.put(`${cp}/jobtest/edit/${jobTestNo}`, updatedData)
            .then(response => {
                console.log("Response from server:", response.data);
                swal("수정 완료", "데이터가 성공적으로 수정되었습니다.", "success");

                $tr.find('.edit-btn, .delete-btn').show(); // 수정/삭제 버튼 표시
                $tr.find('.save-btn, .cancel-btn').hide(); // 저장/취소 버튼 숨기기

                $tr.find('[data-key]').each(function () {
                    const $td = $(this);
                    const key = $td.data('key');
                    if (key === 'cocoStts') {
                        const selectedText = $td.find('select option:selected').text();
                        $td.text(selectedText);
                    } else {
                        $td.text(updatedData[key]);
                    }
                });
            })
            .catch(error => {
                console.error("Error updating data:", error);
                swal("수정 실패", "데이터 수정 중 오류가 발생했습니다.", "error");
            });
    });

    // 문제 추가 버튼 클릭 시
    $(".add-btn").on("click", () => {
        const newRow = `
            <tr data-job-test-no="new">
                <td class="text-center">신규</td>
                <td class="text-center">
                    <select class="form-select">
                        <option value="JT01">진취형</option>
                        <option value="JT02">탐구형</option>
                        <option value="JT03">예술형</option>
                        <option value="JT04">사회형</option>
                        <option value="JT05">현장형</option>
                        <option value="JT06">관리형</option>
                        <option value="JT07">관습형</option>
                    </select>
                </td>
                <td>
                    <input type="text" class="form-control" placeholder="검사 문항 입력">
                </td>
                <td class="d-flex justify-content-center">
                    <button type="button" class="btn btn-success add-save-btn me-2">저장</button>
                    <button type="button" class="btn btn-secondary cancel-btn">취소</button>

                </td>
            </tr>
        `;
        $("#jobTestTableBody").append(newRow);
    });

    // 문제 추가 저장 버튼 클릭 시
    $(document).on("click", ".add-save-btn", function () {
        const $row = $(this).closest("tr");
        const jobTestType = $row.find("select").val();
        const jobTestText = $row.find("input").val().trim();

        if (!jobTestText) {
            swal("오류", "문항 내용을 입력해주세요.", "error");
            return;
        }

        console.log("Adding new test:", { jobTestType, jobTestText });



    axios.post(`${cp}/jobtest/create`, { jobTestType, jobTestText })
    .then((response) => {
        const jobTestList = response.data; // 전체 리스트 반환
        const tableBody = document.querySelector("#jobTestTableBody");

        // 테이블 초기화
        tableBody.innerHTML = "";

        // 리스트를 다시 렌더링
        jobTestList.forEach((jobTest) => {
            const newRow = `
                <tr data-job-test-no="${jobTest.jobTestNo}">
                    <td class="text-center">${jobTest.rnum}</td>
                    <td class="text-center" data-key="cocoStts">${jobTest.commoncode.cocoStts}</td>
                    <td class="job-test-text" data-key="jobTestText">${jobTest.jobTestText}</td>
                    <td class="d-flex justify-content-center">
                        <button type="button" class="btn btn-info edit-btn me-2">수정</button>
                        <button type="button" class="btn btn-danger delete-btn">삭제</button>
						<button type="button" class="btn btn-success save-btn me-2" style="display: none;">저장</button>
                        <button type="button" class="btn btn-secondary cancel-btn" style="display: none;">취소</button>
                    </td>
                </tr>
            `;
            tableBody.insertAdjacentHTML("beforeend", newRow);
        });

       swal({
            title: "성공",
            text: "문제가 추가되었습니다.",
            icon: "success",
            button: "확인", // 버튼 텍스트
        })
    }).catch((error) => {
        console.error("Error creating data:", error);
        swal("오류", "문제 추가 중 문제가 발생했습니다.", "error");
    });

    });

    // 삭제 버튼 클릭 시
    $(document).on("click", ".delete-btn", function () {
        const $row = $(this).closest("tr");
        const jobTestNo = $row.data("job-test-no");
        swal({
            title: "정말 삭제하시겠습니까?",
            text: "삭제 후 복구할 수 없습니다.",
            icon: "warning",
            buttons: ["취소", "삭제"],
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                axios.delete(`${cp}/jobtest/delete/${jobTestNo}`)
                    .then(() => {
                        swal("삭제 완료", "문제가 성공적으로 삭제되었습니다.", "success");
                        $row.remove();
                    })
                    .catch(error => {
                        console.error("Error deleting data:", error);
                        swal("삭제 실패", "문제 삭제 중 오류가 발생했습니다.", "error");
                    });
            }
        });
    });

    // Helper function to create a select box
    function createSelectBox(selectedValue, options) {
        const matchedValue = options.find(option => option.label === selectedValue)?.value;
        let selectHtml = `<select class="form-select" data-origin="${selectedValue}">`;
        options.forEach(option => {
            const isSelected = option.value === matchedValue ? "selected" : "";
            selectHtml += `<option value="${option.value}" ${isSelected}>${option.label}</option>`;
        });
        selectHtml += "</select>";
        return selectHtml;
    }
});
