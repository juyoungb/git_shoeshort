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

<style>
#info td { font-size:1.2em; }
#cnt { width:20px; text-ailgn:right;}
.smt { width:150px; height:30px;}
#review { display:none; }

option {border: 1px solid black;}

.input {
  outline: none;
   display: inline-block; 
   border: 1px solid black; 
   width:50px; height:20px; 
   cursor:pointer; 
   text-align:center;
    border-radius:10px;
    font-size: 13px;
}
.block {
display: inline-block; 
 width:50px; 
 height:20px; 
 border: 1px solid black; 
 text-align:center; 
 color:#efefef;
  border-radius:10px;
  font-size: 13px;
}


.hand { cursor:pointer; }
#main {   
	 margin-left: auto;
    margin-right: auto;
    max-width: 1280px;
    overflow: hidden;
    padding: 30px 40px 120px
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
 
  .time-section {
    display: inline-block;
    margin: 0 5px;
    padding: 10px;
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
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
function swapIng(img){
   var big = document.getElementById("bigImg"); 
   //큰이미지를 보여주는 img태그를 big이라는 이름의 객체로 받아줌
   big.src = "resources/img/product/" + img ;
}


function setCnt(op) {
   var price = <%=realPrice %>;
   var frm = document.frm;
   //var size = frm.size.value();    //10:150 ->  ps_idx:ps_stock
   var ss = "";
   var size = frm.size; // size라는 이름의 컨트롤들을 배열로 받아옴
	
   for(var i = 0; i < size.length ; i++){
      if(size[i].style.backgroundColor)   ss += "s" +size[i].value;   
   }
   if (ss != "") {
      var cnt = parseInt(frm.cnt.value);
   
      var max = 10;  // 재고가 10이상일 경우10을 최대값으로 지정
      
      if (op == "+" && cnt < max) frm.cnt.value = cnt + 1;   
      else if (op == "-" && cnt > 1)  frm.cnt.value = cnt - 1;
      
      var total =  document.getElementById('total');
      total.innerHTML = price * frm.cnt.value;
   
   } else {
      alert("옵션을 먼저 선택하세요.");
   } 
}

var preSize = "";
function chgColor(click) {
	var price = <%=realPrice %>;
   	var cnt = parseInt(frm.cnt.value);
   	var size1 = document.getElementById(preSize);
   	var total =  document.getElementById('total');
   	if(preSize != "")   {
   		size1.style.backgroundColor="";
    	frm.cnt.value = 1;   // size변경시 cnt값을 다시 1로 지정
    }
   	total.innerHTML = price * frm.cnt.value;
   
   	var size2 = document.getElementById(click);
   	if(size2.style.backgroundColor)   size2.style.backgroundColor="";
   	else                     size2.style.backgroundColor="rgb(170, 170, 170)";
   	preSize = click;
}

function buy(kind) {
	<% if (isLogin) {%>
   		var frm = document.frm;
   		var ss = "";
	   	var size = frm.size; 
	   	var cnt = parseInt(frm.cnt.value);
		
   		for(var i = 0; i < size.length ; i++){
	   		if(size[i].style.backgroundColor)   ss += size[i].value;
   		}
   
   		if(ss == ""){
      		alert("옵션(사이즈)을 선택하세요.");  
     		return;
   		} 
   
		if (kind == "c") {//장바구니 담기일 경우   
      		$.ajax({
         		type :"POST" , url:"cartProcIn",
		        data:{"piid" : "<%=productList.get(0).getPi_id() %>", "psidx" : ss, "cnt": cnt},
		        success :function(chkRs){//장바구니 담기에 실패했을 경우
		        	if(chkRs == 0){
               			alert("장바구니 담기에 실패하였습니다.\n다시 시도해 주세요.");
               			return;
         			} else { //장바구니 담기에 성공했을 경우
		            	if(confirm("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?")) {
		               		location.href="orderCart";
		            	}
         			}
      			}
     		}); 
    } else {// 바로 구매하기일 경우
        document.frm2.size.value = ss;
	    document.frm2.cnt.value = cnt;
	    document.frm2.submit(); 	      
    }
<% } else {%> 
   location.href = "login?url=/shoeshort/productView?piid=<%=productList.get(0).getPi_id() %>";
<%}%>
}
   
function showTap(chk) {
	var obj1 = document.getElementById("desc");
	obj1.style.display="none"
	var obj2 = document.getElementById("review");
	obj2.style.display="none";
	
	var obj3 = document.getElementById(chk);
	obj3.style.display="block"

}



</script>
<div align="center" class="m-3">
<div class="container">
<table width="1200" cellpadding="5">
<tr align="center">
<td width="50%" align="center">
<!-- 이미지 관련 영역 -->
   <table width="100%" cellpadding="5">
   <tr><td colspan="3" align="center">

 <% 
   for(ProductInfo pl: productList){%>
      <img src="resources/img/product/<%=pl.getPi_img1()%>" width="450px" height="450" id="bigImg">
      </td></tr>      
      <tr align="center">
      <div>
      <td width="33.3%">
         <img src="resources/img/product/<%=pl.getPi_img1()%>" width="160" height="160" onclick="swapIng('<%=pl.getPi_img1()%>')" class="hand">
      </td>
      <td width="33.3%">
 <% if(pl.getPi_img2() !=null && !pl.getPi_img2().equals("")) {%>		
			<img src="resources/img/product/<%=pl.getPi_img2() %>" width="160" height="160" onclick="swapIng('<%=pl.getPi_img2() %>')" class="hand">
<%} %>		
		</td>
		<td width="33.3%">
<% if(pl.getPi_img3() !=null && !pl.getPi_img3().equals("")) {%>		
			<img src="resources/img/product/<%=pl.getPi_img3() %>" width="160" height="160" onclick="swapIng('<%=pl.getPi_img3() %>')" class="hand">
<%} %>
	  </td>	   
      </div>
      </tr>   
      </table>
      <form name="frm2" method="post" action="orderForm">
		   <input type="hidden" name="piid" value="<%=pl.getPi_id()%>">
		   <input type="hidden" name="kind" value="d">
		   <input type="hidden" name="cnt" value="">
		   <input type="hidden" name="size" value="">
	 </form>  
<td width="35%" valign="top" >
<!-- 상품 정보 관련 영역 -->
   <form name="frm" method="post">
   <input type="hidden" name="kind" value="d">
   <input type="hidden" name="piid" value="<%=pl.getPi_id()%>">
   
   <table  cellpadding="5" style="height:600px; width:100%" id="info">   
   <tr><td algin="left" colspan="2"  style="font-weight: bold;"><%=pl.getPb_name()%></td>
   <tr><td colspan="2" width="20%" algin="left"><%=pl.getPi_name()%></td>
   <tr><td align="left" style="font-size:15px">브랜드</td><td style="font-size:15px"><%=pl.getPb_name()%></td></tr>
   <tr><td align="left" style="font-size:15px">제조사</td><td style="font-size:15px"><%=pl.getPi_com() %></td></tr>
   <tr><td align="left">가격</td><td><%=realPrice %>원</td></tr>   
   <tr>
   <td style="font-size: 14px; font-weight: bold;">사이즈</td>
   <td> 
     
   <div>	
   	  <input type="hidden" name="size" value="" >
      <c:forEach items="${stockList }" var="sl" >
  			<c:if test="${sl.getPs_stock() == 0 }">
				<input type="text"  name="size" id="${sl.getPs_size() }"  value="${sl.getPs_size() }"  class="block"  > 
			</c:if>
			<c:if test="${sl.getPs_stock() != 0 }">
     			<input type="text"  name="size" id="${sl.getPs_size() }"  value="${sl.getPs_size() }"  onclick="chgColor('${sl.getPs_size() }');" 
    			class="input"  readonly="readonly">  
    		</c:if>
		</c:forEach>
		<hr>
   </div>
   </td>
   
   </tr>
   <tr>
   <td align="right">수량</td>
   <td>
   <input type="button"  class="btn btn-outline-secondary btn-sm"  value="-" onclick="setCnt(this.value);" style="width:25px; height:30px">
   <input type="text" name="cnt" id="cnt"  value="1" readonly="readonly" style="width:25px; height:30px">
   <input type="button"  class="btn btn-outline-secondary btn-sm" value="+" onclick="setCnt(this.value);" style="width:25px; height:30px">
   </td>
   </tr>
   <tr><td colspan="2" align="left" style="font-size: 20px; font-weight: bold;"> 총 결제 금액: <span  align="right" id="total"></span>원
   </td></tr>
   <tr><td colspan="2" align="center">
   <input type="button" value="장바구니 담기" class="btn btn-danger" style="background-color: #FF4646;"  onclick="buy('c');">
   <input type="button" value="바로 구매하기" class="btn btn-success" onclick="buy('d');">
   </table>
   </form>
</td>
</tr>
<tr>
</table>
<hr>
<p align="left" style="">스타일</p>
<div><!-- style -->
<div class="album py-5 bg-body-tertiary" >
<div class="container">
<div class="row row-cols-3 g-3">
 <c:forEach items="${styleList }" var="si" >
	   <div class="col-xs-3">
          <div class="card" style="width: 18rem;">
          	<a href="styleView?siidx=${si.getSi_idx() }&piid=${si.getPi_id()}">
            <img src="resources\img\style_img\${si.getSi_img()}" width="100%" height="350" /></a>
            <div class="card-body">
              <p class="card-text"><a href="memStyle?miid=${si.getMi_id()}" style="color:black; text-decoration-line: none;">${si.getMi_id() }</a></p>
              <div class="d-flex justify-content-between align-items-center">
                	<div width="50%">${si.getSi_content() }</div>
                <div align="right"><img src="resources\img\style_img\${si.getSi_img()}" width="20px" height="20px" />${si.getSi_good() }</div>
              </div>
            </div>
          </div>
        </div>

</c:forEach>
   </div>   
 </div>
  </div>   
</div>
<input type="button" value="더보기" class="btn btn-outline-secondary"  onclick="location.href='style'" />
<hr>
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" data-toggle="tab" href="#desc">상품설명</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#change">번역</a>
   
  </ul>
  <div class="tab-content">
    <div class="tab-pane fade show active" id="desc">
       <img src="resources/img/product/crocs_detail.png" width="1200" height="1000">
   <%--     <img src="resources/img/product/<%=pl.getPi_desc() %>" width="1200" height="1000"> --%>
    </div>
    <div class="tab-pane fade" id="change">
   	<div id="app">
   	<div class="row m-3">
    <div class="col-md-6">
    	내용  <textarea v-model="text" style="width:100%; height:350px;"></textarea>
    </div>
    <div class="col-md-6">
       	 결과  <div style="width:100%; height:350px; background-color: #efefefef; padding: 10px; text-align: left;">{{ result }}</div>
    </div>
	</div>
	<div class="row">
    <div class="col-md-12 text-center mt-3">
        <!-- 번역하기 버튼 -->
        <button class="btn btn-dark" v-on:click="translate()">번역하기</button>
    </div>
</div>
    	</div>
    </div>
  </div>

</div>


</div>
</div>
 
<script>
new Vue({
    el: "#app", 
    data: {
        text: "",
        result: ""
    }, 
    methods: {
        translate: function() {
            axios.get(
                "./trans?text=" + this.text, {
                    headers: { "Content-Type": "application/json; charset=UTF-8" }
                }
            )
            .then(response => {
               // alert(JSON.stringify(response.data));

                this.result = response.data;
            })
            .catch(e => {    // GET 요청 실패시 실행할 코드
                console.log(e);
            })
        }
    }
});
</script> 

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<%} %>
<%@ include file="../_inc/inc_foot_fr.jsp" %>