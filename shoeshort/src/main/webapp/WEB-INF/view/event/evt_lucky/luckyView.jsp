<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet">
<title>Bootstrap Example</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script>
// 종료시간을 구함
var time = "${edate}";
const endDate = new Date(time);

function updateRemainingTime() {
  const currentDate = new Date();
  const timeDifference = endDate - currentDate;

  if (timeDifference > 0) {
    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);
	// 숫자가 한자리수 일 경우도 있어서 한자리수 이면 0을 붙여줌
    document.getElementById('days').textContent = days < 10 ? '0' + days : days;
    document.getElementById('hours').textContent = hours < 10 ? '0' + hours : hours;
    document.getElementById('minutes').textContent = minutes < 10 ? '0' + minutes : minutes;
    document.getElementById('seconds').textContent = seconds < 10 ? '0' + seconds : seconds;
  } else {
    document.getElementById('days').textContent = '0';
    document.getElementById('hours').textContent = '0';
    document.getElementById('minutes').textContent = '0';
    document.getElementById('seconds').textContent = '0';
  }
}

setInterval(updateRemainingTime, 1000);
updateRemainingTime();

</script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
function clickBtn(idx) {
	
    var frm = document.frm;
    var price = frm.price.value;
    
	var min = "${minPrice}";
	var max = "${maxPrice}";
	if( price > min  && price < max) {
		 document.getElementsByName("price").value ="";
		return alert(min + "보다 크고 " + max +"작아야합니다.");
	}	
	$.ajax({
		type: "POST",
        url: "luckProcIn",
        data: {"elidx": idx, "price": price},
        success: function(chkRs){
            if (chkRs == 0) {
               	alert("로그인이 필요합니다.");
               	location.href = "login";
            } else if(chkRs > 0){
               	alert("참여 완료!"); 
            } else {
           		alert("이미 참여하셨습니다.");
           }
       }
   });
   
    //초기화시킴
    price ="";
}

</script>
<style>
#main {   
	 margin-left: auto;
    margin-right: auto;
    max-width: 1280px;
    overflow: hidden;
    padding: 30px 40px 120px
    }
#button {
    font-size: 18px;
    font-weight: 700;
    height: 40px;
    letter-spacing: -.09px;
    width:100%
}       
.count {
	border:1px solid black;
}
 body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
  }
  
  #countdown {

    padding: 20px;
    display: inline-block;
  }
  
  span {
    font-size: 24px;
    margin: 0 5px;
    padding: 5px 10px;
    background-color: #333;
    color: #fff;
    border-radius: 5px;
  }
    .time-section {
    display: inline-block;
    margin: 0 5px;
    padding: 10px;
    background-color: #333;
    color: #fff;
    border-radius: 5px;
  }
  
  .time-label {
    margin-top: 5px;
    font-size: 14px;
    color: #555;
  }
  
</style>

</head>
<body>

<c:forEach var="el" items="${evtList }" >  
<div id="main" class="grid gap-2">
	<div class="p-2 g-col-6">
    	<img src="resources/img/product/${el.getPi_img1() }" width="90%" height="500px">
  	</div>

  	<div class="g-col-5">
   		<strong class="product-title">EVENT</strong>
    	<div>
     		<p class="product-description">${el.getPi_name() }</p>
			<div id="countdown" style="text-align:center">
			  <div class="time-section">
			    <span id="days"></span>
			    <div class="time-label">일</div>
			  </div> :
			  <div class="time-section">
			    <span id="hours"></span>
			    <div class="time-label">시간</div>
			  </div> :
			  <div class="time-section">
			    <span id="minutes"></span>
			    <div class="time-label">분</div>
			  </div>  :
			  <div class="time-section">
			    <span id="seconds"></span>
			    <div class="time-label">초</div>
			  </div>
			</div>
			<form name="frm">
      		<input type="number" placeholder="000,000,000" name="price" class="form-control mx-auto" width="400px"><br><br>
    		<div class="d-grid gap-2 col-6 mx-auto">
      			<input class="btn btn-dark" value="응모하기" id="button" onclick="clickBtn('${el.getEl_idx()}')"></input><br>   			
    		</div>
    		</form>
    		<hr>
    		<strong>응모 기간</strong>        
    		<p style="font-size:14px"> ${el.getSdate()} ~ ${el.getEdate()}</p><hr>
    		<strong>응모가격</strong>
    		<p style="font-size:14px"> ${el.getEl_min_price() }원 ~ ${el.getEl_max_price()}원</p>
    	</div>
  	</div>
</c:forEach>
  	<div class="p-2 g-col-12">
  		<hr>
   		<div padding="10px" margin="10px">
  			<strong>유의 사항</strong>
	  		<ul>
			    <li> 본 이벤트는 <strong>shoeshort</strong> 본인 인증 회원 대상 전용 이벤트입니다.</li>
			    <li>광고성 정보 수신 설정(앱푸시, 문자, 메일)에 모두 동의해야 응모 참여 가능하며, 응모하기 버튼을 한 번 더 클릭해야 정상 참여됩니다.</li>
			    <li>기존 광고성 정보 수신 모두 동의된 경우, 바로 구매 응모 화면으로 이동됩니다.</li>
			    <li>응모 이벤트 참여 완료는 마이페이지  구매입찰에서 확인 가능합니다.</li>
			    <li>당첨자에 한하여 거래 체결 관련 개별 메시지를 발송 드리며, 미당첨자에게는 별도의 연락을 드리지 않습니다.</li>
			    <li>응모 시 등록한 결제정보로 결제가 진행되지 않으면 당첨이 즉시 취소되며, 후 순위 당첨자로 변경될 수 있습니다.</li>
			    <li>당첨자에게 서류 요청 후, 72시간 내에 회신이 없으면 당첨이 자동 취소되며, 재추첨은 진행되지 않습니다.</li>
			    <li>제세공과금은 <strong>shoeshort</strong>에서 부담하며, 제세공과금 처리를 위해 가입하신 이메일 주소로 신분증 등 서류를 요청드립니다.</li>
			    <li>회원정보가 일치하지 않거나 부당한 방법으로 응모한 고객의 경우 당첨이 자동 취소되며, 및 추후 이벤트 응모 시 불이익을 받을 수 있습니다.</li>
			    <li>경품은 랜덤으로 지급되며, 교환 및 환불이 불가능합니다.</li>
			    <li>일부 제품은 출시 연기 및 수급 상황에 따라 배송이 늦어질 수 있습니다.</li>
  			</ul>
		</div>
</div> 

</body>
</html>

<%@ include file="../../_inc/inc_foot_fr.jsp" %>