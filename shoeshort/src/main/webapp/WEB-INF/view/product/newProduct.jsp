<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
request.setCharacterEncoding("utf-8");
List<ProductInfo> productList = (List<ProductInfo>)request.getAttribute("productList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
del { font-size:0.7em; color:#a0a0a0;}
.saleStock { font-size:0.7em; }
#list { width:1200px;}
 body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
  }
</style>
<body>
<div align="center" class="m-5">
<h2 >NEW/신상품</h2>
<div class="album py-5 bg-body-tertiary" >
	<div class="container">
	<div class="row row-cols-4 g-4" style="margin-left:60px">
<%	for(int i = 0 ; i < productList.size(); i++) {
	if (i % 4 == 0) out.println("<tr>");
	ProductInfo pi = productList.get(i);
	String price = pi.getPi_price() + "원";		
	if (pi.getPi_dc() > 0) {  //할인율이 있으면
		price = Math.round(pi.getPi_price() * (1 - pi.getPi_dc()) )+ "원"; //실제 판매가
		price = "<del>" + pi.getPi_price() + "</del>&nbsp;&nbsp;&nbsp;" + price;
	}
%> 		
			<div class="card shadow-sm m-3" style="width:250px; position:relative;" >
	  			<a href="productView?piid=<%=pi.getPi_id()%>">
	    		<img src="resources/img/product/<%=pi.getPi_img1() %>" width="100%" style="height: 15rem;" /></a>
	    	<div class="card-body" style="text-align:left; padding-top: 15px; font-size: 13px; width: 200px; height: 100px;">
	     		<p class="card-text"><%=pi.getPi_name() %></p>
				<p class="card-text" style="font-weight:bold;"><%=price %></p>

	    	</div>
		</div>
	

<%} %>
	</div>
	</div>
</div>
</div>

<%@ include file="../_inc/inc_foot_fr.jsp" %>