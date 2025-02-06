/**
 * 
 */
document.addEventListener('DOMContentLoaded', function(){
	axios.get(`${postScriptCP.value}/lecture/my`)
		.then(({data})=>{
			data.forEach(lect=>{
				$('#study-nav').prepend(`<li>
			            <a href="${postScriptCP.value }/lecture/${lect.lectNo}/materials">
			              <i class="bi bi-circle"></i><span>${lect.lectNm}</span>
			            </a>
			          </li>`);
			})
		})
		.catch(err=>{
			console.log("로그인 안하면 강의 목록이 보일리가 없지요")
		});
})