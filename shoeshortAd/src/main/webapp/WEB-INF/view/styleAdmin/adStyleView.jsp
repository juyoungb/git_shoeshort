<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
StyleInfo styleView = (StyleInfo)request.getAttribute("styleView");
ProductInfo styleProduct =(ProductInfo)request.getAttribute("styleProduct");
String date = styleView.getSi_date().substring(0,10);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script>
function styleDel() {
	if (confirm("정말 삭제하시겠습니까?\n삭제된 글은 다시 복구할 수 없습니다.")) {
		location.href = "adStyleProcDel?siidx=<%=styleView.getSi_idx()%>&piid=<%=styleView.getPi_id() %>";
	}
}
function link() {
		location.href = "adStyle";
}

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#styleView { position: absolute; left:25%; top: 5%; align-content: center; width:50%;}
.good {vertical-align:top;}
</style>
</head>
<body>
<div id="styleView">
<h3><%=styleView.getMi_id() %></h3>
<h3><%=date %></h3>
<hr />
<table width="100%" cellpadding="5">
<tr>
<td colspan="3" align="center">
			<img src="resources/img/style_img/<%=styleView.getSi_img() %>" width="500" height="500" border="0" /><br />
	</td> 
</tr>
</table>
<hr />
<table width="100%" align="center">
<tr>
<td align="left">
	<a href="product/product_view" >
	<img src="resources/img/product_img/<%=styleProduct.getPi_img1() %>" width="100" height="100" /></a><br /><%=styleProduct.getPi_name() %><br /><%=styleProduct.getPi_price() %>원
</td>
<td width="70%" align="left"><%=styleView.getSi_content() %></td>
<td width="10%" align="right" class="good">
	<img src="resources/img/style_img/style_good02.png"/>
</td>
<td class="good">
<%=styleView.getSi_good() %>
</td>
</tr>
<tr>
<td align="right" colspan="4">
	<input type="button" width="20" value="목록" class="btn btn-dark" onclick="link();" />
	<input type="button" width="20" value="삭제" class="btn btn-dark"  onclick="styleDel();" />
</td>
</tr>
</table>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>