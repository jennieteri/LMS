/**
 * 
 */
document.addEventListener("DOMContentLoaded", async ()=>{
	const cp = document.querySelector("#contextPath").value;
	const id = document.querySelector("#id").value;
	
	await getMyPageScore(cp,id)
})


async function getMyPageScore(cp,id){
	
	 try {
        const response = await fetch(`${cp}/mypage/score/${id}`)
        const data = await response.json();
//여기선 성적 리스트가 들어있음.
        const myPageLectureList = data.myPageLectureList;
		
		console.log(myPageLectureList)
		if(myPageLectureList){
			await drawingScore(myPageLectureList)
		}
       	
    } catch (error) {
        console.error('Error:', error);
    }
	
}

async function drawingScore(list) {
    const ctx = document.getElementById('myChart').getContext('2d');

    // 학기별 데이터 정리
    const semesterData = {};
    list.forEach(item => {
        const semester = item.lectureVO.semesterVO.label;
        if (!semesterData[semester]) {
            semesterData[semester] = {
                totalScore: 0,
                totalCredits: 0
            };
        }
        semesterData[semester].totalScore += item.attenScore * item.lectureVO.lectScore;
        semesterData[semester].totalCredits += item.lectureVO.lectScore;
    });
console.log(semesterData)

    // 학기별 평균 평점 계산
    const labels = [];
    const values = [];
    Object.entries(semesterData).forEach(([semester, data]) => {
        labels.push(semester);
        values.push((data.totalScore / data.totalCredits).toFixed(2));
    });

  const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,    // 학기
        datasets: [{
            label: '학기별 평균 평점',
            data: values,   // 평균 평점
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                min: 0,
                max: 4.5,    // y축 최대값 설정
                ticks: {
                    stepSize: 0.5
                }
            }
        },
        animations: {
            y: {
                from: 0,        // y축 0에서부터 막대가 올라오도록 설정
                duration: 1500, // 애니메이션 지속 시간
                easing: 'easeOutQuart'  // 부드러운 애니메이션 효과
            }
        }
    }
});




}
