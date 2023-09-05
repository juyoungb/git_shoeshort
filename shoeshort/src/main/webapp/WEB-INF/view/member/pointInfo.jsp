<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>


<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>

<%
request.setCharacterEncoding("utf-8");
List<PointDetail> pointList =(List<PointDetail>)request.getAttribute("pointList");

%>
<style>
table{
	width:100%;
	border: 1px solid #444444;
}
table th,td{
	border: 1px solid #444444;
	text-align:left;
	}
</style>
<br>
<h2>포인트 내역</h2>
<div align="left">
<div style="width:1000px; height:100px; border-radius:8px; outline: solid 1px black;">
<div style="margin:20px;">사용가능한 포인트  <%=loginInfo.getMi_point() %>
소멸예정 포인트  <%=loginInfo.getMi_point()/10 %>
</div>
<br>
</div>
<span>포인트 유효기간은 적립일로부터 최대 1년까지이며, 유형에 따라 달라질 수 있습니다.</span>
</div>
<br><br>

<table class="table" >
<tr>
<th>상세 내역</th>
<th>만료기간</th>
<th>적립/사용</th>
<%
for(PointDetail p:pointList){
%>
  
<tr>
<td><%=p.getMp_desc()%></td> 
<td><%=p.getMp_sdate()%>~<%=p.getMp_edate()%></td> 
<td><%=p.getMp_point()%>p[<%=(p.getMp_su().equals("s") ? "적립":"사용") %>]</td>
</tr> 
<%}%>
</table>
</div>
</div>
<%@ include file="../_inc/inc_foot_fr.jsp" %>