<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="_inc/inc_head_ad.jsp" %>
<%IndexData td = (IndexData)request.getAttribute("td"); %>
<br>
<style>
body{
background:#e9e9e9;
}
#tb1{
	background-color:white;
	width:1300px;
	height: 180px;
	font-size:24px;
	font-weight:400;
	margin: 10px 20px;
	border-radius: 10px;
}
#tb1 td{
text-align:left;
}
#num{
font-size:34px;
font-weight:600;
}
#tb1 img{
	width:70%;
}
 .flex-container{
            display: flex;
        }
 .flex-item{
     width: 315px;
     height: 315px;
     margin:5px;
     background-color: white;
     border-radius: 10px;
 }
 .flex-item2{
     width: 630px;
     height: 315px;
     margin:10px;
     background-color: white;
     border-radius: 10px 5px;
 }
  .flex-item2 table td{
	padding: 10px;
  }
#itemtitle{
	font-size:20px;
	margin:10px 20px;
	font-weight:bold;
}
#itemtitle2{
	font-size:16px;
	margin:20px 20px;
	
}
.google-visualization-legend {
    font-size: 20px; /* 원하는 폰트 크기로 조절하세요. */
    /* 다른 스타일 속성을 여기에 추가할 수 있습니다. */
  }
</style>
<div style="margin-left:50px;">
<table id="tb1">
<tr>
<td width="10%" style="text-align:center"> 
	<img src="resources/img/icon/cart.png" />
</td>
<td width="13%">   
		오늘 주문 접수<br>  
		<span id="num">${td.getTdOrderCnt() }</span>건
</td>
<td width="10%" style="text-align:center"> 
<img src="resources/img/icon/member.png"/>
</td> 
<td width="10%">  
		오늘 가입수<br>
		<span id="num">${td.getTodayMem() }</span>명		
</td>
<td width="10%" style="text-align:center"> 
<img src="resources/img/icon/money.png"/>
</td>
<td width="20%">  
		이번 달 매출<br>
		<span id="num"><fmt:formatNumber value="${td.getSalesMonth()}" pattern="#,###"/></span>KRW	
</td>
<td width="10%">  
	총 회원<br>
	<span id="num">${td.getAllMem()}</span>명	
</td>
<td width="*">  
	누적 포인트<br>
	<span id="num"><fmt:formatNumber value="${td.getAcmltPoint()}" pattern="#,###"/></span>P	
</td>
</tr>
</table>
<div style="margin:10px 20px;">
	<div class="flex-container">
	        <div class="flex-item" align="center">
	        <div id="chart_div"></div></div>
	        <div class="flex-item" align="center">
	        	<br>
	        	<span id="itemtitle">주문처리(30일 기준)</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>주문 접수</th><td>${td.getOrdCntTotal() }개</td></tr>
				<tr><th>상품 준비</th><td>${td.getOrdCnt1() }개</td></tr>
				<tr><th>배송 중</th><td>${td.getOrdCnt2() }개</td></tr>
				<tr><th>배송 완료</th><td>${td.getOrdCnt3() }개</td></tr>
				</table>
				<span id="itemtitle">상품현황</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>전체 재고량</th><td>${td.getPs()}개</td></tr>
				<tr><th>전체 상품 수 </th><td>${td.getPcnt()}개</td></tr>
				<tr><th>판매 중</th><td>${td.getSale()}개</td></tr>
				<tr><th>판매 중지</th><td>${td.getUnsale()}개</td></tr>
				</table>
				</div>
	        <div class="flex-item" align="center">
	        	<br>
	        	<span id="itemtitle">스타일 월드컵</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>투표 기간</th><td style="font-weight:bold"><%=(td.getVe() > 0 ? "D-"+td.getVe():(td.getVe() == 0 ? "D-DAY":"마감")) %></td></tr>
				<tr><th>참여자</th><td>${td.getPrt() }명</td></tr>
				<tr><th>모집 기간</th><td style="font-weight:bold"><%=(td.getCe() > 0 ? "D-"+td.getCe():(td.getCe() == 0 ? "D-DAY":"마감")) %></td></tr>
				<tr><th>지원자</th><td>${td.getVol() }명</td></tr>
				</table>
				<br>
				<span id="itemtitle">행운의 숫자 찾기</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>마감일</th><td style="font-weight:bold"><%=(td.getLuckyDiff() > 0 ? "D-"+td.getLuckyDiff():(td.getLuckyDiff() == 0 ? "D-DAY":"마감")) %></td></tr>
				<tr><th>참여자</th><td>${td.getLuckyMem()}명</td></tr>
				</table>
	        </div>
	        <div class="flex-item" align="center">
	        <br>
	        	<span id="itemtitle">판매량 1위 </span>
	        	<!-- <a href="" style="padding-left: 50px;"><img src="resources/img/icon/more.png"></a> -->
	        	<img src="resources/img/product/${td.getPi_img1()}" width="250px" height="200px;" style="border-radius:10px;" /><br>
	        	<span style="font-weight:600;">< ${td.getPi_name()} > </span>
			</div>
	</div>
</div>
<div style="margin:10px 20px;">
	<div class="flex-container">
	        <div class="flex-item2">
				<ul class="nav nav-tabs" id="myTab" role="tablist">
				  <li class="nav-item" role="presentation">
				    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button" role="tab" aria-controls="home" aria-selected="true">매출액</button>
				  </li>
				  <li class="nav-item" role="presentation">
				    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">가입 수</button>
				  </li>
				  <li class="nav-item" role="presentation">
				    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false">주문 수</button>
				  </li>
				</ul>
				<div class="tab-content" id="myTabContent" style="margin-top:30px;">
				  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab" align="center">
				    <div id="chart_div2"></div>
				  </div>
				  <div class="tab-pane fade active" id="profile" role="tabpanel" aria-labelledby="profile-tab">
					<div id="chart_div3"></div>
				  </div>
				  <div class="tab-pane fade active" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				  	<div id="chart_div4"></div>
				  </div>
			</div>
		</div>
	        <div class="flex-item2">
	        	<div>
				<span id="itemtitle">공지사항</span>
				<a href="adNoticeList" style="padding-left: 450px;"><img src="resources/img/icon/more.png"></a>
<!-- 				<img style="position:absolute; left: 1250px; top: 640px;" src="resources/img/icon/more.png"> -->
				</div>
				<table>
				<c:forEach var="nl" items="${td.getNoticeInfo()}"><!-- ${nl.getNl_idx()}  -->
				<tr><td><a href="adNoticeView?nlidx=${nl.getNl_idx() }" style="text-decoration-line:none; color:black;">[${nl.getNl_ctgr()}] ${nl.getNl_title()}	</a></td></tr>
				</c:forEach>
				</table>

			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Topping');
  data.addColumn('number', 'Slices');
  data.addRows([
    ['men', ${td.getMaleCnt()}],
    ['women', ${td.getAllMem()-td.getMaleCnt()}],
  ]);

  // Set chart options
  var options = {
    'width': 350,
    'height': 350,
    is3D: true,
    legend: 'top',
    backgroundColor: 'transparent' // 배경을 투명하게 설정
  };
  var legendStyles = document.createElement('style');
  legendStyles.innerHTML = '.google-visualization-legend { font-size: 20px; }';
  document.head.appendChild(legendStyles);
  
  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
  chart.draw(data, options);
  
}
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawChart1);

function drawChart1() {
	  var data = new google.visualization.DataTable();
	  data.addColumn('string', 'Day'); // 요일을 문자열로 추가
	  data.addColumn('number', '매출액');

	  data.addRows([
		  <%for(ChartData sale: td.getSalesChart()) {%>
		  ['<%=(sale.getDate().substring(5, 10)).replace("-", ".")%>', <%=sale.getVal()%>],
	    <%}%>
	    // 나머지 요일에 대한 데이터를 추가하세요.
	  ]);

	  var options = {
	    'width': 600,
	    'height': 200,
	    legend: 'none',
	    backgroundColor: 'white'
	  };

	  var chart = new google.visualization.LineChart(document.getElementById('chart_div2'));
	  chart.draw(data, options);
	}

google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawChart2);

function drawChart2() {
	  var data = new google.visualization.DataTable();
	  data.addColumn('string', 'Day'); // 요일을 문자열로 추가
	  data.addColumn('number', '개수');

	  data.addRows([
		  <%for(ChartData sale: td.getMemChart()) {%>
		  ['<%=(sale.getDate().substring(5, 10)).replace("-", ".")%>', <%=sale.getVal()%>],
	    <%}%>
	    // 나머지 요일에 대한 데이터를 추가하세요.
	  ]);

	  var options = {
	    'width': 600,
	    'height': 200,
	    legend: 'none',
	    backgroundColor: 'white'
	  };

	  var chart = new google.visualization.LineChart(document.getElementById('chart_div3'));
	  chart.draw(data, options);
	}
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawChart3);

function drawChart3() {
	  var data = new google.visualization.DataTable();
	  data.addColumn('string', 'Day'); // 요일을 문자열로 추가
	  data.addColumn('number', '개수');

	  data.addRows([
		  <%for(ChartData sale: td.getOrdChart()) {%>
		  ['<%=(sale.getDate().substring(5, 10)).replace("-", ".") %>', <%=sale.getVal() %>],
	    <%}%>
	    // 나머지 요일에 대한 데이터를 추가하세요.
	  ]);

	  var options = {
	    'width': 600,
	    'height': 200,
	    legend: 'none',
	    backgroundColor: 'white'
	  };

	  var chart = new google.visualization.LineChart(document.getElementById('chart_div4'));
	  chart.draw(data, options);
	}

</script>
<%@ include file="_inc/inc_foot_ad.jsp" %>