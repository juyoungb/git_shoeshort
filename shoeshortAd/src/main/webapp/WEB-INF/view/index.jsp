<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="_inc/inc_head_ad.jsp" %>
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

</style>
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
	        	<span id="itemtitle2">주문처리(100일 기준)</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>주문접수</th><td>${td.getOrdCntTotal() }개</td></tr>
				<tr><th>상품준비</th><td>${td.getOrdCnt1() }개</td></tr>
				<tr><th>배송중</th><td>${td.getOrdCnt2() }개</td></tr>
				<tr><th>배송완료</th><td>${td.getOrdCnt3() }개</td></tr>
				</table>
				<br>
				<span id="itemtitle2">상품현황</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>전체 상품 수 </th><td>${td.getPs()}개</td></tr>
				<tr><th>판매중</th><td>${td.getPsOn()}개</td></tr>
				<tr><th>판매중지</th><td>${td.getPsOff()}개</td></tr>
				<tr><th>품절</th><td>${td.getPsSold()}개</td></tr>
				</table>
				</div>
	        <div class="flex-item" align="center">
	        	<br>
	        	<span id="itemtitle2">스타일 월드컵</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>투표기간</th><td>D-10</td></tr>
				<tr><th>참여자</th><td>40명</td></tr>
				<tr><th>모집기간</th><td>D-7</td></tr>
				<tr><th>지원자</th><td>3명</td></tr>
				</table>
				<br>
				<span id="itemtitle2">행운의 비밀번호 찾기</span>
	        	<table style="width:300px;text-align:center; margin:5px 7px; padding:10px;" border="1">
				<tr><th>마감일</th><td>D-DAY</td></tr>
				<tr><th>참여자</th><td>20명</td></tr>
				<tr><th>당첨자</th><td>X</td></tr>
				</table>
	        </div>
	        <div class="flex-item">
	        	<span id="itemtitle">이 주의 추천 신발</span><a href="" style="padding-left: 50px;"><img src="resources/img/icon/more.png"></a>
	        	<img src="resources/img/product/NN10003_pd.png" width="290px" style="margin:10px">
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
				    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false">방문 수</button>
				  </li>
				</ul>
				<div class="tab-content" id="myTabContent">
				  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
				  <div id="chart_div2"></div>
				  </div>
				  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
					<div id="chart_div3"></div>
				  </div>
				  <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				  	<div id="chart_div4"></div>
				  </div>
			</div>
		</div>
	        <div class="flex-item2">
	        	<div>
				<span id="itemtitle">공지사항</span>
				<a href="" style="padding-left: 450px;"><img src="resources/img/icon/more.png"></a>
<!-- 				<img style="position:absolute; left: 1250px; top: 640px;" src="resources/img/icon/more.png"> -->
				</div>
				<table>
				<c:forEach var="nl" items="${td.getNoticeInfo()}"><!-- ${nl.getNl_idx()}  -->
				<tr><td><a href="" style="text-decoration-line:none; color:black;">[${nl.getNl_ctgr()}] ${nl.getNl_title()}	</a></td></tr>
				</c:forEach>
				</table>

			</div>
	</div>
</div>
<script type="text/javascript">

// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
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
                 'width':280,
                 'height':280,
                 is3D: true,
                 legend:'none'};

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
  chart.draw(data, options);
}
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawBackgroundColor);

function drawBackgroundColor() {
      var data = new google.visualization.DataTable();
      data.addColumn('number', 'X');
      data.addColumn('number', 'Dogs');

      data.addRows([
    	  /*[i, ]  */
        [0, 0],   [1, 40],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
        [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
        [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
        [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
        [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
        [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
        [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
        [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
        [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
        [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
        [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
        [66, 70], [67, 72], [68, 75], [69, 80]
      ]);

      var options = {
    		  'width':600,
              'height':200,
              legend:'none',
        backgroundColor: 'white'
      };

      var chart = new google.visualization.LineChart(document.getElementById('chart_div2'));
      chart.draw(data, options);
    }
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawBackgroundColor1);
function drawBackgroundColor1() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Dogs');

    data.addRows([
  	  
      [0, 0],   [1, 40],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
      [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
      [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
      [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
      [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
      [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
      [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
      [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
      [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
      [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
      [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
      [66, 70], [67, 72], [68, 75], [69, 80]
    ]);

    var options = {
  		  'width':600,
            'height':200,
            legend:'none',
      backgroundColor: 'white'
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div3'));
    chart.draw(data, options);
  }
google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawBackgroundColor2);
function drawBackgroundColor2() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Dogs');

    data.addRows([
  	  
      [0, 0],   [1, 40],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
      [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
      [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
      [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
      [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
      [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
      [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
      [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
      [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
      [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
      [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
      [66, 70], [67, 72], [68, 75], [69, 80]
    ]);

    var options = {
  		  'width':600,
            'height':200,
            legend:'none',
      backgroundColor: 'white'
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div4'));
    chart.draw(data, options);
  }
</script>
<%@ include file="_inc/inc_foot_ad.jsp" %>