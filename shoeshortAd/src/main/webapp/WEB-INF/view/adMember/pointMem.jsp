<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<%
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
List<MemberPoint> pointList = (List<MemberPoint>)request.getAttribute("pointList");
int bsize = pageInfo.getBsize();		int cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize();		int pcnt = pageInfo.getPcnt();
int rcnt = pageInfo.getRcnt();
String schtype = pageInfo.getSchtype() , keyword = pageInfo.getKeyword(), args = pageInfo.getArgs()  ,schargs = pageInfo.getSchargs();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
td, th {border:1px solid black;}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="resources/js/popUpJs.js"></script>
<style>
a {text-decoration: none; color:black;}
</style>
</head>
<body>
<div align="center" style="position: absolute; left: 200px; top: 100px; width: 80%; ">
<h2 align="left">포인트 관리</h2>
<br />
<table width="100%" cellpadding="5">
<tr><td align="right" style="border: none;">
	<form name="frmSch" method="get">
	<fieldset>
		<select name="schtype">
			<option value="allP" <% if(schtype.equals("allP")){ %>selected="selected"<% } %>>전체</option>
			<option value="id" <% if(schtype.equals("id")){ %>selected="selected"<% } %>>아이디</option>
			<option value="name" <% if(schtype.equals("name")){ %>selected="selected"<% } %>>이름</option>
		</select>
		<input type="text" name="keyword" value="<%=keyword %>">
		<input type="submit" class="btn btn-dark" value="검색">
		<input type="button" class="btn btn-dark" value="전체" onclick="location.href='pointMem'">
	</fieldset>
	</form>
</td></tr>
</table>
<br /> 
<table width="100%" height="60%"  cellpadding="0" cellspacing="0" id="list" style="text-align:center; " >
<tr>
<th width="10%" align="left">번호</th><th width="10%">아이디</th><th width="10%">이름</th><th width="10%">사용처</th>
<th width="15%">상세내역</th><th width="15%">발행일자</th><th width="15%">유효기간</th><th width="10%">포인트</th><th width="10%">적립</th>
</tr>
<%
if (pointList.size() > 0) {
	int num = rcnt - (psize * (cpage - 1));
	System.out.println(num);
	for (MemberPoint mp : pointList) {
		String su = "";
		if (mp.getMp_su().equals("s")) su = "적립";
		else su = "사용";
%>	
<tr onmouseover="this.bgColor ='#efefef'" onmouseout="this.bgColor =''">
<td><%=num %></td><td><%=mp.getMi_id() %></td>
<td><%=mp.getMi_name() %></td><td><%=su %></td>
<td><%=mp.getMp_desc() %></td><td><%=mp.getMp_sdate() %></td>
<td><%=mp.getMp_edate() %></td><td><%=mp.getMp_point() %>P</td>
<td><input type="button" class="btn btn-outline-dark" onclick="popUp('pointForm?miid=<%=mp.getMi_id() %>','','500','300');" value="적립"/></td></tr>
<%
		num--;
	}

} else if (pointList.size() > 10){
	
} else { //게시글 목록이 없으면
	out.println("<tr><td colspan='5' align='center'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}

%>
</table>
<br>
<table width="100%" cellpadding="5">
<tr>
<td width="600" align="center" style="border: none;">
<%
if (rcnt > 0) { //게시글이 있으면 - 페이징 영역을 보여줌
	String link = "pointMem?1=1" + schargs + "&cpage=";
	
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + link + "1'>[<<]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + link + (cpage - 1) + "&schtype="+ schtype +"&keyword="+ keyword +"'>[<]</a>&nbsp;&nbsp;&nbsp;");		
	}
	int spage = (cpage -1) / bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호
	for (int i = 1, j = spage ; i<= bsize && j <=pcnt ; i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (cpage == j){
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.println("&nbsp;<a href='" + link + j + "&schtype="+ schtype +"&keyword="+ keyword +"'>" + j + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;&nbsp;[>>]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + link + (cpage + 1) +"'>[>]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + link + pcnt + "&schtype="+ schtype +"&keyword="+ keyword +"'>[>>]</a>");
	}
}
%>
</td>
</tr>
</table>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>