/**
 * 
 */
document.addEventListener("DOMContentLoaded",async ()=>{
	const lectNo = document.getElementById('lectNo').value;
	const dataElement = document.getElementById('contextData');
	const contextPath = dataElement.dataset.contextPath;
	const url = `${contextPath}/lectureCart/${lectNo}`;
	//console.log(lectNo)
	
	await showLecturePaper(url,lectNo);
})

async function showLecturePaper(url,lectNo) {

	console.log(lectNo)
    fetch(url)
	.then(resp=>resp.json())
	.then(data=>{
		d1 = removeNullProperties(data.lectVo);
		//console.log(d1)
	})
	.catch(err=>console.log(err.status))


}

// null 값을 제거하는 재귀 함수
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