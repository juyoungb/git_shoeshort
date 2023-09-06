<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/inc_head_fr.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="resources/css/indexStyle.css">
<div id="video" align="center" style="background-color:black;">
    <video
  src="resources/video/${videoInfo.getMm_media() }"
  autoplay muted
  loop
  style="pointer-events: none;	width:1300px;  	height:400px;  	object-fit: fill;"></video>
</div>
<br>
<div align="center">
<div style="width:1300px"><hr></div>
<div>
	<div style="width:1300px;height:300px;">
		<div style="width:45%;height:100%;border: 1px solid black; display:inline-block;"></div>
		<div style="width:45%;height:100%; border: 1px solid black;display:inline-block;"></div>
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
		<img src="resources/img/product/${nl.getPi_img1()}" id="productImg" style="border-radius:8px;"/>
		</a>
		<br><span style=""></span>
		${nl.getPi_name()}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:formatNumber value="${nl.getPi_price()}" pattern="#,###원"/>
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
		<img src="resources/img/product/${sl.getPi_img1()}" id="productImg"style="border-radius:8px;"/>
		</a>
		<br>
		${sl.getPi_name()}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:formatNumber value="${sl.getPi_price()}" pattern="#,###원"/>
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
<%@ include file="_inc/inc_foot_fr.jsp" %>