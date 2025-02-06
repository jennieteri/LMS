

// 모달이 열릴 때마다 해당 데이터를 채워넣고, absenceCd 값을 가져오는 함수
var myModal = document.getElementById('staticBackdrop');

var contextPath = document.querySelector('#contextPath');
var lectNo = document.querySelector('#lectNo');




/*
myModal.addEventListener('show.bs.modal', function (event) {
    // 버튼을 클릭했을 때 전달된 데이터를 가져오기
    var button = event.relatedTarget; // 버튼을 클릭한 요소
    var lectOrder = button.getAttribute('data-lectOrder');
    var studentName = button.getAttribute('data-studentName');
    var studentId = button.getAttribute('data-studentId');
    var absenceReason = button.getAttribute('data-absenceReason');
    //var absenceReason = editorInstance.getData();
    var absenceStatus = button.getAttribute('data-absenceStatus');
    //var lectNo = button.getAttribute('data-lectNo'); // data-lectNo 
    //var lectNo = `${lectNo.value}` 
    var absenceCd = button.getAttribute('data-absenceCd'); // data-absenceCd 
    var weekCd = button.getAttribute('data-weekCd'); // weekCd 값 

	console.log("내용의 값이 있나요? : ", absenceReason);
	//editorInstance.setData(absenceReason);
	console.log("data.absenceResn 사유 내용 : ", data.absenceResn);
	
	editorInstance.getData(data.absenceResn); 
	
	// editorInstance.setData(data.absenceResn);

    // 모달에 해당 데이터 채우기
    document.getElementById('lectOrder2').textContent = lectOrder;
    document.getElementById('studentName2').textContent = studentName;
    document.getElementById('studentId2').textContent = studentId;
    document.getElementById('absenceReason2').innerHTML = editorInstance.getData();
    document.getElementById('absenceStatus2').textContent = absenceStatus;

	console.log("사유 저장된거 보기2 : ", editorInstance.getData());
	console.log('모달창에 넣어줄거  : ',lectOrder, studentName, studentId, absenceStatus)
	
	console.log(`${lectNo.value}`)
	console.log('모달 승인버튼 : ',absenceCd, `${lectNo.value}`, lectOrder, studentId, weekCd)
	console.log('모달 반려버튼 : ',absenceCd)


     // 모달 -> 승인 버튼
    document.getElementById('approveButton').addEventListener('click', function() {
        // 승인 처리 시 absenceStatus를 'CO02'로 설정
        document.getElementById('absenceStatus2').textContent = 'CO02';
        handleAbsenceRequest(true, absenceCd, `${lectNo.value}`, lectOrder, studentId, weekCd, 'ATN1'); // 승인 처리
    });	

    // 모달 -> 반려 버튼
    document.getElementById('rejectButton').addEventListener('click', function() {
        // 반려 처리 시 absenceStatus를 'CO03'으로 설정
        document.getElementById('absenceStatus2').textContent = 'CO03';
        handleAbsenceRequest(false, absenceCd, `${lectNo.value}`, lectOrder, studentId, weekCd, null); // 반려 처리
    });
});*/



myModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget; // 버튼을 클릭한 요소

    var lectOrder = button.getAttribute('data-lectOrder');
    var studentName = button.getAttribute('data-studentName');
    var studentId = button.getAttribute('data-studentId');
    var absenceReason = button.getAttribute('data-absenceReason');
    var absenceStatus = button.getAttribute('data-absenceStatus');
    var absenceCd = button.getAttribute('data-absenceCd');
    var weekCd = button.getAttribute('data-weekCd');
    
    // 서버에서 absenceResn 데이터를 가져오는 fetch 요청을 추가
    fetch(`${contextPath}/lecture/${lectNo.value}/absence/${absenceCd}`)
        .then(response => response.json())  // 서버에서 받은 JSON 응답
        .then(data => {
            // 여기서 'data'는 서버 응답 데이터입니다
            console.log("서버에서 받은 데이터:", data);
            console.log("서버에서 받은 데이터:", data.absenceResn);

            // 서버 응답에서 사유(absenceResn) 값을 가져와서 CKEditor에 설정
            // editorInstance.setData(data.absenceResn);

			console.log("data.atst : ", data.atst);

            // 모달에 해당 데이터 채우기
            document.getElementById('lectOrder2').textContent = lectOrder;
            document.getElementById('studentName2').textContent = studentName;
            document.getElementById('studentId2').textContent = studentId;
            document.getElementById('absenceReason2').innerHTML = data.absenceResn; // absenceReason 업데이트
            document.getElementById('absenceStatus2').textContent = absenceStatus; // absenceStatus 값 채우기
			document.getElementById('absenceATST').value = data.atst;
			var formattedDate = '';
			
			if(data.absenceDt != null){
			formattedDate = data.absenceDt.slice(0, 4) + '년 ' +
							data.absenceDt.slice(4, 6) + '월 ' +
							data.absenceDt.slice(6, 8) + '일';  
			} else {
				formattedDate = '';
			}
			if (data.prst != '대기') {
                document.getElementById('dummyData').style.display = 'none'; // 승인 버튼 숨기기
                document.getElementById('approveButton').style.display = 'none'; // 승인 버튼 숨기기
                document.getElementById('rejectButton').style.display = 'none'; // 반려 버튼 숨기기
				document.querySelectorAll('.preReturn').forEach(element => {
				    element.style.display = 'none';  // 각 요소의 display 속성을 'none'으로 설정
				});
			} else {
                document.getElementById('dummyData').style.display = 'inline-block'; // 승인 버튼 보이기
                document.getElementById('approveButton').style.display = 'inline-block'; // 승인 버튼 보이기
                document.getElementById('rejectButton').style.display = 'inline-block'; // 반려 버튼 보이기
				document.querySelectorAll('.preReturn').forEach(element => {
				    element.style.display = ''; 
				});
			}
        	
			// '출석'일 때 승인 및 반려 버튼 숨기기
            if (data.atst === '출석') {
				console.log("출석이요출석이요출석이요출석이요출석이요출석이요")
				if(data.absenceDt != null){
					document.getElementById('modalDate').textContent = `승인날짜 : ${formattedDate}`;
				} else {
					formattedDate = '';
				}
			} else{
				console.log("출석아니요출석아니요출석아니요출석아니요출석아니요")
				if(data.absenceDt == null){
					formattedDate = '';
					document.getElementById('modalDate').textContent = '';
				}
			}
            
		})
        .catch(error => {
            console.error('Error:', error);
        });

    // 모달 -> 승인 버튼
    document.getElementById('approveButton').addEventListener('click', function() {
        const cocoCd = 'CO02'; // 승인 상태 값
        document.getElementById('absenceStatus2').textContent = cocoCd; // 'CO02' 설정

        // 승인 처리 함수 호출 (absenceStatus는 cocoCd로 사용)
        handleAbsenceRequest(true, absenceCd, `${lectNo.value}`, lectOrder, studentId, weekCd, 'ATN1', cocoCd, null); // 승인 시 'ATN1' 사용
    });

    // 모달 -> 반려 버튼
    document.getElementById('rejectButton').addEventListener('click', function() {
        const cocoCd = 'CO03'; // 반려 상태 값
        document.getElementById('absenceStatus2').textContent = cocoCd; // 'CO03' 설정
		var absenceReturn = document.getElementById('absenceReturn').value; // 반려 사유 설정
		console.log("absenceReturn : ", absenceReturn);
		
        // 반려 처리 함수 호출 (absenceStatus는 cocoCd로 사용)
        handleAbsenceRequest(false, absenceCd, `${lectNo.value}`, lectOrder, studentId, weekCd, null, cocoCd, absenceReturn); // 반려 시 null 사용
    });
});
	
	// 모달창 닫힐때 초기화 함
	$('#staticBackdrop').on('hidden.bs.modal', function () {
        // 텍스트 입력 초기화
        $('#absenceReturn').val('');
    });



	// '데이터 입력' 버튼 눌럿을때
	document.getElementById("dummyData").addEventListener("click", function(event) {
		document.getElementById('absenceReturn').value = `
		안녕하세요. 담임교수입니다.

		최근 제출된 공결 신청에 대해 검토한 결과, 다음과 같은 사유로 일부 신청이 반려되었습니다. 반려된 신청에 대해서는 아래의 사항을 참고하시기 바랍니다.
		
		1. 공결 사유 미비
		제출된 공결 사유서에 필요한 정보가 부족하여 반려되었습니다. 공결 사유는 반드시 구체적으로 기재해 주시기 바랍니다.
		2. 제출 기한 초과
		공결 신청이 기한을 지나 제출되어 반려되었습니다. 공결 신청은 반드시 기한 내에 제출해야 합니다.
		3. 증빙 서류 미비
		공결 신청에 필요한 증빙 서류가 제출되지 않았습니다. 증빙 서류가 없는 경우 공결 신청은 반려될 수 있습니다.
		4. 공결 사유의 부적합
		개인적인 사정 외의 이유로 신청된 공결이 부적합하다고 판단되어 반려되었습니다. 공결은 병결, 가족 사망 등 정당한 사유에 한해 인정됩니다.
		반려된 신청에 대해 이의가 있을 경우, 학생처로 문의해 주시기 바랍니다.
		감사합니다.
		`;
	});






function removeNullProperties(obj) {
    if (typeof obj !== 'object' || obj === null) {
        return obj; // 객체가 아니거나 null인 경우 그대로 반환
    }

    // 객체를 복제하면서 null이 아닌 값만 유지
    if (Array.isArray(obj)) {
        return obj.map(item => removeNullProperties(item)).filter(item => item !== null);
    }

    const result = {};
    for (const key in obj) {
        if (obj[key] !== null) {
            result[key] = removeNullProperties(obj[key]);
        }
    }
    return result;
}


// 업데이트할때 데이터가 있는지 확인해야함 
// handleAbsenceRequest 함수
function handleAbsenceRequest(isApproved, absenceCd, lectNo, lectOrder, stuId, weekCd, atndCd, cocoCd, absenceReturn) {
    const data = {
        absenceCd: absenceCd, 
        cocoCd: cocoCd, // cocoCd는 승인/반려 상태 코드
        isApproved: isApproved, // 승인 여부
		absenceReturn: absenceReturn // 반려사유
    };

    // 승인 처리일 때만 attendanceVO를 포함
    if (isApproved) {
        data.attendanceVO = {
            lectNo: lectNo,
            lectOrder: lectOrder,
            stuId: stuId,
            weekCd: weekCd,
            atndCd: atndCd // 출결 상태 정보 (승인 시 ATN1)
        };
    } else {
        // 반려 처리일 때는 attendanceVO를 포함하지 않거나 null로 설정
        data.attendanceVO = null; // 반려 시에는 출결 상태를 변경하지 않음
        //data.attendanceVO = {
		//	absenceReturn: absenceReturn
		//}
    }

    // removeNullProperties 함수를 호출하여 null 속성 제거
    const cleanData = removeNullProperties(data);

    console.log('cleanData : ', cleanData);
    
    const url = `${contextPath}/lecture/${lectNo}/absence/edit`;

    console.log("url 확인");
    console.log(`${contextPath}`);
    console.log(`${lectNo}`);
    console.log("교수님 데이터바꿔주세요. : ", data);

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cleanData) // null이 제거된 데이터를 서버로 전송
    })
    .then(response => response.json())
    .then(data => {
        // swal로 성공 메시지 표시하고 확인 후 페이지 새로고침
        swal({
            title: isApproved ? "공결 인증서 승인 처리 완료!" : "공결 인증서 반려 처리 완료!",
            icon: "success",
            buttons: {
                confirm: {
                    text: "확인",
                    value: true,
                    visible: true,
                    className: "btn btn-primary",
                    closeModal: true
                }
            }
        }).then((confirmed) => {
            if (confirmed) {
                // 확인 버튼 클릭 시 페이지 새로 고침
                location.reload(); // 페이지 새로고침
            }
        });
    })
    .catch(error => {
        console.error('Error:', error);
        swal("서버와의 통신 오류가 발생했습니다.", "", "error");
    });
}



// 수정버튼을 눌렀을때 처리하는 스크립트
document.querySelectorAll('.editBtn').forEach(button => {
    button.addEventListener('click', function() {
        const absenceCd = this.getAttribute('data-absenceCd');  // absenceCd 값을 가져옵니다.
		
		
        console.log('absenceCd:', absenceCd);
		console.log(contextPath);
		console.log(`${contextPath.value}`);
		console.log(`${lectNo.value}`);

        // AJAX 요청으로 서버에 absenceCd를 보내고 데이터 받아오기
        fetch(`${contextPath}/lecture/${lectNo.value}/absence/${absenceCd}`)
		.then(response => response.json())
		.then(data => {
		    console.log("서버에서 받은 데이터:", data);  // 데이터 구조를 콘솔로 출력
		
		    // 서버로부터 받은 데이터를 모달에 업데이트합니다.
		    document.getElementById('lectOrder').innerText = data.lectOrder;
		    document.getElementById('studentName').innerText = data.studentVO.nm;
		    document.getElementById('studentId').innerText = data.stuId;
		    document.getElementById('absenceStatus').innerText = data.atst;
		
		    // editAbsenceReason에 서버에서 받아온 사유 설정
			/*const input = document.getElementById('inputText').value;
			/*ck에디터.
			어제 햇던거 모달창 닫을때  */
			
			console.log("data.absenceResn 사유 내용 : ", data.absenceResn);

		    editorInstance.setData(data.absenceResn); // textarea에 사유 설정
		
		    // 모달을 표시
		    $('#staticBackdropEdit').modal('show');
			
			
			////////////////////////////////////////////////// 수정 버튼 추가
			
			// 기존 클릭 이벤트 리스너 제거
	        //$('#saveEditButton').off('click'); 
			
			// 수정 버튼 클릭 시 fetch로 데이터 업데이트
	        $('#saveEditButton').on('click', function() {
	            const absenceResn = editorInstance.getData(); // 수정된 사유
				//const absenceCd = document.getElementById('absenceCd').value;
				
				console.log("수정 버튼 눌렀을때 데이터가 보이는지1 : ", document.getElementById('editor'))
				console.log("수정 버튼 눌렀을때 데이터가 담기는지2 : ", data.absenceResn)
				console.log("수정 버튼 눌렀을때 데이터가 담기는지3 : ", data.absenceResn.value)
				console.log("수정 버튼 눌렀을때 데이터가 담기는지4 : ", editorInstance.getData)
				console.log("수정 버튼 눌렀을때 데이터가 담기는지5 : ", editorInstance.getData());
				
				
				
				console.log('Request Body:', {
			        absenceCd: absenceCd,
			        absenceResn: absenceResn
			    });

	            // fetch로 서버에 업데이트 요청
	            fetch(`${contextPath}/lecture/${lectNo.value}/absence/edit/reason`, {
	                method: 'POST',
					//body: formData
	                headers: {
	                    'Content-Type': 'application/json',
	                },
	                body: JSON.stringify({
	                    absenceCd: absenceCd, 
	                    absenceResn: absenceResn,
						//absenceReturn: absenceReturn
	                })
	            })
	            .then(response => response.json())
	            .then(data => {
				    console.log('성공:', data);
				    
				    // swal을 사용하여 성공 메시지를 표시
				    swal({
				        title: '성공!',
				        text: '사유가 성공적으로 수정되었습니다.',
				        icon: 'success',
				        button: '확인'
				    }).then(() => {
				        $('#staticBackdropEdit').modal('hide'); // 모달 닫기
				        window.location.reload(); // 페이지 새로고침
				    });
				})
				.catch(error => {
				    console.error('에러 발생:', error);
				    
				    // swal을 사용하여 에러 메시지를 표시
				    swal({
				        title: '오류!',
				        text: '수정 중 오류가 발생했습니다.',
				        icon: 'error',
				        button: '확인'
				    });
				});
	        });
		})
		.catch(error => {
		    console.error('Error:', error);
		});
	});
});



// 공결 인증서 삭제 처리
document.addEventListener('DOMContentLoaded', function() {
    // 'absenceDeleteBtn' 클래스를 가진 모든 버튼을 선택
    const deleteButtons = document.querySelectorAll('.absenceDeleteBtn');

    // 각 버튼에 클릭 이벤트 추가
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // 버튼에서 data-absenceCd와 data-cocoCd 값을 가져옴
            const absenceCd = button.getAttribute('data-absenceCd');
            const cocoCd = button.getAttribute('data-cocoCd');
			const lectNo = button.getAttribute('data-lectNo');
            

			// fetch로 서버에 업데이트 요청
            fetch(`${contextPath.value}/lecture/${lectNo}/absence/drop`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    absenceCd: absenceCd, 
                })
            })
            .then(response => response.json())
            .then(data => {
			    console.log('성공:', data);
			    
			    // swal을 사용하여 성공 메시지 표시
			    swal({
			        title: '성공!',
			        text: '공결 신청이 성공적으로 삭제되었습니다.',
			        icon: 'success',
			        button: '확인'
			    }).then(() => {
			        $('#staticBackdropEdit').modal('hide'); // 모달 닫기
			        window.location.reload(); // 페이지 새로고침
			    });
			})
			.catch(error => {
			    console.error('에러 발생:', error);
			    
			    // swal을 사용하여 에러 메시지 표시
			    swal({
			        title: '오류!',
			        text: '삭제 중 오류가 발생했습니다.',
			        icon: 'error',
			        button: '확인'
			    });
			});
        });
    });
});




/*// 셀렉트 박스
document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('statusFilter').addEventListener('change', function() {
    var selectedStatus = this.value; // 셀렉트박스에서 선택된 값
    console.log("선택된 값: " + selectedStatus); // 디버깅용 로그

    var rows = document.querySelectorAll('.absenceRow'); // 모든 행을 선택

    rows.forEach(function(row) {
      var status = row.getAttribute('data-absence-status'); // 각 행의 상태 값 (absence.PRST)
      console.log("행의 상태 값: " + status); // 디버깅용 로그

      // "전체조회"가 선택되면 모든 행을 표시
      if (selectedStatus === '전체') {
        row.style.display = ''; // 모든 행을 보이도록 설정
      } else if (status === selectedStatus) {
        // 그 외에는 선택된 상태와 일치하는 행만 표시
        row.style.display = ''; // 상태가 일치하는 행은 보이도록 설정
      } else {
        row.style.display = 'none'; // 그 외의 행은 숨기기
      }
    });
  });
});*/





var contextPath = document.querySelector('#form-table').dataset['path'];

var editorInstance;
	//필요한 자원 import하는 구문
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
	
	//이미지 업로드 처리할때 보안때문에 html에서 제공하는 token 사용
	const csrfMetaTag = document.querySelector('meta[name="csrf-token"]');
	//만약 meta 설정이 없는 경우 null로 초기화 있다면, token값 가져옴
	const csrfToken = csrfMetaTag ? csrfMetaTag.getAttribute('content') : null;
	
	//Editor의 종류중 사용할 Editor 생성 ClassicEditor권장 다른 종류 사용하고싶다면, 문서확인 후 사용
	ClassicEditor.create( document.querySelector('#editor'),{
		licenseKey: 'GPL' //5버전 기본 라이센스키 ; 필수값
		//사용할 플러그인 플러그인종류는 문서확인
		,  plugins: [
            Essentials, Bold, Italic, Underline, Link, Image, ImageUpload,
            List, BlockQuote, Heading, MediaEmbed, Table, TableToolbar, 
            FontSize, FontFamily, FontColor, Undo, SimpleUploadAdapter
        ],
		// 툴바 메뉴
        toolbar: [
            'undo', 'redo', '|', 'bold', 'italic', 'underline', '|', 'link', 'imageUpload', '|',
            'bulletedList', 'numberedList', '|', 'blockquote', 'insertTable', '|',
            'fontSize', 'fontFamily', 'fontColor', '|', 'mediaEmbed'
        ]
		,language: 'ko' // 한국어 설정
		//이미지 업로드 어댑터 config설정
		//이미지 url은 noticeboard폴더에 해당 처리 controller를 만들어두었음.
		//컨트롤러에서 반환값은 url임으로 반환값이 어떻게 오는지 알아야 데이터를 처리할 수 있음.
		, simpleUpload: { 
					uploadUrl: `${contextPath}/imageUpload`
					, headers:  csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {}
					
			}
	})
	//promise객체가 반환됨.
	.then(editor => {
		editor.editing.view.change(writer => {
			writer.setStyle('height', '400px', editor.editing.view.document.getRoot());
		});
		editorInstance = editor;
    })
    .catch(error => {
        console.error(error);

	})//create에디터 끝
	
	
	document.querySelectorAll('.viewBtn').forEach(button => {
		button.addEventListener('click', function() {
			const absenceCd = this.getAttribute('data-absenceCd');
			if (!absenceCd) {
				console.error("absenceCd가 없습니다.");
				return;
			}

			// AJAX 요청으로 서버에 absenceCd를 보내고 데이터 받아오기
			fetch(`${contextPath}/lecture/${lectNo.value}/absence/${absenceCd}`)
				.then(response => response.json())
				.then(data => {
					// 서버로부터 받은 데이터를 모달에 업데이트
					document.getElementById('lectOrder2').innerText = data.lectOrder;
					document.getElementById('studentName2').innerText = data.studentVO.nm;
					document.getElementById('studentId2').innerText = data.stuId;
					document.getElementById('absenceStatus2').innerText = data.atst;
					//document.getElementById('absenceCd2').value = absenceCd; 
					
					const fileDetails = data.atchFile.fileDetails
					
					console.log("조회 버튼눌렀을때 :", data)
					console.log("파일 있는데??? :", fileDetails)
					
					//atchFileId
					renderFileDetails(fileDetails)
					
					
					
					//updateInstance.setData(data.absenceResn);
					
					document.getElementById('absenceCd2').value = absenceCd;
					console.log("수정에 들어간 데이터", data);
				})
				.catch(error => {
					console.error('Error:', error);
				});
		});
	});
	
	
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
	
	
	
	
	
	
	
	
	
	
	