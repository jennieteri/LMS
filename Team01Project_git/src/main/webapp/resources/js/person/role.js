/**
 * 
 */

function showRole(personId) {
    // 데이터 속성에서 권한 문자열 가져오기
    const cp = document.querySelector("#cp").value;
    const personTypeElement = document.querySelector(`#role[data-id="${personId}"] a`);
    const personTypes = personTypeElement.textContent
        .trim()
        .split(/\s*,\s*/); // 공백 포함 여부와 상관없이 콤마로 구분

    // 권한이 비어있으면 진행하지 않음
    if (!personTypes || personTypes.length === 0) {
        swal({
            title: "권한을 선택해주세요.",
            icon: "error", // 에러 마크 표시
            button: "확인"
        });
        return; // 더 이상 진행하지 않음
    }

    // 권한 한글-영문 매핑
    const roleMap = {
        '학생': 'STUDENT',
        '교수': 'PROFESSOR',
        '교직원': 'EMPLOYEE',
        '조교': 'ASSISTANT'
    };

    // 모달 표시
    const modal = new bootstrap.Modal(document.getElementById('roleModal'));
    modal.show();

    // 체크박스 상태 업데이트
    const checkboxes = document.querySelectorAll("input[name='personType']");
    checkboxes.forEach(checkbox => {
        const koreanRole = Object.keys(roleMap).find(key => roleMap[key] === checkbox.value);
        checkbox.checked = personTypes.includes(koreanRole);
    });

    // 저장 버튼 클릭 시 로직
    const saveButton = document.querySelector('#roleModal .btn-primary');
    saveButton.onclick = function() {
        const selectedTypes = Array.from(checkboxes)
            .filter(cb => cb.checked)
            .map(cb => cb.value);

        // 선택된 권한이 없으면 저장하지 않음
        if (selectedTypes.length === 0) {
            swal({
                title: "권한을 선택해주세요.",
                icon: "error", // 에러 마크 표시
                button: "확인"
            });
            return; // 저장 로직 실행 안 함
        }

        console.log('Selected types:', selectedTypes);

        fetch(`${cp}/person/role`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: personId, // id는 이미 정의되어 있어야 함
                personType: selectedTypes // personType은 체크된 값들
            })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
			
			fetch(`${cp}/resource/reload`)
			
            // 권한 업데이트 후, 테이블 업데이트
            const personTypeElement = document.querySelector(`#role[data-id="${personId}"] a`);
            
            // selectedTypes를 영어에서 한글로 변환하여 업데이트
            const roleMap = {
                'STUDENT': '학생',
                'PROFESSOR': '교수',
                'EMPLOYEE': '교직원',
        		'ASSISTANT': '조교'
            };

            const koreanRoles = selectedTypes.map(type => roleMap[type]); // 영어 값들을 한글로 변환

            personTypeElement.textContent = koreanRoles.join(', '); // 업데이트된 권한을 콤마로 구분하여 텍스트로 설정
        })
        .catch((error) => {
            console.error('Error:', error);
        });

        modal.hide();
    };
}





//업데이트 성공 이제 권한 새로 그려야함
// 1. reload 갔다 왔을 때 제대로 작동하도록
// 2. 랜더링을 테이블 지웠다 다시그리기로 할지 아니면 이 결과를 다시 그리도록 할지
//


// 모달 닫기
function closeModal() {
	const modal = document.getElementById("roleModal");
	modal.style.display = "none"; // 모달창을 숨김
}
