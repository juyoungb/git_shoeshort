<%@page import="org.apache.catalina.startup.SetAllPropertiesRule"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<% 
request.setCharacterEncoding("utf-8");
List<StyleInfo> styleList = (List<StyleInfo>)request.getAttribute("styleList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int rcnt = pageInfo.getRcnt(), cpage = pageInfo.getCpage(), bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2 align="center">STYLE</h2>
<p align="right" style="margin-right: 100px;"><a href="style?cpage=1&ob=a">인기순</a> | <a href="style?cpage=1&ob=b">최신순</p>
<table width="80%" align="center" cellpadding="20px" cellspacing="0px">
<%
int i = 0;
for(i = 0;i < styleList.size();i++) {
	StyleInfo si = styleList.get(i);
	if (i % 4 == 0) out.println("<tr>");
%>
	<td width="25%" align="center" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
		<a href="styleView?siidx=<%=si.getSi_idx() %>&piid=<%=si.getPi_id() %>">
			<img src="resources/img/style_img/<%=si.getSi_img() %>" width="200px" height="300px" border="0" /></a>
			<br /><a href='memStyle?miid=<%=si.getMi_id() %>' ><%=si.getMi_id() %></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="resources/img/style_img/style_good02.png" /><span><%=si.getSi_good() %></span>
			<br /><%=si.getSi_content() %>
	</td> 
<%
	if (i % 4 == 3) out.println("</tr>");
}

if (i % 4 > 0) {
	for (int j = 0; j < (4 - (i % 4)); j++){
		out.println("<td width='25%'></td>");
	}
	out.println("</tr>");
}
%>
</table>
<p align='center'><!-- 페이징 영역을 보여줄 p태그 -->
<%	
String qs = pageInfo.getObargs();

// 페이징영역 링크에서 사용할  쿼리 스트링의 공통 부분(정렬방식)
if (pageInfo.getCpage() == 1){
	out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;");
}else {
	out.println("<a href='style?cpage=1"+ qs +"'>[&lt;&lt;]</a>&nbsp;&nbsp;");
	out.println("<a href='style?cpage="+ (pageInfo.getCpage() - 1) + qs +"'>[&lt;&lt;]</a>&nbsp;&nbsp;");
}

int spage = (pageInfo.getCpage() - 1) / pageInfo.getBsize() * pageInfo.getBsize() + 1; //현재 블록에서의 시작 페이지 번호
int j = 0;
for (i = 1, j = spage ; i <= pageInfo.getBsize() && j <= pageInfo.getPcnt() ; i++, j++){
	// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
	// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
	if (pageInfo.getCpage() == j){
		out.println("&nbsp;<strong>" + j  +"</strong>&nbsp;");
	} else {
		out.println("&nbsp;<a href='style?cpage="+ j + qs +"'>" + j + "</a>&nbsp;");
	}
}

if (pageInfo.getCpage() == pageInfo.getPcnt()) {
	out.println("&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
} else {
	out.println("&nbsp;&nbsp;<a href='style?cpage="+ (pageInfo.getCpage() + 1) + qs +"'&gt;[&gt;]</a>");
	out.println("&nbsp;&nbsp;&nbsp;<a href='style?cpage="+ pageInfo.getPcnt() + qs +"'&gt;[&gt;&gt;]</a>");
}
%>
</p>
</body>
</html>