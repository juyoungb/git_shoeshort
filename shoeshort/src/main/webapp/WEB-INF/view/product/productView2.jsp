<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

request.setCharacterEncoding("utf-8");
List<ProductInfo> productList = (List<ProductInfo>)request.getAttribute("productList");

long realPrice = productList.get(0).getPi_price();   // 수량 변경에 따른 가격 연산을 위한 변수
String price = productList.get(0).getPi_price() + "원"; // 가격 출력을 위한 변수
if (productList.get(0).getPi_dc() > 0) { //할인율이 있으면
   realPrice = Math.round(realPrice * (1- productList.get(0).getPi_dc()));
   price = "<del>" + productList.get(0).getPi_price() + "</del>" + "&nbsp;&nbsp;&nbsp;" + realPrice + "원";
}

%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet">
<title>Bootstrap Example</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script>

</script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
</script>
<style>
#main {   
	 margin-left: auto;
    margin-right: auto;
    max-width: 1280px;
    overflow: hidden;
    padding: 30px 40px 120px
    }
#button {
    font-size: 18px;
    font-weight: 700;
    height: 40px;
    letter-spacing: -.09px;
    width:100%
}       
.count {
	border:1px solid black;
}
 body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
  }
  
  #countdown {

    padding: 20px;
    display: inline-block;
  }
  
  span {
    font-size: 24px;
    margin: 0 5px;
    padding: 5px 10px;
    background-color: #333;
    color: #fff;
    border-radius: 5px;
  }

  
  .time-label {
    margin-top: 5px;
    font-size: 14px;
    color: #555;
  }
  
</style>

</head>
<body>

<div id="main" class="grid gap-2">
<% 
   for(ProductInfo pl: productList){%>
   <form name="frm2" method="post" action="orderForm">
   <input type="hidden" name="piid" value="<%=pl.getPi_id()%>">
   <input type="hidden" name="kind" value="d">
   <input type="hidden" name="size" value="">
   <input type="hidden" name="cnt" value="">
	</form>  

	<div class="p-2 g-col-6">
    	<img src="resources/img/product/<%=pl.getPi_img1()%>"  height="500px">
  	
  	
  	<div class="g-col-5">
   		<strong class="product-title">EVENT</strong>
    	<div>
     		<p class="product-description"></p>
			<div id="countdown" style="text-align:center">
				<div>
					<form name="frm" method="post">
  					<input type="hidden" name="kind" value="d">
   					<input type="hidden" name="piid" value="<%=pl.getPi_id()%>">
			  	</div>
				<div>
					상품명<%=pl.getPi_name()%>
					브랜드<%=pl.getPb_name()%>
					제조사<%=pl.getPi_com() %>
					가격<%=realPrice %>    
				</div> 
				<div>
  					<c:forEach items="${stockList }" var="sl" >
      					<c:if test="${sl.getPs_stock() == 0 }">
							<input type="text" name="size" id="${sl.getPs_size() }"  value="${sl.getPs_size() }"  class="block" style="width:30px; height:15px;" > 
						</c:if>
						<c:if test="${sl.getPs_stock() != 0 }">
         					<input type="text" name="size" id="${sl.getPs_size() }"  value="${sl.getPs_size() }"  onclick="chgColor('${sl.getPs_size() }');" 
        			 		class="input" style="width:30px; height:15px;" readonly="readonly">  
        				</c:if>
					</c:forEach>
				</div> 			
				<div>
					  <input type="button" value="-" onclick="setCnt(this.value);">
					  <input type="text" name="cnt" id="cnt"  value="1" readonly="readonly" >
					  <input type="button" value="+" onclick="setCnt(this.value);">									 
					     구매 가격 : <span id="total"></span>원					 
					   <input type="button" value="바로 구매하기" class="btn btn-white" style="background-color: #FF4646;" onclick="buy('d');">
					  <input type="button" value="장바구니 담기" class="btn btn-success" onclick="buy('c');">
				
					  </form>				
				</div> 
				</div>
			</div>
    	</div>
  	</div>

  	<div class="p-2 g-col-12">
  		<hr>
   		<div class="p-3 m-1" >
  			<strong>유의 사항</strong>
	  		
		</div>
</div> 

</body>
</html>
<%} %>
<%@ include file="../_inc/inc_foot_fr.jsp" %>