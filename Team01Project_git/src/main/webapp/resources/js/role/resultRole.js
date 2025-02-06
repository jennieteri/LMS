/**
 * 
 */
function filter (role, filters){
	
	const url = `${contextPath}/role/personFilter`;
	
	fetch(url, {
		method :"POST",
		headers : {
			"Content-type" :"application/json",
			
		},
		body :JSON.stringify({
			role:role,
			...filter,
		}),
		
	})
	
	.then((response) =>{
		if(!response.ok){
			
			throw new Error ("데이터를 가져오는데 실패");
		}
		return response.json(); 
		
	})
	.then((data)=>{
		updateResultTable(data);
	})
	.catch((error) =>{
		console.error("Error:", error);
	});

}

function updateResultTable(data){
	const resultSection = document.getElementById("resultSection");
	const resultTableBody = document.getElementById("result");
	
	resultTableBody.innerHTML = "";
	
	if(data.length === 0){
		resultTableBody.innerHTML = `<tr><td colspan="3">결과가 없습니다.</td></tr>`;
		
	}else {
		
		data.forEach((item) =>{
			const row = document.createElement("tr");
			
			row.innerHTML =	`
			<td>${item.nm || "N/A" }</td>
			<td>${item.gradeCd || item.empDept  || item.deptNo || "N/A" }</td>
			<td>${item.deptCd  || item.deptNo || "N/A" }</td>
			`;
			resultTableBody.appendChild(row)
		})
		
	}
	
	resultSection.style.display = "block"; // 결과 
	
}