<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String now = LocalDateTime.now()+"";
List<OrderInfo> orderList  = (List<OrderInfo>)request.getAttribute("orderList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int bsize = pageInfo.getBsize();		int cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize();		int pcnt = pageInfo.getPcnt();
int rcnt = pageInfo.getRcnt();				

String schtype = pageInfo.getSchtype(), args = pageInfo.getArgs()  ,schargs = pageInfo.getSchargs();
String keyword = pageInfo.getKeyword(), sdate =pageInfo.getSdate(), edate=pageInfo.getEdate();
String periods = pageInfo.getPeriods(), status2 = pageInfo.getStatus2();
System.out.println("stauts2"+status2);

%>
<style>
table{
	border: 1px solid #121212;
	width:80%;
}
table th,td{
border: 1px solid #121212;
text-align: center;
padding:5px;
margin:5px;
}
table td{
height:30px;
}
#wrap{
	border:none;
	background: white;
	margin: 20px;
  	padding: 10px;
  	width:100%;
  	heigt:100%;
  	text-align:left;
}
</style>
<script>
function resetBtn(){
	frm = document.frm1;
	frm.reset();
	frm.edate.value="";
	frm.sdate.value="";
	frm.keyword.value="";
	
	var chks =frm.period;
		for(var i=0; i<chks.length; i++){
			chks[i].checked=false;
		}
		chks[chks.length-1].checked=true;
	var chks =frm.status;
		for(var i=0; i<chks.length; i++){
			chks[i].checked=false;
		}
		chks[0].checked=true;
}
</script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<div id="wrap"  style="margin-left:200px;">
<div align="center">

<div align="left">
<table style="border:none;">
<tr><td style="text-align:left; border:none;">&nbsp;
<h2 >주문 조회</h2>
</td>
</tr>
</table>
<br>
<form name="frm1">
<table cellpadding="0" cellspacing="0" id="list" style="width:1300px;" >
<tr><th>검색어</th><td style="text-align:left">&nbsp;
<select name="schtype">
<option value="all">전체</option>
<option value="oi_id">상품번호</option>
<option value="mi_name">이름</option>
</select>
<input type="text" style="width:40%" id="keyword" name="keyword" value="<%=keyword %>"  placeholder="검색어 입력"/></td></tr>
<tr><th>날짜</th><td style="text-align:left">&nbsp;
<script>
<% %>
</script>
<input type="text" style="width:15%" name="sdate" id="sdate" value="<%=sdate %>" size="10" class="ipt" /> -
<input type="text" style="width:15%" name="edate" id="edate" value="<%=edate %>" size="10" class="ipt" />
<div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
  <input type="checkbox" class="btn-check" id="btncheck1" name="period" value="t" <%if(periods != null && periods.contains("t")) {%>checked="checked" <%} %>onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck1">오늘</label>

  <input type="checkbox" class="btn-check" id="btncheck2" name="period" value="w" <%if(periods != null && periods.contains("w")) {%>checked="checked" <%} %> onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck2">일주일</label>

  <input type="checkbox" class="btn-check" id="btncheck3" name="period"  value="m" <%if(periods != null && periods.contains("m")) {%>checked="checked" <%} %> onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck3">1개월</label>
  
  <input type="checkbox" class="btn-check" id="btncheck4" name="period" value="all"<%if(periods == null || periods.contains("all") ) {%>checked="checked" <%} %>" onclick="allChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck4" >전체</label>
</div>
</td></tr>
<tr><th>주문 상태</th><td style="text-align:left">&nbsp;
  
  <input type="checkbox" class="btn-check" name="status" value="all" id="btncheck5"<%if(status2 == null || status2.contains("all")) {%>checked="checked" <%} %>  onclick="allChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck5">전체</label>
  
  <input type="checkbox" class="btn-check" name="status" value="b"  id="btncheck6"<%if(status2 != null && status2.contains("b")) {%>checked="checked" <%} %> onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck6">배송 준비</label>

  <input type="checkbox" class="btn-check" name="status" value="c"  id="btncheck7"<%if(status2 != null && status2.contains("c")) {%>checked="checked" <%} %> onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck7">배송중</label>
  
  <input type="checkbox" class="btn-check" name="status" value="d" id="btncheck8" <%if(status2 != null && status2.contains("d")) {%>checked="checked" <%} %> onclick="selChk(this)" />
  <label class="btn btn-outline-dark" for="btncheck8">배송완료</label>
  	 		 	
</td></tr>
</table>

<span style="line-height:50%"><br></span><!-- <br> 절반 -->
<table style="width:1300px; border:none;">
<tr><td style="border:none;">
<input style="width:10%" type="submit" class="btn btn-outline-dark" value="검색"/>&nbsp;&nbsp;&nbsp;&nbsp;
<input style="width:10%" type="button" class="btn btn-outline-dark" onclick="resetBtn();" value="초기화"/>
</td>
</tr>
</table>
</form>
</div>
<br>
<div align="left">
<form name="frm" action="orderStatus" method="POST">
<table style="width:1300px"  cellpadding="0" cellspacing="0" id="list" class="table table-striped table-hover">
<input type="hidden" name="chk"/>
 <thead>
	<tr>
	<th style ="vertical-align:middle "  scope="col"><input type="checkbox"name="all" id="all" onclick="chkAll(this)" /></th>
	<th scope="col" width="">아이디</th><th scope="col" width="">이름</th>
	<th scope="col" width="">주문번호</th><th scope="col" width="">수량</th>
	<th scope="col" width="">가격</th><th scope="col" width="">구매일</th>
	<th  scope="col"width="">배송상태</th>
	</tr>
 </thead>
 <tbody>
<!-- db -->
<%
if (orderList.size() > 0) {
	int i = 1 ;
	for(OrderInfo oi : orderList) {%>
	
    <tr>
		<tr style="cursor:pointer;"onclick="popUp('orderDetail?oiid=<%=oi.getOi_id()%>&uid=<%=oi.getMi_id()%>','','1000','800');">
		<td  style ="vertical-align : middle " ><input type="checkbox"  name="chk" value="<%=oi.getOi_id() %>" onclick="chkOne(this)" /></td>
		<td><%=oi.getMi_id() %></td><td><%=oi.getMi_name() %></td>
		<td><%=oi.getOi_id() %></td><td><%=oi.getCnt() %></td>
		<td><%=oi.getOi_pay() %></td>
		<td><%=now.substring(0,10).equals(oi.getOi_date().substring(0,10)) ? oi.getOi_date().substring(10) : oi.getOi_date().substring(0,10) %></td>
		<td><%=oi.getOi_status().equals("d")? "배송완료":(oi.getOi_status().equals("c")? "배송중":"배송 준비 중") %></td>		
	</tr>
<%
	}
} else { //검색이 잘못되서 목록이 없으면
	out.println("<tr><td colspan='11' align='center'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</tbody>
<!-- db -->
</table>
<span style="line-height:20%"><br></span><!-- <br> 절반 -->
<table style="width:1300px; border:none;">
<tr>
<td style="text-align:right;border:none;">&nbsp;
<select name="schOrder">
<option value="d">배송완료</option><!-- 1 -->
<option value="c">배송중</option>
<option value="b">배송준비</option>
</select>
<input style="width:60px; height:27px;" type="button" class="btn btn-outline-dark btn-sm" onclick="chkChange()"  value="변경"/>
</td>
</tr>
</table>
<!-- 번호  -->

<table style="width:1300px; border:none;">
<tr>
<td width="600" align="center" style="border:none;">
<%
if (rcnt > 0) { //게시글이 있으면 - 페이징 영역을 보여줌
	String link = "adOrderList?1=1" + schargs + "&cpage=";
	
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
</form>
</div>
</div>
</div>


<%@ include file="../_inc/inc_foot_ad.jsp" %>
