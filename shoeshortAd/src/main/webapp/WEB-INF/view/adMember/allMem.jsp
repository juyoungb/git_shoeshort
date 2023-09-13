<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
List<MemberInfo> memberList  = (List<MemberInfo>)request.getAttribute("memberList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int bsize = pageInfo.getBsize();		int cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize();		int pcnt = pageInfo.getPcnt();
int rcnt = pageInfo.getRcnt();
String schtype = pageInfo.getSchtype() , keyword = pageInfo.getKeyword(), args = pageInfo.getArgs()  ,schargs = pageInfo.getSchargs();
%>
<style>
#info td { font-size:1.2em; }
#cnt { width:20px; text-ailgn:right;}
.smt { width:150px; height:30px;}
#review { display:none; }

option {border: 1px solid black;}
.input {
  outline: none; display: inline-block; border:0; width:30px; height:15px; cursor:pointer; text-align:center; 
}

.hand { cursor:pointer; }
#list {border:1px solid black;}
tr, th, td {border:1px solid black;}
th {text-align:center;}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script src="resources/js/popUpJs.js"></script>
<script>
$(function() {
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		//buttonImage: "/images/kr/create/btn_calendar.gif",
		buttonImageOnly: true,
		// showOn :"both",
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		duration:200,
		showAnim:'show',
		showMonthAfterYear: false
		// yearSuffix: '년'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$( "#edusdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#eduedate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});



var preSize = "";
function chgColor(chk,val) {
	var frm = document.frm;
	var size1 = document.getElementById(preSize);
	var size2 = document.getElementById(chk);
	
	if(size2.style.backgroundColor){
		size2.style.backgroundColor="";
	} else{
		size2.style.backgroundColor="#5ACCFF";
	}	
	preSize = chk;	// preSize = 'a'
}
/*
function dateChk(chk) {
	var date = document.getElementById(preSize);
	var date2 = document.getElementById(chk);

	if(date2.style.backgroundColor){
		date2.style.backgroundColor="";
	} else {
		date2.style.backgroundColor="#5ACCFF";
	}
	
	preSize = chk;
}
*/
function makeSch(){
	var frm = document.frm;	var sch = "";
	
	var arr = frm.status;
	var isFirst = true;		
	for (var i = 0; i < arr.length ; i++){	// 특정 버튼의 색이 #5ACCFF이면 값을 넘기는 조건만들기
		if(arr[i].style.backgroundColor != ""){
			if (isFirst){
				if (sch != "")	sch += ",";	// 기존에 검색어가 있을 경우 ','로 구분
				isFirst = false;
				sch += arr[i].id;
			}else {
				sch += "," + arr[i].id;
			}
		}	
	}
	document.frm.statusS.value = sch;
	document.frm.submit();
}

function selectDate(dateId) {
    var frm = document.frm;
    var selectedDate = document.getElementById(dateId);
    var hiddenInput = document.getElementById("chkDate");
    
	// 날짜 선택 버튼이 클릭되면 달력으로 선택한 날짜를 초기화 하기 위한 값들을 받아옴
    var edusdate = document.getElementById("edusdate");
    var eduedate = document.getElementById("eduedate");
    
    // 모든 날짜 버튼의 배경색 초기화
    var dateButtons = document.getElementsByName("date");
    for (var i = 0; i < dateButtons.length; i++) {
        dateButtons[i].style.backgroundColor = "";
    }

    // 선택한 날짜 버튼 강조 표시
    selectedDate.style.backgroundColor = "#5ACCFF";

    // 선택한 날짜 값을 숨겨진 필드에 저장
    hiddenInput.value = dateId;
    
    // 버튼으로 선택한 값을 히든객체에 value값으로 저장 후 달력으로 선택한 날짜 값을 초기화
    edusdate.value = "";
    eduedate.value = "";

}
function calendar() {
    var dateButtons = document.getElementsByName("date");
    var chkDate = document.getElementById("chkDate");
    var edusdate = document.getElementById("edusdate");
    var eduedate = document.getElementById("eduedate");

    if (edusdate.value !== "" || eduedate.value !== "") {
        for (var i = 0; i < dateButtons.length; i++) {
            dateButtons[i].style.backgroundColor = "";
        }
        chkDate.value = "";
    }
}


</script>
</head>
<body>
<div align="center">
<h2 align="left" style="margin-left: 10%;">전체 회원 조회</h2>
<br />
<form name="frm" >
<table align="center" width="80%" height="100px" cellpadding="10" >
<tr>
<th style="text-align:center;">검색어</th>
<td>
	<select name="schtype">
		<option value="total">전체</option>
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="email">이메일</option>
	</select>
	<input type="text" name="keyword" placeholder="검색어 입력" >
</td>
</tr>
<tr>
<th style="text-align:center;">회원상태</th>	
<td>	
	<input type="hidden" name="statusS" id="statusS" value="">			
	<input type="button" name="status" id="allS"  value="전체"  onclick="chgColor('allS');" class="input" style="width:70px; height:25px; readonly="readonly" class="hand">	 	
	<input type="button" name="status" id="a"  value="정상회원"  onclick="chgColor('a');"  class="input" style="width:70px; height:25px;" readonly="readonly" class="hand">	 		 				
	<input type="button" name="status" id="b"  value="탈퇴회원"  onclick="chgColor('b');"  class="input" style="width:70px; height:25px;" readonly="readonly" class="hand">	 		 				
	<input type="button" name="status" id="c"  value="휴면회원"  onclick="chgColor('c');"  class="input" style="width:70px; height:25px;" readonly="readonly" class="hand">	 						
</td>	
</tr>
<tr>
<th style="text-align:center;">날짜</th>
<td>
	<input type="text" name="edusdate" id="edusdate" value="" size="10" class="ipt" onclick="calendar()"> ~
	<input type="text" name="eduedate" id="eduedate" value="" size="10" class="ipt" onclick="calendar()">
	<input type="hidden" name="date" id="chkDate" value="" />
	<input type="button" name="date" id="today" value="오늘" size="10" class="input" style="width:70px; height:25px;" onclick="selectDate('today')" readonly="readonly">
	<input type="button" name="date" id="all" value="전체" size="10" class="input" style="width:70px; height:25px;" onclick="selectDate('all')" readonly="readonly">
	<input type="button" name="date" id="week" value="일주일" size="10" class="input" style="width:70px; height:25px;" onclick="selectDate('week')" readonly="readonly">
	<input type="button" name="date" id="month" value="1개월" size="10" class="input" style="width:70px; height:25px;" onclick="selectDate('month')" readonly="readonly">
</td>
</tr>
</table>
<br />
<div align="center">
	<input type="button" class="btn btn-outline-dark" value="검 색" onclick="makeSch();">&nbsp;&nbsp;
	<input type="button" class="btn btn-outline-dark" value="초기화" onclick="location.href='allMem';">
</div>
</form>
<br><br>
<table width="80%" height="50%" style="text-align:center;">
<tr>
<th>번호</th>
<th>아이디</th>
<th>이름</th>
<th>이메일</th>
<th>전화번호</th>
<th>가입일자</th>
<th>상태</th>
</tr>
<%

if (memberList.size() > 0) {
	int i = rcnt - (psize * (cpage - 1));
	for(MemberInfo mi : memberList) {
		String status = "";
		if (mi.getMi_status().equals("a")) status = "정상회원";
		else if (mi.getMi_status().equals("b")) status = "휴면회원";
		else if (mi.getMi_status().equals("c")) status = "탈퇴회원";
%>
		<tr>
		<td><%=i %></td>
		<td><div onclick="popUp('adMemDetail?miid=<%=mi.getMi_id() %>','','650','350');" style="cursor:pointer;"><%=mi.getMi_id() %></div></td>
		<td align="center"><%=mi.getMi_name() %></td>
		<td align="center"><%=mi.getMi_email() %></td>
		<td><%=mi.getMi_phone() %></td>
		<td><%=mi.getMi_date() %></td>
		<td><%=status %></td>
	</tr>
<%
		i--;
	}
} else { //검색이 잘못되서 목록이 없으면
	out.println("<tr><td colspan='11' align='center'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<br>
<div width="80%" cellpadding="5">
<p>
<span align="center" style="border: none;">
<%
if (rcnt > 0) { //게시글이 있으면 - 페이징 영역을 보여줌
	String link = "allMem?1=1" + schargs + "&cpage=";
	
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + link + "1'>[<<]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + link + (cpage - 1) + "'>[<]</a>&nbsp;&nbsp;&nbsp;");		
	}
	
	int spage = (cpage -1) / bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호
	System.out.println("spage : "+ spage);
	for (int i = 1, j = spage ; i<= bsize && j <=pcnt ; i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함\
	System.out.println(" for spage : "+ spage);
		System.out.println(cpage);
		System.out.println(j);
		if (cpage == j){
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.println("&nbsp;<a href='" + link + j + "'>" + j + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;&nbsp;[>>]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + link + (cpage + 1) +"'>[>]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + link + pcnt + "'>[>>]</a>");
	}
}
%>
</span>
</p>
</div>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>