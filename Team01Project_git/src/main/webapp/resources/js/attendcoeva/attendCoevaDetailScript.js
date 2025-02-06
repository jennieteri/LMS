/**
 * 
 */


const profeId = document.querySelector("#profeId").value ;
const contextPath = document.querySelector("#contextPath").value ;
const baseUrl = `${contextPath}/attendCoeva`;


document.addEventListener("DOMContentLoaded", async ()=>{
	//console.log(profeId)	
	//console.log(contextPath)	
	
	await getProfeLectureList();
	
	
})

async function getProfeLectureList(){
	
	const resp = await fetch(`${baseUrl}/${profeId}`)
	const data = await resp.json();
	const list = await data.lectList
	
	//console.log(list)
	removeNullProperties(list)
	//console.log(list)
	
	const selectElement = document.querySelector('.form-select');
		selectElement.innerHTML = '';

		list.forEach(semester => {
			const option = document.createElement('option');
			option.value = semester.semstrNo;
			option.textContent = `${semester.year}-${semester.semester}`;
			selectElement.appendChild(option);
		});
		
		const semBox = document.getElementById('semBox');
		if (semBox) {
			semBox.addEventListener('change', async (e) => {
				const semVal = e.target.value;
				await getProfeAttenCoevaList(semVal);
			});
			semBox.dispatchEvent(new Event('change'));
		}

		return list;
	
}

async function getProfeAttenCoevaList(semVal){
	try {
		const response = await fetch(`${baseUrl}/profe/${profeId}/${semVal}`);
		const data = await response.json();
		const selectProfeCoevaList = data.selectProfeCoevaList;

		//console.log(selectProfeCoevaList);

		// null 값을 제거한 데이터 생성
		const notNullList = removeNullProperties(selectProfeCoevaList);
		//console.log(notNullList);

		// 테이블의 <tbody>를 선택
		const tableBody = document.querySelector('#tbody');
		tableBody.innerHTML = ''; // 기존 내용 초기화
		selectProfeCoevaList.forEach((list, index) => {
			
		const row = `
			<input type="hidden" id="lectNo" name="lectNo" value="${list.lectureVO.lectNo}">
        <tr onclick="test('${list.lectureVO.lectNo}','${list.lectureVO.semstrNo}')">
            <td class="text-center"  style=" border-right: 1px solid #dee2e6;">${list.lectureVO.semesterVO.year || ''}</td>
            <td class="text-center" style=" border-right: 1px solid #dee2e6;">${list.lectureVO.semesterVO.semester || ''}</td>
            <td class="text-center" style=" border-right: 1px solid #dee2e6;">${list.lectureVO.lectNm || ''}</td>
		</tr>
`
		tableBody.insertAdjacentHTML('beforeend', row);
		})
	} catch (error) {
		console.error('Error:', error);
	}
}

async function test(lectNo, semstrNo) {
    try {
        const response = await fetch(`${baseUrl}/profe/${profeId}/${lectNo}/${semstrNo}`);
        const data = await response.json();
        const selectProfeCoevaDetail = data.selectProfeCoevaDetail;

        // Null 값 제거
        const notNullList = removeNullProperties(selectProfeCoevaDetail);

        // 평가 목록을 업데이트
        const evalBox = document.querySelector('#evalBox');
        evalBox.innerHTML = ''; // 기존 내용 초기화

        notNullList.forEach((list, index) => {
            const row = `
                <div class="card mb-3" style="border: 1px solid #d3d3d3; border-radius: 6px; transition: all 0.2s ease;">
                    <div class="card-body" style="padding: 15px;">
                        <h6 class="card-subtitle mb-2 text-muted" style="font-weight: 600;">학생 ${index + 1}:</h6>
                        <p class="card-text" style="color: #212529; margin-bottom: 0;">${list.attenCoeva}</p>
                    </div>
                </div>
            `;
            evalBox.insertAdjacentHTML('beforeend', row);
        });

    } catch (error) {
        console.error('Error:', error);
    }
}




function removeNullProperties(obj) {
	if (typeof obj !== 'object' || obj === null) {
		return obj;
	}

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












