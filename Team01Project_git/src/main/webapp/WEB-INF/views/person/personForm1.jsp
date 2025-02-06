<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #cjFam {
        border: 1px solid pink;
        margin-bottom: 10px;
    }
</style>
</head>
<body>

<!-- 버튼 영역 -->

<button onclick="addFam()">추가</button>

<!-- 가족 리스트 출력 영역 -->
<div id="famList"></div>

<!-- 가족 정보 템플릿 -->

<div id="cjFam" style="display: none">

     <div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">@</span>
  <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
</div>
   <div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">@</span>
  <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
</div>
    <button onclick="revFam(this)">삭제</button>
</div>

 <!-- 가족 정보 템플릿 --> 
<!-- <div id="cjFam" style="display: none"> -->
<!--     <select id="cjRel"> -->
<!--         <option value="0">관계</option> -->
<!--         <option value="1">부</option> -->
<!--         <option value="2">모</option> -->
<!--         <option value="3">형제</option> -->
<!--     </select><br> -->
<!--     이름: <input type="text" id="cjName"><br> -->
<!--     나이: <input type="text" id="cjAge"><br> -->
<!--     <button onclick="revFam(this)">삭제</button> -->
<!-- </div> -->

<script>
  // 가족 데이터를 JSON 형태로 생성
  function sendFams() {
      let fams = famList.querySelectorAll("#cjFam");
      let famsArr = []; // 가족 객체를 담을 배열

      for (let i = 0; i < fams.length; i++) {
          fam = fams[i];
          let famOne = {
              rel: fam.querySelector("#cjRel").value,
              name: fam.querySelector("#cjName").value,
              age: fam.querySelector("#cjAge").value - 0
          };
          famsArr.push(famOne);
      }

      console.log("체크:", famsArr);
  }  

  // 가족 정보 삭제
  function revFam(pBtn) {
      pBtn.closest("#cjFam").remove();
  }

  // 가족 등록
  const famList = document.querySelector("#famList");
  const cjFam = document.querySelector("#cjFam"); // 템플릿 원본

  function addFam() {
      let famOne = cjFam.cloneNode(true);  // 템플릿 복사
      famOne.style.display = "block";  // 복사한 템플릿을 보이게 설정
      famList.appendChild(famOne);   // 리스트에 추가
  }  
</script>
<button onclick="sendFams()">전송</button><br>
</body>
</html>
