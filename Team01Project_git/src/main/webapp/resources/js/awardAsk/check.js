  // 선택된 값을 표시
  function getCheck(event) {
      document.getElementById('result').innerText = event.target.value;
  }

  // 폼 제출 이벤트 처리
  document.getElementById('createAward').addEventListener('submit', function (e) {
      // 동의 라디오 버튼 값 확인
      const agreement = document.querySelector('input[name="financialInfoAgreement"]:checked');
      if (!agreement || agreement.value !== "동의") {
          // 기본 제출 동작 취소
          e.preventDefault();
          // 경고창 표시
          swal("동의 확인", "동의를 하셔야 신청이 가능합니다.", "warning");
      }
  });