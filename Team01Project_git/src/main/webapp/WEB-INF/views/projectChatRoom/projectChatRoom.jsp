<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600&display=swap" rel="stylesheet">

<!-- SockJS CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.2/sockjs.min.js"></script>

<!-- STOMP CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>


<style>
   body {
       background-color: #f9f9f9;
        font-family: 'Nunito', Arial, sans-serif; /* 동글동글한 폰트 적용 */
   }

   .container {
       max-width: 1200px;
       margin: 20px auto;
       display: flex;
       gap: 20px;
   }

   /* 왼쪽 패널 (차트 영역) */
   .left-panel {
       flex: 0.8; /* 크기 조정 */
       background-color: #fff;
       border-radius: 10px;
       box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
       padding: 20px;
       display: flex;
       flex-direction: column;
       gap: 20px;
       height: 600px;
   }

   .chart-container {
         position: relative; /* 텍스트와 차트를 잘 배치하기 위해 position 사용 */
	    width: 100%; /* 부모 컨테이너에 맞춤 */
	    display: flex;
	    flex-direction: column;
	    align-items: center; /* 가운데 정렬 */
	    gap: 10px; /* 차트와 텍스트 사이 간격 */
	    overflow: hidden; /* 차트가 넘어가지 않도록 숨김 */
   }
   
	canvas {
	    width: 100%; /* 너비를 부모 컨테이너에 맞춤 */
	    height: auto; /* 비율 유지 */
	    max-height: 250px; /* 차트의 최대 높이를 설정 */
	    display: block;
	}
   
   .chart-container p {
	    margin: 0; /* 여백 제거 */
	    font-size: 14px; /* 텍스트 크기 */
	    font-weight: bold; /* 텍스트 굵게 */
	    color: #333; /* 텍스트 색상 */
	    text-align: center; /* 텍스트 가운데 정렬 */
	}

   /* 오른쪽 패널 (채팅 영역) */
   .right-panel {
       flex: 1.2; /* 크기 조정 */
       background-color: #fff;
       border-radius: 10px;
       box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
       display: flex;
       flex-direction: column;
       overflow: hidden;
       height: 600px;
   }

   #message-container {
       flex: 1;
       padding: 20px;
       overflow-y: auto; /* 스크롤 활성화 */
       background-color: #f2f2f2;
   }

	 /* 메시지 스타일 */
	 .message {
	    margin-bottom: 15px; /* 메시지 간 간격 */
	}
		 
	 
    .message.sent {
        margin-bottom: 10px;
        display: flex;
        flex-direction: column; /* 세로 정렬 */
    	align-items: flex-end; /* 오른쪽 정렬 */
        justify-content: flex-end; /* 기본 우측 정렬 */
        font-size: 14px; /* 글씨 크기 조정 */
    }
	.sender {
	    font-size: 12px;
	    color: #555;
	    margin-bottom: 5px;
	    font-weight: bold;
	}
	
    .message.received {
    	margin-bottom: 10px;
    	display: flex;
	    flex-direction: column; /* 세로 정렬 */
	    align-items: flex-start; /* 왼쪽 정렬 */
        justify-content: flex-start; /* 받은 메시지는 좌측 정렬 */
    }
	
	.message.sent .bubble {
          max-width: 70%;
          padding: 10px 15px;
          border-radius: 15px;
          font-size: 14px;
          line-height: 1.5;
          background-color: #007bff;
          color: white;
          border-bottom-right-radius: 5px;
      }

      .message.received .bubble {
            max-width: 70%;
		    padding: 10px 15px;
		    border-radius: 15px;
		    font-size: 14px;
		    line-height: 1.5;
		    background-color: #e0e0e0; /* 받은 메시지 배경색 */
		    color: #333;
		    border-bottom-left-radius: 5px;
		    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
      }
	/* 입장 메시지 스타일 */
	.system-message .bubble {
	    background-color: #f0f0f0;
	    color: #666;
	    text-align: center;
	    border-radius: 10px;
	    font-size: 12px;
	    font-weight: bold;
	    padding: 8px;
	}
	/*날짜 구분선*/
	.date-divider {
	    text-align: center;
	    font-size: 12px;
	    color: #888;
	    margin: 20px 0; 
	    font-weight: bold;
	    border-top: 1px solid #ddd;
	    padding-top: 10px;
	}
	
   #chat-input-container {
       display: flex;
       align-items: center;
       padding: 10px;
       background-color: #fff;
       border-top: 1px solid #ddd;
   }

   #message-input {
        flex: 1;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 20px;
        outline: none;
        font-family: 'Nunito', Arial, sans-serif;
   }

   #send-btn {
       margin-left: 10px;
       background-color: #007bff;
       color: white;
       border: none;
       border-radius: 20px;
       padding: 8px 16px;
       cursor: pointer;
       font-family: 'Nunito', Arial, sans-serif;
   }

   #send-btn:hover {
       background-color: #0056b3;
   }
	
   
</style>
<div class="text-center"> &lt; ${projectTeam.teamNm } 채팅방 > </div>
 <div id="mainDiv" class="container" data-lect-no="${lectNo }">
        <!-- 왼쪽 패널 -->
        <div class="left-panel">
            <!-- 멤버 차트 -->
		    <div class="chart-container" style="text-align: center; margin-bottom: 20px; width: 100%;">
		        <canvas  id="memberChart" style="max-width: 100%; height: auto;"></canvas>
		        <p style="margin-top: 10px; font-weight: bold;">일감현황</p>
		    </div>
		    <!-- 프로젝트 차트 -->
		    <div class="chart-container" style="text-align: center; margin-bottom: 20px; width: 100%;">
		        <canvas id="projectChart" style="max-width: 100%; height: auto;"></canvas>
		        <p style="font-weight: bold;">프로젝트진행현황</p>
		    </div>
        </div>

        <!-- 오른쪽 패널 -->
        <div class="right-panel">
        	
            <div id="message-container" data-path="${pageContext.request.contextPath }"
            	 data-task-no="${taskNo }" data-team-cd="${teamCd }" data-user="${pageContext.request.userPrincipal.name }">
                
                
            </div>
            <div id="chat-input-container">
                <input type="text" id="message-input" placeholder="메시지를 입력하세요">
                <button id="send-btn">전송</button>
            </div>
        </div>
    </div>
 
 
<script src="${pageContext.request.contextPath }/resources/js/projectChatRoom/projectChat.js"></script> 