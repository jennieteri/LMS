document.addEventListener("DOMContentLoaded", () => {
	
	const contextPath = document.getElementById('contextPath').value;
	const cnbNo = document.getElementById('cnbNo').value;
	const contextTitle = document.getElementById('contextTitle'); // 제목 가져오기	
	const mainBoardCount = document.getElementById('mainBoardCount').value; 
	
	document.getElementById('cancleBtn').addEventListener('click', function() {
        const url = `${contextPath}/lecture/${lectNo.value}/board/detail/${cnbNo}`;
        window.location.href = url;
    });
	
	fetch(`${contextPath}/lecture/${lectNo.value}/board/boardDetail/${cnbNo}`)
	    .then(response => response.json())  // 서버에서 받은 JSON 응답
	    .then(data => {
			console.log("data : ", data);
	
			contextTitle.value = data.cnbNm; // 제목 넣어주기

			if (data.cnbMainYn === 'Y') {
			    document.getElementById('boardMainYn').checked = true;  // 체크박스 체크
			} else {
			    document.getElementById('boardMainYn').checked = false;  // 체크박스 체크 해제
			}
				
			// 메인공지사항이 값이 3일 경우
			if (mainBoardCount === '3' && data.cnbMainYn === 'N') {
			    // 체크박스 클릭 이벤트 리스너 추가
			    document.getElementById('boardMainYn').addEventListener('click', function(event) {
			        this.checked = false;  // 체크 해제
			        swal({
			            icon: 'warning',
			            title: '초과',
			            text: '메인 공지사항이 한도를 초과했습니다. (3개)'
			        });
			        // 클릭된 체크박스의 상태를 원래대로 유지하지 않도록 클릭을 취소
			        event.preventDefault();  // 클릭 이벤트 취소
			    });
			}
			
	
			editorInstance[0].setData(data.cnbNotes); // 저장된 '내용' 넣기
			
			const fileDetails = data.atchFile.fileDetails // 파일 데이터 가져오기
			renderFileDetails(fileDetails); // 파일 데이터 넣어주기
			
		})
		
		// 서버에서 받은 파일 상세 목록 데이터를 렌더링하는 함수
		function renderFileDetails(fileDetails) {
		    const container = document.getElementById('fileDetailsContainer2'); // 출력될 컨테이너
		    container.innerHTML = ''; // 기존 내용을 초기화
		
		    if (fileDetails && fileDetails.length > 0) {
		        fileDetails.forEach((fd, index) => {
		            const isLast = index === fileDetails.length - 1; // 마지막 항목인지 확인
		
		            // 파일 상세 정보를 추가
		            container.innerHTML += `
		                <span>
		                    ${fd.orignlFileNm}[${fd.fileFancysize}]
		                    <a data-atch-file-id="${fd.atchFileId}" 
		                       data-file-sn="${fd.fileSn}" 
		                       class="btn btn-danger file-delete-btn" 
		                       href="javascript:;">
		                        삭제
		                    </a>
		                    ${isLast ? '' : '|'}
		                </span>`;
		        });
		
		        // 삭제 버튼 이벤트 추가
		        document.querySelectorAll('.file-delete-btn').forEach(deleteBtn => {
		            deleteBtn.addEventListener('click', function () {
		                const atchFileId = this.getAttribute('data-atch-file-id');
		                const fileSn = this.getAttribute('data-file-sn');
		
		                // 삭제 요청 처리
		                fetch(`${contextPath}/atch/${atchFileId}/${fileSn}`, {
		                    method: 'DELETE',
		                    headers: {
		                        'Content-Type': 'application/json'
		                    },
		                    body: JSON.stringify({ atchFileId, fileSn })
		                })
		                    .then(response => response.json())
		                    .then(result => {
		                        if (result.success) {
		                            swal({
							            icon: 'success',
							            title: '삭제 완료',
							            text: '선택한 첨부파일이 삭제되었습니다.'
							        });
		                            this.closest('span').remove(); // 삭제된 파일의 UI 제거
		                        } else {
		                            swal({
							            icon: 'warning',
							            title: '삭제 실패',
							            text: '첨부파일 삭제에 실패했습니다.'
							        });
		                        }
		                    })
		                    .catch(error => {
		                        console.error('Error:', error);
		                        swal({
							        icon: 'error',
							        title: '삭제 오류',
							        text: '첨부파일을 삭제하는데 오류가 발생했습니다.'
							    });
		                    });
		            });
		        });
		    } else {
		        container.innerHTML = '<p>첨부파일 없음</p>';
		    }
		}
		
	document.getElementById('saveEditButton').addEventListener('click', function() {
		fetch(`${contextPath}/lecture/${lectNo.value}/board/boardDetail/${cnbNo}`)
		.then(response => response.json())  // 서버에서 받은 JSON 응답
		.then(data => {
			
			const editcontent = document.getElementById('updateEditor');
			const cnbNotes = editorInstance[0].getData(editcontent);
			let cnbMainYn = '';
			
			if (document.getElementById('boardMainYn').checked) {
				cnbMainYn = 'Y';
			} else {
			    cnbMainYn = 'N';
			}
			
			
			console.log("수정할 제목 : ", contextTitle.value);
			console.log("수정할 내용 : ", cnbNotes);

			const updateData = {
		        cnbNm: contextTitle.value,
		        cnbNotes: cnbNotes,
				cnbMainYn: cnbMainYn
	    	};
			
			let absenseForm = document.querySelector('#fileForm');
			let formData = new FormData(absenseForm);
			
			for (let key in updateData) {
				formData.append(key, updateData[key]);
			}
			
			formData.set('cnbNotes', cnbNotes);
			
			console.log("formData 데이터 찍어보자 : ", formData.get('uploadFiles'))
			
			fetch(`${contextPath}/lecture/${lectNo.value}/board/edit/${cnbNo}`, {
	        method: 'POST',
	        body: formData
	    })
			.then(response => {
	            if (!response.ok) {
	                throw new Error("서버에서 오류 발생");
	            }
	            return response.json();
	        })
	        .then(data => {
	            console.log("수정 성공:", data);
	            swal("수정 완료", "공결 사유가 정상적으로 수정되었습니다.", "success")
	                .then(() => {
	                    const url = `${contextPath}/lecture/${lectNo.value}/board/detail/${cnbNo}`;
        				window.location.href = url;
	                });
	        })
	        .catch(error => {
	            console.error("수정 요청 중 오류 발생:", error);
	            swal("오류 발생", "수정 중 문제가 발생했습니다.", "error");
	        });
		})
	});
	
})