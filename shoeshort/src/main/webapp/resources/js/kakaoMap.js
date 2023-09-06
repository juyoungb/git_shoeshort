var mapObj;		// 파일 전체에서 사용하기 위해 전역변수로 선언

function showMap(){
	var mapBox = document.getElementById('map');
	var mapOpt = {
		center: new kakao.maps.LatLng(33.450701, 126.570667),
		// 위도와 경도로 지도의 중심지표값
		level:3	// 지도의 크기 레벨로 최대 확대(1) 부터 최대 축소(14)까지 설정가능
		// 현재 level값 추출  : getLevel() / 새로운 level 지정 : setLevel(lvl)
	};
	mapObj = new kakao.maps.Map(mapBox, mapOpt);	// 지도 객체 생성
	
	// 지도 컨트롤 추가
	var zoomCtrl = new kakao.maps.ZoomControl();
	mapObj.addControl(zoomCtrl, kakao.maps.ControlPosition.RIGHT);
	// 지도 확대 및 축소 제어 줌 컨트롤 추가 및 위치 지정
	
	var mapCtrl = new kakao.maps.MapTypeControl();
	mapObj.addControl(mapCtrl, kakao.maps.ControlPosition.TOPRIGHT);
	// 일반 지도와 스카이뷰로 타입 전환이 가능한 컨트롤 추가 및 컨트롤 위치 지정
}
function zoom (val){
	var level = mapObj.getLevel();	// getLevel(), setLevel()은 Kakao 지도 API에 내장된 함수
		mapObj.setLevel(eval(level + val));
	
	var linePath = [
	    new kakao.maps.LatLng(1.4186453592181152E7,4173524.9195949174),
	    new kakao.maps.LatLng(1.4227023309903907E7,4226462.296340855)
	    // new kakao.maps.LatLng(33.45178067090639, 126.5726886938753) 
	];

	// 지도에 표시할 선을 생성합니다
	var polyline = new kakao.maps.Polyline({
	    path: linePath, // 선을 구성하는 좌표배열 입니다
	    strokeWeight: 5, // 선의 두께 입니다
	    strokeColor: '#FFAE00', // 선의 색깔입니다
	    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	    strokeStyle: 'solid' // 선의 스타일입니다
	});
		// 지도에 선을 표시합니다 
		polyline.setMap(mapObj);
}
function setDraggable(chk){
	mapObj.setDraggable(chk);
}

function addr2Geo(addr, bsiname){
	var geocoder = new kakao.maps.services.Geocoder();

	// 주소를 좌표로 변경해주는 객체 생성
	geocoder.addressSearch(addr, function(result, status){
		if (status == kakao.maps.services.Status.OK){
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			var marker = new kakao.maps.Marker({
				map: mapObj, position: coords
			});	
			// 결과값을 받은 위치를 마커로 생성
			var infowindow = new kakao.maps.InfoWindow({
				content: "<div id='infoWindow'>"+ bsiname +"</div>"
			});
			infowindow.open(mapObj, marker);
			
			mapObj.setCenter(coords);
		}
	});
}