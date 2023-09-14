<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
List<ProductInfo> productList  = (List<ProductInfo>)request.getAttribute("productList");
List<ProductCtgr> ctgrList = (List<ProductCtgr>)request.getAttribute("ctgrList");
List<ProductBrand> brandList   = (List<ProductBrand>)request.getAttribute("brandList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

String schtype = pageInfo.getSchtype() , keyword = pageInfo.getKeyword(), args = pageInfo.getArgs();

// vo패키지에서 키워드 가져올려고함
RcmdSearch rcchk = new RcmdSearch();
String gender = rcchk.getGender(), season = rcchk.getRc_season(), age = rcchk.getRc_age(), rc_keyword = rcchk.getRc_keyword();
	// 성별,							//계절							//나이					//키워드
String ctgr ="", isview ="", date ="";
//카테고리
String [] arrGender = gender.split(",");
String [] arrSeason = season.split(",");
String [] arrAge= age.split(",");
String [] arrKeyword = rc_keyword.split(",");

String sch = pageInfo.getSch();

	System.out.println("adproduct sch: " + sch);
	System.out.println("adproduct args: " + args);
if (args != null && !args.equals("")) {
   String[] arrSch = args.split(",");
   for (int i = 0; i < arrSch.length; i++){

	System.out.println(arrSch[i]); 
	  char c = arrSch[i].charAt(0);
      if (c == 'g')   gender = arrSch[i].substring(1);
      else if (c == 'c')   ctgr = arrSch[i].substring(1);  //카테고리
      else if (c == 's')   season = arrSch[i].substring(1); // 계절
      else if (c == 'i')   keyword = arrSch[i].substring(1);  //게시여부
      else if (c == 'a')   age = arrSch[i].substring(1);	//나이
      else if (c == 'k')   keyword = arrSch[i].substring(1); // 수영장,졸업식,...
      else if (c == 'd')   date = arrSch[i].substring(1); // 날짜
   }
}
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
 th, td {border:1px solid black;}
.bnt { width:70px; height:25px; border:0; readonly:readonly; cursor:pointer; text-align:center}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script src="resources/js/popUpJs.js"></script>
<script>

function makeSch() { // 검색폼의 조건을 쿼리스트링 sch의 값으로 만듬 
    var frm = document.frm2;   var sch = "";
	
    
    var arr1 = frm.gender;   //성별
    var isFirst = true;   
    for (var i = 0; i < arr1.length ; i++){
       if(arr1[i].style.backgroundColor){
          if (isFirst){   // 첫번째로 선택한 체크박스이면
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             sch += "g"+ arr1[i].value;
          }else {
             sch += ":" + arr1[i].value;
          }
       }
    }
    
    var arr2 = frm.ctgr;   //카테고리
    var isFirst = true;   
    for (var i = 0; i < arr2.length ; i++){
       if(arr2[i].style.backgroundColor){
          if (isFirst){   // 첫번째로 선택한 체크박스이면
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             sch += "c"+ arr2[i].value;
          }else {
             sch += ":" + arr2[i].value;
          }
       }
    }
    
 	
	if(edusdate.value != "" || eduedate.value != "") {// 날짜가 선택될 시 (오늘, 일주일, 1개월 )무시하고 검색 조건에 추가
		var isFirst = true; 
	    if (isFirst){
	    	 if (sch != "")   sch += ",";   
	    	 isFirst = false;
	    	 if (edusdate.value < eduedate.value) { // 종료일이 시작일 보다 커야 true
	 			sch += "d" + edusdate.value + ":" + eduedate.value;  
	 	  	}else{
		  		alert("시작일과 종료일을 확인해 주세요.");
		  		return false;
		  	}
	    } 	
	} else { // 오늘,일주일,1개월을 클릭할 경우(날짜 무시)
    	var arr3 = frm.date;   //날짜
    	var isFirst = true;   
		for (var i = 0; i < arr3.length ; i++){
      		if(arr3[i].style.backgroundColor){
          		if (isFirst){   // 첫번째로 선택한 체크박스이면
             		if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             		isFirst = false;
             		if (arr3[i].value !="전체") {	          
	             		var Nowdate = new Date(); //현재 날짜 시간
	             		var nowYear = Nowdate.getFullYear();	 //현재 년 
	             		var tmp = Nowdate.getMonth() + 1;	 //현재 달
		             	var month = dateChg(tmp);
		             	var curDay = Nowdate.getDate();
		             	var today = nowYear + "-" + month + "-" + dateChg(curDay);
		             	if(arr3[i].value == "오늘"){            	 
		            	 	arr3[i].value = today;	
	             		} else if (arr3[i].value == "일주일") {
	            	 		arr3[i].value =  + nowYear + "-" + month + "-" + dateChg(curDay -7) +":"+  today ;
	             		} else if (arr3[i].value == "1개월") {
	            	 		arr3[i].value =  nowYear + "-" + dateChg(month - 1) + "-" + dateChg(curDay + 1) + ":" + today ;
	            		}		             	
	             	}
             		sch += "d"+ arr3[i].value;
				} else {
            	sch += ":" + arr3[i].value;
          		}
       		}
		}
	}
	
	
    var arr4 = frm.isview;   //게시여부
    var isFirst = true;   
    for (var i = 0; i < arr4.length ; i++){
       if(arr4[i].checked){
          if (isFirst){
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             // 판매/미판매로 가져올때 'y'와 'n'으로 변경하려고 만듬
             if(arr4[i].value= "판매")    	 	 arr4[i].value = "y";
             else if(arr4[i].value ="미판매")		 arr4[i].value = "n";
             sch += "i"+ arr4[i].value;
          }else {
             sch += ":" + arr4[i].value;
          }
       }
    }
    
    var arr5 = frm.age;   //추천검색어 나이
    var isFirst = true;   
    for (var i = 0; i < arr5.length ; i++){
       if(arr5[i].checked){
          if (isFirst){   
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             sch += "a"+ arr5[i].value;
          }else {
             sch += ":" + arr5[i].value;
          }
       }
    }
    
    var arr6 = frm.season; //추천검색어 계절
    var isFirst = true;   
    for (var i = 0; i < arr6.length ; i++){
       if(arr6[i].checked){
          if (isFirst){   
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             sch += "s"+ arr6[i].value;
          }else {
             sch += ":" + arr6[i].value;
          }
       }
    }

    var arr7 = frm.rc_keyword;
    var isFirst = true;      
    for (var i = 0; i < arr7.length ; i++){
       if(arr7[i].checked){
          if (isFirst){
             if (sch != "")   sch += ",";   // 기존에 검색어가 있을 경우 ','로 구분
             isFirst = false;
             sch += "k"+ arr7[i].value;
          }else {
             sch += ":" + arr7[i].value;
          }
       }
    }
    
    //검색어와 검색조건을 가져감
    var schtype = frm.schtype.value;
    var keyword = frm.keyword.value.trim();  //사용자가 입력한 값이므로 trim()을 해줌(장난을 쳤을 수도 있으므로)
    document.frm.schtype.value = schtype;
    document.frm.keyword.value = keyword;
    document.frm.sch.value = sch;
    document.frm.submit();      // 검색조건들을 가져가기 위해서 submit시킴
 }
 
function dateChg(curDay) {
	var data = curDay >= 10 ? curDay : "0" + curDay;    
	return data;
}

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

var preObj = "";
function chgColor(chk, allNm) {
	var obj = document.getElementById(chk);
	var name = document.getElementById(allNm + "_all");
	if(obj.style.backgroundColor){
		obj.style.backgroundColor="";
		name.style.backgroundColor="#5ACCFF";
	} else {
		obj.style.backgroundColor="#5ACCFF";
		name.style.backgroundColor="";
	}
	preObj = chk;
	
} 


function allchgColor(name) {
	var all = document.getElementsByName(name);
	var name = document.getElementById(name + "_all");
	
	for(var i =0; i < all.length; i++) {
		if(all[i].style.backgroundColor) {
			all[i].style.backgroundColor ="";
		}
	}
	name.style.backgroundColor ="#5ACCFF";
}



var preSize = "";
function dateColor(click) {
	var all = document.getElementsByName(click.name);
	var name = document.getElementById(click.id);
	
	for(var i =0; i < all.length; i++) {
		if(all[i].style.backgroundColor) {
			all[i].style.backgroundColor ="";
		}
	}
	name.style.backgroundColor ="#5ACCFF";
	
}

function productIsview(piid, type) {
	if (confirm(piid + "상품을 " + type +"로 변경하시겠습니까?")) {
		location.href = "adProducIsview?piid="+ piid + "&type="+type;
	}
}

function chkBox(name, checked) {
    var chks = document.getElementsByName(name);
    var allChk = document.getElementById(name + "_all");
    
    for (var i = 0; i < chks.length; i++) { 	
        if (chks[i] != checked) {
        	allChk.checked = false;
        } 
        if (chks[0] == checked) {
        	chks[i].checked = false;
        	allChk.checked = true;
        }
    }
}

function selectAll(click)  {
	var all = document.getElementsByName(click);
	
	if(all.style.backgroundColor){
		all.style.backgroundColor="";
	} else {
		all.style.backgroundColor="#5ACCFF";
	}
	
 
}



</script>
</head>
<body>
<div align="center">
<h2 align="left" style="margin-left:11%">상품 조회</h2>
<form name="frm">
<input type="hidden" name="sch" value="">
<input type="hidden" name="schtype" value="">
<input type="hidden" name="keyword" value="">
</form>

<div style="margin:20px;">
<table  text-align="center" width="80%" cellpadding="5" cellspacing="0" id="list" margin="10">
<tr>
<form name="frm2">
<th>검색어</th>
<td>
	<select name="schtype">
		<option value="">전체</option>
		<option value="pi_id">상품아이디</option>
		<option value="pi_name">상품명</option>
		<option value="pb_name">브랜드명</option>
	</select>
	<input type="text" name="keyword" placeholder="검색어 입력" >
</td>
</tr>
<tr>
<th>성별</th>
<td>
	<input type="button" name="gender" id="gender_all"  value="전체"  onclick="allchgColor('gender');" style="background-color: #5ACCFF;"
	 readonly="readonly" class="bnt">
<%
for(String st : arrGender){
	
%>	
	<input type="button" name="gender" id="<%=st %>"  value="<%=st %>" onclick="chgColor('<%=st %>','gender');" class="bnt">
<%} %>	
 </td>	
</tr>
<tr>
<th>카테고리</th>
<td>
	<input type="button" name="ctgr" id="ctgr_all"  value="전체"  onclick="allchgColor('ctgr');" style="background-color: #5ACCFF;" readonly="readonly" class="bnt">
<% for (ProductCtgr pc : ctgrList) {%>
	<input type="button" name="ctgr" id="<%=pc.getPcb_name() %>"  value="<%=pc.getPcb_name() %>"  onclick="chgColor('<%=pc.getPcb_name() %>','ctgr');"  readonly="readonly" class="bnt">

<%} %>
	
</td>
</tr>
<tr>
<th>날짜</th>
<td>
	<input type="text" name="edusdate" id="edusdate" value="" size="10" class="ipt" > ~
	<input type="text" name="eduedate" id="eduedate" value="" size="10" class="ipt" >
	<input type="button" name="date" id="date_all" value="전체" size="10" onclick="dateColor(this)" style="background-color: #5ACCFF;" class="bnt">
	<input type="button" name="date" id="today" value="오늘" size="10" onclick="dateColor(this)" class="bnt">
	<input type="button" name="date" id="week" value="일주일" size="10" onclick="dateColor(this)" class="bnt">
	<input type="button" name="date" id="month" value="1개월" size="10" onclick="dateColor(this)" class="bnt">
</td>
</tr>
<th>게시여부</th>
<td rowspan="1">
	<input type="checkbox" name="isview" id="isview_all" value="전체" size="10" checked="checked" onclick="chkBox('isview', this);"><label for="y">전체</label> 
	<input type="checkbox" name="isview" id="y" value="판매" size="10" onclick="chkBox('isview', this);"><label for="y">판매</label>
	<input type="checkbox" name="isview" id="n" value="미판매" size="10" onclick="chkBox('isview', this);"><label for="n">미판매</label>
</td>
<tr>
<th>추천검색</th>
<td>
	나이 : <input type="checkbox" name="age" id="age_all"  value="전체"  onclick="chkBox('age', this);"  checked="checked">
	<label for="age_all">전체</label>
<%
for (String st : arrAge) {
%>
    <input type="checkbox" name="age" value="<%=st %>" id="<%=st %>" onclick="chkBox('age', this)">
    <label for="<%=st %>"><%=st %></label>
<%}%>
 <br><br>
	계절 : <input type="checkbox" name="season" id="season_all"  value="전체"  onclick="chkBox('season',this);"  checked="checked">
	<label for="season_all">전체</label>
<%
for (String st : arrSeason) {
%>
    <input type="checkbox" name="season" value="<%=st %>" id="<%=st %>"  onclick="chkBox('season', this)">
    <label for="<%=st %>"><%=st %></label>
<%}%>
<br><br>
	키워드 : <input type="checkbox" name="rc_keyword" id="rc_keyword_all"  value="전체"  onclick="chkBox('rc_keyword',this);"  checked="checked">
	<label for="rc_keyword_all">전체</label>
<%
for (String st : arrKeyword) {
%>
    <input type="checkbox" name="rc_keyword" value="<%=st %>" id="<%=st %>" onclick="chkBox('rc_keyword', this)">
    <label for="<%=st %>"><%=st %></label>
<%}%> 
<br>

</td>
</tr>
<tr style="border: 0;">
<td colspan="2" align="center" style="border: 0;">
	<input type="button" value="검 색"  class="btn btn-dark" onclick="makeSch();">
	<input type="button" value="초기화" class="btn btn-secondary"  onclick="location.href='adProductList';">
</td>
</tr>
</form>
</table>
</div>
<div style="margin:20px;" align="center">
<table cellpadding="5" cellspacing="0"  width="80%" text-align="center">
<tr>
<th>번호</th>
<th>상품코드</th>
<th>상품코드</th>
<th>상품명</th>
<th>브랜드 명</th>
<th>카테고리</th>
<th>발매가</th>
<th>재고량</th>
<th>등록일</th>
<th>수정일</th>
<th>노출여부</th>
<th>게시/미게시</th>
</tr>
<%
if (productList.size() > 0) {
	int i = 1;
	for(ProductInfo pi : productList) {%>
		<tr onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
		<td><%=i %></td>
		<td><%=pi.getPi_img1() %></td>
		<td align="center"> <a href="" onclick="popUp('adProductStockList?piid=<%=pi.getPi_id() %>','','550','500');" ><%=pi.getPi_id() %></a></td>
		<td align="center"><%=pi.getPi_name() %></td>
		<td align="center"><%=pi.getPb_name() %></td>
		<td><%=pi.getPcb_name() %></td>
		<td><%=pi.getPi_cost() %></td>
		<td><%=pi.getStock() %></td>
		<td><%=pi.getPi_date() %></td>
		<td><%=pi.getPi_last() %></td>
		<td><%=pi.getPi_isview() %></td>
		<td align="center">
		<%
		if(pi.getPi_isview().equals("y")){%>
			<input type="button" width="15px" value="미게시"  class="btn btn-secondary btn-sm" onclick="productIsview('<%=pi.getPi_id()%>', '미게시');">
		<%} else{ %>
			<input type="button" value="게시" class="btn btn-dark btn-sm" onclick="productIsview('<%=pi.getPi_id()%>', '게시');">
			
		<%} %>					
	</td>
	</tr>
<%
		i++;
	}
} 

if (productList.size() > 0) {

	String link = "adProductList?1=1" + args + "&cpage=";
	out.println("<tr><td colspan='12' align='center'>");
	
	if (pageInfo.getCpage() == 1) {
		out.println("[<<]&nbsp;&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	}else{
		out.println("<a href='adProductList?cpage=1'>[<<]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='adProductList?cpage=1" + (pageInfo.getCpage() - 1) + "'>[<]</a>&nbsp;&nbsp;&nbsp;");		
	}
	
	
	
	for (int i =1, j =  pageInfo.getSpage() ; i<= pageInfo.getBsize() && j <= pageInfo.getPcnt(); i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함\
		if (i == pageInfo.getCpage())
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		else 
			out.println("&nbsp;<a href='adProductList?cpage=" +  j + "'>" + j + "</a>&nbsp;");
		
	}
	
	if (pageInfo.getCpage() == pageInfo.getPcnt()) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;&nbsp;[>>]");
	}else{
		out.println("&nbsp;&nbsp;<a href='adProductList?cpage=" + (pageInfo.getCpage() + 1) +"'>[>]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='adProductList?cpage="  + pageInfo.getPcnt() + "'>[>>]</a>");
	}
	out.println("</td></tr>");
} else {
	out.println("검색된 상품이 없습니다.");
}
%>
</table>
</div>
</div>
</body>
</html>