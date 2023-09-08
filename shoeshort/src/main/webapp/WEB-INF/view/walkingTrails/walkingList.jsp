<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
PageInfo pi = (PageInfo)request.getAttribute("pi");
JSONArray features = (JSONArray)request.getAttribute("features");
List<ProductInfo> product = (List<ProductInfo>)request.getAttribute("product");

int rcnt = pi.getRcnt(), cpage = pi.getCpage(), bsize = pi.getBsize(), pcnt = pi.getPcnt(), spage = pi.getSpage(), psize = pi.getPsize(), num = pi.getNum();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#list tr { height:25px;}
#list tr, #list td { padding:8px 3px;}
#list th { border-bottom:double black 3px;}
#list td { border-bottom:dotted black 1px;}
#walkingList { width:50%; left:2%; top: 250px; height:700px;}
.page-link {color:black;}
th {text-align:center;}
a {text-decoration: none; color:black;}
</style>
<script src="resources/js/popUpJs.js"></script>
</head>
<body>
<div id="video" align="center">
    <img
  src="resources/img/walkingTrails.png"
  autoplay muted
  loop
  style="pointer-events: none;	width:90%;  height:400px;  object-fit: fill;" />
</div>
<br />
<div style="display:flex; align:center; margin-left:7%; width:90%;">
	<div id="walkingList">
	<h2>전국 유명 산책로 리스트</h2>
	<br />
	<table width="100%"  cellpadding="0" cellspacing="0" id="list">
	<tr>
	<th width="5%">번호</th><th width="10%">산책로 구분</th><th width="10%">명칭</th><th width="10%">코스번호</th><th width="10%">코스명</th>
	<th width="10%">코스난이도</th><th width="15%">소요시간</th><th width="15%">코스 길이</th>
	</tr>
	<%
	if (features.size() > 0) {
		int i = 0;
		for (i = 0 ; i < features.size() ; i++) {
			JSONObject jo = (JSONObject)features.get(i);
			JSONObject properties = (JSONObject)jo.get("properties");
	%>
	<tr align="center">
	<td><%=num-- %></td>
	<td><%=properties.get("cat_nam") %></td>
	<td onclick="lineMap('<%=properties.get("ag_geom") %>');"><%=properties.get("lnk_nam") %></td>
	<td><%=properties.get("cos_num") %></td>
	<td><%=properties.get("cos_nam") %></td>
	<td><%=properties.get("cos_lvl") %></td>
	<td><%=properties.get("len_tim") %></td>
	<td><%=properties.get("leng_lnk") %></td>
	</tr>
	<% } %>
	</table>
	<br />
	<!-- 페이징 영역 시작 -->
	<table width="100%" align="center" cellpadding="6">
	<tr>
	<td align="center" colspan="7">
	<% 
	if (rcnt > 0) {
		String link = "walkingList?1=1&cpage=";
	%>
		<nav aria-label="Page navigation example" style="text-align:center; width:500px;">
			<ul class="pagination">
	<%
		if (cpage == 1) {
	%>
				<li class="page-item"><a class="page-link" href="#">이전</a></li>
	<%
		} else {
	%>
				<li class="page-item"><a class="page-link" href="<%=link + (cpage - 1)%>">이전</a></li>
	<% 
		}
		int j = 0;
		for (i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++){
			// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
			// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
			if (cpage == j){
	%>
				<li class="page-item"><a class="page-link" href="#"><%=j %></a></li>
	<%
			} else {
	%>			<li class="page-item"><a class="page-link" href="<%=link + j%>"><%=j %></a></li> <%
			}
		}
		if (cpage == pcnt){
	%>
				<li class="page-item"><a class="page-link" href="#">다음</a></li>
	<%
		} else{ 
	%>
				<li class="page-item"><a class="page-link" href="<%=link +(cpage + 1) %>">다음</a></li>
	<%	} %>
			</ul>
		</nav>
	<% } %>
	페이지 크기 : 
	<select name="psize" onchange="location.href='walkingList?psize=' + this.value;">
		<option value="10">10개</option>
		<option value="15">15개</option>
		<option value="20">20개</option>
		<option value="30">30개</option>
		<option value="50">50개</option>
	</select>
	</td>
	</tr>
	<%
		} else {
			out.println("<tr><td colspan='8' align='center'>");
			out.println("데이터가 없습니다.</td></tr>");
		}
	%>
	</table>
	<!-- 페이징 영역 종료 -->
	</div>
	<div style="width:50%;">
	<h2>산책을 위한 신발 추천</h2>
	<br /><br />
	<% for (ProductInfo p : product) {%>
		<div class="card" style="width: 14rem; margin-left:50px; float: left;">
		  <a href="productView?piid=<%=p.getPi_id() %>"><img src="resources\img\product\<%=p.getPi_img1() %>" class="card-img-top" style="width:100%; height:100%;" ></a>
		  <div class="card-body">
		    <p class="card-text"><%=p.getPi_name() %></p>
		    <p class="card-text"><%=p.getPi_price() %></p>
		  </div>
		</div>
	<% } %>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<%@ include file="../_inc/inc_foot_fr.jsp" %>