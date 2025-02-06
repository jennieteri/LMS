/**
 * 
 */

 	 const url = "../../prop/person"; //원본은 props 로 시작
	 const personBody = document.querySelector("#personBody")
	 const names = document.querySelectorAll("name")
	 const selDiv = document.getElementById("selDiv")
	 const selForm = document.getElementById("selForm")
	 const newPersonForm = document.getElementById("newPersonForm")

	 const newBtn = document.getElementById("newBtn")
	 const udBtn = document.getElementById("udBtn")
	 const com = document.getElementById("com")
	 const textDiv = document.getElementById("textDiv")
 document.addEventListener("DOMContentLoaded",(e)=>{
	 
	 // 1. 실행하자 마자 리스트 가져오기
	  listUp();
	 
	
	 // 2. 하나 선택하면 div에 회원 정보 출력하며 input type text로 출력하기
	 personBody.addEventListener("click",(e)=>{
		 const uid = e.target.dataset['uid']
//		 console.log(uid)
		peopleUp(uid)
	 })
	
	
	 // 3. 값 수정하고 수정버튼을 누르면 수정하기 
	selForm.addEventListener("submit",async(e)=>{
		e.preventDefault()
		console.log(e.target)
		 const formData = new FormData(selForm);
		 const jsonData = {};
		  formData.forEach((value, key) => {
			  console.log(key)
		    jsonData[key] = value;
		  });
		
		
		const res= await fetch(url,{
			method:'put',
			headers:{
				  'Content-Type': 'application/json'
			},
			body:JSON.stringify(jsonData)
		})
		const da =await res.json()
		pd = JSON.parse(da);
		personBodyClean();
		listUp();
		selDivClean();
		textDiv.innerHTML=pd['update'];
	})//수정이벤트 끝
	
	//입력폼에 입력하고 전송하면 저장하고 리스트 새로 불러오기
	newPersonForm.addEventListener("submit",async(e)=>{
		e.preventDefault();
		console.log(e.target)
		const formData = new FormData(newPersonForm);
		
		console.log(formData)
		const jsonData={};
		formData.forEach((value,key)=>{
			jsonData[key]=value
		});
		console.log(jsonData);
		
	await fetch(url,{
			method:'post',
			headers:{
				'content-type':'application/json'
			},
			body:JSON.stringify(jsonData)
		}).then(resp=>resp.json())
		.then(data=>{
		pd=JSON.parse(data)
		textDiv.innerHTML=pd['create'];
		personBodyClean();
		listUp();
		e.target.reset();
		})
	})//업데이트 끝
 })//dom 긑 
	
	 // 4. 선택한 상태에서 삭제버튼 누르면 삭제하기
async function deleteUp(){
	 const selId = document.getElementById("selId")
	 const delBtn = document.getElementById("delBtn")
	 var delPer = selId.value;
	 
	 const obj = {id:delPer}
	 const da = JSON.stringify(obj);
	 
	 console.log(obj)
	 console.log(da)
	 const resp = await fetch((url),{
		 method:'delete',
		 Headers:{
			 'content-type':'application/json'
		 },
		  body:da
	 })
	 

	const data = await resp.json();
	const pd = await JSON.parse(data)
	textDiv.innerHTML=pd['delete'];
	
	personBodyClean();
	listUp();
	selDivClean();
	
}
 	
 //리스트 가져오기
function listUp(){
	 personBodyClean();
	 
	 fetch(url)
	 .then(resp=>resp.json())
	 .then(list=>{
		 list.forEach(({id,name,gender,age,address})=>{
			//i 가 인덱스 v가 밸류 v, i 순으로 혹은 구조분해
			//이 가져온것들을 tbody 아래에 집어넣기
			code = `<tr>
	<td value="${id}" name="id""><a href="javascript:void(0)"  data-uid="${id}">${id}     </a></td>
	<td value="${name}" name="name" >${name}   </td>
	<td value="${gender}" name="gender" >${gender} </td>
	<td value="${age}" name="age" >${age}    </td>
	<td value="${address}" name="address">${address}</td>
					</tr>`
		
		personBody.insertAdjacentHTML("beforeend",code)
		 })
	 })
 }
 //하나 선택하면 가져오긴
 async function peopleUp(uid){
	 const selectUrl=`${url}?who=${uid}`
	 
	 console.log(selectUrl)
	 var code=``;
	 
	const response = await fetch(selectUrl);
  	const { id, name, gender, age, address } = await response.json();
  	const ad = address;
	code=`
		<table border="1">
			<thead>
			<tr>
				<th>계정</th>
				<th>이름</th>
				<th>성별</th>
				<th>나이</th>
				<th>주소</th>
			</tr>
			</thead>	
			<tbody>		
				<tr>
					<th> <input type="text" id="selId" name="id" value="${id}" ></th>
					<th> <input type="text" name="name" value="${name}" ></th>
					<th> <input type="text" name="gender" value="${gender}" ></th>
					<th> <input type="text" name="age" value="${age}" > </th>
					<th><textarea rows="20" cols="20" name="address" >${ad}</textarea><th>
				</tr>
			</tbody>		
		</table>
		<button type="submit" id="upBtn"> 수정하기 </button>
		<button type="button" id="delBtn" onclick="deleteUp()"> 삭제하기 </button>
		<div id="com"><div>`;

		 selDiv.innerHTML=code;
 }
 
 //테이블 바디 비우기
 function personBodyClean(){
	 const personBody = document.querySelector("#personBody")
	 personBody.replaceChildren();
 }
 //선택창 비우기
 function selDivClean(){
	 const selDiv = document.querySelector("#selDiv")
	 selDiv.replaceChildren();
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 