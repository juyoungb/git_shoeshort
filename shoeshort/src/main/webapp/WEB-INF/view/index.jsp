<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/inc_head_fr.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.json.simple.*"%>
<%@ page import="org.json.simple.parser.*"%>
<%
request.setCharacterEncoding("utf-8");
JSONArray itemList = (JSONArray)request.getAttribute("itemList");

%>
<link rel="stylesheet" href="resources/css/indexStyle.css">

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<style>
#id {
	border: 1px solid black;
}
</style>
<div id="video" align="center" style="background-color:black;">
    <video
  src="resources/video/${videoInfo.getMm_media() }"
  autoplay muted
  loop
  style="pointer-events: none;	width:1300px;  	height:400px;  	object-fit: fill;"></video>
</div>
<br>
<div align="center">
<div style="width:1300px" align="left"><hr><span style="font-size:18px; font-weight:bold;">이주의 추천 신발</span></div>
	<div style="width:1300px;height:300px;">
		<div id="app" class="container" style="width:100%; ">
		 <div class="row justify-content-center">
    <div class="col-8 p-5">
		<table style="width:100%; ">
		<tr>
			<c:forEach items="${strArray }" var="i">
				<td>${i}</td>
			</c:forEach>
		</tr>
		<%
		
if(itemList.size() > 0){
	for(int i = 0; i<itemList.size(); i++) {
		JSONObject jo = (JSONObject)itemList.get(i);
%>	
	
	<tr>
	<td><input type="hidden" v-model="src1" value="<%=jo.get("wf3Am") %>" ><p v-html="icon(src1)"></p></td>
	<td><input type="hidden" v-model="src2" value="<%=jo.get("wf4Am") %>"><p v-html="icon(src2)"></p></td>
	<td><input type="hidden" v-model="src3" value="<%=jo.get("wf5Am") %>"><p v-html="icon(src3)"></p></td>
	<td><input type="hidden" v-model="src4" value="<%=jo.get("wf6Am") %>"><p v-html="icon(src4)"></p></td>
	<td><input type="hidden" v-model="src5" value="<%=jo.get("wf7Am") %>"><p v-html="icon(src5)"></p></td>
	<td><input type="hidden" v-model="src6" value="<%=jo.get("wf8") %>"><p v-html="icon(src6)"></p></td>
	<td><input type="hidden" v-model="src7" value="<%=jo.get("wf9") %>"><p v-html="icon(src7)"></p></td>
	<td><input type="hidden" v-model="src8" value="<%=jo.get("wf10") %>"><p v-html="icon(src8)"></p></td>
	</tr>
	
	<tr>
	<td><%=jo.get("wf3Am") %></td>
	<td><%=jo.get("wf4Am") %></td>
	<td><%=jo.get("wf5Am") %></td>
	<td><%=jo.get("wf6Am") %></td>
	<td><%=jo.get("wf7Am") %></td>
	<td><%=jo.get("wf8") %></td>
	<td><%=jo.get("wf9") %></td>
	<td><%=jo.get("wf10") %></td>
	</tr>
	
<%
	
	}
} else{
	out.println("데이터가 없습니다.");
}
%></table>
		</div>
		<div class="col" style="width:45%;height:100%; display:inline-block;">
			<div width="100%">
			<c:forEach var="pl" items="${productList}">
				<a href="productView?&piid=${pl.getPi_id() }"><img src="resources/img/product/${pl.getPi_img1() }" style="border-radius:8px; background-color:#efefefef; width:200px; height:200px"></a>
				<div style="text-align:left; padding-top: 15px; font-size: 13px; width: 200px; height: 100px;">		
					<p>${pl.getPi_name() }</p>
					<strong style="font-size:15px; align:left; padding-top: 15px;">${pl.getPi_price() }원</strong>
				</div>
			</div>
			</c:forEach>
			
		</div>
		
	</div>
	</div>
</div>
	
<div style="width:1300px"><hr></div>
<div style="width:1300px; padding-left:10px;" align="left">
<span style="font-size:20px; font-weight:bold;">Top brand</span>
</div>
<br>
<!-- 브랜드 변경예정 -->
<div id="brands">
<table style="text-align:center">
	<tr>
	<td width="33.3%">
	<a href="productList?pcb=null&sch=bNN"><img src="resources/img/brand/nikeLogo.png" id="brandImg" /></a>
	</td>
	<td width="33.3%">
	<a href="productList?pcb=null&sch=bDD"><img src="resources/img/brand/DrMartensLogo.png" id="brandImg" /></a>	
	</td>
	<td width="33.3%">
	<a href="productList?pcb=null&sch=bCC"><img src="resources/img/brand/crocsLogo.png" id="brandImg" /></a>
	</td>
	</tr>
</table>
</div>
<br>
<div style="width:1300px"><hr></div>
<div style="width:1300px; padding-left:10px;" align="left">
<span style="font-size:20px; font-weight:bold;">New</span>
</div>
<br>
<table>
<c:forEach var="nl" items="${newList}" begin="0" end="4">
	<td width="25%" align="center">
		<a href="productView?piid=${nl.getPi_id()}"">
		<img src="resources/img/product/${nl.getPi_img1()}" id="productImg" style="border-radius:8px; background-color:#efefefef;"/>
		</a>
		<br>
		<div style="text-align:left; padding-top: 15px; font-size: 13px; width: 200px; height: 100px;">
		<p>${nl.getPi_name()}</p>
		<strong style="font-size:15px; align:left; padding-top: 15px;"><fmt:formatNumber value="${nl.getPi_price()}" pattern="#,###원"/></strong>
		</div>
	</td>
</c:forEach>
</table>
<br>
<table>
<input type="button" value="더보기" class="btn btn-outline-secondary"  onclick="location.href='newProduct'" />
</table>
<br>
<div style="width:1300px"><hr></div>
<div style="width:1300px; padding-left:10px;" align="left">
<span style="font-size:18px; font-weight:bold;">Top sale</span>
</div>
<br>
<table>
<tr><td>
</td></tr>
<c:forEach var="sl" items="${saleList}" begin="0" end="4" varStatus="idx">
	<td width="25%" align="center">
		<a href="productView?piid=${sl.getPi_id()}">
		<img src="resources/img/product/${sl.getPi_img1()}" id="productImg" style="border-radius:8px; background-color:#efefefef;"/>
		</a>
		<br>
		<div style="text-align:left; padding-top: 15px; font-size: 13px; width: 200px; height: 100px;">
		<p>${sl.getPi_name()}</p>
		<strong style="font-size:15px; align:left; padding-top: 15px;"><fmt:formatNumber value="${sl.getPi_price()}" pattern="#,###원"/></strong>
		</div>
	</td>
</c:forEach>
</table>
<br><br>
<div style="background-color:yellowgreen;">
<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="width:1300px">
  <div class="carousel-indicators">
  	<c:forEach var="il" items="${imgList}" varStatus="i">
  	<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${i.index}" aria-label="Slide ${i.count}"
  	<c:if test="${i.index==0}">class="active" aria-current="true"</c:if >></button>
  	</c:forEach>
  </div>
  <div class="carousel-inner">
   <c:forEach var="il" items="${imgList}" varStatus="i">
		<div class="carousel-item active">
			<a href="${il.getMm_link() }"><img style="width:700px; height:600px;"src="resources/img/slide/${il.getMm_media() }"  class="d-block w-100" alt="" ></a>
		</div>
	 </c:forEach>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
</div>
<br>
<div style="width:1300px; padding-left:10px;" align="left">
	<span style="font-size:18px; font-weight:bold;">Top Style</span>
</div>
<br>
<table cellpadding="2">
<c:forEach var="si" items="${styleList}" varStatus="idx">
	<td align="center">
		<a href="styleView?siidx=${si.getSi_idx()}&piid=${si.getPi_id()}">
		<img src="resources/img/style_img/${si.getSi_img()}" width="200px" height="300px;" border="0" style="border-radius:8px;"/>
		</a>
	</td>
</c:forEach>
</table>
</div>
<br>
<hr><br>
<script>
new Vue ({
	el : "#app",
	data:{
        src1: '${jo.get("wf3Am")}',
        src2: '${jo.get("wf4Am")}',
        src3: '${jo.get("wf5Am")}',
        src4: '${jo.get("wf6Am")}',
        src5: '${jo.get("wf7Am")}',
        src6: '${jo.get("wf8")}',
        src7: '${jo.get("wf9")}',
        src8: '${jo.get("wf10")}'
      },
	computed : {
		icon () {
			 return function(srcKey) {
				switch (srcKey) {
            	case '맑음':
                	return "<img src='resources/img/weather/sun.png' width='30px' height='30px'>";
            	case '비':
                	return "<img src='resources/img/weather/rain.png' width='30px' height='30px'>"; 
           		 default:
                	return "<img src='resources/img/weather/cloud.png' width='30px' height='30px'>";
        		}
			};
		}
	}

});

</script>
<%@ include file="_inc/inc_foot_fr.jsp" %>