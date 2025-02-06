function addCertificate() {
    const container = document.getElementById('certificatesContainer'); //자격증 폼

    const certificateDiv = document.createElement('div');
    certificateDiv.classList.add('certificate-item', 'row', 'align-items-center', 'mb-3'); // 스타일 설정

    // 자격증 폼 HTML 템플릿
    certificateDiv.innerHTML = `
		<div class="row align-items-center g-3">
		    <div class="col-md-4">
		        <label class="form-label">자격증 이름</label>
		        <input type="text" class="form-control certNm" placeholder="자격증 이름을 입력하세요" required>
		    </div>
		    <div class="col-md-4">
		        <label class="form-label">취득일</label>
		        <input type="date" class="form-control certDate" required>
		    </div>
		    <div class="col-md-4">
		        <div class="d-flex align-items-end">
		            <div style="flex: 1;">
		                <label class="form-label">첨부파일</label>
		                <input type="file" class="form-control certFile" multiple>
		            </div>
		            <button type="button" class="btn btn-danger ms-2" onclick="removeCertificate(this)">삭제</button>
		        </div>
		    </div>
		</div>
    `;

    container.appendChild(certificateDiv); 
}

function removeCertificate(button) {
    const certificateItem = button.closest('.certificate-item');
    
    if (certificateItem) {
        certificateItem.remove();
    }
}

const maxLength = 600;

document.querySelectorAll('.intlimit').forEach(textarea => {
    const remainingChars = document.createElement('p');
    remainingChars.style.color = '#6c757d';
    remainingChars.style.textAlign = 'right';
    remainingChars.style.fontSize = '12px';
    remainingChars.textContent = `남은 글자 수: ${maxLength}`;

    textarea.parentNode.appendChild(remainingChars);

    textarea.addEventListener('input', () => {
        if (textarea.value.length > maxLength) {
            textarea.value = textarea.value.substring(0, maxLength);
        }

        const remaining = maxLength - textarea.value.length;
        remainingChars.textContent = `남은 글자 수: ${remaining}`;

        remainingChars.style.color = remaining < 0 ? 'red' : '#6c757d';
    });
});






document.getElementById('saveBtn').addEventListener('click', async function () {

    const userConfirmation = await swal({
        title: "주의사항",
        text: "한 번 제출된 자기소개서는 수정이 불가합니다. 제출하시겠습니까?",
        icon: "warning",
        buttons: ["취소", "저장"],
        dangerMode: true,
    });

    if (!userConfirmation) {
        return;
    }

    const introduceForm = document.getElementById('introduceForm');
    const formData = new FormData(introduceForm);

    try {
        const response = await fetch('/yguniv/introduce/create/introduce', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error('자기소개서 저장 실패');

        const introduceId = await response.json(); // 서버에서 반환된 intCd
        console.log('Introduce saved with ID:', introduceId);

        await saveCertificates(introduceId);
        
        // 저장 성공 알림창
        swal({
            title: "등록 완료",
            text: "자기소개서와 자격증이 성공적으로 등록되었습니다.",
            icon: "success",
            button: "확인"
        }).then(()=>{
			window.location.href=`/yguniv/introduce/list/{stuId}`
			})
    } catch (error) {
        console.error('Error saving Introduce:', error);
        swal({
            title: "등록 실패",
            text: "등록 중 오류가 발생했습니다. 다시 시도해주세요.",
            icon: "error",
            button: "확인"
        });
    }
});

async function saveCertificates(introduceId) {
    const certificateForms = document.querySelectorAll('#certificatesContainer .certificate-item');
    if (certificateForms.length === 0) {
        return;
    }

    for (let i = 0; i < certificateForms.length; i++) {
        const formData = new FormData();
        const certificateItem = certificateForms[i];

        formData.append('intCd', introduceId);
        formData.append('certNm', certificateItem.querySelector('.certNm').value);
        formData.append('certDate', certificateItem.querySelector('.certDate').value);
        const uploadFiles = certificateItem.querySelector('.certFile').files;
        if (uploadFiles.length > 0) {
            formData.append('uploadFiles', uploadFiles[0]);
        }

        try {
            const response = await fetch('/yguniv/introduce/create/certificate', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) throw new Error(`Certificate ${i + 1} 저장 실패`);
            console.log(`Certificate ${i + 1} 저장 완료`);
        } catch (error) {
            console.error('Error saving Certificate:', error);
            swal({
                title: "자격증 저장 실패",
                text: `자격증 ${i + 1} 저장 중 문제가 발생했습니다.`,
                icon: "warning",
                button: "확인"
            });
        }
    }
}
