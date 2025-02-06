document.addEventListener("DOMContentLoaded", () => {

	const contextPath = document.getElementById('contextPath').value;
	const cnbNo = document.getElementById('cnbNo').value;
	const contextTitle = document.getElementById('contextTitle'); // 제목 가져오기
	const mainBoardCount = document.getElementById('mainBoardCount').value; 
	
	// 공지사항 게시판 상세조회
	fetch(`${contextPath}/lecture/${lectNo.value}/board/boardDetail/${cnbNo}`)
	    .then(response => response.json())  // 서버에서 받은 JSON 응답
	    .then(data => {
			console.log("data : ", data);
			document.getElementById('absenceReason2').innerHTML = data.cnbNotes; // 게시물에 CK에디터 넣어주기
			renderFileDetails(data.atchFile.fileDetails)	
		
		// 서버에서 받은 파일 상세 목록 데이터를 렌더링하는 함수
		function renderFileDetails(fileDetails) {
		    const container = document.getElementById('fileDetailsContainer'); 
		    container.innerHTML = ''; // 기존 내용을 초기화
		
		    if (fileDetails && fileDetails.length > 0) {
		        let content = '<td>'; // 파일 리스트를 <td> 안에 넣을 것임
	
		        fileDetails.forEach((fd, index) => {
		            const downUrl = `${contextPath}/absence/atch/${fd.atchFileId}/${fd.fileSn}`; // 다운로드 URL 생성
		            content += `
		                <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
		                ${index !== fileDetails.length - 1 ? '|' : ''} <!-- 마지막 항목 뒤에 '|' 추가 -->
		            `;
		        });
		
		        content += '</td>'; // <td> 태그 닫기
		        container.innerHTML = content; // 최종적으로 동적으로 생성된 내용을 container에 삽입
		    } else {
		        container.innerHTML = '<td>첨부파일 없음</td>'; // 파일이 없을 경우
		    }
		}
	
	})
	
	document.getElementById('editButton').addEventListener('click', function() {
		
        // 버튼 클릭 시 이동할 URL
        const url = `${contextPath}/lecture/${lectNo.value}/board/editform/${cnbNo}`;
        
        // 페이지 이동
        window.location.href = url;
    });
	
	// 수정 버튼을 눌렀을 때 모달창 띄워주기
	/*
	document.getElementById('editButton').addEventListener('click', function() {
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
		                            swal('파일이 삭제되었습니다.');
		                            this.closest('span').remove(); // 삭제된 파일의 UI 제거
		                        } else {
		                            swal('파일 삭제에 실패했습니다.');
		                        }
		                    })
		                    .catch(error => {
		                        console.error('Error:', error);
		                        swal('파일 삭제 중 오류가 발생했습니다.');
		                    });
		            });
		        });
		    } else {
		        container.innerHTML = '<p>첨부파일 없음</p>';
		    }
		}
	
	});
	*/
	
	// 모달 창에 있는 수정버튼 눌렀을때
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
	                    window.location.reload(); // 페이지 새로고침
	                });
	        })
	        .catch(error => {
	            console.error("수정 요청 중 오류 발생:", error);
	            swal("오류 발생", "수정 중 문제가 발생했습니다.", "error");
	        });
		})
	});
	
	
	
	
	
	document.getElementById('dropButton').addEventListener('click', function() {
		fetch(`${contextPath}/lecture/${lectNo.value}/board/boardDetail/${cnbNo}`)
		.then(response => response.json())  // 서버에서 받은 JSON 응답
		.then(data => {
			swal({
			    title: "정말 삭제하시겠습니까?",
			    text: "삭제된 데이터는 복구할 수 없습니다.",
			    icon: "warning",
			    buttons: {
			        confirm: {
			            text: "삭제",
			            value: true,
			            visible: true,
			            className: "btn btn-danger",
			            closeModal: true
			        },
					cancel: {
			            text: "취소",
			            value: null,
			            visible: true,
			            className: "btn btn-secondary",
			            closeModal: true
			        }
			    },
			    dangerMode: true,
			})
			.then((willDelete) => {
			    if (willDelete) {
			        // 삭제 요청 처리
			        fetch(`${contextPath}/lecture/${lectNo.value}/board/drop/${cnbNo}`, {
			            method: "DELETE",
			        })
			        .then(response => {
			            if (!response.ok) {
			                throw new Error("서버에서 오류 발생");
			            }
			            return response.json();
			        })
			        .then(data => {
			            console.log("삭제 성공:", data);
			            swal("삭제 완료", "공결 사유가 정상적으로 삭제되었습니다.", "success")
			                .then(() => {
		        				window.location.href = `${contextPath}/lecture/${lectNo.value}/board`;
			                });
			        })
			        .catch(error => {
			            console.error("삭제 요청 중 오류 발생:", error);
			            swal("오류 발생", "삭제 중 문제가 발생했습니다.", "error");
			        });
			    } else {
			        swal("취소", "삭제가 취소되었습니다.", "error");
			    }
			});

		})
	})
	

	// 날짜, 시간 포맷팅 작업
	// 시간 데이터 포맷 (사용하지 않음)
	function timeAgo(date) {
	    const now = new Date();
	    const targetDate = new Date(date);
	
	    // 날짜가 오늘인지 확인
	    const isToday = now.toDateString() === targetDate.toDateString();
	
	    // 시간 차이 계산
	    const diffInMs = now - targetDate; // 시간 차이 (밀리초 단위)
	    const diffInSec = Math.floor(diffInMs / 1000); // 초 단위로 변환
	    const diffInMin = Math.floor(diffInSec / 60); // 분 단위로 변환
	    const diffInHour = Math.floor(diffInMin / 60); // 시간 단위로 변환
	
	    if (isToday) {
	        // 오늘 날짜일 경우
	        if (diffInMin < 2) {
	            return "방금 전";  // 2분 이내
	        } else if (diffInMin < 60) {
	            return `${diffInMin}분 전`;  // 2분 이상 1시간 미만
	        } else {
	            return `${diffInHour}시간 전`;  // 1시간 이상
	        }
	    } else {
	        // 오늘이 아닌 날짜일 경우
	        const year = targetDate.getFullYear();
	        const month = String(targetDate.getMonth() + 1).padStart(2, '0');
	        const day = String(targetDate.getDate()).padStart(2, '0');
	        return `${year}년 ${month}월 ${day}일`;  // 년-월-일 형식
	    }
	}
	
	// 예시 사용법:
	const timeString = '2025-01-04T10:25:54';  // 예시 시간
	console.log(timeAgo(timeString));  // 오늘 날짜가 아니면 "2025-01-04" 형식으로 출력


}); 




