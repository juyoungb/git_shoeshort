<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%

request.setCharacterEncoding("utf-8");
String g = request.getParameter("g");
String ct = request.getParameter("ct");
System.out.println("g :" +g);
System.out.println("ct :" +ct);
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
List<ProductInfo> productList = (List<ProductInfo>)request.getAttribute("productList");
List<ProductStock> stockList = (List<ProductStock>)request.getAttribute("stockList");
List<ProductBrand> brandList = (List<ProductBrand>)request.getAttribute("brandList");
List<ProductCtgrBig> ctgrList = (List<ProductCtgrBig>)request.getAttribute("ctgrList");



String name = "", ctgr = "", chkBrd = "",  sp = "", ep = "", sch = pageInfo.getSch() , gender="", size="";

if (sch != null && !sch.equals("")) {
// 검색조건 : &sch=ntest,bB1:B2:B3,p100000~20000
   String[] arrSch = sch.split(",");
   for (int i = 0 ; i < arrSch.length ; i++) {
      char c = arrSch[i].charAt(0);
      if (c == 'n') {// 상품명 검색일 경우(n 검색어)
         name = arrSch[i].substring(1);
      } else if (c =='c') {// 브랜드 검색일 경우(c:카테고리)
         ctgr = arrSch[i].substring(1);
      } else if (c =='b') {// 브랜드 검색일 경우(b 브랜드 1:브랜드2)
         chkBrd = arrSch[i].substring(1);
      } else if (c =='p') { // 가격대 검색일 경우(p시작가~종료가 (이상~이하))
         sp = arrSch[i].substring(1, arrSch[i].indexOf('~'));      //시작가               
         ep = arrSch[i].substring(arrSch[i].indexOf('~') + 1); //종료가            
      } else if (c =='g') { // 성별검색일 경우(g 성별)
         gender = arrSch[i].substring(1);         
   
      } else if (c =='s') { // 성별검색일 경우(s 사이즈)
         size = arrSch[i].substring(1);      

      }
   }
}
%>
<style>
.bigCtgr {
   width:100px; height:30px; font-size:1.5em; background:#efefef;
   text-align:center; border:1px solid #c1c1c1; margin:10px;
   padding:5px; display:inline-block;
}
#pcb { background:lightgreen; }
del { font-size:0.7em; color:#a0a0a0;}
.saleStock { font-size:0.7em; }
 body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
  }
  .page-link {
  color: #000; 
  background-color: #fff;
  border: 1px solid #ccc; 
}

.page-item.active .page-link {
 z-index: 1;
 color: #555;
 font-weight:bold;
 background-color: #f1f1f1;
 border-color: #ccc;
 
}

.page-link:focus, .page-link:hover {
  color: #000;
  background-color: #fafafa; 
  border-color: #ccc;
}
#main {   
	 margin-left: auto;
    margin-right: auto;
    max-width: 1280px;
    overflow: hidden;
    padding: 30px 40px 120px
    }
</style>
<script>
function makeSch() {   // 검색폼의 조건들을 쿼리스트링sch의 값으로 만듬    검색조건 : &sch=ntest,c:a,bB1:B2:B3,gw,s240,p100000~20000   
   var frm = document.frm2;   var sch = "";
   // 상품명 검색어 조건
   var pdt = frm.pdt.value.trim();
   if (pdt != "") sch += "n" + pdt; //sch=n검색어
   
   //카테고리
   var arr = frm.ctgr; // 카테고리는 이름의 컨트롤들을 배열로 받아옴

   var isFrist = true;   // 카테고리 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
   for(var i = 0 ; i < arr.length ; i++){
      if (arr[i].checked) {    
      
         if (isFrist){ //첫번째로 선택한 체크박스이면 
            if (sch != "") sch += ",";   // 기존에 검색어가 있을경우 쉼표로 구분         
            isFrist = false;         
            sch += "c" + arr[i].value;
            
         } else {
            sch += ":" + arr[i].value;
         }
      }
   }   
   
   // 브랜드 조건
   var barr = frm.brand; // brand라는 이름의 컨트롤들을 배열로 받아옴
   var isFrist = true;   //브랜드 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
   for(var i = 0 ; i < barr.length ; i++){
      if (barr[i].checked) {    
         if (isFrist){ //첫번째로 선택한 체크박스이면
            if (sch != "") sch += ",";   // 기존에 검색어가 있을경우 쉼표로 구분         
            isFrist = false;         
            sch += "b" + barr[i].value;
         } else {
            sch += ":" + barr[i].value;
         }
      }
   }   // sch=n검색어,b브랜드(들)
   
   // 성별
   
   //var gender = frm.gender;
   // 성별
   var garr = frm.gender; // gender라는 이름의 컨트롤들을 배열로 받아옴
   var isFrist = true;   //성별 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
   for(var i = 0 ; i < garr.length ; i++){
      if (garr[i].checked) {    
         if (isFrist){ //첫번째로 선택한 체크박스이면
            if (sch != "") sch += ",";   // 기존에 검색어가 있을경우 쉼표로 구분         
            isFrist = false;         
            sch += "g" + garr[i].value;
         } else {
            sch += ":" + garr[i].value;
         }
      }
   }   
   
   //사이즈   
   var sarr = frm.size; // size라는 이름의 컨트롤들을 배열로 받아옴
   var isFrist = true;   //size 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
   for(var i = 0 ; i < sarr.length ; i++){
      if (sarr[i].checked) {    
         if (isFrist){ //첫번째로 선택한 체크박스이면
            if (sch != "") sch += ",";   // 기존에 검색어가 있을경우 쉼표로 구분         
            isFrist = false;         
            sch += "s" + sarr[i].value;
         } else {
            sch += ":" + sarr[i].value;
         }
      }
   }   
   
   //가격대
   var sp = frm.sp.value,  ep = frm.ep.value;
   if (sp !="" || ep != "") { // 가격대중 하나라도 값이 있으면
      if (sch != "") sch += ",";
      sch += "p" + sp + "~" + ep; // sch=n검색어,b브랜드(들),p최저가~최고가
   }

   
   document.frm1.sch.value = sch;
   document.frm1.submit();
   
}

function initSch() { // 검색조건 (상품명, 브랜드, 가격대)들을 모두 없애주는 함수
	
   var frm = document.frm2;
   frm.pdt.value="";   frm.sp.value="";   frm.ep.value="";

   
   var arr ="";
   
   arr = frm.ctgr;   // ctgr라는 이름의 컨트롤들을 배열로 받아옴
   for(var i = 0; i < arr.length; i++) {
      arr[i].checked = false;   // 체크표시 날림
   }
   
   arr = frm.brand;   // brand라는 이름의 컨트롤들을 배열로 받아옴
   for(var i = 0; i < arr.length; i++) {
      arr[i].checked = false;   // 체크표시 날림
   }
   
   
   arr = frm.gender;   // gender라는 이름의 컨트롤들을 배열로 받아옴
   for(var i = 0; i < arr.length; i++) {
      arr[i].checked = false;   // 체크표시 날림
   }
   
   arr = frm.size;   // size라는 이름의 컨트롤들을 배열로 받아옴
   for(var i = 0; i < arr.length; i++) {
      arr[i].checked = false;   // 체크표시 날림
   }
} 

function onlyNum(obj) {
   if (isNaN(obj.value)) {
      // isNaN = 숫자가 아니면
      obj.value = "";
   }
}


function display(ct){    
    var con = document.getElementById("myDIV" + ct);    
    var con1 = document.getElementById(ct);    
    if(con.style.display=='none'){       
       con.style.display = 'block';    
       con1.src="resources/img/product/minus.png"
    }else{       
       con.style.display = 'none'; 
       con1.src="resources/img/product/plus.png"
    } 
}

</script>
</head>
<body>
<div align="center" class="m-5"><h2 style="font-weight:bold; align:center">상품 목록</h2>
	<table width="90%">
		<tr>
		<div class="m-5">
			<td width="150" valign="top">
<!-- 검색조건 입력 폼  -->
   			<form name="frm1">
<!-- 검색조건으로 링크를 걸기 위한 쿼리스트링용 컨트롤들의 집합 전부 hidden -->
	   			<input type="hidden" name="pcb" value="<%=pageInfo.getPcb() %>">
  			 	<input type="hidden" name="sch" value="">
   			</form>
      		<form name="frm2"> 
				<div class="p-2" style="border: 1px solid black;">   
      				<input type="text" class="form-control" name="pdt" id="pdt" placeholder="상품명  검색" value="" width="100" height="20px"><br>   
     				카테고리<img src="resources/img/product/plus.png" onclick="display('A');" width="25px" height="25px" id="A"><br>
      				<div class="form-check" id="myDIVA" style="display:none" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
						<% for (ProductCtgrBig pbc : ctgrList) { %>
								<input type="checkbox" name="ctgr" id="<%=pbc.getPcb_id() %>" value="<%=pbc.getPcb_id() %>" 
						<%
   								if (ctgr.indexOf(pbc.getPcb_id()) >= 0) { %>checked="checked"<% } %>><label class="form-check-label" for="<%=pbc.getPcb_id() %>"><%=pbc.getPcb_name() %></label><br>
						<% } %>      
      				</div>   
      
   
      				브랜드명<img src="resources/img/product/plus.png" onclick="display('B');" width="25px" height="25px"  id="B"><br>
      				<div class="form-check" id="myDIVB" style="display:none" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
						<% for (ProductBrand brand : brandList) {%>
							<input type="checkbox" name="brand" id="<%=brand.getPb_id() %>" value="<%=brand.getPb_id() %>"
   						<%
  							 if (chkBrd.indexOf(brand.getPb_id()) >= 0) { %>checked="checked"<% } %>><label class="form-check-label" for="<%=brand.getPb_id() %>"><%=brand.getPb_name() %></label><br>
						<% } %>   
      				</div>
      				
      				성별<img src="resources/img/product/plus.png" onclick="display('C');" width="25px" height="25px" id="C"><br>
      				<div class="form-check" id="myDIVC" style="display:none" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
				      	<input type="checkbox" name="gender" id="m" value="m" <% if (gender.indexOf("m") >= 0)  { %>checked="checked"<% } %>>
				      	<label class="form-check-label" for="m">남자</label><br>
				      	
				      	<input type="checkbox" name="gender" id="w" value="w" <% if (gender.indexOf("w") >= 0) { %>checked="checked"<% } %>>
				      	<label class="form-check-label" for="w">여자</label><br>
				      	
				      	<input type="checkbox" name="gender" id="k" value="k" <% if (gender.indexOf("k") >= 0) { %>checked="checked"<% } %>>
				      	<label class="form-check-label" for="k">키즈</label>
      				</div>

     				 사이즈<img src="resources/img/product/plus.png" onclick="display('D');" width="25px" height="25px" id="D"><br>
      				<div class="form-check" id="myDIVD" style="display:none" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
						<% for (ProductStock st : stockList) {%>
							<input type="checkbox" name="size" id="<%=st.getPs_size() %>" value="<%=st.getPs_size() %>" 
   						<%
   							if (size.indexOf(st.getPs_size()) >= 0) { %>checked="checked"<% } %>><label class="form-check-label" for="<%=st.getPs_size() %>"><%=st.getPs_size() %></label><br>
						<% } %>      
					</div>      
				    <input type="text" name="sp" class="price" value="" placeholder="최저가" onkeyup="onlyNum(this);" width="100" height="20px">~
				    <input type="text" name="ep" class="price" value="" placeholder="최고가" onkeyup="onlyNum(this);" width="100" height="20px">
   					<div class="mx-auto p-2">	
					    <input type="button" value="상품 검색" class="btn btn-dark btn-sm" onclick="makeSch();">
						<input type="button" value="조건 초기화" class="btn btn-outline-secondary btn-sm"  onclick="location.href=productList?sch=<%=pageInfo.getInit()%>">
					</div>		   

				</div>  
   			</form>
		</td>
	</div>
<td width="*" valign="top">
<!-- 상품 목록 및 페이징 영역 -->
	<%
	if (pageInfo.getRcnt() > 0) {
   		String lnk = "productList?cpage=1" + pageInfo.getSchargs();  // 정렬 및 보기 방식용 공통 링크 정렬이 바뀌면 다시 1페이지로돌아옴
	%>
	   <div class="container">
	   <hr>
   		<div align="right" style="width:100%;">
     	 <select name="ob" onchange="location.href='<%=lnk  %>&ob=' + this.value;">
         	<option value="a" <% if(pageInfo.getOb().equals("a")) { %>selected="selected"<% } %>>최신 순</option>
         	<option value="b" <% if(pageInfo.getOb().equals("b")) { %>selected="selected"<% } %>>판매 순</option>
         	<option value="c" <% if(pageInfo.getOb().equals("c")) { %>selected="selected"<% } %>>낮은 가격순</option>
         	<option value="d" <% if(pageInfo.getOb().equals("d")) { %>selected="selected"<% } %>>높은 가격순</option>
      	</select>&nbsp;&nbsp;&nbsp;&nbsp;
   		</div>   
  

   <div class="row row-cols-4">
	<%
   		int i = 0;
   	 	for (i = 0 ; i < productList.size() ; i++) {
    		ProductInfo pi = productList.get(i);
        	String price = pi.getPi_price() + "원";      
        	if (pi.getPi_dc() > 0) {  //할인율이 있으면
            	price = Math.round(pi.getPi_price() * (1 - pi.getPi_dc()) )+ "원"; //실제 판매가
            	price = "<del>" + pi.getPi_price() + "</del>&nbsp;&nbsp;&nbsp;" + price;
        	}
        
			%>
			
			
			<div class="col-2 m-4"> 
				<div class="card" style="width: 15rem;">
		   			<a href="productView?&piid=<%=pi.getPi_id()%>" >
		   			<img src="resources/img/product/<%=pi.getPi_img1() %>" class="card-img" width="250px" height="250px"></a>
		        	<div class="card-body">
		        		<p class="card-text" style="font-weight:bold; font-size: 11px"><%=pi.getPb_name() %></p>
		        		<p style="font-size: 11px"><%=pi.getPi_name() %></p>
		        	 	<em><%=price %></em>
		        	</div>       
		      	</div>
		   </div>
		    
		
			<%         
   		}
   out.println("</div>");
   out.println("</div>");	

   out.println("</table>");
  
	%>
<!-- 상품 페이징 -->
<table width="100%" align="center" cellpadding="6" border="0" >
<tr><td align="center" colspan="7">
<%
if (pageInfo.getRcnt() > 0) {
	String link = "productList?1=1"+pageInfo.getSchargs()+"&cpage=";
%>
	<nav aria-label="Page navigation example" style="text-align:center; width:200px;">
		<ul class="pagination  justify-content-center"" >
<%
	if (pageInfo.getCpage() == 1) {
%>
			<li class="page-item-black"><a class="page-link" href="#">이전</a></li>
<%
	} else {
%>
			<li class="page-item"><a class="page-link" href="<%=link + (pageInfo.getCpage() - 1)%>">이전</a></li>
<% 
	}
	int spage = (pageInfo.getCpage() -1) / pageInfo.getBsize() * pageInfo.getBsize() + 1; //현재 블록에서의 시작 페이지 번호
	int j = 0;
	for (i = 1, j = spage ; i<= pageInfo.getBsize() && j <= pageInfo.getPcnt() ; i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (pageInfo.getCpage() == j){
%>
			<li class="page-item"><a class="page-link" href="#"><%=j %></a></li>
<%
		} else {
%>			<li class="page-item"><a class="page-link" href="<%=link + j%>"><%=j %></a></li> <%
		}
	}
	if (pageInfo.getCpage() == pageInfo.getPcnt()){
%>
			<li class="page-item"><a class="page-link" href="#">다음</a></li>
<%
	} else{ 
%>
			<li class="page-item"><a class="page-link" href="<%=link +(pageInfo.getCpage()  + 1) %>">다음</a></li>
<%
	}
%>
		</ul>
	</nav>
<%
	}
}
%>

</td>
</tr>
</table>
<%@ include file="../_inc/inc_foot_fr.jsp" %>