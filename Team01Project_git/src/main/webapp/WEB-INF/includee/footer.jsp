<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Bitter:ital,wght@0,100..900;1,100..900&family=Playfair+Display:ital,wght@0,400..900;1,400..900&display=swap" rel="stylesheet">

<style>
    .copyright {
        background-color: #0033A0;
        padding: 10px;
        position: fixed; 
        bottom: 0; /* 화면 하단에 위치 */
        left: 0; /* 좌측부터 시작 */
        width: 100%; /* 전체 폭 사용 */
        box-sizing: border-box;
        z-index: 999;
    }

    .copyright .logo {
        text-align: right;
        color: white;
    }
    
    #copyright {
        font-family: "Bitter", serif;
        text-align: right;
        color: white;
    }
    .location-info {
        font-family: "Bitter", serif; /* 원하는 폰트 설정 */
        font-size: 12px; /* 적절한 폰트 크기 */
        color: white;
        cursor: pointer;
    }
</style>
<div class="copyright" style="background-color: #00247D; padding: 10px; display: flex; justify-content: space-between; align-items: center;">
    <div id="map" class="fade-transition hidden" style="width:500px;height:400px;position:absolute;top:-400px;left:0;z-index:1000;"></div>
    <span style="color: white; cursor: pointer;" class="location-info"
          onmouseover="showMap()" 
          onmouseout="hideMap()" 
          onclick="location.href='https://map.kakao.com/link/map/36.3250, 127.4089'">
        <strong>(34908) 대전광역시 중구 계룡로 846 &nbsp; 연근대학교</strong>
    </span>
    <div id="copyright" style="color: white;">
        Copyright &copy; <strong><span style="color: white;">1999 YONGUEN UNIVERSITY</span></strong>. All Rights Reserved
    </div>
</div>

<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=209dad58f3cfdbdfd873671aa88ab859"></script>
<script type="text/javascript">
let map = null;
let marker = null;

kakao.maps.load(function() {
    var mapContainer = document.getElementById('map');
    var mapOption = { 
        center: new kakao.maps.LatLng(36.3250, 127.4089),
        level: 2
    };

    // 지도를 생성합니다
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커가 표시될 위치입니다 
    var markerPosition = new kakao.maps.LatLng(36.3250, 127.4089); 

    // 마커를 생성합니다
    marker = new kakao.maps.Marker({
        position: markerPosition
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);1
});

function showMap() {
    document.getElementById('map').classList.remove('hidden');
    document.getElementById('map').classList.add('visible');
    if(map) {
        map.relayout();
        // 마커 위치를 지도 중심으로 설정
        map.setCenter(new kakao.maps.LatLng(36.3250, 127.4089));
    }
}

function hideMap() {
    document.getElementById('map').classList.remove('visible');
    document.getElementById('map').classList.add('hidden');
}


</script>
