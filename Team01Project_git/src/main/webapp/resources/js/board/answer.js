 // 추가된 것 JSON 형태로 만들기 
            function sendFams(){
                let fams = famList.querySelectorAll("#cjFam");
                let famsArr=[]; // 빈 배열
                for (let i =0; i<fams.length; i++){
                    fam = fams[i];
                    let famOne  = {
                        rel:fam.querySelector("#cjRel").value,
                        name:fam.querySelector("#cjName").value
                    }
                    famsArr.push(famOne);
                }
                console.log("체크:", famsArr)
            }

             function revFam(pBtn){
                pBtn.closest("#cjFam").remove();
             }
                const famList = document.querySelector("#famList");
                const cjFam = document.querySelector("#cjFam") // 프로토 타입 (원본 틀, 템플릿) 으로 쓸거임
                // script 로 html 문자열 더하기를 많이 하는데.
                // 상황에 따라선 html 템플릿 패턴을 사용하는 것이 훨씬 유리 
                // html 문자열 더하기만 난무하는 건 관리나 재활용 측면에서 그리 좋지 않으니 
                //html 템플릿 패턴도 생각해서 자주 써보도록 합시다. (프로그램 실력이 잘 늘어남)

                function addFam(){
	
	cjFam.style.display = "block"
                   //let famOne = cjFam.cloneNode(true); // 요렇게만 쓰면 껍데기만 복사 
                    //famOne.style.display = "block" // 복사한 거 눈에 보이겡
                   
				//famOne.removeAttribute("id");
				//famOne.classList.add("fam-item");
				
				
				 //famList.appendChild(famOne); //추가
                }

    const reply = document.querySelector("#reply")
	function plus(pBtn){
	
	    let replySpan = document.createElement("span");
        replySpan.innerHTML = cjFam.querySelector("#cjName").value;
        reply.appendChild(replySpan);  
		cjFam.style.display = "none"
	
	}
				
